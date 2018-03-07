package com.hch.hooney.tourtogether.Recycler.Comment;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.hch.hooney.tourtogether.DAO.TourAPI.Comment;
import com.hch.hooney.tourtogether.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hch on 2018-02-10.
 */

public class CommentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<Comment> list;
    private RecyclerView recyclerView;
    private String Contentid;
    private String user;

    private DatabaseReference rootRef;
    private DatabaseReference postRef;
    private DatabaseReference userRef;
    private DatabaseReference commentRef;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public CommentAdapter(Context mContext, ArrayList<Comment> list, RecyclerView recyclerView,
                            String contentid, String user) {
        this.mContext = mContext;
        this.list = list;
        this.recyclerView = recyclerView;
        this.Contentid = contentid;
        this.user = user;

        //firebase;
        rootRef = FirebaseDatabase.getInstance().getReference();
        postRef = rootRef.child("post");
        userRef = rootRef.child("ulog").child(user);
        commentRef = rootRef.child("comment");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_comment,parent,false);
        CommentHolder holder = new CommentHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CommentHolder hold = (CommentHolder)holder;
        final Comment item = list.get(position);

        hold.uName.setText(item.getUName());
        if(!item.getUProFileImage().equals("")){
            Picasso.with(mContext).load(item.getUProFileImage()).into(hold.uProfile);
        }
        hold.uProfile.setBackground(new ShapeDrawable(new OvalShape()));
        hold.uProfile.setClipToOutline(true);

        hold.WriteDate.setText(item.getWriteDate());
        hold.Content.setText(item.getContent());

        if(item.getUid().equals(DAO.user.getUID())){
            hold.Menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(mContext,v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.menu_comment_remove:
                                    remove(item.getC_Key(), position);
                                    break;
                            }
                            return true;
                        }
                    });
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.comment, popup.getMenu());
                    popup.show();
                }
            });
        }else{
            hold.Menu.setVisibility(View.GONE);
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

    private void remove(final String key, int postion){
        postRef.child(Contentid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int nowComment = Integer.parseInt((String)dataSnapshot.child("commentCount").getValue());
                postRef.child(Contentid).child("commentCount").setValue((nowComment-1)+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        userRef.child(Contentid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int nowComment = Integer.parseInt((String)dataSnapshot.child("commentCount").getValue());
                userRef.child(Contentid).child("commentCount").setValue((nowComment-1)+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        commentRef.child(Contentid).child(key).removeValue();
        list.remove(postion);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
