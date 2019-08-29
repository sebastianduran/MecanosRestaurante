package com.example.mecanos.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.mecanos.R;
import com.example.mecanos.databinding.EditFragmentBinding;
import com.example.mecanos.db.entity.PlatoEntity;
import com.example.mecanos.viewmodel.PlatoCreateViewModel;
import com.example.mecanos.viewmodel.PlatoUpdateViewModel;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class EditFragment extends Fragment {

    public static final String TAG = "EditFragment";

    private static final String KEY_PLATO_ID = "plato_id";

    PlatoUpdateViewModel.Factory factory;
    PlatoUpdateViewModel updateViewModel;
    PlatoCreateViewModel createViewModel;

    private EditFragmentBinding mBinding;
    int plato_id;
    String nombre ="";
    String descripcion ="";
    float precio = 0f;
    float costo = 0f;

    boolean add = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.edit_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PlatoEntity plato = new PlatoEntity();

        String[] CATEGORIES = new String[] {"Sandwich", "Empanada", "Bebidas", "Otros"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getContext(),
                        R.layout.dropdown_menu_popup_item,
                        CATEGORIES);

        AutoCompleteTextView editTextFilledExposedDropdown =
                getView().findViewById(R.id.category_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);

        if (getArguments()!= null){
            factory = new PlatoUpdateViewModel.Factory(
                    getActivity().getApplication(), getArguments().getInt(KEY_PLATO_ID));
            updateViewModel = ViewModelProviders.of(this, factory )
                    .get(PlatoUpdateViewModel.class);
            subscribeToModel(updateViewModel);
            plato_id = getArguments().getInt(KEY_PLATO_ID);
            add = false;
        }else {
            createViewModel = ViewModelProviders.of(getActivity())
                .get(PlatoCreateViewModel.class);
            add = true;
        }


        mBinding.platoSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(mBinding.editTextName.toString())){
                    Toast.makeText(getActivity(),"Ingrese el nombre del plato",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(mBinding.editTextName.toString())){
                    Toast.makeText(getActivity(),"Ingrese una description",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(mBinding.editTextPreci.getText().toString().trim())){
                    Toast.makeText(getActivity(),"Ingrese un precio",Toast.LENGTH_SHORT).show();
                }
                else{



                    nombre = mBinding.editTextName.getText().toString();
                    descripcion = mBinding.categoryDropdown.getText().toString();

                    precio = Float.parseFloat(mBinding.editTextPreci.getText().toString().trim());
                    costo = Float.parseFloat(mBinding.editTextCost.getText().toString().trim());

                    plato.setName(nombre);
                    plato.setDescription(descripcion);
                    plato.setPrice(precio);
                    plato.setCosto(costo);
                    

                    if (add){
                        createViewModel.insert(plato);
                    }else{
                        plato.setId(plato_id);
                        updateViewModel.update(plato);
                    }

                    PlatoListFragment fragment = new PlatoListFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .addToBackStack("plato")
                            .replace(R.id.fragment_container,
                                    fragment, null).commit();

                }

            }
        });
        
    }

    private void subscribeToModel(final PlatoUpdateViewModel model) {
        // Observe product data
        model.getObservablePlato().observe(this, new Observer<PlatoEntity>() {
            @Override
            public void onChanged(@Nullable PlatoEntity platoEntity) {
                model.setPlato(platoEntity);
                mBinding.setPlato(platoEntity);
            }
        });
    }

    /** Creates product fragment for specific product ID */
    public static EditFragment forPlato(int platoId) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PLATO_ID, platoId);
        fragment.setArguments(args);
        return fragment;
    }


}
