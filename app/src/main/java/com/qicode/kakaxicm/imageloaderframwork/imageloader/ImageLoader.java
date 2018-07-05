package com.qicode.kakaxicm.imageloaderframwork.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.qicode.kakaxicm.imageloaderframwork.imageloader.config.DisplayConfig;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.config.ImageLoaderConfig;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.policy.LoadPolicy;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.request.BitmapRequest;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.request.RequestQueue;

/**
 * Created by chenming on 2018/7/5
 */
public class ImageLoader {
    //配置
    private ImageLoaderConfig mImageLoaderConfig;

    //请求队列
    private RequestQueue mRequestQueue;
    //单例
    private static ImageLoader mInstance;

    private ImageLoader() {

    }

    private ImageLoader(ImageLoaderConfig imageLoaderConfig) {
        this.mImageLoaderConfig = imageLoaderConfig;
        //请求队列
        mRequestQueue = new RequestQueue(mImageLoaderConfig.getThreadCount());
        mRequestQueue.start();//初始化一次，开启调度线程
    }

    /**
     * 获取单例方法
     * 第一次调用需要配置ImageLoaderConfig
     *
     * @param config
     * @return
     */
    public static ImageLoader getInstance(ImageLoaderConfig config) {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(config);
                }
            }

        }
        return mInstance;
    }

    /**
     * @return
     */
    public static ImageLoader getInstance() {
        if (mInstance == null) {
            throw new UnsupportedOperationException("没有初始化");
        }
        return mInstance;
    }

    public ImageLoaderConfig getImageLoaderConfig() {
        return mImageLoaderConfig;
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param uri
     */
    public void loadImage(ImageView imageView, String uri) {
        BitmapRequest request = new BitmapRequest(imageView, uri, null, null, null);
        mRequestQueue.addRequest(request);
    }

    //下面几个是重载方法
    public void loadImage(ImageView imageView, String uri, DisplayConfig displayconfig) {
        BitmapRequest request = new BitmapRequest(imageView, uri, displayconfig, null, null);
        mRequestQueue.addRequest(request);
    }

    public void loadImage(ImageView imageView, String uri, LoadPolicy loadPolicy) {
        BitmapRequest request = new BitmapRequest(imageView, uri, null, loadPolicy, null);
        mRequestQueue.addRequest(request);
    }

    public void loadImage(ImageView imageView, String uri, DisplayConfig displayconfig, LoadPolicy loadPolicy) {
        BitmapRequest request = new BitmapRequest(imageView, uri, displayconfig, loadPolicy, null);
        mRequestQueue.addRequest(request);
    }

    public static interface ImageListener {
        /**
         * @param imageView
         * @param bitmap
         * @param uri
         */
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }
}
