package com.aumento.intelligentfarming.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.intelligentfarming.CropLifecycleActivity;
import com.aumento.intelligentfarming.ModelClass.MyCropListModelClass;
import com.aumento.intelligentfarming.R;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropResultAdapter extends RecyclerView.Adapter<CropResultAdapter.MyViewHolder> {

    private static final String TAG = "CropResultAdapter";
    private final String ip;
    private final String uid;
    private List<MyCropListModelClass> myCropList;
    private Context mCtx;

    public CropResultAdapter(List<MyCropListModelClass> myCropList, Context mCtx) {
        this.myCropList = myCropList;
        this.mCtx = mCtx;

        GlobalPreference globalPreference = new GlobalPreference(mCtx);
        ip = globalPreference.RetriveIP();
        uid = globalPreference.RetriveUID();
        Log.d(TAG, "cropResultUserId: "+uid);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_crop_result, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MyCropListModelClass lists = myCropList.get(position);

        holder.cropNameTextView.setText(lists.getCrop_name());
        Glide.with(mCtx)
                .load("http://"+ip+ "/intelligent_farming/admin/tbl_crops/uploads/" + lists.getCrop_image())
                .into(holder.cropImageView);

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addCrop(lists.getId());

               /* Intent intent = new Intent(mCtx, CropLifecycleActivity.class);
                intent.putExtra("cid",lists.getId());
                intent.putExtra("name",lists.getCrop_name());
                intent.putExtra("image",lists.getCrop_image());
                mCtx.startActivity(intent);*/
            }
        });
    }

    private void addCrop(final String cropid) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ip+"/intelligent_farming/addCrop.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ((Activity)mCtx).finish();

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
                params.put("cropid",cropid);
                params.put("userid",uid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);

    }

    @Override
    public int getItemCount() {
        return myCropList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Button addButton;
        private ImageView cropImageView;
        private TextView cropNameTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cropImageView = (ImageView) itemView.findViewById(R.id.cropImageView);
            cropNameTextView = (TextView) itemView.findViewById(R.id.cropNameTextView);
            addButton = (Button) itemView.findViewById(R.id.addButton);
        }
    }
}
