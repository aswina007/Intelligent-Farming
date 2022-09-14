package com.aumento.intelligentfarming.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import com.aumento.intelligentfarming.AgriCenterActivity;
import com.aumento.intelligentfarming.OtherUserActivity;
import com.aumento.intelligentfarming.R;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "MapFragment";
    private String uid;
    private String url;
    HashMap<String, String> userId = new HashMap<>();

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalPreference globalPreference = new GlobalPreference(getContext());
        String ip = globalPreference.RetriveIP();
        uid = globalPreference.RetriveUID();
        url = "http://" + ip + "/intelligent_farming/mapMarkerLists.php";

    }

    private void loadMapMarkers(String url, final String uid, final GoogleMap mMap) {

        Log.d(TAG, "loadMapMarkers: "+url);

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

                        if(response.contains("data")) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id");
                                String centre = object.getString("ac_centre");
                                String latitude = object.getString("ac_latitude");
                                String longitude = object.getString("ac_longitude");

                                LatLng latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));

                                Marker marker  = mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title("Center: "+centre)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.center_pin)));

                                userId.put(marker.getId(),id);

                            }

                        }

                        if(response.contains("user")) {

                            Log.d(TAG, "onResponse: user ");

                            JSONArray jsonArray = jsonObject.getJSONArray("user");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String type = object.getString("type");
                                String latitude = object.getString("latitude");
                                String longitude = object.getString("longitude");

                                LatLng latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                                Marker marker  = mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title("User: "+name)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_pin)));

                                userId.put(marker.getId(),id);
                                /*mMap.addMarker(new MarkerOptions().position(centerLatLng).title(type+" "+name).snippet(type)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));*/
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

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return  view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);

        loadMapMarkers(url, uid, mMap);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                if(marker.getTitle().contains("User")){
                    String uid = userId.get(marker.getId());
                    Intent intent = new Intent(getActivity(), OtherUserActivity.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);
//                    Toast.makeText(getActivity(), ""+uid, Toast.LENGTH_SHORT).show();
                }
                else if(marker.getTitle().contains("Center")){
                    String sid = userId.get(marker.getId());
                    Intent intent = new Intent(getActivity(), AgriCenterActivity.class);
                    intent.putExtra("sid",sid);
                    startActivity(intent);
//                    Toast.makeText(getActivity(), ""+uid, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(9.954527, 76.242398);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f));

    }

}
