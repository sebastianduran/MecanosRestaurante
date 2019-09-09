package com.example.mecanos.viewmodel;

import android.app.Application;

import com.example.mecanos.BasicApp;
import com.example.mecanos.DataRepository;
import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.model.Ingredient;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class IngredientViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    private final LiveData<IngredientEntity> mObservableIngredient;

    public ObservableField<IngredientEntity> ingredient = new ObservableField<>();

    private final int mIngredientId;


    public IngredientViewModel(@NonNull Application application, DataRepository repository,
                               final int ingredientId) {
        super(application);

        mIngredientId = ingredientId;

        mObservableIngredient = repository.loadIngredient(mIngredientId);

        mRepository = ((BasicApp) application).getRepository();
    }


    public LiveData<IngredientEntity> getObservableIngredient (){
        return mObservableIngredient;
    }

    public void setIngredient(IngredientEntity ingredient){
        this.ingredient.set(ingredient);
    }

    public void insert(IngredientEntity ingredient){
        mRepository.insertIngredient(ingredient);
    }

    public void update(IngredientEntity ingredient){
        mRepository.updateIngredient(ingredient);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mIngredientId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int ingredientId) {
            mApplication = application;
            mIngredientId = ingredientId;
            mRepository = ((BasicApp) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PlatoViewModel(mApplication, mRepository, mIngredientId);
        }
    }
}
