package com.hch.hooney.tourtogether;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hch.hooney.tourtogether.DAO.DAO;
import com.hch.hooney.tourtogether.DAO.USER;
import com.hch.hooney.tourtogether.Fragments.AccountFragment;
import com.hch.hooney.tourtogether.Fragments.CourseFragment;
import com.hch.hooney.tourtogether.Fragments.HomeFragment;
import com.hch.hooney.tourtogether.Fragments.SearchFragment;
import com.hch.hooney.tourtogether.SettingForEvent.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    //Layout Resource
    private BottomNavigationView navigation;
    private ImageButton post_on_Map;

    //variable
    private FragmentManager manager;

    private Thread loadDateToServer;
    private Handler handler;
    private int selectTab;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(selectTab!=1){
                        selectTab=1;
                        switchFragment(new HomeFragment());
                        post_on_Map.setVisibility(View.VISIBLE);
                    }
                    return true;
                case R.id.navigation_search:
                    if(selectTab!=2) {
                        selectTab = 2;
                        switchFragment(new SearchFragment());
                        post_on_Map.setVisibility(View.GONE);
                    }
                    return true;
                case R.id.navigation_course:
                    if(selectTab!=3) {
                        selectTab = 3;
                        switchFragment(new CourseFragment());
                        post_on_Map.setVisibility(View.GONE);
                    }
                    return true;
                case R.id.navigation_account:
                    if(selectTab!=4) {
                        selectTab = 4;
                        switchFragment(new AccountFragment());
                        post_on_Map.setVisibility(View.GONE);
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what){
                    case 1001:
                        if(!(init())){
                            Log.e(TAG, "Init Method Error....");
                        }
                        if(!(event())){
                            Log.e(TAG, "Event Method Error....");
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        loadDateToServer = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = 1001;
                handler.sendMessage(msg);
            }
        });
        loadDateToServer.start();
    }

    private boolean init(){
        try{
            //resource init.
            navigation = (BottomNavigationView) findViewById(R.id.navigation);
            post_on_Map = (ImageButton) findViewById(R.id.Main_Map_BTN);

            //variable
            manager = this.getSupportFragmentManager();
            selectTab=-1;
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean event(){
        try{
            //navigation setting.
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            //navigation remove default event.
            BottomNavigationViewHelper.removeShiftMode(navigation);
            //navigation set select 'home'.
            navigation.setSelectedItemId(R.id.navigation_home);

            post_on_Map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PostMapActivity.class);
                    intent.putExtra("func", "none");
                    startActivity(intent);
                }
            });

            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void switchFragment(Fragment fragment){
        try{
            manager.beginTransaction().replace(R.id.content, fragment).commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        DAO.fUser = DAO.mAuth.getCurrentUser();
    }
}
