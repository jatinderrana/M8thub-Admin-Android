package com.lifelineconnect.m8thubadmin.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.lifelineconnect.m8thubadmin.GetAfterbroadcast;
import com.lifelineconnect.m8thubadmin.GetbroadcastbyId;
import com.lifelineconnect.m8thubadmin.MainActivity;
import com.lifelineconnect.m8thubadmin.Pojo.ChannelPojo;
import com.lifelineconnect.m8thubadmin.R;
import com.lifelineconnect.m8thubadmin.Utils.MyApolloClients;
import com.lifelineconnect.m8thubadmin.adapter.FeedAfterAdapter;
import com.lifelineconnect.m8thubadmin.adapter.FeedsAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Channel#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Channel extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String TAG = "Channel";
    public static ArrayList<ChannelPojo> channpojo;
    public static List<GetbroadcastbyId.BroadcastGet> feeds;
    public static List<GetAfterbroadcast.BroadcastGet> Afterfeeds;
   // public static

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static FrameLayout aboveframe;
    public static TextView finishtext;
    public static ImageView poster;
    public static Button closebutton;


    private OnFragmentInteractionListener mListener;

    private static RecyclerView recyclerView;
    private static ProgressBar progressbar;


    private boolean isMute_popup = false;
    private boolean isAdd_popup = false;
    private boolean isMenu_popup = false;
    private Spinner spin_show_subs_channels, spin_channel_update, spin_msg_mentions, spin_msg_conversations;

    private String strSubChannel, strChannelUpdate, strMsg, strMentions, strSeconds, strChannelID;
    private ArrayList<String> subs_channel_list = new ArrayList<>();
    private ArrayList<String> channel_update_list = new ArrayList<>();
    private ArrayList<String> msg_conversation_list = new ArrayList<>();
    private ArrayList<String> mentions_list = new ArrayList<>();

    private static SwipeRefreshLayout mSwipeRefreshLayout;



    public Channel() {
        // Required empty public constructor
    }

    public static Channel newInstance(String param1, String param2) {
        Channel fragment = new Channel();
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
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView  = inflater.inflate(R.layout.fragment_channel, container, false);
        init(rootView);
        return rootView;

    }

    private void init(View rootView) {


        recyclerView            = (RecyclerView) rootView.findViewById(R.id.recycleView);
        progressbar  = (ProgressBar) rootView.findViewById(R.id.progressbar);


        recyclerView.setHasFixedSize(true);


        Get_Feeds(getContext());



        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Tag_channel","onattach");
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
         default:
             break;
        }
    }




    @Override
    public void onRefresh() {
        Get_Feeds(getContext());
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    private static void Get_Feeds(Context context) {
        if(progressbar!=null)
        progressbar.setVisibility(View.VISIBLE);
        MyApolloClients.getApolloClient(context).query(GetbroadcastbyId
                .builder()
                .build())
                .enqueue(new ApolloCall.Callback<GetbroadcastbyId.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<GetbroadcastbyId.Data> response) {
                        Log.d("resp feed >", response.data().toString());

                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                feeds = new ArrayList<>();
                                feeds = response.data().broadcastGet();
                                Log.d("resp feed > 22", feeds.toString());
                               // Log.d("tag_respo_siz", String.valueOf(Channel.channpojo.size()));
//                                if(Channel.channpojo.size() == 0){
//                                    tv_emptyMsg.setVisibility(View.VISIBLE);
//                                    recyclerView.setVisibility(View.GONE);
//                                }
                                recyclerView.setAdapter(null);
                                LinearLayoutManager layoutManager= new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

                                final FeedsAdapter customAdapter = new FeedsAdapter(context, feeds);
                                recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
                                customAdapter.notifyDataSetChanged();





                                if(progressbar!=null)
                                progressbar.setVisibility(View.GONE);
                                try {
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }catch (NullPointerException e){}
                            }
                        });

                    }
                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.d("resp er userCH", e.toString());
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(progressbar!=null)
                                progressbar.setVisibility(View.GONE);

                                try {
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }catch (NullPointerException e){}
                            }
                        });

                    }
                });



    }

    public static void GetAfterFeedData(Context context, String id){
        if(progressbar!=null)
            progressbar.setVisibility(View.VISIBLE);
        MyApolloClients.getApolloClient(context).query(GetAfterbroadcast
                .builder()
                .after(Integer.parseInt(id))
                .build()).enqueue(new ApolloCall.Callback<GetAfterbroadcast.Data>() {
            @Override
            public void onResponse(@Nonnull Response<GetAfterbroadcast.Data> response) {

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Afterfeeds = new ArrayList<>();
                        Afterfeeds = response.data().broadcastGet();
                        Log.d("respo after", String.valueOf(response.data().broadcastGet()) +" <<");
                        recyclerView.setAdapter(null);
                        LinearLayoutManager layoutManager= new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView

                        final FeedAfterAdapter customAdapter = new FeedAfterAdapter(context, response.data().broadcastGet());
                        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
                        customAdapter.notifyDataSetChanged();
                        if(progressbar!=null)
                            progressbar.setVisibility(View.GONE);



                    }
                });
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(progressbar!=null)
                            progressbar.setVisibility(View.GONE);

                    }
                });
            }
        });
    }





}
