package com.meirengu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by huoyan403 on 3/13/2017.
 */
public class UuidUtils {
    public static String[] chars = new String[]
            {
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
            };
private static AtomicInteger InitId = new AtomicInteger();
    public static Integer getShortUuid()
    {

        //return (int) (System.currentTimeMillis()-1000000000000L/1000)<<16|(InitId.addAndGet(1));
        StringBuffer stringBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++)
        {
            String str      = uuid.substring(i * 4, i * 4 + 4);
            int strInteger  = Integer.parseInt(str, 16);
            stringBuffer.append(chars[strInteger % 0xA]);
        }
        stringBuffer.append((int)(Math.random()*10));
        return Integer.valueOf(stringBuffer.toString());
    }

//    public static String getUuid(){
//        return UUID.randomUUID().toString().replace("-", "");
//    }
    public static void main(String args[]){
        List<Integer> test01 = new ArrayList<Integer>();
        for(int i = 1; i<1000000; i++){
            test01.add(getShortUuid());
            //System.err.println(getShortUuid());
        }

        for(int i = 1; i<999999; i++){
            for(int j = 1; j<999999; j++) {
                if(i == j){
                    break;
                }
                if(test01.get(i).equals(test01.get(j))){
                    System.err.println(test01.get(j));
                    System.out.print("i == "+i+"+j == +"+j);
                }
            }

        }
    }
}
