package com.example.infs3605group3application.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.infs3605group3application.HomePage.CrisisAdapter;
import com.example.infs3605group3application.HomePage.PostAdapter;
import com.example.infs3605group3application.Model.Crisis;
import com.example.infs3605group3application.Model.Post;
import com.example.infs3605group3application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CrisisFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Crisis> list;
    private CrisisAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crisis_page, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        list = new ArrayList<Crisis>();
        adapter = new CrisisAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("crisis")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Crisis crisis = document.toObject(Crisis.class);
                                list.add(crisis);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                        }
                    }
                });

        return view;
    }
}
