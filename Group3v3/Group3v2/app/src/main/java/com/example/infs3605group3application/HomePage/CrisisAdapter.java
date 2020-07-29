package com.example.infs3605group3application.HomePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infs3605group3application.Model.Crisis;
import com.example.infs3605group3application.Model.Post;
import com.example.infs3605group3application.R;

import java.util.ArrayList;

public class CrisisAdapter extends RecyclerView.Adapter<CrisisAdapter.MyViewHolder> {

    Context context;
    ArrayList<Crisis> posts;

    public CrisisAdapter(Context c, ArrayList<Crisis> p) {
        context = c;
        posts = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cirsis,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(posts.get(position).getSubject());
        holder.pubDate.setText(posts.get(position).getDescription());
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
            pubDate = (TextView) itemView.findViewById(R.id.Db_pubDate);
        }

    }
}
