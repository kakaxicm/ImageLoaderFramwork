package com.qicode.kakaxicm.imageloaderframwork.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.qicode.kakaxicm.imageloaderframwork.imageloader.request.BitmapRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by chenming on 2018/7/5
 * 磁盘缓存
 */
public class DiskCache implements Cache {
    //单例
    private static DiskCache mDiskCache;
    private String mCacheDir = "Image";
    //MB
    private static final int MB = 1024 * 1024;
    //JW的神作
    private DiskLruCache mDiskLruCache;

    private DiskCache(Context context) {
        iniDiskCache(context);
    }

    public static DiskCache getInstance(Context context) {
        if (mDiskCache == null) {
            synchronized (DiskCache.class) {
                if (mDiskCache == null) {
                    mDiskCache = new DiskCache(context);
                }
            }
        }
        return mDiskCache;
    }

    /**
     * 初始化磁盘缓存
     *
     * @param context
     */
    private void iniDiskCache(Context context) {
        File directory = new File(Environment.getExternalStorageDirectory(), mCacheDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
        try {
            mDiskLruCache = DiskLruCache.open(directory, 1, 1, 50 * MB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        OutputStream os = null;
        try {
            editor = mDiskLruCache.edit(request.getImageUriMd5());
            os = editor.newOutputStream(0);
            boolean ressult = saveBitmap2Disk(bitmap, os);
            if(ressult){
                editor.commit();
            }else{
                editor.abort();
            }
        } catch (IOException e) {
        }
    }

    /**
     * 保存bp到文件
     * @param bitmap
     * @param os
     * @return
     */
    private boolean saveBitmap2Disk(Bitmap bitmap, OutputStream os) {
        BufferedOutputStream bos = new BufferedOutputStream(os);

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;

    }

    @Override
    public Bitmap get(BitmapRequest request) {
        String key = request.getImageUriMd5();
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if(snapshot != null){
                InputStream inputStream = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(BitmapRequest request) {
        try {
            mDiskLruCache.remove(request.getImageUriMd5());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
