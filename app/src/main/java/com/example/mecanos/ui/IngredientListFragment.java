package com.example.mecanos.ui;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mecanos.R;
import com.example.mecanos.databinding.IngredientListFragmentBinding;
import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.model.Ingredient;
import com.example.mecanos.ui.adapters.IngredientAdapter;
import com.example.mecanos.ui.clickcallback.IngredientClickCallback;
import com.example.mecanos.viewmodel.IngredientListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class IngredientListFragment extends Fragment {

    public static final String TAG = "IngredientListFragment";

    private IngredientAdapter mIngredientAdapter;

    private IngredientListFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

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
                if(query == null){
                    subscribeUi(viewModel.getIngredients());
                }else{
                    subscribeUi(viewModel.searchIngredient("*"+query+"*"));
                }
            }
        });
    }

    private void subscribeUi(LiveData<List<IngredientEntity>> liveData){
        liveData.observe(this, new Observer<List<IngredientEntity>>() {
            @Override
            public void onChanged(List<IngredientEntity> ingredientEntities) {
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

            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((IngredientActivity) getActivity()).show(ingredient);
            }

        }
    };
}
