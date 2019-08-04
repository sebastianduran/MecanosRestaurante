package com.example.mecanos.db.entity;

import com.example.mecanos.model.PlatoIngredient;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
/* Entity para la tabla donde se relacionan los platos con los ingredientes que tiene cada uno
    tiene dos llaves externas que son el id de los platos y el id de los ingredientes
 */
@Entity(tableName = "platosingredients",
        foreignKeys = {
                @ForeignKey(entity = PlatoEntity.class,
                        parentColumns = "id",
                        childColumns = "pi_platoId"),
                @ForeignKey(entity = IngredientEntity.class,
                        parentColumns = "id",
                        childColumns = "pi_ingredientId")
        },
        indices = {@Index("pi_platoId"),@Index("pi_ingredientId")})

public class PlatoIngredientEntity implements PlatoIngredient {

    @PrimaryKey
    @NonNull
    public int id;

    @ColumnInfo(name = "pi_platoId")// renombrado de las columnas de esta tabla
    public int platoId;

    @ColumnInfo(name = "pi_ingredientId")
    public int ingredientId;

    @ColumnInfo(name = "cantidad")
    public float cantidad;


    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getPlatoId() {
        return platoId;
    }

    public void setPlatoId(int platoId) {
        this.platoId = platoId;
    }

    @Override
    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public PlatoIngredientEntity() {

    }

}
