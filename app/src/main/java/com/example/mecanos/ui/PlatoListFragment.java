package com.example.mecanos.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mecanos.R;
import com.example.mecanos.databinding.ListFragmentBinding;
import com.example.mecanos.db.entity.PlatoEntity;
import com.example.mecanos.model.Plato;
import com.example.mecanos.ui.adapters.PlatoAdapter;
import com.example.mecanos.ui.clickcallback.PlatoClickCallback;
import com.example.mecanos.ui.clickcallback.PlatoLongClickCallback;
import com.example.mecanos.viewmodel.PlatoListViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class PlatoListFragment extends Fragment {

    public static final String TAG = "PlatoListFragment";
    public static final String PLATO_KEY_ID = "plato_id";

    private PlatoAdapter mPlatoAdapter;

    private ListFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);

        mPlatoAdapter = new PlatoAdapter(mPlatoClickCallback, mPlatoLongClickCallback);
        mBinding.platosList.setAdapter(mPlatoAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final PlatoListViewModel viewModel =
                ViewModelProviders.of(this).get(PlatoListViewModel.class);

        mBinding.platosSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable query = mBinding.platosSearchBox.getText();
                if (query == null || query.toString().isEmpty()) {
                    subscribeUi(viewModel.getPlatos());
                } else {
                    subscribeUi(viewModel.searchPlatos("*" + query + "*"));
                }
            }
        });
        mBinding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtra(PLATO_KEY_ID, -1);
                startActivity(intent);
                /*if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    ((EditActivity) getActivity()).addplato(); esto no se puede hacer por que esta dentro de platoactivity
                }*/
            }
        });

        subscribeUi(viewModel.getPlatos());
    }

    private void subscribeUi(LiveData<List<PlatoEntity>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, new Observer<List<PlatoEntity>>() {
            @Override
            public void onChanged(@Nullable List<PlatoEntity> misPlatos) {
                if (misPlatos != null) {
                    mBinding.setIsLoading(false);
                    mPlatoAdapter.setPlatoList(misPlatos);
                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings();
            }
        });
    }

    private final PlatoClickCallback mPlatoClickCallback = new PlatoClickCallback() {
        @Override
        public void onClick(Plato plato) {

            Intent intent = new Intent(getActivity(), EditActivity.class);
            intent.putExtra(PLATO_KEY_ID, plato.getId());
            startActivity(intent);
            /*if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((PlatoActivity) getActivity()).show(plato);
            }*/
        }
    };

    private final PlatoLongClickCallback mPlatoLongClickCallback = new PlatoLongClickCallback() {
        @Override
        public boolean onLongClick(Plato plato) {
            final PlatoListViewModel viewModel =
                ViewModelProviders.of(getActivity()).get(PlatoListViewModel.class);
            PlatoEntity entity = new PlatoEntity(plato);

            new AlertDialog.Builder(getContext())
                    .setTitle(plato.getName())
                    .setMessage("Escoje la accion para el plato:  " + plato.getName())
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            viewModel.delete(entity);
                            Toast.makeText(getActivity(), "Elimnado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                                ((PlatoActivity) getActivity()).edit(plato);
                            }


                        }
                    })
                    .show();
            return true;
        }
    };
}
