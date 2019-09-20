package com.example.mecanos.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mecanos.R;
import com.example.mecanos.model.Plato;

public class PlatoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plato);

        if (savedInstanceState == null) {
            PlatoListFragment fragment = new PlatoListFragment();


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, PlatoListFragment.TAG).commit();
        }
    }

    /** Shows the product detail fragment */
    public void show(Plato plato) {

        PlatoFragment platoFragment = PlatoFragment.forPlato(plato.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("plato")
                .replace(R.id.edit_plato_container,
                        platoFragment, null).commit();
    }

    public void edit(Plato plato){
        EditFragment editFragment = EditFragment.forPlato(plato.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("plato")
                .replace(R.id.edit_plato_container,
                        editFragment, null).commit();
    }

    public void add(){
        EditFragment editFragment = new EditFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("plato")
                .replace(R.id.edit_plato_container,
                        editFragment, null).commit();
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(PlatoActivity.this, MainActivity.class);
        startActivity(setIntent);
        finish();
    }
}
