package com.aumento.intelligentfarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.intelligentfarming.Adapter.CropResultAdapter;
import com.aumento.intelligentfarming.Adapter.MyCropListAdapter;
import com.aumento.intelligentfarming.ModelClass.MyCropListModelClass;
import com.aumento.intelligentfarming.utils.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCropListActivity extends AppCompatActivity {

    private static final String TAG = "SelectCropListActivity";
    private RecyclerView allCropListRV;
    private List<MyCropListModelClass> cropList;
    private CropResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_crop_list);

        allCropListRV = (RecyclerView) findViewById(R.id.viewAllCropRecyclerView);
        allCropListRV.setLayoutManager(new LinearLayoutManager(SelectCropListActivity.this));
        allCropListRV.setNestedScrollingEnabled(true);
        allCropListRV.setItemAnimator(new DefaultItemAnimator());

        GlobalPreference globalPreference = new GlobalPreference(SelectCropListActivity.this);
        String ip = globalPreference.RetriveIP();
        String url = "http://" + ip + "/intelligent_farming/allCropList.php";

        loadMyCropList(url);

    }

    private void loadMyCropList(String url) {

        cropList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.contains("Failed")){
                    Toast.makeText(SelectCropListActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for(int i=0; i< jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String image = object.getString("image");

                            cropList.add(new MyCropListModelClass(id, name, image));
                        }

                        adapter = new CropResultAdapter(cropList, SelectCropListActivity.this);
                        allCropListRV.setAdapter(adapter);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(SelectCropListActivity.this);
        requestQueue.add(stringRequest);

    }

}
