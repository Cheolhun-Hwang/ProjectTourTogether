package com.hch.hooney.tourtogether;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.Recycler.Course.CourseAdapter;
import com.hch.hooney.tourtogether.Recycler.Search.SearchResultAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CourseActivity extends AppCompatActivity {

    private final String TAG = "SearchActivity";
    private ProgressDialog asyncDialog;
    //layout
    private RecyclerView searchResultListView;
    private EditText textFilterEdit;
    private ImageButton textFilterBTN;
    private TextView filedText;
    private ImageButton back;
    private Button filterPop;
    private Button filterReg;
    private TextView NoListText;

    //variable
    private ArrayList<TourApiItem> searchResultList;
    private String filed_title;
    private String tourapi_URL;
    private String Contenttype;
    private boolean isPopular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

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
        }
    }

    private void init(){
        asyncDialog = new ProgressDialog(this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));

        //res
        back = (ImageButton) findViewById(R.id.course_activity_back_btn);
        searchResultListView = (RecyclerView) findViewById(R.id.course_activity_recyclerview);
        searchResultListView.setHasFixedSize(false);
        searchResultListView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        textFilterEdit = (EditText) findViewById(R.id.course_activity_search_edittext);
        textFilterBTN = (ImageButton) findViewById(R.id.course_activity_search_btn);
        filedText = (TextView) findViewById(R.id.course_activity_title_field_textview);
        filedText.setText(filed_title);
        filterPop = (Button) findViewById(R.id.course_activity_filter_Popularity);
        filterReg = (Button) findViewById(R.id.course_activity_filter_Register);
        NoListText = (TextView) findViewById(R.id.course_activity_noneList_text);


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
        if(searchResultList.size() <= 0){
            NoListText.setVisibility(View.VISIBLE);
            searchResultListView.setVisibility(View.GONE);
        }else{
            NoListText.setVisibility(View.GONE);
            searchResultListView.setAdapter(new CourseAdapter(getApplicationContext(), searchResultList));
        }

        asyncDialog.dismiss();
    }

    private void filter_titleText(){
        if(textFilterEdit.getText().equals("")){
            searchResultListView.setAdapter(new CourseAdapter(getApplicationContext(), searchResultList));
            return;
        }else{
            ArrayList<TourApiItem> tempList = new ArrayList<TourApiItem>();
            for(int i=0; i<searchResultList.size();i++){
                if(searchResultList.get(i).getTitle().toLowerCase().contains(textFilterEdit.getText().toString().toLowerCase())){
                    tempList.add(searchResultList.get(i));
                }
            }
            searchResultListView.setAdapter(new CourseAdapter(getApplicationContext(), tempList));
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
        if(searchResultList.size() > 0){
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
            searchResultListView.setAdapter(new CourseAdapter(getApplicationContext(), searchResultList));
        }
    }

    private void filterReg(){
        if(searchResultList.size() > 0){
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
            searchResultListView.setAdapter(new CourseAdapter(getApplicationContext(), searchResultList));
        }
    }

    private void loadDataToTOURAPI(){
        searchResultList = new ArrayList<TourApiItem>();

        asyncDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                parse_xml_list();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setUI();
                    }
                });
            }
        }).start();
    }

    private void parse_xml_list(){
        StringBuffer buffer=new StringBuffer();
        try {
            URL url= new URL(tourapi_URL); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            TourApiItem item = null;
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    /*case XmlPullParser.START_DOCUMENT:
                        buffer.append("Strat :");
                        break;*/

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기

                        if(tag.equals("item")){
                            item = new TourApiItem();
                        }else if(tag.equals("addr1")){
                            xpp.next();
                            item.setAddr1(xpp.getText());
                        }else if(tag.equals("addr2")){
                            xpp.next();
                            item.setAddr2(xpp.getText());
                        }else if(tag.equals("areacode")){
                            xpp.next();
                            item.setAreaCode(xpp.getText());
                        }else if(tag.equals("contentid")){
                            xpp.next();
                            item.setContentID(xpp.getText());
                        }else if(tag.equals("contenttypeid")){
                            xpp.next();
                            item.setContentTypeID(xpp.getText());
                        }else if(tag.equals("cat1")){
                            xpp.next();
                            item.setCat1(xpp.getText());
                        }else if(tag.equals("cat2")){
                            xpp.next();
                            item.setCat2(xpp.getText());
                        }else if(tag.equals("cat3")){
                            xpp.next();
                            item.setCat3(xpp.getText());
                        }else if(tag.equals("firstimage")){
                            xpp.next();
                            item.setFirstImage(xpp.getText());
                        }else if(tag.equals("mapx")){
                            xpp.next();
                            item.setMapx(Double.parseDouble(xpp.getText()));
                        }else if(tag.equals("mapy")){
                            xpp.next();
                            item.setMapy(Double.parseDouble(xpp.getText()));
                        }else if(tag.equals("modifiedtime")){
                            xpp.next();
                            item.setModifyDateTIme(xpp.getText());
                        }else if(tag.equals("readcount")){
                            xpp.next();
                            item.setReadCount(xpp.getText());
                        }else if(tag.equals("sigungucode")){
                            xpp.next();
                            item.setSigunguCode(xpp.getText());
                        }else if(tag.equals("title")){
                            xpp.next();
                            item.setTitle(xpp.getText());
                        }else if(tag.equals("tel")){
                            xpp.next();
                            item.setTel(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기
                        if(tag.equals("item")) {
                            searchResultList.add(item);
                        }
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }//getXmlData method...

}
