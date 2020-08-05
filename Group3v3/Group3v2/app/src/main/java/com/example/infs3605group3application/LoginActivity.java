package com.example.infs3605group3application;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605group3application.Model.Author;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class LoginActivity extends AppCompatActivity {
        private Button login;
    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login_page);
        login = findViewById(R.id.btn_login);
        editTextUsername  = findViewById(R.id.et_username);
        editTextPassword = findViewById(R.id.et_password);

        //if password verification is correct
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextUsername.getText().toString();
                String password  =editTextPassword.getText().toString();
                login(name,password);
            }
        });
    }

    private void login(final String name, String password){
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
                                SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putString("name",name);
                                edit.putBoolean("login",true);
                                edit.apply();
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this,"Incorrect account password",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(LoginActivity.this,"Incorrect account password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



}
}
