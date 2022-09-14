package com.aumento.intelligentfarming.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumento.intelligentfarming.CropLifecycleActivity;
import com.aumento.intelligentfarming.ModelClass.MyCropListModelClass;
import com.aumento.intelligentfarming.R;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.bumptech.glide.Glide;

import java.util.List;

public class MyCropListAdapter extends RecyclerView.Adapter<MyCropListAdapter.MyViewHolder> {

    private final String ip;
    private List<MyCropListModelClass> myCropList;
    private Context mCtx;

    public MyCropListAdapter(List<MyCropListModelClass> myCropList, Context mCtx) {
        this.myCropList = myCropList;
        this.mCtx = mCtx;

        GlobalPreference globalPreference = new GlobalPreference(mCtx);
        ip = globalPreference.RetriveIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_crop_list_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MyCropListModelClass lists = myCropList.get(position);

        holder.cropNameTextView.setText(lists.getCrop_name());
        Glide.with(mCtx)
                .load("http://"+ip+ "/intelligent_farming/admin/tbl_crops/uploads/" + lists.getCrop_image())
                .into(holder.cropImageView);

        holder.crop_list_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, CropLifecycleActivity.class);
                intent.putExtra("cid",lists.getId());
                intent.putExtra("name",lists.getCrop_name());
                intent.putExtra("image",lists.getCrop_image());
                mCtx.startActivity(intent);
//                Toast.makeText(mCtx, ""+lists.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCropList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout crop_list_card;
        private ImageView cropImageView;
        private TextView cropNameTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            crop_list_card = (LinearLayout) itemView.findViewById(R.id.crop_list_card);
            cropImageView = (ImageView) itemView.findViewById(R.id.cropImageView);
            cropNameTextView = (TextView) itemView.findViewById(R.id.cropNameTextView);
        }
    }
}
