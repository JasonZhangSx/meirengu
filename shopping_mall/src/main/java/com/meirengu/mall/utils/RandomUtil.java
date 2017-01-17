package com.meirengu.mall.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.cglib.core.HashCodeCustomizer;

import java.util.UUID;

/**
 * 随机码生成规则
 *
 * @author 建新
 * @create 2017-01-13 15:18
 */
public class RandomUtil {

    /**
     * 获取时间戳
     * @param length 长度
     * @return
     */
    public static String genTimeStamp(int length){
        String currentStamp = String.valueOf(System.currentTimeMillis());
        return currentStamp.substring(0, length);
    }

    /**
     * 生成随机码
     * @param length 长度
     * @return
     */
    public static synchronized String genRandomCode(int length){
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while(i < length){
            int num = RandomUtils.nextInt(0, 9);
            sb.append(num);
            i++;
        }
        return sb.toString();
    }

    public static void main(String[] args){
        //String code = RandomUtil.genRandomCode(8);
        int i = 0;
        while (i < 50){
            //System.out.println(i+":"+code);
            //System.out.println(RandomUtil.generateShortUuid());
            System.out.println(RandomUtil.genTimeStamp(10));
            i++;
        }

    }

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };


    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }
}
