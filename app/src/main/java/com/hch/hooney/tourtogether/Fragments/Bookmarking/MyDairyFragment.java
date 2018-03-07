package com.hch.hooney.tourtogether.Fragments.Bookmarking;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hch.hooney.tourtogether.AddMyCourseActivity;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.MyCourse;
import com.hch.hooney.tourtogether.R;
import com.hch.hooney.tourtogether.Recycler.Bookmark.MyCourse.MyCourseAdapter;
import com.hch.hooney.tourtogether.Recycler.Bookmark.MyCourseList.MyCourseListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDairyFragment extends Fragment {
    private final String TAG = "MyDairyFragment";
    private final int SIGNAL_CREATE_LIST = 7001;
    private ProgressDialog asyncDialog;
    private View view;

    private int nowLIstSize;

    private RecyclerView myCourseView;
    private Button showMap;     //add List


    private DatabaseReference rootRef;
    private DatabaseReference mycourseRef;

    private ArrayList<MyCourse> myCourseList;

    public MyDairyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_dairy, container, false);
        init();
        setEvent();
        setUI();
        return view;
    }

    private void setEvent(){
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddMyCourseActivity.class);
                intent.putExtra("size", nowLIstSize);
                startActivityForResult(intent, SIGNAL_CREATE_LIST);
            }
        });

        mycourseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //실시간
                myCourseList.clear();
                for(DataSnapshot items : dataSnapshot.getChildren()){
                    nowLIstSize = Integer.parseInt(items.getKey());
                    MyCourse item = items.getValue(MyCourse.class);
                    myCourseList.add(item);
                }

                myCourseView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //에러
            }
        });
    }

    private void init(){
        //firebase;
        rootRef = FirebaseDatabase.getInstance().getReference();
        mycourseRef = rootRef.child("MyCourse").child(DAO.user.getUID());

        myCourseList = new ArrayList<MyCourse>();
        nowLIstSize = 0;

        myCourseView = (RecyclerView) view.findViewById(R.id.mycourse_list);
        myCourseView.setHasFixedSize(false);
        myCourseView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        showMap = (Button) view.findViewById(R.id.mycourse_showmap);
    }

    private void setUI(){
        myCourseView.setAdapter(new MyCourseListAdapter(getContext(), myCourseList));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d(TAG, "requestCode : " + requestCode);
        if(requestCode == SIGNAL_CREATE_LIST){
            //Log.d(TAG, "resultCode : " + resultCode);
            if(resultCode == Activity.RESULT_OK){
                //myCourseView.getAdapter().notifyDataSetChanged();
            }else if(resultCode == Activity.RESULT_CANCELED){

            }
        }
    }
}
