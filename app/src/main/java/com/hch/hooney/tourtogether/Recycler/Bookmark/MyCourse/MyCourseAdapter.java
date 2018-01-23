package com.hch.hooney.tourtogether.Recycler.Bookmark.MyCourse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.R;
import com.squareup.picasso.Picasso;

/**
 * Created by qewqs on 2018-01-24.
 */

public class MyCourseAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private RecyclerView mycourseList;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public MyCourseAdapter(Context mContext, RecyclerView mycourseList) {
        this.mContext = mContext;
        this.mycourseList = mycourseList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark_mycourse,parent,false);
        MyCourseHolder holder = new MyCourseHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyCourseHolder hold = (MyCourseHolder) holder;
        final TourApiItem item = DAO.myCourseList.get(position);

        hold.mc_num.setText((position+1)+"");
        Picasso.with(mContext).load(item.getFirstImage()).into(hold.mc_imageView);
        hold.mc_title.setText(item.getTitle());
        hold.mc_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = position-1;
                if(num<0){
                    num = 0;
                }
                DAO.myCourseList.remove(position);
                DAO.myCourseList.add(num, item);

                mycourseList.getAdapter().notifyDataSetChanged();
            }
        });
        hold.mc_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAO.myCourseList.remove(position);
                Toast.makeText(mContext, mContext.getResources().getText(R.string.mycourse_remove), Toast.LENGTH_SHORT).show();

                mycourseList.getAdapter().notifyDataSetChanged();
            }
        });
        hold.mc_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = position+1;
                if(num>=DAO.myCourseList.size()){
                    num = position;
                }
                DAO.myCourseList.remove(position);
                DAO.myCourseList.add(num, item);

                mycourseList.getAdapter().notifyDataSetChanged();
            }
        });
        hold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Detail Info...", Toast.LENGTH_SHORT).show();
            }
        });

        setAnimation(hold.itemView, position);
    }

    @Override
    public int getItemCount() {
        return DAO.myCourseList.size();
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
