package com.hch.hooney.tourtogether.Fragments.Bookmarking;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hch.hooney.tourtogether.R;
import com.hch.hooney.tourtogether.Recycler.Bookmark.Spot.bSpotAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpotFragment extends Fragment {
    private View view;

    private RecyclerView bookmark_spot;

    public SpotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_spot, container, false);

        init();
        setUI();

        return view;
    }

    private void init(){
        bookmark_spot = (RecyclerView) view.findViewById(R.id.bookmark_spot_list);
        bookmark_spot.setHasFixedSize(false);
        bookmark_spot.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void setUI(){
        bookmark_spot.setAdapter(new bSpotAdapter(getContext(), bookmark_spot));
    }
}
