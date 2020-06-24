package com.example.infs3605group3application;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CrisisCategory {
    @PrimaryKey
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
