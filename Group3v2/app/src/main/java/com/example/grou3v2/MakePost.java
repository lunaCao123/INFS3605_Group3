package com.example.grou3v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grou3v2.Model.Author;
import com.example.grou3v2.Model.Crisis;
import com.example.grou3v2.Model.Post;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakePost extends AppCompatActivity {
    private EditText Title;
    private EditText Message;
    private Spinner CrisisType;
    private Spinner PostType;
    private Button ChooseFiles;
    private Button Post;
    private TextView FileName;
    private static final int PICKFILE_RESULT_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_post);
        Title = findViewById(R.id.Et_Title);
        Message = findViewById(R.id.Et_Message);
        ChooseFiles = findViewById(R.id.Bt_ChooseFiles);
        Post = findViewById(R.id.Bt_Post);
        FileName = findViewById(R.id.Tv_FileName);
        addItemsCrisisType();
        addItemsPostType();

        ChooseFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent,PICKFILE_RESULT_CODE);
            }
        });

    }

    private void writeNewPost(int postNumber, String authorId, int pubDate, String title, String messageContent, String crisisCode, String urgency) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(postNumber, authorId, pubDate, title, messageContent, crisisCode, urgency);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts" + key, postValues);
        childUpdates.put("/author-posts" + authorId + "/" + key, postValues);
        childUpdates.put("/crisis-posts" + crisisCode + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    //TODO: write onCLick method for post writing page
    //@Override
    //public void onClick()

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    String FilePath = data.getData().getPath();
                    FileName.setText(FilePath + " attached");
                }
                break;
        }
    }


    public void addItemsCrisisType(){
        CrisisType = findViewById(R.id.Db_CrisisType);
        List<String> crisistype = new ArrayList<String>();
        crisistype.add("COVID-19");
        crisistype.add("Bushfires");
        crisistype.add("Floods");
        crisistype.add("Riots");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, crisistype);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CrisisType.setAdapter(dataAdapter);
    }

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
