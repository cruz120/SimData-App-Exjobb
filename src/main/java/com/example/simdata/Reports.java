package com.example.simdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.simdata.ui.reports.ReportsFragment;

public class Reports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ReportsFragment.newInstance())
                    .commitNow();
        }
    }
}
