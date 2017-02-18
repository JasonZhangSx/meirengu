
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.pay.vo.WxRefundSendData;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.MD5Util;
import com.meirengu.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;



public class WebContent1 {
    /**
     * 读取一个网页全部内容
     */
   /* private JList contentpanel;
    private String url;
    private HashMap<String, String> hm;*/

  /*  WebContent1(JList contentarea, String weburl) {
        this.contentpanel = contentarea;
        this.url = weburl;
        hm = new HashMap<String, String>();

    }*/

    public String getOneHtml(final String htmlurl) throws IOException {
        URL url;
        String temp;
        final StringBuffer sb = new StringBuffer();
        try {
            url = new URL(htmlurl);
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));// 读取网页全部内容
            while ((temp = in.readLine()) != null) {
                sb.append(temp);
            }
            in.close();
        } catch (final MalformedURLException me) {
            System.out.println("你输入的URL格式有问题！请仔细输入");
            me.getMessage();
            throw me;
        } catch (final IOException e) {
            e.printStackTrace();
            throw e;
        }
        return sb.toString();
    }

    /**
     *
     * @param s
     * @return 获得网页标题
     */
    public String getTitle(final String s) {
        String regex;
        String title = "";
        final List<String> list = new ArrayList<String>();
        regex = "<title>.*?</title>";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher ma = pa.matcher(s);
        while (ma.find()) {
            list.add(ma.group());
        }
        for (int i = 0; i < list.size(); i++) {
            title = title + list.get(i);
        }
        return outTag(title);
    }

    /**
     *
     * @param s
     * @return 获得链接
     */
    public List<String> getLink(final String s) {
        String regex;
        final List<String> list = new ArrayList<String>();
        regex = "<a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(s);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    /**
     *
     * @param s
     * @return 获得脚本代码
     */
    public List<String> getScript(final String s) {
        String regex;
        final List<String> list = new ArrayList<String>();
        regex = "<script.*?</script>";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(s);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    /**
     *
     * @param s
     * @return 获得CSS
     */
    public List<String> getCSS(final String s) {
        String regex;
        final List<String> list = new ArrayList<String>();
        regex = "<style.*?</style>";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(s);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    /**
     *
     * @param s
     * @return 去掉标记
     */
    public String outTag(final String s) {
        return s.replaceAll("<.*?>", "");
    }

    public String outTagContent(final String s) {
        return s.replaceAll("(<.*?>)&&(^<br>)", "");// 对内容保留换行
    }

    /**
     *
     * @param s
     * @return 获取的文章标题及内容
     */
    public HashMap<String, String> getFromSina(final String s) {
        final HashMap<String, String> hm = new HashMap<String, String>();
        final StringBuffer sb = new StringBuffer();
        String html = "";
        System.out.println("\n------------------开始读取网页(" + s + ")--------------------");
        try {
            html = getOneHtml(s);
        } catch (final Exception e) {
            e.getMessage();
        }
        // System.out.println(html);
        System.out.println("------------------读取网页(" + s + ")结束--------------------\n");
        System.out.println("------------------分析(" + s + ")结果如下--------------------\n");
        String title = outTag(getTitle(html));
        title = title.replaceAll("_新浪博客", "");
        String tag = getTag(html);
        //final Pattern pa=Pattern.compile("<div class=\"adv_news\">(.*?)((\r\n)*)(.*?)((\r\n)*)(.*?)</div>",Pattern.DOTALL);
        // final Pattern pa = Pattern.compile("<div class=\"original\">(.*?)</p></div>", Pattern.DOTALL);
        final Pattern pa = Pattern.compile("<div class=\"adv_news\">(.*?)</div>", Pattern.DOTALL);
        final Matcher ma = pa.matcher(html);
        while (ma.find()) {
            sb.append(ma.group());
        }
        String temp = sb.toString();

        temp = temp.replaceAll("(<br>)+?", "\n");// 转化换行
        temp = temp.replaceAll("<p><em>.*?</em></p>", "");// 去图片注释
        temp = temp.replaceAll("&nbsp", " ");
        temp = temp.replaceAll("<a .*?</a>", " ");

        hm.put("title", title);
        hm.put("content", outTag(temp));
        hm.put(("tag"), tag);
        return hm;

    }


    /**
     *
     * @param s url
     * @return 获取某个div下的a标签链接
     */
    public ArrayList<List<String>> getDivA(final String s,String clas) {
        final ArrayList<List<String>> links = new ArrayList<List<String>>();
        final StringBuffer sb = new StringBuffer();
        String html = "";
        try {
            // html = getOneHtml(s);

            HttpUtil.HttpResult doGet = HttpUtil.doGet(s);
            html=doGet.getContent();
        } catch (final Exception e) {
            e.getMessage();
        }


        final Pattern pa = Pattern.compile("<div class=\"Hero_info\" id=\""+clas+"\">(.*?)</div>", Pattern.DOTALL);
        final Matcher ma = pa.matcher(html);
        while (ma.find()) {
            sb.append(ma.group());
        }
        String temp = sb.toString();
        List<String> link = getLink(temp);
        for(int i=0;i<link.size();i=i+3){
            ArrayList<String> list=new ArrayList<String>();
            list.add(link.get(i));
            if(i+1 >= link.size()){
                links.add(list);
                break;
            }else{
                list.add(link.get(i+1));
            }
            if(i+2 >= link.size()){
                links.add(list);
                break;
            }else{
                list.add(link.get(i+2));
            }
            links.add(list);
        }
        return links;
    }

    /***** 线程同步方法 *************/
    /*
     * public synchronized void GetWebContent() { hm=getFromSina(url); String temp="   "+hm.get("content")+"\n"; WebInfo
     * info; /*if(temp.length()>300) info=new WebInfo(hm.get("title"),temp.substring(0,300)+"........",hm.get("tag"));
     * else info=new WebInfo(hm.get("title"),temp,hm.get("tag"));
     */
    /*
     * info=new WebInfo(hm.get("title"),hm.get("content"),hm.get("tag")); DefaultListModel
     * model=(DefaultListModel)contentpanel.getModel(); model.add(model.getSize(),info);
     * 
     * }
     */

    /**
     *
     * @param s 测试一组网页，针对雅虎知识堂
     */

    public String getTag(final String s) {
        String regex;
        String tag = "";
        final List<String> list = new ArrayList<String>();
        regex = "<td class=\"blog_tag\">(.*?)</td>";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher ma = pa.matcher(s);
        while (ma.find()) {
            list.add(ma.group());

        }
        for (int i = 0; i < list.size(); i++) {
            tag = tag + list.get(i);
        }
        ;
        tag = tag.replaceAll("<script.*?</script>", " ");// 除去script

        tag = tag.replaceAll("<.*?>", " ");// 除去HTML标签
        tag = tag.replaceAll("标签：", " ");// 除去前两个字
        tag = tag.trim();// 除去空格

        return tag;
    }

    // public String GetResult()
    public String replayce(String str) {
        String s = str;
        String regex;
        regex = "/.*? ";
        s = s.replaceAll(regex, "*");
        return s;
    }

    public String SaveContent(String str) {
        // System.out.println(str);
        try {
            FileOutputStream fos = new FileOutputStream("F:\\学习和工作\\分词技术\\savearticle\\3.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            String saveString = str.replaceAll("\n", "\r\n");
            // System.out.println("保存的是："+saveString);
            osw.write(saveString);
            osw.flush();
            osw.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        return str;
    }

    public boolean Sqlinsert(String s) {
        boolean b = false;
        System.out.println("进入sql");
        String tag[];
        tag = s.split(" ");
        try {

            System.out.println("进入try");
            Class.forName("com.mysql.jdbc.Driver"); // mysql数据库驱动
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test?      "
                    + "useUnicode=true&characterEncoding=gbk", "admin", "");// 连接数据库
            System.out.println("连接成功");
            for (int i = 0; i < tag.length; i++) {
                String command = "insert into tag(Tag_ID,Tag_tag) values(?,?)";
                PreparedStatement prepared = con.prepareCall(command);
                prepared.clearParameters();
                prepared.setString(1, "0" + i);// 此处数据库中为自动增长
                prepared.setString(2, tag[i]);
                prepared.executeUpdate();
            }

            System.out.println("成功");
            b = true;
        } catch (SQLException ae) {
        } catch (ClassNotFoundException cnfe) {
        }

        return b;
    }

    public static void main(final String args[]) throws IOException {
        String s = "{\"code\":200,\"data\":null,\"msg\":\"成功\"}";
        JSONObject json = JSONObject.parseObject(s);
        System.out.print(json.get("code"));
    }

    public static String get32MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            //mdTemp.update(s.getBytes("UTF-8"));
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}