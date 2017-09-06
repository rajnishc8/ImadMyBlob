package com.mobitribe.myblog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TEST on 9/4/2017.
 */

public class ApiManager {
    private static ApiInterface apiInterface;

    private static void createApiInterface(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client =  new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://api.github.com")
                .baseUrl(NetworkURL.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface  = retrofit.create(ApiInterface.class);

    }

    public static ApiInterface getApiInterface()
    {
        if(apiInterface == null)
            createApiInterface();

        return apiInterface;
    }
}
