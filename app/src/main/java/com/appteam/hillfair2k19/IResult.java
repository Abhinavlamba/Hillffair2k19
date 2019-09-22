package com.appteam.hillfair2k19;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IResult {
    public void notifySuccess(String requestType, JSONObject response , JSONArray jsonArray);
    public void notifyError(String requestType, VolleyError error);
}