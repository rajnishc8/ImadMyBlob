package com.mobitribe.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TEST on 9/4/2017.
 */

public class ErrorResponse {
    @SerializedName("error")
    String error;

    public String getError()
    {
        return error;
    }
}
