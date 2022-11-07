package com.example.login_signup;

import com.google.gson.annotations.SerializedName;

public class UserGet {
    @SerializedName("MobileUserId")
    private int superId;

    public UserGet(int id){
        this.superId = id;
    }
    public int getID(){
        return superId;
    }
}
