package com.example.simdata.ui.swimpoolreports;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simdata.R;
import com.example.simdata.SwimpoolPartition;
import com.example.simdata.SwimpoolReports;
import com.example.simdata.room.database.db.entity.AttendanceReport;
import com.example.simdata.room.database.db.entity.Swimpool;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SwimpoolReportsFragment extends Fragment {

    FloatingActionButton addEntryBtn;
    TextView swimmingPoolName;
    SwimpoolReportsViewModel swimpoolReportsViewModel;
    private SwimpoolReportsViewModel mViewModel;
    int swimmingPoolId;
    String pickedSwimmingPoolName;
    ListView reportList;


    public static SwimpoolReportsFragment newInstance() {
        return new SwimpoolReportsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swimpool_reports_fragment, container, false);

        swimmingPoolId = getActivity().getIntent().getIntExtra("swimmingPoolId",0);
        pickedSwimmingPoolName = getActivity().getIntent().getStringExtra("swimmingPoolName");

        swimmingPoolName = (TextView)view.findViewById(R.id.swimmingPoolName);
        swimmingPoolName.setTextSize(20);
        swimmingPoolName.setText(pickedSwimmingPoolName);

        if(swimmingPoolId > 0){
            System.out.println("swimmingPoolId: "+swimmingPoolId);

        }

        reportList = (ListView)view.findViewById(R.id.listview_reports);

        swimpoolReportsViewModel = ViewModelProviders.of(this).get(SwimpoolReportsViewModel.class);
        swimpoolReportsViewModel.getSwimmingPoolReports(swimmingPoolId).observe(this, new Observer<List<AttendanceReport>>() {
            @Override
            public void onChanged(@Nullable List<AttendanceReport> attendanceReports) {
                String attendanceReportList[];
                if(attendanceReports.isEmpty()){
                    attendanceReportList = new String [1];
                    attendanceReportList[0] = " ";
                }else {

                    attendanceReportList = new String [attendanceReports.size()];

                    for (int i = 0; i < attendanceReports.size(); i++) {
                        attendanceReportList[i] = i+1 +"      "+  attendanceReports.get(i).getReporttime();
                        }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.swimpool_reports_activity, R.id.textViewRep, attendanceReportList);
                    reportList.setAdapter(arrayAdapter);
                }


            }
        });


        addEntryBtn = (FloatingActionButton) view.findViewById(R.id.addEntryButton);
        addEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SwimpoolPartition.class);
                intent.putExtra("swimmingPoolId",swimmingPoolId);
                intent.putExtra("swimmingPoolName",pickedSwimmingPoolName);
                startActivityForResult(intent,1);
            }
        });


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SwimpoolReportsViewModel.class);
        // TODO: Use the ViewModel


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);


                if(resultCode == RESULT_OK){

                    swimmingPoolId = data.getIntExtra("swimmingPoolId",0);
                    String pickedSwimmingPoolName = data.getStringExtra("swimmingPoolName");

                    swimmingPoolName.setTextSize(20);
                    swimmingPoolName.setText(pickedSwimmingPoolName);
                    // txtfavouratecount.setText(sSuName);
                }





        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
