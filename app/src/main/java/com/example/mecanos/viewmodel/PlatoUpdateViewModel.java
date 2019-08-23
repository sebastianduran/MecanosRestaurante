package com.example.mecanos.viewmodel;

import android.app.Application;

import com.example.mecanos.BasicApp;
import com.example.mecanos.DataRepository;
import com.example.mecanos.db.entity.PlatoEntity;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PlatoUpdateViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    private final LiveData<PlatoEntity> mObservablePlato;

    public ObservableField<PlatoEntity> plato = new ObservableField<>();

    private final int mPlatoId;

    public PlatoUpdateViewModel(@NonNull Application application,
                                DataRepository repository,
                                final int platoId) {
        super(application);

        mRepository = ((BasicApp) application).getRepository();

        mPlatoId = platoId;

        mObservablePlato = repository.loadPlato(mPlatoId);


    }

    public void setPlato(PlatoEntity plato) {
        this.plato.set(plato);
    }

    public LiveData<PlatoEntity> getObservablePlato() {
        return mObservablePlato;
    }

    public void update(PlatoEntity plato){
        mRepository.insert(plato);
    }


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
            return (T) new PlatoUpdateViewModel(mApplication, mRepository, mPlatoId);
        }
    }
}