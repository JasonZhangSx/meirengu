package com.meirengu.uc.utils;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 * Created by huoyan403 on 5/10/2017.
 */
public class DataEncryptionUtilsTest {

    @Test
    public void testEncrypt() throws NoSuchAlgorithmException {

        String password = "296BD8C9DA72B12EC22F1347B7B11C05";//加密 解密 密钥
        //加密
        long stimeStarted=System.currentTimeMillis();
//        String content="{\"baseInfo\":{\"w\":720,\"activity\":\"1\",\"phonetype\":\"google||Galaxy Nexus\",\"fid\":\"10001\",\"netType\":2,\"dsn\":\"54E0F5258D3615A534DD49C508A94013\",\"h\":1184,\"sysVersion\":\"4.2.2\",\"version\":\"1.2.0.001\"}}";
        String content="6216618009001077164";

        System.out.println("加密前：" + content);
        byte[] encryptResult = DataEncryptionUtils.encrypt(content, password);
        String encryptResultStr = DataEncryptionUtils.parseByte2HexStr(encryptResult);
        System.out.println("加密后：" + encryptResultStr);
        //解密
        byte[] decryptFrom = DataEncryptionUtils.parseHexStr2Byte(encryptResultStr);
        byte[] decryptResult = DataEncryptionUtils.decrypt(decryptFrom, password);
        System.out.println("解密后：" + new String(decryptResult));
        long stimeEnd=System.currentTimeMillis();
        System.err.print(stimeEnd-stimeStarted);


    }

}
