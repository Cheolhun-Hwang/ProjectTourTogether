package com.hch.hooney.tourtogether.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hch.hooney.tourtogether.R;
import com.hch.hooney.tourtogether.Recycler.Main.mainPostAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private final String TAG = "HomeFragment";

    //Layout Resource
    private RecyclerView mainRecyclerview;

    //variable
    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();

        setUI();

        return view;
    }

    private void init(){
        mainRecyclerview = (RecyclerView) view.findViewById(R.id.home_mainRecyclerView);
        mainRecyclerview.setHasFixedSize(false);
        mainRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void setUI(){
        mainRecyclerview.setAdapter(new mainPostAdapter(getContext()));
    }

}
