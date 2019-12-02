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
import com.example.danilo.myapplicationmobilehub.adapter.TemplateListAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemplateListFragment extends Fragment {

    ListView listView;

    DynamoDBMapper dynamoDBMapper;

    public TemplateListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_template_list, container, false);
        final TargetDTO targetDTO = new TargetDTO();

        targetDTO.setUserId("681206978735");

        listView = view.findViewById(R.id.template_list_view);

//        expandableListView = view.findViewById(R.id.expandable_templates_fragment);
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        RetrieveTemplates retrieveTemplates = new RetrieveTemplates();
        retrieveTemplates.execute(targetDTO);

        return view;
    }


    private class RetrieveTemplates extends AsyncTask<TargetDTO, Void, List<TemplateModel>> {

        @Override
        protected List<TemplateModel> doInBackground(TargetDTO... targetDTOS) {

            InspectorModel inspectorModel = dynamoDBMapper.load(InspectorModel.class, targetDTOS[0].getUserId());

            return inspectorModel.getTemplates();
//            return apiClient.targetsPost(targetDTOS[0]);
//            return null;
        }

        @Override
        protected void onPostExecute(List<TemplateModel> templateModels) {
            super.onPostExecute(templateModels);
//            Log.i(LOG_TAG, "Target models " + targetModels.size());
            System.out.println("Template models size " + templateModels.size());

            TemplateListAdapter adapter = new TemplateListAdapter(getActivity(), R.layout.template_list_adapter, templateModels);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    TemplateFragment templateFragment = new TemplateFragment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("TemplateModel", (TemplateModel) parent.getItemAtPosition(position));
                    templateFragment.setArguments(bundle);

                    ft.replace(R.id.main_screen, templateFragment);
                    ft.addToBackStack(null);
                    ft.commit();

                }
            });
//            ExpandableTemplatesListViewAdapter adapter = new ExpandableTemplatesListViewAdapter(getActivity(), templateModels);
//
//            expandableListView.setAdapter(adapter);

        }
    }

}
