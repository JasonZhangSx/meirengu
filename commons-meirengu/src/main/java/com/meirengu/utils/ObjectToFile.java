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

    public void writeObject( List<HashMap<String,String>> list,String filePath) {
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

    public List<HashMap<String,String>> readObject(String filePath){
        try {
            FileInputStream freader;
            freader = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);

            logger.info("ObjectToFile.readObject successful:{}");
            return  ( List<HashMap<String,String>>) objectInputStream.readObject();

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


    public static void main(String args[]){
        ObjectToFile of = new ObjectToFile();
        Map map = new HashMap();
        map.put("userId",123123123);
        map.put("money",1000000);

        Map map1 = new HashMap();
        map1.put("userId",343453);
        map1.put("money",3000000);

        List list = new ArrayList();
        list.add(map);
        list.add(map1);

        of.writeObject(list,"E://1.txt");
        System.err.print(of.readObject("E://1.txt"));
    }

}
