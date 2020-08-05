package com.example.infs3605group3application;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.infs3605group3application.Model.Post;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewsDetailActivity extends AppCompatActivity {
    private Post news;
    private Button bt_delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
         news = (Post) getIntent().getSerializableExtra("news");
        TextView tv_title =  findViewById(R.id.tv_title);
        TextView Tv_CrisisType =  findViewById(R.id.Tv_CrisisType);
        TextView tv_auth =  findViewById(R.id.tv_auth);
        TextView tv_postType =  findViewById(R.id.tv_postType);
        TextView pubDate =  findViewById(R.id.tv_date);
        ImageView iv_logo =  findViewById(R.id.iv_logo);
        TextView tv_message =  findViewById(R.id.tv_message);
         bt_delete =  findViewById(R.id.bt_delete);

        tv_title.setText(news.getTitle());
        pubDate.setText(news.getPubDate());
        tv_postType.setText(news.getUrgency());
        tv_auth.setText(news.getAuthorId());
        Tv_CrisisType.setText(news.getCrisisCode());
        tv_message.setText(news.getMessageContent());
        if (!TextUtils.isEmpty(news.getImageUrl())){
            Glide.with(this).load(news.getImageUrl()).into(iv_logo);
        }else{
            iv_logo.setImageResource(R.drawable.logo);
        }
        SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("login", false);
        if (!isLogin){
            bt_delete.setVisibility(View.GONE);

        }else{
            bt_delete.setVisibility(View.VISIBLE);
        }
        findViewById(R.id.bt_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }
    private void delete(){

        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Confirm delete?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("posts").document(news.getKey()).delete();
                        finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();


    }
}
