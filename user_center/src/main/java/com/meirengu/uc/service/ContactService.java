package com.meirengu.uc.service;

import com.meirengu.model.Result;
import com.meirengu.uc.model.Contract;

import java.util.Map;

/**
 * Created by huoyan403 on 4/11/2017.
 */
public interface ContactService {
    //    生成盖章合同 并创建保全  并把保全文本下载到本地一份
    Result CreateContactFile(Map<String,String> map);
    //返回查看保全合同地址
    Contract ViewContactFile(Map<String,String> map);
    //返回下载地址
    String DownContactFile(Map<String, String> map);

}
