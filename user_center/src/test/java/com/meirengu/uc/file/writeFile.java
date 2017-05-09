package com.meirengu.uc.file;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by huoyan403 on 5/9/2017.
 */
public class writeFile {

    @Test
    public void writeFile() throws IOException {

        StringBuffer stringBuffer = new StringBuffer();
        for(int i=1000;i<2000;i++){
            FileOutputStream fileOutputStream = new FileOutputStream("E://1.txt");
            stringBuffer.append("1380000"+i+",123456,127.0.0.1,1 \n");
            fileOutputStream.write(stringBuffer.toString().getBytes());
        }
    }
}
