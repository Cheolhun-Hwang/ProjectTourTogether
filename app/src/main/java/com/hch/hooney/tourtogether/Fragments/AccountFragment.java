package com.hch.hooney.tourtogether.Fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;


import com.hch.hooney.tourtogether.R;
import com.hch.hooney.tourtogether.SettingForEvent.AccountTabAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private final String TAG = "AccountFragment";

    //Layout Resource
    private ViewPager viewPager;
    private AccountTabAdapter accountTabAdapter;
    private TabLayout tabLayout;

    //variable
    private View view;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);

        init();

        return view;
    }

    private void init(){
        viewPager = (ViewPager) view.findViewById(R.id.account_viewpager);

        tabLayout = (TabLayout) view.findViewById(R.id.account_tabs);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.account_tab1)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.account_tab2)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.account_tab3)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        accountTabAdapter = new AccountTabAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(accountTabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
