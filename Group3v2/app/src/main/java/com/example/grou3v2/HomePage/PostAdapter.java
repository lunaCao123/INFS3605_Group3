package com.example.grou3v2.HomePage;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grou3v2.Model.Post;
import com.example.grou3v2.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    Context context;
    ArrayList<Post> posts;

    public PostAdapter(Context c, ArrayList<Post> p) {
        context = c;
        posts = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.post,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(posts.get(position).getTitle());
        holder.pubDate.setText(posts.get(position).getPubDate());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, pubDate;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            pubDate = (TextView) itemView.findViewById(R.id.pubDate);
        }

    }
}
