package com.example.infs3605group3application.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.infs3605group3application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactFragment extends Fragment {

    View rootView;
    private Spinner spType;
    private EditText EtSubject;
    private EditText EtContactInfo;
    private EditText EtMessageQ;
    private String type;
    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.contact, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        spType = rootView.findViewById(R.id.sp_type);
        EtSubject = rootView.findViewById(R.id.Et_Subject);
        EtContactInfo = rootView.findViewById(R.id.Et_ContactInfo);
        EtMessageQ = rootView.findViewById(R.id.Et_MessageQ);
        Button btSend = rootView.findViewById(R.id.bt_send);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCrisis();
            }
        });
        initCategory();
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               type=((TextView)view).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void initCategory() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("crisisCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                             List<String> categoryList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String crisisCategory = (String) document.getData().get("crisisCategory");
                                categoryList.add(crisisCategory);
                            }
                            String[] data = categoryList.toArray(new String[categoryList.size()]);
                            ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_item,
                                    data);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spType.setAdapter(adapter);
                        } else {
                        }
                    }
                });

    }

    private void postCrisis() {


        if (TextUtils.isEmpty(type)){
            return;
        }
        String subject = EtSubject.getText().toString();
        String contactInfo = EtContactInfo.getText().toString();
        String message = EtMessageQ.getText().toString();
        if (TextUtils.isEmpty(subject)){
            EtSubject.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(contactInfo)){
            EtContactInfo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(message)){
            EtMessageQ.requestFocus();
            return;
        }
        progressDialog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object>  crisis= new HashMap<>();
        crisis.put("description", message);
        crisis.put("subject", subject);
        crisis.put("category", type);
        crisis.put("contactInfo", contactInfo);
        db.collection("crisis")
                .add(crisis)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                        EtSubject.setText("");
                        EtContactInfo.setText("");
                        EtMessageQ.setText("");
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"Error adding",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
