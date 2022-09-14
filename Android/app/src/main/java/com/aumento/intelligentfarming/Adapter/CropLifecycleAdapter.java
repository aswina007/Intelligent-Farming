package com.aumento.intelligentfarming.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.aumento.intelligentfarming.ModelClass.CropLifecycleModelClass;
import com.aumento.intelligentfarming.R;
import com.aumento.intelligentfarming.utils.GlobalPreference;

import java.util.List;

public class CropLifecycleAdapter extends RecyclerView.Adapter<CropLifecycleAdapter.MyViewHolder> {

    private final String ip;
    private List<CropLifecycleModelClass> cropLifecycle;
    private Context mCtx;

    public CropLifecycleAdapter(List<CropLifecycleModelClass> cropLifecycle, Context mCtx) {
        this.cropLifecycle = cropLifecycle;
        this.mCtx = mCtx;
        GlobalPreference globalPreference = new GlobalPreference(mCtx);
        ip = globalPreference.RetriveIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crop_lifecycle_list_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CropLifecycleModelClass lists = cropLifecycle.get(position);

        holder.weekTextView.setText(lists.getWeek());
        holder.descTextView.setText(lists.getDesc());

    }

    @Override
    public int getItemCount() {
        return cropLifecycle.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView weekTextView,descTextView ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            weekTextView = (TextView) itemView.findViewById(R.id.cropWeekTextView);
            descTextView = (TextView) itemView.findViewById(R.id.cropDescTextView);
        }
    }
}
