package com.lifelineconnect.m8thubadmin.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.lifelineconnect.m8thubadmin.MainActivity;
import com.lifelineconnect.m8thubadmin.Pojo.ChannelPojo;
import com.lifelineconnect.m8thubadmin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jaspreet on 12/21/17.
 */

public class AdapterChannelImg extends RecyclerView.Adapter<AdapterChannelImg.ViewHolder> {

    Context context;
    public ArrayList<ChannelPojo> channelPojos;
    int radiusArr[];
    int wid,hig;

    public AdapterChannelImg(Context context, ArrayList<ChannelPojo> channelPojo) {
        this.context = context;
        this.channelPojos = channelPojo;

        radiusArr = new int[]{25, 23, 21, 19, 17};// decide blur amount


        DisplayMetrics metrics = new DisplayMetrics();
        ((FragmentActivity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
         wid =  metrics.widthPixels;
         hig=context.getResources().getDimensionPixelSize(R.dimen._120jdp);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.res_channel_items, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pos) {
        //final int pos = position % channelPojos.size();
        Log.e("getDescription----","getDescription-----"+channelPojos.get(pos).getDescription());
        holder.tv_channelDes.setText(channelPojos.get(pos).getDescription());
        holder.tv_membership.setText(channelPojos.get(pos).getMembership());
        holder.tv_name.setText(channelPojos.get(pos).getName());





        try {
            holder.tv_totalLikes.setText(String.valueOf(channelPojos.get(pos).getLikeCount()));
        }catch (NullPointerException e){
            holder.tv_totalLikes.setText("0");
        }

        try {
            holder.tv_totalShare.setText(String.valueOf(channelPojos.get(pos).getShareCount()));
        }catch (NullPointerException e){
            holder.tv_totalShare.setText("0");
        }

        try {
            if (!channelPojos.get(pos).getAvatar().equals(null)
                    || !channelPojos.get(pos).getAvatar().equals("null")
                    || !channelPojos.get(pos).getAvatar().equals("")) {
                try {
                    Picasso.with(context).load(channelPojos.get(pos).getAvatar()).into(holder.iv_avatar);
                } catch (IllegalArgumentException e) {
                }
            }
        }catch (NullPointerException e){}

        try {

            if (channelPojos.get(pos).getCover_image() !=null
                    || !channelPojos.get(pos).getCover_image().equals("null")
                    || !channelPojos.get(pos).getCover_image().equals("")) {
                try {

//                    Picasso.with(mContext)
//                            .load(R.drawable.check)
//                            .transform(new BlurTransformation(mContext, 25, 1))
//                            .into(holder.image);



                    Picasso.with(context)
                            .load(channelPojos
                            .get(pos).getCover_image())
                            //.transform(new BlurTransformation(context, 25, 1))
                            .into(holder.image);
                } catch (IllegalArgumentException e) {
                }
            }
        }catch (NullPointerException e){}
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


    @Override
    public int getItemCount() {
        return channelPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_avatar, btn_like, btn_unlike;
        TextView tv_name, tv_channelDes, tv_membership,
                tv_totalViews, tv_totalLikes, tv_totalShare;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            image           = (ImageView) itemView.findViewById(R.id.imgView);
            iv_avatar       = (ImageView) itemView.findViewById(R.id.iv_avatar);
            btn_like        = (ImageView) itemView.findViewById(R.id.btn_like);
            btn_unlike      = (ImageView) itemView.findViewById(R.id.btn_unlike);
            tv_membership   = (TextView) itemView.findViewById(R.id.tv_membership);
            tv_channelDes   = (TextView) itemView.findViewById(R.id.tv_channelDes);
            tv_name         = (TextView) itemView.findViewById(R.id.tv_name);
            tv_totalShare   = (TextView) itemView.findViewById(R.id.tv_totalShare);
            tv_totalViews   = (TextView) itemView.findViewById(R.id.tv_totalViews);
            tv_totalLikes   = (TextView) itemView.findViewById(R.id.tv_totalLikes);

        }

        public void bindTo(Drawable drawable) {
            image.setImageDrawable(drawable);
            ViewGroup.LayoutParams lp = image.getLayoutParams();
            if (lp instanceof FlexboxLayoutManager.LayoutParams) {
                FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams)lp;
                flexboxLp.setFlexGrow(1.0f);        }
        }

        public void bindTo(String avatar) {
            Picasso.with(context).load(avatar).into(image);
            ViewGroup.LayoutParams lp = image.getLayoutParams();
            if (lp instanceof FlexboxLayoutManager.LayoutParams) {
                FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams)lp;
                flexboxLp.setFlexGrow(1.0f);        }

        }
    }
}
