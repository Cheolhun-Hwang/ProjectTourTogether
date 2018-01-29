package com.hch.hooney.tourtogether.Recycler.Result;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hch.hooney.tourtogether.R;

/**
 * Created by hch on 2018-01-29.
 */

public class ResultSingleImageHolder extends RecyclerView.ViewHolder {
    public ImageView mainImaview;

    public ResultSingleImageHolder(View itemView) {
        super(itemView);

        mainImaview = (ImageView) itemView.findViewById(R.id.item_result_single_imageview);
    }
}
