package com.example.infs3605group3application.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.infs3605group3application.AnswerQuestions;
import com.example.infs3605group3application.MakePost;
import com.example.infs3605group3application.Model.Author;
import com.example.infs3605group3application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class LoginFragment extends Fragment {
        private Button login;
    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_page, container, false);
        login = view.findViewById(R.id.loginButton);
        editTextUsername  = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);

        //if password verification is correct
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name = editTextUsername.getText().toString();
               String password  =editTextPassword.getText().toString();
               login(name,password);
            }
        });
        return view;
    }
    private void login(String name,String password){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference authorsRef = db.collection("authors");
        authorsRef.whereEqualTo("password", password).whereEqualTo("name", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            Author author=null;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // Log.d(TAG, document.getId() + " => " + document.getData());
                                author=   document.toObject(Author.class);
                            }
                            if (author!=null){
                                Intent intent = new Intent(getActivity(), AnswerQuestions.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getActivity(),"Incorrect account password",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getActivity(),"Incorrect account password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



}
}
