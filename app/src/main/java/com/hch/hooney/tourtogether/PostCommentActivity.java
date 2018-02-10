package com.hch.hooney.tourtogether;

import android.app.ProgressDialog;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.TourAPI.Comment;
import com.hch.hooney.tourtogether.Recycler.Comment.CommentAdapter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostCommentActivity extends AppCompatActivity {
    private final String TAG = "PostCommentActivity";

    private String contentID;
    private String comment_title;

    private ArrayList<Comment> commentlist;

    private ImageButton back;
    private TextView title;

    private TextView zeroComment;
    private RecyclerView CommentListView;
    private EditText uWriteContent;
    private ImageButton uWriteSend;
    private ImageView uProfile;

    private DatabaseReference rootRef;
    private DatabaseReference postRef;
    private DatabaseReference userRef;
    private DatabaseReference commentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);

        if(getIntent()==null){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent), Toast.LENGTH_SHORT).show();
            finish();
        }else{
            contentID = getIntent().getStringExtra("contentid");
            comment_title = getIntent().getStringExtra("title");

            init();
            setEvent();
        }
    }

    private void init(){

        commentlist = new ArrayList<Comment>();

        //firebase;
        rootRef = FirebaseDatabase.getInstance().getReference();
        postRef = rootRef.child("post");
        userRef = rootRef.child("ulog").child(DAO.user.getUID());
        commentRef = rootRef.child("comment");

        back = (ImageButton) findViewById(R.id.post_comment_back_btn);
        title = (TextView) findViewById(R.id.post_comment_title);
        title.setText(comment_title);
        uProfile = (ImageView) findViewById(R.id.post_comment_uProfile);
        Picasso.with(getApplicationContext()).load(DAO.user.getUPROFILEIMAGE()).into(uProfile);
        uProfile.setBackground(new ShapeDrawable(new OvalShape()));
        uProfile.setClipToOutline(true);

        zeroComment = (TextView) findViewById(R.id.post_comment_zero_list);
        uWriteContent = (EditText)findViewById(R.id.post_comment_edittext);
        uWriteSend = (ImageButton) findViewById(R.id.post_comment_send_btn);
        CommentListView = (RecyclerView) findViewById(R.id.post_comment_list);
        CommentListView.setHasFixedSize(false);
        CommentListView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        CommentListView.setAdapter(new CommentAdapter(this, commentlist, CommentListView,
                contentID));
    }

    private void setEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        uWriteSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(uWriteContent.length() < 1 ){
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.post_comment_error_fill_form),
                            Toast.LENGTH_SHORT).show();
                }else{
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy_MM_dd_HH_mm_sss");
                    String key = DAO.user.getUID()+"_"+dateFormat2.format(date);

                    Comment item = new Comment();
                    item.setC_Key(key);
                    item.setUid(DAO.user.getUID());
                    item.setUName(DAO.user.getUNAME());
                    item.setUProFileImage(DAO.user.getUPROFILEIMAGE());
                    item.setWriteDate(dateFormat.format(date));
                    item.setContent(uWriteContent.getText().toString());

                    commentRef.child(contentID).child(key).setValue(item);
                    postRef.child(contentID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int nowcommentCount = Integer.parseInt((String)dataSnapshot.child("commentCount").getValue());
                            postRef.child(contentID).child("commentCount").setValue((nowcommentCount+1)+"");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    userRef.child(contentID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int nowcommentCount = Integer.parseInt((String)dataSnapshot.child("commentCount").getValue());
                            userRef.child(contentID).child("commentCount").setValue((nowcommentCount+1)+"");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    uWriteContent.setText("");
                }
            }
        });
        commentRef.child(contentID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentlist.clear();
                for(DataSnapshot items : dataSnapshot.getChildren()){
                    Comment item = items.getValue(Comment.class);
                    commentlist.add(0, item);
                }

                if(commentlist.size() >= 1){
                    CommentListView.setVisibility(View.VISIBLE);
                    CommentListView.getAdapter().notifyDataSetChanged();
                    zeroComment.setVisibility(View.GONE);
                }else{
                    zeroComment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
