package com.example.simdata;

import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.simdata.room.database.db.entity.Activitytype;
import com.example.simdata.room.database.db.entity.AttendanceReport;
import com.example.simdata.room.database.db.entity.Template;
import com.example.simdata.ui.swimpoolpartition.SwimpoolPartitionFragment;
import com.example.simdata.ui.swimpoolpartition.SwimpoolPartitionViewModel;
import com.example.simdata.ui.swimpoolreports.SwimpoolReportsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwimpoolPartition extends AppCompatActivity {
    private GradientDrawable drawable;
    private SwimpoolPartitionViewModel swimpoolPartitionViewModel;
    int swimmingPoolId;
    private String pickedSwimmingPoolName;
    private EditText dateTime;

    EditText resultText;
    //private ArrayList<String> reportDataHolder = new ArrayList();
    public  String [] reportDataHolder = null;
    public  JSONArray JSONTemplateArr;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swimpool_partition_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SwimpoolPartitionFragment.newInstance())
                    .commitNow();
        }
        swimpoolPartitionViewModel = ViewModelProviders.of(this).get(SwimpoolPartitionViewModel.class);


        //PICKED SWIMMING POOL ID AND NAME
        swimmingPoolId = getIntent().getIntExtra("swimmingPoolId",0);
        pickedSwimmingPoolName = getIntent().getStringExtra("swimmingPoolName");
        System.out.println("FUCKED:::::: "+swimmingPoolId + pickedSwimmingPoolName);


        //PICKED TEMPLATE ID
        final int TemplateId =  getIntent().getIntExtra("EXTRA_TEMPLATE_ID",0);

        dateTime = (EditText) findViewById(R.id.dateTimeInput);
        LocalDateTime myDateObj =  LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        dateTime.setText(formattedDate);

        Button templateBtn = (Button) findViewById(R.id.chooseTemplate);
        templateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 openTemplateActivity();
            }
        });
        Button registerBtn = (Button) findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(reportDataHolder != null && JSONTemplateArr != null ) {
                    for (String val : reportDataHolder) {
                        System.out.println("::::::VÄRDE FROM REPORTDATAHOLDER: "+val);
                    }
                    System.out.println("ARRAYFORM"+Arrays.toString(reportDataHolder));

                    System.out.println ("PRINTING OUT JSON DATA: " + JSONTemplateArr.toString());
                    try {

                        for (int i = 0; i < JSONTemplateArr.length(); i++) {

                            if (reportDataHolder[i] != null) {
                                String t = reportDataHolder[i];
                                String [] data = t.split("-");
                                if(data.length > 1) {

                                    JSONTemplateArr.getJSONObject(i).put("attendees",Integer.parseInt(data[1]));
                                    JSONTemplateArr.getJSONObject(i).put("activitytype", Integer.parseInt(data[0]));

                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    AttendanceReport attendanceReport = new AttendanceReport();
                    attendanceReport.setReporttime(dateTime.getText().toString());
                    attendanceReport.setSwimpool_id(swimmingPoolId);
                    attendanceReport.setPartitionResult(JSONTemplateArr.toString());
                    swimpoolPartitionViewModel.addAttRep(attendanceReport);
                    Intent intent = new Intent(getApplicationContext(), SwimpoolReports.class);
                    intent.putExtra("swimmingPoolId", swimmingPoolId);
                    intent.putExtra("swimmingPoolName", pickedSwimmingPoolName);
                    startActivity(intent);

                }

            }
        });


        if(TemplateId > 0){
           System.out.println("TEMPLATE_ID_PICK: "+TemplateId);


           swimpoolPartitionViewModel.getShowTemplateId(TemplateId).observe(this, new Observer<List<Template>>() {
               @Override
               public void onChanged(@Nullable List<Template> templates) {


                     try {
                       JSONTemplateArr = new JSONArray(templates.get(0).getPartition());

                       for (int i = 0; i < JSONTemplateArr.length(); i++) {

                           JSONObject templatesValues = JSONTemplateArr.getJSONObject(i);
                           double left = roundDownOne(templatesValues.getDouble("left"));
                           double top = roundDownOne(templatesValues.getDouble("top"));
                           double right = roundDownOne(templatesValues.getDouble("right"));
                           double bottom = roundDownOne(templatesValues.getDouble("bottom"));

                           showSelectedPoolPartition(left,top,right,bottom,0,i,JSONTemplateArr.length());

                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           });

        }




    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),SwimpoolReports.class);
        intent.putExtra("swimmingPoolId", swimmingPoolId);
        intent.putExtra("swimmingPoolName", pickedSwimmingPoolName);
        startActivityForResult(intent,0);
        return  true;
    }

    public void openTemplateActivity () {
        Intent intent = new Intent(SwimpoolPartition.this, Templates.class);
        intent.putExtra("swimmingPoolId", swimmingPoolId);
        intent.putExtra("swimmingPoolName", pickedSwimmingPoolName);
        startActivity(intent);
    }

    private void showSelectedPoolPartition(double left, double top, double right, double bottom, int scale, final int id, final int sizeArr){

        int size = 500;
        drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(Color.CYAN);
        drawable.setStroke(3, Color.BLACK);

        FrameLayout fl = (FrameLayout) findViewById(R.id.EditTemplate);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        int width = (int) (size*(right - left));
        int height = (int) (size*(bottom - top));
        int mStart = (int) (size*left);
        int mTop = (int) (size*top);


        final Button btn = new Button(this);
        btn.setId(id);
        btn.setWidth(width);
        btn.setHeight(height);
        btn.setBackground(drawable);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Log.i("TAG", "The index is" + index);
                Toast.makeText(v.getContext(), "Part:" + id, Toast.LENGTH_SHORT).show();
                //sendPickedTemplateToEdit(id);
                showInputDialog(btn,id,sizeArr);

            }
        });

        params.setMarginStart(mStart);
        params.setMargins(0,mTop+scale,0,0);
        btn.setLayoutParams(params);

        fl.addView(btn);

    }
    public static double roundDownOne(double d) {
        return ((long)(d * 1e2)) / 1e2;
        //Long typecast will remove the decimals
    }

    public void showInputDialog(final Button btn, final int idBtn, final int sizeArr) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.popup, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        final EditText antal = (EditText) promptView.findViewById(R.id.edittext);
        final Spinner actSpinner = (Spinner) promptView.findViewById(R.id.spinner);
        // setup a dialog window

        ActivityTypeRepository activityTypeRepository = new ActivityTypeRepository(getApplication());
        List<Activitytype> activitytypes = activityTypeRepository.getActivityType();

        String [] atNames = new String [activitytypes.size()];
    if(activitytypes != null) {

        for (int i = 0; i < activitytypes.size(); i++) {

            atNames[i] = activitytypes.get(i).getName();

        }
    }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,atNames);
        actSpinner.setAdapter(adapter);


        alertDialogBuilder.setCancelable(false).setPositiveButton("Lägg till", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


        //  Toast.makeText(getApplicationContext(), "Lagt till: " + act.getText().toString() + "/"+ antal.getText().toString(), Toast.LENGTH_SHORT).show();

          btn.setText(actSpinner.getSelectedItem().toString() +" / "+antal.getText().toString());

                       // reportDataHolder = new ArrayList(sizeArr);
                        String activityType = Integer.toString(actSpinner.getSelectedItemPosition()+1);
                        String quantity = antal.getText().toString();

                        if(reportDataHolder == null){
                            reportDataHolder = new String[sizeArr];

                        }
                        reportDataHolder[idBtn] = activityType+"-"+quantity;
                        /*
                        if(reportDataHolder != null) {
                            for (String val : reportDataHolder) {
                                System.out.println("::::::VÄRDE FROM REPORTDATAHOLDER: "+val);
                            }
                        }

                        */
                    }
                })
                .setNegativeButton("Avbryt",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


}
