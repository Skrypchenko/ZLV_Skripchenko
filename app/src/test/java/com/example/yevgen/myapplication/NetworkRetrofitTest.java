package com.example.yevgen.myapplication;

import com.example.yevgen.myapplication.waiter.Waiter;
import com.example.zlv_skripchenko.BuildConfig;
import com.example.zlv_skripchenko.utils.MyContact;
import com.example.zlv_skripchenko.utils.MyContacts;
import com.example.zlv_skripchenko.network.NetworkAPI;
import com.example.zlv_skripchenko.network.NetworkController;
import com.example.zlv_skripchenko.utils.Post;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Yevgen on 18.06.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 25)
public class NetworkRetrofitTest {



    private static NetworkAPI getritAPI;
    private static NetworkAPI postritAPI;

    MockWebServer mockServer;
    @Before
    public void setUp() throws Exception{
        NetworkController.getInstance().setTestClient();
       NetworkController.start(RuntimeEnvironment.application);
       getritAPI = NetworkController.getGetNetworkAPI();
       postritAPI = NetworkController.getPostNetworkAPI();
       this.mockServer = new MockWebServer();
       this.mockServer.start();
    }

    @After
    public void setDown() throws Exception{
        this.mockServer.shutdown();
    }




    @Test
    public void test() throws Exception {
        mockServer.enqueue(new MockResponse().setBody("hello, world!"));
        String url = mockServer.url("/").toString();
        String result = new OkHttpClient().newCall(new Request.Builder().url(url).build()).execute().body().string();
        assertEquals("hello, world!", result);
    }



    @Test
    public void reposTestPost() throws InterruptedException, IOException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Waiter waiter = new Waiter();
        final String title = "title";
        final String body = "body";
        final long userid = 1;
        postritAPI.savePost(title,body,userid).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                waiter.assertNotNull(response);
                Post result = response.body();
                waiter.assertNotNull(result);
                waiter.assertNotNull(result.getTitle());
                waiter.assertNotNull(result.getBody());
                waiter.assertEquals(result.getTitle(), title);
                waiter.assertEquals(result.getBody(), body);
                waiter.assertEquals(result.getUserId(), userid);
                waiter.resume();
                latch.countDown();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                waiter.fail(t);
                latch.countDown();
            }
        });
        latch.await();

    }



    @Test
    public void reposTestGet() throws InterruptedException,Exception {
        final Waiter waiter = new Waiter();
        final CountDownLatch latch = new CountDownLatch(1);
        getritAPI.getQuestions().enqueue(new Callback<MyContacts>() {
            @Override
            public void onResponse(Call<MyContacts> call, Response<MyContacts> response) {
                waiter.assertNotNull(response);
                MyContacts m =response.body();
                MyContacts result = response.body();
                waiter.assertNotNull(result);
                List<MyContact> list = result.getContacts();
                waiter.assertNotNull(list);
                waiter.assertTrue(list.size() > 0);
                MyContact myContact = list.get(0);
                System.out.println(m.getContacts().get(0).getEmail());
                waiter.resume();
                latch.countDown();
            }

            @Override
            public void onFailure(Call<MyContacts> call, Throwable t) {
                waiter.fail(t);
                latch.countDown();
            }
        });
        latch.await();
    }
}

