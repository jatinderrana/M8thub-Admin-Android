package com.lifelineconnect.m8thubadmin.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by rana on 3/28/2018.
 */

public class ObservableVideoView extends VideoView /*implements SeekBar.OnSeekBarChangeListener*/
{

    private IVideoViewActionListener mVideoViewListener;
    private boolean mIsOnPauseMode = false;

//    @Override
//    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        if (mVideoViewListener != null)
//        {
//            mVideoViewListener.onProgressChanged(seekBar, progress, fromUser);
//        }
//    }
//
//    @Override
//    public void onStartTrackingTouch(SeekBar seekBar) {
//        if (mVideoViewListener != null)
//        {
//            mVideoViewListener.onStartTrackingTouch(seekBar);
//        }
//    }
//
//    @Override
//    public void onStopTrackingTouch(SeekBar seekBar) {
//        if (mVideoViewListener != null)
//        {
//            mVideoViewListener.onStopTrackingTouch(seekBar);
//        }
//    }

    public interface IVideoViewActionListener
    {
        void onPause();
        void onResume();
        void onTimeBarSeekChanged(int currentTime);
//        boolean canSeekForward();
//        boolean canSeekBackward();
//        boolean onTouchEvent();
//        boolean onTrackballEvent();
        // boolean onKeyDown();
//        boolean onKeyUp();
//        void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);
//        void onStartTrackingTouch(SeekBar seekBar);
//        void onStopTrackingTouch(SeekBar seekBar);
    }

    public void setVideoViewListener(IVideoViewActionListener listener)
    {
        mVideoViewListener = listener;
    }

    @Override
    public void pause()
    {
        super.pause();

        if (mVideoViewListener != null)
        {
            mVideoViewListener.onPause();
        }

        mIsOnPauseMode = true;
    }

    @Override
    public void start()
    {
        super.start();

        if (mIsOnPauseMode)
        {
            if (mVideoViewListener != null)
            {
                mVideoViewListener.onResume();
            }

            mIsOnPauseMode = false;
        }
    }

    @Override
    public void seekTo(int msec)
    {
        super.seekTo(msec);

        if (mVideoViewListener != null)
        {
            mVideoViewListener.onTimeBarSeekChanged(msec);
        }
    }



    //    @Override
//    public boolean canSeekBackward() {
//         super.canSeekBackward();
//        if (mVideoViewListener != null)
//        {
//            mVideoViewListener.canSeekBackward();
//        }
//        return  mVideoViewListener.canSeekBackward();
//    }
//
//    @Override
//    public boolean canSeekForward() {
//         super.canSeekForward();
//            if (mVideoViewListener != null)
//            {
//                mVideoViewListener.canSeekForward();
//            }
//        return  mVideoViewListener.canSeekForward();
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//         super.onTouchEvent(ev);
//        if (mVideoViewListener != null)
//        {
//            mVideoViewListener.onTouchEvent();
//        }
//        return  mVideoViewListener.onTouchEvent();
//    }
//
//    @Override
//    public boolean onTrackballEvent(MotionEvent ev) {
//         super.onTrackballEvent(ev);
//        if (mVideoViewListener != null)
//        {
//            mVideoViewListener.onTrackballEvent();
//        }
//        return  mVideoViewListener.onTrackballEvent();
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//         super.onKeyDown(keyCode, event);
//        if (mVideoViewListener != null)
//        {
//            mVideoViewListener.onKeyDown();
//        }
//        return  mVideoViewListener.onKeyDown();
//    }
//
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//         super.onKeyUp(keyCode, event);
//        if (mVideoViewListener != null)
//        {
//            mVideoViewListener.onKeyUp();
//        }
//        return  mVideoViewListener.onKeyUp();
//    }

    public ObservableVideoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ObservableVideoView(Context context)
    {
        super(context);
    }

    public ObservableVideoView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
}
