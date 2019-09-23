package com.example.mecanos.ui;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mecanos.R;
import com.example.mecanos.databinding.EditFragmentBinding;
import com.example.mecanos.databinding.IngredientListFragmentBinding;
import com.example.mecanos.databinding.PlatoFragmentBinding;
import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.db.entity.IngredientsByPlatoEntity;
import com.example.mecanos.db.entity.PlatoEntity;
import com.example.mecanos.model.Ingredient;
import com.example.mecanos.model.IngredientsByPlato;
import com.example.mecanos.model.PlatoIngredient;
import com.example.mecanos.ui.adapters.IngredientAdapter;
import com.example.mecanos.ui.adapters.IngredientByPlatoAdapter;
import com.example.mecanos.ui.clickcallback.IngredientByPlatoClickCallback;
import com.example.mecanos.ui.clickcallback.IngredientClickCallback;
import com.example.mecanos.viewmodel.IngredientViewModel;
import com.example.mecanos.viewmodel.PlatoViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class PlatoFragment extends Fragment {

    public static final String TAG = "PlatoFragment";

    private static final String KEY_PLATO_ID = "plato_id";

    private PlatoFragmentBinding mBinding;

    private IngredientByPlatoAdapter mIngredientByPlatoAdapter;

    PlatoViewModel platoViewModel;

    int plato_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        mBinding = DataBindingUtil.inflate(inflater, R.layout.plato_fragment, container, false);


        // Create and set the adapter for the RecyclerView.
        mIngredientByPlatoAdapter = new IngredientByPlatoAdapter(mIngredientClickCallback);
        mBinding.ingredientbyplatoList.setAdapter(mIngredientByPlatoAdapter);

        return mBinding.getRoot();
    }

    private final IngredientClickCallback mIngredientClickCallback = new IngredientClickCallback() {
        @Override
        public void onClick(Ingredient ingredient) {

        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        plato_id = getActivity().getIntent().getExtras().getInt(KEY_PLATO_ID);

        Log.d(KEY_PLATO_ID, String.valueOf(plato_id));

        if (plato_id == -1){
            mBinding.setIsLoading(true);
        }else {

            platoViewModel = ViewModelProviders.of(this).get(PlatoViewModel.class);
            subscribeUi(platoViewModel.getIngredientsbyPlato(plato_id));
        }

    }

    private void subscribeUi(LiveData<List<IngredientEntity>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, new Observer<List<IngredientEntity>>() {
            @Override
            public void onChanged(@Nullable List<IngredientEntity> ingredientsByPlato) {
                if (ingredientsByPlato != null) {
                    mBinding.setIsLoading(false);
                    mIngredientByPlatoAdapter.setIngredientByPlatoList(ingredientsByPlato);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings();
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
