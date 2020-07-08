package com.example.grou3v2.Model;

public class Crisis {
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