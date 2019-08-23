package com.example.mecanos.viewmodel;

import android.app.Application;

import com.example.mecanos.BasicApp;
import com.example.mecanos.DataRepository;
import com.example.mecanos.db.entity.PlatoEntity;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class PlatoListViewModel extends AndroidViewModel {
    private final DataRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<PlatoEntity>> mObservablePlatos;

    public PlatoListViewModel(Application application) {
        super(application);

        mObservablePlatos = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservablePlatos.setValue(null);

        mRepository = ((BasicApp) application).getRepository();
        LiveData<List<PlatoEntity>> platos = mRepository.getPlatos();

        // observe the changes of the products from the database and forward them
        mObservablePlatos.addSource(platos, mObservablePlatos::setValue);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<PlatoEntity>> getPlatos() {
        return mObservablePlatos;
    }

    public LiveData<List<PlatoEntity>> searchPlatos(String query) {
        return mRepository.searchPlatos(query);
    }


    //CRUD
    public void insert(PlatoEntity plato){
        mRepository.insert(plato);
    }
    public void update(PlatoEntity plato){
        mRepository.update(plato);
    }
    public void delete(PlatoEntity plato){
        mRepository.delete(plato);
    }
}
