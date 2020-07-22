package com.example.defaultdemotoken.Retrofit;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    //http://dkbraende.demoproject.info/rest/V1/"
    //http://dkbraende.demoproject.info/rest/V1/

   // public static final String MAIN_URLL = "http://dkbraende.demoproject.info/rest/V1/";
    public static final String MAIN_URLL = "http://app.demoproject.info/rest/V1/";
    public static final String SUB_URLL = "http://app.demoproject.info/rest/V1/";

    private static Retrofit retrofit = null;
    private static Retrofit retrofit1 = null;

    public static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100,TimeUnit.SECONDS).build();

    public static OkHttpClient client1 = new OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100,TimeUnit.SECONDS).build();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(MAIN_URLL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }



}
