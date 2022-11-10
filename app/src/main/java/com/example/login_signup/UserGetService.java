package com.example.login_signup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserGetService {
    @GET("mobile_user_account")
    Call<List<UserGet>> getSuperID();
}
