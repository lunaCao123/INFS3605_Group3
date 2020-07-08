package com.example.grou3v2.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CrisisCategory {
    @PrimaryKey
    @ColumnInfo(name="crisisCategory")
    private String crisisCategory;
    private String description;

    public void setCrisisCategory(String crisisCategory) {
        this.crisisCategory = crisisCategory;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCrisisCategory() {
        return crisisCategory;
    }

    public String getDescription() {
        return description;
    }
}

