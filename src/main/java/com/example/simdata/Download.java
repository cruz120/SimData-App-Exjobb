package com.example.simdata;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.simdata.room.database.db.SwimpoolDatabase;
import com.example.simdata.room.database.db.entity.Activitytype;
import com.example.simdata.room.database.db.entity.Location;
import com.example.simdata.room.database.db.entity.Swimpool;
import com.example.simdata.room.database.db.entity.Template;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Download {

    ArrayList<String> allId;
    SharedPreferences prefs;
    private RequestQueue mQueue;
    private Context context;
    private String accessToken;
    private Application application;
    private String idToken;
    private static final String ID_TOKEN_PARAM = "GCP_IAAP_AUTH_TOKEN";
    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUALS = "=";
    public Download(String accessToken,String idToken, Context context, Application application)
    {
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.context = context;
        this.application = application;
     //   SwimpoolDatabase database = SwimpoolDatabase.getInstance(context);
    }

    public void getLocation(){


        mQueue = Volley.newRequestQueue(context);

        String url = "https://simapi-dot-student-projekt.appspot.com/location?access_token" + EQUALS + accessToken;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject jsonLocationId = response.getJSONObject("location");
                            int location_id = jsonLocationId.getInt("id");
                            JSONObject jsonLocationName = response.getJSONObject("location");
                            String name = jsonLocationName.getString("name");

                            SwimmingPoolDataRepository swimmingPoolDataRepository = new SwimmingPoolDataRepository(application);
                            Location location = new Location();
                            location.setId(location_id);
                            location.setName(name);
                            swimmingPoolDataRepository.addLocation(location);
/*
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putLong("location_id", expireDate);
                            editor.putString("accessToken", accessToken);
                            editor.apply();   */
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
                headers.put("cookie",ID_TOKEN_PARAM+ EQUALS + idToken);

                return headers;
            }
        };
  //      request.setRetryPolicy(new DefaultRetryPolicy( INITIAL_VALUE, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);


    }

    public void getSwimPool(){

        mQueue = Volley.newRequestQueue(context);

        String url = "https://simapi-dot-student-projekt.appspot.com/swimpools?access_token" + EQUALS + accessToken;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // allId = new ArrayList<>();
                        try {


                            JSONArray jsonSwimPoolArr = response.getJSONArray("swimpools");
                            for(int i = 0; i < jsonSwimPoolArr.length(); i++){

                                JSONObject swimPoolData = jsonSwimPoolArr.getJSONObject(i);

                                int id = swimPoolData.getInt("id");
                                String name = swimPoolData.getString("name");
                              //  allId.add(swimPoolData.getString("id"));

                                int location_id = swimPoolData.getInt("location_id");
                                double rect_left = swimPoolData.getDouble("rect_left");
                                double rect_top = swimPoolData.getDouble("rect_top");
                                double rect_right = swimPoolData.getDouble("rect_right");
                                double rect_bottom = swimPoolData.getDouble("rect_bottom");

                                SwimmingPoolDataRepository swimmingPoolDataRepository = new SwimmingPoolDataRepository(application);
                                Swimpool swimpool = new Swimpool();
                                swimpool.setId(id);
                                swimpool.setName(name);
                                swimpool.setLocation_id(location_id);
                                swimpool.setRect_left(rect_left);
                                swimpool.setRect_top(rect_top);
                                swimpool.setRect_right(rect_right);
                                swimpool.setRect_bottom(rect_bottom);
                                swimmingPoolDataRepository.addSwimpool(swimpool);

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
                headers.put("cookie",ID_TOKEN_PARAM+ EQUALS + idToken);

                return headers;
            }
        };
    //    request.setRetryPolicy(new DefaultRetryPolicy( INITIAL_VALUE_2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }

    public void getTemplates(String swimpoolId){

        mQueue = Volley.newRequestQueue(context);

        String url = "https://simapi-dot-student-projekt.appspot.com/templates?swimpool_id"+EQUALS + swimpoolId +"&access_token=" + accessToken;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray jsonTemplateArray = response.getJSONArray("templates");
                            for(int i = 0; i < jsonTemplateArray.length(); i++){
                                JSONObject partition = jsonTemplateArray.getJSONObject(i);

                                int id = partition.getInt("id");
                                String name = partition.getString("name");
                                int swimpool_id = partition.getInt("swimpool_id");

                                System.out.println(id);
                                System.out.println(name);
                                System.out.println(swimpool_id);
                                JSONArray jsonPartitionArray = partition.getJSONArray("partitioning");

                                Template template = new Template();
                                template.setId(id);
                                template.setName(name);
                                template.setSwimpool_id(swimpool_id);
                                template.setPartition(jsonPartitionArray.toString());
                                SwimmingPoolDataRepository swimmingPoolDataRepository = new SwimmingPoolDataRepository(application);
                                swimmingPoolDataRepository.addTemplate(template);

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
                headers.put("cookie",ID_TOKEN_PARAM+ EQUALS + idToken);

                return headers;
            }
        };
     //   request.setRetryPolicy(new DefaultRetryPolicy( INITIAL_VALUE_3, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }

    public void getActivityType(){


        mQueue = Volley.newRequestQueue(context);

        String url = "https://simapi-dot-student-projekt.appspot.com/activitytypes?access_token" + EQUALS + accessToken;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray jsonActivityTypeArray = response.getJSONArray("activitytypes");
                            for(int i = 0; i < jsonActivityTypeArray.length(); i++){
                                JSONObject activitytypes = jsonActivityTypeArray.getJSONObject(i);

                                int id = activitytypes.getInt("id");
                                String name = activitytypes.getString("name");
                                String short_name = activitytypes.getString("short_name");

                                System.out.println(id);
                                System.out.println(name);
                                System.out.println(short_name);

                                Activitytype activitytype = new Activitytype();
                                activitytype.setId(id);
                                activitytype.setName(name);
                                activitytype.setShort_name(short_name);
                                ActivityTypeRepository activityTypeRepository = new ActivityTypeRepository(application);
                                activityTypeRepository.addActivityType(activitytype);

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
        /* (non-Javadoc)
         * @see com.android.volley.Request#getHeaders()
         */
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
           // Map<String, String> headers = new HashMap<String, String>();
            //headers.put("cookie",COOKIE);
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("cookie",ID_TOKEN_PARAM+ EQUALS + idToken);
            return headers;
        }

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            // since we don't know which of the two underlying network vehicles
            // will Volley use, we have to handle and store session cookies manually
            // checkSessionCookie(response.headers);


            return super.parseNetworkResponse(response);
        }

        };
     //  request.setRetryPolicy(new DefaultRetryPolicy( INITIAL_VALUE_4, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);


    }



    private static double roundDownOne(double d) {
        return ((long)(d * 1e2)) / 1e2;
        //Long typecast will remove the decimals
    }
    private void saveArrayList(ArrayList<String> list , String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }
}
