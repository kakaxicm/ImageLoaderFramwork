package com.qicode.kakaxicm.imageloaderframwork.imageloader.config;

import com.qicode.kakaxicm.imageloaderframwork.imageloader.cache.Cache;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.cache.MemoryCache;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.policy.LoadPolicy;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.policy.ReversePolicy;

/**
 * Created by chenming on 2018/7/5
 * 全局配置
 */
public class ImageLoaderConfig {
    //缓存策略
    private Cache mCache = new MemoryCache();//默认仅支持默认缓存
    //加载策略
    private LoadPolicy mPolicy = new ReversePolicy();//默认后进先出加载策略
    //并发线程数
    private int mThreadCount = Runtime.getRuntime().availableProcessors();//默认cpu个数
    private DisplayConfig mDisplayConfig = new DisplayConfig();//默认空的displayConfig

    //单例
    private ImageLoaderConfig() {
    }

    public Cache getCache() {
        return mCache;
    }

    public LoadPolicy getPolicy() {
        return mPolicy;
    }

    public int getThreadCount() {
        return mThreadCount;
    }

    public DisplayConfig getDisplayConfig() {
        return mDisplayConfig;
    }

    //Builder模式
    public static class Builder {
        private ImageLoaderConfig mConfig;

        public Builder() {
            mConfig = new ImageLoaderConfig();
        }

        /**
         * 设置缓存类型
         *
         * @param cachePolicy
         * @return
         */
        public Builder setCachePolicy(Cache cachePolicy) {
            mConfig.mCache = cachePolicy;
            return this;
        }

        /**
         * 设置加载策略
         *
         * @param policy
         * @return
         */
        public Builder setLoadPolicy(LoadPolicy policy) {
            mConfig.mPolicy = policy;
            return this;
        }

        public Builder setThreadCount(int count) {
            mConfig.mThreadCount = count;
            return this;
        }

        /**
         * 设置加载过程中的图片
         *
         * @param resID
         * @return
         */
        public Builder setLoadingImage(int resID) {
            mConfig.mDisplayConfig.loadingImage = resID;
            return this;
        }

        /**
         * 设置加载过程中的图片
         *
         * @param resID
         * @return
         */
        public Builder setFaildImage(int resID) {
            mConfig.mDisplayConfig.faildImage = resID;
            return this;
        }

        /**
         * 返回
         *
         * @return
         */
        public ImageLoaderConfig build() {
            return mConfig;
        }

    }


}
