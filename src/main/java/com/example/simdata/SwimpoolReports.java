package com.example.simdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.simdata.ui.swimpoolreports.SwimpoolReportsFragment;

public class SwimpoolReports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swimpool_reports_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SwimpoolReportsFragment.newInstance())
                    .commitNow();
        }
getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
