package com.example.danilo.myapplicationmobilehub.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amazonaws.mobile.api.idzt9jftjm4c.model.FindingModel;
import com.example.danilo.myapplicationmobilehub.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FindingListAdapter extends ArrayAdapter<FindingModel> {

    private Context context;

    int mResource;

    public FindingListAdapter(FragmentActivity context, int resource, List<FindingModel> objects) {
        super(context, resource, objects);

        this.context = context;
        this.mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String severity = getItem(position).getSeverity();
        Date createdAt = getItem(position).getCreatedAt();
        String finding = getItem(position).getTitle();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvSeverity = (TextView) convertView.findViewById(R.id.textViewSeverity);
        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.textViewCreated);
        TextView tvFindings = (TextView) convertView.findViewById(R.id.textViewFinding);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
        String strDate = formatter.format(createdAt);
        switch (severity.toLowerCase()) {
            case "high":
                tvSeverity.setTextColor(parent.getResources().getColor(R.color.darkRed));
                break;
            case "low":
                tvSeverity.setTextColor(parent.getResources().getColor(R.color.lowGreen));
                break;
            case "medium":
                tvSeverity.setTextColor(parent.getResources().getColor(R.color.yellow));
                break;
            case "informational":
                tvSeverity.setTextColor(parent.getResources().getColor(R.color.informationBlue));
                break;
        }
        tvSeverity.setText(severity);
        tvCreatedAt.setText(strDate);
        tvFindings.setText(finding);

        return convertView;
    }
}
