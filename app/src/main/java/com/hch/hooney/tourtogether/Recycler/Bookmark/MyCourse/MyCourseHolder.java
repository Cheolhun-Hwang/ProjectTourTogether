package com.hch.hooney.tourtogether.Recycler.Bookmark.MyCourse;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hch.hooney.tourtogether.R;

/**
 * Created by qewqs on 2018-01-24.
 */

public class MyCourseHolder extends RecyclerView.ViewHolder{
    public TextView mc_num;
    public ImageView mc_imageView;
    public TextView mc_title;
    public ImageButton mc_up;
    public ImageButton mc_close;
    public ImageButton mc_down;

    public MyCourseHolder(View itemView) {
        super(itemView);

        mc_num = (TextView) itemView.findViewById(R.id.bookmark_mycourse_num);
        mc_imageView = (ImageView) itemView.findViewById(R.id.bookmark_mycourse_imageview);
        mc_title = (TextView) itemView.findViewById(R.id.bookmark_mycourse_title);
        mc_up = (ImageButton) itemView.findViewById(R.id.bookmark_mycourse_upImageView);
        mc_close = (ImageButton) itemView.findViewById(R.id.bookmark_mycourse_closeImageView);
        mc_down = (ImageButton)itemView.findViewById(R.id.bookmark_mycourse_downImageView);
    }
}
