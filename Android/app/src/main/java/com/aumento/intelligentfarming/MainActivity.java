package com.aumento.intelligentfarming;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.intelligentfarming.Fragment.HomeFragment;
import com.aumento.intelligentfarming.Fragment.MapFragment;
import com.aumento.intelligentfarming.Fragment.MarketFragment;
import com.aumento.intelligentfarming.Fragment.NewsFragment;
import com.aumento.intelligentfarming.Fragment.WeatherFragment;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private BottomNavigationView bottomNavigation;
    private ImageView optionsButton;
    private Intent intent;
    private String url;
    private GlobalPreference globalPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        globalPreference = new GlobalPreference(getApplicationContext());
        String ip = globalPreference.RetriveIP();
        url = "http://"+ ip + "/intelligent_farming/allCropList.php";

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new HomeFragment());

        optionsButton = findViewById(R.id.optionsButton);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(MainActivity.this,view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.options_profile:
                                intent = new Intent(MainActivity.this, ProfileActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.options_settings:
                                intent = new Intent(MainActivity.this, QueryActivity.class);
                                startActivity(intent);
                                return true;
                        }
                        return false;
                    }
                });// to implement on click event on items of menu
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.options_menu, popup.getMenu());
                popup.show();
            }
        });

        loadCropList();

    }

    private void loadCropList() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.contains("Failed")){
                    Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                }
                else {
                    globalPreference.addCropResponse(response);
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

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(new HomeFragment());
                            return true;
                        case R.id.navigation_map:
                            openFragment(new MapFragment());
                            return true;
                        case R.id.navigation_market:
                            openFragment(new MarketFragment());
                            return true;
                        case R.id.navigation_weather:
                            openFragment(new WeatherFragment());
                            return true;
                        case R.id.navigation_news:
                            openFragment(new NewsFragment());
                            return true;
                    }
                    return false;
                }
            };
}




