package com.example.danilo.myapplicationmobilehub.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.amazonaws.mobile.api.idzt9jftjm4c.InspectorAPIClient;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.InspectorModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TargetDTO;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TargetModel;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.danilo.myapplicationmobilehub.R;
import com.example.danilo.myapplicationmobilehub.adapter.TargetListAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TargetListFragment extends Fragment {

//    ExpandableListView expandableListView;

    InspectorAPIClient apiClient;

    DynamoDBMapper dynamoDBMapper;

    ListView listView;

    public TargetListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_target_list, container, false);

        listView = view.findViewById(R.id.target_list_view);

        final TargetDTO targetDTO = new TargetDTO();

        targetDTO.setUserId("681206978735");

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        RetrieveTargets retrieveTargets = new RetrieveTargets();
        retrieveTargets.execute(targetDTO);

        return view;
    }

    private class RetrieveTargets extends AsyncTask<TargetDTO, Void, List<TargetModel>> {

        @Override
        protected List<TargetModel> doInBackground(TargetDTO... targetDTOS) {

            InspectorModel inspectorModel = dynamoDBMapper.load(InspectorModel.class, targetDTOS[0].getUserId());

            return inspectorModel.getTargets();
        }

        @Override
        protected void onPostExecute(List<TargetModel> targetModels) {
            super.onPostExecute(targetModels);
            TargetListAdapter adapter = new TargetListAdapter(getActivity(), R.layout.target_list_adapter, targetModels);
            listView.setAdapter(adapter);
        }
    }


}
