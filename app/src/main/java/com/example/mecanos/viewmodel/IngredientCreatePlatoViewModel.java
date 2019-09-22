package com.example.mecanos.viewmodel;

import android.app.Application;

import com.example.mecanos.BasicApp;
import com.example.mecanos.DataRepository;
import com.example.mecanos.db.entity.IngredientsByPlatoEntity;
import com.example.mecanos.db.entity.PlatoEntity;
import com.example.mecanos.db.entity.PlatoIngredientEntity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class IngredientCreatePlatoViewModel extends AndroidViewModel {
    private final DataRepository mRepository;



    public IngredientCreatePlatoViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((BasicApp) application).getRepository();

    }

    public void insert(PlatoIngredientEntity platoIngredientEntity){
        mRepository.insertIngredientPlato(platoIngredientEntity);
    }


}
