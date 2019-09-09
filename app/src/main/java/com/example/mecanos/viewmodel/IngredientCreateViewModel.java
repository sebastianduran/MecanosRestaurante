package com.example.mecanos.viewmodel;

import android.app.Application;

import com.example.mecanos.BasicApp;
import com.example.mecanos.DataRepository;
import com.example.mecanos.db.entity.IngredientEntity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class IngredientCreateViewModel extends AndroidViewModel {
    private final DataRepository mRepository;

    public IngredientCreateViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((BasicApp) application).getRepository();

    }

    public void insert(IngredientEntity ingredientEntity){
        mRepository.insertIngredient(ingredientEntity);
    }
}
