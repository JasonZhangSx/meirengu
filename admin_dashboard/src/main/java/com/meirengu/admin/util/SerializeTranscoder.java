package com.meirengu.admin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/10 19:15.
 */
public class SerializeTranscoder<M extends Serializable> {
    private final static Logger logger = LoggerFactory.getLogger(SerializeTranscoder.class);

    public void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                logger.info("Unable to close " + closeable, e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<M> deserializeToList(byte[] in) throws IOException, ClassNotFoundException {
        List<M> list = new ArrayList<>();
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                while (true) {
                    M m = (M) is.readObject();
                    if (m == null) {
                        break;
                    }
                    list.add(m);
                }
                is.close();
                bis.close();
            }
        }catch (EOFException e){
        } catch (ClassNotFoundException e) {
            logger.error("Caught CNFE decoding %d bytes of data");
            throw new ClassNotFoundException("Caught CNFE decoding %d bytes of data",e);
        }  finally {
            close(is);
            close(bis);
        }
        return  list;
    }


    @SuppressWarnings("unchecked")
    public byte[] listToSerialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        List<M> values = (List<M>) value;
        byte[] results = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            for (M m : values) {
                os.writeObject(m);
            }
            // os.writeObject(null);
            os.close();
            bos.close();
            results = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            close(os);
            close(bos);
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    public byte[] objectToSerialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] result = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            M m = (M) value;
            os.writeObject(m);
            os.close();
            bos.close();
            result = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            close(os);
            close(bos);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public M deserializeToObject(byte[] in) throws IOException, ClassNotFoundException {
        M result = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                result = (M) is.readObject();
                is.close();
                bis.close();
            }
        } catch (IOException e) {
            logger.error("Caught IOException decoding %d bytes of data");
            throw new IOException("Caught IOException decoding %d bytes of data",e);
        } catch (ClassNotFoundException e) {
            logger.error("Caught CNFE decoding %d bytes of data");
            throw new ClassNotFoundException("Caught CNFE decoding %d bytes of data",e);
        } finally {
            close(is);
            close(bis);
        }
        return result;
    }
}
