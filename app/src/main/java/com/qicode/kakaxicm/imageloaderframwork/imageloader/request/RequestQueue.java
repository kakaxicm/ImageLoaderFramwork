package com.qicode.kakaxicm.imageloaderframwork.imageloader.request;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenming on 2018/7/5
 * 请求队列管理
 * 优先级队列:Queue<Bitmapqueue>
 * 分发器:Dispatcher轮询从队列中取执行bp加载线程
 */
public class RequestQueue {
    //阻塞优先级队列
    private BlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<>();
    //并发线程数量
    private int mDispatcherCount = 4;
    //序列号生成器，线程安全
    private AtomicInteger serialNumbGenerator = new AtomicInteger(0);

    //执行线程
    private RequestDispatcher[] mDispatchers;

    public RequestQueue(int count) {
        mDispatcherCount = count;
        mDispatchers = new RequestDispatcher[mDispatcherCount];
    }

    public void start() {
        for (int i = 0; i < mDispatcherCount; i++) {
            RequestDispatcher dispatcher = new RequestDispatcher(mRequestQueue);
            mDispatchers[i] = dispatcher;
            dispatcher.start();
        }
    }

    /**
     * 添加请求
     * @param request
     */
    public void addRequest(BitmapRequest request){
        if(!mRequestQueue.contains(request)){
            //给请求进行编号
            request.setSerialNo(serialNumbGenerator.incrementAndGet());
            mRequestQueue.add(request);
        }
    }
}
