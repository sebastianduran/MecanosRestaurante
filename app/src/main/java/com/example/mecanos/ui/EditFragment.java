package com.example.mecanos.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mecanos.R;
import com.example.mecanos.databinding.EditFragmentBinding;
import com.example.mecanos.db.entity.PlatoEntity;
import com.example.mecanos.viewmodel.PlatoCreateViewModel;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class EditFragment extends Fragment {

    public static final String TAG = "EditFragment";

    private static final String KEY_PLATO_ID = "plato_id";

    private EditFragmentBinding mBinding;
    String nombre ="";
    String descripcion ="";
    float precio = 0f;
    float costo = 0f;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.edit_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final PlatoCreateViewModel viewModel =
                ViewModelProviders.of(this).get(PlatoCreateViewModel.class);



        mBinding.platoSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PlatoEntity plato = new PlatoEntity();

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
                    descripcion = mBinding.editTextDescription.getText().toString();

                    precio = Float.parseFloat(mBinding.editTextPreci.getText().toString().trim());
                    costo = Float.parseFloat(mBinding.editTextCost.getText().toString().trim());

                    plato.setName(nombre);
                    plato.setDescription(descripcion);
                    plato.setPrice(precio);
                    plato.setCosto(costo);
                    viewModel.insert(plato);
                }

            }
        });
        
    }


    public static EditFragment editPlato(int platoId){
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PLATO_ID, platoId);
        fragment.setArguments(args);
        return fragment;
    }
}
