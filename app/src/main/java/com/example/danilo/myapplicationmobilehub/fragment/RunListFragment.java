package com.example.danilo.myapplicationmobilehub.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amazonaws.mobile.api.idzt9jftjm4c.model.InspectorModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.RunModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TargetDTO;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TemplateModel;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.danilo.myapplicationmobilehub.R;
import com.example.danilo.myapplicationmobilehub.adapter.RunListAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunListFragment extends Fragment {

    DynamoDBMapper dynamoDBMapper;

    ListView listView;

    public RunListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_run_list, container, false);
        final TargetDTO targetDTO = new TargetDTO();

        targetDTO.setUserId("681206978735");

        listView = view.findViewById(R.id.run_list_view);
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        RetrieveRuns retrieveRuns = new RetrieveRuns();
        retrieveRuns.execute(targetDTO);



        return view;
    }

    private class RetrieveRuns extends AsyncTask<TargetDTO, Void, InspectorModel> {

        @Override
        protected InspectorModel doInBackground(TargetDTO... targetDTOS) {

            InspectorModel inspectorModel = dynamoDBMapper.load(InspectorModel.class, targetDTOS[0].getUserId());

            return inspectorModel;
        }

        @Override
        protected void onPostExecute(InspectorModel inspectorModel) {
            super.onPostExecute(inspectorModel);
            final RunListAdapter adapter = new RunListAdapter(getActivity(), R.layout.run_list_adapter, inspectorModel.getRuns(), inspectorModel);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    RunFragment runFragment = new RunFragment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("RunModel", (RunModel) parent.getItemAtPosition(position));
                    bundle.putSerializable("InspectorModel", (InspectorModel) inspectorModel);
                    runFragment.setArguments(bundle);

                    ft.replace(R.id.main_screen, runFragment);
                    ft.addToBackStack(null);
                    ft.commit();

                }
            });

        }
    }


}
