package com.example.mecanos.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mecanos.R;
import com.example.mecanos.databinding.IngredientListFragmentBinding;
import com.example.mecanos.model.IngredientsByPlato;
import com.example.mecanos.ui.adapters.IngredientByPlatoAdapter;
import com.example.mecanos.ui.clickcallback.IngredientByPlatoClickCallback;
import com.example.mecanos.viewmodel.IngredientViewModel;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class IngredientFragment extends Fragment {

    private static final String KEY_INGREDIENT_ID = "ingrediente_id";

    private IngredientListFragmentBinding mBinding;

    private IngredientByPlatoAdapter mIngredientByPlatoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        mBinding = DataBindingUtil.inflate(inflater, R.layout.ingredient_list_fragment, container, false);


        return mBinding.getRoot();
    }

    private final IngredientByPlatoClickCallback mIngredientByPlatoClickCallback = new IngredientByPlatoClickCallback() {
        @Override
        public void onClick(IngredientsByPlato ingredientsByPlato) {

        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void subscribeToModel(final IngredientViewModel model){

    }

    public static IngredientFragment forIngredient(int ingredientId) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_INGREDIENT_ID, ingredientId);
        fragment.setArguments(args);
        return fragment;
    }


}
