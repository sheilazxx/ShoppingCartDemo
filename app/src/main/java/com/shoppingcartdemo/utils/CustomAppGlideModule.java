package com.shoppingcartdemo.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Administrator on 2017/11/16.
 */
@GlideModule
public class CustomAppGlideModule extends AppGlideModule {
    public static int CIRCLE = 0;
    public static int ROUND = 1;

    public static void glideLoader(Context mContext, Uri uri, ImageView imageView) {
        //原生 API
        GlideApp
                .with(mContext)
                .load(uri)
                .centerCrop()
                .into(imageView);

    }

    public static void glideLoader(Context mContext, int drawableId, ImageView imageView) {
        GlideApp
                .with(mContext)
                .load(drawableId)
                .centerCrop()
                .into(imageView);

    }

    public static void glideLoader(Context mContext, String  url, ImageView imageView) {
        GlideApp
                .with(mContext)
                .load(url)
                .centerCrop()
                .into(imageView);

    }
    public static void glideLoader(Context mContext, String url, ImageView imageView, int emptyImg, int erroImg) {
        GlideApp
                .with(mContext)
                .load(url)

                .placeholder(emptyImg)
                .error(erroImg)
                .fallback(emptyImg)
                .into(imageView);
    }


    public static void glideLoader(Context mContext, String url, ImageView imageView, int erroImg, int emptyImg, int tag) {
        if (tag == CIRCLE) {

            GlideApp
                    .with(mContext)
                    .load(url)
                    .centerCrop()
                    .placeholder(emptyImg)
                    .error(erroImg)
                    .fallback(emptyImg)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView);

        } else if (tag == ROUND) {
            GlideApp
                    .with(mContext)
                    .load(url)
                    .centerCrop()
                    .placeholder(emptyImg)
                    .error(erroImg)
                    .fallback(emptyImg)
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(20)))
                    .into(imageView);


        } else {
            GlideApp
                    .with(mContext)
                    .load(url)
                    .centerCrop()
                    .placeholder(emptyImg)
                    .error(erroImg)
                    .fallback(emptyImg)
                    .into(imageView);
        }
    }
}
