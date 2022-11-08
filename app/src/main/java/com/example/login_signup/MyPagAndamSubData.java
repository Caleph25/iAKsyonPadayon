package com.example.login_signup;

public class MyPagAndamSubData {
    private Integer categoryID;
    private String mainName;
    private String mainDetails;
    private String mainImage;

    public MyPagAndamSubData(Integer categoryID, String mainName, String mainDetails, String mainImage) {
        this.categoryID = categoryID;
        this.mainName = mainName;
        this.mainDetails = mainDetails;
        this.mainImage = mainImage;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getMainDetails() {
        return mainDetails;
    }

    public void setMainDetails(String mainDetails) {
        this.mainDetails = mainDetails;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
}
