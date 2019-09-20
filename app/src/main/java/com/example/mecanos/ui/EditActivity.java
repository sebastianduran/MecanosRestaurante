package com.example.mecanos.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mecanos.R;
import com.example.mecanos.model.Plato;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        if(savedInstanceState == null){
            EditFragment editFragment = new EditFragment();
            //PlatoFragment platoFragment = new PlatoFragment();
            IngredientListFragment ingredientListFragment = new IngredientListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.edit_plato_container, editFragment, EditFragment.TAG)
                    //.add(R.id.ingredient_plato_container, platoFragment, PlatoFragment.TAG)
                    .add(R.id.ingredient_list_container, ingredientListFragment, IngredientListFragment.TAG)
                    .commit();
        }
    }

    public void addplato() {

       /* EditFragment editFragment = new EditFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("plato")
                .replace(R.id.edit_plato_container,
                        editFragment, null).commit();

        PlatoFragment platoFragment = new PlatoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("plato")
                .replace(R.id.ingredient_plato_container,
                        platoFragment, null).commit();

        IngredientListFragment ingredientListFragment = new IngredientListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("plato")
                .replace(R.id.ingredient_list_container,
                        ingredientListFragment, null).commit();*/
    }




}
