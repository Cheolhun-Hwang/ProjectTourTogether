package com.hch.hooney.tourtogether.Recycler.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hch.hooney.tourtogether.R;

/**
 * Created by hooney on 2018. 1. 26..
 */

public class SearchResultHolder extends RecyclerView.ViewHolder {
    public ImageView sr_Image;
    public TextView sr_viewCount;
    public TextView sr_dateTime;
    public TextView sr_Title;
    public TextView sr_location;

    public SearchResultHolder(View itemView) {
        super(itemView);

        sr_Image = (ImageView) itemView.findViewById(R.id.item_search_mainImage);
        sr_viewCount = (TextView) itemView.findViewById(R.id.item_search_viewCount);
        sr_dateTime = (TextView) itemView.findViewById(R.id.item_search_dateTime);
        sr_Title = (TextView) itemView.findViewById(R.id.item_search_title);
        sr_location = (TextView) itemView.findViewById(R.id.item_search_location);
    }
}
