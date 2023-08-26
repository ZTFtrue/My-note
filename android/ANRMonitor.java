package com.ztftrue.app;


import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;

import java.util.HashMap;
import java.util.Map;

/**
 * 来源: <a href="https://blog.csdn.net/rzleilei/article/details/123252533">来自这里</a>
 * https://github.com/bytebeats/Anr-Monitor
 */
public class ANRMonitor {

    final static String TAG = "anr";

    public static void init(Context context) {
        //开关
        if (true) {
            return;
        }
        ANRMonitor anrMonitor = new ANRMonitor();
        anrMonitor.start(context);
        Log.i(TAG, "ANRMonitor init");
    }

    private void start(Context context) {
        Looper mainLooper = Looper.getMainLooper();
        // 这样设置开始执行消息开始的时候才进行 监测
        mainLooper.setMessageLogging(printer);
        // 新建一个自带 handler 的线程.
        HandlerThread handlerThread = new HandlerThread(ANRMonitor.class.getSimpleName());
        handlerThread.start();
        //获取 新建的 HandlerThread 的 handler
        threadHandler = new Handler(handlerThread.getLooper());
        // 获取当前线程, 也就是调用这个代码的线程.
        mCurrentThread = Thread.currentThread();
    }

    private long lastFrameTime = 0L;
    private Handler threadHandler;
    private long mSampleInterval = 40;
    private Thread mCurrentThread; // 获取当前线程, 也就是调用这个代码的线程.
    private final Map<String, String> mStackMap = new HashMap<>();

    private final Printer printer = new Printer() {
        @Override
        public void println(String it) {
            long currentTimeMillis = System.currentTimeMillis();
//            Looper 执行消息前输出 Dispatching , 执行消息后输出"<<<<< Finished to "
            //其实这里应该是一一对应判断的，但是由于是运行主线程中，所以Dispatching之后一定是Finished，依次执行
            if (it.contains("Dispatching")) {
                lastFrameTime = currentTimeMillis;// 记录开始记录时间
                //开始进行记录
                threadHandler.postDelayed(mRunnable, mSampleInterval);
                synchronized (mStackMap) {
                    mStackMap.clear();
                }
                return;
            }
            if (it.contains("Finished")) {
                long useTime = currentTimeMillis - lastFrameTime;
                //记录时间
                if (useTime > 20) {
                    //todo 要判断哪里耗时操作导致的
                    Log.i(TAG, "ANR:" + it + ", useTime:" + useTime);
                    //大于100毫秒，则打印出来卡顿日志
                    if (useTime > 100) {
                        synchronized (mStackMap) {
                            Log.i(TAG, "mStackMap.size:" + mStackMap.size());
                            for (String key : mStackMap.keySet()) {
                                Log.i(TAG, "key:" + key + ",state:" + mStackMap.get(key));
                            }
                            mStackMap.clear();
                        }
                    }
                }
                // 依次定时任务
                threadHandler.removeCallbacks(mRunnable);
            }
        }
    };


    // 定时任务
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            getStackTrace();
            threadHandler
                    .postDelayed(mRunnable, mSampleInterval);
        }
    };

    /**
     * 获取方法栈
     */
    protected void getStackTrace() {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : mCurrentThread.getStackTrace()) {
            stringBuilder
                    .append(stackTraceElement.toString())
                    .append("\n");
        }
        synchronized (mStackMap) {
            mStackMap.put(mStackMap.size() + "", stringBuilder.toString());
        }
    }

}