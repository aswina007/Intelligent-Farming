package com.aumento.intelligentfarming.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.intelligentfarming.Adapter.MyCropListAdapter;
import com.aumento.intelligentfarming.ModelClass.MyCropListModelClass;
import com.aumento.intelligentfarming.R;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketFragment extends Fragment {

    private static final String TAG = "MarketFragment";

    BarChart barChart;
    ArrayList<BarEntry> entries;
    ArrayList<String> xLabels;

    List<String> month = new ArrayList<>();
    List<String> year = new ArrayList<>();
    List<String> cropList;

    private Spinner MMspin;
    private Spinner YYspin;
    private Spinner cropSpinner;

    private GlobalPreference globalPreference;
    private String url;

    public MarketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalPreference = new GlobalPreference(getActivity());
        String ip = globalPreference.RetriveIP();
        url = "http://"+ ip + "/intelligent_farming/marketValuesList.php";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_market, container, false);

        barChart = view.findViewById(R.id.BarChart);

        loadCropList();

        month.add("January");
        month.add("February");
        month.add("March");
        month.add("April");
        month.add("May");
        month.add("June");
        month.add("July");
        month.add("August");
        month.add("September");
        month.add("October");
        month.add("November");
        month.add("December");

        year.add("2017");
        year.add("2018");
        year.add("2019");
        year.add("2020");

        cropSpinner = (Spinner) view.findViewById(R.id.cropSpinner);
        ArrayAdapter cropSpinnerAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,cropList);
        cropSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropSpinner.setAdapter(cropSpinnerAdapter);

        MMspin = (Spinner) view.findViewById(R.id.mmspinner);
        ArrayAdapter MM = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,month);
        MM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MMspin.setAdapter(MM);

        YYspin = (Spinner) view.findViewById(R.id.yyspinner);
        ArrayAdapter YY = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,year);
        YY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YYspin.setAdapter(YY);

        Button marketSearchButton = (Button) view.findViewById(R.id.marketSearchButton);
        marketSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadData();

            }
        });

        return view;
    }

    private void loadCropList() {

        cropList = new ArrayList<>();

        String response = globalPreference.RetriveCrop();

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


    private void loadData() {

        entries = new ArrayList<>();
        xLabels = new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.contains("Failed")){
                    Toast.makeText(getActivity(), "No Data available...", Toast.LENGTH_SHORT).show();
                    barChart.clear();
                }
                else {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for(int i=0; i< jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String district = object.getString("district");
                            float price = Float.parseFloat(object.getString("price"));

                            entries.add(new BarEntry(i, price));
                            xLabels.add(district);

                        }

                        BarDataSet dataSet = new BarDataSet(entries,"Average Price");

                        BarData data = new BarData(dataSet);
                        barChart.setData(data);
                        barChart.animateXY(2000, 2000);
                        barChart.invalidate();
                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setGranularity(1f);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setAxisMinimum(0);
                        xAxis.setAxisMaximum(barChart.getBarData().getBarWidth() * xLabels.size());

                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return xLabels.get((int) value);
                            }

                        });

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("crop",cropSpinner.getSelectedItem().toString());
                params.put("month",MMspin.getSelectedItem().toString());
                params.put("year",YYspin.getSelectedItem().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


}
