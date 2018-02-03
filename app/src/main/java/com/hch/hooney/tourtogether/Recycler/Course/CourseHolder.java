package com.hch.hooney.tourtogether.Recycler.Course;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hch.hooney.tourtogether.R;

/**
 * Created by hch on 2018-02-03.
 */

public class CourseHolder extends RecyclerView.ViewHolder{
    public TextView ch_DateAndTime;
    public TextView ch_title;
    public ImageView ch_imageview;
    public TextView ch_viewCount;

    public CourseHolder(View itemView) {
        super(itemView);
        ch_DateAndTime = (TextView) itemView.findViewById(R.id.bookmark_route_date);
        ch_title = (TextView) itemView.findViewById(R.id.bookmark_route_title);
        ch_imageview = (ImageView) itemView.findViewById(R.id.bookmark_route_imageview);
        ch_viewCount = (TextView) itemView.findViewById(R.id.bookmark_route_viewcount);
    }
}
