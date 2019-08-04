package com.example.mecanos.db.entity;

import com.example.mecanos.model.Plato;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "platos")
public class PlatoEntity implements Plato {

    @PrimaryKey
    @NonNull
    public int id;
    public String name;
    public String description;
    public float price;
    public float costo;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public float getPrice() {
        return price;
    }

    public void setPrice(float price){
        this.price = price;
    }

    @Override
    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public PlatoEntity(){

    }

    @Ignore
    public PlatoEntity(int id, String name, String description, float price, float costo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.costo = costo;
    }

    public PlatoEntity(Plato plato) {
        this.id = plato.getId();
        this.name = plato.getName();
        this.description = plato.getDescription();
        this.price = plato.getPrice();
        this.costo = plato.getCosto();
    }

}
