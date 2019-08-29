package com.example.mecanos.db.entity;

import androidx.room.Entity;
import androidx.room.Fts4;

@Entity(tableName = "ingredientFts")
@Fts4(contentEntity = IngredientEntity.class)
public class IngredientFtsEntity {

    private String nombre;

    public IngredientFtsEntity(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

}
