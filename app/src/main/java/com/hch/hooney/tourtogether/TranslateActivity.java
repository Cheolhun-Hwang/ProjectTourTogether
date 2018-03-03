package com.hch.hooney.tourtogether;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.Recycler.Translate.TranslateItem;
import com.hch.hooney.tourtogether.TranslateNaver.PapagoNMT;
import com.hch.hooney.tourtogether.TranslateNaver.PapagoSMT;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class TranslateActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private final String TAG = "TranslateActivity";
    private ImageButton Back;
    private AdView adView;
    private Spinner from, to;
    private ImageButton switch_spinner, runTr;
    private Button hearing, save;
    private TextView resultTextView, editCouter;
    private EditText content;
    private boolean isSMT, isContextRight;

    private String ttsTarget;
    private TextToSpeech tts;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        init();
        setEvent();
    }

    private void init(){
        //variable
        isSMT = true;
        isContextRight = false;

        adView = (AdView)findViewById(R.id.translate_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        switch_spinner = (ImageButton) findViewById(R.id.translate_switch_spinner);
        from = (Spinner) findViewById(R.id.tr_spinner_from);
        to  = (Spinner) findViewById(R.id.tr_spinner_to);
        hearing = (Button) findViewById(R.id.translate_healing);
        hearing.setEnabled(false);
        save = (Button) findViewById(R.id.translate_save);
        resultTextView = (TextView) findViewById(R.id.translate_result_textview);
        runTr = (ImageButton) findViewById(R.id.translate_runTR);
        content = (EditText)findViewById(R.id.translate_editText);
        editCouter = (TextView) findViewById(R.id.translate_textCounter);

        Back = (ImageButton) findViewById(R.id.translate_back);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == 1001){
                    String result = (String)msg.obj;
                    resultTextView.setText(result);
                }

                return true;
            }
        });

        initSpinner();

        Intent chkIntent = new Intent();
        chkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(chkIntent, 8008);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 8008){
            switch (resultCode){
                case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
                    Log.d(TAG, "Case : CHECK_VOICE_DATA_PASS");
                    tts = null;
                    break;
                case TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA:
                case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA:
                case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME:
                    Log.d(TAG, "Case : 언어 요소 필요 > " + resultCode);
                    Intent installIntent = new Intent();
                    installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(installIntent);
                    break;
                case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
                default:
                    Log.e(TAG, "None Exist TTS");
            }
        }
    }

    private void runSwitchSpinner(){
        int from_index = from.getSelectedItemPosition();
        int to_index = to.getSelectedItemPosition();

        from.setSelection(to_index);
        to.setSelection(from_index);
    }

    private boolean isSameSpinner(){
        if(from.getSelectedItem().equals(to.getSelectedItem())){
            return true;
        }else{
            return false;
        }
    }

    private void initSpinner(){
        if(DAO.Language.equals("ko")){
            ttsTarget = "ko";
            from.setSelection(0);
            to.setSelection(1);
        }else if(DAO.Language.equals("ja")){
            from.setSelection(2);
            to.setSelection(0);
            ttsTarget = "en";
        }else if(DAO.Language.equals("zh-CN")){
            from.setSelection(3);
            to.setSelection(0);
            ttsTarget = "en";
        }else if(DAO.Language.equals("zh-TW")){
            from.setSelection(4);
            to.setSelection(0);
            ttsTarget = "en";
        }else{
            from.setSelection(1);
            to.setSelection(0);
            ttsTarget = "en";
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setEvent(){
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        switch_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runSwitchSpinner();
            }
        });
        runTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTranslate();
            }
        });
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                if(length > 100){
                    isContextRight = false;
                    editCouter.setTextColor(getResources().getColor(R.color.red_400));
                }else{
                    isContextRight = true;
                    editCouter.setTextColor(getResources().getColor(R.color.grey_700));
                }
                editCouter.setText(length+" / 100");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        hearing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts = new TextToSpeech(TranslateActivity.this, TranslateActivity.this);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDatabase();
            }
        });
    }

    private void saveDatabase(){
        TranslateItem item = new TranslateItem();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy_MM_dd_HH_mm_sss");
        String key = dateFormat2.format(date);
        item.setT_id(key);
        item.setT_from(from.getSelectedItemPosition());
        item.setT_to(to.getSelectedItemPosition());
        item.setT_origin(content.getText().toString());
        item.setT_translate(resultTextView.getText().toString());

        DAO.handler.insert_translate(item);
        Toast.makeText(getApplicationContext(), getResources().getText(R.string.translate_func_save),
                Toast.LENGTH_SHORT).show();
    }

    private String getSpinnerText(int num){
        switch (num){
            case 0 :
                isSMT = false;
                return "ko";
            case 1:
                isSMT = false;
                return "en";
            case 2:
                isSMT = true;
                return "ja";
            case 3:
                isSMT = false;
                return "zh-CN";
            case 4:
                isSMT = false;
                return "zh-TW";
        }
        return "None";
    }

    private void runTranslate(){
        if(isSameSpinner()){
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.translate_same_Language),
                    Toast.LENGTH_SHORT).show();
        }else{
            netTranslate();
        }
    }

    private void netTranslate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String fromLan = getSpinnerText(from.getSelectedItemPosition());
                String toLan = getSpinnerText(to.getSelectedItemPosition());
                ttsTarget = toLan;
                Log.d(TAG, "From : " + fromLan + " / To : " + toLan);
                if((fromLan.equals("None")) || (toLan.equals("None"))){
                    Log.e(TAG, "Error Check from, to Language");
                }else{
                    if(isContextRight){
                        String res = "";
                        if(isSMT){
                            res = new PapagoSMT(TranslateActivity.this, fromLan, toLan,
                                    content.getText().toString()).send();
                        }else{
                            res = new PapagoNMT(TranslateActivity.this, fromLan, toLan,
                                    content.getText().toString()).send();
                        }

                        Log.d(TAG, "RES : " + res);

                        if(res.equals("none")){
                            res = getResources().getString(R.string.translate_same_Language);
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hearing.setEnabled(true);
                                }
                            });
                        }

                        Message msg = handler.obtainMessage();
                        msg.what = 1001;
                        msg.obj = res;
                        handler.sendMessage(msg);
                    }

                }
            }
        }).start();
    }


    @Override
    public void onInit(int status) {
        int result = 0;
        if(status == TextToSpeech.SUCCESS){
            if(ttsTarget.equals("ko")){
                result= tts.setLanguage(Locale.KOREA);
            }else if(ttsTarget.equals("en")){
                result= tts.setLanguage(Locale.ENGLISH);
            }else if(ttsTarget.equals("ja")){
                result= tts.setLanguage(Locale.JAPANESE);
            }else if(ttsTarget.equals("zh-CN")){
                result= tts.setLanguage(Locale.SIMPLIFIED_CHINESE);
            }else if(ttsTarget.equals("zh-TW")){
                result= tts.setLanguage(Locale.TRADITIONAL_CHINESE);
            }

            if(result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e(TAG, "Language is not Available");
            }else{
                runTTS();
            }
        }else{
            Log.e(TAG, "TTS is Not init()");
        }
    }

    @Override
    protected void onDestroy() {
        if(tts != null){
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if(tts != null){
            tts.stop();
        }
        super.onPause();
    }

    private void runTTS(){
        String target = resultTextView.getText().toString();
        Log.d(TAG, "Target : " + target);
        tts.speak(target, TextToSpeech.QUEUE_ADD, null);
    }
}
