package com.qicode.kakaxicm.imageloaderframwork.imageloader.policy;


import com.qicode.kakaxicm.imageloaderframwork.imageloader.request.BitmapRequest;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class ReversePolicy implements LoadPolicy {
    @Override
    public int compareto(BitmapRequest request1, BitmapRequest request2) {
        return request2.getSerialNo() - request1.getSerialNo();
    }
}
