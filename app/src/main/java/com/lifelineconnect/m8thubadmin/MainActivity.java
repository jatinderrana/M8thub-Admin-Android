package com.lifelineconnect.m8thubadmin;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lifelineconnect.m8thubadmin.Utils.CustomViewPager;
import com.lifelineconnect.m8thubadmin.Utils.TestContent;
import com.lifelineconnect.m8thubadmin.fragments.Channel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public static int POS;
    public static String curAdapter;
    private CustomViewPager mViewPager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                //    mTextMessage.setText(R.string.title_home);
                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = getResources();
        TestContent.LoadTests(res.openRawResource(R.raw.tests));
        load();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager = (CustomViewPager) findViewById(R.id.container1);
        //mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.disableScroll(true);

        setupViewPager(mViewPager);
    }

    private void load() {
        // Load native libraries


        try {
            System.loadLibrary("red5android");
        } catch (UnsatisfiedLinkError error) {
            Log.e("", "Error while loading red5android native library", error);
        }
        try {
            System.loadLibrary("red5streaming");
        } catch (UnsatisfiedLinkError error) {
            Log.e("", "Error while loading red5streaming native library", error);
        }

    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Channel(), "Channel");
        viewPager.setAdapter(adapter);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {



            super.setPrimaryItem(container, position, object);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {


            return mFragmentTitleList.get(position);
        }



    }
}
