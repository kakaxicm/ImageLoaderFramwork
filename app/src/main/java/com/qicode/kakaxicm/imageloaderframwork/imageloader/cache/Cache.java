package com.qicode.kakaxicm.imageloaderframwork.imageloader.cache;

import android.graphics.Bitmap;

import com.qicode.kakaxicm.imageloaderframwork.imageloader.request.BitmapRequest;

/**
 * Created by chenming on 2018/7/5
 */
public interface Cache {
    /**
     * 缓存bitmap
     * @param bitmap
     */
    void put(BitmapRequest request, Bitmap bitmap);

    /**
     * 通过请求取Bitmap
     * @param request
     * @return
     */
    Bitmap get(BitmapRequest request);

    /**
     * 移除缓存
     * @param request
     */
    void remove(BitmapRequest request);
}
