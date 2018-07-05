package com.qicode.kakaxicm.imageloaderframwork.imageloader.policy;


import com.qicode.kakaxicm.imageloaderframwork.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2017/2/6 0006.
 * 顺序执行
 */

public class SerialLoadPolicy implements LoadPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request1.getSerialNo() - request2.getSerialNo();
    }
}
