package com.example.mecanos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mecanos.R;
import com.example.mecanos.databinding.ActivityMainBinding;
import com.example.mecanos.model.Plato;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AppCompatImageButton platosBtn = binding.platosButton;
        AppCompatImageButton ingredientsBtn = binding.ingredientsButton;

        platosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlatoActivity.class);
                startActivity(intent);

            }
        });
        ingredientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IngredientActivity.class);
                startActivity(intent);

            }
        });


    }


}
