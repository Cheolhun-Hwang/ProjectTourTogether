package com.hch.hooney.tourtogether.Fragments;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hch.hooney.tourtogether.R;
import com.hch.hooney.tourtogether.Service.GPS;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends Fragment {
    private final String TAG = "CourseeFragment";

    //Layout Resource
    private Button searchArea;
    private Button searchRadius;
    private LinearLayout searchAreaLayout;
    private LinearLayout searchRadiuslayout;

    private TextView searchAreaShowLocation;
    private Button searchAreaAutoFind;
    private Button searchAreaSelectFind;

    private TextView searchRadiusShowLocation;
    private Button searchRadiusAutoFind;
    private Spinner searchRadiusSpinner;

    private Button fieldTab1;
    private Button fieldTab2;
    private Button fieldTab3;
    private Button fieldTab4;
    private Button fieldTab5;
    private Button fieldTab6;

    private Button searchBTN;
    //variable
    private View view;

    private boolean isSearchArea;  //true : Search Area false : Search Radius
    private int selectField; //0: None 1: Family 2: alone 3: healing 4: walking 5: camping 6:taste
    private String radius;
    private double lat;
    private double lon;

    public CourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_course, container, false);

        init();
        event();

        return view;
    }

    private void init(){
        //variable
        isSearchArea = true;
        selectField = 0;

        //layout
        searchArea = (Button) view.findViewById(R.id.course_area_btn);
        searchRadius = (Button) view.findViewById(R.id.course_radius_btn);
        searchAreaLayout = (LinearLayout) view.findViewById(R.id.course_area_layout);
        searchRadiuslayout = (LinearLayout) view.findViewById(R.id.course_radius_layout);
        searchAreaShowLocation = (TextView) view.findViewById(R.id.course_area_show_location_textview);
        searchAreaAutoFind = (Button) view.findViewById(R.id.course_area_find_location_btn);
        searchAreaSelectFind = (Button) view.findViewById(R.id.course_radius_select_location_btn);
        searchRadiusShowLocation = (TextView) view.findViewById(R.id.course_radius_show_location_textview);
        searchRadiusAutoFind = (Button) view.findViewById(R.id.course_radius_find_location_btn);
        searchRadiusSpinner = (Spinner) view.findViewById(R.id.course_radius_spinner);
        fieldTab1 = (Button) view.findViewById(R.id.course_field_tab1);
        fieldTab2 = (Button) view.findViewById(R.id.course_field_tab2);
        fieldTab3 = (Button) view.findViewById(R.id.course_field_tab3);
        fieldTab4 = (Button) view.findViewById(R.id.course_field_tab4);
        fieldTab5 = (Button) view.findViewById(R.id.course_field_tab5);
        fieldTab6 = (Button) view.findViewById(R.id.course_field_tab6);
        searchBTN = (Button) view.findViewById(R.id.course_search_btn);
    }

    private void event(){
        searchArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSearchArea){
                    isSearchArea = true;
                    clearLocationFiledButton();
                    selectLocationFiledButton();
                }
            }
        });
        searchRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSearchArea){
                    isSearchArea = false;
                    clearLocationFiledButton();
                    selectLocationFiledButton();
                }
            }
        });
        fieldTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectField=1;
                clearFieldButton();
                selectFieldButton();
            }
        });
        fieldTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectField=2;
                clearFieldButton();
                selectFieldButton();
            }
        });
        fieldTab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectField=3;
                clearFieldButton();
                selectFieldButton();
            }
        });
        fieldTab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectField=4;
                clearFieldButton();
                selectFieldButton();
            }
        });
        fieldTab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectField=5;
                clearFieldButton();
                selectFieldButton();
            }
        });
        fieldTab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectField=6;
                clearFieldButton();
                selectFieldButton();
            }
        });
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Radius : " + searchRadiusSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        searchAreaAutoFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDangerousPermissions();
                GPS gps = new GPS(getContext());
                if(gps.isGetLocation()){
                    lat = gps.getLat();
                    lon = gps.getLon();

                    searchAreaShowLocation.setText("gps : " + lat + " / " + lon);
                    gps.stopUsingGPS();
                }else{
                    gps.showSettingAlert();
                }
            }
        });
        searchRadiusAutoFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDangerousPermissions();
                GPS gps = new GPS(getContext());
                if(gps.isGetLocation()){
                    lat = gps.getLat();
                    lon = gps.getLon();

                    searchRadiusShowLocation.setText("gps : " + lat + " / " + lon);
                    gps.stopUsingGPS();
                }else{
                    gps.showSettingAlert();
                }
            }
        });
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Radius : " + searchRadiusSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearLocationFiledButton(){
        searchArea.setBackgroundColor(getResources().getColor(R.color.grey_200));
        searchArea.setTextColor(getResources().getColor(R.color.grey_500));
        searchRadius.setBackgroundColor(getResources().getColor(R.color.grey_200));
        searchRadius.setTextColor(getResources().getColor(R.color.grey_500));
        searchAreaLayout.setVisibility(View.GONE);
        searchRadiuslayout.setVisibility(View.GONE);
    }

    private void selectLocationFiledButton(){
        if(isSearchArea){
            searchArea.setBackgroundColor(getResources().getColor(R.color.MainColor));
            searchArea.setTextColor(getResources().getColor(R.color.white));
            searchAreaLayout.setVisibility(View.VISIBLE);
        }else{
            searchRadius.setBackgroundColor(getResources().getColor(R.color.MainColor));
            searchRadius.setTextColor(getResources().getColor(R.color.white));
            searchRadiuslayout.setVisibility(View.VISIBLE);
        }
    }

    private void clearFieldButton(){
        fieldTab1.setBackgroundColor(getResources().getColor(R.color.white));
        fieldTab1.setTextColor(getResources().getColor(R.color.grey_400));
        fieldTab2.setBackgroundColor(getResources().getColor(R.color.white));
        fieldTab2.setTextColor(getResources().getColor(R.color.grey_400));
        fieldTab3.setBackgroundColor(getResources().getColor(R.color.white));
        fieldTab3.setTextColor(getResources().getColor(R.color.grey_400));
        fieldTab4.setBackgroundColor(getResources().getColor(R.color.white));
        fieldTab4.setTextColor(getResources().getColor(R.color.grey_400));
        fieldTab5.setBackgroundColor(getResources().getColor(R.color.white));
        fieldTab5.setTextColor(getResources().getColor(R.color.grey_400));
        fieldTab6.setBackgroundColor(getResources().getColor(R.color.white));
        fieldTab6.setTextColor(getResources().getColor(R.color.grey_400));
    }

    private void selectFieldButton(){
        switch (selectField){
            case 1:
                fieldTab1.setBackgroundColor(getResources().getColor(R.color.MainColor));
                fieldTab1.setTextColor(getResources().getColor(R.color.white));
                break;
            case 2:
                fieldTab2.setBackgroundColor(getResources().getColor(R.color.MainColor));
                fieldTab2.setTextColor(getResources().getColor(R.color.white));
                break;
            case 3:
                fieldTab3.setBackgroundColor(getResources().getColor(R.color.MainColor));
                fieldTab3.setTextColor(getResources().getColor(R.color.white));
                break;
            case 4:
                fieldTab4.setBackgroundColor(getResources().getColor(R.color.MainColor));
                fieldTab4.setTextColor(getResources().getColor(R.color.white));
                break;
            case 5:
                fieldTab5.setBackgroundColor(getResources().getColor(R.color.MainColor));
                fieldTab5.setTextColor(getResources().getColor(R.color.white));
                break;
            case 6:
                fieldTab6.setBackgroundColor(getResources().getColor(R.color.MainColor));
                fieldTab6.setTextColor(getResources().getColor(R.color.white));
                break;
            default:
                break;
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
            permissionCheck = ContextCompat.checkSelfPermission(getActivity(), permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "권한있음");
        } else {
            Log.d(TAG, "권한없음");

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[0])) {
                Log.d(TAG, "권한설명란");
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions, 1);
            }
        }
    }//end of checkDangerousPermissions

    // 사용자의 권한 확인 후 사용자의 권한에 대한 응답 결과를 확인하는 콜백 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
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
