package com.hch.hooney.tourtogether;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.MyCourse;
import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.Recycler.Bookmark.MyCourse.MyCourseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddMyCourseActivity extends AppCompatActivity {
    private final String TAG = "AddMyCourseActivity";

    private ImageButton back;
    private Button complete;

    private int nowListSize;

    private EditText title;
    private RecyclerView recyclerView;

    private DatabaseReference rootRef;
    private DatabaseReference mycourseRef;

    private ArrayList<TourApiItem> temp_book_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_course);

        if(getIntent() == null){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent), Toast.LENGTH_SHORT).show();
            finish();
        }else{
            nowListSize = getIntent().getIntExtra("size", 0);

            init();
            setEvent();
            setUi();
        }


    }

    private void init(){
        //firebase;
        rootRef = FirebaseDatabase.getInstance().getReference();
        mycourseRef = rootRef.child("MyCourse").child(DAO.user.getUID());

        temp_book_list = DAO.bookmarkSpotList;

        back = (ImageButton)findViewById(R.id.add_mycourse_back);
        complete = (Button) findViewById(R.id.add_mycourse_complete);
        title = (EditText) findViewById(R.id.add_mycourse_edittext);
        recyclerView = (RecyclerView) findViewById(R.id.add_mycourse_list);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
    }

    private void setEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.add_course_add_Error_title), Toast.LENGTH_SHORT).show();
                }else{
                    if(temp_book_list.size() < 1){
                        Toast.makeText(getApplicationContext(), getResources().getText(R.string.add_course_add_Error), Toast.LENGTH_SHORT).show();
                    }else{
                        MyCourse myCourse = new MyCourse();

                        //Country
                        myCourse.setMc_Coutry(DAO.Country);
                        //Language
                        myCourse.setMc_Language(DAO.Language);
                        //Title
                        myCourse.setMc_Title(title.getText().toString());

                        //Now Date
                        Date nowDate = new Date();
                        SimpleDateFormat dateSet = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        myCourse.setMc_Date(dateSet.format(nowDate));

                        //Course List
                        myCourse.setMc_routeList(temp_book_list);

                        //key
                        myCourse.setMc_key((nowListSize+1)+"");

                        mycourseRef.child(myCourse.getMc_key()).setValue(myCourse);

                        setResult(Activity.RESULT_OK);
                        finish();
                    }

                }


            }
        });
    }

    private void setUi(){
        recyclerView.setAdapter(new MyCourseAdapter(getApplicationContext(), recyclerView, temp_book_list));
    }
}
