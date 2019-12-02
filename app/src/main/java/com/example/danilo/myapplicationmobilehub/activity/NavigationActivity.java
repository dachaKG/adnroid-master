package com.example.danilo.myapplicationmobilehub.activity;

import android.app.NativeActivity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


import com.amazonaws.mobile.api.idzt9jftjm4c.model.InspectorModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.RunModel;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.danilo.myapplicationmobilehub.R;
import com.example.danilo.myapplicationmobilehub.fragment.AddTargetFragment;
import com.example.danilo.myapplicationmobilehub.fragment.FindingListFragment;
import com.example.danilo.myapplicationmobilehub.fragment.RunFragment;
import com.example.danilo.myapplicationmobilehub.fragment.RunListFragment;
import com.example.danilo.myapplicationmobilehub.fragment.StarterFragment;
import com.example.danilo.myapplicationmobilehub.fragment.TargetListFragment;
import com.example.danilo.myapplicationmobilehub.fragment.TemplateListFragment;
import com.example.danilo.myapplicationmobilehub.notifications.MyFirebaseMessagingService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.stream.Stream;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String LOG_TAG = NavigationActivity.class.getSimpleName();

    private int activeItem = -1;

    public static PinpointManager pinpointManager;

    DynamoDBMapper dynamoDBMapper;
    int activeitem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Amazon Inspector");
        toolbar.setTitleTextColor(getResources().getColor(R.color.orange));
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.orange));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.darkGrey));
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        System.out.println("Device id: " + android_id);
        // Initialize PinpointManager
        System.out.println("pinpoint manager: " + getPinpointManager(getApplicationContext()));

        Fragment fragment = null;
        if (savedInstanceState == null) {
            fragment = getFragmentToShow(activeItem);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.main_screen, fragment, "starterFragment");
            ft.addToBackStack(null);
            ft.commit();

        } else {
            activeItem = savedInstanceState.getInt("activeItem");
            fragment = getFragmentToShow(activeItem);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Toast.makeText(NavigationActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("INIT", "user details onResult: " + userStateDetails.getUserState());
                        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentials());
                        dynamoDBMapper = DynamoDBMapper.builder()
                                .dynamoDBClient(dynamoDBClient)
                                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                .build();

                        TextView textView = findViewById(R.id.user_name);
                        textView.setText(AWSMobileClient.getInstance().getUsername());
                        if (intent.getStringExtra("runArn") != null) {

                            String runArn = intent.getStringExtra("runArn");
                            String id = runArn.split(":")[4];
                            InspectorModel inspectorModel = dynamoDBMapper.load(InspectorModel.class, id);
                            Stream<RunModel> runModelList = inspectorModel.getRuns().stream().filter(run -> run.getArn().equals(runArn));

                            RunModel runModel = runModelList.findFirst().get();
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            RunFragment runFragment = new RunFragment();

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("RunModel", runModel);
                            runFragment.setArguments(bundle);

                            ft.replace(R.id.main_screen, runFragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );


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

            System.out.println("token firebase " + FirebaseInstanceId.getInstance().getToken());
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("activeItem", activeItem);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        activeItem = item.getItemId();
        Fragment fragment = getFragmentToShow(id);

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.main_screen, fragment, "starterFragment");
            ft.addToBackStack(null);
            ft.commit();
        }

        item.setChecked(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Fragment getFragmentToShow(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_targets_list:
                fragment = new TargetListFragment();
                getSupportActionBar().setTitle(R.string.app_target_list);
                break;
            case R.id.nav_templates_list:
                fragment = new TemplateListFragment();
                getSupportActionBar().setTitle(R.string.app_template_list);
                break;
            case R.id.nav_runs_list:
                fragment = new RunListFragment();
                getSupportActionBar().setTitle(R.string.app_run_list);
                break;
            case R.id.nav_finding_list:
                fragment = new FindingListFragment();
                getSupportActionBar().setTitle(R.string.app_findings_list);
                break;
            case R.id.nav_add_target:
                fragment = new AddTargetFragment();
                getSupportActionBar().setTitle(R.string.add_target);
                break;
            case R.id.nav_log_out:
                AWSMobileClient.getInstance().signOut();
                Intent i = new Intent(NavigationActivity.this, AuthenticatorActivity.class);
                startActivity(i);
                break;
            default:
                fragment = new StarterFragment();
                getSupportActionBar().setTitle(R.string.homepage);
//                logout();


           /* case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;*/
//            default:
//                fragment = new StarterFragment();
//                getSupportActionBar().setTitle(R.string.app_bar_home);
        }
        return fragment;
    }


}
