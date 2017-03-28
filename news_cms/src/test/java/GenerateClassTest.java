import com.meirengu.utils.GenEntityMysql;

/**
 * 生成mysql文件
 *
 * @author 建新
 * @create 2017-03-14 14:54
 */
public class GenerateClassTest {

    public static void main(String[] args) {

        GenEntityMysql gm = new GenEntityMysql();
        String basePath = System.getProperty("user.dir");
        String projectName = "news_cms";
        String packageFather = "com.meirengu.news";
        String packageModel = "model";
        String packageDao = "dao";
        String packageService = "service";
        String packageServiceImpl = "impl";
        String packageController = "controller";
        String mapperPath = "resources\\mybatis\\";

        String authorName = "建新";//作者名字
        //数据库连接
        String url = "jdbc:mysql://192.168.0.135:3306/cms";
        String username = "dev";
        String password = "dev@1qa";
        String driver = "com.mysql.jdbc.Driver";
        String databaseName = "cms";
        gm.initParams(basePath, projectName, packageFather, packageModel, packageDao, packageService,
                packageServiceImpl, packageController, mapperPath, authorName, url,
                username, password, driver, databaseName);
        gm.start("version_upgrade");
    }
}