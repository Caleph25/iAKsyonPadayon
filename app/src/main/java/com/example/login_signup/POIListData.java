package com.example.login_signup;

public class POIListData {
    private String description;
    private String imageUrl;
    private String xLocation;
    private String yLocation;
    public POIListData(String description, String imageUrl,String xLocation,String yLocation) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImgId() {
        return imageUrl;
    }
    public void setImgId(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getxLocation() {
        return xLocation;
    }
    public void setxLocation(String xLocation) {
        this.xLocation = xLocation;
    }
    public String getyLocation() {
        return yLocation;
    }
    public void setyLocation(String yLocation) {
        this.yLocation = yLocation;
    }
}
