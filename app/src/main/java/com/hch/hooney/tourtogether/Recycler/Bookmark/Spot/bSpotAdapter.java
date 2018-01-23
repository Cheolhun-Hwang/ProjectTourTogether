package com.hch.hooney.tourtogether.Recycler.Bookmark.Spot;

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

public class bSpotAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private RecyclerView bookmark_spot;

    public bSpotAdapter(Context mContext, RecyclerView viewlist) {
        this.mContext = mContext;
        this.bookmark_spot = viewlist;
    }

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark_spot,parent,false);
        bSpotHolder holder = new bSpotHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        bSpotHolder hold = (bSpotHolder) holder;
        final TourApiItem item = DAO.bookmarkSpotList.get(position);

        Picasso.with(mContext).load(item.getFirstImage()).into(hold.bs_imageView);
        hold.bs_title.setText(item.getTitle());
        hold.bs_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mContext.getResources().getText(R.string.bookmark_remove), Toast.LENGTH_SHORT).show();
                DAO.bookmarkSpotList.remove(position);
                bookmark_spot.getAdapter().notifyDataSetChanged();
            }
        });

        hold.bs_addMyCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAO.myCourseList.add(item);
                Toast.makeText(mContext, mContext.getResources().getText(R.string.bookmark_add), Toast.LENGTH_SHORT).show();
            }
        });

        hold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Detail Info...", Toast.LENGTH_SHORT).show();
            }
        });

        setAnimation(hold.itemView,position);
    }

    @Override
    public int getItemCount() {
        return DAO.bookmarkSpotList.size();
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
