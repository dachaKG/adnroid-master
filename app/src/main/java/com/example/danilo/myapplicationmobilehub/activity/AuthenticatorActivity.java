package com.example.danilo.myapplicationmobilehub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

//import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.example.danilo.myapplicationmobilehub.R;

public class AuthenticatorActivity extends AppCompatActivity {

    public static final String LOG_TAG = AuthenticatorActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);


        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i(LOG_TAG, userStateDetails.getUserState().toString());
                switch (userStateDetails.getUserState()){
                    case SIGNED_IN:
                        Intent i = new Intent(AuthenticatorActivity.this, NavigationActivity.class);
                        startActivity(i);
                        break;
                    case SIGNED_OUT:
                        showSignIn();
                        break;
                    default:
                        AWSMobileClient.getInstance().signOut();
                        showSignIn();
                        break;
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(LOG_TAG, e.toString());
            }
        });
    }

    private void showSignIn() {
        try {
            AWSMobileClient.getInstance().showSignIn(this,
                    SignInUIOptions.builder().nextActivity(NavigationActivity.class).build());
        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
    }
}
