package com.example.mecanos.db.dao;

import com.example.mecanos.db.converter.DateConverter;
import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.db.entity.IngredientsByPlatoEntity;
import com.example.mecanos.db.entity.PlatoIngredientEntity;
import com.example.mecanos.model.Ingredient;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

@Dao
@TypeConverters(DateConverter.class)
public interface PlatoIngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PlatoIngredientEntity> platoIngredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PlatoIngredientEntity platoIngredient);

    @Query("SELECT ingredients.* " +
            "FROM platosingredients " +
            "INNER JOIN ingredients  ON ingredients.id = platosingredients.pi_ingredientId " +
            "INNER JOIN platos ON platos.id = platosingredients.pi_platoId " +
            "where pi_platoId = :platoId")
    LiveData<List<IngredientEntity>> loadPlatoIngredients(int platoId);

    @Query("SELECT * FROM platosingredients where pi_platoId = :platoId")
    int[] loadPlatoIngredientsIds(int platoId);


}
