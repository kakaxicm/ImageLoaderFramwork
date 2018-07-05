package com.qicode.kakaxicm.imageloaderframwork.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.qicode.kakaxicm.imageloaderframwork.imageloader.request.BitmapRequest;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.util.ImageSize;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.util.ImageSizeUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by chenming on 2018/7/5
 * 从网络请求图片
 */
public class UrlLoader extends AbsLoader {

    @Override
    protected Bitmap fetchBitmap(BitmapRequest request) {
        InputStream is = null;
        String urlStr = request.getImageUri();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            is.mark(1024 * 1024);
            //获得网络图片的宽高
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;//先读取bp大小
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opts);
            ImageView imageView = request.getImageView();
            ImageSize imageSize = ImageSizeUtil.getImageViewSize(imageView);
            //计算宽高比
            opts.inSampleSize = ImageSizeUtil.caculateInSampleSize(opts, imageSize.width, imageSize.height);
            //再加载图片
            opts.inJustDecodeBounds = false;
            is.reset();
            bitmap = BitmapFactory.decodeStream(is, null, opts);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }

        }
        return null;
    }
}
