package com.mobitribe.myblog;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthenticationActivity extends AppCompatActivity {

    EditText username, password;
    ProgressDialog progressDialog;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gets called when the button is clicked.
                if(isFormValid()) {
                    //perform signin
                    performSignIn();
                }else {
                    //Replace the text in the textview
                    //replaceText(enteredText);
                }
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gets called when the button is clicked.

                //gets called when the button is clicked.
                if(isFormValid()) {
                    //perform registration
                    performRegistration();
                }else {
                    //Replace the text in the textview
                    //replaceText(enteredText);
                }
            }
        });

        //initialising progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait");


    }

    private Boolean isFormValid() {

        if (username.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Username cannot be left empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if (password.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Password cannot be left empty", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void performSignIn() {
        //Mock an api call to sign in.
        //new SignInTask().execute(username.getText().toString(),password.getText().toString());

        //Do the real one
        showProgressDialog(true);
        AuthenticationRequest AuthReq = new AuthenticationRequest(username.getText().toString().trim(), password.getText().toString().trim());
        ApiManager.getApiInterface().login(AuthReq).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                showProgressDialog(false);
                if(response.isSuccessful()) {
                     //showAlert("welcome", response.body().getMessage());
                    navigateToArticleListActivity();
                 } else {
                     try {
                         String errorMessage = response.errorBody().string();
                         try{
                             ErrorResponse errorResponse = new Gson().fromJson(errorMessage, ErrorResponse.class);
                             showAlert("SignIn failed", errorResponse.getError());
                         } catch(JsonSyntaxException jsonException) {
                             jsonException.printStackTrace();
                             showAlert("SignIn failed", "Something went wrong");
                         }
                     } catch (IOException e) {
                         e.printStackTrace();
                         showAlert("SignIn failed", "Something went wrong-1");
                     }
                 }

            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                showProgressDialog(false);
                showAlert("SignIn failed", "Something went wrong-2");
            }
        });

    }


   private void performRegistration() {
       //Mock an api call to register.
       //new RegisterationTask().execute(username.getText().toString(),password.getText().toString());

       //Do the real one
       showProgressDialog(true);
       AuthenticationRequest AuthReq = new AuthenticationRequest(username.getText().toString().trim(), password.getText().toString().trim());
       ApiManager.getApiInterface().registration(AuthReq).enqueue(new Callback<MessageResponse>() {
           @Override
           public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
               showProgressDialog(false);
               if(response.isSuccessful()) {
                   showAlert("welcome", response.body().getMessage());
               } else {
                   try {
                       String errorMessage = response.errorBody().string();
                       try{
                           ErrorResponse errorResponse = new Gson().fromJson(errorMessage, ErrorResponse.class);
                           showAlert("Registration failed", errorResponse.getError());
                       } catch(JsonSyntaxException jsonException) {
                           jsonException.printStackTrace();
                           showAlert("Registration failed", "Something went wrong");
                       }
                   } catch (IOException e) {
                       e.printStackTrace();
                       showAlert("Registration failed", "Something went wrong-1");
                   }
               }

           }

           @Override
           public void onFailure(Call<MessageResponse> call, Throwable t) {
               showProgressDialog(false);
               showAlert("Registration failed", "Something went wrong-2");
           }
       });

   }

    private void showProgressDialog(Boolean shouldShow) {
        if(shouldShow) {
            progressDialog.show();
        }else {
            progressDialog.dismiss();
        }
    }

    private void showAlert(String title, String message)
    {
        //Building the dialog
        Log.i(TAG,"Inside showAlert");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int which)
            {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    class SignInTask extends AsyncTask<String, Void, Boolean>
    {
        String mockUsername = "test";
        String mockPassword = "password";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(true); //at start show our progress dialog
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showProgressDialog(false); //at end hide our progress dialog

            if(aBoolean)
            {
                showAlert("Welcome", "You have successfully signed in");
            }
            else
            {
                showAlert("Failed", "Username/Password is incorrect");
            }

        }

     //   @Override
     //   protected void onProgressUpdate(Void... values) {
     //       super.onProgressUpdate(values);
     //   }


        @Override
        protected Boolean doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];

            // do something with this
            //simulate processing
            try{
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            return username.contentEquals(mockUsername) && password.contentEquals(mockPassword);

            //return true; //or false
        }
    }

    private void navigateToArticleListActivity()
    {
        Intent intent = new Intent(this, ArticleListActivity.class);
        startActivity(intent);
    }

}
