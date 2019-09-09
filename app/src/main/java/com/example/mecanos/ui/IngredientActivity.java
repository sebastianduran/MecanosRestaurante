package com.example.mecanos.ui;

import android.os.Bundle;

import com.example.mecanos.model.Ingredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.mecanos.R;

public class IngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        if (savedInstanceState == null) {
            IngredientListFragment fragment = new IngredientListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredient_fragment_container, fragment, IngredientListFragment.TAG).commit();
        }

    }
/*
    public void show(Ingredient ingredient) {

        IngredientListFragment ingredientFragment = IngredientListFragment.forIngredient(ingredient.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("ingredient")
                .replace(R.id.ingredient_fragment_container,
                        ingredientFragment, null).commit();
    }*/

    public void edit(Ingredient ingredient){
        IngredientEditFragment editFragment = IngredientEditFragment.forIngredient(ingredient.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("ingredient")
                .replace(R.id.ingredient_fragment_container,
                        editFragment, null).commit();
    }

    public void add() {
        IngredientEditFragment editFragment = new IngredientEditFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("ingredient")
                .replace(R.id.ingredient_fragment_container,
                        editFragment, null).commit();
    }
}
