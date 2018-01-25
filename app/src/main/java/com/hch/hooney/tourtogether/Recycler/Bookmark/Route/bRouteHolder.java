package com.hch.hooney.tourtogether.Recycler.Bookmark.Route;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hch.hooney.tourtogether.R;

/**
 * Created by qewqs on 2018-01-25.
 */

public class bRouteHolder extends RecyclerView.ViewHolder {
    public TextView br_DateAndTime;
    public TextView br_title;
    public ImageView br_imageview;
    public TextView br_viewCount;

    public bRouteHolder(View itemView) {
        super(itemView);

        br_DateAndTime = (TextView) itemView.findViewById(R.id.bookmark_route_date);
        br_title = (TextView) itemView.findViewById(R.id.bookmark_route_title);
        br_imageview = (ImageView) itemView.findViewById(R.id.bookmark_route_imageview);
        br_viewCount = (TextView) itemView.findViewById(R.id.bookmark_route_viewcount);
    }
}
