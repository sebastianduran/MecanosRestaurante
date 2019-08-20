package com.example.mecanos.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mecanos.R;
import com.example.mecanos.model.Plato;

public class EditActivity extends AppCompatActivity {

    public static final String TAG ="EditAotivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        if(savedInstanceState == null){
            EditFragment fragment = new EditFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_ed, fragment, EditFragment.TAG).commit();
        }
    }

    public void editShow(Plato plato){

    }
}
