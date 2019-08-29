package com.example.mecanos.viewmodel;

import android.app.Application;

import com.example.mecanos.BasicApp;
import com.example.mecanos.DataRepository;
import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.model.Ingredient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class IngredientListViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    private final MediatorLiveData<List<IngredientEntity>> mObservableIngredients;

    public IngredientListViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((BasicApp) application).getRepository();

        mObservableIngredients = new MediatorLiveData<>();
        mObservableIngredients.setValue(null);

        /* Asociaciones */
        LiveData<List<IngredientEntity>> ingredients = mRepository.getIngredients();
    }

    public LiveData<List<IngredientEntity>> getIngredients(){
        return mObservableIngredients;
    }

    public LiveData<List<IngredientEntity>> searchIngredient(String query){
        return mRepository.searchIngredients(query);
    }


}
