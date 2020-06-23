package com.example.simdata;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.simdata.room.database.db.entity.Template;
import com.example.simdata.ui.swimpoolpartition.SwimpoolPartitionViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Templates extends AppCompatActivity {
    private static Template _instance;
    private static final String TAG = "";
    private GradientDrawable drawable;
    private RequestQueue mQueue;
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    private SharedPreferences _preferences;
    private SwimpoolPartitionViewModel swimpoolPartitionViewModel;
    int swimmingPoolId;
    String pickedSwimmingPoolName;
    public static Template get() {
        return _instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);
        mQueue = Volley.newRequestQueue(this);
       // showAndDownloadTemplates();
          showAllTemplates();





   }


   private void showAllTemplates(){

       swimmingPoolId = getIntent().getIntExtra("swimmingPoolId",0);
       pickedSwimmingPoolName = getIntent().getStringExtra("swimmingPoolName");
       System.out.println("FUCKED IN TEMPLATES:::::: "+swimmingPoolId + pickedSwimmingPoolName);

       swimpoolPartitionViewModel = ViewModelProviders.of(this).get(SwimpoolPartitionViewModel.class);

       swimpoolPartitionViewModel.getGetTemplate(swimmingPoolId).observe(this, new Observer<List<Template>>() {
           @Override
           public void onChanged(@Nullable List<Template> templates) {


              // for(int i = 0; i < templates.size(); i++){
                  // System.out.println("GET TEMPLATES FROM "+ pickedSwimmingPoolName+ ":"+templates.get(i).getPartition());
    // }
               int scale = 10;
               try {
                   // jsonString is a string variable that holds the JSON
                    for(int i = 0; i < templates.size(); i++){
                              //  System.out.println("GET TEMPLATES FROM "+ pickedSwimmingPoolName+ ":"+templates.get(i).getPartition());
                                scale = scale + 10;
                                JSONArray jsonTemaplateArr=new JSONArray(templates.get(i).getPartition());
                                for (int j = 0; j < jsonTemaplateArr.length(); j++) {
                                    JSONObject templatesValues = jsonTemaplateArr.getJSONObject(j);
                                    double left = roundDownOne(templatesValues.getDouble("left"));
                                    double top = roundDownOne(templatesValues.getDouble("top"));
                                    double right = roundDownOne(templatesValues.getDouble("right"));
                                    double bottom = roundDownOne(templatesValues.getDouble("bottom"));
                                    double pos = i;

                                    createPoolPartition(left,top+pos,right,bottom+pos,scale,templates.get(i).getId());
                   }
                    }
               } catch (JSONException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }

           }
       });


   }

   private void showAndDownloadTemplates(){
       //TEST JSON VALUES
      // String url = "https://api.myjson.com/bins/ytcbh";
       SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
       String ACCESS_TOKEN = settings.getString("accessToken","defValue");
       String url = "https://simapi-dot-student-projekt.appspot.com/templates?swimpool_id=2&access_token=" + ACCESS_TOKEN;
       System.out.println(ACCESS_TOKEN);
       System.out.println(url);
       JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
               new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
                       int scale = 10;
                       try {

                           JSONArray jsonTemplateArray = response.getJSONArray("templates");
                          for(int i = 0; i < jsonTemplateArray.length(); i++){
                             JSONObject partition = jsonTemplateArray.getJSONObject(i);

                                      scale = scale + 10;
                                  JSONArray jsonPartitionArray = partition.getJSONArray("partitioning");
                                  for(int j = 0; j < jsonPartitionArray.length(); j++) {
                                       JSONObject poolCoordinates = jsonPartitionArray.getJSONObject(j);
                                       double left = roundDownOne(poolCoordinates.getDouble("left"));
                                       double top = roundDownOne(poolCoordinates.getDouble("top"));
                                       double right = roundDownOne(poolCoordinates.getDouble("right"));
                                       double bottom = roundDownOne(poolCoordinates.getDouble("bottom"));
                                       double pos = i;

                                       createPoolPartition(left, top+pos, right, bottom+pos, scale,partition.getInt("id"));
                               }
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
           }
       }
       )
       {            /** Passing some request headers* */

       @Override
       protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
           // since we don't know which of the two underlying network vehicles
           // will Volley use, we have to handle and store session cookies manually
        // checkSessionCookie(response.headers);
           return super.parseNetworkResponse(response);
       }
           /* (non-Javadoc)
            * @see com.android.volley.Request#getHeaders()
            */
           @Override
           public Map<String, String> getHeaders() throws AuthFailureError {
               Map<String, String> headers = new HashMap<String, String>();
               headers.put("cookie","GCP_IAAP_AUTH_TOKEN_58AC943B9C26677A=eyJhbGciOiJSUzI1NiIsImtpZCI6IjJiZjg0MThiMjk2M2YzNjZmNWZlZmRkMTI3YjJjZWUwN2M4ODdlNjUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyMDYwOTI2ODU3NTUtdGFuMW92MTJkaTE2c3Y5YjU1b2Z2bjMxaTVoZjExanMuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyMDYwOTI2ODU3NTUtdGFuMW92MTJkaTE2c3Y5YjU1b2Z2bjMxaTVoZjExanMuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTEyMTI3NzI4MDkyNTU4NzY0MjQiLCJoZCI6Im1lZGxleS5zZSIsImVtYWlsIjoicmljaGFyZC5jcnV6QG1lZGxleS5zZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoienlMVXBSQW4wZkdleEwyMFVXbzFpUSIsImdvb2dsZSI6eyJnaWMiOiJBTkM1OVFwck5ya1V1c2VBbFRuXzJCaUE0TUdHQmpLYy14by1xZWpkMHdzM0pOYUc2WW5KT1lMeDZqMmFDU2JIdTA2ZWdaVEV5SFlGSVJxY0drMF9UNExsM2ZUTHI0RG5NTlB2LVlYUGllR2xJQkIzS2lzS1NVbVNZV2dZLU5jd0JYNWhHbFJxYllpbjZxNDN3ZGZ5OUFXSHFpWDF2T0RkY0FQeU0xUmczR3MxWE9hUWoyTHJ5MXhOaHVqdXhCQjRZRS1WNGhNTVJESzZYanl6WE9LZ1lsTmNfV0ZUcnpOWllGX0kybHJjMXN0YnhmZzlmNmRWNWlIM3Z3In0sImlhdCI6MTU2ODMzNzE1MSwiZXhwIjoxNTY4MzQwNzUxfQ.HfCPfs8Zten1ekOi9ZClyOxHW0NbhMR_K8TMQrh0H1XH4_4bT23zWzL5VkPjgXivNCmq5AaYTDOnp5CRJZ9nBfQb3VWqVjiFUwPO5Cha5hAaNYhjoVi0wvbL3Xx8z9Yc-JNSNy5emWbheeXqXPlx43WDQLcFom6-I6dvCdIAsqLL3lY8QFEsSLlLF54ukWeAg1-cC8lMwi3J_cfrfOQSpHAvMF-MxMWCdN9Wx_-Xsgm4rMdmBCTXmy1KP96yHJtbvcxvgdRPQFESq1t-Lz3G41cpRvlSfd8S6YJSgwX7fY6KbeK-eFnCKPKE__zN8ioV6N8xYFter3rIPtpxPKJQHw; GCP_IAAP_AUTH_TOKEN_4D49D6E09C92FAB0=eyJhbGciOiJSUzI1NiIsImtpZCI6IjJiZjg0MThiMjk2M2YzNjZmNWZlZmRkMTI3YjJjZWUwN2M4ODdlNjUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyMDYwOTI2ODU3NTUtdGFuMW92MTJkaTE2c3Y5YjU1b2Z2bjMxaTVoZjExanMuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyMDYwOTI2ODU3NTUtdGFuMW92MTJkaTE2c3Y5YjU1b2Z2bjMxaTVoZjExanMuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTEyMTI3NzI4MDkyNTU4NzY0MjQiLCJoZCI6Im1lZGxleS5zZSIsImVtYWlsIjoicmljaGFyZC5jcnV6QG1lZGxleS5zZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoienlMVXBSQW4wZkdleEwyMFVXbzFpUSIsImdvb2dsZSI6eyJnaWMiOiJBTkM1OVFwck5ya1V1c2VBbFRuXzJCaUE0TUdHQmpLYy14by1xZWpkMHdzM0pOYUc2WW5KT1lMeDZqMmFDU2JIdTA2ZWdaVEV5SFlGSVJxY0drMF9UNExsM2ZUTHI0RG5NTlB2LVlYUGllR2xJQkIzS2lzS1NVbVNZV2dZLU5jd0JYNWhHbFJxYllpbjZxNDN3ZGZ5OUFXSHFpWDF2T0RkY0FQeU0xUmczR3MxWE9hUWoyTHJ5MXhOaHVqdXhCQjRZRS1WNGhNTVJESzZYanl6WE9LZ1lsTmNfV0ZUcnpOWllGX0kybHJjMXN0YnhmZzlmNmRWNWlIM3Z3In0sImlhdCI6MTU2ODMzNzE1MSwiZXhwIjoxNTY4MzQwNzUxfQ.HfCPfs8Zten1ekOi9ZClyOxHW0NbhMR_K8TMQrh0H1XH4_4bT23zWzL5VkPjgXivNCmq5AaYTDOnp5CRJZ9nBfQb3VWqVjiFUwPO5Cha5hAaNYhjoVi0wvbL3Xx8z9Yc-JNSNy5emWbheeXqXPlx43WDQLcFom6-I6dvCdIAsqLL3lY8QFEsSLlLF54ukWeAg1-cC8lMwi3J_cfrfOQSpHAvMF-MxMWCdN9Wx_-Xsgm4rMdmBCTXmy1KP96yHJtbvcxvgdRPQFESq1t-Lz3G41cpRvlSfd8S6YJSgwX7fY6KbeK-eFnCKPKE__zN8ioV6N8xYFter3rIPtpxPKJQHw; GCP_IAP_UID=111212772809255876424");


               return headers;
           }
       };
      mQueue.add(request);
   }


   public void createPoolPartition(double left, double top, double right, double bottom, int scale, final int id){


       int size = 500;
       drawable = new GradientDrawable();
       drawable.setShape(GradientDrawable.RECTANGLE);
       drawable.setColor(Color.CYAN);
       drawable.setStroke(3, Color.BLACK);

       FrameLayout fl = (FrameLayout) findViewById(R.id.Fl);

       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
               LinearLayout.LayoutParams.WRAP_CONTENT,
               LinearLayout.LayoutParams.WRAP_CONTENT);

       int width = (int) (size*(right - left));
       int height = (int) (size*(bottom - top));
       int mStart = (int) (size*left);
       int mTop = (int) (size*top);


       Button btn = new Button(this);
       btn.setId(id);
       btn.setWidth(width);
       btn.setHeight(height);
       btn.setBackground(drawable);
       btn.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
             //  Log.i("TAG", "The index is" + index);
              Toast.makeText(v.getContext(), "Template:" + id, Toast.LENGTH_SHORT).show();
              sendPickedTemplate(id);
           }
       });

       params.setMarginStart(mStart);
       params.setMargins(0,mTop+scale,0,0);
       btn.setLayoutParams(params);

       fl.addView(btn);

   }
    public void sendPickedTemplate(int templateId) {
        Intent intent = new Intent(getBaseContext(), SwimpoolPartition.class);
        intent.putExtra("EXTRA_TEMPLATE_ID",templateId);
        intent.putExtra("swimmingPoolId",swimmingPoolId);
        intent.putExtra("swimmingPoolName",pickedSwimmingPoolName);
        startActivity(intent);
    }
    public static double roundDownOne(double d) {
        return ((long)(d * 1e2)) / 1e2;
        //Long typecast will remove the decimals
    }
}
