package com.qicode.kakaxicm.imageloaderframwork.imageloader.policy;

import com.qicode.kakaxicm.imageloaderframwork.imageloader.request.BitmapRequest;

/**
 * Created by chenming on 2018/7/5
 */
public interface LoadPolicy {
    /**
     * 两个BItmapRequest进行优先级比较
     *
     * @param request1
     * @param request2
     * @return
     */
    int compareto(BitmapRequest request1, BitmapRequest request2);
}
