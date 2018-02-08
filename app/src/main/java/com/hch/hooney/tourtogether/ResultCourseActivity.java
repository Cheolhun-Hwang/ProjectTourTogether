package com.hch.hooney.tourtogether;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.TourAPI.Course;
import com.hch.hooney.tourtogether.DAO.TourAPI.Nature;
import com.hch.hooney.tourtogether.DAO.TourAPI.RepeatInfo;
import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.hch.hooney.tourtogether.Recycler.Result.ResultCourseRouteAdapter;
import com.hch.hooney.tourtogether.SettingForEvent.CustomLinearLayoutManager;
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
    private RecyclerView.LayoutManager layoutManager;

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
            course = new Course(item);
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

                    for(int i = 0; i < course.getSpotlist().size();i++){
                        parse_xml_sub_basic(i);
                    }

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

        spotsListView = (RecyclerView) findViewById(R.id.result_course_spots_list);
        spotsListView.setHasFixedSize(true);
        layoutManager = new CustomLinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        spotsListView.setLayoutManager(layoutManager);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.result_course_map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.result_course_map, supportMapFragment).commit();
        }
    }


    private void setUI(){
        Field.setText(field);

        isBookmarking = DAO.chkAddBookmarking(course.getContentID());

        if(isBookmarking){
            bookmaking.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_do));
        }
        bookmaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBookmarking){
                    TourApiItem item = new TourApiItem();
                    item.setAddr1(course.getAddr1());
                    item.setAddr2(course.getAddr2());
                    item.setAreaCode(course.getAreaCode());
                    item.setCat1(course.getCat1());
                    item.setCat2(course.getCat2());
                    item.setCat3(course.getCat3());
                    item.setContentID(course.getContentID());
                    item.setContentTypeID(course.getContentTypeID());
                    item.setFirstImage(course.getFirstImage());
                    item.setMapx(course.getMapx());
                    item.setMapy(course.getMapy());
                    item.setModifyDateTIme(course.getModifyDateTIme());
                    item.setReadCount(course.getReadCount());
                    item.setSigunguCode(course.getSigunguCode());
                    item.setTitle(course.getTitle());
                    item.setTel(course.getTel());
                    item.setDirections(course.getDirections());
                    item.setBasic_overView(course.getBasic_overView());

                    DAO.handler.insert_course(item);
                    DAO.load_bookmarkCourse();
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.notify_add_bookmarking), Toast.LENGTH_SHORT).show();
                    bookmaking.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_do));
                    isBookmarking = true;
                }else{
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.notify_already_add_bookmarking), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(!course.getModifyDateTIme().equals("")){
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyyMMddHHmmss").parse(course.getModifyDateTIme());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            modifyDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
        }

        if(!course.getFirstImage().equals("")){
            Picasso.with(getApplicationContext()).load(course.getFirstImage()).into(mainImage);
        }else{
            mainImage.setVisibility(View.GONE);
        }

        if(!course.getTitle().equals("")){
            title.setText(course.getTitle());
        }

        if(!course.getBasic_overView().equals("")){
            overview.setText(Html.fromHtml(course.getBasic_overView()));
        }

        if(!course.getTotalDistance().equals("")){
            total_distanse.setText(Html.fromHtml(course.getTotalDistance()));
        }else{
            total_distanse.setVisibility(View.GONE);
        }

        if(!course.getTime().equals("")){
            time.setText(Html.fromHtml(course.getTime()));
        }else{
            time.setVisibility(View.GONE);
        }

        if(course.getSpotlist()==null || course.getSpotlist().size() < 1){
            spotsListView.setVisibility(View.GONE);
        }else{
            spotsListView.setAdapter(new ResultCourseRouteAdapter(getApplicationContext(), course.getSpotlist()));
        }
    }

    private void setMap(){
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;
                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                googleMap.getUiSettings().setZoomGesturesEnabled(false);
                googleMap.getUiSettings().setZoomControlsEnabled(true);

                float bitmapDescriptorFactory = 0;
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(guideItem.getGpsy(), guideItem.getGpsx()), 14));

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(course.getMapy(), course.getMapx()), 12));
                bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ROSE;
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(course.getMapy(), course.getMapx()))
                        .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                        .title(course.getTitle())
                        .zIndex((float) 0)
                );

                for(int j = 0;j<course.getSpotlist().size();j++){
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(course.getSpotlist().get(j).getMapy(), course.getSpotlist().get(j).getMapx()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title(course.getSpotlist().get(j).getTitle())
                            .zIndex((float) (j+1))
                    );
                }

                PolylineOptions polygonOptions = new PolylineOptions();
                ArrayList<LatLng> points = new ArrayList<LatLng>();


                points.add(new LatLng(course.getMapy(), course.getMapx()));

                for(int i = 0; i<course.getSpotlist().size();i++){
                    LatLng position = new LatLng(course.getSpotlist().get(i).getMapy(), course.getSpotlist().get(i).getMapx());
                    points.add(position);
                }
                polygonOptions.addAll(points);
                polygonOptions.width(10);
                polygonOptions.color(Color.RED);

                if(polygonOptions != null){
                    googleMap.addPolyline(polygonOptions);
                }

                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setCompassEnabled(true);
                } else {
                    Toast.makeText(getApplicationContext(), "GPS 권한을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Toast.makeText(getApplicationContext(), "맵페이지", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


    private void setURLS(){
        String service="";
        if(DAO.Language.equals("ko")){
            service="KorService";
        }else{
            service="KorService";
            //service="EngService";
        }

        url_basic_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailCommon?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+course.getContentTypeID()+
                "&contentId="+course.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
        url_intro_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailIntro?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+course.getContentTypeID()+
                "&contentId="+course.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
        url_course_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                "/detailInfo?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+course.getContentTypeID()+
                "&contentId="+course.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y";
    }

    private void parse_xml_sub_basic(int position){

        TourApiItem target = course.getSpotlist().get(position);

        String url_basic_info_sub = "http://api.visitkorea.or.kr/openapi/service/rest/KorService"+
                "/detailCommon?ServiceKey="+DAO.ServiceKey+
                "&contentTypeId="+
                "&contentId="+target.getContentID()+
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";

        try {
            URL url= new URL(url_basic_info_sub); //문자열로 된 요청 url을 URL 객체로 생성.
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

                        if(tag.equals("overview")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setBasic_overView("");
                            }else{
                                target.setBasic_overView(xpp.getText());
                            }
                        }else if(tag.equals("addr1")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setAddr1("");
                            }else{
                                target.setAddr1(xpp.getText());
                            }
                        }else if(tag.equals("addr2")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setAddr2("");
                            }else{
                                target.setAddr2(xpp.getText());
                            }
                        }else if(tag.equals("cat1")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setCat1("");
                            }else{
                                target.setCat1(xpp.getText());
                            }
                        }else if(tag.equals("cat2")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setCat2("");
                            }else{
                                target.setCat2(xpp.getText());
                            }
                        }else if(tag.equals("cat3")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setCat3("");
                            }else{
                                target.setCat3(xpp.getText());
                            }
                        }else if(tag.equals("contenttypeid")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setContentTypeID("");
                            }else{
                                target.setContentTypeID(xpp.getText());
                            }
                        }else if(tag.equals("firstimage")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setFirstImage("");
                            }else{
                                target.setFirstImage(xpp.getText());
                            }
                        }else if(tag.equals("mapx")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setMapx(0.0);
                            }else{
                                target.setMapx(Double.parseDouble(xpp.getText()));
                            }
                        }else if(tag.equals("mapy")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setMapy(0.0);
                            }else{
                                target.setMapy(Double.parseDouble(xpp.getText()));
                            }
                        }else if(tag.equals("modifiedtime")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setModifyDateTIme("");
                            }else{
                                target.setModifyDateTIme(xpp.getText());
                            }
                        }else if(tag.equals("areacode")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setAreaCode("");
                            }else{
                                target.setAreaCode(xpp.getText());
                            }
                        }else if(tag.equals("sigungucode")){
                            xpp.next();
                            if(xpp.getText()==null){
                                target.setSigunguCode("");
                            }else{
                                target.setSigunguCode(xpp.getText());
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기
                        if(tag.equals("item")) {
                            course.getSpotlist().set(position, target);
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

    private void parse_xml_spots_repeat(){
        ArrayList<TourApiItem> tempList = new ArrayList<TourApiItem>();
        try {
            URL url= new URL(url_course_info); //문자열로 된 요청 url을 URL 객체로 생성.
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
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기
                        if(tag.equals("item")){
                            item = new TourApiItem();
                        }else if(tag.equals("subcontentid")){
                            xpp.next();
                            item.setContentID(xpp.getText());
                        }else if(tag.equals("subdetailimg")){
                            xpp.next();
                            item.setFirstImage(xpp.getText());
                        }else if(tag.equals("subname")){
                            xpp.next();
                            item.setTitle(xpp.getText());
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
            course.setSpotlist(tempList);
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

                        if(tag.equals("distance")){
                            xpp.next();
                            if(xpp.getText() == null){
                                course.setTotalDistance("");
                            }else{
                                course.setTotalDistance(xpp.getText());
                            }
                        }else if(tag.equals("taketime")){
                            xpp.next();
                            if(xpp.getText() == null){
                                course.setTime("");
                            }else{
                                course.setTime(xpp.getText());
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

                        if(tag.equals("overview")){
                            xpp.next();
                            if(xpp.getText()==null){
                                course.setBasic_overView("");
                            }else{
                                course.setBasic_overView(xpp.getText());
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
