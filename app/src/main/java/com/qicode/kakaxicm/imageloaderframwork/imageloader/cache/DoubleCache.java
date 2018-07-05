package com.qicode.kakaxicm.imageloaderframwork.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.qicode.kakaxicm.imageloaderframwork.imageloader.request.BitmapRequest;

/**
 * Created by chenming on 2018/7/5
 * 双缓存
 */
public class DoubleCache implements Cache {
    //内存缓存
    private MemoryCache mMemoryCache = new MemoryCache();
    //硬盘缓存
    private DiskCache mDiskCache;


    public DoubleCache(Context context) {
        mDiskCache = DiskCache.getInstance(context);
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        mMemoryCache.put(request, bitmap);
        mDiskCache.put(request, bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        //从内存中取
        Bitmap bitmap = mMemoryCache.get(request);
        if (bitmap == null) {
            //从磁盘中取
            bitmap = mDiskCache.get(request);
            if (bitmap != null) {
                //放入内存，方便再获取
                mMemoryCache.put(request, bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public void remove(BitmapRequest request) {
        mMemoryCache.remove(request);
        mDiskCache.remove(request);
    }
}
