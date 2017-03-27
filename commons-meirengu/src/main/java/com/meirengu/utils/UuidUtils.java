package com.meirengu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by huoyan403 on 3/13/2017.
 */
public class UuidUtils {

    private static AtomicInteger IntId = new AtomicInteger(0);
    public static Integer getShortUuid() {
        Integer i = ((int) (System.currentTimeMillis() - 10000000000000L) / 1000) << 16L | (IntId.addAndGet(1));
        return Integer.parseInt((i+"").substring(1,10));
    }
    public static void main(String args[]){
        List<Integer> test01 = new ArrayList<Integer>();
        for(int i = 1; i<10000; i++){
            test01.add(getShortUuid());
            System.err.println(getShortUuid());
        }
    }
}
