package com.appteam.hillfair2k19;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    IResult mResultCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVolleyCallback();
        VolleyService mVolleyService = new VolleyService(mResultCallback,this);
        mVolleyService.getJsonObjectDataVolley("GETCALL","https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response , JSONArray jsonArray) {
//                Log.d(TAG, "Volley requester " + requestType);
//                Log.d(TAG, "Volley JSON post" + response);
                if (response != null)
                {
                    //JsonObject
                }
                else
                {
                    //JsonArray
                }
                Toast.makeText(MainActivity.this, String.valueOf(response), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
//                Log.d(TAG, "Volley requester " + requestType);
//                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

}
