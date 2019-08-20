package com.example.mecanos.db;

import android.content.Context;

import com.example.mecanos.AppExecutors;
import com.example.mecanos.db.converter.DateConverter;
import com.example.mecanos.db.dao.IngredientDao;
import com.example.mecanos.db.dao.PlatoDao;
import com.example.mecanos.db.dao.PlatoIngredientDao;
import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.db.entity.PlatoEntity;
import com.example.mecanos.db.entity.PlatoFtsEntity;
import com.example.mecanos.db.entity.PlatoIngredientEntity;
import com.example.mecanos.model.PlatoIngredient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {PlatoEntity.class,
                      PlatoFtsEntity.class,
                      IngredientEntity.class,
                      PlatoIngredientEntity.class},
        version = 2)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "basic-sample-db";

    public abstract PlatoDao platoDao();

    public abstract IngredientDao ingredientDao();

    public abstract PlatoIngredientDao platoIngredientDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }
    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                // Add a delay to simulate a long-running operation
                                addDelay();
                                // Generate the data for pre-population
                                AppDatabase database = AppDatabase.getInstance(appContext, executors);
                                //List<PlatoEntity> platos = DataGenerator.generatePlatos();
                                //List<IngredientEntity> ingredients = DataGenerator.generateIngredients();
                                //List<PlatoIngredientEntity> pi = DataGenerator.generateIngredientsForPlatos();

                                //insertData(database, platos, ingredients, pi);
                                // notify that the database was created and it's ready to be used
                                database.setDatabaseCreated();
                            }
                        });
                    }
                })
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final AppDatabase database, final List<PlatoEntity> platos,
                                   final List<IngredientEntity> ingredients,
                                   final List<PlatoIngredientEntity> pi) {
        database.runInTransaction(()->{
            database.platoDao().insertAll(platos);
            database.ingredientDao().insertAll(ingredients);
            database.platoIngredientDao().insertAll(pi);
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `platosFts` USING FTS4("
                    + "`name` TEXT, `description` TEXT, content=`platos`)");
            database.execSQL("INSERT INTO platosFts (`rowid`, `name`, `description`) "
                    + "SELECT `id`, `name`, `description` FROM platos");

        }
    };

}
