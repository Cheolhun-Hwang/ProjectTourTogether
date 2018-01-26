package com.hch.hooney.tourtogether;

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
    private TextView mTextMessage;
    private BottomNavigationView navigation;

    //variable
    private FragmentManager manager;

    private Thread loadDateToServer;
    private Handler handler;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(new HomeFragment());
                    return true;
                case R.id.navigation_search:
                    switchFragment(new SearchFragment());
                    return true;
                case R.id.navigation_course:
                    switchFragment(new CourseFragment());
                    return true;
                case R.id.navigation_account:
                    switchFragment(new AccountFragment());
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
                USER user = new USER();
                user.setUID("0000");
                user.setUNAME("Honney");
                user.setUCOUNTRY("KR");
                user.setUPROFILEIMAGE("https://yt3.ggpht.com/-iLYgyUzcTzQ/AAAAAAAAAAI/AAAAAAAAAAA/_N59TIbtHlI/s108-c-k-no-mo-rj-c0xffffff/photo.jpg");
                user.setUUID("qewqsa");
                DAO.setUser(user);

                DAO.setCountryAndLanguage(getApplicationContext());
                DAO.init_mainPostList();
                DAO.init_bookmarkSpot();
                DAO.init_bookmarkRoute();
                DAO.init_myCourse();
                DAO.loadData_mainPostList();

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

            //variable
            manager = this.getSupportFragmentManager();

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

}
