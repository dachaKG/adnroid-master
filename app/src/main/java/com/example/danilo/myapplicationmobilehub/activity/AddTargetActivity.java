package com.example.danilo.myapplicationmobilehub.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobile.api.idzt9jftjm4c.InspectorAPIClient;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.InspectorModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TargetDTO;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.example.danilo.myapplicationmobilehub.R;

public class AddTargetActivity extends AppCompatActivity {

    Button submitButton;
    EditText targetText;

    InspectorAPIClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_target);

        apiClient = new ApiClientFactory()
                .credentialsProvider(AWSMobileClient.getInstance().getCredentialsProvider())
                .build(InspectorAPIClient.class);

        submitButton = findViewById(R.id.submit_target);
        targetText = findViewById(R.id.add_target_id);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TargetDTO targetDTO = new TargetDTO();
                targetDTO.setUserId("681206978735");
                targetDTO.setTargetId(targetText.getText().toString());


                PersistTarget persistTarget = new PersistTarget(AddTargetActivity.this);
                persistTarget.execute(targetDTO);

            }
        });

    }


    private class PersistTarget extends AsyncTask<TargetDTO, Void, InspectorModel> {


        Context context;

        private PersistTarget(Context context) {
            this.context = context;
        }

        @Override
        protected InspectorModel doInBackground(TargetDTO... targetDTOS) {
            return apiClient.targetPost(targetDTOS[0]);
        }

        @Override
        protected void onPostExecute(InspectorModel inspectorModel) {
            super.onPostExecute(inspectorModel);

            if(inspectorModel != null) {
                Intent intent = new Intent(context, NavigationActivity.class);
                startActivity(intent);
            }
        }
    }

}
