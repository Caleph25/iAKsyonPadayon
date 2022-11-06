package com.example.login_signup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/api/mobile_user_account_personal_info")
    Call<UserResponse>saveUser(@Body UserRequest userRequest);

}
