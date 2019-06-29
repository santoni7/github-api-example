package com.bigdig.githubapiexample;

import android.content.Context;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubService {
    private static GithubApi api;

    public static GithubApi getApi(){
        if(api != null) {
            return api;
        } else {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            api = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GithubApi.class);
            return api;
        }
    }
}
