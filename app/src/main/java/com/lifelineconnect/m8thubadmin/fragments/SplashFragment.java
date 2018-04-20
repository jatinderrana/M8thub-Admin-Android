package com.lifelineconnect.m8thubadmin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifelineconnect.m8thubadmin.R;


/**
 * Created by ${Mukesh} on ${Oct-2017}.
 */

public class SplashFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lay_splash, container, false);

      //  getActivity().startService(new Intent(getActivity().getBaseContext(), OnClearFromRecentService.class));
        return v;
    }
    public static SplashFragment newInstance(String text) {

        SplashFragment f = new SplashFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);


        return f;
    }


}
