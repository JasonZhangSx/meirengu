package com.meirengu.uc.service;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-24 14:08
 */
public class NotifyTest {

    public static void main(String[] args){
        StringBuffer s = new StringBuffer();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 100000; i++){
            //s.append(i).append(",");
            list.add(i);
        }
        List<Integer> l = list.subList(100, 200);
        System.out.println(s);
    }
}
