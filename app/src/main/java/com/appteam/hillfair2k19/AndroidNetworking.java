package com.appteam.hillfair2k19;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

public class AndroidNetworking {
       IResult mResultCallback = null;
        Context mContext;
        AndroidNetworking(IResult resultCallback, Context context){
            mResultCallback = resultCallback;
            mContext = context;
        }
    public void postJsondataAndroidNetworking(final String requestType , String url , JSONObject jsonObject)
    {
        com.androidnetworking.AndroidNetworking.post(url)
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        if (mResultCallback != null)
                            mResultCallback.notifySuccess(requestType,response,null);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
        public void getJsonObjectAndroidNetworking(final String requestType , String url)
        {
            com.androidnetworking.AndroidNetworking.get(url)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            if (mResultCallback != null)
                                mResultCallback.notifySuccess(requestType,response,null);
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                        }
                    });
        }
        public void getJsonArrayAndroidNetworking(final String requestType , String url)
        {
            com.androidnetworking.AndroidNetworking.get(url)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // do anything with response
                            if (mResultCallback != null)
                                mResultCallback.notifySuccess(requestType,null,response);
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                        }
                    });
        }

    }
