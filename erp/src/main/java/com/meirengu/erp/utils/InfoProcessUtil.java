package com.meirengu.erp.utils;

import com.meirengu.utils.StringUtil;

/**
 * Created by huoyan403 on 5/9/2017.
 */
public class InfoProcessUtil {

    public static String generateDefaultMobile(String mobile){
        StringBuffer sb = new StringBuffer();
        if (mobile != null) {
            char[] chars = mobile.toCharArray();
            if (chars.length == 11) {
                sb.append(chars[0]).append(chars[1]).append(chars[2]).append("*")
                        .append(chars[7]).append(chars[8]).append(chars[9]).append(chars[10]);
            }

        }
        return sb.toString();
    }


    public static String generateDefaultIdCard(String idcard){
        StringBuffer sb = new StringBuffer();
        if (!StringUtil.isEmpty(idcard)) {
            char[] chars = idcard.toCharArray();
            if (chars.length == 18) {
                sb.append(chars[0]).append(chars[1]).append(chars[2]).append(chars[3]).append(chars[4]).append(chars[5]).append("*")
                        .append(chars[14]).append(chars[15]).append(chars[16]).append(chars[17]);
            }

        }
        return sb.toString();
    }

    public static String generateDefaultBankIdCard(String BankIdcard){
        StringBuffer sb = new StringBuffer();
        if (!StringUtil.isEmpty(BankIdcard)) {
            char[] chars = BankIdcard.toCharArray();
            sb.append(chars[0]).append(chars[1]).append(chars[2]).append(chars[3]).append(chars[4]).append(chars[5]);
            for(int i = 0;i<8;i++){
                sb.append("*");
            }
            if (chars.length == 15) {
                sb.append(chars[11]).append(chars[12]).append(chars[13]).append(chars[14]);
            }
            if (chars.length == 16) {
                sb.append(chars[12]).append(chars[13]).append(chars[14]).append(chars[15]);
            }
            if (chars.length == 17) {
                sb.append(chars[13]).append(chars[14]).append(chars[15]).append(chars[16]);
            }
            if (chars.length == 18) {
                sb.append(chars[14]).append(chars[15]).append(chars[16]).append(chars[17]);
            }
        }
        return sb.toString();
    }
}
