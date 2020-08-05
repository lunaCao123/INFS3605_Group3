package com.example.infs3605group3application;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.infs3605group3application.Model.Crisis;
import com.example.infs3605group3application.Model.Post;

public class CrisisDetailActivity extends AppCompatActivity {
    private Crisis crisis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crisis);
         crisis = (Crisis) getIntent().getSerializableExtra("crisis");
        TextView tvSubject =  findViewById(R.id.tvSubject);
        TextView tv_category =  findViewById(R.id.tv_category);
        TextView et_contactInfo =  findViewById(R.id.et_contactInfo);
        TextView tv_description =  findViewById(R.id.tv_description);

        tvSubject.setText(crisis.getSubject());
        tv_category.setText(crisis.getCategory());
        et_contactInfo.setText(crisis.getContactInfo());
        tv_description.setText(crisis.getDescription());


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(CrisisDetailActivity.this ,AnswerQuestions.class);
                intent.putExtra("crisis",crisis);
                startActivity(intent);
                finish();
            }
        });
    }
}
