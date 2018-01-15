package com.hch.hooney.tourtogether.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hch.hooney.tourtogether.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private final String TAG = "SearchFragment";

    //Layout Resource

    //variable
    private View view;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

}
