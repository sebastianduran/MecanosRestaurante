package com.example.mecanos.viewmodel;

import android.app.Application;

import androidx.databinding.ObservableField;

import com.example.mecanos.BasicApp;
import com.example.mecanos.DataRepository;
import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.db.entity.IngredientsByPlatoEntity;
import com.example.mecanos.db.entity.PlatoEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PlatoViewModel extends AndroidViewModel {
    private final DataRepository mRepository;

    public ObservableField<PlatoEntity> plato = new ObservableField<>();


    private MediatorLiveData<List<IngredientEntity>> mObservableIngreditentsByPlato;

    public PlatoViewModel(Application application) {
        super(application);


        mObservableIngreditentsByPlato = new MediatorLiveData<>();

        mObservableIngreditentsByPlato.setValue(null);

        mRepository = ((BasicApp) application).getRepository();
        LiveData<List<IngredientEntity>> ingredients = mRepository.getIngredients();


        mObservableIngreditentsByPlato.addSource(ingredients, mObservableIngreditentsByPlato::setValue);
    }

    public LiveData<List<IngredientEntity>> getIngredients(){
        return mObservableIngreditentsByPlato;
    }

    /**
     * Expose the LiveData Comments query so the UI can observe it.
     */

    public LiveData<List<IngredientEntity>> getIngredientsbyPlato(int mPlatoId) {
        return mRepository.loadPlatoIngredients(mPlatoId);
    }


}
