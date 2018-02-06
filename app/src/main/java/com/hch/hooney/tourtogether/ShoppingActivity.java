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
import com.hch.hooney.tourtogether.DAO.TourAPI.Leisure;
import com.hch.hooney.tourtogether.DAO.TourAPI.RepeatInfo;
import com.hch.hooney.tourtogether.DAO.TourAPI.Shopping;
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

public class ShoppingActivity extends AppCompatActivity {
    private final static String TAG = "ShoppingActivity";
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

    private LinearLayout sp_infoCenter_layout;
    private TextView sp_infoCenter;
    private LinearLayout sp_openTime_layout;
    private TextView sp_openTime;
    private LinearLayout sp_rest_layout;
    private TextView sp_rest;
    private LinearLayout sp_saleItem_layout;
    private TextView sp_saleItem;
    private LinearLayout sp_parking_layout;
    private TextView sp_parking;
    private LinearLayout sp_restRoom_layout;
    private TextView sp_restRoom;
    private LinearLayout sp_result_add_layout;
    private TextView sp_result_add;

    //variable
    private GoogleMap googleMap;

    private Shopping shopping;
    private String url_basic_info;
    private String url_intro_info;
    private String url_repeate_info;
    private String url_images_info;
    private String field;

    private boolean isBookmarking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        if(getIntent()==null){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent), Toast.LENGTH_LONG).show();
            finish();
        }else{
            TourApiItem item = (TourApiItem) getIntent().getSerializableExtra("basic");
            shopping = new Shopping(item);
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
        back = (ImageButton) findViewById(R.id.sp_result_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Field = (TextView) findViewById(R.id.sp_result_field);
        bookmaking = (ImageButton)findViewById(R.id.sp_result_bookmarking);
        modifyDate = (TextView) findViewById(R.id.sp_result_modify);
        title = (TextView) findViewById(R.id.sp_result_title);
        directionlayout=(LinearLayout)findViewById(R.id.sp_result_directions_layout);
        direction = (TextView) findViewById(R.id.sp_result_directions);
        telLayout = (LinearLayout) findViewById(R.id.sp_result_tel_layout);
        tel = (TextView) findViewById(R.id.sp_result_tel);
        homepageLayout = (LinearLayout) findViewById(R.id.sp_result_homepage_layout);
        homepage = (TextView) findViewById(R.id.sp_result_homepage);
        addr1 = (TextView) findViewById(R.id.sp_result_addr);
        overview = (TextView) findViewById(R.id.sp_result_overview);
        mainImage = (ImageView) findViewById(R.id.sp_result_mainImage);

        sp_infoCenter_layout = (LinearLayout)findViewById(R.id.sp_result_infoCenter_layout);
        sp_infoCenter = (TextView) findViewById(R.id.sp_result_infoCenter);
        sp_openTime_layout = (LinearLayout)findViewById(R.id.sp_result_openTime_layout);
        sp_openTime = (TextView) findViewById(R.id.sp_result_openTime);
        sp_rest_layout = (LinearLayout)findViewById(R.id.sp_result_rest_layout);
        sp_rest = (TextView) findViewById(R.id.sp_result_rest);
        sp_saleItem_layout = (LinearLayout)findViewById(R.id.sp_result_saleItem_layout);
        sp_saleItem = (TextView) findViewById(R.id.sp_result_saleItem);
        sp_restRoom_layout = (LinearLayout)findViewById(R.id.sp_result_restRoom_layout);
        sp_restRoom = (TextView) findViewById(R.id.sp_result_restRoom);
        sp_parking_layout = (LinearLayout)findViewById(R.id.sp_result_parking_layout);
        sp_parking = (TextView) findViewById(R.id.sp_result_parking);

        sp_result_add_layout = (LinearLayout)findViewById(R.id.sp_result_add_layout);
        sp_result_add = (TextView) findViewById(R.id.sp_result_add);

        smallLayout = (LinearLayout)findViewById(R.id.sp_result_smallImageView_layout);
        smallList = (RecyclerView) findViewById(R.id.sp_result_smallImageView);
        smallList.setHasFixedSize(true);
        smallList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.sp_result_map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.sp_result_map, supportMapFragment).commit();
        }
    }

    private void setUI(){
        Field.setText(field);

        isBookmarking = DAO.chkAddBookmarking(shopping.getContentID());

        if(isBookmarking){
            bookmaking.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_do));
        }
        bookmaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBookmarking){
                    TourApiItem item = new TourApiItem();
                    item.setAddr1(shopping.getAddr1());
                    item.setAddr2(shopping.getAddr2());
                    item.setAreaCode(shopping.getAreaCode());
                    item.setCat1(shopping.getCat1());
                    item.setCat2(shopping.getCat2());
                    item.setCat3(shopping.getCat3());
                    item.setContentID(shopping.getContentID());
                    item.setContentTypeID(shopping.getContentTypeID());
                    item.setFirstImage(shopping.getFirstImage());
                    item.setMapx(shopping.getMapx());
                    item.setMapy(shopping.getMapy());
                    item.setModifyDateTIme(shopping.getModifyDateTIme());
                    item.setReadCount(shopping.getReadCount());
                    item.setSigunguCode(shopping.getSigunguCode());
                    item.setTitle(shopping.getTitle());
                    item.setTel(shopping.getTel());
                    item.setDirections(shopping.getDirections());
                    item.setBasic_overView(shopping.getBasic_overView());

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

        if(!shopping.getModifyDateTIme().equals("")){
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyyMMddHHmmss").parse(shopping.getModifyDateTIme());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            modifyDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
        }

        if(!shopping.getFirstImage().equals("")){
            Picasso.with(getApplicationContext()).load(shopping.getFirstImage()).into(mainImage);
        }else{
            mainImage.setVisibility(View.GONE);
        }

        if(!shopping.getTitle().equals("")){
            title.setText(shopping.getTitle());
        }

        if(!shopping.getAddr1().equals("")){
            addr1.setText(shopping.getAddr1() + " " + shopping.getAddr2());
        }

        if(!shopping.getTel().equals("")){
            tel.setText(shopping.getTel());
            Linkify.addLinks(tel, Linkify.PHONE_NUMBERS);
        }else{
            telLayout.setVisibility(View.GONE);
        }

        if(!shopping.getBasic_homepage().equals("")){
            homepage.setText(Html.fromHtml(shopping.getBasic_homepage().replaceAll("<br>", " ")));
            Linkify.addLinks(homepage, Linkify.WEB_URLS);
        }else{
            homepageLayout.setVisibility(View.GONE);
        }

        if(!shopping.getDirections().equals("")){
            direction.setText(Html.fromHtml(shopping.getDirections()));
        }else{
            directionlayout.setVisibility(View.GONE);
        }

        if(!shopping.getBasic_overView().equals("")){
            overview.setText(Html.fromHtml(shopping.getBasic_overView()));
        }

        if(!shopping.getIntro_infoCenter().equals("")){
            sp_infoCenter.setText(Html.fromHtml(shopping.getIntro_infoCenter()));
            Linkify.addLinks(sp_infoCenter, Linkify.ALL);
        }else{
            sp_infoCenter_layout.setVisibility(View.GONE);
        }

        if(!shopping.getIntro_openTime().equals("")){
            sp_openTime.setText(Html.fromHtml(shopping.getIntro_openTime()));
        }else{
            sp_openTime_layout.setVisibility(View.GONE);
        }

        if(!shopping.getIntro_rest().equals("")){
            sp_rest.setText(Html.fromHtml(shopping.getIntro_rest()));
        }else{
            sp_rest_layout.setVisibility(View.GONE);
        }

        if(!shopping.getIntro_saleItem().equals("")){
            sp_saleItem.setText(Html.fromHtml(shopping.getIntro_saleItem()));
        }else{
            sp_saleItem_layout.setVisibility(View.GONE);
        }

        if(!shopping.getIntro_parking().equals("")){
            sp_parking.setText(Html.fromHtml(shopping.getIntro_parking()));
        }else{
            sp_parking_layout.setVisibility(View.GONE);
        }
        if(!shopping.getIntro_restRoom().equals("")){
            sp_restRoom.setText(Html.fromHtml(shopping.getIntro_restRoom()));
        }else{
            sp_restRoom_layout.setVisibility(View.GONE);
        }

        if(shopping.getRepeateList() == null || (shopping.getRepeateList().size() < 1)){
            sp_result_add_layout.setVisibility(View.GONE);
        }else{
            StringBuffer buffer = new StringBuffer();

            for (RepeatInfo repeatInfo:shopping.getRepeateList()) {
                buffer.append(repeatInfo.getInfoName()+" :<br>");
                buffer.append(repeatInfo.getInfoText()+"<br><br>");
            }

            sp_result_add.setText(Html.fromHtml(buffer.toString()));
        }

        if(shopping.getSmallImageList()==null){
            smallLayout.setVisibility(View.GONE);
        }else{
            smallList.setAdapter(new ResultSingleImageAdapter(getApplicationContext(), shopping.getSmallImageList()));
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
                Log.d(TAG, shopping.getMapx()+" / " + shopping.getMapy());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(shopping.getMapy(), shopping.getMapx()), 16));
                bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ROSE;
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(shopping.getMapy(), shopping.getMapx()))
                        .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                        .title(shopping.getTitle())
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
                "&contentTypeId="+shopping.getContentTypeID()+ "&contentId="+shopping.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
        url_intro_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailIntro?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+shopping.getContentTypeID()+ "&contentId="+shopping.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
        url_repeate_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailInfo?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+shopping.getContentTypeID()+"&contentId="+shopping.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y";
        url_images_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailImage?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+shopping.getContentTypeID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&contentId="+shopping.getContentID()+"&imageYN=Y";
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
            shopping.setSmallImageList(tempList);
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
            shopping.setRepeateList(tempList);
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

                        if(tag.equals("infocentershopping")){
                            xpp.next();
                            if(xpp.getText() == null){
                                shopping.setIntro_infoCenter("");
                            }else{
                                shopping.setIntro_infoCenter(xpp.getText());
                            }
                        }else if(tag.equals("parkingshopping")){
                            xpp.next();
                            if(xpp.getText() == null){
                                shopping.setIntro_parking("");
                            }else{
                                shopping.setIntro_parking(xpp.getText());
                            }
                        }else if(tag.equals("opentime")){
                            xpp.next();
                            if(xpp.getText() == null){
                                shopping.setIntro_openTime("");
                            }else{
                                shopping.setIntro_openTime(xpp.getText());
                            }
                        }else if(tag.equals("restroom")){
                            xpp.next();
                            if(xpp.getText() == null){
                                shopping.setIntro_restRoom("");
                            }else{
                                shopping.setIntro_restRoom(xpp.getText());
                            }
                        }else if(tag.equals("restdateshopping")){
                            xpp.next();
                            if(xpp.getText() == null){
                                shopping.setIntro_rest("");
                            }else{
                                shopping.setIntro_rest(xpp.getText());
                            }
                        }else if(tag.equals("saleitem")){
                            xpp.next();
                            if(xpp.getText() == null){
                                shopping.setIntro_saleItem("");
                            }else{
                                shopping.setIntro_saleItem(xpp.getText());
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
                            shopping.setBasic_homepage(xpp.getText());
                        }else if(tag.equals("overview")){
                            xpp.next();
                            if(xpp.getText()==null){
                                shopping.setBasic_overView("");
                            }else{
                                shopping.setBasic_overView(xpp.getText());
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
