package com.example.danilo.myapplicationmobilehub.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.api.idzt9jftjm4c.model.FindingModel;
import com.example.danilo.myapplicationmobilehub.R;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindingFragment extends Fragment {


    public FindingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_finding, container, false);

        Bundle bundle = getArguments();
        FindingModel findingModel = (FindingModel) bundle.getSerializable("FindingModel");

        TextView arn = view.findViewById(R.id.textViewArnFinding);
        TextView createdAt = view.findViewById(R.id.textViewStartFinding);
        TextView title = view.findViewById(R.id.textViewTitleFinding);
        TextView severity = view.findViewById(R.id.textViewSeverityFinding);
        Button buttonDesc = view.findViewById(R.id.button_desc);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
        String strDate = formatter.format(findingModel.getCreatedAt());

        buttonDesc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create(); //Read Update
                alertDialog.setTitle("Description");
                alertDialog.setMessage(findingModel.getDescription());

                alertDialog.show();
            }

        });

        Button buttonRecommendation = view.findViewById(R.id.button_recomm);
        buttonRecommendation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create(); //Read Update
                alertDialog.setTitle("Recommendation");
                alertDialog.setMessage(findingModel.getRecommendation());

                alertDialog.show();
            }

        });

        arn.setText(findingModel.getArn());
        createdAt.setText(strDate);
        title.setText(findingModel.getTitle());
        switch (findingModel.getSeverity().toLowerCase()) {
            case "high":
                severity.setTextColor(getActivity().getColor(R.color.darkRed));
                break;
            case "low":
                severity.setTextColor(getActivity().getColor(R.color.lowGreen));
                break;
            case "medium":
                severity.setTextColor(getActivity().getColor(R.color.yellow));
                break;
            case "informational":
                severity.setTextColor(getActivity().getColor(R.color.informationBlue));
                break;
        }
        severity.setText(findingModel.getSeverity());

        return view;
    }

}
