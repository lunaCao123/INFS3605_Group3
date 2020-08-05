package com.example.infs3605group3application.HomePage;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infs3605group3application.Model.Post;

import com.example.infs3605group3application.R;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.title.setText(posts.get(position).getTitle());
        holder.crisisTypeText.setText(posts.get(position).getCrisisCode());
        holder.pubDate.setText(posts.get(position).getPubDate());
        if (!TextUtils.isEmpty(posts.get(position).getImageUrl())){
            Glide.with(context).load(posts.get(position).getImageUrl()).into(holder.postPhoto);
        }else{
            if (posts.get(position).getCrisisCode().equals("COVID-19")){
                holder.postPhoto.setImageResource(R.drawable.pendmic);
            }else if (posts.get(position).getCrisisCode().equals("Bushfires")){
                holder.postPhoto.setImageResource(R.drawable.fire);
            }else if (posts.get(position).getCrisisCode().equals("Droughts")){
                holder.postPhoto.setImageResource(R.drawable.drought);
            }else if (posts.get(position).getCrisisCode().equals("Floods")){
                holder.postPhoto.setImageResource(R.drawable.floor);
            }else if (posts.get(position).getCrisisCode().equals("Riots")){
                holder.postPhoto.setImageResource(R.drawable.riots);
            }else{
                holder.postPhoto.setImageResource(R.drawable.logo);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNewsItemClickListener!=null){
                    onNewsItemClickListener.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    OnNewsItemClickListener onNewsItemClickListener;
    public interface OnNewsItemClickListener{
        void onClick(int position);
    }

    public void setOnNewsItemClickListener(OnNewsItemClickListener onNewsItemClickListener) {
        this.onNewsItemClickListener = onNewsItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, pubDate,crisisTypeText;
        ImageView postPhoto;
        public MyViewHolder(View itemView) {
            super(itemView);
            crisisTypeText = (TextView) itemView.findViewById(R.id.crisisTypeText);
            title = (TextView) itemView.findViewById(R.id.title);
            pubDate = (TextView) itemView.findViewById(R.id.Db_pubDate);
            postPhoto = (ImageView) itemView.findViewById(R.id.postPhoto);
        }

    }
}
