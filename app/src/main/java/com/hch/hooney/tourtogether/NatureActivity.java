package com.hch.hooney.tourtogether;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.TourAPI.Accomodation;
import com.hch.hooney.tourtogether.DAO.TourAPI.Dining;
import com.hch.hooney.tourtogether.DAO.TourAPI.Nature;
import com.hch.hooney.tourtogether.DAO.TourAPI.RepeatInfo;
import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.Recycler.Result.ResultSingleImageAdapter;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NatureActivity extends AppCompatActivity {
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
    private TextView addr1;

    private LinearLayout homepageLayout;
    private TextView homepage;
    private LinearLayout telLayout;
    private TextView tel;
    private LinearLayout directionlayout;
    private TextView direction;
    private TextView overview;

    private LinearLayout smallLayout;
    private RecyclerView smallList;

    private SupportMapFragment supportMapFragment;

    private LinearLayout nt_useTime_layout;
    private TextView nt_useTime;
    private LinearLayout nt_restDate_layout;
    private TextView nt_restDate;
    private LinearLayout nt_contactus_layout;
    private TextView nt_contactus;
    private LinearLayout nt_result_add_layout;
    private TextView nt_result_add;

    //variable
    private GoogleMap googleMap;

    private Nature nature;
    private String url_basic_info;
    private String url_intro_info;
    private String url_repeate_info;
    private String url_images_info;

    private String ContentID;
    private String ContentTypeID;
    private String field;

    private boolean isBookmarking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature);

        if(getIntent()==null){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent), Toast.LENGTH_LONG).show();
            finish();
        }else{
            ContentTypeID = getIntent().getStringExtra("ContentTypeID");
            ContentID = getIntent().getStringExtra("ContentID");
            field = getIntent().getStringExtra("field");

            init();
            setURLS();

            new Thread(new Runnable() {
                @SuppressLint("LongLogTag")
                @Override
                public void run() {
                    //loadDate
                    Log.d(TAG, url_basic_info);
                    Log.d(TAG, url_intro_info);
                    Log.d(TAG, url_repeate_info);
                    Log.d(TAG, url_images_info);
                    parse_xml_Nature_basic();
                    parse_xml_Nature_intro();
                    parse_xml_Nature_repeat();
                    parse_xml_Nature_smallImages();

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
        asyncDialog = new ProgressDialog(getApplicationContext());
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));

        //variable
        nature = new Nature();

        //layout
        back = (ImageButton) findViewById(R.id.nt_result_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Field = (TextView) findViewById(R.id.nt_result_field);
        bookmaking = (ImageButton)findViewById(R.id.nt_result_bookmarking);
        modifyDate = (TextView) findViewById(R.id.nt_result_modify);
        title = (TextView) findViewById(R.id.nt_result_title);
        directionlayout=(LinearLayout)findViewById(R.id.nt_result_directions_layout);
        direction = (TextView) findViewById(R.id.nt_result_directions);
        telLayout = (LinearLayout) findViewById(R.id.nt_result_tel_layout);
        tel = (TextView) findViewById(R.id.nt_result_tel);
        homepageLayout = (LinearLayout) findViewById(R.id.nt_result_homepage_layout);
        homepage = (TextView) findViewById(R.id.nt_result_homepage);
        addr1 = (TextView) findViewById(R.id.nt_result_addr);
        overview = (TextView) findViewById(R.id.nt_result_overview);
        mainImage = (ImageView) findViewById(R.id.nt_result_mainImage);

        nt_useTime_layout = (LinearLayout)findViewById(R.id.nt_result_useTime_layout);
        nt_useTime = (TextView) findViewById(R.id.nt_result_useTime);
        nt_restDate_layout = (LinearLayout)findViewById(R.id.nt_result_restDate_layout);
        nt_restDate = (TextView) findViewById(R.id.nt_result_restDate);
        nt_contactus_layout = (LinearLayout)findViewById(R.id.nt_result_contactus_layout);
        nt_contactus = (TextView) findViewById(R.id.nt_result_contactus);
        nt_result_add_layout = (LinearLayout)findViewById(R.id.nt_result_add_layout);
        nt_result_add = (TextView) findViewById(R.id.nt_result_add);

        smallLayout = (LinearLayout)findViewById(R.id.nt_result_smallImageView_layout);
        smallList = (RecyclerView) findViewById(R.id.nt_result_smallImageView);
        smallList.setHasFixedSize(true);
        smallList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.nt_result_map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.nt_result_map, supportMapFragment).commit();
        }
    }

    private void setUI(){
        Field.setText(field);

        isBookmarking = DAO.chkAddBookmarking(nature.getBasic_contentID());

        if(isBookmarking){
            bookmaking.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_do));
        }
        bookmaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBookmarking){
                    TourApiItem item = new TourApiItem();
                    item.setAddr1(nature.getBasic_addr1());
                    item.setAddr2("");
                    item.setAreaCode(nature.getBasic_areaCode());
                    item.setCat1("");
                    item.setCat2("");
                    item.setCat3("");
                    item.setContentID(nature.getBasic_contentID());
                    item.setContentTypeID(nature.getBasic_contentTypeID());
                    item.setFirstImage(nature.getBasic_firstImage());
                    item.setMapx(nature.getBasic_mapX());
                    item.setMapy(nature.getBasic_mapY());
                    item.setModifyDateTIme(nature.getBasic_modifyDate());
                    item.setReadCount("");
                    item.setSigunguCode(nature.getBasic_sigungu());
                    item.setTitle(nature.getBasic_title());

                    DAO.bookmarkSpotList.add(0, item);
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.notify_add_bookmarking), Toast.LENGTH_LONG).show();
                    bookmaking.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_do));
                    isBookmarking = true;
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.notify_already_add_bookmarking), Toast.LENGTH_LONG).show();
                }
            }
        });

        if(!nature.getBasic_modifyDate().equals("")){
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyyMMddHHmmss").parse(nature.getBasic_modifyDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            modifyDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
        }

        if(!nature.getBasic_firstImage().equals("")){
            Picasso.with(getApplicationContext()).load(nature.getBasic_firstImage()).into(mainImage);
        }else{
            mainImage.setVisibility(View.GONE);
        }

        if(!nature.getBasic_title().equals("")){
            title.setText(nature.getBasic_title());
        }

        if(!nature.getBasic_addr1().equals("")){
            addr1.setText(nature.getBasic_addr1());
        }

        if(!nature.getBasic_tel().equals("")){
            tel.setText(nature.getBasic_tel());
            Linkify.addLinks(tel, Linkify.PHONE_NUMBERS);
        }else{
            telLayout.setVisibility(View.GONE);
        }

        if(!nature.getBasic_homepage().equals("")){
            homepage.setText(Html.fromHtml(nature.getBasic_homepage().replaceAll("<br>", " ")));
            Linkify.addLinks(homepage, Linkify.WEB_URLS);
        }else{
            homepageLayout.setVisibility(View.GONE);
        }

        if(!nature.getBasic_directory().equals("")){
            direction.setText(Html.fromHtml(nature.getBasic_directory()));
        }else{
            directionlayout.setVisibility(View.GONE);
        }

        if(!nature.getBasic_overView().equals("")){
            overview.setText(Html.fromHtml(nature.getBasic_overView()));
        }

        if(!nature.getIntro_infocenter().equals("")){
            nt_contactus.setText(Html.fromHtml(nature.getIntro_infocenter()));
            Linkify.addLinks(nt_contactus, Linkify.ALL);
        }else{
            nt_contactus_layout.setVisibility(View.GONE);
        }

        if(!nature.getIntro_restdate().equals("")){
            nt_restDate.setText(Html.fromHtml(nature.getIntro_restdate()));
        }else{
            nt_restDate_layout.setVisibility(View.GONE);
        }

        if(!nature.getIntro_usetime().equals("")){
            nt_useTime.setText(Html.fromHtml(nature.getIntro_usetime()));
        }else{
            nt_useTime_layout.setVisibility(View.GONE);
        }

        if(nature.getRepeateList() == null || (nature.getRepeateList().size() < 1)){
            nt_result_add_layout.setVisibility(View.GONE);
        }else{
            StringBuffer buffer = new StringBuffer();

            for (RepeatInfo repeatInfo:nature.getRepeateList()) {
                buffer.append(repeatInfo.getInfoName()+" :\n");
                buffer.append("\t\t\t"+repeatInfo.getInfoText()+"\n\n");
            }

            nt_result_add.setText(Html.fromHtml(buffer.toString()));
        }

        if(nature.getSmallImageList()==null){
            smallLayout.setVisibility(View.GONE);
        }else{
            smallList.setAdapter(new ResultSingleImageAdapter(getApplicationContext(), nature.getSmallImageList()));
        }
    }

    private void setMap(){
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;
                googleMap.getUiSettings().setScrollGesturesEnabled(false);

                float bitmapDescriptorFactory = 0;
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(guideItem.getGpsy(), guideItem.getGpsx()), 14));
                Log.d(TAG, nature.getBasic_mapX()+" / " + nature.getBasic_mapY());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(nature.getBasic_mapY(), nature.getBasic_mapX()), 16));
                bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ROSE;
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(nature.getBasic_mapY(), nature.getBasic_mapX()))
                        .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                        .title(nature.getBasic_title())
                        .zIndex((float) 0)
                );

                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                } else {
                    Toast.makeText(getApplicationContext(), "GPS 권한을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setURLS(){
        String service="";
        if(DAO.Language.equals("ko")){
            service="KorService";
        }else{
            service="EngService";
        }

        url_basic_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailCommon?ServiceKey="+ DAO.ServiceKey+
                "&contentTypeId="+ContentTypeID+ "&contentId="+ContentID+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
        url_intro_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailIntro?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+ContentTypeID+ "&contentId="+ContentID+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
        url_repeate_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailInfo?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+ContentTypeID+"&contentId="+ContentID+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y";
        url_images_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailImage?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+ContentTypeID+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&contentId="+ContentID+"&imageYN=Y";
    }

    private void parse_xml_Nature_smallImages(){
        ArrayList<String> tempList = new ArrayList<String>();
        try {
            URL url= new URL(url_images_info); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결


            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기
                        if(tag.equals("smallimageurl")){
                            xpp.next();
                            tempList.add(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType= xpp.next();
            }
            nature.setSmallImageList(tempList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }//getXmlData method...

    private void parse_xml_Nature_repeat(){
        ArrayList<RepeatInfo> tempList = new ArrayList<RepeatInfo>();
        try {
            URL url= new URL(url_repeate_info); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결


            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            RepeatInfo item = null;
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기
                        if(tag.equals("item")){
                            item = new RepeatInfo();
                        }else if(tag.equals("infoname")){
                            xpp.next();
                            item.setInfoName(xpp.getText());
                        }else if(tag.equals("infotext")){
                            xpp.next();
                            item.setInfoText(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();
                        if(tag.equals("item")) {
                            tempList.add(item);
                        }
                        break;
                }
                eventType= xpp.next();
            }
            nature.setRepeateList(tempList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }//getXmlData method...

    private void parse_xml_Nature_intro(){
        StringBuffer buffer=new StringBuffer();
        try {
            URL url= new URL(url_intro_info); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    /*case XmlPullParser.START_DOCUMENT:
                        buffer.append("Strat :");
                        break;*/
                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기

                        if(tag.equals("infocenter")){
                            xpp.next();
                            if(xpp.getText() == null){
                                nature.setIntro_infocenter("");
                            }else{
                                nature.setIntro_infocenter(xpp.getText());
                            }
                        }else if(tag.equals("restdate")){
                            xpp.next();
                            if(xpp.getText() == null){
                                nature.setIntro_restdate("");
                            }else{
                                nature.setIntro_restdate(xpp.getText());
                            }
                        }else if(tag.equals("usetime")){
                            xpp.next();
                            if(xpp.getText() == null){
                                nature.setIntro_usetime("");
                            }else{
                                nature.setIntro_usetime(xpp.getText());
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기
                        if(tag.equals("item")) {
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

    private void parse_xml_Nature_basic(){
        StringBuffer buffer=new StringBuffer();
        try {
            URL url= new URL(url_basic_info); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    /*case XmlPullParser.START_DOCUMENT:
                        buffer.append("Strat :");
                        break;*/

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기

                        if(tag.equals("addr1")){
                            xpp.next();
                            nature.setBasic_addr1(xpp.getText());
                        }else if(tag.equals("addr2")){
                            xpp.next();
                            String temp = nature.getBasic_addr1();
                            nature.setBasic_addr1(temp+" "+xpp.getText());
                        }else if(tag.equals("contentid")){
                            xpp.next();
                            nature.setBasic_contentID(xpp.getText());
                        }else if(tag.equals("contenttypeid")){
                            xpp.next();
                            nature.setBasic_contentTypeID(xpp.getText());
                        }else if(tag.equals("directions")){
                            xpp.next();
                            nature.setBasic_directory(xpp.getText());
                        }else if(tag.equals("firstimage")){
                            xpp.next();
                            nature.setBasic_firstImage(xpp.getText());
                        }else if(tag.equals("homepage")){
                            xpp.next();
                            nature.setBasic_homepage(xpp.getText());
                        }else if(tag.equals("mapx")){
                            xpp.next();
                            nature.setBasic_mapX(Double.parseDouble(xpp.getText()));
                        }else if(tag.equals("mapy")){
                            xpp.next();
                            nature.setBasic_mapY(Double.parseDouble(xpp.getText()));
                        }else if(tag.equals("modifiedtime")){
                            xpp.next();
                            nature.setBasic_modifyDate(xpp.getText());
                        }else if(tag.equals("overview")){
                            xpp.next();
                            if(xpp.getText()==null){
                                nature.setBasic_overView("");
                            }else{
                                nature.setBasic_overView(xpp.getText());
                            }
                        }else if(tag.equals("tel")){
                            xpp.next();
                            nature.setBasic_tel(xpp.getText());
                        }else if(tag.equals("title")){
                            xpp.next();
                            nature.setBasic_title(xpp.getText());
                        }else if(tag.equals("areacode")){
                            xpp.next();
                            nature.setBasic_areaCode(xpp.getText());
                        }else if(tag.equals("sigungucode")){
                            xpp.next();
                            nature.setBasic_sigungu(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기
                        if(tag.equals("item")) {
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
