package com.hch.hooney.tourtogether.TranslateNaver;

import android.app.Activity;
import android.util.Log;

import com.hch.hooney.tourtogether.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by hch on 2018-02-25.
 */

public class PapagoSMT {
    private String clientId = "";//애플리케이션 클라이언트 아이디값";
    private String clientSecret = "";//애플리케이션 클라이언트 시크릿값";

    private String from;
    private String to;

    private String Content;

    public PapagoSMT(Activity activity, String f, String t, String c) {
        clientId = activity.getResources().getString(R.string.naver_id);
        clientSecret = activity.getResources().getString(R.string.naver_secret);
        this.from = f;
        this.to = t;
        this.Content = c;
    }

    public String send(){
        try {
            String text = URLEncoder.encode(this.Content, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source="+this.from+"&target="+this.to+"&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
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
            Log.d("Papago SMT", response.toString());

            JSONObject json = new JSONObject(response.toString());
            JSONObject mes = new JSONObject(json.getString("message"));
            JSONObject result = new JSONObject(mes.getString("result"));
            String res = result.getString("translatedText");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Papago SMT", "Error");
            return "none";
        }
    }
}
