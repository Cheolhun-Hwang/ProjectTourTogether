package com.hch.hooney.tourtogether;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.Suggestion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SuggetionsActivity extends AppCompatActivity {
    private final String TAG = "SuggetionsActivity";
    private Spinner spinner_reason;
    private ImageButton back;
    private Button send_reason;
    private EditText context_reason;
    private FirebaseDatabase mFiredb;
    private DatabaseReference mSuggestion;
    private ProgressDialog asyncDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggetions);

        init();
        setEvent();
    }

    private void init(){
        asyncDialog = new ProgressDialog(this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage(getResources().getString(R.string.notify_loading_data));

        mFiredb = FirebaseDatabase.getInstance();
        mSuggestion = mFiredb.getReference().child("Suggestion");

        spinner_reason = (Spinner) findViewById(R.id.suggestions_reason);
        back = (ImageButton) findViewById(R.id.suggestions_back);
        context_reason = (EditText)findViewById(R.id.suggestions_context);
        send_reason = (Button)findViewById(R.id.suggestions_send);
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

        send_reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String context = context_reason.getText().toString();
                if(context.length() < 1){
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.suggestions_reason_message),
                            Toast.LENGTH_SHORT).show();
                }else{
                    asyncDialog.show();
                    send();
                }
            }
        });
    }

    private void send(){
        Suggestion suggestion = new Suggestion();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        int auto = pref.getInt("autologin", 0);
        if(auto == 1){
            suggestion.setLogin("Google");
        }else if(auto == 2){
            suggestion.setLogin("Facebook");
        }else{
            suggestion.setLogin("Error : " + auto);
        }

        suggestion.setWContext(context_reason.getText().toString());
        suggestion.setReason(spinner_reason.getSelectedItem().toString());
        suggestion.setReasonNum(spinner_reason.getSelectedItemPosition());
        suggestion.setWCountry(DAO.Country);
        Date nowDate = new Date(System.currentTimeMillis());
        SimpleDateFormat dateSet = new SimpleDateFormat("yyyy-MM-dd hh:mm:sss");
        SimpleDateFormat dateSet2 = new SimpleDateFormat("yyyy_MM_dd_hh_mm_sss");
        suggestion.setWhen(dateSet.format(nowDate));

        mSuggestion.child(DAO.user.getUID()).child(dateSet2.format(nowDate)).setValue(suggestion);
        asyncDialog.dismiss();
        Toast.makeText(getApplicationContext(), getResources().getText(R.string.suggestions_reson_Ok),
                Toast.LENGTH_SHORT).show();
        finish();
    }
}
