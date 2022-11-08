package com.example.login_signup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface imageInterface {
    @POST("reportedevents")
    Call<addImageRes> uploadImage(@Body addImageRes imageRes);
}
