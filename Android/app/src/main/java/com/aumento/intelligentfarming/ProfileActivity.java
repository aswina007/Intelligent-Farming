package com.aumento.intelligentfarming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private EditText userNameET, userLNameET, userEmailET, userPasswordET, userUsernameET;
    private EditText userMobileTV;
    private TextView submitButtonTV;
    private CircleImageView userIV;
    private String uid;
    private String url;
    private String ip;
    private String update_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
        uid = globalPreference.RetriveUID();
        ip = globalPreference.RetriveIP();
        url = "http://"+ ip +  "/intelligent_farming/getProfile.php";
        update_url = "http://"+ ip +  "/intelligent_farming/updateProfile.php";
        init();
        loadData();

        submitButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray jsonArray = jsonObj.getJSONArray("data");

                    JSONObject object = jsonArray.getJSONObject(0);
                    String id = object.getString("id");
                    String name = object.getString("name");
                    String email = object.getString("email");
                    String phone_no = object.getString("phone_no");
                    String username = object.getString("username");
                    String password = object.getString("password");
                    String image = object.getString("image");

                    userNameET.setText(name);
                    userLNameET.setText(name);
                    userEmailET.setText(email);
                    userUsernameET.setText(username);
                    userPasswordET.setText(password);
                    userMobileTV.setText(phone_no);

                    Glide.with(getApplicationContext())
                            .load("http://"+ ip + "/intelligent_farming/admin/tbl_user/uploads/" +image)
//                            .load(R.drawable.ic_search_black_24dp)
                            .into(userIV);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userId",uid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void updateData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, update_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);

                Toast.makeText(ProfileActivity.this, ""+response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userId",uid);
                params.put("fname",userNameET.getText().toString());
                params.put("lname",userLNameET.getText().toString());
                params.put("email",userEmailET.getText().toString());
                params.put("username",userUsernameET.getText().toString());
                params.put("password",userPasswordET.getText().toString());
                params.put("mobile",userMobileTV.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private void init() {
        userNameET = (EditText) findViewById(R.id.userFirstNameEditText);
        userLNameET = (EditText) findViewById(R.id.userLastNameEditText);
        userEmailET = (EditText) findViewById(R.id.userEmailEditText);
        userPasswordET = (EditText) findViewById(R.id.userPasswordEditText);
        userUsernameET = (EditText) findViewById(R.id.userUsernameEditText);
        userMobileTV = (EditText) findViewById(R.id.userPhoneNumberEditText);
        submitButtonTV = (TextView) findViewById(R.id.submitButtonTextView);
        userIV = (CircleImageView) findViewById(R.id.userImageView);
    }

}
