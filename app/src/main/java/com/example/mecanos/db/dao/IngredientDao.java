package com.example.mecanos.db.dao;

import com.example.mecanos.db.entity.IngredientEntity;
import com.example.mecanos.db.entity.PlatoEntity;
import com.example.mecanos.model.Ingredient;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(IngredientEntity ingredient);

    @Update
    void update(IngredientEntity ingredient);

    @Delete
    void delete(IngredientEntity ingredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<IngredientEntity> ingredients);

    @Query("select * from ingredients where id = :ingredientId")
    LiveData<IngredientEntity> loadIngredient(int ingredientId);

    @Query("select * from ingredients")
    LiveData<List<IngredientEntity>> loadAllIngredients();

    @Query("SELECT * FROM ingredients WHERE id IN(:ingredientIds)")
    LiveData<List<IngredientEntity>> loadIngredientsIdByPlato(int[] ingredientIds);

    @Query("SELECT ingredients.* FROM ingredients JOIN ingredientFts ON (ingredients.id = ingredientFts.rowid) "
            + "WHERE ingredientFts MATCH :query")
    LiveData<List<IngredientEntity>> searchAllIngredients(String query);
}
