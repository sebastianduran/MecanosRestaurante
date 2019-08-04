package com.example.mecanos;

import com.example.mecanos.db.AppDatabase;
import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.db.entity.IngredientsByPlatoEntity;
import com.example.mecanos.db.entity.PlatoEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class DataRepository {
    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<PlatoEntity>> mObservablePlatos;

    // esto lo agrege sin saber si es necesario private MediatorLiveData<List<IngredientEntity>> mObservableIngredients;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservablePlatos = new MediatorLiveData<>();

        mObservablePlatos.addSource(mDatabase.platoDao().loadAllPlatos(),
                platoEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservablePlatos.postValue(platoEntities);
                    }
                });


    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<PlatoEntity>> getPlatos() {
        return mObservablePlatos;
    }

    public LiveData<PlatoEntity> loadPlato(final int platoId) {
        return mDatabase.platoDao().loadPlato(platoId);
    }

    public LiveData<IngredientEntity> loadIngredient(final int ingredientId) {
        return mDatabase.ingredientDao().loadIngredient(ingredientId);
    }

    public LiveData<List<IngredientEntity>> loadAllIngredients() {
        return mDatabase.ingredientDao().loadAllIngredients();
    }

    //* Repository para la tabla que relaciona platos e ingredientes*/
    public LiveData<List<IngredientsByPlatoEntity>> loadPlatoIngredients(final int platoId) {
        return mDatabase.platoIngredientDao().loadPlatoIngredients(platoId);
    }


    public LiveData<List<PlatoEntity>> searchPlatos(String query) {
        return mDatabase.platoDao().searchAllPlatos(query);
    }
}
