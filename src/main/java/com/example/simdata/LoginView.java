package com.example.simdata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ProgressBar;

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
import com.example.simdata.room.database.db.entity.Swimpool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.simdata.room.database.db.SwimpoolDatabase.getInstance;

public class LoginView extends AppCompatActivity {



    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    /****FILL THIS WITH YOUR INFORMATION*********/
    //This is the public api key of our application
    private static final String API_KEY = "206092685755-tan1ov12di16sv9b55ofvn31i5hf11js.apps.googleusercontent.com";
    //This is the private api key of our application
    private static final String SECRET_KEY = "";
    //This is any string we want to use. This will be used for avoid CSRF attacks. You can generate one here: http://strongpasswordgenerator.com/
    private static final String STATE = "";
    //This is the url that LinkedIn Auth process will redirect to. We can put whatever we want that starts with http:// or https:// .
    //We use a made up url that we will intercept when redirecting. Avoid Uppercases.
    private static final String REDIRECT_URI = "https://simapi-dot-student-projekt.appspot.com/_gcp_gatekeeper/authenticate";
   // private static final String REDIRECT_URI = "https://simapi-dot-student-projekt.appspot.com/_gcp_gatekeeper/authenticate";
    /*********************************************/

    //These are constants used for build the urls
    private static final String AUTHORIZATION_URL = "https://accounts.google.com/o/oauth2/auth";
    private static final String ACCESS_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String SECRET_KEY_PARAM = "client_secret";
    private static final String RESPONSE_TYPE_PARAM = "response_type";
    private static final String GRANT_TYPE_PARAM = "grant_type";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String RESPONSE_TYPE_VALUE ="code";
    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String STATE_PARAM = "state";
    private static final String REDIRECT_URI_PARAM = "redirect_uri";

    private static final String SCOPE_PARAM = "scope";
    private static final String SCOPE_URL = "email openid";
    /*---------------------------------------*/
    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUALS = "=";

    private static final String USER_AGENT = "Chrome/56.0.0.0 Mobile";
    private WebView webView;
    private ProgressDialog pd;
    private static SwimpoolDatabase swimpoolDatabase;
    private static final int INITIAL_VALUE = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        webView = (WebView) findViewById(R.id.web_view);

        webView.getSettings().setUserAgentString(USER_AGENT);

        //Show a progress dialog to the user
       pd = ProgressDialog.show(this, "", this.getString(R.string.loading),true);

        webView.setWebViewClient(new WebViewClient() {



            @Override
            public void onPageFinished(WebView view, String url) {

                //This method will be executed each time a page finished loading.
                //The only we do is dismiss the progressDialog, in case we are showing any.
                    if(pd!=null && pd.isShowing()){
                     pd.dismiss();
                    }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //This method will be called when the Auth proccess redirect to our RedirectUri.
                //We will check the url looking for our RedirectUri.
               // System.out.println("TEST:1" +request.getUrl().toString() + "TEST2:" +REDIRECT_URI);
                if (request.getUrl().toString().startsWith(REDIRECT_URI)) {
                    Log.i("Authorize", "");
                    Uri uri = Uri.parse(request.getUrl().toString());
                    //We take from the url the authorizationToken and the state token. We have to check that the state token returned by the Service is the same we sent.
                    //If not, that means the request may be a result of CSRF and must be rejected.
                    String stateToken = uri.getQueryParameter(STATE_PARAM);
                    if (stateToken == null || !stateToken.equals(STATE)) {
                        Log.e("Authorize", "State token doesn't match");
                        return true;
                    }

                    //If the user doesn't allow authorization to our application, the authorizationToken Will be null.
                    String authorizationToken = uri.getQueryParameter(RESPONSE_TYPE_VALUE);
                    if (authorizationToken == null) {
                        Log.i("Authorize", "The user doesn't allow authorization.");
                        return true;
                    }
                    Log.i("Authorize", "Auth token received: " + authorizationToken);

                    //Generate URL for requesting Access Token
                    String accessTokenUrl = getAccessTokenUrl(authorizationToken);
                    //We make the request in a AsyncTask
                    new PostRequestAsyncTask().execute(accessTokenUrl);

                } else {
                    //Default behaviour
                    Log.i("Authorize", "Redirecting to: " + request);
                    webView.loadUrl(request.getUrl().toString());
                }
                return true;
            }
        });

        //Get the authorization Url
        String authUrl = getAuthorizationUrl();
        Log.i("Authorize", "Loading Auth Url: " + authUrl);
        //Load the authorization URL into the webView
        webView.loadUrl(authUrl);




    }

    /**
     * Method that generates the url for get the access token from the Service
     *
     * @return Url
     */
    private static String getAccessTokenUrl(String authorizationToken) {
        return ACCESS_TOKEN_URL
                + QUESTION_MARK
                + GRANT_TYPE_PARAM + EQUALS + GRANT_TYPE
                + AMPERSAND
                + RESPONSE_TYPE_VALUE + EQUALS + authorizationToken
                + AMPERSAND
                + CLIENT_ID_PARAM + EQUALS + API_KEY
                + AMPERSAND
                + REDIRECT_URI_PARAM + EQUALS + REDIRECT_URI
                + AMPERSAND
                + SECRET_KEY_PARAM + EQUALS + SECRET_KEY;
    }

    /**
     * Method that generates the url for get the authorization token from the Service
     *
     * @return Url
     */
    private static String getAuthorizationUrl() {
        return AUTHORIZATION_URL
                + QUESTION_MARK + RESPONSE_TYPE_PARAM + EQUALS + RESPONSE_TYPE_VALUE
                + AMPERSAND + CLIENT_ID_PARAM + EQUALS + API_KEY
                + AMPERSAND + STATE_PARAM + EQUALS + STATE
                + AMPERSAND + REDIRECT_URI_PARAM + EQUALS + REDIRECT_URI
                + AMPERSAND + SCOPE_PARAM + EQUALS + SCOPE_URL;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //Ändra sen...
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
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

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {


                            @Override
                            public void onResponse(JSONObject response) {
                                // textView.setText("Response: " + response.toString());

                                try {
                                    int expiresIn = response.has("expires_in") ? response.getInt("expires_in") : 0;
                                    String accessToken = response.has("access_token") ? response.getString("access_token") : null;
                                    String idToken = response.has("id_token") ? response.getString("id_token") : null;

                                    if (expiresIn > 0 && accessToken != null && idToken != null) {
                                        Log.i("Authorize", "This is the access Token: " + accessToken + ". It will expires in " + expiresIn + " secs");

                                        System.out.println("idtoken"+ idToken);

                                        //Calculate date of expiration
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.add(Calendar.SECOND, expiresIn);
                                        long expireDate = calendar.getTimeInMillis();

                                        ////Store both expires in and access token in shared preferences

                                       // SharedPreferences preferences = LoginView.this.getSharedPreferences("user_info", 0);
                                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginView.this);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putLong("expires", expireDate);
                                        editor.putString("accessToken", accessToken);
                                        editor.putString("idToken",idToken);
                                        editor.apply();


                                    }

                                } catch (JSONException e) {
                                    Log.e("Authorize", "Error Parsing Http response " + e.getLocalizedMessage());
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
                };
              //  jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( INITIAL_VALUE, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                  queue.add(jsonObjectRequest);
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean status) {

              if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }

            if (status) {

                //If everything went Ok, change to another activity.
                Intent startProfileActivity = new Intent(LoginView.this, UpdateData.class);
                startActivity(startProfileActivity);
                finish();
            }

        }

}

}
