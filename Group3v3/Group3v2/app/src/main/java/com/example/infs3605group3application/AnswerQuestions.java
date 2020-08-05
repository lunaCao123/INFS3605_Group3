package com.example.infs3605group3application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605group3application.Model.Crisis;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerQuestions extends AppCompatActivity {
    private TextView spType;
    private TextView EtSubject;
    private TextView EtContactInfo;
    private TextView EtMessageQ;
    private ProgressDialog progressDialog;
    private Crisis crisis;
    private CheckBox ck_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_questions);
        crisis = (Crisis) getIntent().getSerializableExtra("crisis");
        progressDialog = new ProgressDialog(this);
        spType = findViewById(R.id.sp_type);
        EtSubject = findViewById(R.id.Et_Subject);
        EtContactInfo = findViewById(R.id.Et_ContactInfo);
        ck_check = findViewById(R.id.ck_check);
        EtMessageQ = findViewById(R.id.Et_MessageQ);
        spType.setText(crisis.getCategory());
        EtSubject.setText(crisis.getSubject());
        EtContactInfo.setText(crisis.getContactInfo());
        EtMessageQ.setText(crisis.getDescription());
        Button btSend = findViewById(R.id.bt_send);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postFaqs();
            }
        });

    }


    private void postFaqs() {
        if (ck_check.isChecked()){
            String subject = EtSubject.getText().toString();
            String contactInfo = EtContactInfo.getText().toString();
            String message = EtMessageQ.getText().toString();
            progressDialog.show();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> maps= new HashMap<>();
            maps.put("description", message);
            maps.put("subject", subject);
            maps.put("category", crisis.getCategory());
            maps.put("contactInfo", contactInfo);
            db.collection("FAQS")
                    .add(maps)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AnswerQuestions.this,"Success",Toast.LENGTH_SHORT).show();
                            EtSubject.setText("");
                            EtContactInfo.setText("");
                            EtMessageQ.setText("");
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AnswerQuestions.this,"Error adding",Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(AnswerQuestions.this,"Respond",Toast.LENGTH_SHORT).show();
        }


    }
}
