package com.aumento.intelligentfarming;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.intelligentfarming.utils.GlobalPreference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "SignupActivity";
    CircleImageView userIV;
    EditText userFirstNameET, userLastNameET, userPhoneNumberET, userEmailET, userUsernameET, userPasswordET;
    TextView submitButtonTV;
    private String ip;
    private int PICK_IMAGE_REQUEST = 100;
    private Bitmap bitmap;
    private LocationManager locationManager;
    private String lat;
    private String lon;
    Boolean is = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
        ip = globalPreference.RetriveIP();

        init();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,2000,0, this);

        submitButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        userIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                is = true;
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                userIV.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void signUp() {

        String image = "";

        if(is)
            image = getStringImage(bitmap);

        final String finalImage = image;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ip+"/intelligent_farming/register.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                Toast.makeText(SignupActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                if(response.contains("Failed")){
                    Toast.makeText(SignupActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                Toast.makeText(SignupActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("fname",userFirstNameET.getText().toString());
                params.put("lname",userLastNameET.getText().toString());
                params.put("phone",userPhoneNumberET.getText().toString());
                params.put("email",userEmailET.getText().toString());
                params.put("username",userUsernameET.getText().toString());
                params.put("password",userPasswordET.getText().toString());
                params.put("latitude",lat);
                params.put("longitude",lon);
                params.put("image", finalImage);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
        requestQueue.add(stringRequest);

    }

    private void init() {

        userFirstNameET = (EditText) findViewById(R.id.userFirstNameTextView);
        userLastNameET = (EditText) findViewById(R.id.userLastNameTextView);
        userPhoneNumberET = (EditText) findViewById(R.id.userPhoneNumberTextView);
        userEmailET = (EditText) findViewById(R.id.userEmailTextView);
        userUsernameET = (EditText) findViewById(R.id.userUsernameTextView);
        userPasswordET = (EditText) findViewById(R.id.userPasswordTextView);
        submitButtonTV = (TextView) findViewById(R.id.submitButtonTextView);
        userIV = (CircleImageView) findViewById(R.id.userImageView);

    }

    @Override
    public void onLocationChanged(Location location) {
//        Toast.makeText(this, ""+location.getLongitude()+" "+location.getLatitude(), Toast.LENGTH_SHORT).show();
        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
