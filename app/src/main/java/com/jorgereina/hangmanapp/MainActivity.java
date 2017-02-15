package com.jorgereina.hangmanapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jorgereina.hangmanapp.models.Player;
import com.jorgereina.hangmanapp.models.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

//    private static final String BASE_URL = "http://linkedin-reach.hagbpyjegb.us-west-2.elasticbeanstalk.com/";
    private static final String BASE_URL = "http://linkedin-reach.hagbpyjegb.us-west-2.elasticbeanstalk.com/words";

    String value = "";

    private OkHttpClient client;
    private Request request;
    private Handler handler;
    private String[] words;
    Word word;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        word = new Word();
        word.setWord("hello");
        tv = (TextView) findViewById(R.id.hw_tv);
        handler = new Handler(Looper.getMainLooper());
        player = new Player();



        networkCall();





    }

    private void networkCall() {
        client = new OkHttpClient();
        request = new Request.Builder()
                .url(BASE_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                value = response.body().string();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    private void convertResponse(String str){

        List<String> wordList = new ArrayList<>();
        Collections.addAll(wordList, str.split("\n"));
        words = str.split("\n");
        tv.setText(Arrays.toString(words));
    }

}
