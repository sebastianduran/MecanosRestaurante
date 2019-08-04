package com.example.mecanos.db.entity;

import androidx.room.Entity;
import androidx.room.Fts4;

@Entity(tableName = "platosFts")
@Fts4(contentEntity = PlatoEntity.class)
public class PlatoFtsEntity {

    private String name;
    private String description;

    public PlatoFtsEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
