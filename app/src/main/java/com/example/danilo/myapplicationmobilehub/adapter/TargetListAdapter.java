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

import com.amazonaws.mobile.api.idzt9jftjm4c.model.TargetModel;
import com.example.danilo.myapplicationmobilehub.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TargetListAdapter extends ArrayAdapter<TargetModel> {

    private Context context;

    int mResource;


    public TargetListAdapter(FragmentActivity context, int resource, List<TargetModel> objects) {
        super(context, resource, objects);

        this.context = context;
        this.mResource = resource;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).getName();
        String arn = getItem(position).getArn();
        Date created = getItem(position).getCreatedAt();

        TargetModel targetModel = new TargetModel();
        targetModel.setArn(arn);
        targetModel.setName(name);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource, parent, false);

        //holder= new RecyclerView.ViewHolder();
        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvArn = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textView3);
//            holder.= (TextView) convertView.findViewById(R.id.textView3);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
        String strDate = formatter.format(created);

        tvName.setText(name);
        tvArn.setText(arn);
        tvDate.setText(strDate);

        return convertView;
    }
}
