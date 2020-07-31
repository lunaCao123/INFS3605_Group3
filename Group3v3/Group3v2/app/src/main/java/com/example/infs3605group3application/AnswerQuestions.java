package com.example.infs3605group3application;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AnswerQuestions extends AppCompatActivity {
    private TextView Contact;
    private TextView Date;
    private TextView Question;
    private TextView Subject;
    private TextView Crisis;
    private EditText Answer;
    private Button Respond;
    private CheckBox FAQ;

    private ProgressDialog progressDialog;
    private StorageReference mStoreReference;
    private FirebaseFunctions mFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_questions);

        Contact = findViewById(R.id.Contact);
        Date = findViewById(R.id.Tv_Date);
        Question = findViewById(R.id.Tv_Question2);
        Subject = findViewById(R.id.Tv_Subject);
        Crisis = findViewById(R.id.Tv_CrisisType2);
        Answer = findViewById(R.id.Et_AnswerQ);
        Respond = findViewById(R.id.Bt_Respond);
        FAQ = findViewById(R.id.Cb_FAQs);
        //TO DO use adapter to set XML based on information stored in database

        mFunctions = FirebaseFunctions.getInstance();
        progressDialog = new ProgressDialog(this);

        Respond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FAQ.isSelected()) {
                    String answer = Answer.getText().toString();
                    String subject = Subject.toString();
                    String question = Question.toString();
                    String date = Date.toString();
                    String crisis = Crisis.toString();
                    writeNewAnswer(1, answer, subject, question, date, crisis);
                }
            }
        });
    }
    private void writeNewAnswer(int postNumber, String answer, String subject, String question, String date, String crisis) {
        progressDialog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> posts= new HashMap<>();
        posts.put("postNumber", postNumber);
        posts.put("answer", answer);
        posts.put("subject", subject);
        posts.put("question", question);
        posts.put("date", date);
        posts.put("crisisCode", crisis);
        db.collection("faqs")
                .add(posts)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AnswerQuestions.this,"Success",Toast.LENGTH_SHORT).show();
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
    }

}
