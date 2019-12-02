package com.example.danilo.myapplicationmobilehub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amazonaws.mobile.api.idzt9jftjm4c.model.InspectorModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.RunModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TemplateModel;
import com.example.danilo.myapplicationmobilehub.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RunListAdapter extends ArrayAdapter<RunModel> {

    private Context context;

    int mResource;

    InspectorModel mInspectorModel;

    public RunListAdapter(FragmentActivity context, int resource, List<RunModel> objects, InspectorModel inspectorModel) {
        super(context, resource, objects);

        this.context = context;
        this.mResource = resource;
        this.mInspectorModel = inspectorModel;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String[] runArnArray = getItem(position).getArn().split("/");
        String runId = runArnArray[runArnArray.length - 1];
        String status = getItem(position).getState();
        Integer duration = getItem(position).getDurationInSeconds() / 60;
        Date startedAt = getItem(position).getStartedAt();
        Date completedAt = getItem(position).getCompletedAt();
        Map<String, Integer> findings = getItem(position).getFindingCounts();
        String templateArn = getItem(position).getAssessmentTemplateRun();
        String templateName = null;
        for(TemplateModel templateModel : mInspectorModel.getTemplates()) {
            if(templateModel.getArn().equals(templateArn)) {
                templateName = templateModel.getName();
                break;
            }
        }

        Integer findingsCount = 0;

        if (findings != null) {
            for (Integer value : findings.values()) {
                findingsCount += value;
            }
        }

        String completedDate = "";
        String strDate = null;
        if (completedAt != null) {
            completedDate = completedAt.toString();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
            strDate = formatter.format(completedAt);
        }
        else
            completedDate = "Not completed yet";

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTemplateName = (TextView) convertView.findViewById(R.id.textView1Run);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.textView4Run);
        TextView tvFindings = (TextView) convertView.findViewById(R.id.textView5Run);
//        TextView tvCompletedAt = (TextView) convertView.findViewById(R.id.textView4Run);
        TextView tvStardDate = (TextView) convertView.findViewById(R.id.textView2Run);
        TextView tvDuration = (TextView) convertView.findViewById(R.id.textView3Run);



        if(templateName != null)
            tvTemplateName.setText(templateName);
        tvStatus.setText(status);
        tvFindings.setText(Integer.toString(findingsCount) + " findings");
        if(strDate != null)
            tvDuration.setText(Integer.toString(duration) + " mins");
        else
            tvDuration.setText(Integer.toString(duration) + " mins");

        tvStardDate.setText(strDate);

        return convertView;
    }
}
