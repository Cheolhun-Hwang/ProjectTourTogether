package com.hch.hooney.tourtogether;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
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
import com.hch.hooney.tourtogether.Recycler.Result.ResultSingleImageAdapter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DiningAndAccomodationActivity extends AppCompatActivity {
    private final static String TAG = "DiningAndAccomodationActivity";
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

    private MapFragment mapFragment;
    private SupportMapFragment supportMapFragment;


    //d
    private LinearLayout d_layout;
    private LinearLayout d_parking_layout;
    private TextView d_parking;
    private LinearLayout d_openHour_layout;
    private TextView d_openHour;
    private LinearLayout d_dayoff_layout;
    private TextView d_dayoff;
    private LinearLayout d_menu_layout;
    private TextView d_menu;
    private LinearLayout d_smoke_layout;
    private TextView d_smoke;
    private LinearLayout d_reservation_layout;
    private TextView d_reservation;

    //a
    private LinearLayout a_layout;
    private LinearLayout a_check_in_layout;
    private TextView a_check_in;
    private LinearLayout a_check_out_layout;
    private TextView a_check_out;
    private LinearLayout a_roomtype_layout;
    private TextView a_roomtype;
    private LinearLayout a_parking_layout;
    private TextView a_parking;
    private LinearLayout a_cooking_layout;
    private TextView a_cooking;
    private LinearLayout a_foodPlace_layout;
    private TextView a_foodPlace;
    private LinearLayout a_subFacility_layout;
    private TextView a_subFacility;
    private LinearLayout a_reservation_layout;
    private TextView a_reservation;
    private LinearLayout a_contactus_layout;
    private TextView a_contactus;


    //variable
    private GoogleMap googleMap;


    private Accomodation accomodation;
    private Dining dining;
    private String url_basic_info;
    private String url_intro_info;
    private String url_images_info;

    private String ContentID;
    private String ContentTypeID;
    private String field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_and_accomodation);

        if(getIntent()==null){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent), Toast.LENGTH_LONG).show();
            finish();
        }else{
            ContentTypeID = getIntent().getStringExtra("ContentTypeID");
            ContentID = getIntent().getStringExtra("ContentID");
            field = getIntent().getStringExtra("field");

            init();
            setURLS();

            if(ContentTypeID.equals("32")||ContentTypeID.equals("80")){
                //asyncDialog.show();
                new Thread(new Runnable() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void run() {
                        //loadDate
                        Log.d(TAG, url_basic_info);
                        Log.d(TAG, url_intro_info);
                        Log.d(TAG, url_images_info);
                        parse_xml_Accom_basic();
                        parse_xml_Accom_intro();
                        parse_xml_Accom_smallImages();

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
            }else{
                //asyncDialog.show();
                new Thread(new Runnable() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void run() {
                        Log.d(TAG, url_basic_info);
                        Log.d(TAG, url_intro_info);
                        Log.d(TAG, url_images_info);
                        //loadDate
                        parse_xml_Dining_basic();
                        parse_xml_Dining_intro();
                        parse_xml_Dining_smallImages();

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

    }

    private void init(){
        asyncDialog = new ProgressDialog(getApplicationContext());
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));

        back = (ImageButton) findViewById(R.id.da_result_back);
        Field = (TextView) findViewById(R.id.da_result_field);
        bookmaking = (ImageButton)findViewById(R.id.da_result_bookmarking);
        modifyDate = (TextView) findViewById(R.id.da_result_modify);
        title = (TextView) findViewById(R.id.da_result_title);
        directionlayout=(LinearLayout)findViewById(R.id.da_result_directions_layout);
        direction = (TextView) findViewById(R.id.da_result_directions);
        telLayout = (LinearLayout) findViewById(R.id.da_result_tel_layout);
        tel = (TextView) findViewById(R.id.da_result_tel);
        homepageLayout = (LinearLayout) findViewById(R.id.da_result_homepage_layout);
        homepage = (TextView) findViewById(R.id.da_result_homepage);
        addr1 = (TextView) findViewById(R.id.da_result_addr);
        overview = (TextView) findViewById(R.id.da_result_overview);
        mainImage = (ImageView) findViewById(R.id.da_result_mainImage);
        smallLayout = (LinearLayout)findViewById(R.id.da_result_smallImageView_layout);
        smallList = (RecyclerView) findViewById(R.id.da_result_smallImageView);
        smallList.setHasFixedSize(true);
        smallList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.result_map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.result_map, supportMapFragment).commit();
        }

        if(ContentTypeID.equals("32")||ContentTypeID.equals("80")){
            //variable
            accomodation = new Accomodation();
            //layout
            a_layout = (LinearLayout)findViewById(R.id.da_result_a_layout);
            a_layout.setVisibility(View.VISIBLE);
            a_check_in_layout = (LinearLayout) findViewById(R.id.da_result_a_chkIn_layout);
            a_check_in = (TextView) findViewById(R.id.da_result_a_chkIn);
            a_check_out_layout = (LinearLayout) findViewById(R.id.da_result_a_chkOut_layout);
            a_check_out = (TextView) findViewById(R.id.da_result_a_chkOut);
            a_roomtype_layout = (LinearLayout) findViewById(R.id.da_result_a_roomType_layout);
            a_roomtype = (TextView) findViewById(R.id.da_result_a_roomType);
            a_parking_layout = (LinearLayout) findViewById(R.id.da_result_a_parking_layout);
            a_parking = (TextView) findViewById(R.id.da_result_a_parking);
            a_cooking_layout = (LinearLayout) findViewById(R.id.da_result_a_cooking_layout);
            a_cooking = (TextView) findViewById(R.id.da_result_a_cooking);
            a_foodPlace_layout = (LinearLayout) findViewById(R.id.da_result_a_foodPlace_layout);
            a_foodPlace = (TextView) findViewById(R.id.da_result_a_foodPlace);
            a_subFacility_layout = (LinearLayout) findViewById(R.id.da_result_a_subFacility_layout);
            a_subFacility = (TextView) findViewById(R.id.da_result_a_subFacility);
            a_reservation_layout=(LinearLayout)findViewById(R.id.da_result_a_reservation_layout);
            a_reservation = (TextView) findViewById(R.id.da_result_a_reservation);
            a_contactus_layout = (LinearLayout)findViewById(R.id.da_result_a_contactus_layout);
            a_contactus = (TextView) findViewById(R.id.da_result_a_contactus);
        }else{
            //variable
            dining = new Dining();
            //layout
            d_layout = (LinearLayout)findViewById(R.id.da_result_d_layout);
            d_layout.setVisibility(View.VISIBLE);
            d_parking_layout = (LinearLayout) findViewById(R.id.da_result_d_parking_layout);
            d_parking = (TextView) findViewById(R.id.da_result_d_parking);
            d_openHour_layout = (LinearLayout) findViewById(R.id.da_result_d_openHour_layout);
            d_openHour = (TextView) findViewById(R.id.da_result_d_openHour);
            d_dayoff_layout = (LinearLayout) findViewById(R.id.da_result_d_rest_layout);
            d_dayoff = (TextView) findViewById(R.id.da_result_d_rest);
            d_menu_layout = (LinearLayout)findViewById(R.id.da_result_d_menu_layout);
            d_menu = (TextView)findViewById(R.id.da_result_d_menu);
            d_smoke_layout = (LinearLayout) findViewById(R.id.da_result_d_smoke_layout);
            d_smoke = (TextView) findViewById(R.id.da_result_d_smoke);
            d_reservation_layout = (LinearLayout) findViewById(R.id.da_result_d_reservation_layout);
            d_reservation = (TextView)findViewById(R.id.da_result_d_reservation);
        }
    }

    private void setUI(){
        if(ContentTypeID.equals("32")||ContentTypeID.equals("80")){

            Field.setText(field);

            if(!accomodation.getBasic_modifyDate().equals("")){
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyyMMddHHmmss").parse(accomodation.getBasic_modifyDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                modifyDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
            }

            if(!accomodation.getBasic_firstImage().equals("")){
                Picasso.with(getApplicationContext()).load(accomodation.getBasic_firstImage()).into(mainImage);
            }

            if(!accomodation.getBasic_title().equals("")){
                title.setText(accomodation.getBasic_title());
            }

            if(!accomodation.getBasic_addr1().equals("")){
                addr1.setText(accomodation.getBasic_addr1());
            }

            if(!accomodation.getBasic_tel().equals("")){
                tel.setText(accomodation.getBasic_tel());
            }else{
                telLayout.setVisibility(View.GONE);
            }

            if(!accomodation.getBasic_homepage().equals("")){
                homepage.setText(accomodation.getBasic_homepage());
            }else{
                homepageLayout.setVisibility(View.GONE);
            }

            if(!accomodation.getBasic_directory().equals("")){
                direction.setText(accomodation.getBasic_directory());
            }else{
                direction.setVisibility(View.GONE);
            }

            if(!accomodation.getBasic_overView().equals("")){
                overview.setText(accomodation.getBasic_overView());
            }

            if(!accomodation.getIntro_checkInTime().equals("")){
                a_check_in.setText(accomodation.getIntro_checkInTime());
            }else{
                a_check_in_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_checkOutTime().equals("")){
                a_check_out.setText(accomodation.getIntro_checkOutTime());
            }else{
                a_check_out_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_roomtype().equals("")){
                a_roomtype.setText(accomodation.getIntro_roomtype());
            }else{
                a_roomtype_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_chkCooking().equals("")){
                a_cooking.setText(accomodation.getIntro_chkCooking());
            }else{
                a_cooking_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_parking().equals("")){
                a_parking.setText(accomodation.getIntro_parking());
            }else{
                a_parking_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_foodPlace().equals("")){
                a_foodPlace.setText(accomodation.getIntro_foodPlace());
            }else{
                a_foodPlace_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_subFacility().equals("")){
                a_subFacility.setText(accomodation.getIntro_subFacility());
            }else{
                a_subFacility_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_reservation().equals("")){
                a_reservation.setText(accomodation.getIntro_reservation());
            }else{
                a_reservation_layout.setVisibility(View.GONE);
            }
            if(!accomodation.getIntro_infoCenter().equals("")){
                a_contactus.setText(accomodation.getIntro_infoCenter());
            }else{
                a_contactus_layout.setVisibility(View.GONE);
            }

            if(accomodation.getSmallImageList()==null){
                smallLayout.setVisibility(View.GONE);
            }else{
                smallList.setAdapter(new ResultSingleImageAdapter(getApplicationContext(), accomodation.getSmallImageList()));
            }
        }else{
            Field.setText(field);

            if(!dining.getBasic_modifyDate().equals("")){
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyyMMddHHmmss").parse(dining.getBasic_modifyDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                modifyDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
            }

            if(!dining.getBasic_firstImage().equals("")){
                Picasso.with(getApplicationContext()).load(accomodation.getBasic_firstImage()).into(mainImage);
            }

            if(!dining.getBasic_title().equals("")){
                title.setText(dining.getBasic_title());
            }

            if(!dining.getBasic_addr1().equals("")){
                addr1.setText(dining.getBasic_addr1());
            }

            if(!dining.getBasic_tel().equals("")){
                tel.setText(dining.getBasic_tel());
            }else{
                telLayout.setVisibility(View.GONE);
            }

            if(!dining.getBasic_homepage().equals("")){
                homepage.setText(dining.getBasic_homepage());
            }else{
                homepageLayout.setVisibility(View.GONE);
            }

            if(!dining.getBasic_directory().equals("")){
                direction.setText(dining.getBasic_directory());
            }else{
                direction.setVisibility(View.GONE);
            }

            if(!dining.getBasic_overView().equals("")){
                overview.setText(dining.getBasic_overView());
            }

            if(!dining.getIntro_parking().equals("")){
                d_parking.setText(dining.getIntro_parking());
            }else{
                d_parking_layout.setVisibility(View.GONE);
            }

            if(!dining.getIntro_openHour().equals("")){
                d_openHour.setText(dining.getIntro_openHour());
            }else{
                d_openHour_layout.setVisibility(View.GONE);
            }

            if(!dining.getIntro_dayOff().equals("")){
                d_dayoff.setText(dining.getIntro_dayOff());
            }else{
                d_dayoff_layout.setVisibility(View.GONE);
            }

            if(!dining.getIntro_menu().equals("")){
                d_menu.setText(dining.getIntro_menu());
            }else{
                d_menu_layout.setVisibility(View.GONE);
            }

            if(!dining.getIntro_smoking().equals("")){
                d_smoke.setText(dining.getIntro_parking());
            }else{
                d_smoke_layout.setVisibility(View.GONE);
            }

            if(!dining.getIntro_reservation().equals("")){
                d_reservation.setText(dining.getIntro_reservation());
            }else{
                d_reservation_layout.setVisibility(View.GONE);
            }

            if(dining.getSmallImageList()==null){
                smallLayout.setVisibility(View.GONE);
            }else{
                smallList.setAdapter(new ResultSingleImageAdapter(getApplicationContext(), dining.getSmallImageList()));
            }
        }
    }

    private void setMap(){
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;
                googleMap.getUiSettings().setScrollGesturesEnabled(false);

                float bitmapDescriptorFactory = 0;
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(guideItem.getGpsy(), guideItem.getGpsx()), 14));
                if(ContentTypeID.equals("32")||ContentTypeID.equals("80")){
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(accomodation.getBasic_mapY(), accomodation.getBasic_mapX()), 15));
                    bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ROSE;
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(accomodation.getBasic_mapY(), accomodation.getBasic_mapX()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title(accomodation.getBasic_title())
                            .zIndex((float) 0)
                    );
                }else{
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dining.getBasic_mapY(), dining.getBasic_mapX()), 15));
                    bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ROSE;
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(dining.getBasic_mapY(), dining.getBasic_mapX()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title(dining.getBasic_title())
                            .zIndex((float) 0)
                    );
                }


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

        if(ContentTypeID.equals("32")||ContentTypeID.equals("80")){
            url_basic_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                    "/detailCommon?ServiceKey="+ DAO.ServiceKey+
                    "&contentTypeId="+ContentTypeID+ "&contentId="+ContentID+
                    "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
            url_intro_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                    "/detailIntro?ServiceKey="+DAO.ServiceKey+
                    "&contentTypeId="+ContentTypeID+ "&contentId="+ContentID+
                    "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
            url_images_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                            "/detailImage?ServiceKey="+DAO.ServiceKey+
                            "&contentTypeId="+ContentTypeID+
                            "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&contentId="+ContentID+"&imageYN=Y";
        }else{
            url_basic_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                    "/detailCommon?ServiceKey="+DAO.ServiceKey+
                    "&contentTypeId="+ContentTypeID+"&contentId="+ContentID+
                    "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
            url_intro_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                    "/detailIntro?ServiceKey="+DAO.ServiceKey+
                    "&contentTypeId="+ContentTypeID+"&contentId="+ContentID+
                    "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
            url_images_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                    "/detailInfo?ServiceKey="+DAO.ServiceKey+
                    "&contentTypeId="+ContentTypeID+"&contentId="+ContentID+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y";
        }
    }

    private void parse_xml_Accom_smallImages(){
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
            accomodation.setSmallImageList(tempList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }//getXmlData method...

    private void parse_xml_Accom_intro(){
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

                        if(tag.equals("checkintime")){
                            xpp.next();
                            accomodation.setIntro_checkInTime(xpp.getText());
                        }else if(tag.equals("checkouttime")){
                            xpp.next();
                            accomodation.setIntro_checkOutTime(xpp.getText());
                        }else if(tag.equals("chkcooking")){
                            xpp.next();
                            accomodation.setIntro_chkCooking(xpp.getText());
                        }else if(tag.equals("foodplace")){
                            xpp.next();
                            accomodation.setIntro_foodPlace(xpp.getText());
                        }else if(tag.equals("infocenterlodging")){
                            xpp.next();
                            accomodation.setIntro_infoCenter(xpp.getText());
                        }else if(tag.equals("parkinglodging")){
                            xpp.next();
                            accomodation.setIntro_parking(xpp.getText());
                        }else if(tag.equals("reservationlodging")){
                            xpp.next();
                            accomodation.setIntro_reservation(xpp.getText());
                        }else if(tag.equals("roomtype")){
                            xpp.next();
                            accomodation.setIntro_roomtype(xpp.getText());
                        }else if(tag.equals("subfacility")){
                            xpp.next();
                            accomodation.setIntro_subFacility(xpp.getText());
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

    private void parse_xml_Accom_basic(){
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
                            accomodation.setBasic_addr1(xpp.getText());
                        }else if(tag.equals("addr2")){
                            xpp.next();
                            String temp = accomodation.getBasic_addr1();
                            accomodation.setBasic_addr1(temp+" "+xpp.getText());
                        }else if(tag.equals("contentid")){
                            xpp.next();
                            accomodation.setBasic_contentID(xpp.getText());
                        }else if(tag.equals("contenttypeid")){
                            xpp.next();
                            accomodation.setBasic_contentTypeID(xpp.getText());
                        }else if(tag.equals("directions")){
                            xpp.next();
                            accomodation.setBasic_directory(xpp.getText());
                        }else if(tag.equals("firstimage")){
                            xpp.next();
                            accomodation.setBasic_firstImage(xpp.getText());
                        }else if(tag.equals("homepage")){
                            xpp.next();
                            accomodation.setBasic_homepage(xpp.getText());
                        }else if(tag.equals("mapx")){
                            xpp.next();
                            accomodation.setBasic_mapX(Double.parseDouble(xpp.getText()));
                        }else if(tag.equals("mapy")){
                            xpp.next();
                            accomodation.setBasic_mapY(Double.parseDouble(xpp.getText()));
                        }else if(tag.equals("modifiedtime")){
                            xpp.next();
                            accomodation.setBasic_modifyDate(xpp.getText());
                        }else if(tag.equals("overview")){
                            xpp.next();
                            accomodation.setBasic_overView(xpp.getText());
                        }else if(tag.equals("tel")){
                            xpp.next();
                            accomodation.setBasic_tel(xpp.getText());
                        }else if(tag.equals("title")){
                            xpp.next();
                            accomodation.setBasic_title(xpp.getText());
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

    private void parse_xml_Dining_smallImages(){
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
            accomodation.setSmallImageList(tempList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }//getXmlData method...

    private void parse_xml_Dining_intro(){
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

                        if(tag.equals("opentimefood")){
                            xpp.next();
                            dining.setIntro_openHour(xpp.getText());
                        }else if(tag.equals("parkingfood")){
                            xpp.next();
                            dining.setIntro_parking(xpp.getText());
                        }else if(tag.equals("reservationfood")){
                            xpp.next();
                            dining.setIntro_reservation(xpp.getText());
                        }else if(tag.equals("restdatefood")){
                            xpp.next();
                            dining.setIntro_dayOff(xpp.getText());
                        }else if(tag.equals("smoking")){
                            xpp.next();
                            dining.setIntro_smoking(xpp.getText());
                        }else if(tag.equals("treatmenu")){
                            xpp.next();
                            dining.setIntro_menu(xpp.getText());
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

    private void parse_xml_Dining_basic(){
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
                            accomodation.setBasic_addr1(xpp.getText());
                        }else if(tag.equals("addr2")){
                            xpp.next();
                            String temp = accomodation.getBasic_addr1();
                            accomodation.setBasic_addr1(temp+" "+xpp.getText());
                        }else if(tag.equals("contentid")){
                            xpp.next();
                            accomodation.setBasic_contentID(xpp.getText());
                        }else if(tag.equals("contenttypeid")){
                            xpp.next();
                            accomodation.setBasic_contentTypeID(xpp.getText());
                        }else if(tag.equals("directions")){
                            xpp.next();
                            accomodation.setBasic_directory(xpp.getText());
                        }else if(tag.equals("firstimage")){
                            xpp.next();
                            accomodation.setBasic_firstImage(xpp.getText());
                        }else if(tag.equals("homepage")){
                            xpp.next();
                            accomodation.setBasic_homepage(xpp.getText());
                        }else if(tag.equals("mapx")){
                            xpp.next();
                            accomodation.setBasic_mapX(Double.parseDouble(xpp.getText()));
                        }else if(tag.equals("mapy")){
                            xpp.next();
                            accomodation.setBasic_mapY(Double.parseDouble(xpp.getText()));
                        }else if(tag.equals("modifiedtime")){
                            xpp.next();
                            accomodation.setBasic_modifyDate(xpp.getText());
                        }else if(tag.equals("overview")){
                            xpp.next();
                            accomodation.setBasic_overView(xpp.getText());
                        }else if(tag.equals("tel")){
                            xpp.next();
                            accomodation.setBasic_tel(xpp.getText());
                        }else if(tag.equals("title")){
                            xpp.next();
                            accomodation.setBasic_title(xpp.getText());
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
