package com.example.mecanos.viewmodel;

import android.app.Application;

import androidx.databinding.ObservableField;

import com.example.mecanos.BasicApp;
import com.example.mecanos.DataRepository;
import com.example.mecanos.db.entity.IngredientsByPlatoEntity;
import com.example.mecanos.db.entity.PlatoEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PlatoViewModel extends AndroidViewModel {

    private final LiveData<PlatoEntity> mObservablePlato;

    public ObservableField<PlatoEntity> plato = new ObservableField<>();

    private final int mPlatoId;


    private LiveData<List<IngredientsByPlatoEntity>> mObservableIngreditentsByPlato;

    public PlatoViewModel(@NonNull Application application, DataRepository repository,
                          final int platoId) {
        super(application);
        mPlatoId = platoId;

        mObservablePlato = repository.loadPlato(mPlatoId);
        mObservableIngreditentsByPlato = repository.loadPlatoIngredients(mPlatoId);
    }

    /**
     * Expose the LiveData Comments query so the UI can observe it.
     */

    public LiveData<List<IngredientsByPlatoEntity>> getIngredientsbyPlato() {
        return mObservableIngreditentsByPlato;
    }

    public LiveData<PlatoEntity> getObservablePlato() {
        return mObservablePlato;
    }

    public void setPlato(PlatoEntity plato) {
        this.plato.set(plato);
    }


    /**
     * A creator is used to inject the product ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mPlatoId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int productId) {
            mApplication = application;
            mPlatoId = productId;
            mRepository = ((BasicApp) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PlatoViewModel(mApplication, mRepository, mPlatoId);
        }
    }

}
