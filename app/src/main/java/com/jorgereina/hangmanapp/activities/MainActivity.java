package com.jorgereina.hangmanapp.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jorgereina.hangmanapp.R;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //    private static final String BASE_URL = "http://linkedin-reach.hagbpyjegb.us-west-2.elasticbeanstalk.com/";
    private static final String BASE_URL = "http://linkedin-reach.hagbpyjegb.us-west-2.elasticbeanstalk.com/words";

    private TextView secretWordTv;
    private TextView updatingTV;
    private TextView guessCountTV;
    private EditText guessEt;
    private Button okBtn;

    String strResponse = "";

    private OkHttpClient client;
    private Request request;
    private Handler handler;
    private String[] wordArray;
    private Random random;
    String secretWord;
    String inputLetter;
    int guessCount = 6;
    char[] dashedWordArr;
    String dashedWord;


    String[] dummyData = {"hello", "facbook", "minie", "youro", "oown", "businee", "programo", "codingd", "coderc", "androida"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(Looper.getMainLooper());
        secretWordTv = (TextView) findViewById(R.id.hw_tv);
        guessEt = (EditText) findViewById(R.id.guess_et);
        updatingTV = (TextView) findViewById(R.id.updating_tv);
        okBtn = (Button) findViewById(R.id.ok_btn);
        guessCountTV = (TextView) findViewById(R.id.counter_tv);
        random = new Random();
        wordArray = strResponse.split("\n"); //splitting response in to sting array

//        networkCall();


        //getting secret word randomly
        secretWord = (dummyData[random.nextInt(dummyData.length)]);
        dashedWord = secretWord;

        dashedWord = dashedWord.replaceAll("(?s).", "-");
        //creating empty word with dashes
        dashedWordArr = new char[secretWord.length()];

        secretWordTv.setText(secretWord);
        dashedWordArr = new char[secretWord.length()];
        for (int i = 0; i < dashedWordArr.length; i++) {
            dashedWordArr[i] = '-';
        }
        updatingTV.setText(dashedWord);

        guessCountTV.setText(guessCount + "");


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting letter that player input
                inputLetter = guessEt.getText().toString();
                //turning letter into a char
                char letter = inputLetter.charAt(0);
                //check if secret word contains the letter
                if (secretWord.contains(inputLetter)) {

                    for (int i = 0; i < secretWord.length(); i++) {

                        if (secretWord.charAt(i) == letter) {

                            dashedWordArr[i] = letter;

                            updatingTV.setText(String.valueOf(dashedWordArr));

                        }
                    }
                }
                else {
                    guessCount--;
                    guessCountTV.setText(guessCount + "");
                    if (guessCount == 0) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Hangman")
                                .setMessage("You ran out of tries. You Lose. Try Again.")
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

            }
        });
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

                strResponse = response.body().string();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        wordArray = strResponse.split("\n");
//                        pickWord.setText(Arrays.toString(wordArray));
                    }
                });
            }
        });
    }


    private void loseGame() {
        //alert dialog showing that you lost
        //get new word

    }

    private void winGame() {
        //get new word
    }

}


