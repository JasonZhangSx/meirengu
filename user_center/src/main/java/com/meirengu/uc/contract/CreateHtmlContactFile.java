package com.meirengu.uc.contract;

import com.meirengu.uc.utils.LogUtils;
import com.meirengu.uc.utils.NumberToCN;
import com.meirengu.utils.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.mapu.themis.api.common.StampType;
import org.mapu.themis.api.request.contract.ContactHtmlCreateFileRequest;
import org.mapu.themis.api.response.contract.ContactHtmlCreateFileResponse;
import rop.thirdparty.com.alibaba.fastjson.JSONArray;
import rop.thirdparty.com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 合同文件生成并盖章
 */
public class CreateHtmlContactFile extends ThemisClientInit {

	public Map CreateHtmlContactFile(Map<String,String> map) throws IOException {

		ContactHtmlCreateFileRequest request=new ContactHtmlCreateFileRequest();
		/**模版类型，TPL_TYPE_HTML使用html文件方式,TPL_TYPE_URL使用url地址方式*/
		request.setTplType(ContactHtmlCreateFileRequest.TPL_TYPE_HTML);//使用html内容上传方式
		/**html内容*/
		String html = FileUtils.readFileToString(new File("E://contract001.html"),"UTF-8");
		//合同内容替换

		html = html.replace("{signatureDate}","<em>"+ DateUtils.getPrintDate()+"</em>");//签署日期
		html = html.replace("{signatureArea}","国萃园1111室");//签署地点

		html = html.replace("{investors}",map.get("investors"));//投资人
		html = html.replace("{investorIdCard}",map.get("investorIdCard"));//投资人身份证号
		html = html.replace("{investorArea}",map.get("investorArea"));//投资人地址
		html = html.replace("{investmentAmount}",String.valueOf(map.get("investmentAmount")));//投资人投资金额

		html = html.replace("{legalRepresentative}",map.get("legalRepresentative"));//法定代表人
		html = html.replace("{projectCompanyArea}",map.get("projectCompanyArea"));//项目公司地址
		Integer manageCompanyValuation = Integer.valueOf(String.valueOf(map.get("manageCompanyValuation")));
		html = html.replace("{manageCompanyValuation}",String.valueOf(manageCompanyValuation/10000));//项目公司估值资金 （万元）
		Integer projectSideValuation = Integer.valueOf(String.valueOf(map.get("projectSideValuation")));
		html = html.replace("{projectSideValuation}",String.valueOf(projectSideValuation/10000));//项目方估值（万元）

		html = html.replace("{platformArea}","平台地址");//平台地址
		html = html.replace("{platformLegalRepresentative}","平台法定代表人");//平台法定代表人

		html = html.replace("{managementCompany}","北京科技管理公司");//管理公司
		html = html.replace("{managementCompanyArea}","北京科技管理公司地址");//管理公司地址
		html = html.replace("{managementCompanyLegalRepresentative}","北京科技管理公司法定代表人");//管理公司法定代表人

		html = html.replace("{projectOwner}",map.get("projectOwner"));//项目方
		html = html.replace("{projectCompany}",map.get("projectCompany"));//项目公司
		html = html.replace("{projectBonusAndReturnStatement}",map.get("projectBonusAndReturnStatement"));//项目分红及回报说明

		Integer manageMoney = Integer.valueOf(String.valueOf(map.get("manageMoney")));
		html = html.replace("{manageMoney}",String.valueOf(manageMoney/10000));//项目公司注册资金 （万元）1
		html = html.replace("{manageDate}","<em>"+String.valueOf(map.get("manageYear"))+"</em>年<em>"+String.valueOf(map.get("manageMonth"))+"</em>月<em>"+String.valueOf(map.get("manageDay"))+"</em>日");//项目公司成立于1

		html = html.replace("{platformServiceFee}",String.valueOf(map.get("platformServiceFee"))+"%");//平台服务费1

		html = html.replace("{shareholder}",map.get("shareholder"));//股东1
		html = html.replace("{shareholderIdCard}",map.get("shareholderArea"));//股东身份证号1
		html = html.replace("{shareholderArea}",map.get("shareholderArea"));//股东地址1
		html = html.replace("{guarantor}",map.get("guarantor"));//担保人1
		html = html.replace("{guarantorIdCard}",map.get("guarantorIdCard"));//担保人身份证号1
		html = html.replace("{guarantorArea}",map.get("guarantorArea"));//担保人地址1

		html = html.replace("{investmentTerm}",String.valueOf(map.get("investmentTerm")));//投资期限1
		html = html.replace("{investmentAmount}",String.valueOf(map.get("investmentAmount")));//投资金额1

		String investmentAmountUpCase = NumberToCN.number2CNMontrayUnit(new BigDecimal(String.valueOf(map.get("investmentAmount"))));
		html = html.replace("{investmentAmountUpCase}",investmentAmountUpCase);//人民币大写2

		html = html.replace("{investmentStalls}",map.get("investmentStalls"));//投资档位1

		String flag = String.valueOf(map.get("flag"));
		if("1".equals(flag)){
			html = html.replace("{exitAndRepurchase}","届满<em>"+String.valueOf(map.get("exitAndRepurchaseDate"))+"</em>个月时，医院退还用户投资本金及利息");//项目公司注册资金 （万元）//exitAndRepurchaseDateNum
		}
		if("2".equals(flag)){
			html = html.replace("{exitAndRepurchase}","每<em>"+String.valueOf(map.get("exitAndRepurchaseDate"))+"</em>月返还利息，届满<em>"+String.valueOf(map.get("exitAndRepurchaseDateNum"))+"</em>个月时，项目方退还用户投资本金。");//项目公司注册资金 （万元）//exitAndRepurchaseDateNum
		}


		/**用户签章*/
		JSONArray stampParams=new JSONArray();

		JSONObject projectOwnerChapter=new JSONObject();
		projectOwnerChapter.put("id","projectOwnerChapter");//对应网页标签上的id，须确认此id为唯一,且找到的标签为行级（inline）或行块（inline-block）元素
		projectOwnerChapter.put("stampType", StampType.ENTERPRISE_01.getCode());//章类型，企业章类型1，其它还有待开发
		projectOwnerChapter.put("stampText", map.get("projectCompany"));//"项目公司盖章"
		stampParams.add(projectOwnerChapter);

		JSONObject platformChapter=new JSONObject();
		platformChapter.put("id","platformChapter");//对应网页标签上的id，须确认此id为唯一,且找到的标签为行级（inline）或行块（inline-block）元素
		platformChapter.put("stampType", StampType.ENTERPRISE_01.getCode());//章类型，企业章类型1，其它还有待开发
		platformChapter.put("stampText", "北京美融城医疗科技有限公司 ");//平台刻章
		stampParams.add(platformChapter);

		JSONObject managementCompanyChapter=new JSONObject();
		managementCompanyChapter.put("id","managementCompanyChapter");//对应网页标签上的id，须确认此id为唯一,且找到的标签为行级（inline）或行块（inline-block）元素
		managementCompanyChapter.put("stampType", StampType.ENTERPRISE_01.getCode());//章类型，企业章类型1，其它还有待开发
		managementCompanyChapter.put("stampText", "北京科技管理公司");
		stampParams.add(managementCompanyChapter);

		request.setStampParams(stampParams);
		request.setTaget(html);
		ContactHtmlCreateFileResponse response = getClient().getContactHtmlCreateFile(request);
		Map result = new HashedMap();
		if(response.isSuccess()&&response.getByteArrayFile()!=null){
			result.put("flag","success");
			result.put("msg",response);
			String filePath = "E://";
			String fileName = "contact_"+map.get("itemId")+"_"+map.get("levelId")+"_"+map.get("userId")+".pdf";
			response.writeFile(filePath+fileName);
		}else{
			result.put("flag","failed");
			result.put("msg",response);
		}
		LogUtils.logResponse(response);

		return  result;
	}
}
