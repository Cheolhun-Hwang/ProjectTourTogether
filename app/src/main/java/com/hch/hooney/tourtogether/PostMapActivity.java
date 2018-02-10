package com.hch.hooney.tourtogether;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.ClusterManager;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.mainPostItem;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostMapActivity extends AppCompatActivity {
    private final String TAG = "PostMapActivity";
    private ProgressDialog asyncDialog;

    private Button tab1, tab2, tab3, tab4, tab5, tab6;
    private ImageButton back;
    private EditText addrEditText;
    private ImageButton searchBTN;

    private SupportMapFragment supportMapFragment;
    private GoogleMap googleMap;

    private ArrayList<mainPostItem> list;

    private String field;
    private boolean[] selectTab;
    private boolean isFocused;
    private double focus_mapx;
    private double focus_mapy;
    private DatabaseReference rootRef;
    private DatabaseReference postRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_map);

        if(getIntent() == null){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent),Toast.LENGTH_SHORT).show();
            finish();
        }else{
            field = getIntent().getStringExtra("func");
            if(field.equals("focus")){
                focus_mapx = getIntent().getDoubleExtra("mapx", 0.0);
                focus_mapy = getIntent().getDoubleExtra("mapy", 0.0);
            }else{

            }
            init();
            loadData();
            setEvent();
        }
    }

    private void init(){
        asyncDialog = new ProgressDialog(this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));
        //firebase;
        rootRef = FirebaseDatabase.getInstance().getReference();
        postRef = rootRef.child("post");
        list = new ArrayList<mainPostItem>();
        selectTab = new boolean[6];
        isFocused = false;

        back = (ImageButton) findViewById(R.id.post_map_back_btn);
        tab1 = (Button) findViewById(R.id.post_map_field_tab1);
        tab2 = (Button) findViewById(R.id.post_map_field_tab2);
        tab3 = (Button) findViewById(R.id.post_map_field_tab3);
        tab4 = (Button) findViewById(R.id.post_map_field_tab4);
        tab5 = (Button) findViewById(R.id.post_map_field_tab5);
        tab6 = (Button) findViewById(R.id.post_map_field_tab6);
        selectAll();
        addrEditText = (EditText) findViewById(R.id.post_map_edittext);
        searchBTN = (ImageButton) findViewById(R.id.post_map_search_btn);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.post_map_map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.post_map_map, supportMapFragment).commit();
        }
    }

    private void loadData(){
        asyncDialog.show();
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot items : dataSnapshot.getChildren()){
                    mainPostItem item = items.getValue(mainPostItem.class);
                    item.setContentID(items.getKey());
                    list.add(0, item);
                }

                if(field.equals("none")){
                    if(list.size()<1){
                        focus_mapx = 126.972625;
                        focus_mapy = 37.553233;
                    }else{
                        focus_mapx = list.get(0).getMapx();
                        focus_mapy = list.get(0).getMapy();
                    }
                }

                setMap();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLocation(addrEditText.getText().toString());
                focusMap();
                addrEditText.setText("");
            }
        });
        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab(0);
                setMarkers();
                focusMap();
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab(1);
                setMarkers();
                focusMap();
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab(2);
                setMarkers();
                focusMap();
            }
        });
        tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab(3);
                setMarkers();
                focusMap();
            }
        });
        tab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab(4);
                setMarkers();
                focusMap();
            }
        });
        tab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab(5);
                setMarkers();
                focusMap();
            }
        });
    }

    private void searchLocation(String searchStr){
        List<Address> addressList = null;
        Geocoder gc = null;
        if(DAO.Language.equals("ko")){
            gc = new Geocoder(this, Locale.KOREA);
        }else{
            gc = new Geocoder(this, Locale.ENGLISH);
        }
        try{
            addressList = gc.getFromLocationName(searchStr, 1);
            if(addressList != null){
                for(int i=0;i<addressList.size();i++){
                    Address outAddr = addressList.get(i);
                    focus_mapx = outAddr.getLongitude();
                    focus_mapy = outAddr.getLatitude();
                }
            }else{
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.map_select_check_edittext),
                        Toast.LENGTH_SHORT).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void focusMap(){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(focus_mapy, focus_mapx), 14));
    }

    private void setMap(){
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;
                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                googleMap.getUiSettings().setZoomGesturesEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setCompassEnabled(true);

                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                }

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(focus_mapy, focus_mapx), 14));

                for(int i = 0 ; i<list.size() ; i++){
                    mainPostItem item = list.get(i);
                    float bitmapDescriptorFactory = 0;
                    if(item.getContentTypeID().equals("32")||item.getContentTypeID().equals("80")){
                        bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ORANGE;
                        if(selectTab[5]){
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(item.getMapy(), item.getMapx()))
                                    .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                                    .title(item.getTitle())
                                    .snippet(item.getContentID())
                                    .zIndex((float) i)
                            );
                        }
                    }else if(item.getContentTypeID().equals("12")||item.getContentTypeID().equals("76")){
                        bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_GREEN;
                        if(selectTab[0]){
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(item.getMapy(), item.getMapx()))
                                    .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                                    .title(item.getTitle())
                                    .snippet(item.getContentID())
                                    .zIndex((float) i)
                            );
                        }
                    }else if(item.getContentTypeID().equals("14")||item.getContentTypeID().equals("78")){
                        bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_BLUE;
                        if(selectTab[1]){
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(item.getMapy(), item.getMapx()))
                                    .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                                    .title(item.getTitle())
                                    .snippet(item.getContentID())
                                    .zIndex((float) i)
                            );
                        }
                    }else if(item.getContentTypeID().equals("28")||item.getContentTypeID().equals("75")){
                        bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_RED;
                        if(selectTab[2]){
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(item.getMapy(), item.getMapx()))
                                    .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                                    .title(item.getTitle())
                                    .snippet(item.getContentID())
                                    .zIndex((float) i)
                            );
                        }
                    }else if(item.getContentTypeID().equals("38")||item.getContentTypeID().equals("79")){
                        bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_CYAN;
                        if(selectTab[3]){
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(item.getMapy(), item.getMapx()))
                                    .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                                    .title(item.getTitle())
                                    .snippet(item.getContentID())
                                    .zIndex((float) i)
                            );
                        }
                    }else if(item.getContentTypeID().equals("39")||item.getContentTypeID().equals("82")){
                        bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_VIOLET;
                        if(selectTab[4]){
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(item.getMapy(), item.getMapx()))
                                    .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                                    .title(item.getTitle())
                                    .snippet(item.getContentID())
                                    .zIndex((float) i)
                            );
                        }
                    }
                }

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        mainPostItem item = list.get((int)marker.getZIndex());

                        Intent intent = new Intent(getApplicationContext(), PostCommentActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("contentid", item.getContentID());
                        intent.putExtra("title", item.getTitle());
                        startActivity(intent);
                    }
                });

                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View view = getLayoutInflater().inflate(R.layout.item_post_map_markers, null);
                        ImageView userProfile = (ImageView) view.findViewById(R.id.item_post_map_userProfileImage);
                        TextView userName = (TextView) view.findViewById(R.id.item_post_map_userNameTextview);
                        TextView Category = (TextView) view.findViewById(R.id.item_post_map_postCategory);
                        TextView writeDate = (TextView) view.findViewById(R.id.item_post_map_postWriteDate);
                        ImageView mainPostImage = (ImageView) view.findViewById(R.id.item_post_map_postImage);
                        TextView maiPostContent = (TextView) view.findViewById(R.id.item_post_map_postContext);

                        mainPostItem item = list.get((int)marker.getZIndex());

                        //User
                        userName.setText(item.getUNAME());
                        Picasso.with(getApplicationContext()).load(item.getUPROFILEIMAGE()).into(userProfile);
                        userProfile.setBackground(new ShapeDrawable(new OvalShape()));
                        userProfile.setClipToOutline(true);

                        //category
                        String feild = "";
                        if(item.getContentTypeID().equals("32")||item.getContentTypeID().equals("80")){
                            feild = getResources().getText(R.string.search_tab6).toString();
                        }else if(item.getContentTypeID().equals("12")||item.getContentTypeID().equals("76")){
                            feild = getResources().getText(R.string.search_tab1).toString();
                        }else if(item.getContentTypeID().equals("14")||item.getContentTypeID().equals("78")){
                            feild = getResources().getText(R.string.search_tab2).toString();
                        }else if(item.getContentTypeID().equals("28")||item.getContentTypeID().equals("75")){
                            feild = getResources().getText(R.string.search_tab3).toString();
                        }else if(item.getContentTypeID().equals("38")||item.getContentTypeID().equals("79")){
                            feild = getResources().getText(R.string.search_tab4).toString();
                        }else if(item.getContentTypeID().equals("39")||item.getContentTypeID().equals("82")){
                            feild = getResources().getText(R.string.search_tab5).toString();
                        }
                        Category.setText("Category : " + feild.replace("\n", " "));

                        //write date
                        writeDate.setText(item.getModifyDateTIme());

                        //Post
                        if(item.getFirstImage().equals("")){
                            mainPostImage.setVisibility(View.GONE);
                        }else{
                            Picasso.with(getApplicationContext()).load(item.getFirstImage()).into(mainPostImage);
                        }
                        maiPostContent.setText(item.getBasic_overView());


                        return view;
                    }
                });
            }
        });
        asyncDialog.dismiss();
    }

    private void setMarkers(){
        googleMap.clear();
        isFocused = false;
        for(int i = 0 ; i<list.size() ; i++) {
            mainPostItem item = list.get(i);
            float bitmapDescriptorFactory = 0;
            if (item.getContentTypeID().equals("32") || item.getContentTypeID().equals("80")) {
                bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ORANGE;
                if (selectTab[5]) {
                    focus_mapy = item.getMapy();
                    focus_mapx = item.getMapx();
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(item.getMapy(), item.getMapx()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title(item.getTitle())
                            .snippet(item.getContentID())
                            .zIndex((float) i)
                    );
                }
            } else if (item.getContentTypeID().equals("12") || item.getContentTypeID().equals("76")) {
                bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_GREEN;
                if (selectTab[0]) {
                    if(!isFocused){
                        focus_mapy = item.getMapy();
                        focus_mapx = item.getMapx();
                        isFocused = true;
                    }
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(item.getMapy(), item.getMapx()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title(item.getTitle())
                            .snippet(item.getContentID())
                            .zIndex((float) i)
                    );
                }
            } else if (item.getContentTypeID().equals("14") || item.getContentTypeID().equals("78")) {
                bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_BLUE;
                if (selectTab[1]) {
                    if(!isFocused){
                        focus_mapy = item.getMapy();
                        focus_mapx = item.getMapx();
                        isFocused = true;
                    }
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(item.getMapy(), item.getMapx()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title(item.getTitle())
                            .snippet(item.getContentID())
                            .zIndex((float) i)
                    );
                }
            } else if (item.getContentTypeID().equals("28") || item.getContentTypeID().equals("75")) {
                bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_RED;
                if (selectTab[2]) {
                    if(!isFocused){
                        focus_mapy = item.getMapy();
                        focus_mapx = item.getMapx();
                        isFocused = true;
                    }
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(item.getMapy(), item.getMapx()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title(item.getTitle())
                            .snippet(item.getContentID())
                            .zIndex((float) i)
                    );
                }
            } else if (item.getContentTypeID().equals("38") || item.getContentTypeID().equals("79")) {
                bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_CYAN;
                if (selectTab[3]) {
                    if(!isFocused){
                        focus_mapy = item.getMapy();
                        focus_mapx = item.getMapx();
                        isFocused = true;
                    }
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(item.getMapy(), item.getMapx()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title(item.getTitle())
                            .snippet(item.getContentID())
                            .zIndex((float) i)
                    );
                }
            } else if (item.getContentTypeID().equals("39") || item.getContentTypeID().equals("82")) {
                bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_VIOLET;
                if (selectTab[4]) {
                    if(!isFocused){
                        focus_mapy = item.getMapy();
                        focus_mapx = item.getMapx();
                        isFocused = true;
                    }
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(item.getMapy(), item.getMapx()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title(item.getTitle())
                            .snippet(item.getContentID())
                            .zIndex((float) i)
                    );
                }
            }
        }
    }

    private void selectAll(){
        for(int i = 0;i<selectTab.length;i++){
            selectTab[i] = true;
        }
        tab1.setTextColor(getResources().getColor(R.color.white));
        tab1.setBackgroundColor(getResources().getColor(R.color.green_500));
        tab2.setTextColor(getResources().getColor(R.color.white));
        tab2.setBackgroundColor(getResources().getColor(R.color.blue_900));
        tab3.setTextColor(getResources().getColor(R.color.white));
        tab3.setBackgroundColor(getResources().getColor(R.color.red_800));
        tab4.setTextColor(getResources().getColor(R.color.white));
        tab4.setBackgroundColor(getResources().getColor(R.color.cyan_500));
        tab5.setTextColor(getResources().getColor(R.color.white));
        tab5.setBackgroundColor(getResources().getColor(R.color.purple_500));
        tab6.setTextColor(getResources().getColor(R.color.white));
        tab6.setBackgroundColor(getResources().getColor(R.color.orange_500));
    }

    private void selectedTab(int num){
        switch (num){
            case 0:
                if(selectTab[num]){
                    tab1.setTextColor(getResources().getColor(R.color.grey_400));
                    tab1.setBackgroundColor(getResources().getColor(R.color.white));
                    selectTab[num]=false;
                }else{
                    tab1.setTextColor(getResources().getColor(R.color.white));
                    tab1.setBackgroundColor(getResources().getColor(R.color.green_500));
                    selectTab[num]=true;
                }
                break;
            case 1:
                if(selectTab[num]){
                    tab2.setTextColor(getResources().getColor(R.color.grey_400));
                    tab2.setBackgroundColor(getResources().getColor(R.color.white));
                    selectTab[num]=false;
                }else{
                    tab2.setTextColor(getResources().getColor(R.color.white));
                    tab2.setBackgroundColor(getResources().getColor(R.color.blue_900));
                    selectTab[num]=true;
                }
                break;
            case 2:
                if(selectTab[num]){
                    tab3.setTextColor(getResources().getColor(R.color.grey_400));
                    tab3.setBackgroundColor(getResources().getColor(R.color.white));
                    selectTab[num]=false;
                }else{
                    tab3.setTextColor(getResources().getColor(R.color.white));
                    tab3.setBackgroundColor(getResources().getColor(R.color.red_800));
                    selectTab[num]=true;
                }
                break;
            case 3:
                if(selectTab[num]){
                    tab4.setTextColor(getResources().getColor(R.color.grey_400));
                    tab4.setBackgroundColor(getResources().getColor(R.color.white));
                    selectTab[num]=false;
                }else{
                    tab4.setTextColor(getResources().getColor(R.color.white));
                    tab4.setBackgroundColor(getResources().getColor(R.color.cyan_500));
                    selectTab[num]=true;
                }
                break;
            case 4:
                if(selectTab[num]){
                    tab5.setTextColor(getResources().getColor(R.color.grey_400));
                    tab5.setBackgroundColor(getResources().getColor(R.color.white));
                    selectTab[num]=false;
                }else{
                    tab5.setTextColor(getResources().getColor(R.color.white));
                    tab5.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    selectTab[num]=true;
                }
                break;
            case 5:
                if(selectTab[num]){
                    tab6.setTextColor(getResources().getColor(R.color.grey_400));
                    tab6.setBackgroundColor(getResources().getColor(R.color.white));
                    selectTab[num]=false;
                }else{
                    tab6.setTextColor(getResources().getColor(R.color.white));
                    tab6.setBackgroundColor(getResources().getColor(R.color.orange_500));
                    selectTab[num]=true;
                }
                break;
            default:
                break;
        }
    }
}
