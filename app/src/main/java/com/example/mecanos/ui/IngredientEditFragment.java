package com.example.mecanos.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mecanos.R;
import com.example.mecanos.databinding.IngredientEditFragmentBinding;
import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.model.Ingredient;
import com.example.mecanos.viewmodel.IngredientCreateViewModel;
import com.example.mecanos.viewmodel.IngredientViewModel;
import com.example.mecanos.viewmodel.PlatoCreateViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class IngredientEditFragment extends Fragment {

    private static final String KEY_INGREDIENT_ID = "ingredient_id";
    IngredientViewModel.Factory factory;
    IngredientViewModel updateViewModel;
    IngredientCreateViewModel createViewModel;

    String nombre ="";
    String proveedor = "";
    float existencias = 0f;
    String unidades = "";

    private IngredientEditFragmentBinding mBinding;

    int ingredient_id;
    boolean add = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.ingredient_edit_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        IngredientEntity ingredient = new IngredientEntity();

        if (getArguments() != null){
            factory = new IngredientViewModel.Factory(
                    getActivity().getApplication(), getArguments().getInt(KEY_INGREDIENT_ID)
            );
            updateViewModel = ViewModelProviders.of(this, factory)
                    .get(IngredientViewModel.class);
            subscribeToModel(updateViewModel);
            ingredient_id = getArguments().getInt(KEY_INGREDIENT_ID);
            add = false;
        }
        else {
            createViewModel = ViewModelProviders.of(getActivity())
                    .get(IngredientCreateViewModel.class);
            add = true;
        }

        mBinding.ingredientSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(mBinding.editTextName.toString())){
                    Toast.makeText(getActivity(),"Ingrese el nombre",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(mBinding.editTextExistencias.toString())){
                    Toast.makeText(getActivity(),"Ingrese el existencias",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(mBinding.editTextUnits.toString())){
                    Toast.makeText(getActivity(),"Ingrese el unidades",Toast.LENGTH_SHORT).show();
                }
                else {
                    nombre = mBinding.editTextName.getText().toString();
                    proveedor = mBinding.editTextProveedor.getText().toString();
                    existencias = Float.parseFloat(mBinding.editTextExistencias.getText().toString().trim());
                    unidades = mBinding.editTextUnits.getText().toString().trim();

                    ingredient.setNombre(nombre);
                    ingredient.setProveedor(proveedor);
                    ingredient.setExistencias(existencias);
                    ingredient.setUnidades(unidades);

                    if (add){
                        createViewModel.insert(ingredient);
                    }else{
                        ingredient.setId(ingredient_id);
                        updateViewModel.update(ingredient);
                    }

                    IngredientListFragment fragment = new IngredientListFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .addToBackStack("ingredient")
                            .replace(R.id.ingredient_fragment_container,
                                    fragment, null).commit();

                }

            }
        });
                

    }

    private void subscribeToModel(final IngredientViewModel model) {
        // Observe product data
        model.getObservableIngredient().observe(this, new Observer<IngredientEntity>() {
            @Override
            public void onChanged(@Nullable IngredientEntity ingredientEntity) {
                model.setIngredient(ingredientEntity);
                mBinding.setIngredient(ingredientEntity);
            }
        });
    }

    /** Creates product fragment for specific product ID */
    public static IngredientEditFragment forIngredient(int ingredientId) {
        IngredientEditFragment fragment = new IngredientEditFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_INGREDIENT_ID, ingredientId);
        fragment.setArguments(args);
        return fragment;
    }
}
