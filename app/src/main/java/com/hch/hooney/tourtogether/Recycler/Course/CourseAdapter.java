package com.hch.hooney.tourtogether.Recycler.Course;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hch on 2018-02-03.
 */

public class CourseAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<TourApiItem> list;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public CourseAdapter(Context mContext, ArrayList<TourApiItem> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark_route,parent,false);
        CourseHolder holder = new CourseHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CourseHolder hold = (CourseHolder)holder;
        TourApiItem item = list.get(position);

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMddHHmmss").parse(item.getModifyDateTIme());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        hold.ch_DateAndTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));

        hold.ch_title.setText(item.getTitle());

        if(!item.getFirstImage().equals("")){
            Picasso.with(mContext).load(item.getFirstImage()).into(hold.ch_imageview);
        }else{
        }
        hold.ch_viewCount.setText("view : " + item.getReadCount());

        hold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Show Route...", Toast.LENGTH_SHORT).show();
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
