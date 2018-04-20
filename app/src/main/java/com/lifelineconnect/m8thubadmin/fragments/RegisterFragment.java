package com.lifelineconnect.m8thubadmin.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.lifelineconnect.m8thubadmin.Helper_fragment;
import com.lifelineconnect.m8thubadmin.Pojo.RegisterPojo;
import com.lifelineconnect.m8thubadmin.R;
import com.lifelineconnect.m8thubadmin.SignUpUser;
import com.lifelineconnect.m8thubadmin.Utils.MyApolloClients;
import com.lifelineconnect.m8thubadmin.Utils.Settings;
import com.lifelineconnect.m8thubadmin.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nonnull;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * Created by ${Mukesh} on ${Oct-2017}.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{
    public static String TAG = "RegisterFragment";
    FragmentTransaction ft;
    public static Dialog dialog;
    EditText email,password,mobileno,firstname,lastname;
    TextView prefix, btn_term,btn_privacy_policy;
    LinearLayout register_button,cancel_button;
    ImageView cameradialog,imagepic;
    private CheckBox chkbox;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    public static String imageEncoded;
    String ip ="", unique_id="";
    public static ArrayList<HashMap<String,Object>> JournalList  = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lay_register, container, false);
        Settings.splash=true;
        cameradialog=v.findViewById(R.id.cameradialog);
        imagepic=v.findViewById(R.id.imagepic);
        email=v.findViewById(R.id.email);
        password=v.findViewById(R.id.password);
        mobileno=v.findViewById(R.id.mobileno);
        firstname=v.findViewById(R.id.firstname);
        lastname=v.findViewById(R.id.lastname);
        prefix=v.findViewById(R.id.prefix);
        register_button=v.findViewById(R.id.register_button);
        cancel_button=v.findViewById(R.id.cancel_button);

        btn_privacy_policy  = v.findViewById(R.id.btn_privacy_policy);
        btn_term            = v.findViewById(R.id.btn_term);
        chkbox              = v.findViewById(R.id.chkbox);

        btn_privacy_policy.setOnClickListener(this);
        btn_term.setOnClickListener(this);
        register_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        cameradialog.setOnClickListener(this);
        imagepic.setOnClickListener(this);
        prefix.setOnClickListener(this);

        prefix.setText(Settings.prefixreg);



        if (getImage(getActivity())){
            if (Settings.currentimage!=null && Settings.currentimage.length()>0){
                byte[] decodedString = Base64.decode(Settings.currentimage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                Bitmap scaled = Bitmap.createScaledBitmap(decodedByte, 75, 75, true);
                imagepic.setImageBitmap(scaled);
            }
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                if (!mobileno.getText().toString().equalsIgnoreCase("")&& mobileno.getText().toString().length()>6&& mobileno.getText().toString().length()<15 && !firstname.getText().toString().equalsIgnoreCase("") &&  !lastname.getText().toString().equalsIgnoreCase("") /*&& !password.getText().toString().equalsIgnoreCase("") && password.getText().toString().length()>3*/){

                    Log.e("RegisterFragment","<<<<Register_Fragment__password>>>"+password.getText().toString());

                    if (!Utils.isEmailValid(email.getText().toString()))
                    {
                        Alertdialog("Please enter valid email id.");
                    }
                    else if(password.getText().toString().length()<8)
                    {
                        Alertdialog("Password length should not be less than 8");
                    }
                    else if(!Utils.isValidPassword(password.getText().toString()))
                    {
                        Alertdialog("Please enter combinational password contain atleast capital alphabet, numeric and special character");
                    }else if(!chkbox.isChecked()){
                        Alertdialog("Please Accept the terms and privacy policies.");
                    }
                    else {
                        //----------Api hit for user register---------
                        RegisterNewUser(getActivity(),prefix.getText().toString(),mobileno.getText().toString(),email.getText().toString(),password.getText().toString(),firstname.getText().toString(),lastname.getText().toString());

                    }


                }
                else {
                    Alertdialog("Please fill required details.");
                }


                break;
            case R.id.cancel_button:
                Fragment mFragment = new ScreenFirstFragment();
                ft  = getActivity().getSupportFragmentManager().beginTransaction();
               //ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                ft.replace(android.R.id.content, mFragment);
                try {
                    ft.commit();
                } catch (IllegalStateException ignored) {

                }
                break;
            case R.id.cameradialog:
                diaLogImage(getActivity());
                break;
            case R.id.imagepic:
                //diaLogImage(getActivity());
                break;
            case R.id.prefix:

                Fragment mFragment1 = new Country();
                ft  = getActivity().getSupportFragmentManager().beginTransaction();
               //ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                ft.replace(android.R.id.content, mFragment1);
                try {
                    ft.commit();
                } catch (IllegalStateException ignored) {

                }
                break;

            case R.id.btn_term:
                Settings.isTerms = true;


               // FragmentTransaction ft;
                mFragment = new TermsFragment();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(android.R.id.content, mFragment);
                ft.addToBackStack(null);
                try {
                    ft.commit();
                } catch (IllegalStateException ignored) {}

//                mFragment=new TermsFragment();
//                ft  = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(android.R.id.content, mFragment);
//                try {
//                    ft.commit();
//                } catch (IllegalStateException ignored) {}
                break;
            case R.id.btn_privacy_policy:
                Settings.isTerms = false;
                mFragment = new TermsFragment();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(android.R.id.content, mFragment);
                ft.addToBackStack(null);
                try {
                    ft.commit();
                } catch (IllegalStateException ignored) {}
                break;
            default:
                break;
        }
    }

    public static RegisterFragment newInstance(String text) {

        RegisterFragment f = new RegisterFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    public void Alertdialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater layout = LayoutInflater.from(getActivity());
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


    public void diaLogImage(Context context)
    {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_camera_gallery);
        RelativeLayout relativeLayout=dialog.findViewById(R.id.topbox);
        relativeLayout.setBackgroundResource(R.drawable.transparentimg);
        TextView btnCamera=dialog.findViewById(R.id.btnCamera);
        TextView btnGallery=dialog.findViewById(R.id.btnGallery);
        TextView btnCancel=dialog.findViewById(R.id.btnCancel);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissions.add(CAMERA);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        btnCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext()
                                .getPackageName() + ".provider", f));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 1);
                        dialog.dismiss();
                    }else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                        dialog.dismiss();
                    }

                }
                catch(Exception e)
                {
                    Utils.showlog("Gallery","gallery>>>>>"+e.toString());
                }


            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {

                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                //Log.d("API123", "permisionrejected " + permissionsRejected.size());

                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if (resultCode != Activity.RESULT_CANCELED)
        {
            getActivity();
            if(resultCode== Activity.RESULT_OK)
                if (requestCode == 1) {
                    Utils.showlog("Camera","Camera>>>>>>>>");
                    File f = new File(Environment.getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }

                    try {
                        Bitmap bitmap;
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        String path = Environment
                                .getExternalStorageDirectory()
                                + File.separator
                                + "Phoenix" + File.separator + "default";
                        path = encodeTobase64(bitmap);
                        //Utils.compressImageSmall(Settings.IMAGE_PATH, "default");
                        f.delete();
                        FileOutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        try {
                            outFile = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outFile);
                            outFile.flush();
                            outFile.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (requestCode == 2) {
                    if (data.getData() != null)
                    {
                        Uri selectedImage = data.getData();
                        String[] filePath = {MediaStore.MediaColumns.DATA};
                        Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePath[0]);
                        String picturePath = c.getString(columnIndex);
                        c.close();
                        Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                        //Bitmap bmImg = BitmapFactory.decodeFile(picturePath);
                        picturePath = encodeTobase64(thumbnail);
                    }
                }
        }
    }

    public String encodeTobase64(Bitmap image)
    {
        Bitmap immagex= Bitmap.createScaledBitmap(image, 75, 75, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] b = baos.toByteArray();
        imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        byte[] decodedString = Base64.decode(imageEncoded, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Bitmap scaled = Bitmap.createScaledBitmap(decodedByte, 75, 75, true);
        imagepic.setImageBitmap(scaled);
        Utils.showlog("LOOK", imageEncoded);

        if (imageEncoded!=null && imageEncoded.length()>0){
            if (imageEncoded.length() > 0 || imageEncoded.isEmpty())
            {
                setImage(getActivity(),imageEncoded);
                }
        }

        return imageEncoded;
    }

    public static void setImage(Context context, String name) {
        Settings.currentimage = name;
        SharedPreferences pref = context.getSharedPreferences(
                Settings.INSTANCE_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Settings.Prefs.IScurrentimage, name);
        editor.commit();
    }
    public static boolean getImage(Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                Settings.INSTANCE_NAME, 0);
        String name = pref.getString(Settings.Prefs.IScurrentimage, "null");
        if (!name.contains("null") && name.trim().length() > 0) {
            Settings.currentimage = name;
            return true;
        } else
            return false;
    }



    private void RegisterNewUser(final Context context, final String prefix, final String mobileno, String email, final String password, final String firstname, final String lastname) {
        final ProgressDialog pd = Utils.showprogressdialog(getActivity());

        Log.d("register >>",prefix+","+mobileno+","+firstname+","+lastname+","+email+","+password);

        MyApolloClients.getApolloClient(context).mutate(SignUpUser.builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(password)
                .mobile(prefix+""+mobileno)
                .build()).enqueue(new ApolloCall.Callback<SignUpUser.Data>() {
            @Override
            public void onResponse(@Nonnull Response<SignUpUser.Data> response) {
                Utils.hideprogressdialog(pd);
                String id = response.data().toString();
                Log.d("success SignUP >>", String.valueOf(id));
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        if(response!=null && response.data()!=null) {
                            String id = response.data().signUp().id();
                            String first_name = firstname;
                            String last_name = lastname;
                            String email1 = email;
                            String jid = response.data().signUp().jid();
                            Boolean is_admin = response.data().signUp().isAdmin();
                            Boolean is_approved_publisher = response.data().signUp().isActive();
                            String mobile = mobileno;

                            RegisterPojo userobj = new RegisterPojo();
                            userobj.setId(id);
                            userobj.setMobile(mobile);
                            userobj.setFirst_name(first_name);
                            userobj.setLast_name(last_name);
                            userobj.setEmail(email);
                            userobj.setJid(jid);
                            userobj.setIs_admin(String.valueOf(is_admin));
                            userobj.setIs_approved_publisher(String.valueOf(is_approved_publisher));
                            userobj.setPassword(password);

                            Utils.setInfo(getActivity(), userobj);


                        }
                    }
                });


                Fragment mFragment = new LoginFragment();
                ft  = getActivity().getSupportFragmentManager().beginTransaction();
                //ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                ft.replace(android.R.id.content, mFragment);
                try {
                    ft.commit();
                } catch (IllegalStateException ignored) {

                }

                //   Toast.makeText(context, id+" Successfully Created ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@Nonnull ApolloException error) {
                Utils.hideprogressdialog(pd);
                Log.d("failure fail >>", String.valueOf(error));

                Utils.hideprogressdialog(pd);
                Utils.showlog(TAG, " onReceived error : " + error.getMessage());
                // Toast.makeText(context, " Error while creating channel ",Toast.LENGTH_SHORT).show();
            }

        });
    }


}
