package com.aumento.intelligentfarming.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumento.intelligentfarming.CropLifecycleActivity;
import com.aumento.intelligentfarming.ModelClass.MyCropListModelClass;
import com.aumento.intelligentfarming.ModelClass.NewsListModelClass;
import com.aumento.intelligentfarming.NewsDetailsActivity;
import com.aumento.intelligentfarming.R;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.bumptech.glide.Glide;

import java.util.List;

public class newsListAdapter extends RecyclerView.Adapter<newsListAdapter.MyViewHolder> {

    private final String ip;
    private List<NewsListModelClass> newsList;
    private Context mCtx;

        public newsListAdapter(List<NewsListModelClass> newsList, Context mCtx) {
        this.newsList = newsList;
        this.mCtx = mCtx;

        GlobalPreference globalPreference = new GlobalPreference(mCtx);
        ip = globalPreference.RetriveIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_news_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NewsListModelClass lists = newsList.get(position);

        final String headline = lists.getHeadline();
        final String desc = lists.getDesc();
        final String image = lists.getImage();

        Log.d("*******", "onBindViewHolder: "+headline+ "  "+ desc);

        holder.newsHeadlineTextView.setText(headline);
        holder.newsDescTextView.setText(desc);
        Glide.with(mCtx)
                .load("http://"+ip+ "/intelligent_farming/admin/tbl_agriculture/uploads/" + image)
                .into(holder.ImageView);

        holder.news_list_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, NewsDetailsActivity.class);
                intent.putExtra("headline", headline);
                intent.putExtra("desc", desc);
                intent.putExtra("image", image);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout news_list_card;
        private ImageView ImageView;
        private TextView newsDescTextView, newsHeadlineTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            news_list_card = (LinearLayout) itemView.findViewById(R.id.news_list_card);
            ImageView = (ImageView) itemView.findViewById(R.id.ImageView);
            newsHeadlineTextView = (TextView) itemView.findViewById(R.id.newsHeadlineTextView);
            newsDescTextView = (TextView) itemView.findViewById(R.id.newsDescTextView);
        }
    }
}
