package com.hch.hooney.tourtogether;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hch.hooney.tourtogether.DAO.DAO;

public class SettingActivity extends AppCompatActivity {
    private final String TAG = "SettingActivity";

    private ImageButton back;
    private SwitchCompat push;
    private Button logout;
    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
        setEvent();
    }

    private void init(){
        back = (ImageButton) findViewById(R.id.setting_back_btn);
        push = (SwitchCompat) findViewById(R.id.setting_push);
        if(DAO.isPush){
            push.setChecked(true);
        }else{
            push.setChecked(false);
        }
        logout = (Button) findViewById(R.id.setting_logout);
        version = (TextView)findViewById(R.id.setting_version);
        version.setText(DAO.nowVersion);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        savePreferences();
        finish();
    }

    private void setEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                savePreferences();
                finish();
            }
        });
        push.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DAO.isPush = true;
                }else{
                    DAO.isPush = false;
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(9008);
                savePreferences();
                finish();
            }
        });
    }

    // 값 저장하기
    private void savePreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("ispush", DAO.isPush);
        editor.commit();
    }
}
