package com.example.login_signup;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface imageInterface {
    @Multipart
    @POST("reportedevents")
    Call<addImageRes> uploadImage( @Part("reportName") RequestBody reportName,
                                   @Part("reportDescription") RequestBody reportDescription,
                                   @Part("ReportCategoryId") RequestBody ReportCategoryId,
                                   @Part("MobileUserAccountId") RequestBody MobileUserAccountId,
                                   @Part MultipartBody.Part images);
}
