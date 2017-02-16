package com.jorgereina.hangmanapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jorgereina.hangmanapp.R;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashScreen extends AppCompatActivity {

    private static final String BASE_URL = "http://linkedin-reach.hagbpyjegb.us-west-2.elasticbeanstalk.com/words";
    private static final int SPLASH_TIME_OUT = 23000;

    private ImageView logoIv;
    private TextView splashTv;

    private OkHttpClient client;
    private Request request;
    private static String wordResponse = "";
    private Handler handler;
    private Button button;

    static String[] listOfWords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logoIv = (ImageView) findViewById(R.id.logo_iv);
        logoIv.setImageResource(R.mipmap.ic_launcher);
        splashTv = (TextView) findViewById(R.id.splash_tv);
        button = (Button) findViewById(R.id.btn);

        handler = new Handler(Looper.getMainLooper());

        networkCall();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                Bundle bundle  = new Bundle();
//                bundle.putString("word", wordResponse);
//                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
////                bundle.putStringArray("words",wordArray );
//                intent.putExtras(bundle);
//                startActivity(intent);
//
//
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);

    }

    private void networkCall() {
        client = new OkHttpClient();
        request = new Request.Builder()
                .url(BASE_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), e+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                wordResponse = response.body().string();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listOfWords = wordResponse.split("\n");
                        splashTv.setText(Arrays.toString(listOfWords));
                    }
                });
            }
        });

    }


}
