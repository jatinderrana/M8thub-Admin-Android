package com.lifelineconnect.m8thubadmin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;


import com.lifelineconnect.m8thubadmin.Utils.Settings;
import com.lifelineconnect.m8thubadmin.Utils.Utils;
import com.lifelineconnect.m8thubadmin.fragments.ScreenFirstFragment;
import com.lifelineconnect.m8thubadmin.fragments.SplashFragment;

/**
 * Created by android on 9/8/2017.
 */

public class Helper_fragment extends FragmentActivity
{
    public static Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction ft;
        Fragment mFragment=new SplashFragment();
        ft  = getSupportFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, mFragment);
        try {
            ft.commit();
        } catch (IllegalStateException ignored) {

        }

    }

    @Override
    public void onStart() {
        registerviews();
        super.onStart();

    }
    public void registerviews(){
        if (!Settings.splash){
            if (Utils.getlogininfo(Helper_fragment.this))
            {
                Thread2secsMainactivity();
            }else {
                Thread2secs();
            }

        }

    }


    public  void Thread2secs(){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction ft;
                Fragment mFragment=new ScreenFirstFragment();
                ft  = getSupportFragmentManager().beginTransaction();
                //ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                ft.replace(android.R.id.content, mFragment);
                try {
                    ft.commit();
                } catch (IllegalStateException ignored) {

                }
            }
        }, 2000);
    }

    public  void Thread2secsMainactivity(){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Utils.getlogininfo(Helper_fragment.this) )
                {
                    Intent intent=new Intent(Helper_fragment.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    FragmentTransaction ft;
                    Fragment mFragment=new ScreenFirstFragment();
                    ft  = getSupportFragmentManager().beginTransaction();
                    //ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                    ft.replace(android.R.id.content, mFragment);
                    try {
                        ft.commit();
                    } catch (IllegalStateException ignored) {

                    }
                }
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
               super.onResume();
    }


    @Override
    protected void onPause() {
       super.onPause();
    }






}
