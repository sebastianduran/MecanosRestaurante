package com.example.mecanos.db.dao;

import com.example.mecanos.db.entity.PlatoEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PlatoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PlatoEntity plato);

    @Update
    void update(PlatoEntity plato);

    @Query("SELECT * FROM platos")
    LiveData<List<PlatoEntity>> loadAllPlatos();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PlatoEntity> platos);

    @Query("select * from platos where id = :platoId")
    LiveData<PlatoEntity> loadPlato(int platoId);

    @Query("select * from platos where id = :platoId")
    PlatoEntity loadPlatoSync(int platoId);

    @Query("SELECT platos.* FROM platos JOIN platosFts ON (platos.id = platosFts.rowid) "
            + "WHERE platosFts MATCH :query")
    LiveData<List<PlatoEntity>> searchAllPlatos(String query);
}
