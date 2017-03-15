package com.meirengu.utils;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenEntityMysql {

    private String basePath;
    private String projectName;
    private String packageFather;
    private String packageModel;
    private String packageDao;
    private String packageService;
    private String packageServiceImpl;
    private String packageController;
    private String mapperPath;


    private String authorName;//作者名字

    //数据库连接
    private String url;
    private String username;
    private String password;
    private String driver;
    private String databaseName;

    //JDBC连接字段
    private  Connection conn;

    /**
     * 获取连接
     */
    private void getConnection(){
        try {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 获取所有表
     */
    private List<String> getTables(){
        String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '"+databaseName+"'";
        List<String> tableList = new ArrayList<>();
        try {
            PreparedStatement statement =  conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                tableList.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }

    /**
     * 获取表的对象
     * @param tableName
     */
    private List<Column> getColumns(String tableName){

        String sql = "select  column_name, column_comment, data_type from information_schema.columns where table_schema ='"+databaseName+"'  and table_name = '"+tableName+"';";
        List<Column> columnList = new ArrayList<>();
        try {
            PreparedStatement statement =  conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Column column = new Column(rs.getString("column_name"), rs.getString("column_comment"), rs.getString("data_type"));
                columnList.add(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columnList;
    }

    /**
     * 生成dao
     * @param tableName
     * @return
     */
    private String generateDao(String tableName){
        String packageName = packageFather + "." + packageDao;

        StringBuffer sb = new StringBuffer();
        //包部分
        sb.append("package " + packageName + ";\r\n");
        sb.append("import "+packageFather+"."+packageModel+"."+initcap(toHump(tableName))+";\n");
        sb.append("import com.meirengu.dao.BaseDao;");
        sb.append("\r\n");
        //注释部分
        sb.append("/**\n");
        sb.append(" * " + initcap(toHump(tableName)) + "Dao \n");
        sb.append(" * @author "+this.authorName+"\n");
        sb.append(" * @create " + new Date()+"\n");
        sb.append(" */\n");
        //实体部分
        sb.append("public interface ").append(initcap(toHump(tableName))+"Dao extends BaseDao<").append(initcap(toHump(tableName))).append(">{\r\n");
        sb.append("}\r\n");

        return sb.toString();
    }

    /**
     *
     * 生成model
     * @param tableName
     * @param columnList
     * @return
     */
    private String generateModel (String tableName, List<Column> columnList){
        String packageName = packageFather + "." + packageModel;
        StringBuffer sb = new StringBuffer();

        sb.append("package " + packageName + ";\r\n");
        sb.append("\r\n");
        sb.append("import java.util.Date;\r\n");
        sb.append("import java.sql.*;\r\n");
        //注释部分
        sb.append(" /*\r\n");
        sb.append("  * " + initcap(toHump(tableName)) + " 实体类\r\n");
        sb.append("  * " + new Date() + " " + this.authorName + "\n  */");
        //实体部分
        sb.append("\r\npublic class ").append(initcap(toHump(tableName))).append("{\r\n");
        processAllAttrs(sb, columnList);//属性
        processAllMethod(sb, columnList);//get set方法
        sb.append("}\r\n");

        return sb.toString();
    }

    /**
     * 生成服务层
     * @param tableName
     * @return
     */
    private String generateService(String tableName){

        String packageName = packageFather + "." + packageService;

        StringBuffer sb = new StringBuffer();
        //包部分
        sb.append("package " + packageName + ";\r\n");
        sb.append("import "+packageFather+"."+packageModel+"."+initcap(toHump(tableName))+";\n");
        sb.append("import com.meirengu.service.BaseService;\n");
        sb.append("\r\n");
        //注释部分
        sb.append("/**\n");
        sb.append(" * " + initcap(toHump(tableName)) + "服务接口 \n");
        sb.append(" * @author "+this.authorName+"\n");
        sb.append(" * @create " + new Date()+"\n");
        sb.append(" */\n");
        //实体部分
        sb.append("public interface ").append(initcap(toHump(tableName))+"Service extends BaseService<").append(initcap(toHump(tableName))).append(">").append("{\r\n");
        sb.append("}\r\n");

        return sb.toString();
    }

    /**
     * 生成服务实现层
     * @param tableName
     * @return
     */
    private String generateServiceImpl(String tableName){
        String packageName = packageFather + "." + packageService + "." + packageServiceImpl;

        StringBuffer sb = new StringBuffer();
        //包部分
        sb.append("package " + packageName + ";\r\n");
        sb.append("import "+packageFather+"."+packageModel+"."+initcap(toHump(tableName))+";\n");
        sb.append("import "+packageFather+"."+packageService+"."+initcap(toHump(tableName))+"Service;\n");
        sb.append("import com.meirengu.service.impl.BaseServiceImpl;\n");
        sb.append("import org.springframework.stereotype.Service;\n");
        sb.append("\r\n");
        //注释部分
        sb.append("/**\n");
        sb.append(" * " + initcap(toHump(tableName)) + "服务实现层 \n");
        sb.append(" * @author "+this.authorName+"\n");
        sb.append(" * @create " + new Date()+"\n");
        sb.append(" */\n");
        //实体部分
        sb.append("@Service\n");
        sb.append("public class ").append(initcap(toHump(tableName))+"ServiceImpl extends BaseServiceImpl<").append(initcap(toHump(tableName))).append("> implements ").append(initcap(toHump(tableName))).append("Service").append("{\r\n");
        sb.append("}\r\n");

        return sb.toString();
    }

    /**
     * 生成mybatis Mapper
     * @param tableName
     * @param columnList
     * @return
     */
    private String generateMybatisMapper(String tableName, List<Column> columnList){

        String packageName = packageFather + "." + packageDao;

        StringBuffer sb = new StringBuffer();
        //mapper头
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "         <!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis" +
                ".org/dtd/mybatis-3-mapper.dtd\" >\n" +
                "         <mapper namespace=\""+packageName+"."+initcap(toHump(tableName))+"Dao\" >\n");
        //sql table and columns
        sb.append("\t<sql id=\"table_name\">"+tableName+"</sql>\n");
        sb.append("\t<sql id=\"select_columns\">");
        StringBuffer columnsAs = new StringBuffer();
        StringBuffer cls = new StringBuffer();
        StringBuffer clsAsJava = new StringBuffer();
        String majorKey = "";
        for(int i = 0 ; i < columnList.size() ; i++ ){
            Column c = columnList.get(i);
            if(i < columnList.size()-1){
                columnsAs.append(c.getColumnName()).append(" as ").append(toHump(c.getColumnName())).append(", ");
                cls.append(c.getColumnName()).append(", ");
                clsAsJava.append("#{").append(toHump(c.getColumnName())).append("}, ");
            }else {
                columnsAs.append(c.getColumnName()).append(" as ").append(toHump(c.getColumnName()));
                cls.append(c.getColumnName());
                clsAsJava.append("#{").append(toHump(c.getColumnName())).append("}");
            }

            if(i == 0){
                majorKey = c.getColumnName();
            }
        }
        sb.append(columnsAs);
        sb.append("\n\t</sql>\n");
        //insert
        sb.append("\t<insert id=\"insert\" parameterType=\""+toHump(tableName)+"\" useGeneratedKeys=\"true\" " +
                "keyProperty=\""+toHump(majorKey)+"\" >\n" +
                "\t\tINSERT INTO <include refid=\"table_name\" />\n" +
                "\t\t\t("+cls.toString()+")\n" +
                "\t\tVALUES\n" +
                "\t\t\t("+clsAsJava.toString()+")\n" +
                "\t</insert>\n");
        //update
        sb.append("\t<update id=\"update\" parameterType=\""+toHump(tableName)+"\">\n" +
                "\t\tUPDATE <include refid=\"table_name\" />\n" +
                "\t\t<set>\n");
        for (int i = 0 ; i < columnList.size() ; i++ ){
            Column c = columnList.get(i);
            sb.append("\t\t\t<if test=\""+toHump(c.getColumnName())+" != null and "+toHump(c.getColumnName())+" != ''\">\n");
            sb.append("\t\t\t\t").append(c.getColumnName()).append("=#{").append(toHump(c.getColumnName())).append("},\n");
            sb.append("\t\t\t</if>\n");
        }
        sb.append("\t\t</set>\n");
        sb.append("\t\t<where>\n" +
                "\t\t\t"+majorKey+" = #{"+toHump(majorKey)+"}\n" +
                "\t\t</where>\n" +
                "\t</update>\n");
        //detail
        sb.append("\t<select id=\"detail\" parameterType=\"integer\" resultType=\""+toHump(tableName)+"\">\n" +
                "\t\tselect <include refid=\"select_columns\" /> from <include refid=\"table_name\"/>\n" +
                "\t\twhere "+majorKey+"=#{id}\n" +
                "\t</select>\n");

        //分页相关查询
        sb.append("\t<!-- 分页相关开始 -->\n" +
                "\t<select id=\"getByPage\" resultType=\"Map\" parameterType=\"Map\">\n" +
                "\t\tselect <include refid=\"select_columns\" />  FROM <include refid=\"table_name\" />\n" +
                "\t</select>\n" +
                "\n" +
                "\t<select id=\"getCount\" parameterType=\"Map\" resultType=\"Integer\">\n" +
                "\t\tselect count(1) FROM <include refid=\"table_name\" />\n" +
                "\t</select>\n" +
                "\t<!-- 分页相关结束 -->\n");
        sb.append("</mapper>");
        return sb.toString();
    }

    private void  generateFile(String content, String fileName){
        System.out.println(fileName);
        System.out.println(content);
        try{
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }

            FileOutputStream o = new FileOutputStream(file);
            o.write(content.getBytes("UTF-8"));
            o.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initParams(String basePath, String projectName, String packageFather, String packageModel, String packageDao, String packageService,
                           String packageServiceImpl, String packageController, String mapperPath, String authorName, String url,
                           String username, String password, String driver, String databaseName){

        this.basePath = basePath;
        this.projectName = projectName;
        this.packageFather = packageFather;
        this.packageModel = packageModel;
        this.packageDao = packageDao;
        this.packageService = packageService;
        this.packageServiceImpl = packageServiceImpl;
        this.packageController = packageController;
        this.mapperPath = mapperPath;
        this.authorName = authorName;
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
        this.databaseName = databaseName;
    }

    public void start(String tables){
        String path = basePath+"\\"+projectName+"\\src\\main\\";
        mapperPath = path + mapperPath;
        String javaPath = path + "java\\"+packageFather.replace(".", "\\")+"\\";
        getConnection();
        String[] tableArray = null;
        if(StringUtil.isEmpty(tables)){
            List<String> tableList = getTables();
            tableArray = new String[tableList.size()];
            tableList.toArray(tableArray);
        }else {
            tableArray = tables.split(",");
        }

        for (String tableName : tableArray){
            List<Column> cList = getColumns(tableName);

            String model = generateModel(tableName, cList);
            generateFile(model, javaPath+packageModel+"\\"+initcap(toHump(tableName))+".java");
            String dao = generateDao(tableName);
            generateFile(dao, javaPath+packageDao+"\\"+initcap(toHump(tableName))+"Dao.java");
            String service = generateService(tableName);
            generateFile(service, javaPath+packageService+"\\"+initcap(toHump(tableName))+"Service.java");
            String serviceImpl = generateServiceImpl(tableName);
            generateFile(serviceImpl, javaPath+packageService+"\\"+packageServiceImpl+"\\"+initcap(toHump(tableName))+"ServiceImpl.java");
            String mapper = generateMybatisMapper(tableName, cList);
            generateFile(mapper, mapperPath+initcap(toHump(tableName))+"Mapper.xml");
        }

    }

    /* 构造函数 */
    public GenEntityMysql() {

    }

    /**
     * 功能：生成所有属性
     * @param sb
     */
    private void processAllAttrs(StringBuffer sb, List<Column> columnList) {
        for (int i = 0; i < columnList.size(); i++) {
            sb.append("\t/** ").append(columnList.get(i).getColumnComment()).append(" */\n");
            sb.append("\tprivate " + sqlType2JavaType(columnList.get(i).getDataType()) + " " + toHump(columnList.get(i).getColumnName()) + ";\r\n");
        }

    }


    /**
     * 功能：生成所有方法
     * @param sb
     */
    private void processAllMethod(StringBuffer sb, List<Column> columnList) {
        sb.append("\n");

        for (int i = 0; i < columnList.size(); i++) {
            String columnName = columnList.get(i).getColumnName();
            String dataType = columnList.get(i).getDataType();
            sb.append("\tpublic void set" + initcap(toHump(columnName)) + "(" + sqlType2JavaType(dataType) + " " + toHump(columnName) + "){\r\n");
            sb.append("\t\tthis." + toHump(columnName) + " = " + toHump(columnName) + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\r\n");
            sb.append("\tpublic " + sqlType2JavaType(dataType) + " get" + initcap(toHump(columnName)) + "(){\r\n");
            sb.append("\t\treturn " + toHump(columnName) + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\r\n");
        }

    }

    /**
     * 功能：将输入字符串的首字母改成大写
     * @param str
     * @return
     */
    private String initcap(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 转换为驼峰式
     * @param str
     * @return
     */
    private String toHump(String str){
        StringBuffer sb = new StringBuffer();
        String[] arrays = str.split("_");
        for (int i = 0 ; i < arrays.length ; i++ ){
            if(i > 0){
                sb.append(initcap(arrays[i]));
            }else {
                sb.append(arrays[i]);
            }
        }

        return sb.toString();
    }

    /**
     * 功能：获得列的数据类型
     *
     * @param sqlType
     * @return
     */
    private String sqlType2JavaType(String sqlType) {

        if (sqlType.equalsIgnoreCase("bit")) {
            return "boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        } else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("bigint")
                || sqlType.equalsIgnoreCase("mediumint") || sqlType.equalsIgnoreCase("smallint")
                || sqlType.equalsIgnoreCase("tinyint")) {
            return "int";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        }

        return null;
    }

    class Column{

        private String columnName;
        private String columnComment;
        private String dataType;

        public Column(){

        }

        public Column(String columnName, String columnComment, String dataType) {
            this.columnName = columnName;
            this.columnComment = columnComment;
            this.dataType = dataType;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnComment() {
            return columnComment;
        }

        public void setColumnComment(String columnComment) {
            this.columnComment = columnComment;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
    }

    public static void main(String[] args) {

        GenEntityMysql gm = new GenEntityMysql();
        String basePath = System.getProperty("user.dir");
        String projectName = "trade";
        String packageFather = "com.meirengu.trade";
        String packageModel = "model";
        String packageDao = "dao";
        String packageService = "service";
        String packageServiceImpl = "impl";
        String packageController = "controller";
        String mapperPath = "resources\\mybatis\\";

        String authorName = "建新";//作者名字
        //数据库连接
        String url = "jdbc:mysql://192.168.0.135:3306/trade";
        String username = "dev";
        String password = "dev@1qa";
        String driver = "com.mysql.jdbc.Driver";
        String databaseName = "trade";
        gm.initParams(basePath, projectName, packageFather, packageModel, packageDao, packageService,
                packageServiceImpl, packageController, mapperPath, authorName, url,
                username, password, driver, databaseName);
        gm.start("");
        //System.out.println(s);
        /*System.out.println(Class.class.getClass().getResource("/").getPath());
        Properties p = System.getProperties();
        System.out.println(System.getProperty("user.dir"));*/
    }

}
