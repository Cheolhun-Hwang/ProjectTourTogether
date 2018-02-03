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
import com.hch.hooney.tourtogether.DAO.TourAPI.Leisure;
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

public class LeisureActivity extends AppCompatActivity {
    private final static String TAG = "LeisureActivity";
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

    private LinearLayout ls_infoCenter_layout;
    private TextView ls_infoCenter;
    private LinearLayout ls_openPeriod_layout;
    private TextView ls_openPeriod;
    private LinearLayout ls_useTime_layout;
    private TextView ls_useTime;
    private LinearLayout ls_rest_layout;
    private TextView ls_rest;
    private LinearLayout ls_fee_layout;
    private TextView ls_fee;
    private LinearLayout ls_parkingfee_layout;
    private TextView ls_parkingfee;
    private LinearLayout ls_parking_layout;
    private TextView ls_parking;
    private LinearLayout ls_reservation_layout;
    private TextView ls_reservation;
    private LinearLayout ls_result_add_layout;
    private TextView ls_result_add;

    //variable
    private GoogleMap googleMap;

    private Leisure leisure;
    private String url_basic_info;
    private String url_intro_info;
    private String url_repeate_info;
    private String url_images_info;
    private String field;

    private boolean isBookmarking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leisure);

        if(getIntent()==null){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent), Toast.LENGTH_LONG).show();
            finish();
        }else{
            TourApiItem item = (TourApiItem) getIntent().getSerializableExtra("basic");
            leisure = new Leisure(item);
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
                    parse_xml_basic();
                    parse_xml_intro();
                    parse_xml_repeat();
                    parse_xml_smallImages();

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
        back = (ImageButton) findViewById(R.id.ls_result_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Field = (TextView) findViewById(R.id.ls_result_field);
        bookmaking = (ImageButton)findViewById(R.id.ls_result_bookmarking);
        modifyDate = (TextView) findViewById(R.id.ls_result_modify);
        title = (TextView) findViewById(R.id.ls_result_title);
        directionlayout=(LinearLayout)findViewById(R.id.ls_result_directions_layout);
        direction = (TextView) findViewById(R.id.ls_result_directions);
        telLayout = (LinearLayout) findViewById(R.id.ls_result_tel_layout);
        tel = (TextView) findViewById(R.id.ls_result_tel);
        homepageLayout = (LinearLayout) findViewById(R.id.ls_result_homepage_layout);
        homepage = (TextView) findViewById(R.id.ls_result_homepage);
        addr1 = (TextView) findViewById(R.id.ls_result_addr);
        overview = (TextView) findViewById(R.id.ls_result_overview);
        mainImage = (ImageView) findViewById(R.id.ls_result_mainImage);

        ls_infoCenter_layout = (LinearLayout)findViewById(R.id.ls_result_contactus_layout);
        ls_infoCenter = (TextView) findViewById(R.id.ls_result_contactus);
        ls_openPeriod_layout = (LinearLayout)findViewById(R.id.ls_result_openPeriod_layout);
        ls_openPeriod = (TextView) findViewById(R.id.ls_result_openPeriod);
        ls_useTime_layout = (LinearLayout)findViewById(R.id.ls_result_useTime_layout);
        ls_useTime = (TextView) findViewById(R.id.ls_result_useTime);
        ls_rest_layout = (LinearLayout)findViewById(R.id.ls_result_rest_layout);
        ls_rest = (TextView) findViewById(R.id.ls_result_rest);
        ls_fee_layout = (LinearLayout)findViewById(R.id.ls_result_fee_layout);
        ls_fee = (TextView) findViewById(R.id.ls_result_fee);
        ls_parking_layout = (LinearLayout)findViewById(R.id.ls_result_parking_layout);
        ls_parking = (TextView) findViewById(R.id.ls_result_parking);
        ls_parkingfee_layout = (LinearLayout)findViewById(R.id.ls_result_parkingFee_layout);
        ls_parkingfee = (TextView) findViewById(R.id.ls_result_parkingFee);
        ls_reservation_layout = (LinearLayout)findViewById(R.id.ls_result_reservation_layout);
        ls_reservation = (TextView) findViewById(R.id.ls_result_reservation);

        ls_result_add_layout = (LinearLayout)findViewById(R.id.ls_result_add_layout);
        ls_result_add = (TextView) findViewById(R.id.ls_result_add);

        smallLayout = (LinearLayout)findViewById(R.id.ls_result_smallImageView_layout);
        smallList = (RecyclerView) findViewById(R.id.ls_result_smallImageView);
        smallList.setHasFixedSize(true);
        smallList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.ls_result_map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.ls_result_map, supportMapFragment).commit();
        }
    }

    private void setUI(){
        Field.setText(field);

        isBookmarking = DAO.chkAddBookmarking(leisure.getContentID());

        if(isBookmarking){
            bookmaking.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_do));
        }
        bookmaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBookmarking){
                    TourApiItem item = new TourApiItem();
                    item.setAddr1(leisure.getAddr1());
                    item.setAddr2(leisure.getAddr2());
                    item.setAreaCode(leisure.getAreaCode());
                    item.setCat1(leisure.getCat1());
                    item.setCat2(leisure.getCat2());
                    item.setCat3(leisure.getCat3());
                    item.setContentID(leisure.getContentID());
                    item.setContentTypeID(leisure.getContentTypeID());
                    item.setFirstImage(leisure.getFirstImage());
                    item.setMapx(leisure.getMapx());
                    item.setMapy(leisure.getMapy());
                    item.setModifyDateTIme(leisure.getModifyDateTIme());
                    item.setReadCount(leisure.getReadCount());
                    item.setSigunguCode(leisure.getSigunguCode());
                    item.setTitle(leisure.getTitle());
                    item.setTel(leisure.getTel());
                    item.setDirections(leisure.getDirections());
                    item.setBasic_overView(leisure.getBasic_overView());

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

        if(!leisure.getModifyDateTIme().equals("")){
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyyMMddHHmmss").parse(leisure.getModifyDateTIme());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            modifyDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
        }

        if(!leisure.getFirstImage().equals("")){
            Picasso.with(getApplicationContext()).load(leisure.getFirstImage()).into(mainImage);
        }else{
            mainImage.setVisibility(View.GONE);
        }

        if(!leisure.getTitle().equals("")){
            title.setText(leisure.getTitle());
        }

        if(!leisure.getAddr1().equals("")){
            addr1.setText(leisure.getAddr1() + " " + leisure.getAddr2());
        }

        if(!leisure.getTel().equals("")){
            tel.setText(leisure.getTel());
            Linkify.addLinks(tel, Linkify.PHONE_NUMBERS);
        }else{
            telLayout.setVisibility(View.GONE);
        }

        if(!leisure.getBasic_homepage().equals("")){
            homepage.setText(Html.fromHtml(leisure.getBasic_homepage().replaceAll("<br>", " ")));
            Linkify.addLinks(homepage, Linkify.WEB_URLS);
        }else{
            homepageLayout.setVisibility(View.GONE);
        }

        if(!leisure.getDirections().equals("")){
            direction.setText(Html.fromHtml(leisure.getDirections()));
        }else{
            directionlayout.setVisibility(View.GONE);
        }

        if(!leisure.getBasic_overView().equals("")){
            overview.setText(Html.fromHtml(leisure.getBasic_overView()));
        }

        if(!leisure.getIntro_infoCenter().equals("")){
            ls_infoCenter.setText(Html.fromHtml(leisure.getIntro_infoCenter()));
            Linkify.addLinks(ls_infoCenter, Linkify.ALL);
        }else{
            ls_infoCenter_layout.setVisibility(View.GONE);
        }

        if(!leisure.getIntro_openPeriod().equals("")){
            ls_openPeriod.setText(Html.fromHtml(leisure.getIntro_openPeriod()));
        }else{
            ls_openPeriod_layout.setVisibility(View.GONE);
        }

        if(!leisure.getIntro_useTime().equals("")){
            ls_useTime.setText(Html.fromHtml(leisure.getIntro_useTime()));
        }else{
            ls_useTime_layout.setVisibility(View.GONE);
        }

        if(!leisure.getIntro_rest().equals("")){
            ls_rest.setText(Html.fromHtml(leisure.getIntro_rest()));
        }else{
            ls_rest_layout.setVisibility(View.GONE);
        }

        if(!leisure.getIntro_fee().equals("")){
            ls_fee.setText(Html.fromHtml(leisure.getIntro_fee()));
        }else{
            ls_fee_layout.setVisibility(View.GONE);
        }

        if(!leisure.getIntro_parking().equals("")){
            ls_parking.setText(Html.fromHtml(leisure.getIntro_parking()));
        }else{
            ls_parking_layout.setVisibility(View.GONE);
        }
        if(!leisure.getIntro_parkingFee().equals("")){
            ls_parkingfee.setText(Html.fromHtml(leisure.getIntro_parkingFee()));
        }else{
            ls_parkingfee_layout.setVisibility(View.GONE);
        }

        if(!leisure.getIntro_reservation().equals("")){
            ls_reservation.setText(Html.fromHtml(leisure.getIntro_reservation()));
            Linkify.addLinks(ls_reservation, Linkify.ALL);
        }else{
            ls_reservation_layout.setVisibility(View.GONE);
        }

        if(leisure.getRepeateList() == null || (leisure.getRepeateList().size() < 1)){
            ls_result_add_layout.setVisibility(View.GONE);
        }else{
            StringBuffer buffer = new StringBuffer();

            for (RepeatInfo repeatInfo:leisure.getRepeateList()) {
                buffer.append(repeatInfo.getInfoName()+" :\n");
                buffer.append("\t\t\t"+repeatInfo.getInfoText()+"\n\n");
            }

            ls_result_add.setText(Html.fromHtml(buffer.toString()));
        }

        if(leisure.getSmallImageList()==null){
            smallLayout.setVisibility(View.GONE);
        }else{
            smallList.setAdapter(new ResultSingleImageAdapter(getApplicationContext(), leisure.getSmallImageList()));
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
                Log.d(TAG, leisure.getMapx()+" / " + leisure.getMapy());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(leisure.getMapy(), leisure.getMapx()), 16));
                bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ROSE;
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(leisure.getMapy(), leisure.getMapx()))
                        .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                        .title(leisure.getTitle())
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
                "&contentTypeId="+leisure.getContentTypeID()+ "&contentId="+leisure.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
        url_intro_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailIntro?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+leisure.getContentTypeID()+ "&contentId="+leisure.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
        url_repeate_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailInfo?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+leisure.getContentTypeID()+"&contentId="+leisure.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y";
        url_images_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailImage?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+leisure.getContentTypeID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&contentId="+leisure.getContentID()+"&imageYN=Y";
    }

    private void parse_xml_smallImages(){
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
            leisure.setSmallImageList(tempList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }//getXmlData method...

    private void parse_xml_repeat(){
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
            leisure.setRepeateList(tempList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }//getXmlData method...

    private void parse_xml_intro(){
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

                        if(tag.equals("infocenterleports")){
                            xpp.next();
                            if(xpp.getText() == null){
                                leisure.setIntro_infoCenter("");
                            }else{
                                leisure.setIntro_infoCenter(xpp.getText());
                            }
                        }else if(tag.equals("parkingleports")){
                            xpp.next();
                            if(xpp.getText() == null){
                                leisure.setIntro_parking("");
                            }else{
                                leisure.setIntro_parking(xpp.getText());
                            }
                        }else if(tag.equals("parkingfeeleports")){
                            xpp.next();
                            if(xpp.getText() == null){
                                leisure.setIntro_parkingFee("");
                            }else{
                                leisure.setIntro_parkingFee(xpp.getText());
                            }
                        }else if(tag.equals("openperiod")){
                            xpp.next();
                            if(xpp.getText() == null){
                                leisure.setIntro_openPeriod("");
                            }else{
                                leisure.setIntro_openPeriod(xpp.getText());
                            }
                        }else if(tag.equals("usefeeleports")){
                            xpp.next();
                            if(xpp.getText() == null){
                                leisure.setIntro_fee("");
                            }else{
                                leisure.setIntro_fee(xpp.getText());
                            }
                        }else if(tag.equals("usetimeleports")){
                            xpp.next();
                            if(xpp.getText() == null){
                                leisure.setIntro_useTime("");
                            }else{
                                leisure.setIntro_useTime(xpp.getText());
                            }
                        }else if(tag.equals("restdateleports")){
                            xpp.next();
                            if(xpp.getText() == null){
                                leisure.setIntro_rest("");
                            }else{
                                leisure.setIntro_rest(xpp.getText());
                            }
                        }
                        else if(tag.equals("reservation")){
                            xpp.next();
                            if(xpp.getText() == null){
                                leisure.setIntro_reservation("");
                            }else{
                                leisure.setIntro_reservation(xpp.getText());
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

    private void parse_xml_basic(){
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
                            leisure.setBasic_homepage(xpp.getText());
                        }else if(tag.equals("overview")){
                            xpp.next();
                            if(xpp.getText()==null){
                                leisure.setBasic_overView("");
                            }else{
                                leisure.setBasic_overView(xpp.getText());
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
