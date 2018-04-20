package com.lifelineconnect.m8thubadmin.Utils;

import android.widget.EditText;

/**
 * Created by jaspreet on 1/10/18.
 */

public class EditTextValidation {
    public static boolean isValidate(EditText et) {
        if(et.getText().toString().trim().equals("")){
            return false;
        }
    return true;
    }
}
