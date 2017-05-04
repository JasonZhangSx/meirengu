package com.meirengu.uc.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huoyan403 on 5/4/2017.
 */
public class ThreadPoolSingleton {

    private ThreadPoolSingleton(){}

    private static ExecutorService executorService;

    private static synchronized void syncInit() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        }
    }

    public static ExecutorService getInstance(){
        if (executorService == null) {
            syncInit();
        }
        return executorService;
    }
}
