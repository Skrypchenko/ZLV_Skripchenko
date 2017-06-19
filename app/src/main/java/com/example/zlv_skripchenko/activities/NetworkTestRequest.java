package com.example.zlv_skripchenko.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.zlv_skripchenko.utils.MyContact;
import com.example.zlv_skripchenko.utils.MyContacts;
import com.example.zlv_skripchenko.network.NetworkController;
import com.example.zlv_skripchenko.utils.Post;
import com.example.zlv_skripchenko.R;


/**
 * Created by Yevgen on 18.06.2017.
 */

public class NetworkTestRequest extends Activity implements View.OnClickListener {


    private Button id_button_get;
    private TextView id_tv_code;
    private TextView id_tv_code_post;
    private Button id_button_post;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        id_button_get = (Button)findViewById(R.id.id_button_get);
        id_button_get.setOnClickListener(this);
        id_tv_code = (TextView) findViewById(R.id.id_tv_code);

        id_button_post = (Button)findViewById(R.id.id_button_post);
        id_button_post.setOnClickListener(this);
        id_tv_code_post = (TextView) findViewById(R.id.id_tv_code_post);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_button_get:
                idSetGet();
                break;
            case R.id.id_button_post:
                idSetPost("TITLE", "BODY", 1);
                break;
        }
    }




    public void idSetGet() {
        Call<MyContacts> call = NetworkController.getGetNetworkAPI().getQuestions();
        call.enqueue(new Callback<MyContacts>() {
            @Override
            public void onResponse(Call<MyContacts> call, Response<MyContacts> response) {
                MyContacts myContacts = response.body();
                if(myContacts!=null){
                    List<MyContact> items = myContacts.getContacts();
                    String str="";
                    if(items!=null){
                    for (int i=0;i<items.size();i++){
                        MyContact myContact = items.get(i);
                        str +="name :\""+myContact.getName()+"\" email :\""+myContact.getEmail()+"\" body :\""+myContact.getGender()+"\" "+"\n";
                    }}
                    id_tv_code.setText(str);
                }
            }
            @Override
            public void onFailure(Call<MyContacts> call, Throwable t) {
            }
        });
    }

    public void idSetPost(String title,String body,long userId) {
        NetworkController.getPostNetworkAPI().savePost(title, body, userId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i("", "post submitted to API." + response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e("", "Unable to submit post to API.");
            }
        });
    }




    public void showResponse(String response) {
        if(response!=null){
            id_tv_code_post.setText(response);
        }
    }


}






