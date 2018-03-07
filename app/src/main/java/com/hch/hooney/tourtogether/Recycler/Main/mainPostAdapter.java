package com.hch.hooney.tourtogether.Recycler.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.provider.ContactsContract;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.DAO.mainPostItem;
import com.hch.hooney.tourtogether.PostCommentActivity;
import com.hch.hooney.tourtogether.PostMapActivity;
import com.hch.hooney.tourtogether.R;
import com.hch.hooney.tourtogether.ResourceCTRL.Location;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by qewqs on 2018-01-23.
 */

public class mainPostAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<mainPostItem> list;
    private RecyclerView recyclerView;

    private DatabaseReference rootRef;
    private DatabaseReference postRef;
    private DatabaseReference userRef;

    private int isLog;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public mainPostAdapter(Context mContext, ArrayList<mainPostItem> list, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.list = list;
        this.recyclerView = recyclerView;
        this.isLog = 0;
        //firebase;
        rootRef = FirebaseDatabase.getInstance().getReference();
        postRef = rootRef.child("post");
        userRef = rootRef.child("ulog").child(DAO.user.getUID());
    }

    public mainPostAdapter(Context mContext, ArrayList<mainPostItem> list, RecyclerView recyclerView,
                           int isLog) {
        this.mContext = mContext;
        this.list = list;
        this.recyclerView = recyclerView;
        this.isLog = isLog;
        //firebase;
        rootRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_post,parent,false);
        mainPostHolder holder = new mainPostHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final mainPostHolder hold = (mainPostHolder) holder;

        final mainPostItem item = list.get(position);
        postRef = rootRef.child("post");
        userRef = rootRef.child("ulog").child(item.getUId());

        //User
        hold.userName.setText(item.getUNAME());
        Picasso.with(mContext).load(item.getUPROFILEIMAGE()).into(hold.userProfile);
        hold.userProfile.setBackground(new ShapeDrawable(new OvalShape()));
        hold.userProfile.setClipToOutline(true);

        //menu
        if(item.getUId().equals(DAO.user.getUID())){
            hold.postMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(mContext,v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.menu_main_post_remove:
                                    remove(item.getContentID(), position);
                                    break;
                            }
                            return true;
                        }
                    });// to implement on click event on items of menu
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.main_post, popup.getMenu());
                    popup.show();
                }
            });
        }else{
            hold.postMenu.setVisibility(View.GONE);
        }

        //category
        String feild = "";
        if(item.getContentTypeID().equals("32")||item.getContentTypeID().equals("80")){
            feild = mContext.getResources().getText(R.string.search_tab6).toString();
        }else if(item.getContentTypeID().equals("12")||item.getContentTypeID().equals("76")){
            feild = mContext.getResources().getText(R.string.search_tab1).toString();
        }else if(item.getContentTypeID().equals("14")||item.getContentTypeID().equals("78")){
            feild = mContext.getResources().getText(R.string.search_tab2).toString();
        }else if(item.getContentTypeID().equals("28")||item.getContentTypeID().equals("75")){
            feild = mContext.getResources().getText(R.string.search_tab3).toString();
        }else if(item.getContentTypeID().equals("38")||item.getContentTypeID().equals("79")){
            feild = mContext.getResources().getText(R.string.search_tab4).toString();
        }else if(item.getContentTypeID().equals("39")||item.getContentTypeID().equals("82")){
            feild = mContext.getResources().getText(R.string.search_tab5).toString();
        }
        hold.postCategory.setText("Category : " + feild.replace("\n", " "));

        //write date
        hold.postWriteDate.setText(item.getModifyDateTIme());

        //Post
        if(item.getFirstImage().equals("")){
            hold.postImage.setVisibility(View.GONE);
        }else{
            Picasso.with(mContext).load(item.getFirstImage()).into(hold.postImage);
        }

        if(DAO.Language.equals("ko")){
            hold.postContext.setText(item.getKrContext());
        }else if(DAO.Language.equals("en")){
            hold.postContext.setText(item.getEnContext());
        }else{
            hold.postContext.setText(item.getBasic_overView());
        }


        //위치 밑줄처리
        SpannableString locationString = new SpannableString(new Location(mContext, item.getMapy(), item.getMapx()).searchLocation());
        locationString.setSpan(new UnderlineSpan(), 0, locationString.length(), 0);
        hold.postLocation.setText(locationString);

        hold.postLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("func", "focus");
                intent.putExtra("mapx", item.getMapx());
                intent.putExtra("mapy", item.getMapy());
                mContext.startActivity(intent);
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

        if(isLog==0){
            hold.postBookMarginRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!DAO.chkAddBookmarking(item.getContentID())){
                        TourApiItem titem = item.getSuper();
                        titem.setBasic_overView("");
                        if(item.getuLanguage().equals("ko")){
                            //title ko
                            //tr en
                            if(DAO.Language.equals("ko")){
                                titem.setTitle(item.getTitle());
                            }else{
                                titem.setTitle(item.getTrtitle());
                            }
                        }else if(item.getuLanguage().equals("en")){
                            //title anything
                            if(DAO.Language.equals("en")){
                                titem.setTitle(item.getTitle());
                            }else{
                                titem.setTitle(item.getTrtitle());
                            }
                        }else{
                            titem.setTitle(item.getTitle());
                        }
                        DAO.handler.insert_spot(titem);
                        DAO.load_bookmarkSpot();
                        hold.postBookMarginImage.setImageResource(R.drawable.bookmark_do);
                        int before = Integer.parseInt(item.getReadCount());
                        hold.postBookMarginText.setText(""+(before+1));
                        item.setReadCount(""+(before+1));
                        addOriginal(item);
                    }else{
                        TourApiItem titem = item.getSuper();
                        hold.postBookMarginImage.setImageResource(R.drawable.bookmark_undo);
                        int before = Integer.parseInt(item.getReadCount());
                        hold.postBookMarginText.setText(""+(before-1));
                        item.setReadCount(""+(before-1));
                        DAO.handler.delete_spot(titem.getContentID());
                        DAO.load_bookmarkSpot();
                        subOriginal(item);
                    }
                }
            });
        }

        hold.postCommentRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostCommentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user", item.getUId());
                intent.putExtra("contentid", item.getContentID());
                intent.putExtra("title", item.getTitle());
                mContext.startActivity(intent);
            }
        });

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

    private void addOriginal(final mainPostItem item){
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int nowBookCount = Integer.parseInt((String)dataSnapshot.child(item.getContentID()).child("readCount").getValue());
                postRef.child(item.getContentID()).child("readCount").setValue((nowBookCount+1)+"");
                userRef.child(item.getContentID()).child("readCount").setValue((nowBookCount+1)+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void subOriginal(final mainPostItem item){
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int nowBookCount = Integer.parseInt((String)dataSnapshot.child(item.getContentID()).child("readCount").getValue());
                postRef.child(item.getContentID()).child("readCount").setValue((nowBookCount-1)+"");
                userRef.child(item.getContentID()).child("readCount").setValue((nowBookCount-1)+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void remove(String contentid, int position){
        postRef.child(contentid).removeValue();
        userRef.child(contentid).removeValue();
        list.remove(position);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
