package com.hch.hooney.tourtogether.Fragments.Bookmarking;


import android.app.Activity;
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

import com.hch.hooney.tourtogether.AddMyCourseActivity;
import com.hch.hooney.tourtogether.R;
import com.hch.hooney.tourtogether.Recycler.Bookmark.MyCourse.MyCourseAdapter;
import com.hch.hooney.tourtogether.Recycler.Bookmark.MyCourseList.MyCourseListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDairyFragment extends Fragment {
    private final String TAG = "MyDairyFragment";
    private final int SIGNAL_CREATE_LIST = 7001;

    private View view;

    private RecyclerView myCourseView;
    private Button showMap;


    public MyDairyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_dairy, container, false);
        init();
        setUI();
        return view;
    }

    private void init(){
        myCourseView = (RecyclerView) view.findViewById(R.id.mycourse_list);
        myCourseView.setHasFixedSize(false);
        myCourseView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        showMap = (Button) view.findViewById(R.id.mycourse_showmap);
    }

    private void setUI(){
        myCourseView.setAdapter(new MyCourseListAdapter(getContext(), myCourseView));

        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), AddMyCourseActivity.class), SIGNAL_CREATE_LIST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "requestCode : " + requestCode);
        if(requestCode == SIGNAL_CREATE_LIST){
            Log.d(TAG, "resultCode : " + resultCode);
            if(resultCode == Activity.RESULT_OK){
                myCourseView.getAdapter().notifyDataSetChanged();
            }else if(resultCode == Activity.RESULT_CANCELED){

            }
        }
    }
}
