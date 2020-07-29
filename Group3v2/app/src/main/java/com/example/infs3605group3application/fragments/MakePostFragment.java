package com.example.infs3605group3application.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.infs3605group3application.R;


public class MakePostFragment extends Fragment {

    public MakePostFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.make_post, container, false);

    return view;
    }


}
