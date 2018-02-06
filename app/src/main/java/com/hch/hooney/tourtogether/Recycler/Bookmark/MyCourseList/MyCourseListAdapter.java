package com.hch.hooney.tourtogether.Recycler.Bookmark.MyCourseList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.MyCourse;
import com.hch.hooney.tourtogether.R;

/**
 * Created by hch on 2018-02-04.
 */

public class MyCourseListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private RecyclerView recyclerView;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public MyCourseListAdapter(Context mContext, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.recyclerView = recyclerView;
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

        MyCourse item = DAO.myCoursesList.get(position);

        hold.mc_date.setText(item.getMc_Date());

        hold.mc_title.setText(item.getMc_Title());

        hold.mc_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mContext.getResources().getText(R.string.mycourse_remove), Toast.LENGTH_SHORT).show();

                DAO.myCoursesList.remove(position);
                //DAO.handler.delete_spot(item.getContentID());
                //DAO.load_bookmarkSpot();
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        hold.mc_showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return DAO.myCoursesList.size();
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
