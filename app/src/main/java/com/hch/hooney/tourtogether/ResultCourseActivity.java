package com.hch.hooney.tourtogether;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.hch.hooney.tourtogether.DAO.TourAPI.Course;
import com.hch.hooney.tourtogether.DAO.TourAPI.Nature;
import com.hch.hooney.tourtogether.DAO.TourApiItem;

import java.util.ArrayList;

public class ResultCourseActivity extends AppCompatActivity {
    private final static String TAG = "NatureActivity";
    private ProgressDialog asyncDialog;

    //layout
    //share
    private ImageButton back;
    private TextView Field;
    private ImageButton bookmaking;

    private TextView modifyDate;
    private TextView title;
    private ImageView mainImage;

    private TextView total_distanse;
    private TextView time;
    private TextView overview;

    private RecyclerView spotsListView;

    private SupportMapFragment supportMapFragment;

    //variable
    private GoogleMap googleMap;

    private Course course;

    private String url_basic_info;
    private String url_intro_info;
    private String url_course_info;

    private String field;

    private boolean isBookmarking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_course);

        if(getIntent()==null){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent), Toast.LENGTH_LONG).show();
            finish();
        }else{
            TourApiItem item = (TourApiItem) getIntent().getSerializableExtra("basic");
            nature = new Nature(item);
            field = getIntent().getStringExtra("field");

            init();
            setURLS();
            asyncDialog.show();
            new Thread(new Runnable() {
                @SuppressLint("LongLogTag")
                @Override
                public void run() {
                    //loadDate
                    Log.d(TAG, url_basic_info);
                    Log.d(TAG, url_intro_info);
                    Log.d(TAG, url_course_info);
                    parse_xml_basic();
                    parse_xml_intro();
                    parse_xml_spots_repeat();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUI();
                            setMap();
                            asyncDialog.dismiss();
                        }
                    });
                }
            }).start();
        }
    }

    private void init(){
        asyncDialog = new ProgressDialog(this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));

        //layout
        back = (ImageButton) findViewById(R.id.result_course_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Field = (TextView) findViewById(R.id.result_course_field);
        bookmaking = (ImageButton)findViewById(R.id.result_course_bookmarking);
        modifyDate = (TextView) findViewById(R.id.result_course_modify);
        title = (TextView) findViewById(R.id.result_course_title);
        total_distanse = (TextView) findViewById(R.id.result_course_total_distance);
        time = (TextView) findViewById(R.id.result_course_time);
        overview = (TextView) findViewById(R.id.result_course_overview);
        mainImage = (ImageView) findViewById(R.id.result_course_mainImage);

        spotsListView = (RecyclerView) findViewById(R.id.nt_result_smallImageView);
        spotsListView.setHasFixedSize(true);
        spotsListView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.result_course_map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.result_course_map, supportMapFragment).commit();
        }
    }
}
