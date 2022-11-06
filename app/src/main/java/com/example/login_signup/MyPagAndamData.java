package com.example.login_signup;

public class MyPagAndamData {

    private String mainName;
    private String mainDetails;
    private Integer mainImage;

    public MyPagAndamData(String mainName, String mainDetails, Integer mainImage) {
        this.mainName = mainName;
        this.mainDetails = mainDetails;
        this.mainImage = mainImage;
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

    public Integer getMainImage() {
        return mainImage;
    }

    public void setMainImage(Integer mainImage) {
        this.mainImage = mainImage;
    }
}
