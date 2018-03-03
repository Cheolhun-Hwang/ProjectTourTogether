package com.hch.hooney.tourtogether.Net;

import android.content.Context;
import android.util.Log;

import com.hch.hooney.tourtogether.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hch on 2018-03-02.
 */

public class InstanceWeather {
    private double lat;
    private double lon;
    private Context mContext;

    public InstanceWeather(double lat, double lon, Context context) {
        this.lat = lat;
        this.lon = lon;
        this.mContext = context;
    }

    public String send(){
        String key = mContext.getResources().getString(R.string.weather_api);
        String res="";
        String apiURL = "https://api2.sktelecom.com/weather/summary"+
                "?appKey="+key+"&version=2&lat="+ this.lat+"&lon="+this.lon;
        URL url = null;
        try {
            url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);                            // default is true
            con.setDoOutput(false);                          //default is false

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200||responseCode==9200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            Log.d("Weather Response : ", response.toString());
            res = response.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            res = "None";
        } catch (IOException e) {
            e.printStackTrace();
            res = "None";
        }


        return res;
    }
}
