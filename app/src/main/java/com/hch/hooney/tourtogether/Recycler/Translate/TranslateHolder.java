package com.hch.hooney.tourtogether.Recycler.Translate;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hch.hooney.tourtogether.R;

/**
 * Created by hch on 2018-03-01.
 */

public class TranslateHolder extends RecyclerView.ViewHolder {
    public TextView t_from;
    public TextView t_to;
    public TextView t_origin;
    public TextView t_translate;
    public ImageButton t_remove;
    public ImageButton t_hearing;

    public TranslateHolder(View itemView) {
        super(itemView);

        t_from = (TextView) itemView.findViewById(R.id.item_translateList_from);
        t_to = (TextView) itemView.findViewById(R.id.item_translateList_to);
        t_origin = (TextView) itemView.findViewById(R.id.item_translateList_origin);
        t_translate = (TextView) itemView.findViewById(R.id.item_translateList_translate);
        t_remove = (ImageButton) itemView.findViewById(R.id.item_translateList_remove);
        t_hearing = (ImageButton) itemView.findViewById(R.id.item_translateList_hearing);
    }
}
