package com.example.simdata.ui.reports;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.simdata.LoginView;
import com.example.simdata.MainMenuActivity;
import com.example.simdata.R;
import com.example.simdata.Reports;
import com.example.simdata.Swimlocation;
import com.example.simdata.SwimmingPoolDataRepository;
import com.example.simdata.UpdateData;
import com.example.simdata.room.database.db.entity.AttendanceReport;
import com.example.simdata.room.database.db.entity.Swimpool;
import com.example.simdata.ui.swimlocation.SwimlocationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsFragment extends Fragment {
    private static final String EQUALS = "=";
    private ReportsViewModel mViewModel;
    private SwimlocationViewModel sViewModel;
    ListView AllReportList;
    private static final String ID_TOKEN_PARAM = "GCP_IAAP_AUTH_TOKEN";



    public static ReportsFragment newInstance() {
        return new ReportsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.reports_fragment, container, false);

        AllReportList = (ListView) view.findViewById(R.id.listview_allReports);

        String reportList[];
        mViewModel = ViewModelProviders.of(this).get(ReportsViewModel.class);
        List<AttendanceReport> allReports = mViewModel.getAllReports();

        List<Swimpool> allSwimpool = mViewModel.getAllSwimmingPoolsNames();


        if (allReports.isEmpty()) {
            reportList = new String[1];
            reportList[0] = " ";
        } else {

            reportList = new String[allReports.size()];


            for (int i = 0; i < allReports.size(); i++) {
                int id = allReports.get(i).getSwimpool_id();
                for (int j = 0; j < allSwimpool.size(); j++) {
                    if (id == allSwimpool.get(j).getId()) {
                        reportList[i] = i + 1 + "      " + allSwimpool.get(j).getName() + "     " + allReports.get(i).getReporttime();

                    }
                }
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.reports_activity, R.id.textViewAllRep, reportList);
            AllReportList.setAdapter(arrayAdapter);
        }

        Button uploadBtn = (Button) view.findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mViewModel.getAllReports() != null)
                uploadData();
            }
        });

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel

    }

    public void uploadData() {


        String url2 = "https://simapi-dot-student-projekt.appspot.com/reports?access_token=";
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        String ACCESS_TOKEN = settings.getString("accessToken", "defValue");
        new PostRequestAsyncTask().execute(url2+ACCESS_TOKEN);

    }

    private class PostRequestAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            //pd = ProgressDialog.show(LoginView.this, "", LoginView.this.getString(R.string.loading),true);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            if (urls.length > 0) {
                String url = urls[0];
try{
    final List<AttendanceReport> allReports = mViewModel.getAllReports();
    JSONObject reportJSON = new JSONObject();

       // for (int i = 0; i < allReports.size(); i++) {

            reportJSON.put("report_time", allReports.get(0).getReporttime());
            reportJSON.put("swimpool_id", allReports.get(0).getSwimpool_id());
            String temp = allReports.get(0).getPartitionResult();
            JSONArray obj = new JSONArray(temp);
            reportJSON.put("report_data", obj);

       // }

    System.out.println("JSON OBJECTS  : " + reportJSON.toString(0));

                RequestQueue queue = Volley.newRequestQueue(getContext());


    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url,reportJSON , new Response.Listener<JSONObject>() {


                            @Override
                            public void onResponse(JSONObject response) {
                                // textView.setText("Response: " + response.toString());
                                    int id = allReports.get(0).getId();
                                    System.out.println("ID : "+id);
                                    Toast.makeText(getContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();

                                    if(response.toString() == "{}" || response.toString().contains("A report with swimpool id" )){
                                       // System.out.println("ID 2 : "+id);
                                          AttendanceReport attendanceReport = new AttendanceReport();
                                          attendanceReport.setId(id);
                                          mViewModel.deleteAttReport(attendanceReport);
                                    }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                error.printStackTrace();
                                Log.e("VOLLEY", error.toString());

                            }
                        }

                        ) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                        String ID_TOKEN = settings.getString("idToken","defValue");

                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("cookie",ID_TOKEN_PARAM + EQUALS + ID_TOKEN);
                        headers.put("Content-Type", "application/json");

                        return headers;
                    }
                };
                //  jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( INITIAL_VALUE, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                queue.add(jsonObjectRequest);
} catch (JSONException e) {
    e.printStackTrace();
}
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean status) {

          //  if (pd != null && pd.isShowing()) { pd.dismiss(); }

            if (status) {

                Intent i = new Intent(getActivity(), MainMenuActivity.class);
                startActivity(i);

            }

        }

    }
}
