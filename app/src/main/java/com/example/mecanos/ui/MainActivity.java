package com.example.mecanos.ui;

import android.os.Bundle;

import com.example.mecanos.R;
import com.example.mecanos.model.Plato;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Add product list fragment if this is first creation
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
                .replace(R.id.fragment_container,
                        platoFragment, null).commit();
    }
}
