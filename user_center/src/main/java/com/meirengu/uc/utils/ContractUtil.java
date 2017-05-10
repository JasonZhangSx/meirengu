package com.meirengu.uc.utils;

import com.meirengu.uc.common.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**创建合同编号工具
 * Created by huoyan403 on 5/5/2017.
 */
public class ContractUtil {



    //收益转让合同编号
    public static String getIncomeContractNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return "MRG-"+ Constants.SYZR+"-"+sdf.format(new Date().getTime())+"-"+new Random().nextInt(10000);
    }

    public static String getPartnershipContractNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return "MRG-"+ Constants.HHXY+"-"+sdf.format(new Date().getTime())+"-"+new Random().nextInt(10000);
    }

    public static String getEquityContractNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return "MRG-"+ Constants.GQZR+"-"+sdf.format(new Date().getTime())+"-"+new Random().nextInt(10000);
    }


    public static String returnContractName(String contractNo){
        String result = contractNo.substring(4,8);
        if(result.equals(Constants.SYZR)){
            return Constants.SYZR_FULLNAME;
        }
        if(result.equals(Constants.HHXY)){
            return Constants.HHXY_FULLNAME;
        }
        if(result.equals(Constants.GQZR)){
            return Constants.GQZR_FULLNAME;
        }
        return null;
    }


}
