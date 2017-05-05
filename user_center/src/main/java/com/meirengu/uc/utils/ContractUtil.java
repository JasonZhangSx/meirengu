package com.meirengu.uc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**创建合同编号工具
 * Created by huoyan403 on 5/5/2017.
 */
public class ContractUtil {


    private static final String SYZR = "SYZR";//收益转让
    private static final String HHXY = "HHXY";//合伙协议
    private static final String GQZR = "GQZR";//股权转让

    //收益转让合同编号
    public static String getIncomeContractNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return "MRG-"+SYZR+"-"+sdf.format(new Date().getTime())+"-"+new Random().nextInt(10000);
    }

    public static String getPartnershipContractNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return "MRG-"+HHXY+"-"+sdf.format(new Date().getTime())+"-"+new Random().nextInt(10000);
    }

    public static String getEquityContractNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return "MRG-"+GQZR+"-"+sdf.format(new Date().getTime())+"-"+new Random().nextInt(10000);
    }


    public static String returnContractName(String contractNo){
        String result = contractNo.substring(4,8);
        if(result.equals(SYZR)){
            return "收益转让协议";
        }
        if(result.equals(HHXY)){
            return "合伙协议(美人谷)";
        }
        if(result.equals(GQZR)){
            return "股权收益权投资协议";
        }
        return null;
    }


}
