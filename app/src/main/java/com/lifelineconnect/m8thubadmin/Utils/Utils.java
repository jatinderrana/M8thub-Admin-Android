package com.lifelineconnect.m8thubadmin.Utils;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lifelineconnect.m8thubadmin.DB.DB_Countrylist;
import com.lifelineconnect.m8thubadmin.Pojo.RegisterPojo;
import com.lifelineconnect.m8thubadmin.Pojo.UserLogin;
import com.lifelineconnect.m8thubadmin.R;
import com.lifelineconnect.m8thubadmin.fragments.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static Context context;
    public static String TAG = "Utils";

    public static void showlog(String tag,String comment)
    {
        Log.e(tag,comment);
    }
    public static void setInfo(Context context, RegisterPojo register) {
        //Settings.currentimage = prefix;
        SharedPreferences pref = context.getSharedPreferences(
                Settings.INSTANCE_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();

        if(register.getMobile()!=null)
            editor.putString(Settings.Prefs.IScurrentmobileno, register.getMobile());
        if(register.getEmail()!=null)
            editor.putString(Settings.Prefs.IScurrentemail, register.getEmail());
        if(register.getFirst_name()!=null)
            editor.putString(Settings.Prefs.IScurrentfirstname, register.getFirst_name());
        if(register.getLast_name()!=null)
            editor.putString(Settings.Prefs.IScurrentlastname, register.getLast_name());
        if(register.getId()!=null)
            editor.putString(Settings.Prefs.Id, register.getId());
        if(register.getJid()!=null)
            editor.putString(Settings.Prefs.Jid, register.getJid());
        if(register.getLast_name()!=null)
            editor.putString(Settings.Prefs.IScurrentpassword, register.getLast_name());
        if(register.getIs_admin()!=null)
            editor.putString(Settings.Prefs.Is_admin, register.getIs_admin());
        if(register.getIs_approved_publisher()!=null)
            editor.putString(Settings.Prefs.Is_approved_publisher, register.getIs_approved_publisher());
        if(register.getAvatar()!=null)
            editor.putString(Settings.Prefs.Is_avatar, register.getAvatar());

        editor.commit();
    }

    public static String getUserXMPPJid()
    {
        SharedPreferences pref = context.getSharedPreferences(
                Settings.INSTANCE_NAME, 0);
        return pref.getString(Settings.Prefs.Jid, "");
    }
    public static void setlogininfo(Context context, UserLogin userLogin, String token)
    {
        //Settings.currentimage = prefix;
        SharedPreferences pref = context.getSharedPreferences(
                Settings.INSTANCE_NAME, 0);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Settings.Prefs.Token, token);
        editor.putString(Settings.Prefs.Id, userLogin.getId());
        editor.putString(Settings.Prefs.Name, userLogin.getName());
        editor.putString(Settings.Prefs.Email, userLogin.getEmail());
        editor.putString(Settings.Prefs.Jid, userLogin.getJid());
        editor.putString(Settings.Prefs.Is_admin, userLogin.getIs_admin());
        editor.putString(Settings.Prefs.IScurrentmobileno, userLogin.getMobile());
        editor.commit();
    }


    public static boolean getlogininfo(Context context) {

        SharedPreferences pref = context.getSharedPreferences(
                Settings.INSTANCE_NAME, 0);

        String token = pref.getString(Settings.Prefs.Token, "null");
        String id = pref.getString(Settings.Prefs.Id, "null");
        String name = pref.getString(Settings.Prefs.Name, "null");
        String email = pref.getString(Settings.Prefs.Email, "null");
        String jid = pref.getString(Settings.Prefs.Jid, "null");
        String is_admin = pref.getString(Settings.Prefs.Is_admin, "null");
        String mobile = pref.getString(Settings.Prefs.IScurrentmobileno, "null");

        if (!email.contains("null") && email.trim().length() > 0 && !token.contains("null") && token.trim().length() > 0) {
            Settings.token = token;
            Settings.id=id;
            Settings.name=name;
            Settings.emaillog=email;
            Settings.jid=jid;
            Settings.is_admin=is_admin;
            Settings.mobileno=mobile;
            return true;
        } else
            return false;
    }
    public static boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    public static void Alertdialog(Context context,String message){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater layout = LayoutInflater.from(context);
        View view = layout.inflate(R.layout.custom_text, null);
        LinearLayout customheader=view.findViewById(R.id.customheader);
        alertDialog.setCustomTitle(customheader);
        alertDialog.setMessage(message);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getActivity(), // Write your code here to execute after dialog closed"You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }

    public static ProgressDialog showprogressdialog(Context context)
    {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please wait...");
        pd.show();

        return pd;
    }

    public static void hideprogressdialog(ProgressDialog pd)
    {
        if(pd!=null)
        {
            pd.dismiss();

        }
    }

    public static String gettoken(Context context) {

        SharedPreferences pref = context.getSharedPreferences(
                Settings.INSTANCE_NAME, 0);

        String token = pref.getString(Settings.Prefs.Token, "null");

        return token;
    }
    public static boolean isValidPassword(final String password)
    {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static void setlisthashset(Set<String> set,Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                Settings.INSTANCE_NAME, 0);

        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet(Settings.Prefs.HASHSET, set);
        editor.apply();
    }

    public static Set<String> getlisthashset(Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                Settings.INSTANCE_NAME, 0);
        return pref.getStringSet(Settings.Prefs.HASHSET, null);
    }

    public  static void getCountrylist(Context context) {

        Utils.showlog("Data","basecheck");
        DB_Countrylist cl = null;
        List<String> counrty_name = null, country_img = null,country_prefix = null, country = null,aplha_code=null;
        Cursor testdata = null;
        // DataBaseHelper mDbHelper = new DataBaseHelper(context);
        try {
            cl = new DB_Countrylist(context);

            cl.CreateDataBase();
            cl.open();
            counrty_name = new ArrayList<String>();
            country_img = new ArrayList<String>();
            country_prefix = new ArrayList<String>();

            aplha_code= new ArrayList<String>();
            testdata = cl.getCountrylist();
            StringBuilder sb;
            int i = 0;
            Utils.showlog("getcountry", "size of i" + i);
            if (testdata.moveToFirst()) {
                do {
                    sb = new StringBuilder();
                    String s1 = DB_Countrylist.GetColumnValue(testdata,
                            "countryname");
                    String s2 = DB_Countrylist.GetColumnValue(testdata,
                            "countryprefix");

                    String s3 = DB_Countrylist.GetColumnValue(testdata,
                            "alpha2code");

                    Utils.showlog("getcountry", "size of s3-------"+s3);
                    String img = DB_Countrylist
                            .GetColumnValue(testdata, "images").trim()
                            .toLowerCase().replaceAll(".png", "");
                    sb.append(s1).append("(").append(s2).append(")");
                    // Utils.showlog("getcountry log", "-->"+sb.toString().trim());
                    HashMap<String,Object> tempMap=new HashMap<String,Object>();

                    tempMap.put("cName",sb.toString());
                    tempMap.put("name",s1);
                    tempMap.put("cPrefix",s2);
                    tempMap.put("images",img);
                    tempMap.put("alpha",s3);

                    Country.db_list_country.add(tempMap);
                    Utils.showlog("Value","country--------value----->>>>>"+Country.db_list_country.size());
                    counrty_name.add(sb.toString().trim());
                    country_prefix.add(s2.toString().trim());
                    aplha_code.add(s3.toString().trim());
                    if (img.equalsIgnoreCase("do")) {
                        country_img.add(img.replace("do", "d0").trim());
                    } else {
                        country_img.add(img);
                    }
                    // Constant_variable.temp[i] = sb.toString().trim();
                    // Constant_variable.COUNTRY_IMG[i] = img.trim();
                    // i++;
                    sb = null;
                } while (testdata.moveToNext());
            }

//            Settings.temp = counrty_name
//                    .toArray(new String[counrty_name.size()]);
//            Settings.COUNTRY_CODE = country_img.toArray(new String[country_img
//                    .size()]);
//            Settings.COUNTRY_PREFX = country_prefix.toArray(new String[country_prefix
//                    .size()]);
//            Settings.COUNTRY_alpha = aplha_code.toArray(new String[aplha_code
//                    .size()]);
//
//            Utils.showlog("getcountry", "size of iaaaaa"+Settings.COUNTRY_alpha);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (testdata != null) {
                testdata.close();
                testdata = null;
            }
            if (cl != null) {
                cl.close();
                cl = null;
            }
            //setadapter(context);
            counrty_name = null;
            country_img = null;
        }

        Utils.showlog("getcountry", "size of i");
    }
}
