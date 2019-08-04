package com.example.mecanos.model;

import java.util.Date;

public interface Ingredient {
    int getId();
    String getNombre();
    String getProveedor();
    String getUnidades();
    float getExistencias();
    Date getDiaCompra();
}
