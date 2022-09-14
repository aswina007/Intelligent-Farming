package com.aumento.intelligentfarming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.intelligentfarming.Adapter.CropLifecycleAdapter;
import com.aumento.intelligentfarming.ModelClass.CropLifecycleModelClass;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OtherUserActivity extends AppCompatActivity {

    private String url;
    private static final String TAG = "OtherUserActivity";
    private String uid;
    private TextView mycropTV;
    private TextView nameTV;
    private TextView emailTV;
    private ImageView userIV;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);

        uid = getIntent().getStringExtra("uid");

        GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
        ip = globalPreference.RetriveIP();
        url = "http://"+ ip + "/intelligent_farming/otherUser.php";

        mycropTV = (TextView) findViewById(R.id.mycropTV);
        nameTV = (TextView) findViewById(R.id.nameTV);
        emailTV = (TextView)  findViewById(R.id.emailTV);
        userIV = (ImageView) findViewById(R.id.userIV);
        ImageView backIV = (ImageView) findViewById(R.id.backIV);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadData();
    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.contains("Failed")){
                    Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                }
                else {
                    try{
                        JSONObject jsonObject = new JSONObject(response);

                        if(response.contains("user")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("user");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String name = object.getString("name");
                                String Lname = object.getString("Lname");
                                String email = object.getString("email");
                                String image = object.getString("image");

                                nameTV.setText(name+" "+Lname);
                                emailTV.setText(email);
                                Glide.with(getApplicationContext())
                                        .load("http://"+ip+ "/intelligent_farming/admin/tbl_user/uploads/" + image)
                                        .into(userIV);
                            }
                        }
                        if(response.contains("crop")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("crop");
                            String cname = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id");
                                String name = object.getString("name");

                                Log.d(TAG, "onResponse: " + name);

                                cname = cname + "• "+name + "\n";
                            }

                            mycropTV.setText(cname + "\n");

                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
