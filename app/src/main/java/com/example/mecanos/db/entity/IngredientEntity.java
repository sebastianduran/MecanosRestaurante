package com.example.mecanos.db.entity;

import com.example.mecanos.db.converter.DateConverter;
import com.example.mecanos.model.Ingredient;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "ingredients")
@TypeConverters(DateConverter.class)
public class IngredientEntity implements Ingredient {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    public String nombre;
    public String proveedor;
    public String unidades;
    public float existencias;
    private Date diaCompra;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    @Override
    public float getExistencias() {
        return 0;
    }

    public void setExistencias(float existencias) {
        this.existencias = existencias;
    }

    @Override
    public Date getDiaCompra() {
        return diaCompra;
    }

    public void setDiaCompra(Date diaCompra) {
        this.diaCompra = diaCompra;
    }

    public IngredientEntity(){

    }

    public IngredientEntity(Ingredient ingredient){
        this.id = ingredient.getId();
        this.nombre = ingredient.getNombre();
        this.proveedor = ingredient.getProveedor();
        this.unidades = ingredient.getUnidades();
        this.existencias = ingredient.getExistencias();
        this.diaCompra = ingredient.getDiaCompra();
    }

}
