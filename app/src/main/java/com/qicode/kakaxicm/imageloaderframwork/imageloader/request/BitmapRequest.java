package com.qicode.kakaxicm.imageloaderframwork.imageloader.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.qicode.kakaxicm.imageloaderframwork.imageloader.ImageLoader;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.config.DisplayConfig;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.policy.LoadPolicy;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.util.MD5Utils;

import java.lang.ref.SoftReference;

/**
 * Created by chenming on 2018/7/5
 * 图片请求封装
 * imguri :图片路径
 * imgageview：target
 * displayConfig:加载过程中的一些图片配置
 * loadpolicy:加载策略
 */
public class BitmapRequest implements Comparable<BitmapRequest> {
    private SoftReference<ImageView> mImageViewRef;

    private String imageUri;//文件或者网络地址
    private String imageUriMd5;//cache的key，磁盘缓存文件名
    //下载完成监听
    public ImageLoader.ImageListener imageListener;
    //加载策略,取默认的加载策略
    private LoadPolicy mLoadLoadPolicy = ImageLoader.getInstance().getImageLoaderConfig().getPolicy();//默认全局的加载策略
    private DisplayConfig mDisplayConfig = ImageLoader.getInstance().getImageLoaderConfig().getDisplayConfig();//局部的DisplayConfig,默认全局的dispayconfig

    /**
     * 编号,用于优先队列排序
     */
    private int mSerialNumb;

    public BitmapRequest(ImageView imageView, String imageUri, DisplayConfig displayConfig, LoadPolicy loadPolicy, ImageLoader.ImageListener listener) {
        this.mImageViewRef = new SoftReference<>(imageView);
        imageView.setTag(imageUri);//设置tag,保持请求和响应一致
        this.imageUri = imageUri;
        this.imageUriMd5 = MD5Utils.toMD5(imageUri);
        //配置局部生效的displayConfig
        if (displayConfig != null) {
            this.mDisplayConfig = displayConfig;
        }
        //配置局部生效的加载策略
        if(loadPolicy != null){
            this.mLoadLoadPolicy = loadPolicy;
        }
        if (imageListener != null) {
            this.imageListener = listener;
        }
    }

    public int getSerialNo() {
        return mSerialNumb;
    }

    public void setSerialNo(int serialNo) {
        this.mSerialNumb = serialNo;
    }

    public String getImageUriMd5() {
        return imageUriMd5;
    }

    public String getImageUri() {
        return imageUri;
    }

    public DisplayConfig getDisplayConfig() {
        return mDisplayConfig;
    }

    public ImageView getImageView() {
        return mImageViewRef.get();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitmapRequest that = (BitmapRequest) o;
        if (mSerialNumb != that.mSerialNumb) return false;
        return mLoadLoadPolicy != null ? mLoadLoadPolicy.equals(that.mLoadLoadPolicy) : that.mLoadLoadPolicy == null;
    }

    @Override
    public int hashCode() {
        int result = mLoadLoadPolicy != null ? mLoadLoadPolicy.hashCode() : 0;
        result = 31 * result + mSerialNumb;
        return result;
    }

    /**
     * 交给加载策略实现排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(@NonNull BitmapRequest o) {
        return mLoadLoadPolicy.compareto(o, this);
    }
}
