package com.aumento.intelligentfarming.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.aumento.intelligentfarming.ModelClass.QueryAnswerModelClass;
import com.aumento.intelligentfarming.R;
import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.bumptech.glide.Glide;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class QueryAnswerListAdapter extends RecyclerView.Adapter<QueryAnswerListAdapter.MyViewHolder> {

    private final String ip;
    private List<QueryAnswerModelClass> answerList;
    private Context mCtx;
    boolean more = false;

    public QueryAnswerListAdapter(List<QueryAnswerModelClass> answerList, Context mCtx) {
        this.answerList = answerList;
        this.mCtx = mCtx;

        GlobalPreference globalPreference = new GlobalPreference(mCtx);
        ip = globalPreference.RetriveIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_query_answer_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final QueryAnswerModelClass lists = answerList.get(position);


        holder.qUserNameTextView.setText(lists.getName());
        holder.qDateTextView.setText(lists.getDate());
        holder.qAnswerTextView.setText(lists.getAnswer());

        Log.d("***", "onBindViewHolder: "+holder.qAnswerTextView.getLineCount());
/*
        if(holder.qAnswerTextView.getLineHeight() > 38 ) {
            holder.qShowTextView.setVisibility(View.VISIBLE);
//            holder.qAnswerTextView.setMaxLines(3);
        }
        else
            holder.qShowTextView.setVisibility(View.GONE);*/

        holder.qShowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(mCtx, "asasd"+holder.qAnswerTextView.getLineCount(), Toast.LENGTH_SHORT).show();

                if(holder.qShowTextView.getText().toString().equals("Show more"))
                {
                    holder.qAnswerTextView.setMaxLines(Integer.MAX_VALUE);//your TextView
                    holder.qShowTextView.setText("Show less");
                }
                else
                {
                    holder.qAnswerTextView.setMaxLines(3);
                    holder.qShowTextView.setText("Show more");
                }

            }
        });

        Glide.with(mCtx)
                .load("http://"+ip+ "/intelligent_farming/admin/tbl_user/uploads/" + lists.getImage())
                .into(holder.userAnswerImageView);

    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView userAnswerImageView;
        private TextView qUserNameTextView, qDateTextView, qAnswerTextView, qShowTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userAnswerImageView = (CircleImageView) itemView.findViewById(R.id.userAnswerImageView);
            qUserNameTextView = (TextView) itemView.findViewById(R.id.qUserNameTextView);
            qDateTextView = (TextView) itemView.findViewById(R.id.qDateTextView);
            qAnswerTextView = (TextView) itemView.findViewById(R.id.qAnswerTextView);
            qShowTextView = (TextView) itemView.findViewById(R.id.qShowTextView);
        }
    }
}
