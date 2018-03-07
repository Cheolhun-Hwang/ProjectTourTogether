package com.hch.hooney.tourtogether;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConvertCurrencyActivity extends AppCompatActivity {
    private final String TAG = "ConvertCurrencyActivity";

    private ProgressDialog asyncDialog;
    private LinearLayout type_one_layout, type_two_layout;
    private ImageButton type_one_switch, type_two_switch, back;
    private Spinner type_one_spinner, type_two_spinner;

    private EditText cvc_context;
    private ImageButton cvc_send;
    private TextView cvc_result, cvc_mark;

//    private Handler handler;
//    private Thread getCurrencyData;
    private AdView adView;

    private String[] marks = {
            "USD", "AED", "AUD", "BHD", "CAD", "CHF", "CNH", "DKK",
            "EUR", "GBP", "HKD", "IDR", "JPY", "KRW", "KWD", "MYR",
            "NOK", "NZD", "SAR", "SEK", "SGD", "THB"
    };

    private String res=
            "[{\"result\":1,\"cur_unit\":\"AED\",\"ttb\":\"291.23\",\"tts\":\"297.12\",\"deal_bas_r\":\"294.18\",\"bkpr\":\"294\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"294\",\"kftc_deal_bas_r\":\"294.18\",\"cur_nm\":\"아랍에미리트 디르함\"},{\"result\":1,\"cur_unit\":\"AUD\",\"ttb\":\"830.77\",\"tts\":\"847.56\",\"deal_bas_r\":\"839.17\",\"bkpr\":\"839\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"839\",\"kftc_deal_bas_r\":\"839.17\",\"cur_nm\":\"호주 달러\"},{\"result\":1,\"cur_unit\":\"BHD\",\"ttb\":\"2,836.7\",\"tts\":\"2,894.01\",\"deal_bas_r\":\"2,865.36\",\"bkpr\":\"2,865\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"2,865\",\"kftc_deal_bas_r\":\"2,865.36\",\"cur_nm\":\"바레인 디나르\"},{\"result\":1,\"cur_unit\":\"CAD\",\"ttb\":\"824.52\",\"tts\":\"841.17\",\"deal_bas_r\":\"832.85\",\"bkpr\":\"832\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"832\",\"kftc_deal_bas_r\":\"832.85\",\"cur_nm\":\"캐나다 달러\"},{\"result\":1,\"cur_unit\":\"CHF\",\"ttb\":\"1,138.03\",\"tts\":\"1,161.02\",\"deal_bas_r\":\"1,149.53\",\"bkpr\":\"1,149\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"1,149\",\"kftc_deal_bas_r\":\"1,149.53\",\"cur_nm\":\"스위스 프랑\"},{\"result\":1,\"cur_unit\":\"CNH\",\"ttb\":\"168.99\",\"tts\":\"172.4\",\"deal_bas_r\":\"170.7\",\"bkpr\":\"170\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"170\",\"kftc_deal_bas_r\":\"170.7\",\"cur_nm\":\"위안화\"},{\"result\":1,\"cur_unit\":\"DKK\",\"ttb\":\"177.17\",\"tts\":\"180.74\",\"deal_bas_r\":\"178.96\",\"bkpr\":\"178\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"178\",\"kftc_deal_bas_r\":\"178.96\",\"cur_nm\":\"덴마아크 크로네\"},{\"result\":1,\"cur_unit\":\"EUR\",\"ttb\":\"1,319.78\",\"tts\":\"1,346.45\",\"deal_bas_r\":\"1,333.12\",\"bkpr\":\"1,333\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"1,333\",\"kftc_deal_bas_r\":\"1,333.12\",\"cur_nm\":\"유로\"},{\"result\":1,\"cur_unit\":\"GBP\",\"ttb\":\"1,481.58\",\"tts\":\"1,511.51\",\"deal_bas_r\":\"1,496.55\",\"bkpr\":\"1,496\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"1,496\",\"kftc_deal_bas_r\":\"1,496.55\",\"cur_nm\":\"영국 파운드\"},{\"result\":1,\"cur_unit\":\"HKD\",\"ttb\":\"136.59\",\"tts\":\"139.34\",\"deal_bas_r\":\"137.97\",\"bkpr\":\"137\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"137\",\"kftc_deal_bas_r\":\"137.97\",\"cur_nm\":\"홍콩 달러\"},{\"result\":1,\"cur_unit\":\"IDR(100)\",\"ttb\":\"7.77\",\"tts\":\"7.92\",\"deal_bas_r\":\"7.85\",\"bkpr\":\"7\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"7\",\"kftc_deal_bas_r\":\"7.85\",\"cur_nm\":\"인도네시아 루피아\"},{\"result\":1,\"cur_unit\":\"JPY(100)\",\"ttb\":\"1,007.34\",\"tts\":\"1,027.69\",\"deal_bas_r\":\"1,017.52\",\"bkpr\":\"1,017\",\"yy_efee_r\":\"0.94967\",\"ten_dd_efee_r\":\"0.02374\",\"kftc_bkpr\":\"1,017\",\"kftc_deal_bas_r\":\"1,017.52\",\"cur_nm\":\"일본 옌\"},{\"result\":1,\"cur_unit\":\"KRW\",\"ttb\":\"0\",\"tts\":\"0\",\"deal_bas_r\":\"1\",\"bkpr\":\"1\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"1\",\"kftc_deal_bas_r\":\"1\",\"cur_nm\":\"한국 원\"},{\"result\":1,\"cur_unit\":\"KWD\",\"ttb\":\"3,566.48\",\"tts\":\"3,638.53\",\"deal_bas_r\":\"3,602.51\",\"bkpr\":\"3,602\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"3,602\",\"kftc_deal_bas_r\":\"3,602.51\",\"cur_nm\":\"쿠웨이트 디나르\"},{\"result\":1,\"cur_unit\":\"MYR\",\"ttb\":\"273.82\",\"tts\":\"279.35\",\"deal_bas_r\":\"276.59\",\"bkpr\":\"276\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"276\",\"kftc_deal_bas_r\":\"276.59\",\"cur_nm\":\"말레이지아 링기트\"},{\"result\":1,\"cur_unit\":\"NOK\",\"ttb\":\"136.81\",\"tts\":\"139.58\",\"deal_bas_r\":\"138.2\",\"bkpr\":\"138\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"138\",\"kftc_deal_bas_r\":\"138.2\",\"cur_nm\":\"노르웨이 크로네\"},{\"result\":1,\"cur_unit\":\"NZD\",\"ttb\":\"773.22\",\"tts\":\"788.85\",\"deal_bas_r\":\"781.04\",\"bkpr\":\"781\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"781\",\"kftc_deal_bas_r\":\"781.04\",\"cur_nm\":\"뉴질랜드 달러\"},{\"result\":1,\"cur_unit\":\"SAR\",\"ttb\":\"285.21\",\"tts\":\"290.98\",\"deal_bas_r\":\"288.1\",\"bkpr\":\"288\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"288\",\"kftc_deal_bas_r\":\"288.1\",\"cur_nm\":\"사우디 리얄\"},{\"result\":1,\"cur_unit\":\"SEK\",\"ttb\":\"129.45\",\"tts\":\"132.06\",\"deal_bas_r\":\"130.76\",\"bkpr\":\"130\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"130\",\"kftc_deal_bas_r\":\"130.76\",\"cur_nm\":\"스웨덴 크로나\"},{\"result\":1,\"cur_unit\":\"SGD\",\"ttb\":\"811.45\",\"tts\":\"827.84\",\"deal_bas_r\":\"819.65\",\"bkpr\":\"819\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"819\",\"kftc_deal_bas_r\":\"819.65\",\"cur_nm\":\"싱가포르 달러\"},{\"result\":1,\"cur_unit\":\"THB\",\"ttb\":\"34.06\",\"tts\":\"34.75\",\"deal_bas_r\":\"34.41\",\"bkpr\":\"34\",\"yy_efee_r\":\"0\",\"ten_dd_efee_r\":\"0\",\"kftc_bkpr\":\"34\",\"kftc_deal_bas_r\":\"34.41\",\"cur_nm\":\"태국 바트\"},{\"result\":1,\"cur_unit\":\"USD\",\"ttb\":\"1,069.69\",\"tts\":\"1,091.3\",\"deal_bas_r\":\"1,080.5\",\"bkpr\":\"1,080\",\"yy_efee_r\":\"3.0349\",\"ten_dd_efee_r\":\"0.0843\",\"kftc_bkpr\":\"1,080\",\"kftc_deal_bas_r\":\"1,080.5\",\"cur_nm\":\"미국 달러\"}]";


    private JSONArray cvc_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_currency);

        init();
        setEvent();
        setUI();
    }

    private void init(){
        asyncDialog = new ProgressDialog(this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));

        adView = (AdView)findViewById(R.id.convertCurrency_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        back = (ImageButton)findViewById(R.id.convertCurrency_back);
        type_one_layout = (LinearLayout) findViewById(R.id.convertCurrency_type_One);
        type_one_layout.setVisibility(View.VISIBLE);
        type_two_layout = (LinearLayout) findViewById(R.id.convertCurrency_type_Two);
        type_two_layout.setVisibility(View.GONE);
        type_one_switch = (ImageButton) findViewById(R.id.cvc_type_one_switch);
        type_two_switch = (ImageButton) findViewById(R.id.cvc_type_two_switch);
        type_one_spinner = (Spinner) findViewById(R.id.cvc_type_one_spinner);
        type_two_spinner = (Spinner) findViewById(R.id.cvc_type_two_spinner);
        cvc_context = (EditText) findViewById(R.id.cvc_edit);
        cvc_send = (ImageButton) findViewById(R.id.cvc_send);
        cvc_result = (TextView) findViewById(R.id.cvc_result);
        cvc_mark = (TextView) findViewById(R.id.cvc_mark);

        loadGetCurrency();

//        handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                if(msg.what == 1001){
//                    //get Currency Data
//                }
//
//                return true;
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        type_one_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_one_layout.setVisibility(View.GONE);
                type_one_spinner.setSelection(0);
                type_two_layout.setVisibility(View.VISIBLE);
                cvc_context.setHint("USD 0");
                cvc_context.setText("");
                cvc_mark.setText("KRW");
                cvc_result.setText("0");
            }
        });

        type_two_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_one_layout.setVisibility(View.VISIBLE);
                cvc_context.setHint("KRW 0");
                cvc_context.setText("");
                cvc_result.setText("0");
                cvc_mark.setText("USD");
                type_two_layout.setVisibility(View.GONE);
                type_two_spinner.setSelection(0);
            }
        });

        type_one_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mark = marks[position];
                cvc_mark.setText(mark);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cvc_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cvc_context.length() < 1){
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.currency_fill_edittext),
                            Toast.LENGTH_SHORT).show();
                }else{
                    if(type_one_layout.getVisibility() == View.VISIBLE){
                        exchangeRate(true);
                    }else{
                        exchangeRate(false);
                    }
                }
                //키보드 숨기기
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(cvc_context.getWindowToken(), 0);

            }
        });

    }

    private void exchangeRate(boolean fromKRW){
        try{
            if(fromKRW){
                //KRW -> Other
                String target = marks[type_one_spinner.getSelectedItemPosition()];
                Log.d(TAG, "fromKRW true : Mark, " + target);
                double rate = 0.0;
                for(int i = 0; i<cvc_array.length();i++){
                   JSONObject json = cvc_array.getJSONObject(i);
                    Log.d(TAG, "fromKRW true : json item, " + json.get("cur_unit"));
                   if(json.get("cur_unit").equals(target)){
                       rate = Double.parseDouble(json.get("kftc_deal_bas_r").toString().replaceAll(",", ""));
                       break;
                   }
                }
                Log.d(TAG, "fromKRW true : rate, " +rate);
                double krw = Double.parseDouble(cvc_context.getText().toString());
                if(marks[type_two_spinner.getSelectedItemPosition()].equals("JPY")||
                        marks[type_two_spinner.getSelectedItemPosition()].equals("IDR")){
                    cvc_result.setText(String.format("%.2f", (krw/rate*100)));
                }else{
                    cvc_result.setText(String.format("%.2f", (krw/rate)));
                }

            }else{
                //other -> KRW
                String target = marks[type_one_spinner.getSelectedItemPosition()];
                double money = Double.parseDouble(cvc_context.getText().toString());
                double rate = 0.0;

                Log.d(TAG, "fromKRW false : Mark, " + target);
                Log.d(TAG, "fromKRW false : money, " + money);

                for(int i = 0; i<cvc_array.length();i++){
                    JSONObject json = cvc_array.getJSONObject(i);
                    Log.d(TAG, "fromKRW false : json item, " + json.get("cur_unit"));
                    if(json.get("cur_unit").equals(target)){
                        rate = Double.parseDouble(json.get("kftc_deal_bas_r").toString().replaceAll(",", ""));
                        break;
                    }
                }

                if(marks[type_two_spinner.getSelectedItemPosition()].equals("JPY")||
                        marks[type_two_spinner.getSelectedItemPosition()].equals("IDR")){
                    cvc_result.setText(String.format("%.2f", (money*0.01 * rate)));
                }else{
                    cvc_result.setText(String.format("%.2f", (money*rate)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setUI(){

    }

    private void loadGetCurrency(){
        try {
            cvc_array = new JSONArray(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

//        if(getCurrencyData == null || getCurrencyData.isInterrupted()){
//            getCurrencyData = initCurrencyData();
//        }
//
//        getCurrencyData.start();
    }

    @Override
    protected void onStop() {
//        getCurrencyData.interrupt();
//        getCurrencyData = null;

        super.onStop();
    }

    private Thread initCurrencyData(){
        return new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
