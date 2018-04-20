package com.lifelineconnect.m8thubadmin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.lifelineconnect.m8thubadmin.MainActivity;
import com.lifelineconnect.m8thubadmin.R;
import com.lifelineconnect.m8thubadmin.Utils.EditTextValidation;
import com.lifelineconnect.m8thubadmin.Utils.ObservableVideoView;

import static android.content.Context.WINDOW_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayBroadcast#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayBroadcast extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
     int orientation;
    private int m_interval = 250;
    private Handler m_handler;
    int lastpostionofseekbar=0;
    int startpostionofseekbar=0;
    int totalpostionofseekbar=0;
    private OnFragmentInteractionListener mListener;
    boolean apihit = false;

    private VideoView vv_broadcast;
    private TextView tv_Ch_name, btn_sent_comment,tv_totalComments;
    private EditText et_write_comment;
    private RelativeLayout rlMAin;
    private LinearLayout ll_btn_comments, btn_back, btn_menu, ll_write_msg,ll_live_duration;
    ImageView zoomer;
    long height, deviceHeight, scrollHeight;
    boolean is_SMALL_SCREEN = false;
    private String strComment;
    private RecyclerView rec_cmnt_list;

    private MediaPlayer mediaPlayernew;
    private SurfaceHolder vidHolder;

   // List<GetfeedsbyId.Comment> newlist = new LinkedList<>();

    TextView closeframe,detail1,detail2,detail3,detail4;
    Button watchbutton;

FrameLayout aboveframe;
ImageView poster;
    private boolean flag = false;

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_content = "content";
    boolean fullsc = false;

    private String url = null;
    private String content = null;

    private ObservableVideoView myVideoView;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;
    TextView messageContentTextView;



    public PlayBroadcast() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayBroadcast.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayBroadcast newInstance(String param1, String param2) {
        PlayBroadcast fragment = new PlayBroadcast();
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



        m_handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_play_broadcast, container, false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        long deviceWidth = displayMetrics.widthPixels;
        deviceHeight = displayMetrics.heightPixels;
        height = deviceHeight/2;
        scrollHeight = deviceHeight/4;
        init(v, savedInstanceState);




        return v;

    }

    private void init(View v, Bundle savedInstanceState) {
        vv_broadcast    = (VideoView) v.findViewById(R.id.vv_broadcast);
        btn_back        = (LinearLayout) v.findViewById(R.id.btn_back);
        tv_Ch_name      = (TextView) v.findViewById(R.id.tv_Ch_name);
        btn_sent_comment= (TextView) v.findViewById(R.id.btn_sent_comment);
        tv_totalComments = (TextView) v.findViewById(R.id.tv_totalComments);
        et_write_comment= (EditText) v.findViewById(R.id.et_write_comment);
        ll_btn_comments = (LinearLayout) v.findViewById(R.id.ll_btn_comments);
        ll_write_msg    = (LinearLayout) v.findViewById(R.id.ll_write_msg);
        rlMAin          = (RelativeLayout) v.findViewById(R.id.rlMAin);
        rec_cmnt_list   = (RecyclerView) v.findViewById(R.id.rec_cmnt_list);





        btn_menu =(LinearLayout) v.findViewById(R.id.btn_menu);
        zoomer =(ImageView) v.findViewById(R.id.zoomer);
        orientation = getActivity().getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            zoomer.setImageResource(R.mipmap.zoom_out);
        }else{
            zoomer.setImageResource(R.mipmap.zoom_in);
        }


        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 switch(orientation) {
                    case Configuration.ORIENTATION_PORTRAIT:

                        getActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        break;
                    case Configuration.ORIENTATION_LANDSCAPE:

                        getActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        break;
                }
            }
        });


       // tv_totalComments.setText(""+Channel.feeds.get(MainActivity.POS).comment().size());

     //   Log.d("videoURL", "URL is == "+Channel.feeds.get(MainActivity.POS).broadcast().url());
        //setupVideoView();

        ll_btn_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!is_SMALL_SCREEN) {

                    is_SMALL_SCREEN = true;
                    ll_write_msg.setVisibility(View.VISIBLE);
                    rec_cmnt_list.setVisibility(View.VISIBLE);
                    rec_cmnt_list.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rec_cmnt_list.setLayoutManager(linearLayoutManager);
                     //newlist.addAll(Channel.feeds.get(MainActivity.POS).comment());
//                    customAdapter = new CommentsListAdapter(   getActivity(),  Channel.feeds.get(MainActivity.POS).comment());
//                    rec_cmnt_list.setAdapter(customAdapter); // set the Adapter to RecyclerView
//                    customAdapter.notifyDataSetChanged();
                    //scrollToEnd();


                }else if(is_SMALL_SCREEN){
                    is_SMALL_SCREEN = false;
                    ll_write_msg.setVisibility(View.GONE);
                    rec_cmnt_list.setVisibility(View.GONE);


                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mediaPlayernew!=null)
                {
                    mediaPlayernew.stop();
                    mediaPlayernew.release();
                    mediaPlayernew = null;
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else
                {
                    getActivity().getSupportFragmentManager().popBackStack();
                }


            }
        });


//        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
//        rec_cmnt_list.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
//
//        final CommentsListAdapter customAdapter = new CommentsListAdapter(getContext(), Channel.feeds);
//        rec_cmnt_list.setAdapter(customAdapter); // set the Adapter to RecyclerView
//        customAdapter.notifyDataSetChanged();



//        btn_sent_comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                strComment = et_write_comment.getText().toString();
//
//            }
//        });

        try {
            if (MainActivity.curAdapter.equals("FeedsAdapter")) {
                tv_Ch_name.setText(Channel.feeds.get(MainActivity.POS).channel().name());
                btn_sent_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(EditTextValidation.isValidate(et_write_comment)){

                            strComment = et_write_comment.getText().toString();

                            et_write_comment.setText("");

                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                          //  addcomment();
                         //   createComment(getContext(),strComment,Channel.feeds.get(MainActivity.POS).id());
                        }else{

                        }
                    }
                });
            } else if (MainActivity.curAdapter.equals("FeedsAfterAdapter")) {

//                LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
//                rec_cmnt_list.setLayoutManager(layoutManager); // set LayoutManager to RecyclerView
//
//                final FeedsAdapter customAdapter = new FeedsAdapter(getContext(), Channel.feeds);
//                rec_cmnt_list.setAdapter(customAdapter); // set the Adapter to RecyclerView
//                customAdapter.notifyDataSetChanged();

                tv_Ch_name.setText(Channel.Afterfeeds.get(MainActivity.POS).channel().name());
                btn_sent_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(EditTextValidation.isValidate(et_write_comment)){

                            strComment = et_write_comment.getText().toString();

                            et_write_comment.setText("");
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            //addcomment();
                            //createComment(getContext(),strComment,Channel.Afterfeeds.get(MainActivity.POS).id());



                        }else{

                        }
                    }
                });
            }
        }catch (NullPointerException e){

            Log.d("response >>0", "click failed");

            btn_sent_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(EditTextValidation.isValidate(et_write_comment)){
                        strComment = et_write_comment.getText().toString();
                        et_write_comment.setText("");

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        //addcomment();
                      //  createComment(getContext(),strComment,Channel.feeds.get(MainActivity.POS).id());


                    }else{

                    }
                }
            });
        }



        /************************************** New Video View ******************************************/

        String d1,d2,d3,d4;
        if(MainActivity.curAdapter.equals("FeedsAdapter")){

            url = Channel.feeds.get(MainActivity.POS).url();


            //vv_broadcast.setVideoPath(Channel.feeds.get(MainActivity.POS).broadcast().url());
        }else if(MainActivity.curAdapter.equals("FeedsAfterAdapter")){

            url = Channel.Afterfeeds.get(MainActivity.POS).url();


            //vv_broadcast.setVideoPath(Channel.Afterfeeds.get(MainActivity.POS).broadcast().url());
        }


        //set the media controller buttons
        if (mediaControls == null) {
            mediaControls = new MediaController(getContext());

            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mediaControls.getLayoutParams();
            lp.setMargins(0, 0, 0, Math.round(getActivity().getResources().getDimension(R.dimen._65jdp)));
             mediaControls.setLayoutParams(lp);

            //mediaControls.setPadding(0, 0, 0, Math.round(getActivity().getResources().getDimension(R.dimen._65jdp)));
        }

        //initialize the VideoView
        myVideoView = (ObservableVideoView) v.findViewById(R.id.vv_broadcast);

        aboveframe = (FrameLayout) v.findViewById(R.id.aboveframe);
        closeframe   = (TextView) v.findViewById(R.id.closeframe);
        detail1   = (TextView) v.findViewById(R.id.detail1);
        detail2   = (TextView) v.findViewById(R.id.detail2);
        detail3   = (TextView) v.findViewById(R.id.detail3);
        detail4   = (TextView) v.findViewById(R.id.detail4);
        watchbutton   = (Button) v.findViewById(R.id.watchbutton);

        poster = (ImageView) v.findViewById(R.id.poster);


        watchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboveframe.setVisibility(View.GONE);
                myVideoView.start();
            }
        });

        closeframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myVideoView.getLayoutParams();
        params.width =  metrics.widthPixels;
        params.height = (int) (500*metrics.density);
        params.leftMargin = 0;
        myVideoView.setLayoutParams(params);




        if(!MainActivity.curAdapter.equals("AdapterVideoList") ) {
            // create a progress bar while the video file is loading
            progressDialog = new ProgressDialog(getContext());
            // set a title for the progress bar
            // progressDialog.setName("JavaCodeGeeks Android Video View Example");
            // set a message for the progress bar
            progressDialog.setMessage(getString(R.string.loading_video));
            //set the progress bar not cancelable on users' touch
            progressDialog.setCancelable(true);
            // show the progress bar
            progressDialog.show();
        }

        try {
            //set the media controller in the VideoView
            mediaControls.setAnchorView(myVideoView);
            mediaControls.setMediaPlayer(myVideoView);
            //set the uri of the video to be played
            myVideoView.setMediaController(mediaControls);

            myVideoView.setVideoURI(Uri.parse(url));



        } catch (Exception e) {
            e.printStackTrace();
        }
        myVideoView.setVideoViewListener(mVideoViewListener);
        myVideoView.requestFocus();


        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {

                mediaPlayernew = mediaPlayer;
                totalpostionofseekbar = Math.round(mediaPlayer.getDuration()/1000);

                // close the progress bar and play the video
                if(progressDialog != null)
                progressDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here

                startRepeatingTask();
                if(null != savedInstanceState) {
                    // set you initial fragment object
                    position = savedInstanceState.getInt("Position");
                    myVideoView.seekTo(position);
                    myVideoView.start();
                }else{
                    myVideoView.seekTo(position);
                    if(MainActivity.curAdapter.equals("AdapterVideoList"))
                    {
                        //detail3.setText(mediaPlayer.getDuration());
                    }
                    else {
                        if (position == 0) {

                            myVideoView.start();
                        } else {
                            //if we come from a resumed activity, video playback will be paused
                            myVideoView.pause();
                        }
                    }
                }

            }
        });


        myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayernew = mp;
                Log.e("listner----","listner----onCompletion");
                //stopRepeatingTask();


               // createBroadcastViwe(getActivity(),startpostionofseekbar);
            }
        });

        myVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                stopRepeatingTask();
                mediaPlayernew = mp;
                Log.e("listner----","listner----onError");
                return true;
            }
        });
        myVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mediaPlayernew = mp;
                Log.e("listner----","listner----onInfo");


                return true;
            }
        });



//        DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) myVideoView.getLayoutParams();
//        params.width =  metrics.widthPixels;
//        params.height = metrics.heightPixels;
//        params.leftMargin = 0;
//        myVideoView.setLayoutParams(params);
//        fullscreen.setImageResource(R.mipmap.zoom_out);






    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
        myVideoView.pause();
    }





    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        if(lastpostionofseekbar>0 && lastpostionofseekbar!=totalpostionofseekbar)
        {
            apihit = true;
            //createBroadcastViwe(Global.getInstance(),startpostionofseekbar);
        }
        stopRepeatingTask();

        if(orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onDestroy();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onFragmentInteraction(Uri uri);
    }


//    private void createComment(Context context, String comment, String id){
//        MyApolloClients.getApolloClient(context).mutate(FeedCommentCreate.builder()
//                .feedId(id)
//                .message(comment).build()).enqueue(new ApolloCall.Callback<FeedCommentCreate.Data>() {
//            @Override
//            public void onResponse(@Nonnull Response<FeedCommentCreate.Data> response) {
//                Log.d("response comment > ", response.data().toString());
//            }
//
//            @Override
//            public void onFailure(@Nonnull ApolloException e) {
//                Log.d("response cmnt Failure ", e.toString());
//            }
//        });
//
//    }


//    private void createBroadcastViwe(Context context, int start)
//    {
//        String broadid = "";
//        if(Channel.feeds != null && Channel.feeds.size()>MainActivity.POS)
//        {
//            if (MainActivity.curAdapter.equals("FeedsAdapter")) {
//                broadid = Channel.feeds.get(MainActivity.POS).broadcast().id();
//            } else if (MainActivity.curAdapter.equals("FeedsAfterAdapter")) {
//                broadid = Channel.feeds.get(MainActivity.POS).broadcast().id();
//            }
//
//            Log.e("createBroadcastViwe----", "createBroadcastViwe----" + broadid + "---" + totalpostionofseekbar + "---" + start + "---" + lastpostionofseekbar);
//            MyApolloClients.getApolloClient(context).mutate(BroadCastView.builder()
//                    .broadcastId(broadid)
//                    .viewDuration(totalpostionofseekbar)
//                    .start(start)
//                    .end(lastpostionofseekbar).build()).enqueue(new ApolloCall.Callback<BroadCastView.Data>() {
//                @Override
//                public void onResponse(@Nonnull Response<BroadCastView.Data> response) {
//                    Log.e("createBroadcastViwe----", "createBroadcastViwe----response---" + response.data().toString());
//                    apihit = false;
//                }
//
//                @Override
//                public void onFailure(@Nonnull ApolloException e) {
//                    Log.e("createBroadcastViwe----", "createBroadcastViwe----ApolloException---" + e.toString());
//                    apihit = false;
//                }
//            });
//        }
//    }

//public void addcomment()
//{
//    Preferences abcd =     Preferences.getInstance();
//    Utils.getInfo(getActivity());
//    GetfeedsbyId.User1   newuser = new GetfeedsbyId.User1("User", Settings.firstname,Settings.lastname,abcd.getUserXMPPJid());
//    GetfeedsbyId.Comment addcoment =new GetfeedsbyId.Comment("FeedComment",""+Channel.feeds.get(MainActivity.POS).id(),"",
//            strComment,newuser,null);
//
//    newlist.add(addcoment);
//    customAdapter = new CommentsListAdapter(   getActivity(),  newlist);
//    Log.e("Channelfeed---------","Channelfeed---------2--"+customAdapter);
//    rec_cmnt_list.setAdapter(customAdapter); // set the Adapter to RecyclerView
//    customAdapter.notifyDataSetChanged();
//    scrollToEnd();
//    tv_totalComments.setText(""+newlist.size());
//}



    private ObservableVideoView.IVideoViewActionListener mVideoViewListener = new ObservableVideoView.IVideoViewActionListener()
    {

        @Override
        public void onTimeBarSeekChanged(int currentTime)
        {
            if(!apihit && lastpostionofseekbar>0 && lastpostionofseekbar!=totalpostionofseekbar)
            {
                apihit = true;
               // createBroadcastViwe(getActivity(),startpostionofseekbar);
            }


            startpostionofseekbar= Math.round(currentTime/1000);
         //TODO what you want
            Log.e("listner----","listner----onTimeBarSeekChanged---"+startpostionofseekbar+"----"+lastpostionofseekbar);



        }



        //        @Override
//        public boolean canSeekForward() {
//            Log.e("listner----","listner----canSeekForward");
//            return true;
//        }
//
//        @Override
//        public boolean canSeekBackward() {
//            Log.e("listner----","listner----canSeekBackward");
//            return true;
//        }
//
//        @Override
//        public boolean onTouchEvent() {
//            Log.e("listner----","listner----onTouchEvent");
//            return true;
//        }
//
//        @Override
//        public boolean onTrackballEvent() {
//            Log.e("listner----","listner----onTrackballEvent");
//            return true;
//        }
//
//        @Override
//        public boolean onKeyDown() {
//            Log.e("listner----","listner----onKeyDown");
//            return true;
//        }
//
//        @Override
//        public boolean onKeyUp() {
//            Log.e("listner----","listner----onKeyUp");
//            return true;
//        }

//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            Log.e("listner----","listner----onProgressChanged");
//
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//            Log.e("listner----","listner----onStartTrackingTouch");
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            Log.e("listner----","listner----onStopTrackingTouch");
//        }

        @Override
        public void onResume()
        {
            //TODO what you want
            Log.e("listner----","listner----onResume");
        }

        @Override
        public void onPause()
        {
            //TODO what you want
            Log.e("listner----","listner----onPause");
        }
    };



    Runnable m_statusChecker = new Runnable()
    {
        @Override
        public void run() {
            if(mediaPlayernew!=null)
            lastpostionofseekbar = Math.round(mediaPlayernew.getCurrentPosition()/1000);
            else
             lastpostionofseekbar =0;

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

}
