package com.example.mecanos.ui;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mecanos.R;
import com.example.mecanos.databinding.EditFragmentBinding;
import com.example.mecanos.databinding.IngredientListFragmentBinding;
import com.example.mecanos.databinding.PlatoFragmentBinding;
import com.example.mecanos.db.entity.IngredientsByPlatoEntity;
import com.example.mecanos.db.entity.PlatoEntity;
import com.example.mecanos.model.Ingredient;
import com.example.mecanos.model.IngredientsByPlato;
import com.example.mecanos.ui.adapters.IngredientAdapter;
import com.example.mecanos.ui.adapters.IngredientByPlatoAdapter;
import com.example.mecanos.ui.clickcallback.IngredientByPlatoClickCallback;
import com.example.mecanos.ui.clickcallback.IngredientClickCallback;
import com.example.mecanos.viewmodel.PlatoViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class PlatoFragment extends Fragment {

    public static final String TAG = "PlatoFragment";

    private static final String KEY_PLATO_ID = "plato_id";

    private PlatoFragmentBinding mBinding;

    private IngredientByPlatoAdapter mIngredientByPlatoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        mBinding = DataBindingUtil.inflate(inflater, R.layout.plato_fragment, container, false);


        // Create and set the adapter for the RecyclerView.
        mIngredientByPlatoAdapter = new IngredientByPlatoAdapter(mIngredientByPlatoClickCallback);
        mBinding.ingredientbyplatoList.setAdapter(mIngredientByPlatoAdapter);

        return mBinding.getRoot();
    }

    private final IngredientByPlatoClickCallback mIngredientByPlatoClickCallback = new IngredientByPlatoClickCallback() {
        @Override
        public void onClick(IngredientsByPlato ingredientsByPlato) {

        }
    };

    private final IngredientClickCallback mIngredientClickCallback = new IngredientClickCallback() {
        @Override
        public void onClick(Ingredient ingredient) {

        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PlatoViewModel.Factory factory = new PlatoViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_PLATO_ID));

        final PlatoViewModel model = ViewModelProviders.of(this, factory)
                .get(PlatoViewModel.class);

        mBinding.setPlatoViewModel(model);

        subscribeToModel(model);
    }

    private void subscribeToModel(final PlatoViewModel model) {

        // Observe comments
        model.getIngredientsbyPlato().observe(this, new Observer<List<IngredientsByPlatoEntity>>() {
            @Override
            public void onChanged(@Nullable List<IngredientsByPlatoEntity> ingredientsByPlatoEntities) {
                if (ingredientsByPlatoEntities != null) {
                    mBinding.setIsLoading(false);
                    mIngredientByPlatoAdapter.setIngredientByPlatoList(ingredientsByPlatoEntities);

                } else {
                    mBinding.setIsLoading(true);
                }
            }
        });
    }

    /** Creates product fragment for specific product ID */
    public static PlatoFragment forPlato(int platoId) {
        PlatoFragment fragment = new PlatoFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PLATO_ID, platoId);
        fragment.setArguments(args);
        return fragment;
    }
}
