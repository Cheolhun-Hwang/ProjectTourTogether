package com.hch.hooney.tourtogether.Fragments.Bookmarking;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.mainPostItem;
import com.hch.hooney.tourtogether.R;
import com.hch.hooney.tourtogether.Recycler.Main.mainPostAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MPostFragment extends Fragment {
    private final String TAG = "HomeFragment";
    private ProgressDialog asyncDialog;
    private View view;
    private RecyclerView myLog;

    private ArrayList<mainPostItem> myLogList;
    private DatabaseReference rootRef;
    private DatabaseReference postRef;

    public MPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_mpost, container, false);

        init();
        setUI();

        return view;
    }

    private void init(){
        asyncDialog = new ProgressDialog(getActivity());
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));
        //firebase;
        rootRef = FirebaseDatabase.getInstance().getReference();
        postRef = rootRef.child("ulog").child(DAO.user.getUID());
        myLogList = new ArrayList<mainPostItem>();

        myLog = (RecyclerView) view.findViewById(R.id.myLog_list);
        myLog.setHasFixedSize(false);
        myLog.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void setUI(){
        myLog.setAdapter(new mainPostAdapter(getContext(), myLogList, myLog, 1));
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
                myLogList.clear();
                for(DataSnapshot items : dataSnapshot.getChildren()){
                    mainPostItem item = items.getValue(mainPostItem.class);
                    item.setContentID(items.getKey());
                    myLogList.add(0, item);
                }

                myLog.getAdapter().notifyDataSetChanged();
                asyncDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
