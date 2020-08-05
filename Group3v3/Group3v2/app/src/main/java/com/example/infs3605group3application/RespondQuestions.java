package com.example.infs3605group3application;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
    private Button bt_delete;

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
        bt_delete =  findViewById(R.id.bt_delete);
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
                        db.collection("FAQS").document(crisis.getKey()).delete();
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
