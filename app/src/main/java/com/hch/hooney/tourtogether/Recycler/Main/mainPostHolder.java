package com.hch.hooney.tourtogether.Recycler.Main;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hch.hooney.tourtogether.R;

/**
 * Created by qewqs on 2018-01-23.
 */

public class mainPostHolder extends RecyclerView.ViewHolder {
    public ImageView userProfile;
    public TextView userName;
    public ImageView postMenu;
    public ImageView postImage;
    public TextView postContext;
    public TextView postLocation;

    public RelativeLayout postLikeRL;
    public ImageView postLikeImage;
    public TextView postLikeText;

    public RelativeLayout postCommentRL;
    public TextView postCommentText;

    public mainPostHolder(View itemView) {
        super(itemView);

        userProfile = (ImageView) itemView.findViewById(R.id.userProfileImage);
        userName = (TextView) itemView.findViewById(R.id.userNameTextview);
        postMenu = (ImageView) itemView.findViewById(R.id.postMenuBTN);
        postImage = (ImageView) itemView.findViewById(R.id.postImage);
        postContext = (TextView) itemView.findViewById(R.id.postContext);
        postLocation = (TextView) itemView.findViewById(R.id.postLocation);
        postLikeRL = (RelativeLayout) itemView.findViewById(R.id.LikeLayout);
        postLikeImage = (ImageView) itemView.findViewById(R.id.likeImage);
        postLikeText = (TextView) itemView.findViewById(R.id.likeText);
        postCommentRL = (RelativeLayout) itemView.findViewById(R.id.CommentLayout);
        postCommentText = (TextView) itemView.findViewById(R.id.commentText);
    }
}
