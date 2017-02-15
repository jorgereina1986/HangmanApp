package com.jorgereina.hangmanapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface HangmanApi {
    //http://linkedin-reach.hagbpyjegb.us-west-2.elasticbeanstalk.com/words
    @Headers({"Accept:text/plain"})
    @GET("words")
    Call<ResponseBody> listWords();

}
