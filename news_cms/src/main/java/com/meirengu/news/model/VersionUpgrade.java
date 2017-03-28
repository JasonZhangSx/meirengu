package com.meirengu.news.model;

import com.meirengu.model.BaseObject;
import java.util.Date;
 /*
  * VersionUpgrade 实体类
  * Mon Mar 27 11:05:06 CST 2017 建新
  */
public class VersionUpgrade  extends BaseObject {
	/** 产品APP版本升级索引ID */
	private Integer id;
	/** 产品APP_ID，01：android 02：ios */
	private Integer appId;
	/** 产品APP_CODE字符串，如：app.android.version.key */
	private String appCode;
	/** 产品APP名称 */
	private String appName;
	/** 产品APP渠道标识号 */
	private Integer appChannel;
	/** 是否是一个里程牌式的版本，默认为0，是则为1 */
	private Integer versionMilepost;
	/** 版本号 */
	private String versionCode;
	/** 上一个版本号 */
	private String versionCodeBefore;
	/** 版本大小 */
	private String versionSize;
	/** 更新地址 */
	private String downloadUrl;
	/** 升级信息标题 */
	private String updateTitle;
	/** 升级信息详情 */
	private String updateContent;
	/** 升级类型：1自检升级（自动升级、推送升级、检测升级），2强制更新，3增量更新 */
	private Integer updateType;
	/** 版本状态  1:最新版本，0：之前老版本 */
	private Integer status;
	/** 版本创建时间 */
	private Date createTime;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setAppId(Integer appId){
		this.appId = appId;
	}

	public Integer getAppId(){
		return appId;
	}

	public void setAppCode(String appCode){
		this.appCode = appCode;
	}

	public String getAppCode(){
		return appCode;
	}

	public void setAppName(String appName){
		this.appName = appName;
	}

	public String getAppName(){
		return appName;
	}

	public void setAppChannel(Integer appChannel){
		this.appChannel = appChannel;
	}

	public Integer getAppChannel(){
		return appChannel;
	}

	public void setVersionMilepost(Integer versionMilepost){
		this.versionMilepost = versionMilepost;
	}

	public Integer getVersionMilepost(){
		return versionMilepost;
	}

	public void setVersionCode(String versionCode){
		this.versionCode = versionCode;
	}

	public String getVersionCode(){
		return versionCode;
	}

	public void setVersionCodeBefore(String versionCodeBefore){
		this.versionCodeBefore = versionCodeBefore;
	}

	public String getVersionCodeBefore(){
		return versionCodeBefore;
	}

	public void setVersionSize(String versionSize){
		this.versionSize = versionSize;
	}

	public String getVersionSize(){
		return versionSize;
	}

	public void setDownloadUrl(String downloadUrl){
		this.downloadUrl = downloadUrl;
	}

	public String getDownloadUrl(){
		return downloadUrl;
	}

	public void setUpdateTitle(String updateTitle){
		this.updateTitle = updateTitle;
	}

	public String getUpdateTitle(){
		return updateTitle;
	}

	public void setUpdateContent(String updateContent){
		this.updateContent = updateContent;
	}

	public String getUpdateContent(){
		return updateContent;
	}

	public void setUpdateType(Integer updateType){
		this.updateType = updateType;
	}

	public Integer getUpdateType(){
		return updateType;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

}
