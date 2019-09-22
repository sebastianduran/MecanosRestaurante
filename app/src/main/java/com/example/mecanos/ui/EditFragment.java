package com.example.mecanos.ui;


import android.content.Context;
import android.content.Intent;
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
import com.example.mecanos.model.Plato;
import com.example.mecanos.ui.adapters.IngredientByPlatoAdapter;
import com.example.mecanos.viewmodel.PlatoCreateViewModel;
import com.example.mecanos.viewmodel.PlatoUpdateViewModel;
import com.example.mecanos.viewmodel.PlatoViewModel;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class EditFragment extends Fragment {

    public static final String TAG = "EditFragment";

    private static final String KEY_PLATO_ID = "plato_id";

    private EditFragmentBinding editFragmentBinding;

    private OnEditFragmentListener mCallback;

    private IngredientByPlatoAdapter mIngredientByPlatoAdapter;

    PlatoUpdateViewModel.Factory factory;
    PlatoUpdateViewModel updateViewModel;
    PlatoCreateViewModel createViewModel;
    PlatoViewModel.Factory platoFactory;
    PlatoViewModel platoViewModel;

    int plato_id;
    String nombre ="";
    String descripcion ="";
    float precio = 0f;
    float costo = 0f;

    boolean add = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        editFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.edit_fragment, container, false);
        return editFragmentBinding.getRoot();
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

        plato_id = getActivity().getIntent().getExtras().getInt(KEY_PLATO_ID);

        if (plato_id != -1){
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


        editFragmentBinding.platoSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(editFragmentBinding.editTextName.toString())){
                    Toast.makeText(getActivity(),"Ingrese el nombre del plato",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(editFragmentBinding.editTextName.toString())){
                    Toast.makeText(getActivity(),"Ingrese una description",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(editFragmentBinding.editTextPreci.getText().toString().trim())){
                    Toast.makeText(getActivity(),"Ingrese un precio",Toast.LENGTH_SHORT).show();
                }
                else{

                    nombre = editFragmentBinding.editTextName.getText().toString();
                    descripcion = editFragmentBinding.categoryDropdown.getText().toString();

                    precio = Float.parseFloat(editFragmentBinding.editTextPreci.getText().toString().trim());
                    costo = Float.parseFloat(editFragmentBinding.editTextCost.getText().toString().trim());

                    plato.setName(nombre);
                    plato.setDescription(descripcion);
                    plato.setPrice(precio);
                    plato.setCosto(costo);

                    if (add){
                        createViewModel.insert(plato);
                        createViewModel.getLastPlatoLive().observe(EditFragment.this, plato -> {
                            int lastInsertedRowId = plato.getId();
                            mCallback.messageFromEditFragment(lastInsertedRowId);
                        });
                        Toast.makeText(getActivity(), "Guardado", Toast.LENGTH_SHORT).show();
                    }else{
                        plato.setId(plato_id);
                        updateViewModel.update(plato);
                    }

                }

            }
        });

        editFragmentBinding.platoFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlatoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void subscribeToModel(final PlatoUpdateViewModel model) {
        // Observe product data
        model.getObservablePlato().observe(this, new Observer<PlatoEntity>() {
            @Override
            public void onChanged(@Nullable PlatoEntity platoEntity) {
                model.setPlato(platoEntity);
                editFragmentBinding.setPlato(platoEntity);
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
    //comunicacion con el otro fragment
    public interface OnEditFragmentListener {
        void messageFromEditFragment (int platoId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnEditFragmentListener){
            mCallback = (OnEditFragmentListener) context;
        }else {
            throw new RuntimeException(context.toString()
                    + " se debe implementar OnEditFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }
}
