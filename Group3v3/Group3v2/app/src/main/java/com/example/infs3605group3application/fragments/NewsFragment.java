package com.example.infs3605group3application.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.infs3605group3application.HomePage.PostAdapter;
import com.example.infs3605group3application.Model.Post;
import com.example.infs3605group3application.NewsDetailActivity;
import com.example.infs3605group3application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Post> list;
    PostAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchBar;
    private ImageView ivCategory;


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
        ivCategory = view.findViewById(R.id.iv_category);
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

            //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    filter("");
                }
                return false;
            }
        });
        ivCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showType();
            }
        });
        return view;
    }

    private void showType() {
        List<String> crisistype = new ArrayList<String>();
        crisistype.add("All");
        crisistype.add("COVID-19");
        crisistype.add("Bushfires");
        crisistype.add("Droughts");
        crisistype.add("Floods");
        crisistype.add("Riots");
        final String[] str2=crisistype.toArray(new String[crisistype.size()]);
        new AlertDialog.Builder(getActivity())
                .setTitle("Type")
                .setSingleChoiceItems(str2, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which==0){
                            initData("");
                        }else{
                            initDataByTye(str2[which]);
                        }
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void filter(String query) {
       initData(query);
    }
    private void initDataByTye(final String key) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").whereEqualTo("crisisCode",key)
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
                                list.add(post);

                            }
                            adapter.notifyDataSetChanged();
                        } else {
                        }
                    }
                });
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
                                    if (post.getTitle().toLowerCase().contains(key.toLowerCase())){
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
