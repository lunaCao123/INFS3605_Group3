package com.example.infs3605group3application;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Crisis {
    @PrimaryKey
    private String crisisCode;
    private String crisisName;

    //TODO link crisisCategory table
    private CrisisCategory crisisCategory;
    private String description;

    public void setCrisisCode(String crisisCode) {
        this.crisisCode = crisisCode;
    }

    public void setCrisisName(String crisisName) {
        this.crisisName = crisisName;
    }

    public void setCrisisCategory(CrisisCategory crisisCategory) {
        this.crisisCategory = crisisCategory;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCrisisCode() {
        return crisisCode;
    }

    public String getCrisisName() {
        return crisisName;
    }

    public CrisisCategory getCrisisCategory() {
        return crisisCategory;
    }

    public String getDescription() {
        return description;
    }
}

