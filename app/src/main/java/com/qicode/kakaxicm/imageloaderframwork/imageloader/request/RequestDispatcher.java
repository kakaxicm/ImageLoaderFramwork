package com.qicode.kakaxicm.imageloaderframwork.imageloader.request;

import android.util.Log;

import com.qicode.kakaxicm.imageloaderframwork.imageloader.loader.Loader;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

import static android.content.ContentValues.TAG;

/**
 * Created by chenming on 2018/7/5
 * 轮询从优先级队列中取BitmapRequest交给加载器
 */
public class RequestDispatcher extends Thread {
    BlockingQueue<BitmapRequest> mRequestQueue;//从队列中取请求

    public RequestDispatcher(BlockingQueue<BitmapRequest> requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                //阻塞式函数
                BitmapRequest request = mRequestQueue.take();
                //获取加载器
                String schema = parseSchema(request.getImageUri());
                //根据前缀拿到相应的loader
                Loader loader = LoaderManager.getInstance().getLoader(schema);
                loader.loadImage(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拿到imageUri的前缀
     *
     * @param imageUri
     * @return
     */
    private String parseSchema(String imageUri) {
        if (imageUri.contains("://")) {
            return imageUri.split("://")[0];
        } else {
            Log.i(TAG, "不支持此类型");
        }

        return null;
    }
}
