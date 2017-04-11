package com.meirengu.uc.contract;

import com.meirengu.uc.utils.LogUtils;
import org.apache.commons.io.FileUtils;
import org.mapu.themis.api.common.StampType;
import org.mapu.themis.api.request.contract.ContactHtmlCreateFileRequest;
import org.mapu.themis.api.response.contract.ContactHtmlCreateFileResponse;
import rop.thirdparty.com.alibaba.fastjson.JSONArray;
import rop.thirdparty.com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * 合同文件生成并盖章
 */
public class CreateHtmlContactFile extends ThemisClientInit {

	public  void CreateHtmlContactFile() throws IOException {
		ContactHtmlCreateFileRequest request=new ContactHtmlCreateFileRequest();
		/**模版类型，TPL_TYPE_HTML使用html文件方式,TPL_TYPE_URL使用url地址方式*/
		request.setTplType(ContactHtmlCreateFileRequest.TPL_TYPE_HTML);//使用html内容上传方式
		/**html内容*/
		String html = FileUtils.readFileToString(new File("E://contract001.html"),"UTF-8");
		//合同内容替换

		html = html.replace("{signatureDate}","_2017_年_5_月_18_日");//签署日期
		html = html.replace("{signatureArea}","国萃园1111室");//签署地点
		html = html.replace("{investors}","投资人");//投资人
		html = html.replace("{investorIdCard}","投资人身份证号");//投资人身份证号
		html = html.replace("{investorArea}","投资人地址");//投资人地址
		html = html.replace("{projectOwner}","项目方");//项目方
		html = html.replace("{projectCompany}","项目公司");//项目公司
		html = html.replace("{legalRepresentative}","法定代表人");//法定代表人
		html = html.replace("{projectCompanyArea}","项目公司地址");//项目公司地址
		html = html.replace("{shareholder}","股东");//股东
		html = html.replace("{shareholderIdCard}","股东身份证号");//股东身份证号
		html = html.replace("{shareholderArea}","股东地址");//股东地址
		html = html.replace("{guarantor}","担保人");//担保人
		html = html.replace("{guarantorIdCard}","担保人身份证号");//担保人身份证号
		html = html.replace("{guarantorArea}","担保人地址");//担保人地址
		html = html.replace("{platformArea}","平台地址");//平台地址
		html = html.replace("{platformLegalRepresentative}","平台法定代表人");//平台法定代表人
		html = html.replace("{managementCompany}","管理公司");//管理公司
		html = html.replace("{managementCompanyArea}","管理公司地址");//管理公司地址
		html = html.replace("{managementCompanyLegalRepresentative}","管理公司法定代表人");//管理公司法定代表人
		html = html.replace("{manageDate}","项目公司成立时间");//项目公司成立时间
		html = html.replace("{manageMoney}","项目公司注册资金 （万元）");//项目公司注册资金 （万元）
		html = html.replace("{manageCompanyValuation}","项目公司估值 （万元）");//项目公司估值资金 （万元）
		html = html.replace("{investmentAmoun}","投资人投资金额");//投资人投资金额
		html = html.replace("{investmentAmountUpCase}","人民币大写");//人民币大写
		html = html.replace("{projectSideValuation}","项目方估值");//项目方估值
		html = html.replace("{investmentStalls}","投资档位");//投资档位
		html = html.replace("{investmentAmount}","投资金额");//投资金额
		html = html.replace("{platformServiceFee}","平台服务费");//平台服务费
		html = html.replace("{investmentTerm}","投资期限");//投资期限
		html = html.replace("{exitAndRepurchaseDate}","届满日期");//届满日期
		html = html.replace("{projectBonusAndReturnStatement}","项目分红及回报说明");//项目分红及回报说明

		/**用户签章*/
		JSONArray stampParams=new JSONArray();

		JSONObject projectOwnerChapter=new JSONObject();
		projectOwnerChapter.put("id","projectOwnerChapter");//对应网页标签上的id，须确认此id为唯一,且找到的标签为行级（inline）或行块（inline-block）元素
		projectOwnerChapter.put("stampType", StampType.ENTERPRISE_01.getCode());//章类型，企业章类型1，其它还有待开发
		projectOwnerChapter.put("stampText", "项目公司盖章");
		stampParams.add(projectOwnerChapter);

		JSONObject platformChapter=new JSONObject();
		platformChapter.put("id","platformChapter");//对应网页标签上的id，须确认此id为唯一,且找到的标签为行级（inline）或行块（inline-block）元素
		platformChapter.put("stampType", StampType.ENTERPRISE_01.getCode());//章类型，企业章类型1，其它还有待开发
		platformChapter.put("stampText", "北京医疗科技有限公司");
		stampParams.add(platformChapter);

		JSONObject managementCompanyChapter=new JSONObject();
		managementCompanyChapter.put("id","managementCompanyChapter");//对应网页标签上的id，须确认此id为唯一,且找到的标签为行级（inline）或行块（inline-block）元素
		managementCompanyChapter.put("stampType", StampType.ENTERPRISE_01.getCode());//章类型，企业章类型1，其它还有待开发
		managementCompanyChapter.put("stampText", "北京科技管理公司");
		stampParams.add(managementCompanyChapter);

		request.setStampParams(stampParams);
		request.setTaget(html);
		ContactHtmlCreateFileResponse response = getClient().getContactHtmlCreateFile(request);
		if(response.isSuccess()&&response.getByteArrayFile()!=null){
			response.writeFile("E://contract001.pdf");
		}
		LogUtils.logResponse(response);
	}

}
