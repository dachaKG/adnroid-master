package com.example.danilo.myapplicationmobilehub.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazonaws.mobile.api.idzt9jftjm4c.model.TemplateModel;
import com.example.danilo.myapplicationmobilehub.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemplateFragment extends Fragment {

    private static final String SEPARATOR = "\n";
    public TemplateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_template, container, false);

        Bundle bundle = getArguments();
        TemplateModel templateModel = (TemplateModel) bundle.getSerializable("TemplateModel");

        TextView templateName = view.findViewById(R.id.templateName);
        TextView templateArn = view.findViewById(R.id.templateArn);
        TextView targetName = view.findViewById(R.id.templateTargetName);
        TextView createdStr = view.findViewById(R.id.templateCreatedAt);
        TextView duration = view.findViewById(R.id.templateDuration);
        TextView runCount = view.findViewById(R.id.templateRunCount);
        TextView templateRules = view.findViewById(R.id.templateRules);

        Integer durationMin = templateModel.getDurationInSeconds() / 60;
        Integer runCountInt = templateModel.getAssessmentRunCount();

        StringBuilder stringBuilder = new StringBuilder();

        for(String rule : templateModel.getRulesPackagesArns()){

            stringBuilder.append(rule);

            stringBuilder.append(SEPARATOR);

        }

        String rules = stringBuilder.toString();
        rules = rules.substring(0, rules.length() - SEPARATOR.length());

        Date createdAt = templateModel.getCreatedAt();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
        String strDate = formatter.format(createdAt);

        templateName.setText(templateModel.getName());
        templateArn.setText(templateModel.getArn());
        targetName.setText(templateModel.getAssessmentTargetName());
        createdStr.setText(strDate);
        duration.setText(Integer.toString(durationMin) + " mins");
        runCount.setText(Integer.toString(runCountInt) + " runs");
        templateRules.setText(rules);
        return view;
    }

}
