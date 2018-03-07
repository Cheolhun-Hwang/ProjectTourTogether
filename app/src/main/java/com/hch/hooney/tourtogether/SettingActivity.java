package com.hch.hooney.tourtogether;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.Net.InstanceWeather;
import com.hch.hooney.tourtogether.ResourceCTRL.ConvertAreaCode;
import com.hch.hooney.tourtogether.ResourceCTRL.Location;
import com.hch.hooney.tourtogether.Service.GPS;
import com.hch.hooney.tourtogether.TranslateNaver.PapagoNMT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingActivity extends AppCompatActivity {
    private final String TAG = "SettingActivity";
    private final int SIGNAL_PERMISSON = 7007;

    private AdView adView;
    private ImageButton back, weather_refresh;
    private SwitchCompat push;
    private Button logout, notice, traslate, traslateList, CurrencyConvert, qa, termAndPolicy;
    private TextView version;
    private Handler handler;

    private TextView weather_spot_name, weather_today_min, weather_today_max,
            weather_tomorrow_min, weather_tomorrow_max, weather_at_min, weather_at_max;
    private ImageView weather_today_i, weather_tomorrow_i, weather_afterTomorrow_i;
    private ProgressDialog asyncDialog;
    private JSONObject obj_today, obj_tomorrow, obj_dayaftertomorrow;

    private double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
        setEvent();
        setUi();
    }

    private void setUi(){
        getWeather();
    }

    private void init(){
        asyncDialog = new ProgressDialog(SettingActivity.this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));

        getGPSPreferences();

        back = (ImageButton) findViewById(R.id.setting_back_btn);
        push = (SwitchCompat) findViewById(R.id.setting_push);

        notice = (Button)findViewById(R.id.setting_notice);
        traslate = (Button) findViewById(R.id.setting_translate);
        traslateList = (Button) findViewById(R.id.setting_translate_list);
        CurrencyConvert = (Button) findViewById(R.id.setting_convert);
        termAndPolicy = (Button) findViewById(R.id.setting_t_a_p);

        weather_spot_name = (TextView) findViewById(R.id.weather_spot_name);
        weather_refresh = (ImageButton) findViewById(R.id.weather_spot_refresh);
        weather_today_max = (TextView) findViewById(R.id.weather_today_tmax);
        weather_today_min = (TextView) findViewById(R.id.weather_today_tmin);
        weather_today_i = (ImageView) findViewById(R.id.weather_today_image);
        weather_tomorrow_max = (TextView) findViewById(R.id.weather_tomorrow_tmax);
        weather_tomorrow_min = (TextView) findViewById(R.id.weather_tomorrow_tmin);
        weather_tomorrow_i = (ImageView) findViewById(R.id.weather_tomorrow_image);
        weather_at_max = (TextView) findViewById(R.id.weather_dayaftertomorrow_tmax);
        weather_at_min = (TextView) findViewById(R.id.weather_dayaftertomorrow_tmin);
        weather_afterTomorrow_i = (ImageView) findViewById(R.id.weather_dayaftertomorrow_image);

        qa = (Button)findViewById(R.id.setting_qa);

        if(getisPush()){
            push.setChecked(true);
        }else{
            push.setChecked(false);
        }
        logout = (Button) findViewById(R.id.setting_logout);
        version = (TextView)findViewById(R.id.setting_version);
        version.setText(DAO.nowVersion);

        adView = (AdView)findViewById(R.id.setting_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what==1001){
                    //weather
                    Log.d(TAG, "SET Weather UI");
                    try {
                        weather_spot_name.setText(msg.obj.toString());
                        setToday();
                        setTomorrow();
                        setDayAfter();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if(msg.what==6001){
                    //auto GPS
                    checkDangerousPermissions();
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private void setEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
        push.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    saveIsPushPreferences(true);
                }else{
                    saveIsPushPreferences(false);
                }
            }
        });

        traslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TranslateActivity.class));
            }
        });
        traslateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TranslateListActivity.class));
            }
        });
        weather_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg_getLocation = Message.obtain();
                        msg_getLocation.what=6001;
                        handler.sendMessage(msg_getLocation);
                    }
                }).start();
            }
        });

        CurrencyConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ConvertCurrencyActivity.class));
            }
        });

        termAndPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TermsAndPolicyActivity.class));
            }
        });

        qa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SuggetionsActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(9008);
                finish();
            }
        });
    }

    private void getWeather(){
        new Thread(new Runnable() {
            @Override
            public void run() {
               String result = new InstanceWeather(lat, lon, getApplicationContext()).send();
                try {
                    JSONObject obj_res = new JSONObject(result);
                    JSONObject obj_weather = obj_res.getJSONObject("weather");
                    JSONObject obj_summary = obj_weather.getJSONArray("summary").getJSONObject(0);
                    JSONObject obj_grid = obj_summary.getJSONObject("grid");

                    obj_today = obj_summary.getJSONObject("today");
                    //Log.d(TAG, "TODAY JSON : " + obj_today);
                    obj_tomorrow = obj_summary.getJSONObject("tomorrow");
                    //Log.d(TAG, "TOMORROW JSON : " + obj_tomorrow);
                    obj_dayaftertomorrow = obj_summary.getJSONObject("dayAfterTomorrow");
                    //Log.d(TAG, "DAYAFTERTOMORROW JSON : " + obj_dayaftertomorrow);

                    String addr = obj_grid.getString("city") + " " + obj_grid.getString("county")
                            + " " + obj_grid.getString("village");
                    //Log.d(TAG, "Now Addr : " + addr);

                    if(DAO.Language.equals("en")) {
                        String from = "ko";
                        String to = "en";
                        addr = new PapagoNMT(SettingActivity.this, from, to, addr).send();
                        //Log.d(TAG, "Convert KR _ EN : " + addr);
                    }

                    Message msg = handler.obtainMessage();
                    msg.what=1001;
                    msg.obj=addr;
                    handler.sendMessage(msg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
        boolean isall = true;
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(SettingActivity.this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                isall = false;
                break;
            }
        }

        if (isall) {
            Log.d(TAG, "권한있음");
            runGPS();
        } else {
            Log.d(TAG, "권한없음");

            ActivityCompat.requestPermissions(SettingActivity.this, permissions, SIGNAL_PERMISSON);
        }
    }//end of checkDangerousPermissions



    // 사용자의 권한 확인 후 사용자의 권한에 대한 응답 결과를 확인하는 콜백 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == SIGNAL_PERMISSON) {
            boolean isall = true;
            for(int temp : grantResults){
                if(temp == PackageManager.PERMISSION_DENIED){
                    isall = false;
                }
            }

            if(isall){
                runGPS();
            }else{
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.require_permission),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }//end of onRequestPermissionsResult

    private void runGPS(){
        asyncDialog.show();
        GPS gps = new GPS(SettingActivity.this);
        if(gps.isGetLocation()){
            lat = gps.getLat();
            lon = gps.getLon();
            if(lat == 0.0 && lon == 0.0 ){
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_gps_loading), Toast.LENGTH_SHORT).show();
            }else{
                saveGPSPreferences();
                getWeather();
            }
            asyncDialog.dismiss();
            gps.stopUsingGPS();
        }else{
            asyncDialog.dismiss();
            gps.showSettingAlert();
        }
    }


    private void saveGPSPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat("lat", (float) lat);
        editor.putFloat("lon", (float) lon);
        editor.commit();
    }

    // 값 불러오기
    private void getGPSPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        lat = (double) pref.getFloat("lat", 37.555133f);
        lon = (double) pref.getFloat("lon", 126.970726f);
    }

    private void setWeatherImage(String code, ImageView image){
        if(code.contains("01")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.sun));
        }else if(code.contains("02")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.sun_cloud));
        }else if(code.contains("03")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.cloud_much));
        }else if(code.contains("04")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.cloud));
        }else if(code.contains("05")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.rain));
        }else if(code.contains("06")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.snow));
        }else if(code.contains("07")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.raion_snow));
        }
    }

    private void setToday() throws JSONException {

        JSONObject sky = obj_today.getJSONObject("sky");
        JSONObject tem  = obj_today.getJSONObject("temperature");

        setWeatherImage(sky.getString("code"), weather_today_i);
        weather_today_max.setText("max : " + tem.getString("tmax"));
        weather_today_min.setText("min : " + tem.getString("tmin"));

    }
    private void setTomorrow()throws JSONException{
        JSONObject sky = obj_tomorrow.getJSONObject("sky");
        JSONObject tem  = obj_tomorrow.getJSONObject("temperature");

        setWeatherImage(sky.getString("code"), weather_tomorrow_i);
        weather_tomorrow_max.setText("max : " + tem.getString("tmax"));
        weather_tomorrow_min.setText("min : " + tem.getString("tmin"));
    }

    private void setDayAfter()throws JSONException{
        JSONObject sky = obj_dayaftertomorrow.getJSONObject("sky");
        JSONObject tem  = obj_dayaftertomorrow.getJSONObject("temperature");

        setWeatherImage(sky.getString("code"), weather_afterTomorrow_i);
        weather_at_max.setText("max : " + tem.getString("tmax"));
        weather_at_min.setText("min : " + tem.getString("tmin"));
    }

    private void saveIsPushPreferences(boolean ispush){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("ispush", ispush);
        editor.commit();
    }

    private boolean getisPush(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getBoolean("ispush", true);
    }
}
