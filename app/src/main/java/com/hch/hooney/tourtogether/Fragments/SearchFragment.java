package com.hch.hooney.tourtogether.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.R;
import com.hch.hooney.tourtogether.ResourceCTRL.ConvertAreaCode;
import com.hch.hooney.tourtogether.ResourceCTRL.Location;
import com.hch.hooney.tourtogether.SearchActivity;
import com.hch.hooney.tourtogether.SelectMapActivity;
import com.hch.hooney.tourtogether.Service.GPS;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private final String TAG = "SearchFragment";
    private final int SIGNAL_PERMISSON = 8001;
    private final int SIGNAL_SELECTMAP = 9009;
    private ProgressDialog asyncDialog;
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
    private Handler handler;

    private boolean isSearchArea;  //true : Search Area false : Search Radius
    private int selectField; //0: None 1: Tourist Destination 2: Cultural Facilities 3: Leisure Sports 4: Festival
    private String radius_toString;
    private double lat;
    private double lon;

    private String field;
    private String URL;
    private String CONTETNTTYPEID;
    private String AREACODE;
    private String SIGUNGU;
    private String CAT1;
    private String CAT2;
    private String CAT3;



    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        init();
        event();

        return view;
    }

    private void init(){
        asyncDialog = new ProgressDialog(getActivity());
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));

        //variable
        isSearchArea = true;
        selectField = 0;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what){
                    case 1001:
                        checkDangerousPermissions();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //layout
        searchArea = (Button) view.findViewById(R.id.search_area_btn);
        searchRadius = (Button) view.findViewById(R.id.search_radius_btn);
        searchAreaLayout = (LinearLayout) view.findViewById(R.id.Search_area_layout);
        searchRadiuslayout = (LinearLayout) view.findViewById(R.id.Search_radius_layout);
        searchAreaShowLocation = (TextView) view.findViewById(R.id.search_area_show_location_textview);
        searchAreaAutoFind = (Button) view.findViewById(R.id.search_area_find_location_btn);
        searchAreaSelectFind = (Button) view.findViewById(R.id.search_area_select_location_btn);
        searchRadiusShowLocation = (TextView) view.findViewById(R.id.search_radius_show_location_textview);
        searchRadiusAutoFind = (Button) view.findViewById(R.id.search_radius_find_location_btn);
        searchRadiusSpinner = (Spinner) view.findViewById(R.id.search_radius_spinner);
        fieldTab1 = (Button) view.findViewById(R.id.search_field_tab1);
        fieldTab2 = (Button) view.findViewById(R.id.search_field_tab2);
        fieldTab3 = (Button) view.findViewById(R.id.search_field_tab3);
        fieldTab4 = (Button) view.findViewById(R.id.search_field_tab4);
        fieldTab5 = (Button) view.findViewById(R.id.search_field_tab5);
        fieldTab6 = (Button) view.findViewById(R.id.search_field_tab6);
        searchBTN = (Button) view.findViewById(R.id.search_search_btn);
    }

    private void event(){
        searchArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSearchArea){
                    isSearchArea = true;
                    clearLocationFiledButton();
                    selectLocationFiledButton();
                    AREACODE = null;
                    SIGUNGU = null;
                    searchAreaShowLocation.setText("now...");
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
                    AREACODE = null;
                    SIGUNGU = null;
                    searchRadiusShowLocation.setText("now...");
                }
            }
        });
        fieldTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectField=1;
                CAT1="A01";
                CAT2="";
                CAT3="";
                Log.d(TAG, "DAO.Language : " + DAO.Language);
                if(DAO.Language.equals("ko")){
                    CONTETNTTYPEID="12";
                }else if(DAO.Language.equals("en")){
                    CONTETNTTYPEID="76";
                }

                field = getString(R.string.search_tab1);
                clearFieldButton();
                selectFieldButton();
            }
        });
        fieldTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectField=2;
                CAT1="A02";
                CAT2="";
                CAT3="";
                if(DAO.Language.equals("ko")){
                    CONTETNTTYPEID="14";
                }else if(DAO.Language.equals("en")){
                    CONTETNTTYPEID="78";
                }
                field = getString(R.string.search_tab2);
                clearFieldButton();
                selectFieldButton();
            }
        });
        fieldTab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectField=3;
                CAT1="A03";
                CAT2="";
                CAT3="";
                if(DAO.Language.equals("ko")){
                    CONTETNTTYPEID="28";
                }else if(DAO.Language.equals("en")){
                    CONTETNTTYPEID="75";
                }
                field = getString(R.string.search_tab3);
                clearFieldButton();
                selectFieldButton();
            }
        });
        fieldTab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectField=4;
                CAT1="A04";
                CAT3="";
                if(DAO.Language.equals("ko")){
                    CAT2="A0401";
                    CONTETNTTYPEID="38";
                }else if(DAO.Language.equals("en")){
                    CAT2="A0401";
                    CONTETNTTYPEID="79";
                }
                field = getString(R.string.search_tab4);
                clearFieldButton();
                selectFieldButton();
            }
        });
        fieldTab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectField=5;
                CAT3="";
                if(DAO.Language.equals("ko")){
                    CAT1="A05";
                    CAT2="A0502";
                    CONTETNTTYPEID="39";
                }else if(DAO.Language.equals("en")){
                    CAT1="A05";
                    CAT2="A0502";
                    CONTETNTTYPEID="82";
                }
                field = getString(R.string.search_tab5);
                clearFieldButton();
                selectFieldButton();
            }
        });
        fieldTab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectField=6;
                CAT3="";
                if(DAO.Language.equals("ko")){
                    CAT1="B02";
                    CAT2="B0201";
                    CONTETNTTYPEID="32";
                }else if(DAO.Language.equals("en")){
                    CAT1="B02";
                    CAT2="B0201";
                    CONTETNTTYPEID="80";
                }
                field = getString(R.string.search_tab6);
                clearFieldButton();
                selectFieldButton();
            }
        });
        searchAreaSelectFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectMapActivity.class);
                intent.putExtra("who", "search");
                startActivityForResult(intent, SIGNAL_SELECTMAP);
            }
        });
        searchAreaAutoFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg_getLocation = Message.obtain();
                        msg_getLocation.what=1001;
                        handler.sendMessage(msg_getLocation);
                    }
                }).start();
            }
        });
        searchRadiusAutoFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg_getLocation = Message.obtain();
                        msg_getLocation.what=1001;
                        handler.sendMessage(msg_getLocation);
                    }
                }).start();
            }
        });
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service="";
                if(DAO.Language.equals("ko")){
                    service="KorService";
                }else if(DAO.Language.equals("en")){
                    service="EngService";
                }else{
                    service="EngService";
                }
                Log.d(TAG, "Sevice : " + service);
                if(isSearchArea){
                    URL = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+"/areaBasedList"+
                            "?ServiceKey="+ DAO.ServiceKey+
                            "&contentTypeId="+CONTETNTTYPEID+
                            "&areaCode="+AREACODE+
                            "&sigunguCode="+SIGUNGU+
                            "&cat1="+CAT1+
                            "&cat2="+CAT2+
                            "&cat3="+CAT3+
                            "&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=P"+
                            "&numOfRows=200"+
                            "&pageNo=1 ";
                }else{
                    String tempRadius = searchRadiusSpinner.getSelectedItem().toString()
                            .replaceAll("km", "")
                            .replaceAll(" ", "");
                    radius_toString = tempRadius+"000";

                    URL = "http://api.visitkorea.or.kr/openapi/service/rest/"+service+
                            "/locationBasedList?ServiceKey="+DAO.ServiceKey+
                            "&contentTypeId="+CONTETNTTYPEID+
                            "&mapX="+lon+
                            "&mapY="+lat+
                            "&radius="+radius_toString+
                            "&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=P&numOfRows=200&pageNo=1";
                }
                if(CAT1 ==null || CAT2==null||CAT3==null||AREACODE==null||SIGUNGU==null){
                    Toast.makeText(getContext(), getResources().getText(R.string.notify_search_Fail), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("url", URL);
                    intent.putExtra("field", field);
                    intent.putExtra("contentTypeID", CONTETNTTYPEID);
                    startActivity(intent);
                }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGNAL_SELECTMAP){
            if(resultCode == Activity.RESULT_OK){
                lat = data.getDoubleExtra("mapy", 0.0);
                lon = data.getDoubleExtra("mapx", 0.0);
                if(lat == 0.0 && lon == 0.0 ){
                    Toast.makeText(getContext(), getResources().getText(R.string.error_gps_loading), Toast.LENGTH_SHORT).show();
                }else{
                    Location location = new Location(getContext(), lat, lon);
                    String areaResult = location.searchLocation();

                    if(isSearchArea){
                        searchAreaShowLocation.setText(areaResult);
                    }else{
                        searchRadiusShowLocation.setText(areaResult);
                    }
                    if(DAO.Language == "en"){
                        Toast.makeText(getContext(), getResources().getText(R.string.notify_get_gps), Toast.LENGTH_SHORT).show();
                    }
                    ConvertAreaCode convertAreaCode = new ConvertAreaCode(getContext());
                    convertAreaCode.filteringToAuto(areaResult);
                    AREACODE = convertAreaCode.getAreaCode();
                    SIGUNGU = convertAreaCode.getSigunguCode();
                }
            }
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
            runGPS();
        } else {
            Log.d(TAG, "권한없음");

            ActivityCompat.requestPermissions(this.getActivity(), permissions, SIGNAL_PERMISSON);
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
                    runGPS();
                } else {
                    //Toast.makeText(getApplicationContext(), permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "권한 승인되지 않음.");
                }
            }
        }
    }//end of onRequestPermissionsResult

    private void runGPS(){
        asyncDialog.show();
        GPS gps = new GPS(getContext());
        if(gps.isGetLocation()){
            lat = gps.getLat();
            lon = gps.getLon();
            if(lat == 0.0 && lon == 0.0 ){
                Toast.makeText(getContext(), getResources().getText(R.string.error_gps_loading), Toast.LENGTH_SHORT).show();
            }else{
                Location location = new Location(getContext(), lat, lon);
                String areaResult = location.searchLocation();

                if(isSearchArea){
                    searchAreaShowLocation.setText(areaResult);
                }else{
                    searchRadiusShowLocation.setText(areaResult);
                }
                if(DAO.Language == "en"){
                    Toast.makeText(getContext(), getResources().getText(R.string.notify_get_gps), Toast.LENGTH_SHORT).show();
                }
                ConvertAreaCode convertAreaCode = new ConvertAreaCode(getContext());
                convertAreaCode.filteringToAuto(areaResult);
                AREACODE = convertAreaCode.getAreaCode();
                SIGUNGU = convertAreaCode.getSigunguCode();

            }
            asyncDialog.dismiss();
            gps.stopUsingGPS();
        }else{
            asyncDialog.dismiss();
            gps.showSettingAlert();
        }
    }
}
