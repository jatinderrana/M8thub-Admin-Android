package com.lifelineconnect.m8thubadmin.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.lifelineconnect.m8thubadmin.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by ${Mukesh} on ${Oct-2017}.
 */

public class ScreenFirstFragment extends Fragment implements View.OnClickListener {
    LinearLayout buttonlogin,buttonregister;
    public static String TAG = "ScreenFirstFragment";
    int PERMISSION_ALL = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lay_screenfirst, container, false);
        buttonlogin= v.findViewById(R.id.buttonlogin);
        buttonregister= v.findViewById(R.id.buttonregister);
        buttonlogin.setOnClickListener(this);
        buttonregister.setOnClickListener(this);



        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE};

        if(!hasPermissions(getActivity(), PERMISSIONS))
        {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }



        return v;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public static ScreenFirstFragment newInstance(String text)

    {

        ScreenFirstFragment f = new ScreenFirstFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonlogin:
                FragmentTransaction ft;
                Fragment mFragment=new LoginFragment();
                ft  = getActivity().getSupportFragmentManager().beginTransaction();
               //ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                //ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                ft.replace(android.R.id.content, mFragment);
                try {
                    ft.commit();
                } catch (IllegalStateException ignored) {

                }

                break;
            case R.id.buttonregister:
                mFragment = new RegisterFragment();
                ft  = getActivity().getSupportFragmentManager().beginTransaction();
               //ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                ft.replace(android.R.id.content, mFragment);
                try {
                    ft.commit();
                } catch (IllegalStateException ignored) {

                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {


        super.onStart();
    }

}
