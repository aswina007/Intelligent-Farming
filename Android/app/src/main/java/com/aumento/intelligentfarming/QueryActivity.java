package com.aumento.intelligentfarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.intelligentfarming.Adapter.CropLifecycleAdapter;
import com.aumento.intelligentfarming.Adapter.QueryListAdapter;
import com.aumento.intelligentfarming.ModelClass.CropLifecycleModelClass;
import com.aumento.intelligentfarming.ModelClass.QueryQuestionsModelClass;
import com.aumento.intelligentfarming.utils.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryActivity extends AppCompatActivity {

    private static final String TAG = "QueryActivity";
    Button submitQueryBT;

    RecyclerView queryRecyclerView;
    private ArrayList<QueryQuestionsModelClass> qtnList;
    private QueryListAdapter adapter;
    private String url;
    private PopupWindow changeSortPopUp;
    private PopupWindow mpopup;
    private EditText qnt;
    private String submiturl;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
        String ip = globalPreference.RetriveIP();
        uid = globalPreference.RetriveUID();
        url = "http://"+ ip + "/intelligent_farming/queryList.php";
        submiturl = "http://"+ ip + "/intelligent_farming/submitQuery.php";

        submitQueryBT = (Button) findViewById(R.id.submitQueryButton);
        submitQueryBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortPopup();
            }
        });

        queryRecyclerView = (RecyclerView) findViewById(R.id.queryRecyclerView);
        queryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        queryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        queryRecyclerView.setNestedScrollingEnabled(true);

        loadData();
    }

    private void loadData() {

        qtnList = new ArrayList<>();

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
                            String question = object.getString("question");
                            String qdate = object.getString("qdate");

                            qtnList.add(new QueryQuestionsModelClass(id, question, qdate));
                        }

                        adapter = new QueryListAdapter(qtnList, getApplicationContext());
                        queryRecyclerView.setAdapter(adapter);

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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void showSortPopup()  {

        View popUpView = getLayoutInflater().inflate(R.layout.raw_submit_question,
                null); // inflating popup layout
        mpopup = new PopupWindow(popUpView, LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true); // Creation of popup
        mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
        mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0); // Displaying popup

        qnt = (EditText) popUpView.findViewById(R.id.question);
        Button click = (Button) popUpView.findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                submitQuery();

            }
        });


        // Getting a reference to Close button, and close the popup when clicked.
      ImageView close = (ImageView) popUpView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mpopup.dismiss();
            }
        });

    }

    private void submitQuery() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, submiturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse submit: "+response);

                if(response.contains("Failed")){
                    Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(QueryActivity.this, "Query Submitted", Toast.LENGTH_SHORT).show();
                    mpopup.dismiss();
                    loadData();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("qtn",qnt.getText().toString());
                params.put("uid",uid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }


}
