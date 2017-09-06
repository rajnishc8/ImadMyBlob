package com.mobitribe.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TEST on 9/4/2017.
 */

public class AuthenticationRequest {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    public AuthenticationRequest(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
