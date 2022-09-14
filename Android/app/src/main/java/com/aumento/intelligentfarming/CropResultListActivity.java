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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CropResultListActivity extends AppCompatActivity {

    private static final String TAG = "CropResultListActivity";
    private RecyclerView cropResultListRV;
    private ArrayList<MyCropListModelClass> cropList;
    private CropResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_result_list);

        String response = getIntent().getStringExtra("response");

        cropResultListRV = (RecyclerView) findViewById(R.id.cropResultRecyclerView);
        cropResultListRV.setLayoutManager(new LinearLayoutManager(CropResultListActivity.this));
        cropResultListRV.setNestedScrollingEnabled(true);
        cropResultListRV.setItemAnimator(new DefaultItemAnimator());

        loadData(response);
    }

    private void loadData(String response) {

        cropList = new ArrayList<>();
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
            adapter = new CropResultAdapter(cropList, CropResultListActivity.this);
            cropResultListRV.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
