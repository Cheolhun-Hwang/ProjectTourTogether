package com.hch.hooney.tourtogether.Recycler.Bookmark.MyCourseList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hch.hooney.tourtogether.R;

/**
 * Created by hch on 2018-02-04.
 */

public class MyCourseListHolder extends RecyclerView.ViewHolder {
    public TextView mc_date;
    public TextView mc_title;
    public ImageButton mc_remove;
    public Button mc_showmap;

    public MyCourseListHolder(View itemView) {
        super(itemView);

        mc_date = (TextView) itemView.findViewById(R.id.mycourse_wirte_date);
        mc_title = (TextView) itemView.findViewById(R.id.mycourse_title);
        mc_remove = (ImageButton) itemView.findViewById(R.id.mycourse_remove_IBTN);
        mc_showmap = (Button) itemView.findViewById(R.id.mycourse_showmap);
    }
}
