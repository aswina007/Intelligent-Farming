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
import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AgriCenterActivity extends AppCompatActivity {

    private static final String TAG = "AgriCenterActivity";

    private String sid;
    private String url;

    TextView nameTV, subDescTV, addressTV, emailTV, numberTV;
    ImageView shopIV;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agri_center);

        sid = getIntent().getStringExtra("sid");

        Toast.makeText(this, ""+sid, Toast.LENGTH_SHORT).show();

        GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
        ip = globalPreference.RetriveIP();
        url = "http://"+ ip + "/intelligent_farming/agriCenterDetails.php";

        nameTV = (TextView) findViewById(R.id.nameTV);
        subDescTV = (TextView) findViewById(R.id.subDescTV);
        addressTV = (TextView) findViewById(R.id.addressTV);
        emailTV = (TextView) findViewById(R.id.emailTV);
        numberTV = (TextView) findViewById(R.id.numberTV);
        shopIV = (ImageView) findViewById(R.id.shopIV);
        shopIV.setOnClickListener(new View.OnClickListener() {
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

                        if(response.contains("center")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("center");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String name = object.getString("ac_centre");
                                String desc = object.getString("ac_desc");
                                String address = object.getString("ac_address");
                                String phone = object.getString("ac_phone");
                                String email = object.getString("ac_email");
                                String image = object.getString("ac_image");

                                nameTV.setText(name);
                                subDescTV.setText(desc);
                                addressTV.setText(address);
                                numberTV.setText(phone);
                                emailTV.setText(email);
                                Glide.with(getApplicationContext())
                                        .load("http:"+ ip + "/intelligent_farming/admin/tbl_agri_centers/uploads/" + image)
                                        .into(shopIV);
                            }
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
                params.put("sid",sid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}
