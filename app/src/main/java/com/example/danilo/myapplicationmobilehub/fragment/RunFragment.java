package com.example.danilo.myapplicationmobilehub.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.api.idzt9jftjm4c.model.TemplateModel;
import com.amazonaws.mobile.client.AWSMobileClient;

import com.amazonaws.mobile.api.idzt9jftjm4c.model.FindingModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.InspectorModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.RunModel;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.danilo.myapplicationmobilehub.R;

import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunFragment extends Fragment {

    private static final String SEPARATOR = "\n";
    ListView listView;

    DynamoDBMapper dynamoDBMapper;

    public RunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_run, container, false);

        Bundle bundle = getArguments();
        RunModel runModel = (RunModel) bundle.getSerializable("RunModel");
        InspectorModel inspectorModel = (InspectorModel) bundle.getSerializable("InspectorModel");

        TextView arn = view.findViewById(R.id.runArn);
        TextView status = view.findViewById(R.id.runStatus);
        TextView start = view.findViewById(R.id.runStart);
        TextView duration = view.findViewById(R.id.runDuration);
        TextView tvFindings = view.findViewById(R.id.runFindingsCount);
        TextView tvRules = view.findViewById(R.id.runRules);
        TextView tvRunTemplateName = view.findViewById(R.id.runTemplateName);

        String templateName = null;
        for(TemplateModel templateModel : inspectorModel.getTemplates()) {
            if(templateModel.getArn().equals(runModel.getAssessmentTemplateRun())) {
                templateName = templateModel.getName();
                break;
            }
        }

        Map<String, Integer> findings = runModel.getFindingCounts();

        Integer findingsCount = 0;
        if (findings != null) {
            for (Integer value : findings.values()) {
                findingsCount += value;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(String rule : runModel.getRulesPackagesArns()){

            stringBuilder.append(rule);

            stringBuilder.append(SEPARATOR);

        }

        String rules = stringBuilder.toString();
        rules = rules.substring(0, rules.length() - SEPARATOR.length());
        Date startedAt = runModel.getStartedAt();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
        String strDate = formatter.format(startedAt);

        if (templateName != null) {
            tvRunTemplateName.setText(templateName);
        }
        arn.setText(runModel.getArn());
        status.setText(runModel.getState());
        start.setText(strDate);
        duration.setText(Integer.toString(runModel.getDurationInSeconds() / 60) + " mins");
        tvRules.setText(rules);

        if (findingsCount > 0) {

            listView = view.findViewById(R.id.finding_list_view);
            AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentials());
            dynamoDBMapper = DynamoDBMapper.builder()
                    .dynamoDBClient(dynamoDBClient)
                    .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                    .build();

            SpannableString spannableString = new SpannableString("findings: " + Integer.toString(findingsCount));

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {

                    String id = runModel.getArn().split(":")[4];
                    InspectorModel inspectorModel = dynamoDBMapper.load(InspectorModel.class, id);
                    List<FindingModel> findingModelList = inspectorModel.getFindings().stream().filter(
                            findingModel ->
                                    findingModel.getArn().contains(runModel.getArn())
                    ).collect(Collectors.toList());

                    FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    FindingListFragment findingListFragment = new FindingListFragment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("findingModels", new ArrayList<FindingModel>(findingModelList));
                    findingListFragment.setArguments(bundle);

                    ft.replace(R.id.main_screen, findingListFragment);
                    ft.addToBackStack(null);
                    ft.commit();

                    Toast.makeText(getActivity(), "Number of findings " + findingModelList.size(), Toast.LENGTH_LONG).show();
                }
            };

            spannableString.setSpan(clickableSpan, 0, 10 + String.valueOf(findingsCount).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvFindings.setText(spannableString);
            tvFindings.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            tvFindings.setText(Integer.toString(findingsCount) + " findings");
        }



        return view;
    }

}
