package com.example.myapp.Server;

import android.content.Context;
import android.util.Log;
import android.view.textclassifier.TextLinks;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapp.IWebService;
import com.example.myapp.com.example.myapp.models.Place;
import com.example.myapp.com.example.myapp.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Server implements ServerInterface {
    //http://192.168.1.88:8081/users/login?email=hoang444@gmail.com&password=123456&userType=default
    private static final String SERVER_NAME = "10.0.12.65";
    private static final String SERVER_PORT = "8080";

    public static final String URL_LOGIN = "http://"+SERVER_NAME+":"+SERVER_PORT+"/users/login?";
    public static final String URL_QUERY_PLACES = "http://"+SERVER_NAME+":"+SERVER_PORT+"/places/query?";

    private static final String TAG = "Web Services";
    //Call api(web services)
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    Context context;
    //constructor
    public Server(Context context) {
        this.context = context;
    }
    private User loggedInUser;
    @Override
    public void loginWithEmailAndPassword(String email, String password) {
        requestQueue = Volley.newRequestQueue(context);
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL_LOGIN + "email=" + email + "&password=" + password + "&userType=default",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("result");
                            String message = response.getString("message");
                            if(result.equals("ok")) {
                                //convert jsonobject => User object ?
                                JSONObject data = response.getJSONObject("data");
                                loggedInUser = User.createUserFromJsonObject(data);
                                ((IWebService)context).getResponse(loggedInUser, null);
                            } else {
                                loggedInUser = null;
                                ((IWebService)context).getResponse(loggedInUser, message);
                            }

                        }catch (JSONException e) {
                            loggedInUser = null;
                            ((IWebService)context).getResponse(loggedInUser,
                                    "Cannot login user. Error:"+e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loggedInUser = null;
                        ((IWebService)context).getResponse(loggedInUser,
                                "Cannot login user. Error:"+error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    //http://localhost:8080/places/query?userId=13&offset=0&limit=10
    @Override
    public void queryPlaces(Integer userId, Integer offset, Integer limit) {
        requestQueue = Volley.newRequestQueue(context);
        ArrayList<Place> places = new ArrayList<>();
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_QUERY_PLACES + "userId=" + userId + "&offset=" + offset + "&limit="+limit,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("result");
                            String message = response.getString("message");

                            if(result.equals("ok")) {
                                JSONArray data = response.getJSONArray("data");
                                for(int i = 0; i < data.length(); i++) {
                                    if(data.get(i) instanceof JSONObject) {
                                        JSONObject jsonObject = (JSONObject) data.get(i);
                                        Place place = Place.createPlaceFromJsonObject(jsonObject);
                                        places.add(place);
                                    }
                                }
                                ((IWebService)context).getResponse(places, null);

                            } else {
                                ((IWebService)context).getResponse(places, message);
                            }

                        }catch (JSONException e) {
                            ((IWebService)context).getResponse(places,
                                    "Cannot find places. Error:"+e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loggedInUser = null;
                        ((IWebService)context).getResponse(places,
                                "Cannot find places. Error:"+error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);

    }
}
