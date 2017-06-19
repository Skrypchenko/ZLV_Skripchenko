package com.example.zlv_skripchenko.network;

import android.content.Context;

import com.example.zlv_skripchenko.network.ImmediateExecutor;
import com.example.zlv_skripchenko.network.NetworkAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yevgen on 18.06.2017.
 */

public class NetworkController {
    public static NetworkController instance;
    public static NetworkController getInstance(){
        if(instance==null){
            instance=new NetworkController();
            instance.setWorkClient();
        }
        return instance;
    }

    private static NetworkAPI getritAPI;
    private static NetworkAPI postritAPI;
    private static OkHttpClient client;

    public static OkHttpClient getClient() {
        return client;
    }

    public static void setTestClient() {
        client = new OkHttpClient.Builder()
                .dispatcher(new Dispatcher(new ImmediateExecutor()))
                .build();
    }

    public static void setWorkClient() {
        client = new OkHttpClient.Builder()
                .build();
    }

    public static void start(Context context) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

//         client  = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                long t1 = System.nanoTime();
//                System.out.println(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
//                okhttp3.Response response = chain.proceed(request);
//                long t2 = System.nanoTime();
//                System.out.println(String.format("Received response for %s in %.1fms%n%s%s", response.request().url(), (t2 - t1) / 1e6d, response.headers(), response.body().string()));
//                return response;
//            }
//        }).build();


        Retrofit get_retrofit = new Retrofit.Builder()
                .baseUrl(NetworkAPI.BASE_URL_1)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        getritAPI = get_retrofit.create(NetworkAPI.class);


        Retrofit post_retrofit = new Retrofit.Builder()
                .baseUrl(NetworkAPI.BASE_URL_2)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        postritAPI = post_retrofit.create(NetworkAPI.class);

    }

    public static NetworkAPI getGetNetworkAPI() {
        return getritAPI;
    }

    public static NetworkAPI getPostNetworkAPI() {
        return postritAPI;
    }

}
