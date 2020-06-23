package com.example.simdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.simdata.ui.swimlocation.SwimlocationFragment;

public class Swimlocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swimlocation_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SwimlocationFragment.newInstance())
                    .commitNow();
        }
    }
}
