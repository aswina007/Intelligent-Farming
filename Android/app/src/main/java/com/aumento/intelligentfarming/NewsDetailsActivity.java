package com.aumento.intelligentfarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.aumento.intelligentfarming.utils.GlobalPreference;
import com.bumptech.glide.Glide;

public class NewsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
        String ip = globalPreference.RetriveIP();

        Intent intent = getIntent();
        String headline = intent.getStringExtra("headline");
        String desc = intent.getStringExtra("desc");
        String image = intent.getStringExtra("image");

        ImageView imageView = (ImageView) findViewById(R.id.ImageView);
        TextView newsHeadlineTextView = (TextView) findViewById(R.id.newsHeadlineTextView);
        TextView newsDescTextView = (TextView) findViewById(R.id.newsDescTextView);

        newsHeadlineTextView.setText(headline);
        newsDescTextView.setText(desc);

         Glide.with(getApplicationContext())
                .load("http://"+ip+"/intelligent_farming/admin/tbl_agriculture/uploads/" + image)
                .into(imageView);

    }
}
