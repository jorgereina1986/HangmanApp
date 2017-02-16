package com.jorgereina.hangmanapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jorgereina.hangmanapp.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //    private static final String BASE_URL = "http://linkedin-reach.hagbpyjegb.us-west-2.elasticbeanstalk.com/";

    private TextView secretWordTv;
    private TextView updatingTV;
    private TextView guessCountTV;
    private EditText guessEt;
    private Button okBtn;

    private Random random;
    private String secretWord;
    private String inputLetter;
    int guessCount = 0;
    char[] dashedWordArr;
    private String dashedWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        secretWordTv = (TextView) findViewById(R.id.hw_tv);
        guessEt = (EditText) findViewById(R.id.guess_et);
        updatingTV = (TextView) findViewById(R.id.updating_tv);
        okBtn = (Button) findViewById(R.id.ok_btn);
        guessCountTV = (TextView) findViewById(R.id.counter_tv);

        resetGame();

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting letter that player input
                inputLetter = guessEt.getText().toString();
                inputLetter = inputLetter.toLowerCase();
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
                    if (secretWord.equals(String.valueOf(dashedWordArr))){
                        winGame();
                    }
                    guessEt.setText("");
                }
                else {
                    guessEt.setText("");
                    guessCount--;
                    guessCountTV.setText(guessCount + "");
                    if (guessCount == 0) {

                        loseGame();
                    }
                }

            }
        });
    }

    private void loseGame() {
        //alert dialog showing that you lost
        //get new word
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("YOU LOSE!")
                .setMessage("Try Again?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetGame();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void winGame() {
        //get new word
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Hangman")
                .setTitle("YOU WIN")
                .setMessage("Try Again?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetGame();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void resetGame(){

        random = new Random();
        guessCount = 6;
        //getting secret word randomly
        secretWord = (SplashScreen.listOfWords[random.nextInt(SplashScreen.listOfWords.length)]);
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
    }

}


