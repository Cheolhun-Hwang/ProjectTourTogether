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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.TourAPI.Facility;
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

public class FacilityActivity extends AppCompatActivity {
    private final static String TAG = "FacilityActivity";
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

    private LinearLayout fc_open_layout;
    private TextView fc_open;
    private LinearLayout fc_rest_layout;
    private TextView fc_rest;
    private LinearLayout fc_fee_layout;
    private TextView fc_fee;
    private LinearLayout fc_takeTime_layout;
    private TextView fc_takeTime;
    private LinearLayout fc_parkingfee_layout;
    private TextView fc_parkingfee;
    private LinearLayout fc_parking_layout;
    private TextView fc_parking;
    private LinearLayout fc_contactus_layout;
    private TextView fc_contactus;
    private LinearLayout fc_result_add_layout;
    private TextView fc_result_add;

    //variable
    private GoogleMap googleMap;

    private Facility facility;
    private String url_basic_info;
    private String url_intro_info;
    private String url_repeate_info;
    private String url_images_info;
    private String field;

    private boolean isBookmarking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility);

        if(getIntent()==null){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent), Toast.LENGTH_LONG).show();
            finish();
        }else{
            TourApiItem item = (TourApiItem) getIntent().getSerializableExtra("basic");
            facility = new Facility(item);
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
        asyncDialog = new ProgressDialog(this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));

        //layout
        back = (ImageButton) findViewById(R.id.fc_result_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Field = (TextView) findViewById(R.id.fc_result_field);
        bookmaking = (ImageButton)findViewById(R.id.fc_result_bookmarking);
        modifyDate = (TextView) findViewById(R.id.fc_result_modify);
        title = (TextView) findViewById(R.id.fc_result_title);
        directionlayout=(LinearLayout)findViewById(R.id.fc_result_directions_layout);
        direction = (TextView) findViewById(R.id.fc_result_directions);
        telLayout = (LinearLayout) findViewById(R.id.fc_result_tel_layout);
        tel = (TextView) findViewById(R.id.fc_result_tel);
        homepageLayout = (LinearLayout) findViewById(R.id.fc_result_homepage_layout);
        homepage = (TextView) findViewById(R.id.fc_result_homepage);
        addr1 = (TextView) findViewById(R.id.fc_result_addr);
        overview = (TextView) findViewById(R.id.fc_result_overview);
        mainImage = (ImageView) findViewById(R.id.fc_result_mainImage);

        fc_contactus_layout = (LinearLayout)findViewById(R.id.fc_result_contactus_layout);
        fc_contactus = (TextView) findViewById(R.id.fc_result_contactus);
        fc_fee_layout = (LinearLayout)findViewById(R.id.fc_result_usefee_layout);
        fc_fee = (TextView) findViewById(R.id.fc_result_usefee);
        fc_open_layout = (LinearLayout)findViewById(R.id.fc_result_usetimeculture_layout);
        fc_open = (TextView) findViewById(R.id.fc_result_usetimeculture);
        fc_parking_layout = (LinearLayout)findViewById(R.id.fc_result_parking_layout);
        fc_parking = (TextView) findViewById(R.id.fc_result_parking);
        fc_parkingfee_layout = (LinearLayout)findViewById(R.id.fc_result_parkingFee_layout);
        fc_parkingfee = (TextView) findViewById(R.id.fc_result_parkingFee);
        fc_rest_layout = (LinearLayout)findViewById(R.id.fc_result_restdateculture_layout);
        fc_rest = (TextView) findViewById(R.id.fc_result_restdateculture);
        fc_takeTime_layout = (LinearLayout)findViewById(R.id.fc_result_spendtime_layout);
        fc_takeTime = (TextView) findViewById(R.id.fc_result_spendtime);

        fc_result_add_layout = (LinearLayout)findViewById(R.id.fc_result_add_layout);
        fc_result_add = (TextView) findViewById(R.id.fc_result_add);

        smallLayout = (LinearLayout)findViewById(R.id.fc_result_smallImageView_layout);
        smallList = (RecyclerView) findViewById(R.id.fc_result_smallImageView);
        smallList.setHasFixedSize(true);
        smallList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fc_result_map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fc_result_map, supportMapFragment).commit();
        }
    }

    private void setUI(){
        Field.setText(field);

        isBookmarking = DAO.chkAddBookmarking(facility.getContentID());

        if(isBookmarking){
            bookmaking.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_do));
        }
        bookmaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBookmarking){
                    TourApiItem item = new TourApiItem();
                    item.setAddr1(facility.getAddr1());
                    item.setAddr2(facility.getAddr2());
                    item.setAreaCode(facility.getAreaCode());
                    item.setCat1(facility.getCat1());
                    item.setCat2(facility.getCat2());
                    item.setCat3(facility.getCat3());
                    item.setContentID(facility.getContentID());
                    item.setContentTypeID(facility.getContentTypeID());
                    item.setFirstImage(facility.getFirstImage());
                    item.setMapx(facility.getMapx());
                    item.setMapy(facility.getMapy());
                    item.setModifyDateTIme(facility.getModifyDateTIme());
                    item.setReadCount(facility.getReadCount());
                    item.setSigunguCode(facility.getSigunguCode());
                    item.setTitle(facility.getTitle());
                    item.setTel(facility.getTel());
                    item.setDirections(facility.getDirections());
                    item.setBasic_overView(facility.getBasic_overView());

                    DAO.handler.insert_spot(item);
                    DAO.load_bookmarkSpot();

                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.notify_add_bookmarking), Toast.LENGTH_SHORT).show();
                    bookmaking.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_do));
                    isBookmarking = true;
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.notify_already_add_bookmarking), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(!facility.getModifyDateTIme().equals("")){
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyyMMddHHmmss").parse(facility.getModifyDateTIme());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            modifyDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
        }

        if(!facility.getFirstImage().equals("")){
            Picasso.with(getApplicationContext()).load(facility.getFirstImage()).into(mainImage);
        }else{
            mainImage.setVisibility(View.GONE);
        }

        if(!facility.getTitle().equals("")){
            title.setText(facility.getTitle());
        }

        if(!facility.getAddr1().equals("")){
            addr1.setText(facility.getAddr1() + " " + facility.getAddr2());
        }

        if(!facility.getTel().equals("")){
            tel.setText(facility.getTel());
            Linkify.addLinks(tel, Linkify.PHONE_NUMBERS);
        }else{
            telLayout.setVisibility(View.GONE);
        }

        if(!facility.getBasic_homepage().equals("")){
            homepage.setText(Html.fromHtml(facility.getBasic_homepage().replaceAll("<br>", " ")));
            Linkify.addLinks(homepage, Linkify.WEB_URLS);
        }else{
            homepageLayout.setVisibility(View.GONE);
        }

        if(!facility.getDirections().equals("")){
            direction.setText(Html.fromHtml(facility.getDirections()));
        }else{
            directionlayout.setVisibility(View.GONE);
        }

        if(!facility.getBasic_overView().equals("")){
            overview.setText(Html.fromHtml(facility.getBasic_overView()));
        }

        if(!facility.getIntro_infocenterculture().equals("")){
            fc_contactus.setText(Html.fromHtml(facility.getIntro_infocenterculture()));
            Linkify.addLinks(fc_contactus, Linkify.ALL);
        }else{
            fc_contactus_layout.setVisibility(View.GONE);
        }

        if(!facility.getIntro_parking().equals("")){
            fc_parking.setText(Html.fromHtml(facility.getIntro_parking()));
        }else{
            fc_parking_layout.setVisibility(View.GONE);
        }

        if(!facility.getIntro_parkingfee().equals("")){
            fc_parkingfee.setText(Html.fromHtml(facility.getIntro_parkingfee()));
        }else{
            fc_parkingfee_layout.setVisibility(View.GONE);
        }

        if(!facility.getIntro_restdateculture().equals("")){
            fc_rest.setText(Html.fromHtml(facility.getIntro_restdateculture()));
        }else{
            fc_rest_layout.setVisibility(View.GONE);
        }

        if(!facility.getIntro_spendtime().equals("")){
            fc_takeTime.setText(Html.fromHtml(facility.getIntro_spendtime()));
        }else{
            fc_takeTime_layout.setVisibility(View.GONE);
        }

        if(!facility.getIntro_usefee().equals("")){
            fc_fee.setText(Html.fromHtml(facility.getIntro_usefee()));
        }else{
            fc_fee_layout.setVisibility(View.GONE);
        }

        if(!facility.getIntro_usetimeculture().equals("")){
            fc_open.setText(Html.fromHtml(facility.getIntro_usetimeculture()));
        }else{
            fc_open_layout.setVisibility(View.GONE);
        }

        if(facility.getRepeateList() == null || (facility.getRepeateList().size() < 1)){
            fc_result_add_layout.setVisibility(View.GONE);
        }else{
            StringBuffer buffer = new StringBuffer();

            for (RepeatInfo repeatInfo:facility.getRepeateList()) {
                buffer.append(repeatInfo.getInfoName()+" :<br>");
                buffer.append(repeatInfo.getInfoText()+"<br><br>");
            }

            fc_result_add.setText(Html.fromHtml(buffer.toString()));
        }

        if(facility.getSmallImageList()==null){
            smallLayout.setVisibility(View.GONE);
        }else{
            smallList.setAdapter(new ResultSingleImageAdapter(getApplicationContext(), facility.getSmallImageList()));
        }
    }

    private void setMap(){
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;
                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                googleMap.getUiSettings().setZoomGesturesEnabled(false);
                googleMap.getUiSettings().setZoomControlsEnabled(true);

                float bitmapDescriptorFactory = 0;
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(guideItem.getGpsy(), guideItem.getGpsx()), 14));
                Log.d(TAG, facility.getMapx()+" / " + facility.getMapy());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(facility.getMapy(), facility.getMapx()), 16));
                bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ROSE;
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(facility.getMapy(), facility.getMapx()))
                        .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                        .title(facility.getTitle())
                        .zIndex((float) 0)
                );

//                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
//                        == PackageManager.PERMISSION_GRANTED) {
//                    googleMap.setMyLocationEnabled(true);
//                } else {
//                    Toast.makeText(getApplicationContext(), "GPS 권한을 확인해주세요.", Toast.LENGTH_SHORT).show();
//                }

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
                "&contentTypeId="+facility.getContentTypeID()+ "&contentId="+facility.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
        url_intro_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailIntro?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+facility.getContentTypeID()+ "&contentId="+facility.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
        url_repeate_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailInfo?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+facility.getContentTypeID()+"&contentId="+facility.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y";
        url_images_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailImage?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+facility.getContentTypeID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&contentId="+facility.getContentID()+"&imageYN=Y";
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
            facility.setSmallImageList(tempList);
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
            facility.setRepeateList(tempList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }//getXmlData method...

    private void parse_xml_Nature_intro(){
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

                        if(tag.equals("infocenterculture")){
                            xpp.next();
                            if(xpp.getText() == null){
                                facility.setIntro_infocenterculture("");
                            }else{
                                facility.setIntro_infocenterculture(xpp.getText());
                            }
                        }else if(tag.equals("parkingculture")){
                            xpp.next();
                            if(xpp.getText() == null){
                                facility.setIntro_parking("");
                            }else{
                                facility.setIntro_parking(xpp.getText());
                            }
                        }else if(tag.equals("parkingfee")){
                            xpp.next();
                            if(xpp.getText() == null){
                                facility.setIntro_parkingfee("");
                            }else{
                                facility.setIntro_parkingfee(xpp.getText());
                            }
                        }else if(tag.equals("spendtime")){
                            xpp.next();
                            if(xpp.getText() == null){
                                facility.setIntro_spendtime("");
                            }else{
                                facility.setIntro_spendtime(xpp.getText());
                            }
                        }else if(tag.equals("usefee")){
                            xpp.next();
                            if(xpp.getText() == null){
                                facility.setIntro_usefee("");
                            }else{
                                facility.setIntro_usefee(xpp.getText());
                            }
                        }else if(tag.equals("usetimeculture")){
                            xpp.next();
                            if(xpp.getText() == null){
                                facility.setIntro_usetimeculture("");
                            }else{
                                facility.setIntro_usetimeculture(xpp.getText());
                            }
                        }else if(tag.equals("restdateculture")){
                            xpp.next();
                            if(xpp.getText() == null){
                                facility.setIntro_restdateculture("");
                            }else{
                                facility.setIntro_restdateculture(xpp.getText());
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

                        if(tag.equals("homepage")){
                            xpp.next();
                            facility.setBasic_homepage(xpp.getText());
                        }else if(tag.equals("overview")){
                            xpp.next();
                            if(xpp.getText()==null){
                                facility.setBasic_overView("");
                            }else{
                                facility.setBasic_overView(xpp.getText());
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

}
