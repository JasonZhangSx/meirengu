package com.meirengu.uc.service.impl;

import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.uc.dao.ContractDao;
import com.meirengu.uc.dao.UserAddressDao;
import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.model.Contract;
import com.meirengu.uc.model.User;
import com.meirengu.uc.model.UserAddress;
import com.meirengu.uc.service.ContactService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.uc.utils.NumberToCN;
import com.meirengu.uc.vo.request.AddressVO;
import com.meirengu.utils.DateUtils;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.JacksonUtil;
import com.meirengu.utils.OSSFileUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.mapu.themis.api.common.PersonalIdentifer;
import org.mapu.themis.api.common.StampType;
import org.mapu.themis.api.request.contract.ContactHtmlCreateFileRequest;
import org.mapu.themis.api.request.contract.ContractFileDownloadUrlRequest;
import org.mapu.themis.api.request.contract.ContractFilePreservationCreateRequest;
import org.mapu.themis.api.request.contract.ContractFileViewUrlRequest;
import org.mapu.themis.api.response.contract.ContactHtmlCreateFileResponse;
import org.mapu.themis.api.response.contract.ContractFileDownloadUrlResponse;
import org.mapu.themis.api.response.contract.ContractFileViewUrlResponse;
import org.mapu.themis.api.response.preservation.PreservationCreateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rop.request.UploadFile;
import rop.thirdparty.com.alibaba.fastjson.JSONArray;
import rop.thirdparty.com.alibaba.fastjson.JSONObject;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.meirengu.uc.utils.ThemisClientInit.getClient;

/**
 * Created by huoyan403 on 4/11/2017.
 */
@Service
public class ContactServiceImpl implements ContactService {


    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    private UserAddressDao userAddressDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private AddressServiceImpl addressServiceImpl;

    @Override
    public Result CreateIncomeContactFile(Map<String,String> map) {

        HttpUtil.HttpResult hr = null;
        Result result = new Result();
        String url = ConfigUtil.getConfig("URI_GET_CONTACT_INFO");
        String urlAppend = url+"?item_id="+map.get("itemId")+"&level_id="+map.get("levelId");
        logger.info("ContactServiceImpl.CreateContactFile send get >> uri :{}, params:{}", new Object[]{url});
        try {
            hr = HttpUtil.doGet(urlAppend);
        } catch (Exception e) {
            logger.error("ContactServiceImpl.CreateContactFile send get >> params:{}, exception:{}", new Object[]{e});
        }
        if( hr!=null && hr.getStatusCode()==200){
            Map<String,Object> mapData = new HashedMap();
            mapData = JacksonUtil.readValue(hr.getContent(),Map.class);
            if(mapData!=null){
                Map<String,String> data = (Map)mapData.get("data");
                data.put("itemId",map.get("itemId"));
                data.put("levelId",map.get("levelId"));
                data.put("userId",map.get("userId"));
                //获取投资人信息
                User user = userDao.retrieveByUserId(Integer.parseInt(map.get("userId")));
                if(user ==null){
                    return this.setResult(StatusCode.USER_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.USER_NOT_EXITS));
                }
                UserAddress userAddress = new UserAddress();
                userAddress.setUserId(Integer.parseInt(map.get("userId")));
                userAddress.setIsDefault(1);
                userAddress = userAddressDao.selectByUserAddress(userAddress);
                if(userAddress == null){
                    return this.setResult(StatusCode.ADDRESS_IS_NOT_EXITS, null, StatusCode.codeMsgMap.get(StatusCode.ADDRESS_IS_NOT_EXITS));
                }
                AddressVO addressVO = addressServiceImpl.showAddress(userAddress.getAreaId());
                data.put("investors",user.getRealname());
                data.put("investorIdCard",user.getIdCard());
                data.put("investorArea", addressVO.getProvince() +" "+ addressVO.getCity()+" "+ addressVO.getArea()+" "+userAddress.getUserAddress());

                try {
                    //生成盖章合同
                    ContactHtmlCreateFileRequest request=new ContactHtmlCreateFileRequest();
                    /**模版类型，TPL_TYPE_HTML使用html文件方式,TPL_TYPE_URL使用url地址方式*/
                    request.setTplType(ContactHtmlCreateFileRequest.TPL_TYPE_HTML);//使用html内容上传方式
                    /**html内容*/

                    //oss配置信息  从oss读取文件
                    String contractFolderName = ConfigUtil.getConfig("contractFolderName");
                    String endpoint = ConfigUtil.getConfig("endpoint");
                    String accessKeyId = ConfigUtil.getConfig("accessKeyId");
                    String accessKeySecret = ConfigUtil.getConfig("accessKeySecret");
                    String bucketName = ConfigUtil.getConfig("bucketName");
                    String callback = ConfigUtil.getConfig("callbackUrl");

                    OSSFileUtils fileUtils = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callback);
                    String html = IOUtils.toString(fileUtils.download("contract","contract001.html"),"UTF-8");
                    //合同内容替换
                    html = html.replace("{signatureDate}","<em>"+ DateUtils.getPrintDate()+"</em>");//签署日期
                    html = html.replace("{signatureArea}", ConfigUtil.getConfig("SIGNATUREAREA"));//签署地点

                    html = html.replace("{investors}",data.get("investors"));//投资人
                    html = html.replace("{investorIdCard}",data.get("investorIdCard"));//投资人身份证号
                    html = html.replace("{investorArea}",data.get("investorArea"));//投资人地址
                    html = html.replace("{investmentAmount}",String.valueOf(data.get("investmentAmount")));//投资人投资金额

                    html = html.replace("{legalRepresentative}",data.get("legalRepresentative"));//法定代表人
                    html = html.replace("{projectCompanyArea}",data.get("projectCompanyArea"));//项目公司地址
                    Integer manageCompanyValuation = Integer.valueOf(String.valueOf(data.get("manageCompanyValuation")));
                    html = html.replace("{manageCompanyValuation}",String.valueOf(manageCompanyValuation/10000));//项目公司估值资金 （万元）
                    Integer projectSideValuation = Integer.valueOf(String.valueOf(data.get("projectSideValuation")));
                    html = html.replace("{projectSideValuation}",String.valueOf(projectSideValuation/10000));//项目方估值（万元）

                    html = html.replace("{platformArea}",ConfigUtil.getConfig("PLATFORMAREA"));//平台地址
                    html = html.replace("{platformLegalRepresentative}",ConfigUtil.getConfig("PLATFORMLEGALREPRESENTATIVE"));//平台法定代表人

                    html = html.replace("{managementCompany}",ConfigUtil.getConfig("MANAGEMENTCOMPANY"));//管理公司
                    html = html.replace("{managementCompanyArea}",ConfigUtil.getConfig("MANAGEMENTCOMPANYAREA"));//管理公司地址
                    html = html.replace("{managementCompanyLegalRepresentative}",ConfigUtil.getConfig("MANAGEMENTCOMPANYLEGALREPRESENTATIVE"));//管理公司法定代表人

                    html = html.replace("{projectOwner}",data.get("projectOwner"));//项目方
                    html = html.replace("{projectCompany}",data.get("projectCompany"));//项目公司
                    html = html.replace("{projectBonusAndReturnStatement}",data.get("projectBonusAndReturnStatement"));//项目分红及回报说明

                    Integer manageMoney = Integer.valueOf(String.valueOf(data.get("manageMoney")));
                    html = html.replace("{manageMoney}",String.valueOf(manageMoney/10000));//项目公司注册资金 （万元）1
                    html = html.replace("{manageDate}","<em>"+String.valueOf(data.get("manageYear"))+"</em>年<em>"+String.valueOf(data.get("manageMonth"))+"</em>月<em>"+String.valueOf(data.get("manageDay"))+"</em>日");//项目公司成立于1
                    html = html.replace("{platformServiceFee}",String.valueOf(data.get("platformServiceFee"))+"%");//平台服务费1

                    html = html.replace("{shareholder}",data.get("shareholder"));//股东1
                    html = html.replace("{shareholderIdCard}",data.get("shareholderArea"));//股东身份证号1
                    html = html.replace("{shareholderArea}",data.get("shareholderArea"));//股东地址1
                    html = html.replace("{guarantor}",data.get("guarantor"));//担保人1
                    html = html.replace("{guarantorIdCard}",data.get("guarantorIdCard"));//担保人身份证号1
                    html = html.replace("{guarantorArea}",data.get("guarantorArea"));//担保人地址1

                    html = html.replace("{investmentTerm}",String.valueOf(data.get("investmentTerm")));//投资期限1
                    html = html.replace("{investmentAmount}",String.valueOf(data.get("investmentAmount")));//投资金额1

                    String investmentAmountUpCase = NumberToCN.number2CNMontrayUnit(new BigDecimal(String.valueOf(data.get("investmentAmount"))));
                    html = html.replace("{investmentAmountUpCase}",investmentAmountUpCase);//人民币大写2
                    html = html.replace("{investmentStalls}",data.get("investmentStalls"));//投资档位1

                    String flag = String.valueOf(data.get("flag"));
                    if("1".equals(flag)){
                        html = html.replace("{exitAndRepurchase}","届满<em>"+String.valueOf(data.get("exitAndRepurchaseDate"))+"</em>个月时，医院退还用户投资本金及利息。");
                    }
                    if("2".equals(flag)){
                        html = html.replace("{exitAndRepurchase}","每<em>"+String.valueOf(data.get("exitAndRepurchaseDate"))+"</em>月返还利息，届满<em>"+String.valueOf(data.get("exitAndRepurchaseDateNum"))+"</em>个月时，项目方退还用户投资本金。");//项目公司注册资金 （万元）//exitAndRepurchaseDateNum
                    }


                    /**用户签章*/
                    JSONArray stampParams=new JSONArray();

                    JSONObject projectOwnerChapter=new JSONObject();
                    projectOwnerChapter.put("id","projectOwnerChapter");//对应网页标签上的id，须确认此id为唯一,且找到的标签为行级（inline）或行块（inline-block）元素
                    projectOwnerChapter.put("stampType", StampType.ENTERPRISE_01.getCode());//章类型，企业章类型1，其它还有待开发
                    projectOwnerChapter.put("stampText", data.get("projectCompany"));//"项目公司盖章"
                    stampParams.add(projectOwnerChapter);

                    JSONObject platformChapter=new JSONObject();
                    platformChapter.put("id","platformChapter");//对应网页标签上的id，须确认此id为唯一,且找到的标签为行级（inline）或行块（inline-block）元素
                    platformChapter.put("stampType", StampType.ENTERPRISE_01.getCode());//章类型，企业章类型1，其它还有待开发
                    platformChapter.put("stampText", ConfigUtil.getConfig("PLATFORMNAME"));//平台刻章
                    stampParams.add(platformChapter);

                    JSONObject managementCompanyChapter=new JSONObject();
                    managementCompanyChapter.put("id","managementCompanyChapter");//对应网页标签上的id，须确认此id为唯一,且找到的标签为行级（inline）或行块（inline-block）元素
                    managementCompanyChapter.put("stampType", StampType.ENTERPRISE_01.getCode());//章类型，企业章类型1，其它还有待开发
                    managementCompanyChapter.put("stampText", ConfigUtil.getConfig("MANAGEMENTCOMPANY"));
                    stampParams.add(managementCompanyChapter);

                    JSONObject investorsChapter=new JSONObject();
                    investorsChapter.put("id","investorsChapter");
                    investorsChapter.put("stampType", StampType.PERSONAL_01.getCode());//章类型，个人章，其它还有待开发
                    investorsChapter.put("stampText", data.get("investors"));
                    stampParams.add(investorsChapter);

                    request.setStampParams(stampParams);
                    request.setTaget(html);
                    ContactHtmlCreateFileResponse response = getClient().getContactHtmlCreateFile(request);

                    if(response.isSuccess()&&response.getByteArrayFile()!=null){

                        //盖章成功后 创建保全
                        String fileName = "contract_"+map.get("itemId")+"_"+map.get("levelId")+"_"+map.get("userId")+"_"+new Random().nextInt(1000)+".pdf";
                        ContractFilePreservationCreateRequest builder = new ContractFilePreservationCreateRequest();
                        builder.setFile(new UploadFile(fileName,response.getByteArrayFile()));
                        builder.setPreservationTitle("收益权转让协议 合同");//保全标题
                        builder.setPreservationType(5);//保全类型，默认即可
                        builder.setIdentifer(new PersonalIdentifer(data.get("investorIdCard"),data.get("investors"))); //测试是请修改为自己的姓名和身份证号
                        builder.setSourceRegistryId("6");//平台来源ID
                        builder.setContractAmount(new Double(String.valueOf(data.get("investmentAmount"))));//合同金额
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        String contractNo = "MRG-SYZR-"+sdf.format(new Date().getTime())+"-"+new Random().nextInt(10000);
                        builder.setContractNumber(contractNo);//合同编号
                        builder.setMobilePhone(user.getPhone());//测试时请修改为自己的手机号码
                        builder.setComments("备注信息"); //备注
                        builder.setIsNeedSign(true);//是否启用保全签章

                        PreservationCreateResponse preservationCreateResponse = getClient().createPreservation(builder);

                        //根据保全id 下载最终版合同
                        if(preservationCreateResponse.isSuccess()&&preservationCreateResponse.getPreservationId()!=null) {
                            logger.info("Create a preservation success",preservationCreateResponse.getPreservationId()+" time "+preservationCreateResponse.getPreservationTime());
                            ContractFileDownloadUrlRequest contractFileDownloadUrlRequest = new ContractFileDownloadUrlRequest();
                            contractFileDownloadUrlRequest.setPreservationId(preservationCreateResponse.getPreservationId());
                            ContractFileDownloadUrlResponse contractFileDownloadUrlResponse = getClient().getContactFileDownloadUrl(contractFileDownloadUrlRequest);
                            //根据下载url 获取文件流 并上传oss服务器
                            if (contractFileDownloadUrlResponse.isSuccess() && contractFileDownloadUrlResponse.getDownUrl() != null) {
                                logger.info("get download link success",contractFileDownloadUrlResponse.getDownUrl());
                                URL downUrl = new URL(contractFileDownloadUrlResponse.getDownUrl());
                                HttpURLConnection conn = (HttpURLConnection)downUrl.openConnection();
                                conn.setConnectTimeout(3*1000);   //设置超时间为3秒
                                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");//防止屏蔽程序抓取而返回403错误
                                InputStream inputStream = conn.getInputStream();

                                //获取文件IO流 并将文件保存到oss
                                OSSFileUtils fileUpload = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callback);
                                fileUpload.upload(inputStream,fileName,contractFolderName);

                                //更新用户合同表
                                Contract contract = new Contract();
                                contract.setUserId(Integer.parseInt(map.get("userId")));
                                contract.setItemId(Integer.parseInt(map.get("itemId")));
                                contract.setLevelId(Integer.parseInt(map.get("levelId")));
                                contract.setOrderId(Integer.parseInt(map.get("orderId")));
                                contract.setPreservationId(preservationCreateResponse.getPreservationId().intValue());
                                contract.setContractNo(contractNo);
                                contract.setPreservationTime(new Date(preservationCreateResponse.getPreservationTime()));
                                contract.setContractFilepath(contractFolderName+"/"+fileName);

                                int count = contractDao.insert(contract);
                                if(count!=1){
                                    count = contractDao.insert(contract);
                                }
                                if(count==0){
                                    //// TODO: 4/13/2017  拿着保全信息通知管理员 添加失败 并记录保全信息

                                }
                            }else{
                                logger.info("get download link failed");
                                logger.info("Main Error Code:"+contractFileDownloadUrlResponse.getError().getCode());
                                logger.info("Main Error Message:"+contractFileDownloadUrlResponse.getError().getMessage());
                                logger.info("Main Error Solution:"+contractFileDownloadUrlResponse.getError().getSolution());
                                return this.setResult(StatusCode.FAILED_GET_DOWNLOAD_LINK, null, StatusCode.codeMsgMap.get(StatusCode.FAILED_GET_DOWNLOAD_LINK));
                            }
                        }else{
                            logger.info("Create a preservation failed");
                            logger.info("Main Error Code:"+preservationCreateResponse.getError().getCode());
                            logger.info("Main Error Message:"+preservationCreateResponse.getError().getMessage());
                            logger.info("Main Error Solution:"+preservationCreateResponse.getError().getSolution());
                            return this.setResult(StatusCode.UPLOAD_PDF_FIX_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.UPLOAD_PDF_FIX_FAILED));
                        }

                    }else{
                        logger.info("Upload html stamp failed!");
                        logger.info("Main Error Code:"+response.getError().getCode());
                        logger.info("Main Error Message:"+response.getError().getMessage());
                        logger.info("Main Error Solution:"+response.getError().getSolution());
                        return this.setResult(StatusCode.UPLOAD_HTML_STAMP_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.UPLOAD_HTML_STAMP_FAILED));
                    }
//
                }catch (Exception e){
                    logger.info("ContactServiceImpl.CreateContactFile failed :{}",e.getMessage());
                }
            }else{
                logger.info("ContactServiceImpl.CreateContactFile connected refused :{}");
                return this.setResult(StatusCode.RETRIEVE_PROJECT_GET_MESSAGE_EMPTY, null, StatusCode.codeMsgMap.get(StatusCode.RETRIEVE_PROJECT_GET_MESSAGE_EMPTY));
            }
        }else{
            logger.info("ContactServiceImpl.CreateContactFile connected refused :{}");
            return this.setResult(StatusCode.RETRIEVE_PROJECT_GET_MESSAGE_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.RETRIEVE_PROJECT_GET_MESSAGE_FAILED));
        }
        return this.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
    }

    @Override
    public Result CreateEquityContactFile(Map<String, String> map) {
        return null;
    }

    @Override
    public  List<Map<String,String>> ViewContactFile(Map<String,String> map) {
        Contract contract = new Contract();
        contract.setOrderId(Integer.parseInt(map.get("orderId")));
        List<Contract> contractList = contractDao.select(contract);

        List<Map<String,String>> viewUrl = new ArrayList<>();
        ContractFileViewUrlRequest request = new ContractFileViewUrlRequest();
        for(Contract contract1:contractList){
            Map<String,String> urlMap = new HashMap<String,String>();
            request.setPreservationId(new Long(contract1.getPreservationId()));
            ContractFileViewUrlResponse response = getClient().getContactFileViewUrl(request);
            urlMap.put("contractName",contract1.getContractNo());
            urlMap.put("url",response.getViewUrl());
            viewUrl.add(urlMap);
        }
        return viewUrl;
    }

    @Override
    public  List<Map<String, String>> DownContactFile(Map<String, String> map) {
        Contract contract = new Contract();
        contract.setOrderId(Integer.parseInt(map.get("orderId")));
        List<Contract> contractList = contractDao.select(contract);

        ContractFileDownloadUrlRequest request = new ContractFileDownloadUrlRequest();
        List<Map<String, String>> downUrl = new ArrayList<>();
        for(Contract contract1:contractList){
            request.setPreservationId(new Long(contract1.getPreservationId()));
            ContractFileDownloadUrlResponse response = getClient().getContactFileDownloadUrl(request);
            if (response.isSuccess() && response.getDownUrl() != null) {
                logger.info("Get the connection to see success",response.getDownUrl());
                Map<String,String> urlMap = new HashMap<String,String>();

//                String string = contract1.getContractNo().substring()
//                if(){
//
//                }





                urlMap.put("contractName",contract1.getContractNo());
                urlMap.put("url",response.getDownUrl());
                downUrl.add(urlMap);
            }else{
                logger.info("Get the connection to see failed");
                logger.info("Main Error Code:"+response.getError().getCode());
                logger.info("Main Error Message:"+response.getError().getMessage());
                logger.info("Main Error Solution:"+response.getError().getSolution());
                return null;
            }
        }
        return  downUrl;
    }

    public Result setResult(int code, Object data, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        if (code == 200 && (data != null && !"".equals(data))){
            result.setData(data);
        }
        logger.info("Request getResponse: {}", JSON.toJSON(result));
        return result;
    }
}
