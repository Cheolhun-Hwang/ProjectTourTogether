package com.hch.hooney.tourtogether;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.Fragments.CourseFragment;
import com.hch.hooney.tourtogether.Fragments.SearchFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SelectMapActivity extends AppCompatActivity {
    private final String TAG="SelectMapActivity";
    private final int SIGNAL_PERMISSON = 3001;

    private EditText edittext_location;
    private ImageButton back;
    private ImageButton search_to_edittext;
    private Button selected;

    //variable
    private GoogleMap googleMap;
    private SupportMapFragment supportMapFragment;
    private Geocoder gc;
    private double searchMapx;
    private double searchMapy;

    private String who;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_map);

        if(getIntent() == null){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_intent),
                    Toast.LENGTH_SHORT).show();
            finish();
        }else{
            who = getIntent().getStringExtra("who");
        }

        checkDangerousPermissions();

        init();
        setEvent();
    }

    private void init(){
        searchMapx = 126.972625;
        searchMapy = 37.553233;

        edittext_location = (EditText) findViewById(R.id.select_map_edittext);
        search_to_edittext = (ImageButton) findViewById(R.id.select_map_search_btn);
        back = (ImageButton) findViewById(R.id.select_map_back);
        selected = (Button) findViewById(R.id.select_map_complete);

        if(DAO.Language.equals("ko")){
            gc = new Geocoder(this, Locale.KOREA);
        }else{
            gc = new Geocoder(this, Locale.ENGLISH);
        }

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.select_map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.select_map, supportMapFragment).commit();
        }
        setMap();
    }

    private void setEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;

                if(who.equals("search")){
                    intent = new Intent(getApplicationContext(), SearchFragment.class);
                }else if(who.equals("course")){
                    intent = new Intent(getApplicationContext(), CourseFragment.class);
                }else if(who.equals("edit")){
                    intent = new Intent(getApplicationContext(), PostEditActivity.class);
                }

                intent.putExtra("mapx", searchMapx);
                intent.putExtra("mapy", searchMapy);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        search_to_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLocation(edittext_location.getText().toString());
                focusMap();
            }
        });
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

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(searchMapy, searchMapx), 16));

                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                }

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        searchMapx = latLng.longitude;
                        searchMapy = latLng.latitude;
                        float bitmapDescriptorFactory = 0;
                        markerOptions.position(latLng)
                                .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                                .zIndex((float) 0);
                        googleMap.clear();
                        googleMap.addMarker(markerOptions);
                        focusMap();

                    }
                });

            }
        });
    }

    private void focusMap(){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(searchMapy, searchMapx), 16));
    }

    private void searchLocation(String searchStr){
        List<Address> addressList = null;
        StringBuffer buffer = new StringBuffer();
        try{
            addressList = gc.getFromLocationName(searchStr, 1);
            if(addressList != null){
                for(int i=0;i<addressList.size();i++){
                    Address outAddr = addressList.get(i);
                    searchMapx = outAddr.getLongitude();
                    searchMapy = outAddr.getLatitude();
                }
            }else{
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.map_select_check_edittext),
                        Toast.LENGTH_SHORT).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /* 사용자 권한 확인 메서드
       - import android.Manifest; 를 시킬 것
     */
    private void checkDangerousPermissions() {
        String[] permissions = {//import android.Manifest;
                android.Manifest.permission.ACCESS_FINE_LOCATION,   //GPS 이용권한
                android.Manifest.permission.ACCESS_COARSE_LOCATION, //네트워크/Wifi 이용 권한
                android.Manifest.permission.INTERNET                 //인터넷 사용 권한
        };

        //권한을 가지고 있는지 체크
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "권한있음");
        } else {
            Log.d(TAG, "권한없음");

            ActivityCompat.requestPermissions(this, permissions, SIGNAL_PERMISSON);
        }
    }//end of checkDangerousPermissions

    // 사용자의 권한 확인 후 사용자의 권한에 대한 응답 결과를 확인하는 콜백 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        Log.d(TAG, "권한 : " + requestCode +" / " + SIGNAL_PERMISSON);
        if (requestCode == SIGNAL_PERMISSON) {
            Log.d(TAG, "권한 : " + grantResults.length +" / " + grantResults[0]);
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "권한 : " + grantResults[0]);
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "권한 승인");
                } else {
                    //Toast.makeText(getApplicationContext(), permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "권한 승인되지 않음.");
                }
            }
        }
    }//end of onRequestPermissionsResult
}
