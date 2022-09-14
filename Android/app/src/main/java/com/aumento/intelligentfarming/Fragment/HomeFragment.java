package com.aumento.intelligentfarming.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.intelligentfarming.Adapter.MyCropListAdapter;
import com.aumento.intelligentfarming.AddCropActivity;
import com.aumento.intelligentfarming.IPActivity;
import com.aumento.intelligentfarming.ModelClass.MyCropListModelClass;
import com.aumento.intelligentfarming.R;
import com.aumento.intelligentfarming.SelectCropListActivity;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    FloatingActionButton addCropFab;
    RecyclerView myCropListRV;
    private View view;
    private List<MyCropListModelClass> cropList;
    private MyCropListAdapter adapter;
    private Intent intent;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalPreference globalPreference = new GlobalPreference(getContext());
        String ip = globalPreference.RetriveIP();
        String uid = globalPreference.RetriveUID();
        Log.d(TAG, "userid: "+uid);
        String url = "http://" + ip + "/intelligent_farming/myCropList.php";

        loadMyCropList(url, uid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();

        addCropFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String[] colors = {"All Crops","Crop through Prediction"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose a option");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (colors[which]){
                            case "All Crops":
                                intent = new Intent(getActivity(), SelectCropListActivity.class);
                                startActivity(intent);
                                break;
                            case "Crop through Prediction":
                                intent = new Intent(getActivity(), AddCropActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }
                });
                builder.show();
            }
        });

        return view;
    }

    private void loadMyCropList(String url, final String uid) {

        cropList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.contains("Failed")){
                    Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
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

                        adapter = new MyCropListAdapter(cropList, getContext());
                        myCropListRV.setAdapter(adapter);

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
                params.put("uid",uid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    private void init() {
        addCropFab = (FloatingActionButton) view.findViewById(R.id.addCropFab);

        myCropListRV = (RecyclerView) view.findViewById(R.id.myCropListRecyclerView);
        myCropListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        myCropListRV.setNestedScrollingEnabled(true);
        myCropListRV.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
