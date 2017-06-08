package com.meirengu.uc.utils;

import com.meirengu.utils.JacksonUtil;
import org.junit.Test;

import java.util.Map;

/**
 * Created by huoyan403 on 6/8/2017.
 */
public class JsonTest {
    @Test
    public void testJson(){

        String s = "{\"unionid\":\"o1V5sw9L_-t5EKTU_BlIi_6mZlB0\",\"screen_name\":\"无心\",\"city\":\"衡水\",\"accessToken\":\"x4UDowrfW1hvT6ZZ1iscM9AQ-N1Zm8lJgqDN7bOi6hzP6JWCtdArJKdXqAs7N2Or5uiBpy21AziHO2QUEnGVlBMG1EJlKlyNlXPKFSCq91M\",\"refreshToken\":\"2LtnnzRc1TkfZy2Yaq1zZGhPMJCqvW9rgl1GQvSRwRXUVn1bHUu2535_0T7CpHwEpTXCm4J-QVCj6uIFP8I9N5jC_o0lzOHdZMczPORDsY4\",\"gender\":\"男\",\"province\":\"河北\",\"openid\":\"oXzRQ1G-1-6OnOAQ53kh0_4RF5-A\",\"profile_image_url\":\"http://wx.qlogo.cn/mmopen/gWicbXPiajJnibLAkibicY7vbmwgJHapAiabXVELOOWIxOkBONhEcZDKiaW1dvKdGU4EgAv5JaPuXg0EcksdON5appk79h7CnIEX3Bw/0\",\"country\":\"中国\",\"access_token\":\"x4UDowrfW1hvT6ZZ1iscM9AQ-N1Zm8lJgqDN7bOi6hzP6JWCtdArJKdXqAs7N2Or5uiBpy21AziHO2QUEnGVlBMG1EJlKlyNlXPKFSCq91M\",\"iconurl\":\"http://wx.qlogo.cn/mmopen/gWicbXPiajJnibLAkibicY7vbmwgJHapAiabXVELOOWIxOkBONhEcZDKiaW1dvKdGU4EgAv5JaPuXg0EcksdON5appk79h7CnIEX3Bw/0\",\"name\":\"无心\",\"uid\":\"o1V5sw9L_-t5EKTU_BlIi_6mZlB0\",\"expiration\":\"1494329386185\",\"language\":\"zh_CN\",\"expires_in\":\"1494329386185\"}";

        Map map = JacksonUtil.readValue(s,Map.class);
        System.err.print(map);
    }
}
