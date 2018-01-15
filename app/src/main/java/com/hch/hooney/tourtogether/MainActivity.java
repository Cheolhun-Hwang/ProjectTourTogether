package com.hch.hooney.tourtogether;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

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

        if(!(init())){
            Log.e(TAG, "Init Method Error....");
        }
        if(!(event())){
            Log.e(TAG, "Event Method Error....");
        }
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
