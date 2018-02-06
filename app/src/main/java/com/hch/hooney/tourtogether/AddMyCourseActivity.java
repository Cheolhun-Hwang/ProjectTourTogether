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

import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.MyCourse;
import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.Recycler.Bookmark.MyCourse.MyCourseAdapter;

import java.util.ArrayList;

public class AddMyCourseActivity extends AppCompatActivity {
    private final String TAG = "AddMyCourseActivity";

    private ImageButton back;
    private Button complete;

    private EditText title;
    private RecyclerView recyclerView;

    private ArrayList<TourApiItem> temp_book_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_course);

        init();
        setEvent();
        setUi();
    }

    private void init(){
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

                }else{
                    MyCourse myCourse = new MyCourse();

                    //DAO.myCoursesList.add();

                    setResult(Activity.RESULT_OK);
                    finish();
                }


            }
        });
    }

    private void setUi(){
        recyclerView.setAdapter(new MyCourseAdapter(getApplicationContext(), recyclerView, temp_book_list));
    }
}
