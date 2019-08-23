package com.example.mecanos.viewmodel;

import android.app.Application;

import com.example.mecanos.BasicApp;
import com.example.mecanos.DataRepository;
import com.example.mecanos.db.entity.PlatoEntity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PlatoCreateViewModel extends AndroidViewModel {
    private final DataRepository mRepository;

    public PlatoCreateViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((BasicApp) application).getRepository();

    }

    public void insert(PlatoEntity plato){
        mRepository.insert(plato);
    }


}
