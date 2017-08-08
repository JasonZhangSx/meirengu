package com.meirengu.uc.service;

import com.meirengu.model.Result;
import com.meirengu.uc.model.Contract;

import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 4/11/2017.
 */
public interface ContactService {
    //    生成盖章合同 并创建保全  并把保全文本下载到本地一份
    Result CreateIncomeContactFile(Map<String,String> map);

    //返回查看保全合同地址
    List<Map<String,String>> ViewContactFile(Map<String,String> map);
    //返回下载地址
    List<Map<String,String>> DownContactFile(Map<String, String> map);
    //生成合伙协议
    Result CreateHHXYContactFile(Map<String, String> map);
    //生成股权投资协议
    Result CreateEquityContactFile(Map<String, String> map);

    List<Contract> selectContactFile(String orderId);
}
