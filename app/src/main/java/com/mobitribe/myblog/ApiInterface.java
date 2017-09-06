package com.mobitribe.myblog;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.Call;


/**
 * Created by TEST on 9/4/2017.
 */

public interface ApiInterface {
    @POST(NetworkURL.LOGIN)
    Call<MessageResponse> login(@Body AuthenticationRequest body);

    @POST(NetworkURL.REGISTERATION)
    Call<MessageResponse> registration(@Body AuthenticationRequest body);

    @GET(NetworkURL.GET_ARTICLES)
    Call<List<Article>> getArticles();
}
