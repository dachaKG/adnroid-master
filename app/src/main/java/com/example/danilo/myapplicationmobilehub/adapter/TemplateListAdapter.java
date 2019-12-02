package com.example.danilo.myapplicationmobilehub.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amazonaws.mobile.api.idzt9jftjm4c.model.TargetModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TemplateModel;
import com.example.danilo.myapplicationmobilehub.R;

import java.util.Date;
import java.util.List;

public class TemplateListAdapter extends ArrayAdapter<TemplateModel> {

    private Context context;

    int mResource;

    public TemplateListAdapter(FragmentActivity context, int resource, List<TemplateModel> objects) {
        super(context, resource, objects);

        this.context = context;
        this.mResource = resource;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).getName();
//        String arn = getItem(position).getArn();
        String assessmentTargetName = getItem(position).getAssessmentTargetName();
        Integer duration = getItem(position).getDurationInSeconds() / 60;
        Integer runCount = getItem(position).getAssessmentRunCount();


//        if(duration != null)
//            duration = duration / 60;
//        TemplateModel targetModel = new TemplateModel();
//        targetModel.setArn(arn);
//        targetModel.setName(name);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource, parent, false);

        //holder= new RecyclerView.ViewHolder();
        TextView tvName = (TextView) convertView.findViewById(R.id.textView1Temp);
//        TextView tvArn = (TextView) convertView.findViewById(R.id.textView2Temp);
        TextView tvTargetName = (TextView) convertView.findViewById(R.id.textViewTargetName);
        TextView tvDuration = (TextView) convertView.findViewById(R.id.textView3Temp);
        TextView tvRunCount = (TextView) convertView.findViewById(R.id.textView4Temp);
//        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.textView5Temp);

        tvName.setText(name);
//        tvArn.setText(arn);
        tvTargetName.setText(assessmentTargetName);
        tvDuration.setText(Integer.toString(duration) + " mins");
        tvRunCount.setText(Integer.toString(runCount) + " runs");
//        tvCreatedAt.setText(created.toString());

        return convertView;
    }
}
