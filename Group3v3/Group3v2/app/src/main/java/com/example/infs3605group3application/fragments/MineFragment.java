package com.example.infs3605group3application.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.infs3605group3application.AnswerQuestions;
import com.example.infs3605group3application.CrisisActivity;
import com.example.infs3605group3application.LoginActivity;
import com.example.infs3605group3application.MainActivity;
import com.example.infs3605group3application.MakePost;
import com.example.infs3605group3application.Model.Author;
import com.example.infs3605group3application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MineFragment extends Fragment {
        private TextView tvName;
    private LinearLayout ll_code;
    private LinearLayout ll_post;
    private LinearLayout ll_post_manager;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        tvName = view.findViewById(R.id.tv_username);
        ll_code  = view.findViewById(R.id.ll_code);
        ll_post = view.findViewById(R.id.ll_post);
        ll_post_manager = view.findViewById(R.id.ll_post_manager);

        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp =getActivity(). getSharedPreferences("info", Context.MODE_PRIVATE);
                boolean isLogin = sp.getBoolean("login", false);
                if (isLogin){
                    ((MainActivity)getActivity()).toLoginOut();
                }
            }
        });
        //if password verification is correct
        ll_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp =getActivity(). getSharedPreferences("info", Context.MODE_PRIVATE);
                boolean isLogin = sp.getBoolean("login", false);
                if (!isLogin){
                    toLogin();
                }else{
                    startActivity(new Intent(getActivity(), CrisisActivity.class));
                }
            }
        });
        ll_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp =getActivity(). getSharedPreferences("info", Context.MODE_PRIVATE);
                boolean isLogin = sp.getBoolean("login", false);
                if (!isLogin){
                  toLogin();
                }else{
                    startActivity(new Intent(getActivity(), MakePost.class));
                }
            }
        });
        ll_post_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp =getActivity(). getSharedPreferences("info", Context.MODE_PRIVATE);
                boolean isLogin = sp.getBoolean("login", false);
                if (!isLogin){
                    toLogin();
                }else{
                    startActivity(new Intent(getActivity(), AnswerQuestions.class));
                }
            }
        });
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp =getActivity(). getSharedPreferences("info", Context.MODE_PRIVATE);
                boolean isLogin = sp.getBoolean("login", false);
                if (!isLogin){
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                }

            }
        });
        return view;

    }



    private void toLogin() {

        new AlertDialog.Builder(getActivity())
                .setTitle("Alert")
                .setMessage("Please log in first")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp =getActivity(). getSharedPreferences("info", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("login", false);
        if (isLogin){
            String name = sp.getString("name", "Login");
            tvName.setText(name);
        }else{
            tvName.setText("Login");
        }
    }


}
