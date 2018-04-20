package com.lifelineconnect.m8thubadmin.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lifelineconnect.m8thubadmin.GetAfterbroadcast;
import com.lifelineconnect.m8thubadmin.MainActivity;
import com.lifelineconnect.m8thubadmin.R;
import com.lifelineconnect.m8thubadmin.Utils.RoundedImageView;
import com.lifelineconnect.m8thubadmin.Utils.TimeCalculation;
import com.lifelineconnect.m8thubadmin.Utils.Utils;
import com.lifelineconnect.m8thubadmin.fragments.Channel;
import com.lifelineconnect.m8thubadmin.fragments.PlayBroadcast;
import com.lifelineconnect.m8thubadmin.fragments.SubscribeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

/**
 * Created by jaspreet on 3/5/18.
 */

public class FeedAfterAdapter extends RecyclerView.Adapter<FeedAfterAdapter.ViewHolder>  {
    public static String tag ="FeedAfterAdapter";
    List<GetAfterbroadcast.BroadcastGet> Response;
    int radiusArr[];
    Context context;
    String Date,date2, time;
    String[] splitDate;
    Set<String> val;
    public FeedAfterAdapter(Context context, List<GetAfterbroadcast.BroadcastGet> feeds) {
        this.context = context;
        this.Response = feeds;
        val = Utils.getlisthashset(context);
        radiusArr = new int[]{25, 23, 21, 19, 17};// decide blur amount

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item_res, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {

        // final int pos = position % Response.size();

        holder.btn_menu.setVisibility(View.INVISIBLE);

        Log.d("feedsAdapter > ",Response.get(pos).toString());
        try {
            Date = String.valueOf(Response.get(pos).publishDate());

            holder.tv_membership.setText(TimeCalculation.getTimeStringAgoSinceDateChannle(context,Date));

            try {
                holder.tv_totalLikes.setText(String.valueOf(Response
                        .get(pos).likeCount()));
            }catch (NullPointerException e){
                holder.tv_totalLikes.setText("0");
            }
            try {
                holder.tv_totalShare.setText(String.valueOf(Response.get(pos)
                        .shareCount()));
            }catch (NullPointerException e){
                holder.tv_totalShare.setText("0");
            }

            if(Response.get(pos).channel().avatar()!=null)
            {
                try {
                    Picasso.with(context).load(Response
                            .get(pos).channel().avatar()).into(holder.iv_avatar);
                }catch (IllegalArgumentException e){}
                catch (NullPointerException e){}
            }else{
                try {
                    Picasso.with(context).load(Response
                            .get(pos).channel().avatar()).into(holder.iv_avatar);
                }catch (IllegalArgumentException e){}
                catch (NullPointerException e){}
            }


            if(Response.get(pos).poster()!=null && !Response.get(pos).poster().isEmpty())
            {
                try {
                    Picasso.with(context).load(Response
                            .get(pos).poster()).into(holder.image);
                } catch (IllegalArgumentException e) {
                } catch (NullPointerException e) {
                }
            }else{
                try {
                    if(Response.get(pos).channel().coverImage()!=null)
                        Picasso.with(context).load(Response
                                .get(pos).channel().coverImage()).into(holder.image);
                    else
                        Picasso.with(context).load(Response
                                .get(pos).channel().coverImage()).into(holder.image);
                } catch (IllegalArgumentException e) {
                } catch (NullPointerException e) {
                }
            }

            Log.d("broadcast status ", String.valueOf(Response.get(pos).isLive()));

            if(Response.get(pos).isLive().equals(true)
                    ||Response.get(pos).isLive().equals("true")){

                Log.e("url----","url----0--"+Response.get(pos).url());
                String channelnamenew ="";
                if(Response.get(pos).channel().name()!=null)
                    channelnamenew = Response.get(pos).channel().name();
                else
                    channelnamenew = Response.get(pos).channel().name();


                if(Response.get(pos).url().contains(".")){
                    holder.tv_title.setText(Html.fromHtml("<b>"+channelnamenew
                            + "</b>"+" was "+"<font color=#FF0000>Live</font>"));
                }else{
                    holder.tv_title.setText(Html.fromHtml("<b>"+channelnamenew
                            + "</b>"+" is currently "+"<font color=#FF0000>Live</font>"));
                }
                try {
                    holder.tv_channelDes.setText(Response.get(pos).title());
                    Log.d("title of br true aftr ", Response.get(pos).title());
                }catch (NullPointerException e){}
                // if(Response.get(pos).broadcast().isActive()){
                // Log.d("title of afr tru name", Response.get(pos).channel().name());
//                    holder.tv_title.setText(Html.fromHtml("<b>"+Response.get(pos).broadcast().title()
//                            + "</b>"+" is currently "+"<font color=#FF0000>LIVE</font>"));

                //

//                    }else{
//                        Log.d("title of br not Actv ", Response.get(pos).channel().name());
//                        holder.tv_name.setText(Html.fromHtml("<b>"+Response.get(pos).channel().name()
//                                + "</b>"+" was "+"<font color=#FF0000>LIVE</font>"));
//
//                    }
            }else if(Response.get(pos).isLive().equals(false)
                    ||Response.get(pos).isLive().equals("false")){
                try {
                    holder.tv_channelDes.setText(Response.get(pos).title());
                    Log.d("title of false aftre ", Response.get(pos).title());
                }catch (NullPointerException e){}
                //   Log.d("title of false aftrname", Response.get(pos).channel().name());

                if(Response.get(pos).channel().name()!=null)
                    holder.tv_title.setText(Html.fromHtml("<b>"
                            +Response.get(pos).channel().name()
                            + "</b>"+" uploaded a new video "));
                else
                    holder.tv_title.setText(Html.fromHtml("<b>"
                            +Response.get(pos).channel().name()
                            + "</b>"+" uploaded a new video "));
            }


//            holder.tv_name.setText(Html.fromHtml("<b>"+Response.get(pos).channel().name()
//                    + "</b>"+" " + Response.get(pos).type() +" "+Response.get(pos).channel().id()));
        }catch (NullPointerException e){}
        //holder.tv_membership.setText(Response.get(pos).getMembership());


        // holder.tv_membership.setVisibility(View.GONE);

//        try {
//            holder.tv_channelDes.setText(Response.get(pos).broadcast().title());
//        }catch (NullPointerException e){}





        if(Response != null && pos == Response.size() -1 && Response.size()>2 ){
            Channel.GetAfterFeedData(context, Response.get(Response.size()-3).id());
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.POS = pos;
                MainActivity.curAdapter = "FeedsAdapter";
                try {
//                    if (Response.get(pos).broadcast().url().equals(null) || Response.get(pos).broadcast().url().equals("")) {
//                        Toast.makeText(context, "No broadcast found", Toast.LENGTH_SHORT).show();
//                    } else {


                    if (Response.get(pos).url() == null
                            || Response.get(pos).url().equals("")) {
                        Toast.makeText(context, "No broadcast found", Toast.LENGTH_SHORT).show();
                    }
                    //     else if (Response.get(pos).broadcast().isLive() && !Response.get(pos).broadcast().url().contains(".")) {
                    else if (Response.get(pos).isLive() ) {

                        Log.e("Responseclcik---","Responseclcik----22222--"+Response.get(pos).url());
                        //Toast.makeText(context, "live broadcast found", Toast.LENGTH_SHORT).show();
                        android.support.v4.app.FragmentTransaction fti;
                        //LiveFeedFragment mFragmenti=new LiveFeedFragment();
                        Bundle bundle = new Bundle();

                        bundle.putString("strtitle", Response.get(pos).url());
                        bundle.putString("title", Response.get(pos).title());

                        SubscribeFragment mFragmenti = new SubscribeFragment();
                        mFragmenti.setArguments(bundle);
                        fti = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fti.replace(android.R.id.content, mFragmenti);
                        try {
                            fti.addToBackStack(null);
                            fti.commit();
                        } catch (IllegalStateException ignored) {
                        }
                    }


                    else{
                        android.support.v4.app.FragmentTransaction fti;
                        //LiveFeedFragment mFragmenti=new LiveFeedFragment();
                        Fragment mFragmenti = new PlayBroadcast();
                        fti = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fti.replace(android.R.id.content, mFragmenti);
                        try {
                            fti.addToBackStack(null);
                            fti.commit();
                        } catch (IllegalStateException ignored) {
                        }
                    }

                    //}
                }catch (NullPointerException e){
                    Toast.makeText(context, "No broadcast found", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return Response.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, btn_like, btn_unlike;
        TextView tv_title, tv_channelDes, tv_membership, tv_totalViews,
                tv_totalLikes, tv_totalShare, tv_totalComments;
        protected LinearLayout btn_menu;
        protected RoundedImageView iv_avatar;
        public ViewHolder(View itemView) {
            super(itemView);

//            image           = (ImageView) itemView.findViewById(R.id.imgView);
//            iv_avatar       = (ImageView) itemView.findViewById(R.id.iv_avatar);
//            btn_like        = (ImageView) itemView.findViewById(R.id.btn_like);
//            btn_unlike      = (ImageView) itemView.findViewById(R.id.btn_unlike);
//            tv_membership   = (TextView) itemView.findViewById(R.id.tv_membership);
//            tv_channelDes   = (TextView) itemView.findViewById(R.id.tv_channelDes);
//            tv_title         = (TextView) itemView.findViewById(R.id.tv_title);
//            tv_totalViews   = (TextView) itemView.findViewById(R.id.tv_totalViews);
//            tv_totalLikes   = (TextView) itemView.findViewById(R.id.tv_totalLikes);
//            tv_totalShare   = (TextView) itemView.findViewById(R.id.tv_totalShare);

            image               = (ImageView) itemView.findViewById(R.id.iv_poster);
            iv_avatar           = (RoundedImageView) itemView.findViewById(R.id.iv_avatar);
            //btn_like            = (ImageView) itemView.findViewById(R.id.btn_like);
            //btn_unlike          = (ImageView) itemView.findViewById(R.id.btn_unlike);
            tv_membership       = (TextView) itemView.findViewById(R.id.tv_three);
            tv_channelDes       = (TextView) itemView.findViewById(R.id.tv_two);
            tv_title             = (TextView) itemView.findViewById(R.id.tv_title);
            tv_totalViews       = (TextView) itemView.findViewById(R.id.tv_totalViews);
            tv_totalLikes       = (TextView) itemView.findViewById(R.id.tv_total_likes);
            tv_totalShare       = (TextView) itemView.findViewById(R.id.tv_totalShare);
            tv_totalComments    = (TextView) itemView.findViewById(R.id.tv_totalComments);
            btn_menu            = (LinearLayout) itemView.findViewById(R.id.btn_menu);

        }
    }

    public Bitmap blurRenderScript(Bitmap smallBitmap, int radius) {

        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    private Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

}
