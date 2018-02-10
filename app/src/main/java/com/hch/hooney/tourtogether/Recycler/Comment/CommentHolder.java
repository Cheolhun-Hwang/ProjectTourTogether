package com.hch.hooney.tourtogether.Recycler.Comment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hch.hooney.tourtogether.R;

/**
 * Created by hch on 2018-02-10.
 */

public class CommentHolder extends RecyclerView.ViewHolder {
    public TextView uName;
    public ImageView uProfile;
    public TextView WriteDate;
    public ImageButton Menu;
    public TextView Content;

    public CommentHolder(View itemView) {
        super(itemView);

        uName = (TextView) itemView.findViewById(R.id.item_post_comment_userName);
        uProfile = (ImageView) itemView.findViewById(R.id.item_post_comment_profileImage);
        WriteDate = (TextView) itemView.findViewById(R.id.item_post_comment_writeDate);
        Menu = (ImageButton) itemView.findViewById(R.id.item_post_comment_menu);
        Content = (TextView) itemView.findViewById(R.id.item_post_comment_content);
    }
}
