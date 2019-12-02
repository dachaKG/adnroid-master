package com.example.danilo.myapplicationmobilehub.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobile.api.idzt9jftjm4c.InspectorAPIClient;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.InspectorModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TargetDTO;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.util.StringUtils;
import com.example.danilo.myapplicationmobilehub.R;
import com.example.danilo.myapplicationmobilehub.notifications.MyFirebaseMessagingService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    InspectorAPIClient apiClient;

    public static PinpointManager pinpointManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        apiClient = new ApiClientFactory()
                .credentialsProvider(AWSMobileClient.getInstance().getCredentialsProvider())
                .build(InspectorAPIClient.class);

        CognitoUserPool userPool = new CognitoUserPool(MainActivity.this, new AWSConfiguration(MainActivity.this));
        String str = userPool.getCurrentUser().getUserId();
        String clientId = userPool.getClientId();

        getPinpointManager(getApplicationContext());

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        String msg = "Successful";
                        if(!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });

//        callCloudLogic();
    }

    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            final AWSConfiguration awsConfig = new AWSConfiguration(applicationContext);
            Log.i("Aws config", "aws config " + awsConfig);
            AWSMobileClient.getInstance().initialize(applicationContext, awsConfig, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i("INIT", "user details" + userStateDetails.getUserState());
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", "Initialization error.", e);
                }
            });

            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance(),
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            final String token = task.getResult().getToken();
                            Log.d(LOG_TAG, "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);
                        }
                    });
        }
        return pinpointManager;
    }


    public void callCloudLogic() {
        // Create components of api request
        final TargetDTO targetDTO = new TargetDTO();

        targetDTO.setUserId("681206978735");
//        targetDTO.setRegion("us-east-2");
//        targetDTO.setUsername("danilo");
        targetDTO.setTargetId("0-L2iik4Jo");

        final String method = "POST";

        final String path = "/inspector";

        final String body = "ssss";//.toString();
//        final String body = new Gson().toJson(targetDTO);
        final byte[] content = body.getBytes(StringUtils.UTF8);

        final Map parameters = new HashMap<>();
        parameters.put("id", "681206978735");

        final Map headers = new HashMap<>();

        // Use components to create the api request
        ApiRequest localRequest =
                new ApiRequest(apiClient.getClass().getSimpleName())
                        .withPath(path)
//                        .withParameter("id", "681206978735")
                        .withHttpMethod(HttpMethodName.valueOf(method))
                        .withHeaders(headers)
                        .addHeader("Content-Type", "application/json")
                        .withBody(content)
                        .withParameters(parameters);

        // Only set body if it has content.
//        if (body.length() > 0) {
//            localRequest = localRequest
////                    .addHeader("Content-Length", String.valueOf(content.length))
//                    .withBody(new Gson().toJson(targetDTO));
//        }

        final ApiRequest request = localRequest;

        // Make network call on background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ObjectMapper ow = new ObjectMapper();//.writer().withDefaultPrettyPrinter();

                    ow.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                    ow.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    Log.d(LOG_TAG,
                            "Invoking API w/ Request : " +
//                                    request.getHttpMethod() + ":" +
//                                    request.getPath());
                                    ow.writer().withDefaultPrettyPrinter().writeValueAsString(request));

//                    final ApiResponse response = apiClient.execute(request);
//                    final Empty inspectorModel = apiClient.targetPost(targetDTO);
                    InspectorModel inspectorModel = apiClient.targetPost(targetDTO);
//                    System.out.println("api client rest " + apiClient.runIdGet("681206978735"));
//                    final JSONObject response = apiClient.runIdGet("681206978735");

//                    String json = ow.writeValueAsString(response);
//                    final InputStream responseContentStream = inspectorModel.getContent();
//                    Log.d(LOG_TAG, "Response list: " + ow.writer().withDefaultPrettyPrinter().writeValueAsString(inspectorModel));
//                    Log.d(LOG_TAG, "Response list: " + new Gson().toJson(inspectorModel));
                    Log.d(LOG_TAG, "Response list: " + inspectorModel.getRegion());
//                    if (inspectorModel != null) {
////                        final String responseData = IOUtils.toString(responseContentStream);
////                        Log.d(LOG_TAG, "Response list: " + ow.writer().withDefaultPrettyPrinter().writeValueAsString(inspectorModel));
//                        Log.d(LOG_TAG, "Response list: " + inspectorModel);
////                        JSONObject jo = new JSONObject(ow.writer().withDefaultPrettyPrinter().writeValueAsString(responseContentStream));
////                        Log.d(LOG_TAG, "responseContentStream list: " + jo);
//                    }


//                    Log.d(LOG_TAG, "status code " + response.getStatusCode() + " status text: " + response.getStatusText());

                } catch (final Exception exception) {
                    Log.e(LOG_TAG, exception.getMessage(), exception);
                    exception.printStackTrace();
                }
            }
        }).start();
    }
}
