package com.example.login_signup;

public class POIListData {
    private String description;
    private String imageUrl;
    private String xLocation;
    private String yLocation;
    private float xCurrentLocation;
    private float yCurrentLocation;
    private float distance;
    public POIListData(String description, String imageUrl,String xLocation,String yLocation,float xCurrentLocation,float yCurrentLocation,float distance) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.xCurrentLocation = xCurrentLocation;
        this.yCurrentLocation = yCurrentLocation;
        this.distance = distance;
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
    public float getxLocationCurrent() {
        return xCurrentLocation;
    }
    public void setxLocationCurrent(float xCurrentLocation) {
        this.xCurrentLocation = xCurrentLocation;
    }
    public float getyLocationCurrent() {
        return yCurrentLocation;
    }
    public void setyLocationCurrent(float yCurrentLocation) {
        this.yCurrentLocation = yCurrentLocation;
    }
    public float getDistance() {
        return distance;
    }
    public void setDistance(float distance) {
        this.distance = distance;
    }
}
