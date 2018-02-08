package com.hch.hooney.tourtogether.Recycler.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.mainPostItem;
import com.hch.hooney.tourtogether.R;
import com.squareup.picasso.Picasso;

/**
 * Created by qewqs on 2018-01-23.
 */

public class mainPostAdapter extends RecyclerView.Adapter {
    private Context mContext;

    private DatabaseReference rootRef;
    private DatabaseReference mainPostRef;
    private DatabaseReference myPostRef;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public mainPostAdapter(Context mContext) {
        this.mContext = mContext;
        //firebase;
        rootRef = FirebaseDatabase.getInstance().getReference();
        mainPostRef = rootRef.child("mainPost");
        myPostRef = rootRef.child("myPost").child(DAO.user.getUID());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_post,parent,false);
        mainPostHolder holder = new mainPostHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mainPostHolder hold = (mainPostHolder) holder;
        final mainPostItem item = DAO.mainPostList.get(position);

        //User
        hold.userName.setText(item.getUNAME());
        Picasso.with(mContext).load(item.getUPROFILEIMAGE()).into(hold.userProfile);
        hold.userProfile.setBackground(new ShapeDrawable(new OvalShape()));
        hold.userProfile.setClipToOutline(true);

        //menu
//        hold.postMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "Menu !!", Toast.LENGTH_SHORT).show();
//            }
//        });

        hold.postMenu.setVisibility(View.GONE);

        //Post
        if(item.getFirstImage().equals("")){
            hold.postImage.setVisibility(View.GONE);
        }else{
            Picasso.with(mContext).load(item.getFirstImage()).into(hold.postImage);
        }
        hold.postContext.setText(item.getBasic_overView());

        //위치 밑줄처리
        SpannableString locationString = new SpannableString(item.getAddr1());
        locationString.setSpan(new UnderlineSpan(), 0, item.getAddr1().length(), 0);
        hold.postLocation.setText(locationString);

        hold.postLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Show Map !!", Toast.LENGTH_SHORT).show();
            }
        });

        //BookMarking
        if(DAO.chkAddBookmarking(item.getContentID())){
            hold.postBookMarginImage.setImageResource(R.drawable.bookmark_do);
        }else{
            hold.postBookMarginImage.setImageResource(R.drawable.bookmark_undo);
        }
        hold.postBookMarginText.setText(item.getReadCount());

        //Comment
        hold.postCommentText.setText(item.getCommentCount());

        hold.postBookMarginRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAO.handler.insert_spot(item.getSuper());
                DAO.load_bookmarkSpot();
            }
        });

        hold.postCommentRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Comment !!", Toast.LENGTH_SHORT).show();
            }
        });

        setAnimation(hold.itemView, position);
    }

    @Override
    public int getItemCount() {
        return DAO.mainPostList.size();
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
