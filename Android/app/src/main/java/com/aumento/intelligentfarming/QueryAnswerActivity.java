package com.aumento.intelligentfarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.intelligentfarming.Adapter.QueryAnswerListAdapter;
import com.aumento.intelligentfarming.ModelClass.QueryAnswerModelClass;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryAnswerActivity extends AppCompatActivity {

    private static final String TAG = "QueryAnswerActivity";

    TextView qQtntextView, qDatetextView;
    RecyclerView qAnswerRV;
    EditText answerTyreEditText;
    ImageView sendButton;
    private String url;
    private ArrayList<QueryAnswerModelClass> answerList;
    private QueryAnswerListAdapter adapter;
    private String qid;
    private String submitUrl;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_answer);

        Intent intent = getIntent();
        qid = intent.getStringExtra("id");
        String question = intent.getStringExtra("qtn");
        String date = intent.getStringExtra("date");

        Log.d(TAG, "onCreate: "+qid+" "+question+" "+date);

        qQtntextView = (TextView) findViewById(R.id.qQtntextView);
        qDatetextView = (TextView) findViewById(R.id.qDatetextView);

        qQtntextView.setText(question);
        qDatetextView.setText(date);

        GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
        String ip = globalPreference.RetriveIP();
        uid = globalPreference.RetriveUID();
        url = "http://"+ ip + "/intelligent_farming/queryAnswerList.php";
        submitUrl = "http://"+ ip + "/intelligent_farming/submitQueryAnswer.php";


        answerTyreEditText = (EditText) findViewById(R.id.answerTyreEditText);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitAnswer();

            }
        });

        qAnswerRV = (RecyclerView) findViewById(R.id.qAnswerRecyclerView);
        qAnswerRV.setLayoutManager(new LinearLayoutManager(this));
        qAnswerRV.setItemAnimator(new DefaultItemAnimator());
        qAnswerRV.setNestedScrollingEnabled(true);

        loadAnswers();

    }

    private void submitAnswer() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, submitUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.contains("Failed")){
                    Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                }
                else {
                    answerTyreEditText.setText("");
                    loadAnswers();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("answer",answerTyreEditText.getText().toString());
                params.put("uid",uid);
                params.put("qid",qid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void loadAnswers() {

        Log.d(TAG, "submitAnswer: "+qid);

        answerList = new ArrayList<>();

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
                            String name = object.getString("name");
                            String image = object.getString("image");
                            String date = object.getString("date");
                            String answer = object.getString("answer");

                            answerList.add(new QueryAnswerModelClass(name, image, date, answer));
                        }

                        adapter = new QueryAnswerListAdapter(answerList, getApplicationContext());
                        qAnswerRV.setAdapter(adapter);

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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("qid",qid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
