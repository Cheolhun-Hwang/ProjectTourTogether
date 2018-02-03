package com.hch.hooney.tourtogether.Recycler.Search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.DiningAndAccomodationActivity;
import com.hch.hooney.tourtogether.FacilityActivity;
import com.hch.hooney.tourtogether.LeisureActivity;
import com.hch.hooney.tourtogether.NatureActivity;
import com.hch.hooney.tourtogether.R;
import com.hch.hooney.tourtogether.ShoppingActivity;
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
    private String field;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public SearchResultAdapter(Context mContext, ArrayList<TourApiItem> resultList, String field) {
        this.mContext = mContext;
        this.resultList = resultList;
        this.field = field;
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

        Log.d("Result Image", item.getFirstImage());

        if(item.getFirstImage().equals("")){
        }else{
            Picasso.with(mContext).load(item.getFirstImage()).into(hold.sr_Image);
        }

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMddHHmmss").parse(item.getModifyDateTIme());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        hold.sr_viewCount.setText("view : " + item.getReadCount());

        hold.sr_dateTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));


        hold.sr_location.setText(item.getAddr1()+" "+item.getAddr2());

        hold.sr_Title.setText(item.getTitle());

        hold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getContentTypeID().equals("32")||item.getContentTypeID().equals("80")||
                        item.getContentTypeID().equals("39")||item.getContentTypeID().equals("82")){
                    Intent intent = new Intent(mContext, DiningAndAccomodationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("basic", item);
                    intent.putExtra("field", field);
                    mContext.startActivity(intent);
                }else if(item.getContentTypeID().equals("12")||item.getContentTypeID().equals("76")){
                    Intent intent = new Intent(mContext, NatureActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("basic", item);
                    intent.putExtra("field", field);
                    mContext.startActivity(intent);
                }else if(item.getContentTypeID().equals("14")||item.getContentTypeID().equals("78")){
                    Intent intent = new Intent(mContext, FacilityActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("basic", item);
                    intent.putExtra("field", field);
                    mContext.startActivity(intent);
                }else if(item.getContentTypeID().equals("28")||item.getContentTypeID().equals("75")){
                    Intent intent = new Intent(mContext, LeisureActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("basic", item);
                    intent.putExtra("field", field);
                    mContext.startActivity(intent);
                }else if(item.getContentTypeID().equals("38")||item.getContentTypeID().equals("79")){
                    Intent intent = new Intent(mContext, ShoppingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("basic", item);
                    intent.putExtra("field", field);
                    mContext.startActivity(intent);
                }else{
                    //Intent intent = new Intent(mContext, AnotherFieldActivity.class);
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
