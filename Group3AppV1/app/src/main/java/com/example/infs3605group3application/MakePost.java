package com.example.infs3605group3application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

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
        List<String> posttype = new ArrayList<String>();
        posttype.add("Emergency");
        posttype.add("Update");
        posttype.add("Services");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, posttype);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PostType.setAdapter(dataAdapter);

    }
}
