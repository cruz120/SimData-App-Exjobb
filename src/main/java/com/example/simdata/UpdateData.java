package com.example.simdata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.simdata.room.database.db.entity.Swimpool;

import java.util.List;

public class UpdateData extends AppCompatActivity {
    final static int TIME = 7100;
    private int i = 0;
    private ProgressBar progressBar;
    private TextView txtView;
    boolean success = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtView = (TextView) findViewById(R.id.tView);

        //  i = progressBar.getProgress();

        update();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(i);
                System.out.println("INNANFÖR RUN - ANDRA");
                startActivity();
            }
        }, TIME);


    }

    public void update(){

            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String ACCESS_TOKEN = settings.getString("accessToken", "defValue");
            String ID_TOKEN = settings.getString("idToken","defValue");
            Download startDownload = new Download(ACCESS_TOKEN, ID_TOKEN, getApplicationContext(), getApplication());
            startDownload.getLocation();

            startDownload.getSwimPool();

            Download startDownload_3 = new Download(ACCESS_TOKEN,ID_TOKEN, getApplicationContext(), getApplication());
            startDownload_3.getActivityType();

            SwimmingPoolDataRepository data = new SwimmingPoolDataRepository(getApplication());
            List<Swimpool> swimpools = data.getSwimpool();
            Download newStartDownload = new Download(ACCESS_TOKEN,ID_TOKEN,getApplicationContext(),getApplication());
            for (Swimpool id : swimpools) {
                newStartDownload.getTemplates(Integer.toString(id.getId()));
            }


    }

    public void startActivity(){
        System.out.println("INNANFÖR STARTACTIVITY FÖRSTA");
        Intent intent = new Intent(this,MainMenuActivity.class);
        startActivity(intent);
        finish();

    }
}
