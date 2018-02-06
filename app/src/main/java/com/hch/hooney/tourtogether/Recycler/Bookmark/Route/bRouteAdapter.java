package com.hch.hooney.tourtogether.Recycler.Bookmark.Route;

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
import com.hch.hooney.tourtogether.ResultCourseActivity;
import com.hch.hooney.tourtogether.ShoppingActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qewqs on 2018-01-25.
 */

public class bRouteAdapter  extends RecyclerView.Adapter {
    private Context mContext;
    private RecyclerView bookCourse;
    private String feild;

    public bRouteAdapter(Context mContext, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.bookCourse = recyclerView;
    }

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark_route,parent,false);
        bRouteHolder holder = new bRouteHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bRouteHolder hold = (bRouteHolder)holder;
        final TourApiItem item = DAO.bookmarkRouteList.get(position);

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMddHHmmss").parse(item.getModifyDateTIme());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        hold.br_DateAndTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));

        hold.br_title.setText(item.getTitle());

        Picasso.with(mContext).load(item.getFirstImage()).into(hold.br_imageview);

        hold.br_viewCount.setText("view : " + item.getReadCount());

        hold.br_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mContext.getResources().getText(R.string.bookmark_remove), Toast.LENGTH_SHORT).show();
                DAO.handler.delete_course(item.getContentID());
                DAO.load_bookmarkCourse();
                bookCourse.getAdapter().notifyDataSetChanged();
            }
        });

        hold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feild = "";
                if(item.getCat2().equals("C0112")){
                    feild = mContext.getResources().getText(R.string.recommend_tab1).toString();
                }else if(item.getCat2().equals("C0113")){
                    feild = mContext.getResources().getText(R.string.recommend_tab2).toString();
                }else if(item.getCat2().equals("C0114")){
                    feild = mContext.getResources().getText(R.string.recommend_tab3).toString();
                }else if(item.getCat2().equals("C0115")){
                    feild = mContext.getResources().getText(R.string.recommend_tab4).toString();
                }else if(item.getCat2().equals("C0116")){
                    feild = mContext.getResources().getText(R.string.recommend_tab5).toString();
                }else if(item.getCat2().equals("C0117")){
                    feild = mContext.getResources().getText(R.string.recommend_tab6).toString();
                }else{
                    //Intent intent = new Intent(mContext, AnotherFieldActivity.class);
                }

                Intent intent = new Intent(mContext, ResultCourseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("basic", item);
                intent.putExtra("field", feild);
                mContext.startActivity(intent);
            }
        });

        setAnimation(hold.itemView, position);
    }

    @Override
    public int getItemCount() {
        return DAO.bookmarkRouteList.size();
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
