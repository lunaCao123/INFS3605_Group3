package com.example.infs3605group3application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605group3application.Model.Crisis;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.CrisisViewHolder> {
    private ArrayList<Crisis> mCrisis;
    private RecyclerViewClickListener mListener;


    public Adapter(ArrayList<Crisis> crisis, RecyclerViewClickListener listener) {
        mCrisis = crisis;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public static class CrisisViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView email;
        public TextView type;
        public TextView subject;
        public TextView question;
        public TextView date;
        private RecyclerViewClickListener mListener;

        public CrisisViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            mListener = listener;
            v.setOnClickListener(this);
            email = v.findViewById(R.id.Contact);
            type = v.findViewById(R.id.Tv_CrisisType2);
            subject = v.findViewById(R.id.Tv_Subject);
            question = v.findViewById(R.id.Tv_Question2);
            date = v.findViewById(R.id.Tv_Date)
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public Adapter.CrisisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_questions, parent, false);
        return new CrisisViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(CrisisViewHolder holder, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("crisis")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Crisis crisis = document.toObject(Crisis.class);
                                mCrisis.add(crisis);
                            }
                            Adapter.notifyDataSetChanged();
                        } else {
                        }
                    }
                });
        Crisis crisis = mCrisis.get(position);
        holder.email.setText(mCrisis.add("contactInfo"));
        holder.type.setText(mCrisis.add("category"));
        holder.subject.setText(mCrisis.add("subject"));
        holder.question.setText(mCrisis.add("description");
        holder.date.setText((mCrisis.add("date")));
    }

    @Override
    public int getItemCount() {
        return mCrisis.size();

    }
}