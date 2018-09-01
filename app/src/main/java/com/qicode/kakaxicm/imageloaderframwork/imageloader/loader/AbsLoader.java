package com.qicode.kakaxicm.imageloaderframwork.imageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.qicode.kakaxicm.imageloaderframwork.imageloader.ImageLoader;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.cache.Cache;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.config.DisplayConfig;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.request.BitmapRequest;

/**
 * Created by chenming on 2018/7/5
 * 抽取统一的流程
 * 1.从缓存中取，无则从走子类加载器方法，拿到bp,存入缓存，有则发送给UI线程
 * 2.处理加载过程中和加载后的结果
 */
public abstract class AbsLoader implements Loader {
    private Cache mCache = ImageLoader.getInstance().getImageLoaderConfig().getCache();
    private DisplayConfig mGlobalDisplayConfig = ImageLoader.getInstance().getImageLoaderConfig().getDisplayConfig();

    @Override
    public void loadImage(BitmapRequest request) {
        //从缓存取到Bitmap
        Bitmap bitmap = mCache.get(request);
        if (bitmap == null) {
            //没有则开始加载
            showLoadingImage(request);
            bitmap = fetchBitmap(request);//加载图片，子类实现
            //缓存图片
            cacheBitmap(request, bitmap);
        }else{
            Log.e("ImageLoader", "从缓存中取"+request.getImageUri());
        }

        //更新UI
        postToUIThread(request, bitmap);
    }

    /**
     * 交给主线程显示
     *
     * @param request
     * @param bitmap
     */
    protected void postToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if (imageView != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(request, bitmap);
                }

            });
        }

    }

    /**
     * 更新UI
     *
     * @param request
     * @param bitmap
     */
    private void updateImageView(BitmapRequest request, Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        //加载成功
        if (bitmap != null && imageView.getTag().equals(request.getImageUri())) {
            imageView.setImageBitmap(bitmap);
            //有可能图片还需要客户处理
            if (request.mImageListener != null) {
                request.mImageListener.onComplete(imageView, bitmap, request.getImageUri());
            }
            return;
        }
        //加载失败
        if (bitmap == null) {
            showFailImage(request);
        }
    }

    /**
     * 缓存图片
     *
     * @param request
     * @param bitmap
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (request != null && bitmap != null) {
            synchronized (AbsLoader.class) {
                mCache.put(request, bitmap);
            }
        }
    }

    /**
     * 如果配置了dis
     *
     * @param request
     */
    private void showLoadingImage(BitmapRequest request) {
        //先尝试从request中加载图片
        final DisplayConfig localDisplayConfig = request.getDisplayConfig();
        final ImageView imageView = request.getImageView();
        if (localDisplayConfig != null && localDisplayConfig.loadingImage != -1) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(localDisplayConfig.loadingImage);
                }
            });
            return;
        }
        //如果本次请求没有单独配置DisplayConfig，则从全局配置参数中配置
        if (mGlobalDisplayConfig != null && mGlobalDisplayConfig.loadingImage != -1) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(mGlobalDisplayConfig.loadingImage);
                }
            });
        }
    }

    /**
     * 如果配置了dis
     *
     * @param request
     */
    private void showFailImage(BitmapRequest request) {
        //先尝试从request中加载图片
        final DisplayConfig localDisplayConfig = request.getDisplayConfig();
        final ImageView imageView = request.getImageView();
        if (localDisplayConfig != null && localDisplayConfig.failedImage != -1) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(localDisplayConfig.failedImage);
                }
            });
            return;
        }
        //如果本次请求没有单独配置DisplayConfig，则从全局gloable中配置
        if (mGlobalDisplayConfig != null && mGlobalDisplayConfig.failedImage != -1) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(mGlobalDisplayConfig.failedImage);
                }
            });
        }
    }

    protected abstract Bitmap fetchBitmap(BitmapRequest request);
}
