package com.hch.hooney.tourtogether.Recycler.Search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hooney on 2018. 1. 26..
 */

public class SearchResultAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<TourApiItem> resultList;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public SearchResultAdapter(Context mContext, ArrayList<TourApiItem> resultList) {
        this.mContext = mContext;
        this.resultList = resultList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result_list,parent,false);
        SearchResultHolder holder = new SearchResultHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchResultHolder hold = (SearchResultHolder) holder;
        TourApiItem item = resultList.get(position);

        Picasso.with(mContext).load(item.getFirstImage()).into(hold.sr_Image);

        String dateTime = item.getModifyDateTIme();
        String year = dateTime.substring(0, 4);
        String month = dateTime.substring(4, 6);
        String day = dateTime.substring(6, 8);
        String hour = dateTime.substring(8, 10);
        String min = dateTime.substring(10, 12);

        hold.sr_dateTime.setText(year+"-"+month+"-"+day+" "+hour+":"+min);

        hold.sr_viewCount.setText("view : " + item.getReadCount());

        hold.sr_location.setText(item.getAddr1()+" "+item.getAddr2());

        hold.sr_Title.setText(item.getTitle());

        hold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Detail info...", Toast.LENGTH_SHORT).show();
            }
        });

        setAnimation(hold.itemView, position);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
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
