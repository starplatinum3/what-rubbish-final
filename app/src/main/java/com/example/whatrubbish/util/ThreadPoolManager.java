package com.example.whatrubbish.util;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

    private static ThreadPoolManager instance = new ThreadPoolManager();

    public static ThreadPoolManager getInstance() {
        return instance;
    }

    //线程池
    private ThreadPoolExecutor threadPoolExecutor;
    //请求队列
    private LinkedBlockingQueue<Future<?>> service = new LinkedBlockingQueue<>();

    //初始化
    private ThreadPoolManager() {

        threadPoolExecutor = new ThreadPoolExecutor(4, 10, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), handler);

        threadPoolExecutor.execute(runnable);
    }

    //请求队列里的线程任务排队到线程池执行
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            while (true) {
                FutureTask futureTask = null;

                try {
                    Log.e("myThreadPook", "service size " + service.size());
                    futureTask = (FutureTask) service.take();
                    //Log.d("futureTask", "run: "+futureTask);
                    Log.e("myThreadPook", "池  " + threadPoolExecutor.getPoolSize());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (futureTask != null) {
                    threadPoolExecutor.execute(futureTask);
                }
            }
        }
    };

    public static void run(Thread thread) {
        //Thread thread1 = new Thread();
        //ThreadPoolManager.getInstance().execute(new FutureTask<>(thread),null),null);
        FutureTask<Object> objectFutureTask = new FutureTask<>(thread, null);
        ThreadPoolManager.getInstance().execute(objectFutureTask, null);

        //ThreadPoolManager.getInstance().execute(new FutureTask<>(thread1),null),null);
    }

    //执行的线程任务,延时多少秒执行
    public <T> void execute(final FutureTask<T> futureTask, Object delayed) {


        if (futureTask != null) {
            try {
                //延时执行
                if (delayed != null) {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        public void run() {
                            try {
                                service.put(futureTask);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }, (long) delayed);
                } else {
                    service.put(futureTask);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    //    拒绝机制
    private RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            try {
                service.put(new FutureTask<Object>(runnable, null));
                //
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
//————————————————
//        版权声明：本文为CSDN博主「慕容屠苏」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/kerryqpw/article/details/64129583
 