package com.hch.hooney.tourtogether.Recycler.Result;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hch.hooney.tourtogether.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hch on 2018-01-29.
 */

public class ResultSingleImageAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private ArrayList<String> list;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public ResultSingleImageAdapter(Context mContext, ArrayList<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_single_image,parent,false);
        ResultSingleImageHolder holder = new ResultSingleImageHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ResultSingleImageHolder hold = (ResultSingleImageHolder) holder;
        String item = list.get(position);

        if(!item.equals("")){
            Picasso.with(mContext).load(item).into(hold.mainImaview);
        }

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
