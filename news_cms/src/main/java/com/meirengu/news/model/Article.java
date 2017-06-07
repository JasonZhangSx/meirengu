package com.meirengu.news.model;

import com.meirengu.model.BaseObject;
import java.util.Date;
 /*
  * Article 实体类
  * Tue Jun 06 10:57:35 CST 2017 建新
  */
public class Article  extends BaseObject {
	/** 索引id */
	private Integer articleId;
	/** 分类id */
	private Integer acId;
	/** 跳转链接 */
	private String articleUrl;
	/** 文章标签 */
	private String articleLabel;
	/** 是否显示，0为否，1为是，默认为1 */
	private Integer articleShow;
	/** 排序 */
	private Integer articleSort;
	/** 文章头图 */
	private String articleImg;
	/** 标题 */
	private String articleTitle;
	/** 内容 */
	private String articleContent;
	/** 是否为轮播图文章，0为非轮播图，1为轮播图 */
	private Integer articleIsBanner;
	/** 0为不推荐，1为推荐 */
	private Integer articleIsCommend;
	/** 是否发布，1为发布，0为未发布 */
	private Integer articleIsPublish;
	/** 发布时间 */
	private Date articleTime;
	/** 删除标识  0为删除，1为未删除 */
	private Integer flag;
	/** 创建人 */
	private Integer createUser;
	/** 创建人姓名 */
	private String createUserName;
	/** 创建人头像 */
	private String createUserImg;
	/** 创建人类型 1会员用户 2系统用户 3医生用户 */
	private Integer createUserType;
	/** 创建时间 */
	private Date createTime;

	public void setArticleId(Integer articleId){
		this.articleId = articleId;
	}

	public Integer getArticleId(){
		return articleId;
	}

	public void setAcId(Integer acId){
		this.acId = acId;
	}

	public Integer getAcId(){
		return acId;
	}

	public void setArticleUrl(String articleUrl){
		this.articleUrl = articleUrl;
	}

	public String getArticleUrl(){
		return articleUrl;
	}

	public void setArticleLabel(String articleLabel){
		this.articleLabel = articleLabel;
	}

	public String getArticleLabel(){
		return articleLabel;
	}

	public void setArticleShow(Integer articleShow){
		this.articleShow = articleShow;
	}

	public Integer getArticleShow(){
		return articleShow;
	}

	public void setArticleSort(Integer articleSort){
		this.articleSort = articleSort;
	}

	public Integer getArticleSort(){
		return articleSort;
	}

	public void setArticleImg(String articleImg){
		this.articleImg = articleImg;
	}

	public String getArticleImg(){
		return articleImg;
	}

	public void setArticleTitle(String articleTitle){
		this.articleTitle = articleTitle;
	}

	public String getArticleTitle(){
		return articleTitle;
	}

	public void setArticleContent(String articleContent){
		this.articleContent = articleContent;
	}

	public String getArticleContent(){
		return articleContent;
	}

	public void setArticleIsBanner(Integer articleIsBanner){
		this.articleIsBanner = articleIsBanner;
	}

	public Integer getArticleIsBanner(){
		return articleIsBanner;
	}

	public void setArticleIsCommend(Integer articleIsCommend){
		this.articleIsCommend = articleIsCommend;
	}

	public Integer getArticleIsCommend(){
		return articleIsCommend;
	}

	public void setArticleIsPublish(Integer articleIsPublish){
		this.articleIsPublish = articleIsPublish;
	}

	public Integer getArticleIsPublish(){
		return articleIsPublish;
	}

	public void setArticleTime(Date articleTime){
		this.articleTime = articleTime;
	}

	public Date getArticleTime(){
		return articleTime;
	}

	public void setFlag(Integer flag){
		this.flag = flag;
	}

	public Integer getFlag(){
		return flag;
	}

	public void setCreateUser(Integer createUser){
		this.createUser = createUser;
	}

	public Integer getCreateUser(){
		return createUser;
	}

	public void setCreateUserName(String createUserName){
		this.createUserName = createUserName;
	}

	public String getCreateUserName(){
		return createUserName;
	}

	public void setCreateUserImg(String createUserImg){
		this.createUserImg = createUserImg;
	}

	public String getCreateUserImg(){
		return createUserImg;
	}

	public void setCreateUserType(Integer createUserType){
		this.createUserType = createUserType;
	}

	public Integer getCreateUserType(){
		return createUserType;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

 }
