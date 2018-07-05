package com.qicode.kakaxicm.imageloaderframwork.imageloader.loader;

import android.graphics.Bitmap;

import com.qicode.kakaxicm.imageloaderframwork.imageloader.request.BitmapRequest;

/**
 * Created by chenming on 2018/7/5
 */
public class NoneLoader extends AbsLoader{
    @Override
    protected Bitmap fetchBitmap(BitmapRequest request) {
        return null;
    }
}
