package com.hch.hooney.tourtogether.Recycler.Bookmark.Spot;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hch.hooney.tourtogether.R;

/**
 * Created by qewqs on 2018-01-24.
 */

public class bSpotHolder extends RecyclerView.ViewHolder{
    public ImageView bs_imageView;
    public TextView bs_title;
    public ImageButton bs_close;
    public TextView bs_cat;

    public bSpotHolder(View itemView) {
        super(itemView);

        bs_imageView = (ImageView) itemView.findViewById(R.id.bookmark_spot_imageview);
        bs_title = (TextView) itemView.findViewById(R.id.bookmark_spot_title);
        bs_close = (ImageButton) itemView.findViewById(R.id.bookmark_spot_closeImageView);
        bs_cat = (TextView) itemView.findViewById(R.id.bookmark_spot_cat);
    }
}
