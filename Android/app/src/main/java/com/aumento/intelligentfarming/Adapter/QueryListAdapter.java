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
import com.aumento.intelligentfarming.ModelClass.QueryQuestionsModelClass;
import com.aumento.intelligentfarming.ModelClass.QueryQuestionsModelClass;
import com.aumento.intelligentfarming.QueryAnswerActivity;
import com.aumento.intelligentfarming.R;
import com.aumento.intelligentfarming.utils.GlobalPreference;

import java.util.List;

public class QueryListAdapter extends RecyclerView.Adapter<QueryListAdapter.MyViewHolder> {

    private List<QueryQuestionsModelClass> queryList;
    private Context mCtx;

        public QueryListAdapter(List<QueryQuestionsModelClass> queryList, Context mCtx) {
        this.queryList = queryList;
        this.mCtx = mCtx;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_query_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final QueryQuestionsModelClass lists = queryList.get(position);

        final String qtn = lists.getQtn();
        final String date = lists.getDate();
        final String id = lists.getId();

        holder.queryTitleTextView.setText(qtn);
        holder.queryDateTextView.setText(date);

        holder.query_list_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mCtx, QueryAnswerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("qtn", qtn);
                intent.putExtra("date", date);
                intent.putExtra("id", id);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return queryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout query_list_card;
        private TextView queryTitleTextView, queryDateTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            query_list_card = (LinearLayout) itemView.findViewById(R.id.query_list_card);
            queryTitleTextView = (TextView) itemView.findViewById(R.id.queryTitleTextView);
            queryDateTextView = (TextView) itemView.findViewById(R.id.queryDateTextView);
        }
    }
}
