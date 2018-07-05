package com.qicode.kakaxicm.imageloaderframwork.imageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenming on 2018/7/5
 * 根据url的前缀，提供两种Loader：URLLoader和FileLoader
 */
public class LoaderManager {
    //非典型单例，注册Loader，key为schema
    private Map<String, Loader> mLoaderMap = new HashMap<>();

    private static LoaderManager mInstance = new LoaderManager();

    private LoaderManager() {
        register("http", new UrlLoader());
        register("https", new UrlLoader());
        register("file", new FileLoader());
    }

    public static LoaderManager getInstance() {
        return mInstance;
    }

    private void register(String schema, Loader loader) {
        mLoaderMap.put(schema, loader);
    }

    public Loader getLoader(String schema) {
        if (mLoaderMap.containsKey(schema)) {
            return mLoaderMap.get(schema);
        }
        return new NoneLoader();
    }
}
