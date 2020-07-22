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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakePost extends AppCompatActivity {
    private EditText Title;
    private EditText Message;
    private Spinner CrisisType;
    private Spinner PostType;
    private TextView FileName;
    private EditText Author;
    private EditText PubDate;
    private String title;
    private String message;
    private String crisisType;
    private String postType;
    private String author;
    private String pubDate;
    private Button makePostButton;
    private Button ChooseFiles;
    private static final int PICKFILE_RESULT_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_post);

        //Retrieve data from form
        Title = findViewById(R.id.Et_Title);
        title = Title.getText().toString();
        addItemsCrisisType();
        CrisisType = findViewById(R.id.Db_CrisisType);
        crisisType = CrisisType.getSelectedItem().toString();
        addItemsPostType();
        FileName = findViewById(R.id.Tv_FileName);
        PostType = findViewById(R.id.Db_PostType);
        postType = PostType.getSelectedItem().toString();
        Author = findViewById(R.id.Et_Author);
        author = Author.getText().toString();
        PubDate = findViewById(R.id.Db_pubDate);
        pubDate = PubDate.getText().toString();
        Message = findViewById(R.id.Et_Message);
        message = Message.getText().toString();
        ChooseFiles = findViewById(R.id.Bt_ChooseFiles);

        ChooseFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent,PICKFILE_RESULT_CODE);
            }
        });

        //Set up listener that submits form to database
        makePostButton = new Button(this);
        makePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewPost(1, author, pubDate, title, message, crisisType, postType);
            }
        });
    }

    //Method is used when button on page is pushed to send data to database
    private void writeNewPost(int postNumber, String authorId, String pubDate, String title, String messageContent, String crisisCode, String urgency) {
        //Establish database connection
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //put data in form ready to push to database
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(postNumber, authorId, pubDate, title, messageContent, crisisCode, urgency);
        Map<String, Object> postValues = post.toMap();

        //Add data to all relevant "children"
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts" + key, postValues);
        childUpdates.put("/author-posts" + authorId + "/" + key, postValues);
        childUpdates.put("/crisis-posts" + crisisCode + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String FilePath = data.getData().getPath();
                    FileName.setText(FilePath + " attached");
                }
                break;
        }
    }

    //Add data into spinner for CrisisType
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
