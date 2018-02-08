package com.hch.hooney.tourtogether.Recycler.Bookmark.MyCourseList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.MyCourse;
import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.JustShowMapActivity;
import com.hch.hooney.tourtogether.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hch on 2018-02-04.
 */

public class MyCourseListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<MyCourse> list;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    private DatabaseReference rootRef;
    private DatabaseReference mycourseRef;

    public MyCourseListAdapter(Context mContext, ArrayList<MyCourse> list) {
        this.mContext = mContext;
        this.list = list;

        //firebase;
        rootRef = FirebaseDatabase.getInstance().getReference();
        mycourseRef = rootRef.child("MyCourse").child(DAO.user.getUID());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mycourse_list,parent,false);
        MyCourseListHolder holder = new MyCourseListHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyCourseListHolder hold = (MyCourseListHolder) holder;

        final MyCourse item = list.get(position);

        hold.mc_date.setText(item.getMc_Date());

        hold.mc_title.setText(item.getMc_Title());

        hold.mc_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mContext.getResources().getText(R.string.mycourse_remove), Toast.LENGTH_SHORT).show();
                mycourseRef.child(item.getMc_key()).removeValue();
            }
        });

        hold.mc_showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, JustShowMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("func", "course");
                intent.putExtra("list", item.getMc_routeList());
                mContext.startActivity(intent);
            }
        });

        setAnimation(hold.itemView, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // 새로 보여지는 뷰라면 애니메이션을 해줍니다
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
