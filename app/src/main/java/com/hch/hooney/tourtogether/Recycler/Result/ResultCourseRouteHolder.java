package com.hch.hooney.tourtogether.Recycler.Result;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hch.hooney.tourtogether.R;

/**
 * Created by hooney on 2018. 2. 3..
 */

public class ResultCourseRouteHolder extends RecyclerView.ViewHolder {
    public ImageView rcr_mainImage;
    public TextView rcr_date;
    public TextView rcr_cat;
    public TextView rcr_title;
    public ImageView rcr_downArrow;

    public ResultCourseRouteHolder(View itemView) {
        super(itemView);

        rcr_mainImage = (ImageView) itemView.findViewById(R.id.item_result_course_imageview);
        rcr_date = (TextView) itemView.findViewById(R.id.item_result_course_modifydate);
        rcr_cat = (TextView) itemView.findViewById(R.id.item_result_course_spot_cat);
        rcr_title = (TextView) itemView.findViewById(R.id.item_result_course_title);
        rcr_downArrow = (ImageView) itemView.findViewById(R.id.item_result_course_downArrow);
    }
}
