package com.example.infs3605group3application;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605group3application.Model.Crisis;
import com.example.infs3605group3application.Model.FAQS;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RespondQuestions extends AppCompatActivity {
    private TextView spType;
    private TextView EtSubject;
    private TextView EtMessageQ;
    private FAQS crisis;
    private TextView et_reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.respond_questions);
        crisis = (FAQS) getIntent().getSerializableExtra("FAQS");
        spType = findViewById(R.id.sp_type);
        EtSubject = findViewById(R.id.Et_Subject);
        EtMessageQ = findViewById(R.id.Et_MessageQ);
        et_reply = findViewById(R.id.et_reply);
        spType.setText(crisis.getCategory());
        EtSubject.setText(crisis.getSubject());
        EtMessageQ.setText(crisis.getDescription());
        String reply = crisis.getReply();
        if (!TextUtils.isEmpty(reply)){
            et_reply.setText(crisis.getReply());
        }



    }



}
