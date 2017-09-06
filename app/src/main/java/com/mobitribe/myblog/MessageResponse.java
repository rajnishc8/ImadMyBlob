package com.mobitribe.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TEST on 9/4/2017.
 */

public class MessageResponse {
    @SerializedName("message")
    String message;

    public String getMessage()
    {
        return message;
    }
}
