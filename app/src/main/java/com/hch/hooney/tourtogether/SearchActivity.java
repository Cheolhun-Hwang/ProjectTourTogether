package com.hch.hooney.tourtogether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.Recycler.Search.SearchResultAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private final String TAG = "SearchActivity";

    //layout
    private RecyclerView searchResultListView;
    private EditText textFilterEdit;
    private ImageButton textFilterBTN;
    private TextView filedText;
    private ImageButton back;

    //variable
    private ArrayList<TourApiItem> searchResultList;
    private String filed_title;
    private String tourapi_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if(getIntent() == null){
            Log.e(TAG, "Error : None Intent");
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent), Toast.LENGTH_LONG).show();
            finish();
        }else{
            filed_title = (String) getIntent().getStringExtra("field");
            tourapi_URL = (String) getIntent().getStringExtra("url");

            Log.d(TAG, filed_title+" / "+tourapi_URL);


            init();
            event();
            setUI();
        }
    }

    private void init(){
        //res
        back = (ImageButton) findViewById(R.id.search_activity_back_btn);
        searchResultListView = (RecyclerView) findViewById(R.id.search_activity_recyclerview);
        searchResultListView.setHasFixedSize(false);
        searchResultListView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        textFilterEdit = (EditText) findViewById(R.id.search_activity_search_edittext);
        textFilterBTN = (ImageButton) findViewById(R.id.search_activity_search_btn);
        filedText = (TextView) findViewById(R.id.search_activity_title_field_textview);


        //variable
        loadDataToTOURAPI();
    }

    private void event(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textFilterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void setUI(){
        searchResultListView.setAdapter(new SearchResultAdapter(getApplicationContext(), searchResultList));
    }

    private void loadDataToTOURAPI(){
        searchResultList = new ArrayList<TourApiItem>();


    }
}
