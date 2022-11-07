package com.example.login_signup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("mobile_user_account")
    Call<UserResponse>saveUser(@Body UserRequest userRequest);

}
