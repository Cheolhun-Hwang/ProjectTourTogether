package com.hch.hooney.tourtogether;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.Recycler.Translate.TranslateAdapter;
import com.hch.hooney.tourtogether.Recycler.Translate.TranslateItem;

import java.util.ArrayList;

public class TranslateListActivity extends AppCompatActivity {

    private ArrayList<TranslateItem> itemList;
    private TextView empty;
    private RecyclerView itemListView;
    private AdView adView;
    private ImageButton Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_list);

        init();
        setEvent();
    }

    private void init(){
        itemList = new ArrayList<TranslateItem>();
        load_translateList();

        itemListView = (RecyclerView) findViewById(R.id.translateList_list);
        itemListView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        itemListView.setHasFixedSize(false);

        empty = (TextView) findViewById(R.id.translate_list_empty_text);

        adView = (AdView)findViewById(R.id.translateList_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        Back = (ImageButton) findViewById(R.id.translateList_back);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setEvent(){
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        chkEmpty();
        setUi();
    }

    private void chkEmpty(){
        if(itemList.size() < 1){
            itemListView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }else{
            itemListView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
    }

    private void load_translateList(){
        Cursor cursor = DAO.handler.selectAll_translate();
        if(cursor.moveToFirst()){
            do{
                TranslateItem item = new TranslateItem();
                item.setT_id(cursor.getString(0));
                item.setT_from(cursor.getInt(1));
                item.setT_to(cursor.getInt(2));
                item.setT_origin(cursor.getString(3));
                item.setT_translate(cursor.getString(4));
                itemList.add(item);
            }while (cursor.moveToNext());
        }
    }

    private void setUi(){
        if(itemList.size() > 0){
            itemListView.setAdapter(new TranslateAdapter(itemList, itemListView,getApplicationContext()));
        }
    }
}
