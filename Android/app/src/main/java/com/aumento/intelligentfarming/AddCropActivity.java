package com.aumento.intelligentfarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCropActivity extends AppCompatActivity {

    private static final String TAG = "AddCropActivity";

    private List<String> seasonList = new ArrayList<>();
    private List<String> soilTypeList = new ArrayList<>();
    private List<String> cropList = new ArrayList<>();

    private Spinner seasonSpinner;
    private Spinner soilSpinner;
    private Spinner cropSpinner;

    private EditText pHEditText;
    private EditText elvtnEditText;
    private EditText tempEditText;
    private String url;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);

        GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
        String ip = globalPreference.RetriveIP();
        url = "http://"+ ip + "/intelligent_farming/python/py.php";
        String cropListResponse = globalPreference.RetriveCrop();
        loadCropList(cropListResponse);


        soilTypeList.add("Red Laterite");
        soilTypeList.add("Loamy Soil");
        soilTypeList.add("Sandy Loam ");
        soilTypeList.add("Clay Soil");
        soilTypeList.add("Chalk Soil");
        soilTypeList.add("Peat Soil");
        soilTypeList.add("Clay Soil");


        seasonList.add("January");
        seasonList.add("February");
        seasonList.add("March");
        seasonList.add("April");
        seasonList.add("May");
        seasonList.add("June");
        seasonList.add("July");
        seasonList.add("August");
        seasonList.add("September");
        seasonList.add("October");
        seasonList.add("November");
        seasonList.add("December");

        cropSpinner = (Spinner) findViewById(R.id.cropSpinner);
        ArrayAdapter cropSpinnerAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,cropList);
        cropSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropSpinner.setAdapter(cropSpinnerAdapter);

        seasonSpinner = (Spinner) findViewById(R.id.seasonSpinner);
        ArrayAdapter seasonSpinnerAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,seasonList);
        seasonSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seasonSpinner.setAdapter(seasonSpinnerAdapter);

        soilSpinner = (Spinner) findViewById(R.id.soilSpinner);
        ArrayAdapter soilSpinnerAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,soilTypeList);
        soilSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soilSpinner.setAdapter(soilSpinnerAdapter);

        pHEditText = (EditText) findViewById(R.id.pHEditText);
        elvtnEditText = (EditText) findViewById(R.id.elvtnEditText);
        tempEditText = (EditText) findViewById(R.id.tempEditText);

        Button searchButton = findViewById(R.id.searchButton);

        loadingDialog = new LoadingDialog(AddCropActivity.this);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendData();
                loadingDialog.startLoadingDialog();

            }
        });

    }

    private void sendData() {

        int eli = Integer.parseInt(elvtnEditText.getText().toString());
        if(eli <= 1000 )
            eli = 900;
        else if(eli > 1000 && eli < 1500) {
            eli = 1200;
        } else if(eli >= 1500)
            eli = 1600;

//        final String crop = cropSpinner.getSelectedItem().toString();
        final int season = Integer.parseInt(String.valueOf(seasonSpinner.getSelectedItemId())) +1 ;
        final int soild = Integer.parseInt(String.valueOf(soilSpinner.getSelectedItemId())) +1;
        final int finalEli = eli;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);
                loadingDialog.dismissDialog();
                if(response.contains("No data")){
                    Toast.makeText(getApplicationContext(), "No Data"+response, Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent intent = new Intent(AddCropActivity.this,CropResultListActivity.class);
                    intent.putExtra("response",response);
                    startActivity(intent);
                    finish();
                    Log.d(TAG, "onResponse: "+response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
//                params.put("crop",crop);
                params.put("season", String.valueOf(season));
                params.put("pH",pHEditText.getText().toString());
                params.put("soil_type", String.valueOf(soild));
                params.put("elevation", String.valueOf(finalEli));
                params.put("temperature", tempEditText.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void loadCropList(String response) {

        cropList = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for(int i=0; i< jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                String crop = object.getString("name");

                cropList.add(crop);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }


    }

}
