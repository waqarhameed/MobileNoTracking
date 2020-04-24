package com.example.phonenotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;

public class SignIn extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Log.d("TAG", "Show signIn screen");
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

            @Override
            public void onResult(UserStateDetails result) {
                Log.d(TAG, "onResult: " + result.getUserState());
                switch (result.getUserState()) {
                    case SIGNED_IN:
                        Log.i("INIT", "logged in!");
                        Intent signInActivity = new Intent(SignIn.this, MainActivity.class);
                        startActivity(signInActivity);
                        break;
                    case SIGNED_OUT:
                        Log.i(TAG, "onResult: User did not choose to sign-in");
                        showSignIn();
                        break;
                    default:
                        AWSMobileClient.getInstance().signOut();
                        showSignIn();
                        break;
                }
            }

            public void onError(Exception e) {
                Log.e("INIT", "Initialization error.", e);
            }
        });
    }

    private void showSignIn() {
        try {

            AWSMobileClient.getInstance().showSignIn(
                    this,

                    SignInUIOptions.builder()
                            .logo(R.mipmap.ic_track_call_round)
                            .backgroundColor(R.color.colorPrimary)
                            .canCancel(true)
                            .nextActivity(MainActivity.class).build());

        } catch (Exception e) {
            Log.e("INIT", "SignIn GUI.", e);
        }
    }

}

