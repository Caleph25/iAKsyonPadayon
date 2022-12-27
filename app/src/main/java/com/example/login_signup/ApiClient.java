package com.example.login_signup;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static ApiClient instance =null;
    private UserGetService myApi;
    private static Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.19:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }
    public static String VolletURL(){
        String HostUrl="http://192.168.1.19:8000/api/";
        return HostUrl;
    }
    public static UserService getUserService(){
        UserService userService = getRetrofit().create(UserService.class);

        return userService;
    }
    public static imageInterface getImages(){
        imageInterface images = getRetrofit().create(imageInterface.class);

        return images;
    }
    public static synchronized ApiClient getInstance(){
        if (instance == null){
            instance = new ApiClient();
        }
        return instance;
    }

    public UserGetService getMyApi(){
        return myApi;
    }
}
