package com.example.grou3v2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.grou3v2.Contact_Details;
import com.example.grou3v2.R;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    private Spinner CrisisType;
    private EditText Subject;
    private EditText ContactInfo;
    private EditText Message;
    private TextView MoreInfo;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact, container, false);
        CrisisType = view.findViewById(R.id.Db_CrisisTypeQ);
        Subject = view.findViewById(R.id.Et_Subject);
        ContactInfo = view.findViewById(R.id.Et_ContactInfo);
        Message = view.findViewById(R.id.Et_MessageQ);
        MoreInfo = view.findViewById(R.id.Tv_redirect);
        MoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Contact_Details.class);
                startActivity(intent);
            }
        });
        addItemsCrisisType();
        return view;
    }
    public void addItemsCrisisType(){
        List<String> crisisType = new ArrayList<String>();
        crisisType.add("COVID-19");
        crisisType.add("Bushfires");
        crisisType.add("Floods");
        crisisType.add("Riots");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, crisisType);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CrisisType.setAdapter(dataAdapter);
    }
}