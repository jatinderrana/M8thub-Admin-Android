package com.lifelineconnect.m8thubadmin.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.lifelineconnect.m8thubadmin.Login;
import com.lifelineconnect.m8thubadmin.MainActivity;
import com.lifelineconnect.m8thubadmin.Pojo.LoginPojo;
import com.lifelineconnect.m8thubadmin.Pojo.UserLogin;
import com.lifelineconnect.m8thubadmin.R;
import com.lifelineconnect.m8thubadmin.Utils.MyApolloClients;
import com.lifelineconnect.m8thubadmin.Utils.Settings;
import com.lifelineconnect.m8thubadmin.Utils.Utils;
import com.lifelineconnect.m8thubadmin.adapter.FeedsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

/**
 * Created by ${Mukesh} on ${Oct-2017}.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    EditText email, passwordTxt;
    LinearLayout login_button, cancel_button;
    public static String TAG = "LoginFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lay_login, container, false);
        Settings.splash = true;
        email = v.findViewById(R.id.email);
        passwordTxt = v.findViewById(R.id.password);
        login_button = v.findViewById(R.id.login_button);
        cancel_button = v.findViewById(R.id.cancel_button);
        login_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        cancel_button.performClick();
                        return true;
                    }
                }
                return false;
            }
        });

        return v;
    }

    public static LoginFragment newInstance(String text) {

        LoginFragment f = new LoginFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:

                if (email.getText().toString() != null && !Utils.isEmailValid(email.getText().toString())) {
                    Utils.Alertdialog(getActivity(), "Please enter valid email id.");
                } else {
                    if (!passwordTxt.getText().toString().equalsIgnoreCase("")) {


                        //----------Api hit for user login---------
                        Login(getActivity(), email.getText().toString(), passwordTxt.getText().toString());
                    } else {
                        Utils.Alertdialog(getActivity(), "Incorrect Password");
                        //Toast.makeText(getActivity(),"Incorrect Password",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.cancel_button:
                FragmentTransaction ft;
                Fragment mFragment = new ScreenFirstFragment();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
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


    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }



        /*New Graphql Apollo Login API*/
    public void Login(final Context context, String email, final String password){
//        MyApolloClients.getApolloClient().query(
//                Login.builder().build()).enqueue(new ApolloCall.Callback<Login.Data>() {
//                    @Override
//
//        });

//        MyApolloClients.getApolloClient().mutate(Login.builder()).enqueue(new ApolloCall.Callback<Login.Data>() {
//        });
        ProgressDialog pd = Utils.showprogressdialog(context);

        MyApolloClients.getApolloClient(context).mutate(Login.builder()
                .email(email)       //satchin.joshi@gmail.com
                .password(password)       //R4swe\4QE
                .build())
                .enqueue(new ApolloCall.Callback<Login.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<Login.Data> response) {
                        //Token = response.data().login().token();
                        Log.d("respose success >>", "respose success---"+response);
                        try {
                            boolean isAdmin = response.data().login().user().isAdmin();
                            if(isAdmin) {
                                String id = response.data().login().user().id();
                                String name = response.data().login().user().firstName() + " " + response.data().login().user().lastName();
                                String email = response.data().login().user().email();
                                String jid = response.data().login().user().jid();
                                String is_admin = response.data().login().user().__typename();
                                String mobile = response.data().login().user().mobile();

                                UserLogin userobj = new UserLogin();
                                userobj.setId(id);
                                userobj.setName(name);
                                userobj.setEmail(email);
                                userobj.setJid(jid);
                                userobj.setIs_admin(is_admin);
                                userobj.setMobile(mobile);

                                LoginPojo login = new LoginPojo();
                                login.setToken(response.data().login().token());
                                login.setUserLogin(userobj);

                                String token = login.getToken();
                                Settings.Login = true;
                                Utils.setlogininfo(getActivity(), userobj, token);
                                Utils.getlogininfo(getActivity());

                                Utils.hideprogressdialog(pd);
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            }else{
                                Utils.hideprogressdialog(pd);
                                ((Activity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context,"You are not authorized to use this app",Toast.LENGTH_LONG).show();
                                    }
                                });

                            }


                        }catch (NullPointerException e){
                            Log.d("respose fail >>", "respose exc---"+ String.valueOf(e));
                        }
                    }
                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.d("respose fail >>", "respose exc---"+ String.valueOf(e));
                        Utils.hideprogressdialog(pd);
                    }
                });
    }



}
