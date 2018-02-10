package com.hch.hooney.tourtogether;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hch.hooney.tourtogether.DAO.TourApiItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class JustShowMapActivity extends AppCompatActivity {
    private final String TAG="JustShowMapActivity";

    //layout
    private ImageButton back;

    //variable
    private ArrayList<TourApiItem> courseList;

    private SupportMapFragment supportMapFragment;

    private GoogleMap googleMap;

    private String func;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_show_map);

        if(getIntent()==null){
            Toast.makeText(this, getResources().getText(R.string.error_intent), Toast.LENGTH_SHORT).show();
            finish();
        }else{
            func = getIntent().getStringExtra("func");

            courseList = (ArrayList<TourApiItem>) getIntent().getSerializableExtra("list");
            init();
            setMap();
            setEvent();
        }
    }

    private void init(){

        back = (ImageButton) findViewById(R.id.jsm_back);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.jsm_map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.jsm_map, supportMapFragment).commit();
        }
    }

    private void setEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                googleMap.getUiSettings().setCompassEnabled(true);
                float bitmapDescriptorFactory = 0;
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(guideItem.getGpsy(), guideItem.getGpsx()), 14));

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(courseList.get(0).getMapy(), courseList.get(0).getMapx()), 12));
                bitmapDescriptorFactory = BitmapDescriptorFactory.HUE_ROSE;

                if(func.equals("course")){

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(courseList.get(0).getMapy(), courseList.get(0).getMapx()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title("Course 1 : "+courseList.get(0).getTitle())
                            .snippet("Category : "+getCategory(courseList.get(0).getContentTypeID()))
                            .zIndex((float) 0)
                    );

                    for(int j = 1;j<courseList.size();j++){
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(courseList.get(j).getMapy(), courseList.get(j).getMapx()))
                                .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                                .title("Course "+(j+1)+" : " + courseList.get(j).getTitle())
                                .snippet("Category : "+getCategory(courseList.get(j).getContentTypeID()))
                                .zIndex((float) (j))
                        );
                    }
                }else if(func.equals("book")){
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(courseList.get(1).getMapy(), courseList.get(1).getMapx()))
                            .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                            .title("Course 1 : "+courseList.get(1).getTitle())
                            .snippet("Category : "+getCategory(courseList.get(1).getContentTypeID()))
                            .zIndex((float) 1)
                    );

                    for(int j = 2;j<courseList.size();j++){
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(courseList.get(j).getMapy(), courseList.get(j).getMapx()))
                                .icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory))
                                .title("Course "+(j)+" : " + courseList.get(j).getTitle())
                                .snippet("Category : "+getCategory(courseList.get(j).getContentTypeID()))
                                .zIndex((float) (j))
                        );
                    }
                }

                PolylineOptions polygonOptions = new PolylineOptions();
                ArrayList<LatLng> points = new ArrayList<LatLng>();


                points.add(new LatLng(courseList.get(0).getMapy(), courseList.get(0).getMapx()));

                for(int i = 1; i<courseList.size();i++){
                    LatLng position = new LatLng(courseList.get(i).getMapy(), courseList.get(i).getMapx());
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
                }

                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View view = getLayoutInflater().inflate(R.layout.info_google_map_ballon, null);

                        ImageView mainImage = (ImageView) view.findViewById(R.id.ballon_img);
                        TextView title = (TextView) view.findViewById(R.id.balloon_item_title);
                        TextView snipet = (TextView) view.findViewById(R.id.balloon_item_snippet);

                        title.setText(marker.getTitle());
                        snipet.setText(marker.getSnippet());

                        if(!courseList.get((int)marker.getZIndex()).getFirstImage().equals("")){
                            Picasso.with(getApplicationContext()).load(courseList.get((int)marker.getZIndex()).getFirstImage()).into(mainImage);
                        }

                        return view;
                    }
                });

            }
        });
    }

    private String getCategory(String cat){
        String feild = "";

        if(cat.equals("32")||cat.equals("80")){
            feild = getResources().getText(R.string.search_tab6).toString();
        }else if(cat.equals("12")||cat.equals("76")){
            feild = getResources().getText(R.string.search_tab1).toString();
        }else if(cat.equals("14")||cat.equals("78")){
            feild = getResources().getText(R.string.search_tab2).toString();
        }else if(cat.equals("28")||cat.equals("75")){
            feild = getResources().getText(R.string.search_tab3).toString();
        }else if(cat.equals("38")||cat.equals("79")){
            feild = getResources().getText(R.string.search_tab4).toString();
        }else if(cat.equals("39")||cat.equals("82")){
            feild = getResources().getText(R.string.search_tab5).toString();
        }else{
            //Intent intent = new Intent(mContext, AnotherFieldActivity.class);
        }

        return feild.replaceAll("\n", "");
    }
}
