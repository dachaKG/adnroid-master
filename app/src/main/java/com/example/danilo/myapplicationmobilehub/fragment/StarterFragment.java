package com.example.danilo.myapplicationmobilehub.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.api.idzt9jftjm4c.model.InspectorModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TargetDTO;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.danilo.myapplicationmobilehub.R;
import com.example.danilo.myapplicationmobilehub.activity.NavigationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class StarterFragment extends Fragment {

    DynamoDBMapper dynamoDBMapper;

    public StarterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_starter, container, false);
        final TargetDTO targetDTO = new TargetDTO();
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();
        targetDTO.setUserId("681206978735");

        RetrieveInspectorModel retrieveRuns = new RetrieveInspectorModel();
        retrieveRuns.execute(targetDTO);

        Button addTarget = view.findViewById(R.id.add_target_button);
        addTarget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                AddTargetFragment addTargetFragment = new AddTargetFragment();

                Bundle bundle = new Bundle();
                addTargetFragment.setArguments(bundle);

                ((NavigationActivity) getActivity()).getSupportActionBar().setTitle(R.string.add_target);
//                inflater.getSupportActionBar().setTitle(R.string.app_findings_list);

                ft.replace(R.id.main_screen, addTargetFragment);
                ft.addToBackStack(null);
                ft.commit();
            }

        });
        return view;
    }


    private class RetrieveInspectorModel extends AsyncTask<TargetDTO, Void, InspectorModel> {
        @Override
        protected InspectorModel doInBackground(TargetDTO... targetDTOS) {

            InspectorModel inspectorModel = dynamoDBMapper.load(InspectorModel.class, targetDTOS[0].getUserId());

            return inspectorModel;
        }

        @Override
        protected void onPostExecute(InspectorModel inspectorModel) {
            super.onPostExecute(inspectorModel);

            TextView targets = getActivity().findViewById(R.id.count_targets);
            TextView templates = getActivity().findViewById(R.id.count_templates);
            TextView runs = getActivity().findViewById(R.id.count_runs);
            TextView findings = getActivity().findViewById(R.id.count_findings);

            targets.setText(Integer.toString(inspectorModel.getTargets().size()));
            templates.setText(Integer.toString(inspectorModel.getTemplates().size()));
            runs.setText(Integer.toString(inspectorModel.getRuns().size()));
            findings.setText(Integer.toString(inspectorModel.getFindings().size()));
        }
    }


}
