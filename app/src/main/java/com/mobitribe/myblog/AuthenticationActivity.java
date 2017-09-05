package com.mobitribe.myblog;

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
                    //showAlert();
                }else {
                    //Replace the text in the textview
                    //replaceText(enteredText);
                }
            }
        });

        //initialising progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
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
        new SignInTask().execute(username.getText().toString(),password.getText().toString());
    }


   private void performRegistration() {
        //Mock an api call to register.
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
            return username.contentEquals(mockUsername) && password.contentEquals(mockPassword);

            //return true; //or false
        }
    }

}
