package com.lifelineconnect.m8thubadmin.Utils;

import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rana on 11/16/2017.
 */
public class Settings {
    public static boolean Login=false;
    public static boolean isTerms = false;
    public static boolean splash=false;
    public static final String INSTANCE_NAME = "com.m8thhub";
    public static String  token = "";
    public static String  id = "";
    public static String  name = "";
    public static String  emaillog = "";
    public static String  jid = "";
    public static String  is_admin = "";
    public static String mobileno = "";
    public static String prefixreg = "+1";
    public static String countryreg = "US";
    public static String currentimage = "";

    public static int IMAGE_SIZE = 600;
    public interface Prefs {


        public static String Id = "com.m8thhub.Id";
        public static String Name = "com.m8thhub.Name";
        public static String Email = "com.m8thhub.Email";
        public static String Jid = "com.m8thhub.Jid";
        public static String Is_admin = "com.m8thhub.Is_admin";
        public static String Token = "com.m8thhub.Token";
        public static String IScurrentmobileno = "com.m8thhub.IScurrentmobileno";
        public static String IScurrentimage = "com.m8thhub.IScurrentimage";
        public static String IScurrentemail = "com.m8thhub.IScurrentemail";
        public static String IScurrentpassword = "com.m8thhub.IScurrentpassword";
        public static String IScurrentfirstname = "com.m8thhub.IScurrentfirstname";
        public static String IScurrentlastname = "com.m8thhub.IScurrentlastname";
        public static String Is_approved_publisher = "com.m8thhub.Is_approved_publisher";
        public static String Is_avatar = "com.m8thhub.Is_avatar";
        public static String  HASHSET = "com.m8thhub.HASHSET";
    }



}
