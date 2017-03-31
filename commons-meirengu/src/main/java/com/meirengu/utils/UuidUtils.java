package com.meirengu.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by huoyan403 on 3/13/2017.
 */
public class UuidUtils {

    private static final Logger LOG = LoggerFactory.getLogger(UuidUtils.class);
    private static final InstaIdGenerator instaIdGenerator;

    static {
        instaIdGenerator = new InstaIdGenerator();
    }

//    private static AtomicInteger IntId = new AtomicInteger(0);
//        return ((System.currentTimeMillis() - 10000000000000L) / 1000) << 16L | (IntId.addAndGet(1));

    public static int getShortUuid() {
        Long uuid = instaIdGenerator.nextId(ThreadLocalRandom.current().nextLong());
        return Integer.parseInt((uuid+"").substring((uuid+"").length()-9));
    }
    public static void main(String args[]){
        Set set = new HashSet();
        for(int i = 1; i<1000000; i++){
            set.add(getShortUuid());
            System.err.println(getShortUuid());
        }
        System.err.print(set.size());
    }
}
