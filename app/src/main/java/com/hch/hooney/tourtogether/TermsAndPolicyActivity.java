package com.hch.hooney.tourtogether;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TermsAndPolicyActivity extends AppCompatActivity {
    private final String TAG = "TermsAndPolicyActivity";
    private ImageButton back;
    private TextView context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_policy);

        init();
        setEvent();
        setUi();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void init(){
        back = (ImageButton) findViewById(R.id.term_back);
        context = (TextView) findViewById(R.id.term_context);
    }

    private void setEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUi(){
        context.setText(Html.fromHtml(getResources().getString(R.string.term_context)));
        context.setVerticalScrollBarEnabled(true);
        context.setMovementMethod(new ScrollingMovementMethod());
    }
}
