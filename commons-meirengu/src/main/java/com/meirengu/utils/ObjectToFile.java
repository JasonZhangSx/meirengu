package com.meirengu.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 3/31/2017.
 */
public class ObjectToFile {

    private static final Logger logger = LoggerFactory.getLogger(ObjectToFile.class);

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

    public static Object readArray(String filePath){
        try {
            FileInputStream freader;
//            freader = new FileInputStream(filePath);
//            ObjectInputStream objectInputStream = new ObjectInputStream(freader);
//            logger.info("ObjectToFile.readObject successful:{}");
//            return  ( List<String[]>) objectInputStream.readObject();


            FileInputStream fileInputStream = new FileInputStream(filePath);

            logger.info("ObjectToFile.readObject successful:{}");
            return   fileInputStream.read();

        } catch (FileNotFoundException e) {
            logger.info("ObjectToFile.readObject throws FileNotFoundException:{}",e.getMessage());
            return null;
        } catch (IOException e) {
            logger.info("ObjectToFile.readObject throws IOException:{}",e.getMessage());
            return null;
//        } catch (ClassNotFoundException e) {
//            logger.info("ObjectToFile.readObject throws ClassNotFoundException:{}",e.getMessage());
//            return null;
        }

    }

    public static void writeArray(List<String[]> list,String filePath) {
        try {

            FileOutputStream outStream = new FileOutputStream(filePath);
            StringBuffer sb = new StringBuffer();
            sb.append("[");
            int i = 0;
            for(String[] arr:list){
                i++;
                sb.append("{");
                for (int index = 0; index < arr.length; index++)
                {
                    sb.append("\"");
                    sb.append(arr[index]);
                    sb.append("\"");
                    if(index<arr.length-1){
                        sb.append(",");
                    }
                }
                sb.append("}");
                if(i<arr.length-1){
                    sb.append(",");
                }
            }
            sb.append("]");
            byte[] contentInBytes = sb.toString().getBytes();
            outStream.write(contentInBytes);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
//            objectOutputStream.writeObject(list);
            outStream.close();
            logger.info("ObjectToFile.writeObject successful:{}");
        } catch (FileNotFoundException e) {
            logger.info("ObjectToFile.writeObject failed:{}",e.getMessage());
        } catch (IOException e) {
            logger.info("ObjectToFile.writeObject failed:{}",e.getMessage());
        }
    }

    public static List<Map<String,String>> readObject(String filePath){
        try {
            FileInputStream freader;
            freader = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);

            logger.info("ObjectToFile.readObject successful:{}");
            return  ( List<Map<String,String>>) objectInputStream.readObject();

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

    public static void writeFile(String string,byte[] bytes) {
        FileOutputStream fileOutputStream=null;
        BufferedOutputStream bufferedOutputStream=null;
        try{
            fileOutputStream=new FileOutputStream(string);
            bufferedOutputStream =new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(bytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                bufferedOutputStream.flush();
                fileOutputStream.close();
                bufferedOutputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public static void main(String args[]){
        ObjectToFile of = new ObjectToFile();
        Map map = new HashMap();
        map.put(200736768,100000);
        map.put(164691969,100000);

        List list = new ArrayList();
        list.add(map);
         of.writeObject(list,"E://1.txt");
            of.readArray("E://1.txt");
//        List<String[]> arr = new ArrayList<>();
//        String[] ch = { "a", "b","c"};
//        String[] ch1 = { "1", "2","3"};
//        arr.add(ch);
//        arr.add(ch1);
//        of.writeArray(arr,"E://1.txt");
//        of.writeFile("E://1.txt",arr.toString().getBytes());


    }

}
