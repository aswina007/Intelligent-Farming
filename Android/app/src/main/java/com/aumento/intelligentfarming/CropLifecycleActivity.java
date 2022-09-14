package com.aumento.intelligentfarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
import com.aumento.intelligentfarming.Adapter.MyCropListAdapter;
import com.aumento.intelligentfarming.ModelClass.CropLifecycleModelClass;
import com.aumento.intelligentfarming.ModelClass.MyCropListModelClass;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropLifecycleActivity extends AppCompatActivity {

    private static final String TAG = "CropLifecycleActivity";

    private RecyclerView cropLifecyclerRV;
    private List<CropLifecycleModelClass> cropLifecycle;
    private String cid;
    private String url;
    private CropLifecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_lifecycle);

        cid = getIntent().getStringExtra("cid");
        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");

        GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
        String ip = globalPreference.RetriveIP();
        url = "http://"+ ip + "/intelligent_farming/cropLifecycle.php";

        TextView cropNameTV = (TextView) findViewById(R.id.cropNameTV);
        cropNameTV.setText(name);
        ImageView cropIV = (ImageView) findViewById(R.id.cropIV);
        Glide.with(getApplicationContext())
                .load("http://"+ip+ "/intelligent_farming/admin/tbl_crops/uploads/" + image)
                .into(cropIV);

        cropLifecyclerRV = (RecyclerView) findViewById(R.id.cropLifecyclerRecyclerView);
        cropLifecyclerRV.setLayoutManager(new LinearLayoutManager(this));
        cropLifecyclerRV.setItemAnimator(new DefaultItemAnimator());
        cropLifecyclerRV.setNestedScrollingEnabled(true);

        loadData();
    }

    private void loadData() {

        cropLifecycle = new ArrayList<>();

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
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for(int i=0; i< jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String week = object.getString("week");
                            String lifecycle = object.getString("lifecycle");

                            cropLifecycle.add(new CropLifecycleModelClass(id, week, lifecycle));
                        }

                        adapter = new CropLifecycleAdapter(cropLifecycle, getApplicationContext());
                        cropLifecyclerRV.setAdapter(adapter);

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
                params.put("cid",cid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
