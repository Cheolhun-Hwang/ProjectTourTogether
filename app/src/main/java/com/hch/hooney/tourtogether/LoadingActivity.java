package com.hch.hooney.tourtogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.USER;

public class LoadingActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Thread loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        init();
        event();
    }

    private void init(){
        progressBar = (ProgressBar) findViewById(R.id.loading_progress);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void event(){

    }

    private Thread init_loading_thread(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DAO.setCountryAndLanguage(getApplicationContext());
                    DAO.init_static();
                    DAO.init_handler(getApplicationContext());
                    DAO.loadData_mainPostList();
                    DAO.load_bookmarkSpot();
                    DAO.load_bookmarkCourse();

                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        loading = init_loading_thread();
        loading.start();
        super.onStart();
    }

    @Override
    protected void onPause() {
        loading.interrupt();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
