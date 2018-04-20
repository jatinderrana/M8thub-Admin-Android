package com.lifelineconnect.m8thubadmin.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.lifelineconnect.m8thubadmin.Pojo.ChannelPojo;
import com.lifelineconnect.m8thubadmin.R;
import com.lifelineconnect.m8thubadmin.Utils.Settings;
import com.lifelineconnect.m8thubadmin.Utils.Utils;
import com.lifelineconnect.m8thubadmin.adapter.AdapterChannelImg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchChannelFragment extends Fragment  {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private ImageView btn_search, btn_plus, btn_menu;
    private TextView  fragment_name, tv_emptyMsg;
    private LinearLayout ll_pop_up;
    private boolean isMute_popup = false;
    private boolean isAdd_popup = false;
    private boolean isMenu_popup =false;
     static AdapterChannelImg customAdapter;
    public static String TAG = "Channel";
    static ArrayList<ChannelPojo> channpojo;
    private String strSubChannel, strChannelUpdate, strMsg, strMentions;
    private ArrayList<String> subs_channel_list = new ArrayList<>();
    private ArrayList<String> channel_update_list = new ArrayList<>();
    private ArrayList<String> msg_conversation_list = new ArrayList<>();
    private ArrayList<String> mentions_list = new ArrayList<>();
    private Spinner spin_show_subs_channels, spin_channel_update, spin_msg_mentions, spin_msg_conversations;


    public SearchChannelFragment() {
        // Required empty public constructor
    }

    public static SearchChannelFragment newInstance(String param1, String param2) {
        SearchChannelFragment fragment = new SearchChannelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rooView = inflater.inflate(R.layout.fragment_search_channel, container, false);
        init(rooView);
        return rooView;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void init(View rootView) {

        btn_search              = (ImageView) rootView.findViewById(R.id.btn_search);
        btn_menu                = (ImageView) rootView.findViewById(R.id.btn_menu);
        fragment_name           = (TextView) rootView.findViewById(R.id.fragment_name);
        tv_emptyMsg             = (TextView) rootView.findViewById(R.id.tv_emptyMsg);
        recyclerView            = (RecyclerView) rootView.findViewById(R.id.recycleView);

        recyclerView.setHasFixedSize(true);





        fragment_name.setText("Channels");
     //   btn_search.setBackgroundResource(R.mipmap.back);


        userchannel(getContext());
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView




   }

    public void userchannel(final Context context) {
        Utils.showlog(TAG, " onReceived gettoken : " + Utils.gettoken(context)+"----"+ Settings.UserChannelUrl);
        AndroidNetworking.get(Settings.UserChannelUrl)
                .addHeaders("Accept","application/json")
                .addHeaders("Authorization","Bearer "+Utils.gettoken(context))
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d("tag_responseCH", response.toString());

                        Utils.showlog(TAG, " onReceived response : channel " + response);
                        try {

                            JSONArray jsonArray = response.getJSONArray("data");
                            Log.d("tag_responseAr", jsonArray.toString());
                            Log.d("tag_respo_siz1", String.valueOf(jsonArray.length()));
                            channpojo = new ArrayList<ChannelPojo>();
                            for (int i = 0; i<jsonArray.length();i++) {

                                //JSONObject channel = Utils.getjsonobject(context, response.getString("channel").toString());
                                JSONObject channel      = jsonArray.getJSONObject(i);
                                String id               = channel.getString("id").toString().replace("null","");
                                String name             = channel.getString("name").toString().replace("null", "");
                                String rating           = channel.getString("rating").toString().replace("null", "");
                                String privacy          = channel.getString("privacy").toString().replace("null", "");
                                String partnership      = channel.getString("partnership").toString().replace("null", "");
                                String membership_fee   = channel.getString("membership_fee").toString().replace("null", "");
                                String membership       = channel.getString("membership").toString().replace("null", "");
                                String description      = channel.getString("description").toString().replace("null", "");
                                String currency         = channel.getString("currency").toString().replace("null", "");
                                String cover_image      = channel.getString("cover_image").toString().replace("null", "");
                                String avatar           = channel.getString("avatar").toString().replace("null", "");
                                String is_liked         = channel.getString("is_liked").toString().replace("null","");
                                String is_subscribed    = channel.getString("is_subscribed").toString().replace("null", "");


                                ChannelPojo channpojos = new ChannelPojo();
                                channpojos.setName(name);
                                channpojos.setRating(rating);
                                channpojos.setPrivacy(privacy);
                                channpojos.setPartnership(partnership);
                                channpojos.setMembership(membership);
                                channpojos.setMembership_fee(membership_fee);
                                channpojos.setDescription(description);
                                channpojos.setCurrency(currency);
                                channpojos.setCoverImage(cover_image);
                                channpojos.setAvatar(avatar);
                                channpojos.setId(id);
                                channpojos.setIS_Liked(is_liked);
                                channpojos.setIS_Subscribed(is_subscribed);
                                channpojos.setlikeCount(Integer.parseInt(channel.getString("like_count").toString().replace("null", "0")));
                                channpojos.setShareCount(0);
                                channpojos.setSubsCount(Integer.parseInt(channel.getString("subscriber_count").toString().replace("null", "0")));

                                channpojo.add(channpojos);
                            }

                            Log.d("tag_respo_siz", String.valueOf(channpojo.size()));
                             customAdapter = new AdapterChannelImg(getContext(), channpojo);
                            recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
                            customAdapter.notifyDataSetChanged();






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

                        tv_emptyMsg.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        Utils.showlog(TAG, " onReceived error : " + error.getMessage());
                        Utils.showlog(TAG, " onReceived error : " + error.getErrorBody());
                        Utils.showlog(TAG, " onReceived error : " + error.getErrorCode());

                    }
                });

    }






}
