package com.hch.hooney.tourtogether.Recycler.Bookmark.Spot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.DiningAndAccomodationActivity;
import com.hch.hooney.tourtogether.FacilityActivity;
import com.hch.hooney.tourtogether.LeisureActivity;
import com.hch.hooney.tourtogether.NatureActivity;
import com.hch.hooney.tourtogether.R;
import com.hch.hooney.tourtogether.ShoppingActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by qewqs on 2018-01-24.
 */

public class bSpotAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private RecyclerView bookmark_spot;

    private Intent intent = null;
    private String feild = "";


    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public bSpotAdapter(Context mContext, RecyclerView viewlist) {
        this.mContext = mContext;
        this.bookmark_spot = viewlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark_spot_for_list,parent,false);
        bSpotHolder holder = new bSpotHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        bSpotHolder hold = (bSpotHolder) holder;
        final TourApiItem item = DAO.bookmarkSpotList.get(position);

        if(item.isPost()){
            //post
            hold.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.red_50));
        }else{
            //api
            hold.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        if(item.getFirstImage().equals("")){

        }else{
            Picasso.with(mContext).load(item.getFirstImage()).into(hold.bs_imageView);
        }

        hold.bs_title.setText(item.getTitle());
        hold.bs_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mContext.getResources().getText(R.string.bookmark_remove), Toast.LENGTH_SHORT).show();
                DAO.handler.delete_spot(item.getContentID());
                DAO.load_bookmarkSpot();
                bookmark_spot.getAdapter().notifyDataSetChanged();
            }
        });


        if(item.getContentTypeID().equals("32")||item.getContentTypeID().equals("80")){
            intent = new Intent(mContext, DiningAndAccomodationActivity.class);
            feild = mContext.getResources().getText(R.string.search_tab6).toString();
            hold.bs_cat.setText(feild.replace("\n", " "));
        }else if(item.getContentTypeID().equals("12")||item.getContentTypeID().equals("76")){
            intent = new Intent(mContext, NatureActivity.class);
            feild = mContext.getResources().getText(R.string.search_tab1).toString();
            hold.bs_cat.setText(feild.replace("\n", " "));
        }else if(item.getContentTypeID().equals("14")||item.getContentTypeID().equals("78")){
            intent = new Intent(mContext, FacilityActivity.class);
            feild = mContext.getResources().getText(R.string.search_tab2).toString();
            hold.bs_cat.setText(feild.replace("\n", " "));
        }else if(item.getContentTypeID().equals("28")||item.getContentTypeID().equals("75")){
            intent = new Intent(mContext, LeisureActivity.class);
            feild = mContext.getResources().getText(R.string.search_tab3).toString();
            hold.bs_cat.setText(feild.replace("\n", " "));
        }else if(item.getContentTypeID().equals("38")||item.getContentTypeID().equals("79")){
            intent = new Intent(mContext, ShoppingActivity.class);
            feild = mContext.getResources().getText(R.string.search_tab4).toString();
            hold.bs_cat.setText(feild.replace("\n", " "));
        }else if(item.getContentTypeID().equals("39")||item.getContentTypeID().equals("82")){
            intent = new Intent(mContext, DiningAndAccomodationActivity.class);
            feild = mContext.getResources().getText(R.string.search_tab5).toString();
            hold.bs_cat.setText(feild.replace("\n", " "));
        }else{
            //Intent intent = new Intent(mContext, AnotherFieldActivity.class);
        }

        hold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("basic", item);
                intent.putExtra("field", feild);
                mContext.startActivity(intent);
            }
        });

        setAnimation(hold.itemView,position);
    }

    @Override
    public int getItemCount() {
        return DAO.bookmarkSpotList.size();
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
