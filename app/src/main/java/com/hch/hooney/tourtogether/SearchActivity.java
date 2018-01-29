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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {
    private final String TAG = "SearchActivity";

    //layout
    private RecyclerView searchResultListView;
    private EditText textFilterEdit;
    private ImageButton textFilterBTN;
    private TextView filedText;
    private ImageButton back;
    private Button filterPop;
    private Button filterReg;

    //variable
    private ArrayList<TourApiItem> searchResultList;
    private String filed_title;
    private String tourapi_URL;
    private String Contenttype;
    private boolean isPopular;

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
            Contenttype = (String) getIntent().getStringExtra("contentTypeID");

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
        filedText.setText(filed_title);
        filterPop = (Button) findViewById(R.id.search_activity_filter_Popularity);
        filterReg = (Button) findViewById(R.id.search_activity_filter_Register);


        //variable
        isPopular = true;
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
                filter_titleText();
            }
        });
        filterReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPopular){
                    clear_filterBTN();
                    select_filterBTN();
                    filterReg();
                    isPopular = false;
                }
            }
        });
        filterPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPopular){
                    clear_filterBTN();
                    select_filterBTN();
                    filterPop();
                    isPopular = true;
                }
            }
        });
    }

    private void setUI(){
        searchResultListView.setAdapter(new SearchResultAdapter(getApplicationContext(), searchResultList, filed_title));
    }

    private void filter_titleText(){
        if(textFilterEdit.getText().equals("")){
            searchResultListView.setAdapter(new SearchResultAdapter(getApplicationContext(), searchResultList, filed_title));
            return;
        }else{
            ArrayList<TourApiItem> tempList = new ArrayList<TourApiItem>();
            for(int i=0; i<searchResultList.size();i++){
                if(searchResultList.get(i).getTitle().toLowerCase().contains(textFilterEdit.getText().toString().toLowerCase())){
                    tempList.add(searchResultList.get(i));
                }
            }
            searchResultListView.setAdapter(new SearchResultAdapter(getApplicationContext(), tempList, filed_title));
        }
    }

    private void clear_filterBTN(){
        filterPop.setBackgroundColor(getResources().getColor(R.color.grey_200));
        filterPop.setTextColor(getResources().getColor(R.color.grey_400));
        filterReg.setBackgroundColor(getResources().getColor(R.color.grey_200));
        filterReg.setTextColor(getResources().getColor(R.color.grey_400));
    }

    private void select_filterBTN(){
        if(!isPopular){
            filterPop.setBackgroundColor(getResources().getColor(R.color.MainColor));
            filterPop.setTextColor(getResources().getColor(R.color.white));
        }else{
            filterReg.setBackgroundColor(getResources().getColor(R.color.MainColor));
            filterReg.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void filterPop(){
        ArrayList<TourApiItem> tempList = null;

        for(int i =0 ; i<searchResultList.size();i++){
            if( tempList == null){
                tempList = new ArrayList<TourApiItem>();
                tempList.add(searchResultList.get(i));
            }else{
                int tour_readCount = Integer.parseInt(searchResultList.get(i).getReadCount());

                for(int j = 0 ; j<tempList.size() ; j++){
                    int temp_readCount = Integer.parseInt(tempList.get(j).getReadCount());

                    if(tour_readCount >= temp_readCount){
                        tempList.add(j, searchResultList.get(i));
                        break;
                    }else if(tour_readCount < temp_readCount){
                        if(j == tempList.size()-1){
                            tempList.add(searchResultList.get(i));
                            break;
                        }
                    }
                }
                //templist and
            }
        }
        //searchlist end

        searchResultList = tempList;
        searchResultListView.setAdapter(new SearchResultAdapter(getApplicationContext(), searchResultList, filed_title));
    }

    private void filterReg(){
        ArrayList<TourApiItem> tempList = null;

        for(int i =0 ; i<searchResultList.size();i++){
            if( tempList == null){
                tempList = new ArrayList<TourApiItem>();
                tempList.add(searchResultList.get(i));
            }else{
                Date tour_date = null;
                try {
                    tour_date =  new SimpleDateFormat("yyyyMMddHHmmss").parse(searchResultList.get(i).getModifyDateTIme());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                for(int j = 0 ; j<tempList.size() ; j++){
                    Date compare_date = null;
                    try {
                        compare_date = new SimpleDateFormat("yyyyMMddHHmmss").parse(tempList.get(j).getModifyDateTIme());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    int compare = tour_date.compareTo(compare_date);
                    if(compare >= 0){
                        tempList.add(j, searchResultList.get(i));
                        break;
                    }else if(compare < 0){
                        if(j == tempList.size()-1){
                            tempList.add(searchResultList.get(i));
                            break;
                        }
                    }
                }
                //templist and
            }
        }
        //searchlist end

        searchResultList = tempList;
        searchResultListView.setAdapter(new SearchResultAdapter(getApplicationContext(), searchResultList, filed_title));
    }

    private void loadDataToTOURAPI(){
        searchResultList = new ArrayList<TourApiItem>();

        //temp
        TourApiItem tourApiItem1 = new TourApiItem();
        tourApiItem1.setAddr1("5, Jahamun-ro 5-gil, Jongno-gu, Seoul");
        tourApiItem1.setAddr2("");
        tourApiItem1.setAreaCode("1");
        tourApiItem1.setCat1("A05");
        tourApiItem1.setCat2("A0502");
        tourApiItem1.setCat3("A05020100");
        tourApiItem1.setContentID("582457");
        tourApiItem1.setContentTypeID("82");
        tourApiItem1.setFirstImage("http://tong.visitkorea.or.kr/cms/resource/94/2018594_image2_1.jpg");
        tourApiItem1.setMapx(126.9715935592);
        tourApiItem1.setMapy(37.5776167233);
        tourApiItem1.setModifyDateTIme("20170926110830");
        tourApiItem1.setReadCount("227352");
        tourApiItem1.setSigunguCode("23");
        tourApiItem1.setTitle("Tosokchon Samgyetang (토속촌 삼계탕)");

        searchResultList.add(tourApiItem1);

        TourApiItem tourApiItem2 = new TourApiItem();
        tourApiItem2.setAddr1("105, Namsangongwon-gil, Yongsan-gu, Seoul");
        tourApiItem2.setAddr2("");
        tourApiItem2.setAreaCode("1");
        tourApiItem2.setCat1("A05");
        tourApiItem2.setCat2("A0502");
        tourApiItem2.setCat3("A05020100");
        tourApiItem2.setContentID("555839");
        tourApiItem2.setContentTypeID("82");
        tourApiItem2.setFirstImage("http://tong.visitkorea.or.kr/cms/resource/87/1872587_image2_1.jpg");
        tourApiItem2.setMapx(126.9878594933);
        tourApiItem2.setMapy(37.5511312042);
        tourApiItem2.setModifyDateTIme("20170227164141");
        tourApiItem2.setReadCount("37478");
        tourApiItem2.setSigunguCode("21");
        tourApiItem2.setTitle("n·GRILL (엔그릴)");

        searchResultList.add(tourApiItem2);

        TourApiItem tourApiItem3 = new TourApiItem();
        tourApiItem3.setAddr1("18, Baekbeom-ro, Mapo-gu, Seoul");
        tourApiItem3.setAddr2("");
        tourApiItem3.setAreaCode("1");
        tourApiItem3.setCat1("A05");
        tourApiItem3.setCat2("A0502");
        tourApiItem3.setCat3("A05020100");
        tourApiItem3.setContentID("1555859");
        tourApiItem3.setContentTypeID("82");
        tourApiItem3.setFirstImage("http://tong.visitkorea.or.kr/cms/resource/90/2044190_image2_1.jpg");
        tourApiItem3.setMapx(126.9368640821);
        tourApiItem3.setMapy(37.5531883848);
        tourApiItem3.setModifyDateTIme("20161229172633");
        tourApiItem3.setReadCount("59672");
        tourApiItem3.setSigunguCode("13");
        tourApiItem3.setTitle("Palsaek Samgyeopsal (팔색삼겹살)");

        searchResultList.add(tourApiItem3);

        TourApiItem tourApiItem4 = new TourApiItem();
        tourApiItem4.setAddr1("37, Myeongdong 8ga-gil, Jung-gu, Seoul");
        tourApiItem4.setAddr2("");
        tourApiItem4.setAreaCode("1");
        tourApiItem4.setCat1("A05");
        tourApiItem4.setCat2("A0502");
        tourApiItem4.setCat3("A05020100");
        tourApiItem4.setContentID("349078");
        tourApiItem4.setContentTypeID("82");
        tourApiItem4.setFirstImage("http://tong.visitkorea.or.kr/cms/resource/85/1290985_image2_1.jpg");
        tourApiItem4.setMapx(126.9865454168);
        tourApiItem4.setMapy(37.5619910304);
        tourApiItem4.setModifyDateTIme("20160831145745");
        tourApiItem4.setReadCount("58188");
        tourApiItem4.setSigunguCode("24");
        tourApiItem4.setTitle("Gogung - Myeongdong Branch (고궁 - 명동점)");

        searchResultList.add(tourApiItem4);

        TourApiItem tourApiItem5 = new TourApiItem();
        tourApiItem5.setAddr1("272, Itaewon-ro, Yongsan-gu, Seoul");
        tourApiItem5.setAddr2("");
        tourApiItem5.setAreaCode("1");
        tourApiItem5.setCat1("A05");
        tourApiItem5.setCat2("A0502");
        tourApiItem5.setCat3("A05020100");
        tourApiItem5.setContentID("1151309");
        tourApiItem5.setContentTypeID("82");
        tourApiItem5.setFirstImage("http://tong.visitkorea.or.kr/cms/resource/72/1143072_image2_1.jpg");
        tourApiItem5.setMapx(127.0020585516);
        tourApiItem5.setMapy(37.5386995705);
        tourApiItem5.setModifyDateTIme("20171226150755");
        tourApiItem5.setReadCount("49386");
        tourApiItem5.setSigunguCode("21");
        tourApiItem5.setTitle("Passion 5 (패션5)");

        searchResultList.add(tourApiItem5);

        TourApiItem tourApiItem6 = new TourApiItem();
        tourApiItem6.setAddr1("11-4, Insadong 10-gil, Jongno-gu, Seoul");
        tourApiItem6.setAddr2("");
        tourApiItem6.setAreaCode("1");
        tourApiItem6.setCat1("A05");
        tourApiItem6.setCat2("A0502");
        tourApiItem6.setCat3("A05020100");
        tourApiItem6.setContentID("1945709");
        tourApiItem6.setContentTypeID("82");
        tourApiItem6.setFirstImage("http://tong.visitkorea.or.kr/cms/resource/81/1945681_image2_1.JPG");
        tourApiItem6.setMapx(126.9854562711);
        tourApiItem6.setMapy(37.5745589941);
        tourApiItem6.setModifyDateTIme("20180105160524");
        tourApiItem6.setReadCount("4236");
        tourApiItem6.setSigunguCode("24");
        tourApiItem6.setTitle("Hemlagat (헴라갓)");

        searchResultList.add(tourApiItem6);
    }
}
