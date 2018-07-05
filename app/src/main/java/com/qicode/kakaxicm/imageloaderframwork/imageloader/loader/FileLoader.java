package com.qicode.kakaxicm.imageloaderframwork.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.qicode.kakaxicm.imageloaderframwork.imageloader.request.BitmapRequest;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.util.ImageSize;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.util.ImageSizeUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by chenming on 2018/7/5
 * 本地文件加载图片
 */
public class FileLoader extends AbsLoader {

    @Override
    protected Bitmap fetchBitmap(BitmapRequest request) {
        //文件路径
        final String path = Uri.parse(request.getImageUri()).getPath();
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        BufferedInputStream is = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;//先读取bp大小
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
        ImageView imageView = request.getImageView();
        ImageSize imageSize = ImageSizeUtil.getImageViewSize(imageView);
        //计算宽高比
        opts.inSampleSize = ImageSizeUtil.caculateInSampleSize(opts, imageSize.width, imageSize.height);
        //再加载图片
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, opts);
        return bitmap;
    }
}
