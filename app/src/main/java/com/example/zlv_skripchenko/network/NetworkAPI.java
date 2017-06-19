package com.example.zlv_skripchenko.network;

import com.example.zlv_skripchenko.utils.MyContacts;
import com.example.zlv_skripchenko.utils.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Yevgen on 18.06.2017.
 */

public interface NetworkAPI {

   public static String BASE_URL_1 = "http://api.androidhive.info";
   public static final String BASE_URL_2 = "http://jsonplaceholder.typicode.com/";

    public class ListWrapper<T> {
        List<T> items;
    }


    @GET("/contacts/")
    Call<MyContacts> getQuestions();


    @POST("/posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("title") String title, @Field("body") String body, @Field("userId") long userId);


}
