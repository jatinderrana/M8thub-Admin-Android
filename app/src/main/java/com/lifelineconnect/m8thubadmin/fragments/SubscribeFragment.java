package com.lifelineconnect.m8thubadmin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.lifelineconnect.m8thubadmin.MainActivity;
import com.lifelineconnect.m8thubadmin.R;
import com.lifelineconnect.m8thubadmin.Utils.MyApolloClients;
import com.lifelineconnect.m8thubadmin.Utils.TestContent;
import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5Stream;
import com.red5pro.streaming.R5StreamProtocol;
import com.red5pro.streaming.config.R5Configuration;
import com.red5pro.streaming.event.R5ConnectionEvent;
import com.red5pro.streaming.event.R5ConnectionListener;
import com.red5pro.streaming.view.R5VideoView;

import javax.annotation.Nonnull;


public class SubscribeFragment extends Fragment implements R5ConnectionListener {
    public R5Configuration configuration;

    protected boolean isSubscribing = false;
    protected R5Stream stream;
    String strtitle,title;
    TextView tv_Ch_name;

    LinearLayout btn_back;

    public static SubscribeFragment newInstance() {
        SubscribeFragment fragment = new SubscribeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SubscribeFragment() {
        // Required empty public constructor
    }

    private int m_interval = 1000;
    private Handler m_handler;
    int lastpostionofseekbar=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            strtitle = getArguments().getString("strtitle");
            title = getArguments().getString("title");
        }

        configuration = new R5Configuration(R5StreamProtocol.RTSP, TestContent.GetPropertyString("host"),  TestContent.GetPropertyInt("port"), TestContent.GetPropertyString("context"), TestContent.GetPropertyFloat("buffer_time"));
        configuration.setLicenseKey(TestContent.GetPropertyString("license_key"));
        configuration.setBundleID(getActivity().getPackageName());

        m_handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_subscribe, container, false);

        btn_back        = (LinearLayout) v.findViewById(R.id.btn_back);
        tv_Ch_name      = (TextView) v.findViewById(R.id.tv_Ch_name);
        if(title!=null)
        tv_Ch_name.setText(title);
        else
         tv_Ch_name.setText("");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               stop();
                stopRepeatingTask();
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onSubscribeToggle();

//        Button publishButton = (Button) getActivity().findViewById(R.id.subscribeButton);
//        publishButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onSubscribeToggle();
//            }
//        });
    }
    private void onSubscribeToggle() {
        //Button subscribeButton = (Button) getActivity().findViewById(R.id.subscribeButton);
//        if(isSubscribing) {
//          // stop();
//        }
//        else {
            start();
//        }
//        isSubscribing = !isSubscribing;
//        subscribeButton.setText(isSubscribing ? "stop" : "start");
    }

    public void start() {
        R5VideoView videoView = (R5VideoView) getActivity().findViewById(R.id.subscribeView);
        R5Connection connection = new R5Connection(configuration);
        stream = new R5Stream(connection);

        stream.setListener(this);
        videoView.attachStream(stream);
        stream.play(strtitle);


    }

    @Override
    public void onDestroy() {
//        if(lastpostionofseekbar>0)
//        createBroadcastViwe(getActivity());

        stopRepeatingTask();
        super.onDestroy();
    }

    public void stop() {
        if(stream != null) {

            stream.stop();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isSubscribing) {
            onSubscribeToggle();
        }
    }

    @Override
    public void onConnectionEvent(R5ConnectionEvent r5ConnectionEvent) {
        Log.d("Subscribe", "Subscribe:onConnectionEvent " + r5ConnectionEvent);
        switch (r5ConnectionEvent)
        {
            case START_STREAMING:
                startRepeatingTask();
//                if(stream!=null && stream.getVideoSource()!=null)
//                Log.e("r5ConnectionEvent----","r5ConnectionEvent----START_STREAMING---"+stream.getVideoSource().getFrameSize()+"---"+stream.getVideoSource().getFramerate());
                 break;
         case STOP_STREAMING:
//                if(stream!=null && stream.getVideoSource()!=null)
//                    Log.e("r5ConnectionEvent----","r5ConnectionEvent----STOP_STREAMING---"+stream.getVideoSource().getFrameSize()+"---"+stream.getVideoSource().getFramerate());
            break;
            case DISCONNECTED:
                stopRepeatingTask();
                    if(stream!=null && stream.getVideoSource()!=null)
                 Log.e("r5ConnectionEvent----","r5ConnectionEvent----DISCONNECTED---"+stream.getVideoSource().getFrameSize()+"---"+stream.getVideoSource().getFramerate());

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(Global.getInstance(), "live broadcast stop.", Toast.LENGTH_SHORT).show();
//                    }
//                });

                break;
        }


    }

    Runnable m_statusChecker = new Runnable()
    {
        @Override
        public void run() {

            lastpostionofseekbar =lastpostionofseekbar+1;

            m_handler.postDelayed(m_statusChecker, m_interval);
        }
    };

    public void startRepeatingTask()
    {
        m_statusChecker.run();
    }

    public void stopRepeatingTask()
    {

        m_handler.removeCallbacks(m_statusChecker);
    }


//    private void createBroadcastViwe(Context context)
//    {
//        String broadid = "";
//        if(MainActivity.curAdapter.equals("FeedsAdapter")){
//            broadid =Channel.feeds.get(MainActivity.POS).broadcast().id();
//        }else if(MainActivity.curAdapter.equals("FeedsAfterAdapter")){
//            broadid =Channel.feeds.get(MainActivity.POS).broadcast().id();
//        }
//
//        Log.e("createBroadcastViwe----","createBroadcastViwe----"+broadid+"---"+lastpostionofseekbar+"---"+0+"---"+lastpostionofseekbar);
//        MyApolloClients.getApolloClient(context).mutate(BroadCastView.builder()
//                .broadcastId(broadid)
//                .viewDuration(lastpostionofseekbar)
//                .start(0)
//                .end(lastpostionofseekbar).build()).enqueue(new ApolloCall.Callback<BroadCastView.Data>() {
//            @Override
//            public void onResponse(@Nonnull Response<BroadCastView.Data> response) {
//                Log.e("createBroadcastViwe----", "createBroadcastViwe----response---"+response.data().toString());
//
//            }
//
//            @Override
//            public void onFailure(@Nonnull ApolloException e) {
//                Log.e("createBroadcastViwe----", "createBroadcastViwe----ApolloException---"+e.toString());
//
//            }
//        });
//
//    }
}
