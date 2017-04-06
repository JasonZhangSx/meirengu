package com.meirengu.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 3/31/2017.
 */
public class ObjectToFile {

    private static final Logger logger = LoggerFactory.getLogger(ObjectToFile.class);

    /**
     * 读取文件 采用writeObject方式写入
     * @param filePath
     * @return
     */
    public static  List<Map<String,String>>  readObject(String filePath){
        try {
            FileInputStream freader;
            freader = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);

            logger.info("ObjectToFile.readObject successful:{}");
            return  (  List<Map<String,String>> ) objectInputStream.readObject();

        } catch (FileNotFoundException e) {
            logger.info("ObjectToFile.readObject throws FileNotFoundException:{}",e.getMessage());
            return null;
        } catch (IOException e) {
            logger.info("ObjectToFile.readObject throws IOException:{}",e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            logger.info("ObjectToFile.readObject throws ClassNotFoundException:{}",e.getMessage());
            return null;
        }
    }

    /**
     * 写入文件 采用ObjectOutputStream 写入
     * @param list
     * @param filePath
     */
    public static void writeObject( List<Map<String,String>> list,String filePath) {
        try {

            FileOutputStream outStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);

            objectOutputStream.writeObject(list);
            outStream.close();
            logger.info("ObjectToFile.writeObject successful:{}");
        } catch (FileNotFoundException e) {
            logger.info("ObjectToFile.writeObject failed:{}",e.getMessage());
        } catch (IOException e) {
            logger.info("ObjectToFile.writeObject failed:{}",e.getMessage());
        }
    }

    /**
     * 写入文件 采用明文写入
     * @param list
     * @param filePath
     */
    public static void writeArray(List<String[]> list,String filePath) {
        try {

            FileOutputStream outStream = new FileOutputStream(filePath);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<list.size();i++){
                String[] arr = list.get(i);
                for (int index = 0; index < arr.length; index++)
                {
                    sb.append(arr[index]);
                    if(index<arr.length-1){
                        sb.append(",");
                    }
                }
                if(i<list.size()-1){
                    sb.append("|");
                }
            }
            byte[] contentInBytes = sb.toString().getBytes();
            outStream.write(contentInBytes);
            outStream.close();
            logger.info("ObjectToFile.writeObject successful:{}");
        } catch (FileNotFoundException e) {
            logger.info("ObjectToFile.writeObject failed:{}",e.getMessage());
        } catch (IOException e) {
            logger.info("ObjectToFile.writeObject failed:{}",e.getMessage());
        }
    }


}
