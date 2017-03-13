import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;

import java.io.*;
import java.util.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2016/12/28 13:54.
 */
public class test {
    //http://test-mrg-img.oss-cn-beijing.aliyuncs.com/20150805_160543_mr1438774787269.jpg
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI1Gqnvoh764Aq";
    private static String accessKeySecret = "AoxsjlX4iRWihQposkwNCdOVwTxxAk";

    private static String bucketName = "test-mrg-img";
    private static String key = "asfasf.jpg";

    public static void main(String[] args) throws IOException {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String style = "image/resize,m_fixed,w_100,h_100";
        GetObjectRequest request = new GetObjectRequest(bucketName, key);
        request.setProcess(style);
        ObjectMetadata o = client.getObject(request, new File("asfasf-resize.jpg"));
        System.out.println(o);
//        client.putObject(o.)
        //        List<Bucket> list = client.listBuckets();
//        System.out.println(list.size());
//        InputStream is = new ByteArrayInputStream("Hello OSS".getBytes());
//        File file = new File("C:\\Users\\xiaoyang\\Desktop\\20150805_160543_mr1438774787269.jpg");
//        client.putObject(bucketName, key,file );
//        System.out.println("Object：" + key + "存入OSS成功。");
////        ObjectListing objectListing = client.listObjects(bucketName);
////        List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
////        System.out.println("您有以下Object：");
////        for (OSSObjectSummary object : objectSummary) {
////            System.out.println("\t" + object.getKey());
////        }
////        try {
////
////            /**
////             * Note that there are two ways of uploading an object to your bucket, the one
////             * by specifying an input stream as content source, the other by specifying a file.
////             */
////
////            /*
////             * Upload an object to your bucket from an input stream
////             */
////            System.out.println("Uploading a new object to OSS from an input stream\n");
////            String content = "Thank you for using Aliyun Object Storage Service";
////            client.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));
////
////            /*
////             * Upload an object to your bucket from a file
////             */
////            System.out.println("Uploading a new object to OSS from a file\n");
////            client.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));
////
////            /*
////             * Download an object from your bucket
////             */
////            System.out.println("Downloading an object");
////            OSSObject object = client.getObject(new GetObjectRequest(bucketName, key));
////            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
////            displayTextInputStream(object.getObjectContent());
////
////        } catch (OSSException oe) {
////            System.out.println("Caught an OSSException, which means your request made it to OSS, "
////                    + "but was rejected with an error response for some reason.");
////            System.out.println("Error Message: " + oe.getErrorCode());
////            System.out.println("Error Code:       " + oe.getErrorCode());
////            System.out.println("Request ID:      " + oe.getRequestId());
////            System.out.println("Host ID:           " + oe.getHostId());
////        } catch (ClientException ce) {
////            System.out.println("Caught an ClientException, which means the client encountered "
////                    + "a serious internal problem while trying to communicate with OSS, "
////                    + "such as not being able to access the network.");
////            System.out.println("Error Message: " + ce.getMessage());
////        } finally {
////            /*
////             * Do not forget to shut down the client finally to release all allocated resources.
////             */
////            client.shutdown();
////        }
    }
//public static void main(String[] args) {
//    long lasting = System.currentTimeMillis();
//    try {
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        System.out.println(loader.getResource("test.xml"));
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource("test.xml").getPath());
//        File f = new File(Thread.currentThread().getContextClassLoader().getResource("test.xml").getPath());
//        SAXReader reader = new SAXReader();
//        Document doc = reader.read(f);
//        Element root = doc.getRootElement();
//        Element foo;
//        for (Iterator i = root.elementIterator("VALUE"); i.hasNext();) {
//            foo = (Element) i.next();
//            System.out.print("车牌号码:" + foo.elementText("NO"));
//            System.out.println("车主地址:" + foo.elementText("ADDR"));
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}
    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("oss-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("0123456789011234567890\n");
        writer.close();

        return file;
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("\t" + line);
        }
        System.out.println();

        reader.close();
    }

}
