package com.example.infs3605group3application.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

;

import com.example.infs3605group3application.HomePage.PostAdapter;
import com.example.infs3605group3application.Model.Post;
import com.example.infs3605group3application.NewsDetailActivity;
import com.example.infs3605group3application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NewsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Post> list;
    PostAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_page, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchBar = view.findViewById(R.id.search_bar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        list = new ArrayList<Post>();
        adapter = new PostAdapter(getActivity(),list);
        adapter.setOnNewsItemClickListener(new PostAdapter.OnNewsItemClickListener() {
            @Override
            public void onClick(int position) {
                Post news = list.get(position);
                Intent intent =  new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("news",news);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData("");
            }
        });
        initData("");
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    private void filter(String query) {
       initData(query);
    }

    private void initData(final String key) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        swipeRefreshLayout.setRefreshing(false);
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Post post = document.toObject(Post.class);
                                post.setKey(document.getId());
                                if (TextUtils.isEmpty(key)){
                                    list.add(post);
                                }else {
                                    if (post.getTitle().contains(key)){
                                        list.add(post);
                                    }
                                }

                            }
                            adapter.notifyDataSetChanged();
                        } else {
                        }
                    }
                });
    }


}
