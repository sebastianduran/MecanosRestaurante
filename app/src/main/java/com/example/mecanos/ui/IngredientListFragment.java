package com.example.mecanos.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mecanos.R;
import com.example.mecanos.databinding.IngredientListFragmentBinding;
import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.db.entity.PlatoIngredientEntity;
import com.example.mecanos.model.Ingredient;
import com.example.mecanos.ui.adapters.IngredientAdapter;
import com.example.mecanos.ui.clickcallback.IngredientClickCallback;
import com.example.mecanos.viewmodel.IngredientCreatePlatoViewModel;
import com.example.mecanos.viewmodel.IngredientListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class IngredientListFragment extends Fragment {

    public static final String TAG = "IngredientListFragment";

    private IngredientAdapter mIngredientAdapter;

    private IngredientListFragmentBinding mBinding;

    private EditText editTextCant;

    private int platoId = -1;
    private float usedCant = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.ingredient_list_fragment, container, false );

        mIngredientAdapter = new IngredientAdapter(mIngredientClickCallback);
        mBinding.ingredientsList.setAdapter(mIngredientAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final IngredientListViewModel viewModel =
                ViewModelProviders.of(this).get(IngredientListViewModel.class);

        mBinding.ingredientSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable query = mBinding.ingredientsSearchBox.getText();
                if(query == null || query.toString().isEmpty()){
                    subscribeUi(viewModel.getIngredients());
                }else{
                    subscribeUi(viewModel.searchIngredient("*"+query+"*"));
                }
            }
        });
        mBinding.fbAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    ((IngredientActivity) getActivity()).add();
                }
            }
        });

        subscribeUi(viewModel.getIngredients());
    }

    private void subscribeUi(LiveData<List<IngredientEntity>> liveData){

        liveData.observe(this, new Observer<List<IngredientEntity>>() {
            @Override
            public void onChanged(@Nullable List<IngredientEntity> ingredientEntities) {
                if(ingredientEntities != null){
                    mBinding.setIsLoading(false);
                    mIngredientAdapter.setIngredientList(ingredientEntities);
                }else {
                    mBinding.setIsLoading(true);
                }


                mBinding.executePendingBindings();
            }
        });
    }

    private final IngredientClickCallback mIngredientClickCallback = new IngredientClickCallback() {
        @Override
        public void onClick(Ingredient ingredient) {
            PlatoIngredientEntity platoIngredientEntity = new PlatoIngredientEntity();

            if (platoId == -1){
                Toast.makeText(getActivity(),"Guardar el plato primero", Toast.LENGTH_LONG).show();
            }else{

                IngredientCreatePlatoViewModel ingredientCreatePlatoViewModel = ViewModelProviders.of(getActivity())
                        .get(IngredientCreatePlatoViewModel.class);

                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_units, null);
                editTextCant = view.findViewById(R.id.cantidad_utilizada);

                new AlertDialog.Builder(getContext())
                        .setView(view)
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!TextUtils.isEmpty( editTextCant.getText())){
                                    usedCant = Float.parseFloat(editTextCant.getText().toString().trim());
                                    platoIngredientEntity.setCantidad(usedCant);
                                    platoIngredientEntity.setPlatoId(platoId);
                                    platoIngredientEntity.setIngredientId(ingredient.getId());
                                    Log.d("Datos",
                                            "Cantidad " +String.valueOf(usedCant)+
                                                    " plato id: "+ String.valueOf(platoId)+
                                            "ingredient id: "+String.valueOf(ingredient.getId()));
                                    ingredientCreatePlatoViewModel.insert(platoIngredientEntity);
                                    if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                                        ((EditActivity) getActivity()).refreshPlatoFragment();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }

/*
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((IngredientActivity) getActivity()).show(ingredient);
            }
*/
        }
    };
    public void gotPlatoId (int Id){
        platoId = Id;
    }


}
