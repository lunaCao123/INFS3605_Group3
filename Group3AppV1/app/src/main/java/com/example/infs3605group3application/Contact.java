package com.example.infs3605group3application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Contact extends AppCompatActivity {
    private Spinner CrisisType;
    private EditText Subject;
    private EditText ContactInfo;
    private EditText Message;
    private TextView MoreInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        CrisisType = findViewById(R.id.Db_CrisisTypeQ);
        Subject = findViewById(R.id.Et_Subject);
        ContactInfo = findViewById(R.id.Et_ContactInfo);
        Message = findViewById(R.id.Et_MessageQ);
        MoreInfo = findViewById(R.id.Tv_redirect);
        MoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Contact_Details.class);
                startActivity(intent);
            }
        });
        addItemsCrisisType();
    }
    public void addItemsCrisisType(){
        CrisisType = findViewById(R.id.Db_CrisisTypeQ);
        List<String> crisistype = new ArrayList<String>();
        crisistype.add("COVID-19");
        crisistype.add("Bushfires");
        crisistype.add("Floods");
        crisistype.add("Riots");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, crisistype);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CrisisType.setAdapter(dataAdapter);

    }
}
