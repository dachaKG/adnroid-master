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
import android.widget.TextView;

import com.amazonaws.mobile.api.idzt9jftjm4c.model.FindingModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.InspectorModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TargetDTO;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.danilo.myapplicationmobilehub.R;
import com.example.danilo.myapplicationmobilehub.adapter.FindingListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindingListFragment extends Fragment {

    DynamoDBMapper dynamoDBMapper;

    ListView listView;

    public FindingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finding_list, container, false);

        final TargetDTO targetDTO = new TargetDTO();

        targetDTO.setUserId("681206978735");

        listView = view.findViewById(R.id.finding_list_view);

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentials());
        dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getSerializable("findingModels") != null) {
            ArrayList<FindingModel> findingModels = (ArrayList<FindingModel>) bundle.getSerializable("findingModels");
            final FindingListAdapter adapter = new FindingListAdapter(getActivity(), R.layout.finding_list_adapter, findingModels);

            listView.setAdapter(adapter);
        } else {
            RetrieveFindings retrieveRuns = new RetrieveFindings();
            retrieveRuns.execute(targetDTO);
        }
        return view;

    }

    private class RetrieveFindings extends AsyncTask<TargetDTO, Void, List<FindingModel>> {

        @Override
        protected List<FindingModel> doInBackground(TargetDTO... targetDTOS) {

            InspectorModel inspectorModel = dynamoDBMapper.load(InspectorModel.class, targetDTOS[0].getUserId());

            return inspectorModel.getFindings();
        }

        @Override
        protected void onPostExecute(List<FindingModel> findingModels) {
            super.onPostExecute(findingModels);
            final FindingListAdapter adapter = new FindingListAdapter(getActivity(), R.layout.finding_list_adapter, findingModels);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    FindingFragment findingFragment = new FindingFragment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FindingModel", (FindingModel) parent.getItemAtPosition(position));
                    findingFragment.setArguments(bundle);

                    ft.replace(R.id.main_screen, findingFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }

    }
}
