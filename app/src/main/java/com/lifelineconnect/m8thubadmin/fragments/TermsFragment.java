package com.lifelineconnect.m8thubadmin.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lifelineconnect.m8thubadmin.R;
import com.lifelineconnect.m8thubadmin.Utils.Settings;


/**
 * Created by ${Mukesh} on ${Oct-2017}.
 */

public class TermsFragment extends Fragment implements View.OnClickListener{
    public static Dialog dialog;
    LinearLayout back_key;
    WebView webView;
    private TextView tv_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lay_terms, container, false);
        webView=v.findViewById(R.id.webview);
        back_key=v.findViewById(R.id.back_key);
        tv_name = v.findViewById(R.id.tv_name);

        if(Settings.isTerms){
            tv_name.setText("Terms of Service");
            webView.loadUrl("file:///android_asset/m8thub_terms.html");
        }else{
            tv_name.setText("Privacy Policies");
            webView.loadUrl("file:///android_asset/m8thub_privacypolicy.html");
        }


        back_key.setOnClickListener(this);

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        back_key.performClick();
                        return true;
                    }
                }
                return false;
            }
        });
        return v;
    }
    public static TermsFragment newInstance(String text) {

        TermsFragment f = new TermsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_key:
                getActivity().getSupportFragmentManager().popBackStack();

                break;
            default:
                break;
        }
    }
}
