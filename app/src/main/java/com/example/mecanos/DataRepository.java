package com.example.mecanos;

import android.os.AsyncTask;

import com.example.mecanos.db.AppDatabase;
import com.example.mecanos.db.dao.IngredientDao;
import com.example.mecanos.db.dao.PlatoDao;
import com.example.mecanos.db.dao.PlatoIngredientDao;
import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.db.entity.IngredientsByPlatoEntity;
import com.example.mecanos.db.entity.PlatoEntity;
import com.example.mecanos.db.entity.PlatoIngredientEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class DataRepository {
    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<PlatoEntity>> mObservablePlatos;
    private PlatoDao platoDao;
    private MediatorLiveData<List<IngredientEntity>> mObservableIngredients;
    private IngredientDao ingredientDao;
    private PlatoIngredientDao platoIngredientDao;

    // esto lo agrege sin saber si es necesario private MediatorLiveData<List<IngredientEntity>> mObservableIngredients;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservablePlatos = new MediatorLiveData<>();
        mObservableIngredients = new MediatorLiveData<>();

        mObservablePlatos.addSource(mDatabase.platoDao().loadAllPlatos(),
                platoEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservablePlatos.postValue(platoEntities);
                    }
                });

        mObservableIngredients.addSource(mDatabase.ingredientDao().loadAllIngredients(),
                ingredientEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null){
                        mObservableIngredients.postValue(ingredientEntities);
                    }
                });

        //Esta parte se necesita para insertar plato
        //es independiente a las anteriores instrucciones solo utiliza la definicion de database
        platoDao = database.platoDao();
        ingredientDao = database.ingredientDao();
        platoIngredientDao = database.platoIngredientDao();

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

    public LiveData<PlatoEntity> getLastPlatoLive(){
        return mDatabase.platoDao().getLastPlatoLive();
    }

    public LiveData<List<IngredientEntity>> getIngredients(){
        return mObservableIngredients;
    }

    public LiveData<IngredientEntity> loadIngredient(final int ingredientId) {
        return mDatabase.ingredientDao().loadIngredient(ingredientId);
    }

    public LiveData<List<IngredientEntity>> loadAllIngredients() {
        return mDatabase.ingredientDao().loadAllIngredients();
    }

    //* Repository para la tabla que relaciona platos e ingredientes*/
    public LiveData<List<IngredientEntity>> loadPlatoIngredients(final int platoId) {
        return mDatabase.platoIngredientDao().loadPlatoIngredients(platoId);
    }


    public LiveData<List<PlatoEntity>> searchPlatos(String query) {
        return mDatabase.platoDao().searchAllPlatos(query);
    }

    public LiveData<List<IngredientEntity>> searchIngredients(String query) {
        return mDatabase.ingredientDao().searchAllIngredients(query);
    }

    //*agregar
    public void insert(PlatoEntity plato){
        new InsertPlatoAsyncTask(platoDao).execute(plato);
    }

    private static class InsertPlatoAsyncTask extends AsyncTask<PlatoEntity, Void, Void> {
        private PlatoDao platoDao;
        private InsertPlatoAsyncTask(PlatoDao platoDao){
            this.platoDao = platoDao;
        }
        @Override
        protected Void doInBackground(PlatoEntity... platos) {
            platoDao.insert(platos[0]);
            return null;
        }
    }

    // CRUD ingredients
    public void insertIngredient(IngredientEntity ingredient){
        new InsertIngredientAsyncTask(ingredientDao).execute(ingredient);
    }

    private static class InsertIngredientAsyncTask extends AsyncTask<IngredientEntity, Void, Void> {
        private IngredientDao ingredientDao;
        private InsertIngredientAsyncTask(IngredientDao ingredientDao){
            this.ingredientDao = ingredientDao;
        }
        @Override
        protected Void doInBackground(IngredientEntity... ingredients) {
            ingredientDao.insert(ingredients[0]);
            return null;
        }
    }

    public void update(PlatoEntity plato){
        new UpdatePlatoAsyncTask(platoDao).execute(plato);
    }

    private static class UpdatePlatoAsyncTask extends AsyncTask<PlatoEntity, Void, Void> {
        private PlatoDao platoDao;
        private UpdatePlatoAsyncTask(PlatoDao platoDao){
            this.platoDao = platoDao;
        }
        @Override
        protected Void doInBackground(PlatoEntity... platos) {
            platoDao.update(platos[0]);
            return null;
        }
    }

    public void delete(PlatoEntity plato){
        new DeletePlatoAsyncTask(platoDao).execute(plato);
    }

    private static class DeletePlatoAsyncTask extends AsyncTask<PlatoEntity, Void, Void> {
        private PlatoDao platoDao;
        private DeletePlatoAsyncTask(PlatoDao platoDao){
            this.platoDao = platoDao;
        }
        @Override
        protected Void doInBackground(PlatoEntity... platos) {
            platoDao.delete(platos[0]);
            return null;
        }
    }



    public void updateIngredient(IngredientEntity ingredientEntity){
        new UpdateIngredientAsyncTask(ingredientDao).execute(ingredientEntity);
    }

    private static class UpdateIngredientAsyncTask extends AsyncTask<IngredientEntity, Void, Void> {
        private IngredientDao ingredientDao;
        private UpdateIngredientAsyncTask(IngredientDao ingredientDao){
            this.ingredientDao = ingredientDao;
        }
        @Override
        protected Void doInBackground(IngredientEntity... ingredients) {
            ingredientDao.update(ingredients[0]);
            return null;
        }
    }

    public void deleteIngredient(IngredientEntity ingredient){
        new DeleteIngredientAsyncTask(ingredientDao).execute(ingredient);
    }

    private static class DeleteIngredientAsyncTask extends AsyncTask<IngredientEntity, Void, Void> {
        private IngredientDao ingredientDao;
        private DeleteIngredientAsyncTask(IngredientDao ingredientDao){
            this.ingredientDao = ingredientDao;
        }
        @Override
        protected Void doInBackground(IngredientEntity... ingredients) {
            ingredientDao.delete(ingredients[0]);
            return null;
        }
    }

    // CRUD ingredientsbyplato
    public void insertIngredientPlato(PlatoIngredientEntity ingredientsPlato){
        new InsertIngredientPlatoAsyncTask(platoIngredientDao).execute(ingredientsPlato);
    }

    private static class InsertIngredientPlatoAsyncTask extends AsyncTask<PlatoIngredientEntity, Void, Void> {
        private PlatoIngredientDao platoIngredientDao;
        private InsertIngredientPlatoAsyncTask(PlatoIngredientDao platoIngredientDao){
            this.platoIngredientDao = platoIngredientDao;
        }
        @Override
        protected Void doInBackground(PlatoIngredientEntity... ingredientsByPlatoEntities) {
            platoIngredientDao.insert(ingredientsByPlatoEntities[0]);
            return null;
        }
    }
}
