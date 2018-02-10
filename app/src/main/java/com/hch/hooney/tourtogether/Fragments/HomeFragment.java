package com.hch.hooney.tourtogether.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.MyCourse;
import com.hch.hooney.tourtogether.DAO.mainPostItem;
import com.hch.hooney.tourtogether.PostEditActivity;
import com.hch.hooney.tourtogether.R;
import com.hch.hooney.tourtogether.Recycler.Main.mainPostAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private final String TAG = "HomeFragment";
    private ProgressDialog asyncDialog;
    //Layout Resource
    private RecyclerView mainRecyclerview;
    private FloatingActionButton createPost;

    private ArrayList<mainPostItem> mainList;

    //variable
    private View view;
    private DatabaseReference rootRef;
    private DatabaseReference postRef;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        setEvent();
        setUI();

        return view;
    }

    private void init(){
        asyncDialog = new ProgressDialog(getActivity());
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));
        //firebase;
        rootRef = FirebaseDatabase.getInstance().getReference();
        postRef = rootRef.child("post");
        mainList = new ArrayList<mainPostItem>();
        createPost = (FloatingActionButton) view.findViewById(R.id.home_edit_post);

        mainRecyclerview = (RecyclerView) view.findViewById(R.id.home_mainRecyclerView);
        mainRecyclerview.setHasFixedSize(false);
        mainRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void setEvent(){
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PostEditActivity.class));
            }
        });
    }

    private void setUI(){
        mainRecyclerview.setAdapter(new mainPostAdapter(getContext(), mainList, mainRecyclerview));
    }

    @Override
    public void onResume() {

        loadDataOnServer();

        super.onResume();
    }

    private void loadDataOnServer(){
        asyncDialog.show();
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mainList.clear();
                for(DataSnapshot items : dataSnapshot.getChildren()){
                    mainPostItem item = items.getValue(mainPostItem.class);
                    item.setContentID(items.getKey());
                    mainList.add(0, item);
                }

                mainRecyclerview.getAdapter().notifyDataSetChanged();
                asyncDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
