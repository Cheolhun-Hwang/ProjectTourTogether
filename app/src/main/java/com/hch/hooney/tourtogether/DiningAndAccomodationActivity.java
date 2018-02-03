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
import com.hch.hooney.tourtogether.DAO.TourApiItem;
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

    private boolean isBookmarking;

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
    private boolean isAcco;
    private String field;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_and_accomodation);

        if(getIntent()==null){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent), Toast.LENGTH_LONG).show();
            finish();
        }else{
            TourApiItem item = (TourApiItem) getIntent().getSerializableExtra("basic");
            field = getIntent().getStringExtra("field").toString();

            if(field.equals("먹거리") || field.equals("Food")){
                isAcco = false;
                dining =  new Dining(item);

                init();
                setURLS();
                asyncDialog.show();
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

            }else if(field.equals("숙박") || field.contains("Accom")){
                accomodation = new Accomodation(item);
                isAcco = true;
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
            }
        }

    }

    private void init(){
        asyncDialog = new ProgressDialog(this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));

        back = (ImageButton) findViewById(R.id.da_result_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.da_result_map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.da_result_map, supportMapFragment).commit();
        }

        if(isAcco){
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
        if(isAcco){

            Field.setText(field);

            isBookmarking = DAO.chkAddBookmarking(accomodation.getContentID());

            if(isBookmarking){
                bookmaking.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_do));
            }
            bookmaking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isBookmarking){
                        TourApiItem item = new TourApiItem();
                        item.setAddr1(accomodation.getAddr1());
                        item.setAddr2(accomodation.getAddr2());
                        item.setAreaCode(accomodation.getAreaCode());
                        item.setCat1(accomodation.getCat1());
                        item.setCat2(accomodation.getCat2());
                        item.setCat3(accomodation.getCat3());
                        item.setContentID(accomodation.getContentID());
                        item.setContentTypeID(accomodation.getContentTypeID());
                        item.setFirstImage(accomodation.getFirstImage());
                        item.setMapx(accomodation.getMapx());
                        item.setMapy(accomodation.getMapy());
                        item.setModifyDateTIme(accomodation.getModifyDateTIme());
                        item.setReadCount(accomodation.getReadCount());
                        item.setSigunguCode(accomodation.getSigunguCode());
                        item.setTitle(accomodation.getTitle());
                        item.setTel(accomodation.getTel());
                        item.setDirections(accomodation.getDirections());
                        item.setBasic_overView(accomodation.getBasic_overView());

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

            if(!accomodation.getModifyDateTIme().equals("")){
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyyMMddHHmmss").parse(accomodation.getModifyDateTIme());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                modifyDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
            }

            if(!accomodation.getFirstImage().equals("")){
                Picasso.with(getApplicationContext()).load(accomodation.getFirstImage()).into(mainImage);
            }else{
                mainImage.setVisibility(View.GONE);
            }

            if(!accomodation.getTitle().equals("")){
                title.setText(accomodation.getTitle());
            }

            if(!accomodation.getAddr1().equals("")){
                addr1.setText(accomodation.getAddr1()+ " " + accomodation.getAddr2());
            }

            if(!accomodation.getTel().equals("")){
                tel.setText(accomodation.getTel());
                Linkify.addLinks(tel, Linkify.PHONE_NUMBERS);
            }else{
                telLayout.setVisibility(View.GONE);
            }

            if(!accomodation.getBasic_homepage().equals("")){
                homepage.setText(Html.fromHtml(accomodation.getBasic_homepage().replaceAll("<br>", " ")));
                Linkify.addLinks(homepage, Linkify.WEB_URLS);
            }else{
                homepageLayout.setVisibility(View.GONE);
            }

            if(!accomodation.getDirections().equals("")){
                direction.setText(Html.fromHtml(accomodation.getDirections()));
            }else{
                directionlayout.setVisibility(View.GONE);
            }

            if(!accomodation.getBasic_overView().equals("")){
                overview.setText(Html.fromHtml(accomodation.getBasic_overView()));
            }else{
                overview.setText("");
            }

            if(!accomodation.getIntro_checkInTime().equals("")){
                a_check_in.setText(Html.fromHtml(accomodation.getIntro_checkInTime()));
            }else{
                a_check_in_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_checkOutTime().equals("")){
                a_check_out.setText(Html.fromHtml(accomodation.getIntro_checkOutTime()));
            }else{
                a_check_out_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_roomtype().equals("")){
                a_roomtype.setText(Html.fromHtml(accomodation.getIntro_roomtype()));
            }else{
                a_roomtype_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_chkCooking().equals("")){
                a_cooking.setText(Html.fromHtml(accomodation.getIntro_chkCooking()));
            }else{
                a_cooking_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_parking().equals("")){
                a_parking.setText(Html.fromHtml(accomodation.getIntro_parking()));
            }else{
                a_parking_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_foodPlace().equals("")){
                a_foodPlace.setText(Html.fromHtml(accomodation.getIntro_foodPlace()));
            }else{
                a_foodPlace_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_subFacility().equals("")){
                a_subFacility.setText(Html.fromHtml(accomodation.getIntro_subFacility()));
            }else{
                a_subFacility_layout.setVisibility(View.GONE);
            }

            if(!accomodation.getIntro_reservation().equals("")){
                a_reservation.setText(Html.fromHtml(accomodation.getIntro_reservation()));
                Linkify.addLinks(a_reservation, Linkify.ALL);
            }else{
                a_reservation_layout.setVisibility(View.GONE);
            }
            if(!accomodation.getIntro_infoCenter().equals("")){
                a_contactus.setText(Html.fromHtml(accomodation.getIntro_infoCenter()));
                Linkify.addLinks(a_contactus, Linkify.ALL);
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

            isBookmarking = DAO.chkAddBookmarking(dining.getContentID());

            if(isBookmarking){
                bookmaking.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_do));
            }
            bookmaking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isBookmarking){
                        TourApiItem item = new TourApiItem();
                        item.setAddr1(dining.getAddr1());
                        item.setAddr2(dining.getAddr2());
                        item.setAreaCode(dining.getAreaCode());
                        item.setCat1(dining.getCat1());
                        item.setCat2(dining.getCat1());
                        item.setCat3(dining.getCat1());
                        item.setContentID(dining.getContentID());
                        item.setContentTypeID(dining.getContentTypeID());
                        item.setFirstImage(dining.getFirstImage());
                        item.setMapx(dining.getMapx());
                        item.setMapy(dining.getMapy());
                        item.setModifyDateTIme(dining.getModifyDateTIme());
                        item.setReadCount(dining.getReadCount());
                        item.setSigunguCode(dining.getSigunguCode());
                        item.setTitle(dining.getTitle());
                        item.setTel(dining.getTel());
                        item.setDirections(dining.getDirections());
                        item.setBasic_overView(dining.getBasic_overView());

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

            if(!dining.getModifyDateTIme().equals("")){
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyyMMddHHmmss").parse(dining.getModifyDateTIme());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                modifyDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
            }

            if(!dining.getFirstImage().equals("")){
                Picasso.with(getApplicationContext()).load(dining.getFirstImage()).into(mainImage);
            }else{
                mainImage.setVisibility(View.GONE);
            }

            if(!dining.getTitle().equals("")){
                title.setText(dining.getTitle());
            }

            if(!dining.getAddr1().equals("")){
                addr1.setText(dining.getAddr1()+" "+dining.getAddr2());
            }

            if(!dining.getTel().equals("")){
                tel.setText(dining.getTel());
                Linkify.addLinks(tel, Linkify.PHONE_NUMBERS);
            }else{
                telLayout.setVisibility(View.GONE);
            }

            if(!dining.getBasic_homepage().equals("")){
                homepage.setText(Html.fromHtml(dining.getBasic_homepage().replaceAll("<br>", " ")) );
                Linkify.addLinks(homepage, Linkify.WEB_URLS);
            }else{
                homepageLayout.setVisibility(View.GONE);
            }

            if(!dining.getDirections().equals("")){
                direction.setText(Html.fromHtml(dining.getDirections()));
            }else{
                directionlayout.setVisibility(View.GONE);
            }

            if(!dining.getBasic_overView().equals("")){
                overview.setText(Html.fromHtml(dining.getBasic_overView()));
            }else{
                overview.setText("");
            }

            if(!dining.getIntro_parking().equals("")){
                d_parking.setText(Html.fromHtml(dining.getIntro_parking()));
            }else{
                d_parking_layout.setVisibility(View.GONE);
            }

            if(!dining.getIntro_openHour().equals("")){
                d_openHour.setText(Html.fromHtml(dining.getIntro_openHour()));
            }else{
                d_openHour_layout.setVisibility(View.GONE);
            }

            if(!dining.getIntro_dayOff().equals("")){
                d_dayoff.setText(Html.fromHtml(dining.getIntro_dayOff()));
            }else{
                d_dayoff_layout.setVisibility(View.GONE);
            }

            if(!dining.getIntro_menu().equals("")){
                d_menu.setText(Html.fromHtml(dining.getIntro_menu()));
            }else{
                d_menu_layout.setVisibility(View.GONE);
            }

            if(!dining.getIntro_smoking().equals("")){
                d_smoke.setText(Html.fromHtml(dining.getIntro_parking()));
            }else{
                d_smoke_layout.setVisibility(View.GONE);
            }

            if(!dining.getIntro_reservation().equals("")){
                d_reservation.setText(Html.fromHtml(dining.getIntro_reservation()));
                Linkify.addLinks(d_reservation, Linkify.ALL);
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
            @SuppressLint("LongLogTag")
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;
                googleMap.getUiSettings().setScrollGesturesEnabled(false);

                float bitmapDescriptorFactory = 0;
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(guideItem.getGpsy(), guideItem.getGpsx()), 14));
                if(isAcco){
                    Log.d(TAG, accomodation.getMapx()+" / " + accomodation.getMapy());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(accomodation.getMapy(), accomodation.getMapx()), 16));
                    bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ROSE;
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(accomodation.getMapy(), accomodation.getMapx()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title(accomodation.getTitle())
                            .zIndex((float) 0)
                    );
                }else{
                    Log.d(TAG, dining.getMapx()+" / " + dining.getMapy());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dining.getMapy(), dining.getMapx()), 16));
                    bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ROSE;
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(dining.getMapy(), dining.getMapx()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title(dining.getTitle())
                            .zIndex((float) 0)
                    );
                }


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

        if(isAcco){
            url_basic_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                    "/detailCommon?ServiceKey="+ DAO.ServiceKey+
                    "&contentTypeId="+accomodation.getContentTypeID()+ "&contentId="+accomodation.getContentID()+
                    "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
            url_intro_info = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                    "/detailIntro?ServiceKey="+DAO.ServiceKey+
                    "&contentTypeId="+accomodation.getContentTypeID()+ "&contentId="+accomodation.getContentID()+
                    "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
            url_images_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                            "/detailImage?ServiceKey="+DAO.ServiceKey+
                            "&contentTypeId="+accomodation.getContentTypeID()+
                            "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&contentId="+accomodation.getContentID()+"&imageYN=Y";
        }else{
            url_basic_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                    "/detailCommon?ServiceKey="+DAO.ServiceKey+
                    "&contentTypeId="+dining.getContentTypeID()+"&contentId="+dining.getContentID()+
                    "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
            url_intro_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                    "/detailIntro?ServiceKey="+DAO.ServiceKey+
                    "&contentTypeId="+dining.getContentTypeID()+"&contentId="+dining.getContentID()+
                    "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
            url_images_info="http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                    "/detailImage?ServiceKey="+DAO.ServiceKey+
                    "&contentTypeId="+dining.getContentTypeID()+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&contentId="+dining.getContentID()+"&imageYN=Y";
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
                            if(xpp.getText() == null){
                                accomodation.setIntro_checkInTime("");
                            }else{
                                accomodation.setIntro_checkInTime(xpp.getText());
                            }
                        }else if(tag.equals("checkouttime")){
                            xpp.next();
                            if(xpp.getText() == null){
                                accomodation.setIntro_checkOutTime("");
                            }else{
                                accomodation.setIntro_checkOutTime(xpp.getText());
                            }
                        }else if(tag.equals("chkcooking")){
                            xpp.next();
                            if(xpp.getText() == null){
                                accomodation.setIntro_chkCooking("");
                            }else{
                                accomodation.setIntro_chkCooking(xpp.getText());
                            }
                        }else if(tag.equals("foodplace")){
                            xpp.next();
                            if(xpp.getText()==null){
                                accomodation.setIntro_foodPlace("");
                            }else{
                                accomodation.setIntro_foodPlace(xpp.getText());
                            }
                        }else if(tag.equals("infocenterlodging")){
                            xpp.next();
                            if(xpp.getText()==null){
                                accomodation.setIntro_infoCenter("");
                            }else{
                                accomodation.setIntro_infoCenter(xpp.getText());
                            }
                        }else if(tag.equals("parkinglodging")){
                            xpp.next();
                            if(xpp.getText()==null){
                                accomodation.setIntro_parking("");
                            }else{
                                accomodation.setIntro_parking(xpp.getText());
                            }
                        }else if(tag.equals("reservationlodging")){
                            xpp.next();
                            if(xpp.getText() == null){
                                accomodation.setIntro_reservation("");
                            }else{
                                accomodation.setIntro_reservation(xpp.getText());
                            }
                        }else if(tag.equals("roomtype")){
                            xpp.next();
                            if(xpp.getText() == null){
                                accomodation.setIntro_roomtype("");
                            }else{
                                accomodation.setIntro_roomtype(xpp.getText());
                            }
                        }else if(tag.equals("subfacility")){
                            xpp.next();
                            if(xpp.getText()==null){
                                accomodation.setIntro_subFacility("");
                            }else{
                                accomodation.setIntro_subFacility(xpp.getText());
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

                        if(tag.equals("homepage")){
                            xpp.next();
                            accomodation.setBasic_homepage(xpp.getText());
                        }else if(tag.equals("overview")){
                            xpp.next();
                            if(xpp.getText()==null){
                                accomodation.setBasic_overView("");
                            }else{
                                accomodation.setBasic_overView(xpp.getText());
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
            dining.setSmallImageList(tempList);
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
                            if(xpp.getText()==null){
                                dining.setIntro_openHour("");
                            }else{
                                dining.setIntro_openHour(xpp.getText());
                            }
                        }else if(tag.equals("parkingfood")){
                            xpp.next();
                            if(xpp.getText()==null){
                                dining.setIntro_parking("");
                            }else{
                                dining.setIntro_parking(xpp.getText());
                            }
                        }else if(tag.equals("reservationfood")){
                            xpp.next();
                            if(xpp.getText()==null){
                                dining.setIntro_reservation("");
                            }else{
                                dining.setIntro_reservation(xpp.getText());
                            }
                        }else if(tag.equals("restdatefood")){
                            xpp.next();
                            if(xpp.getText()==null){
                                dining.setIntro_dayOff("");
                            }else{
                                dining.setIntro_dayOff(xpp.getText());
                            }
                        }else if(tag.equals("smoking")){
                            xpp.next();
                            if(xpp.getText()==null){
                                dining.setIntro_smoking("");
                            }else{
                                dining.setIntro_smoking(xpp.getText());
                            }
                        }else if(tag.equals("firstmenu")){
                            xpp.next();
                            String tempString = dining.getIntro_menu();
                            if(xpp.getText() == null){
                                dining.setIntro_menu(tempString);
                            }else{
                                dining.setIntro_menu("Main Menu : " + xpp.getText()+"\n"+tempString);
                            }
                        }else if(tag.equals("treatmenu")){
                            xpp.next();
                            String tempString = dining.getIntro_menu();
                            if(xpp.getText() == null){
                                dining.setIntro_menu(tempString);
                            }else{
                                dining.setIntro_menu(tempString+"\n"+xpp.getText());
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

                        if(tag.equals("homepage")){
                            xpp.next();
                            if(xpp.getText()==null){
                                dining.setBasic_homepage("");
                            }else{
                                dining.setBasic_homepage(xpp.getText());
                            }
                        }else if(tag.equals("overview")){
                            xpp.next();
                            if(xpp.getText()==null){
                                dining.setBasic_overView("");
                            }else{
                                dining.setBasic_overView(xpp.getText());
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
