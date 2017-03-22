package com.meirengu.cf.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.meirengu.exception.BusinessException;
import com.meirengu.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-17 19:57
 */
public class FileUtils {

    private final static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI1Gqnvoh764Aq";
    private static String accessKeySecret = "AoxsjlX4iRWihQposkwNCdOVwTxxAk";
    private static String bucketName = "test-mrg-img";
    private static final String callbackUrl = "";

    private static OSSClient ossClient ;

    private static void init(){
        if(ossClient == null){
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }
    }

   /* public static void upload*/

    public static Map<String,String> createFile(Map<String,String> map, Iterator<String> iter,
                                                MultipartHttpServletRequest multipartHttpServletRequest, String folderName) throws IOException{

        //取得上传文件1
        List<MultipartFile> fileList = multipartHttpServletRequest.getFiles(iter.next());

        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        for (MultipartFile file : fileList){
            if (file != null) {
                //取得当前上传文件的文件名称
                String myFileName = file.getOriginalFilename();
                //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                if (!"".equals(myFileName.trim())) {
                    //重新生成文件名，生成规则：当前时间戳+随机数
                    String fileName = String.valueOf(System.currentTimeMillis() / 1000).concat(String.valueOf(new Random().nextInt(201)));
                    myFileName = myFileName.substring(myFileName.lastIndexOf("."));
                    client.putObject(bucketName, folderName + fileName + myFileName,file.getInputStream());
                    String[] size=file.getName().split("_");
                    String key = null;
                    if (size.length!=1){
                        key=size[size.length-3];
                    }else{
                        key=size[0];
                    }
                    String value=fileName+myFileName;
                    if (map.get(key)!=null){
                        map.put(key,map.get(key).concat(","+value));
                    }else {
                        map.put(key,value);
                    }
//                map.put(size[size.length-3],fileName+myFileName);
                }
            }
        }
        client.shutdown();
        return map;
    }

    public static void testUpload(HttpServletRequest request, String fileName, String folderName) throws IOException {
        init();
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
            MultipartFile file = req.getFile(fileName);
            if (file != null) {
                String originalFilename = file.getOriginalFilename();
                long currentTime = new Date().getTime() + new Random().nextInt(10000);
                String suffix  = originalFilename.substring(originalFilename.lastIndexOf("."));
                String pictureName = currentTime + suffix;
                ossClient.putObject(bucketName, folderName+"/"+pictureName, file.getInputStream());
            }
        }
    }

    private static void callback(String fileName) throws IOException {

        String content = "Hello OSS";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName,
                new ByteArrayInputStream(content.getBytes()));

        Callback callback = new Callback();
        callback.setCallbackUrl(callbackUrl);
        callback.setCallbackHost("oss-cn-hangzhou.aliyuncs.com");
        callback.setCallbackBody("{\\\"bucket\\\":${bucket},\\\"object\\\":${object},"
                + "\\\"mimeType\\\":${mimeType},\\\"size\\\":${size},"
                + "\\\"my_var1\\\":${x:var1},\\\"my_var2\\\":${x:var2}}");
        callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
        callback.addCallbackVar("x:var1", "value1");
        callback.addCallbackVar("x:var2", "value2");
        putObjectRequest.setCallback(callback);

        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        byte[] buffer = new byte[1024];
        putObjectResult.getCallbackResponseBody().read(buffer);
        putObjectResult.getCallbackResponseBody().close();

    }

    /*protected boolean VerifyOSSCallbackRequest(HttpServletRequest request, String ossCallbackBody) throws NumberFormatException, IOException
    {
        boolean ret = false;
        String autorizationInput = new String(request.getHeader("Authorization"));
        String pubKeyInput = request.getHeader("x-oss-pub-key-url");
        byte[] authorization = BinaryUtil.fromBase64String(autorizationInput);
        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
        String pubKeyAddr = new String(pubKey);
        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/") && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/"))
        {
            System.out.println("pub key addr must be oss addrss");
            return false;
        }
        String retString = executeGet(pubKeyAddr);
        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
        retString = retString.replace("-----END PUBLIC KEY-----", "");
        String queryString = request.getQueryString();
        String uri = request.getRequestURI();
        String decodeUri = java.net.URLDecoder.decode(uri, "UTF-8");
        String authStr = decodeUri;
        if (queryString != null && !queryString.equals("")) {
            authStr += "?" + queryString;
        }
        authStr += "\n" + ossCallbackBody;
        ret = doCheck(authStr, authorization, retString);
        return ret;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ossCallbackBody = GetPostBody(request.getInputStream(), Integer.parseInt(request.getHeader("content-length")));
        boolean ret = VerifyOSSCallbackRequest(request, ossCallbackBody);
        System.out.println("verify result:" + ret);
        System.out.println("OSS Callback Body:" + ossCallbackBody);
        if (ret)
        {
            response(request, response, "{\"Status\":\"OK\"}", HttpServletResponse.SC_OK);
        }
        else
        {
            response(request, response, "{\"Status\":\"verdify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static boolean doCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            boolean bverify = signature.verify(sign);
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }*/

}
