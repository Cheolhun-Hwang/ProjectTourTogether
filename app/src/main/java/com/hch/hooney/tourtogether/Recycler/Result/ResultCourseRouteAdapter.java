package com.hch.hooney.tourtogether.Recycler.Result;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hooney on 2018. 2. 3..
 */

public class ResultCourseRouteAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<TourApiItem> list;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    private String feild;

    public ResultCourseRouteAdapter(Context mContext, ArrayList<TourApiItem> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_course_spot,parent,false);
        ResultCourseRouteHolder holder = new ResultCourseRouteHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ResultCourseRouteHolder hold = (ResultCourseRouteHolder) holder;
        TourApiItem item = list.get(position);

        if(!item.getFirstImage().equals("")){
            Picasso.with(mContext).load(item.getFirstImage()).into(hold.rcr_mainImage);
        }

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMddHHmmss").parse(item.getModifyDateTIme());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date == null){
            hold.rcr_date.setText("");
        }else{
            hold.rcr_date.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
        }


        if(item.getContentTypeID().equals("32")||item.getContentTypeID().equals("80")){
            feild = mContext.getResources().getText(R.string.search_tab6).toString();
            hold.rcr_cat.setText(feild.replace("\n", " "));
        }else if(item.getContentTypeID().equals("12")||item.getContentTypeID().equals("76")){
            feild = mContext.getResources().getText(R.string.search_tab1).toString();
            hold.rcr_cat.setText(feild.replace("\n", " "));
        }else if(item.getContentTypeID().equals("14")||item.getContentTypeID().equals("78")){
            feild = mContext.getResources().getText(R.string.search_tab2).toString();
            hold.rcr_cat.setText(feild.replace("\n", " "));
        }else if(item.getContentTypeID().equals("28")||item.getContentTypeID().equals("75")){
            feild = mContext.getResources().getText(R.string.search_tab3).toString();
            hold.rcr_cat.setText(feild.replace("\n", " "));
        }else if(item.getContentTypeID().equals("38")||item.getContentTypeID().equals("79")){
            feild = mContext.getResources().getText(R.string.search_tab4).toString();
            hold.rcr_cat.setText(feild.replace("\n", " "));
        }else if(item.getContentTypeID().equals("39")||item.getContentTypeID().equals("82")){
            feild = mContext.getResources().getText(R.string.search_tab5).toString();
            hold.rcr_cat.setText(feild.replace("\n", " "));
        }else{
            //Intent intent = new Intent(mContext, AnotherFieldActivity.class);
        }

        hold.rcr_title.setText(item.getTitle());


        if(position == (list.size()-1)){
            hold.rcr_downArrow.setVisibility(View.GONE);
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
