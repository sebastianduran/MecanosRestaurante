package com.example.mecanos.db.entity;

import com.example.mecanos.db.converter.DateConverter;
import com.example.mecanos.model.IngredientsByPlato;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.TypeConverters;

public class IngredientsByPlatoEntity implements IngredientsByPlato {

    public int id;

    @ColumnInfo(name="dish")
    public String platoNombre;

    @ColumnInfo(name="product")
    public String ingredientNombre;

    @ColumnInfo(name="cant")
    public float cantidadIngredient;

    @TypeConverters(DateConverter.class)
    public Date compra;


    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Date getCompra() {
        return compra;
    }

    public void setCompra(Date compra) {
        this.compra = compra;
    }

    @Override
    public float getCantidadIngredient() {
        return cantidadIngredient;
    }

    public void setCantidadIngredient(float cantidadIngredient) {
        this.cantidadIngredient = cantidadIngredient;
    }

    @Override
    public String getIngredientNombre() {
        return ingredientNombre;
    }

    public void setIngredientNombre(String ingredientNombre) {
        this.ingredientNombre = ingredientNombre;
    }

    @Override
    public String getPlatoNombre() {
        return platoNombre;
    }

    public void setPlatoNombre(String platoNombre) {
        this.platoNombre = platoNombre;
    }
}
