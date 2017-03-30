package com.meirengu.trade.service.impl;
import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.trade.dao.RebateBatchDao;
import com.meirengu.trade.dao.RebateDao;
import com.meirengu.trade.model.Rebate;
import com.meirengu.trade.model.RebateBatch;
import com.meirengu.trade.service.RebateBatchService;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.trade.utils.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;

import java.util.*;

/**
 * RebateBatch服务实现层 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
@Service
public class RebateBatchServiceImpl extends BaseServiceImpl<RebateBatch> implements RebateBatchService{
    private static final Logger logger = LoggerFactory.getLogger(RebateBatchServiceImpl.class);

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private RebateBatchDao rebateBatchDao;

    @Autowired
    private RebateDao rebateDao;


    /**
     * 新增抵扣券批次
     * @param rebateBatch
     * @return
     */
    @Transactional
    public Result insertRebateBatch(RebateBatch rebateBatch) {
        Result result = new Result();
        int i = insert(rebateBatch);
        if (i == 1) {
            result = batchRebateInsert(rebateBatch);
        } else {
            result.setCode(StatusCode.REBATE_BATCH_ERROR_INSERT);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.REBATE_BATCH_ERROR_INSERT));
        }
        return result;
    }
    public Result batchRebateInsert(RebateBatch rebateBatch) {
        Result result = new Result();
        result.setCode(StatusCode.OK);
        result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
        //券号的生成
        Set<String> strSet = generateNumber(rebateBatch);
        //到这一步不会发生重复
//        if (strSet.size() < rebateBatch.getBatchCount()) {
//            result.setCode(StatusCode.REBATE_SN_REPEAT);
//            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.REBATE_SN_REPEAT));
//            strSet.addAll(generateNumber(rebateBatch));
//        }
        //数据库批量插入
        List<Rebate> rebateList = new ArrayList<Rebate>();
        Rebate rebate = null;
        for (String str : strSet) {
            rebate = new Rebate();
            rebate.setRebateSn(str);
            rebate.setRebateBatch(rebateBatch.getBatchId());
            rebate.setChannel(rebateBatch.getChannel());
            rebate.setOperateAccount(rebateBatch.getOperateAccount());
            rebateList.add(rebate);
        }

        //如果大于1W条，则每次只存1W
        int i = 1;
        int num = 10000;
        int k = 0;//成功保存的条数
        while ((i * num) < rebateBatch.getBatchCount()) {
            k += rebateDao.batchSave(rebateList.subList(((i-1) * num), (i * num)));
            i++;
        }
        k += rebateDao.batchSave(rebateList.subList(((i-1) * num), rebateList.size()));
        logger.info("优惠券成功保存" + k + "条");
        //券号放入redis  异步放入
        pushRedisList(strSet, rebateBatch.getBatchId());
        return result;
    }

    private void pushRedisList(Set<String> strSet, int batchId) {
        List<String> strList = new ArrayList<String>(strSet);
        int i = 1;
        int num = 10000;
        long k = 0;//成功保存的条数
        String[] a = null;
        while ((i * num) < strList.size()) {
            a = strList.subList((i - 1) * num, i * num).toArray(new String[num]);
            k += redisClient.lpush("rebate_batch_" + batchId, a);
            i++;
        }
        k += redisClient.lpush("rebate_batch_" + batchId, strList.subList((i - 1) * num, strList.size()).toArray(new String[(strList.size() - ((i - 1) * num))]));
    }


    /**
     * 生成优惠券号
     * @param rebateBatch
     * @return
     */
    private Set<String> generateNumber(RebateBatch rebateBatch) {
        String[] r=new String[]{"q", "w", "e", "a", "s","d", "z", "x", "c",  "p",  "i", "k",  "m", "j", "u", "f", "r",  "v", "y",  "t", "n",  "b", "g", "h"};
        IdWorker idWorker = new IdWorker(rebateBatch.getRebateType());
        Random random = new Random(47);
        StringBuffer bf = new StringBuffer();
        Set<String> strSet = new HashSet<String>();
        for (int i = 0;i < rebateBatch.getBatchCount();i++) {
            long a = idWorker.nextId();
            bf.append(a);
            int k = random.nextInt(10);
            bf.replace(k,k+1,r[random.nextInt(24)]);
            strSet.add(bf.toString());
            bf.setLength(0);
        }
        //有重复继续补充
        while (strSet.size() < rebateBatch.getBatchCount()) {
            long a = idWorker.nextId();
            bf.append(a);
            int k = random.nextInt(10);
            bf.replace(k,k+1,r[random.nextInt(24)]);
            strSet.add(bf.toString());
            bf.setLength(0);
        }
        return strSet;
    }

    public List<RebateBatch> findByCondition(Map<String, Object> paramMap){
        return rebateBatchDao.findByCondition(paramMap);
    }
}
