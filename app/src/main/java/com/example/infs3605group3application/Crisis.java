package com.example.infs3605group3application;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = CrisisCategory.class,
        parentColumns = "crisisCategory",
        childColumns = "crisisCategory",
        onDelete = ForeignKey.NO_ACTION))
public class Crisis {
    @PrimaryKey
    @ColumnInfo(name="crisisCode")
    private String crisisCode;
    private String crisisName;
    private String state;
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

    public void setState (String state) {
        this.state = state;
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

    public String getState() {
        return state;
    }
}

