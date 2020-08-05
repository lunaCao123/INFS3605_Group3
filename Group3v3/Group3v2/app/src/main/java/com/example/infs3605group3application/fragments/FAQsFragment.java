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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.infs3605group3application.HomePage.FAQSAdapter;
import com.example.infs3605group3application.Model.FAQS;
import com.example.infs3605group3application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FAQsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<FAQS> list;
    FAQSAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frequenly_asked_questions, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        list = new ArrayList<FAQS>();
        adapter = new FAQSAdapter(getActivity(),list);
        adapter.setOnNewsItemClickListener(new FAQSAdapter.OnNewsItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }


        });
        return view;
    }
    private void initData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("FAQS")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        swipeRefreshLayout.setRefreshing(false);
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FAQS post = document.toObject(FAQS.class);
                                list.add(post);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                        }
                    }
                });
    }
}
