package com.example.infs3605group3application;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AnswerQuestions extends AppCompatActivity {
    private TextView Contact;
    private TextView Date;
    private TextView Question;
    private TextView Subject;
    private TextView Crisis;
    private EditText Answer;
    private Button Respond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_questions);

        Contact = findViewById(R.id.Contact);
        Date = findViewById(R.id.Tv_Date);
        Question = findViewById(R.id.Tv_Question2);
        Subject = findViewById(R.id.Tv_Subject);
        Crisis = findViewById(R.id.Tv_CrisisType2);

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("crisis");
        mDatabase.addChildEventListener(new ChildEventListener(){

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String crisis = snapshot.child("category").getValue().toString();
                String contact = snapshot.child("contactInfo").getValue().toString();
                String date = snapshot.child("date").getValue().toString();
                String question = snapshot.child("description").getValue().toString();
                String subject = snapshot.child("subject").getValue().toString();
                Contact.setText(contact);
                Date.setText(date);
                Question.setText(question);
                Subject.setText(subject);
                Crisis.setText(crisis);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
