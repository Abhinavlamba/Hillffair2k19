package com.appteam.hillfair2k19;


import
        android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.cloudinary.android.MediaManager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button phone_button;
    private static final int RC_SIGN_IN = 200;
    private Context context = this;
    public int newBranch = 0;
    public static String fireBaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //configuring cloudinary
//        Map config = new HashMap();
//        config.put("cloud_name", "dpxfdn3d8");
//        config.put("api_key", "172568498646598");
//        config.put("api_secret", "NNa_bFKyVxW0AB30wL8HVoFxeSs");
//        MediaManager.init(this, config);

        setContentView(R.layout.activity_login);
        phone_button = findViewById(R.id.phone_login);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        final SharedPreferences sharedPreferences = getSharedPreferences("number", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("fireBaseId","12345");//TODO delete this
//        editor.commit();
        String Login = sharedPreferences.getString("Login", "gsbs");
        if (!Login.equals("gsbs")) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {

        }

//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("newBranch",newBranch);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//
//        finish();
        phone_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_login:
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.PhoneBuilder().build()))
                                .build(),
                        RC_SIGN_IN);

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            final IdpResponse response = IdpResponse.fromResultIntent(data);
            // Successfully signed in
            if (resultCode == RESULT_OK) {
                final SharedPreferences sharedPreferences = getSharedPreferences("number", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Login", "Complete");

                Toast.makeText(LoginActivity.this, "Authenticated.",
                        Toast.LENGTH_SHORT).show();
                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

                final String uid = mUser.getUid();
                fireBaseId=uid;
                editor.putString("fireBaseId",uid);
                editor.commit();

                Intent intent = new Intent(context, Profile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                finish();

//                mUser.getIdToken(true)
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                String idToken = task.getResult().getToken();
//
////								Intent intent = new Intent(context, MainActivity.class);
////								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////								startActivity(intent);
////
////								finish();
//
//                                // Send token to your backend via HTTPS
//                                takeSignupOrSignin(response, uid, idToken,mUser.getPhoneNumber());
//
//                            } else {
//                                // Handle error -> task.getException();
//                                Log.d("Login Error", task.getException().toString());
//                            }
//                        });

//                finish();
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }
}
