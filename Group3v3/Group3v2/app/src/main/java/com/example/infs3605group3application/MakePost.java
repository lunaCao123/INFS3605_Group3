package com.example.infs3605group3application;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.infs3605group3application.Model.Post;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakePost extends AppCompatActivity {
    private FirebaseFunctions mFunctions;
    private EditText Title;
    private EditText Message;
    private Spinner CrisisType;
    private Spinner PostType;
    private TextView FileName;
    private TextView Author;

    private Button makePostButton;
    private Button ChooseFiles;
    private static final int PICKFILE_RESULT_CODE = 1;
    private ProgressDialog progressDialog;
    private StorageReference mStoreReference;
    private String imageUrl;
    private ImageView ivImg;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_post);
        SharedPreferences sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        String name =  sp.getString("name", "");

        mFunctions = FirebaseFunctions.getInstance();
        progressDialog = new ProgressDialog(this);
        //Retrieve data from form
        Title = findViewById(R.id.Et_Title);

        addItemsCrisisType();
        CrisisType = findViewById(R.id.Db_CrisisType);

        addItemsPostType();
        FileName = findViewById(R.id.Tv_FileName);
        PostType = findViewById(R.id.Db_PostType);

        Author = findViewById(R.id.Et_Author);
        Author.setText(name);
        ivImg = findViewById(R.id.iv_img);
        final Date pubdate = Calendar.getInstance().getTime();

        Message = findViewById(R.id.Et_Message);

        ChooseFiles = findViewById(R.id.Bt_ChooseFiles);

        ChooseFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MakePost.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MakePost.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                    return;
                }
                if (ActivityCompat.checkSelfPermission(MakePost.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MakePost.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PICKFILE_RESULT_CODE);
                }
            }
        });

        //Set up listener that submits form to database
        makePostButton = findViewById(R.id.Bt_Post);
        makePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  title = Title.getText().toString();
                String  postType = PostType.getSelectedItem().toString();
                String  crisisType = CrisisType.getSelectedItem().toString();
                String author = Author.getText().toString();
                String pubDate = pubdate.toString();
                String  message = Message.getText().toString();
                if (TextUtils.isEmpty(imageUrl)){
                    imageUrl="";
                }
                writeNewPost(1, author, pubDate, title, message, crisisType, postType);
            }
        });
    }

    private void upload(Uri selectedUri) {
        progressDialog.show();
        mStoreReference=  FirebaseStorage.getInstance().getReference();
        final StorageReference riversRef = mStoreReference.child("img/"+System.currentTimeMillis()+".jpg");
        UploadTask uploadTask = riversRef.putFile(selectedUri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {


            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                     imageUrl = downloadUri.toString();
                     Glide.with(MakePost.this).load(imageUrl).into(ivImg);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

    }
    //Method is used when button on page is pushed to send data to database
    private void writeNewPost(int postNumber, String authorId, String pubDate, String title, String messageContent, String crisisCode, String urgency) {
        progressDialog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object>  posts= new HashMap<>();
        posts.put("postNumber", postNumber);
        posts.put("authorId", authorId);
        posts.put("pubDate", pubDate);
        posts.put("title", title);
        posts.put("messageContent", messageContent);
        posts.put("crisisCode", crisisCode);
        posts.put("urgency", urgency);
        posts.put("imageUrl", imageUrl);
        db.collection("posts")
                .add(posts)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MakePost.this,"Success",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MakePost.this,"Error adding",Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                final Uri selectedUri = data.getData();
                upload(selectedUri);
                break;
        }
    }

    //Add data into spinner for CrisisType
    public void addItemsCrisisType(){
        CrisisType = findViewById(R.id.Db_CrisisType);
        List<String> crisistype = new ArrayList<String>();
        crisistype.add("COVID-19");
        crisistype.add("Bushfires");
        crisistype.add("Droughts");
        crisistype.add("Floods");
        crisistype.add("Riots");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, crisistype);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CrisisType.setAdapter(dataAdapter);
    }

    //Add data into spinner for PostType
    public void addItemsPostType(){
        PostType = findViewById(R.id.Db_PostType);
        List<String> postType = new ArrayList<String>();
        postType.add("Emergency");
        postType.add("Update");
        postType.add("Services");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, postType);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PostType.setAdapter(dataAdapter);

    }
}
