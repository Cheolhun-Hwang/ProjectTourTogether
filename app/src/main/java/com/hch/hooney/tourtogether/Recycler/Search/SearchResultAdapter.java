package com.hch.hooney.tourtogether.Recycler.Search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.hch.hooney.tourtogether.AnotherFieldActivity;
import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.DiningAndAccomodationActivity;
import com.hch.hooney.tourtogether.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        final TourApiItem item = resultList.get(position);

        Picasso.with(mContext).load(item.getFirstImage()).into(hold.sr_Image);

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMddHHmmss").parse(item.getModifyDateTIme());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        hold.sr_dateTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));


        hold.sr_viewCount.setText("view : " + item.getReadCount());

        hold.sr_location.setText(item.getAddr1()+" "+item.getAddr2());

        hold.sr_Title.setText(item.getTitle());

        hold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getContentTypeID().equals("32")||item.getContentTypeID().equals("80")||
                        item.getContentTypeID().equals("39")||item.getContentTypeID().equals("82")){
                    Intent intent = new Intent(mContext, DiningAndAccomodationActivity.class);
                    intent.putExtra("ContentTypeID", item.getContentTypeID());
                    intent.putExtra("ContentID", item.getContentID());
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, AnotherFieldActivity.class);
                }
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
