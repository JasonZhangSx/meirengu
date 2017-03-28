package com.meirengu.trade.service.impl;
import com.alibaba.fastjson.JSON;
import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.dao.RebateReceiveDao;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.model.Rebate;
import com.meirengu.trade.model.RebateBatch;
import com.meirengu.trade.model.RebateReceive;
import com.meirengu.trade.service.RebateBatchService;
import com.meirengu.trade.service.RebateReceiveService;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.trade.service.RebateService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPoolConfig;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RebateReceive服务实现层 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
@Service
public class RebateReceiveServiceImpl extends BaseServiceImpl<RebateReceive> implements RebateReceiveService{

    private static final Logger logger = LoggerFactory.getLogger(RebateReceiveServiceImpl.class);

    @Autowired
    private RebateService rebateService;
    @Autowired
    private RebateBatchService rebateBatchService;

    @Autowired
    private RebateReceiveDao rebateReceiveDao;
    /**
     * 领取优惠券
     * @param userId
     * @param userPhone
     * @param batchIdList
     * @return
     */
    @Transactional
    public Result receiveRebate(int userId, String userPhone, List<Integer> batchIdList, String activityIdentification) {
        Result result = new Result();

        JedisPoolConfig config = new JedisPoolConfig();
        RedisClient redisService = new RedisClient(config, "192.168.0.135:6379");

        List<String> list = null;
        String rebateSn = null;
        RebateReceive rebateReceive = null;
        Map<String, Object> paramMap = null;
        Rebate rebate = null;
        RebateBatch rebateBatch = null;
        for (Integer batchId : batchIdList) {
            rebateBatch = rebateBatchService.detail(batchId);
            //验证批次是否有效
            if (rebateBatch == null ) {
                logger.error("用户id为: " + userId + "的用户无法领取批次号为: " + batchId + "的优惠券，原因：没有有效的该批次的优惠券");
                result.setCode(StatusCode.REBATE_BATCH_INVALIDITY);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.REBATE_BATCH_INVALIDITY));
                break;
            }
            //验证用户是否有此类优惠券
            paramMap = new HashMap<String, Object>();
            paramMap.put("rebateBatchId", batchId);
            int totalCount = rebateReceiveDao.getCount(paramMap);
            if (totalCount != 0) {
                logger.error("用户id为: " + userId + "的用户无法领取批次号为: " + batchId + "的优惠券，原因：该用户领取该优惠券以打次数上限");
                result.setCode(StatusCode.HAS_REACHE_MAXIMUM_NUMBER_OF_REBATE);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.HAS_REACHE_MAXIMUM_NUMBER_OF_REBATE));
                break;
            }
            //验证该批次下是否有可用优惠券
            boolean flag = false;
            //redis中取出优惠券号
            list = redisService.blpop(1, "rebate_batch_" + batchId);
            if (list != null && list.size() > 1) {
                rebateSn = list.get(1);
                if (StringUtils.isNotBlank(rebateSn)) {
                    flag = true;
                }
            }
            if (flag) {
                //优惠券可以使用
                //新增用户领取优惠券
                rebateReceive = new RebateReceive();
                rebateReceive.setUserId(userId);
                rebateReceive.setUserPhone(userPhone);
                rebateReceive.setRebateSn(rebateSn);
                rebateReceive.setRebateBatchId(rebateBatch.getBatchId());
                rebateReceive.setActivityIdentification(activityIdentification);
                rebateReceive.setReceiveTime(new Date());
                rebateReceive.setStatus(1);//未使用
                insert(rebateReceive);
                //改变该优惠券过期时间
                rebate = new Rebate();
                rebate.setRebateSn(rebateSn);
                if (rebateBatch.getValidType() == 1) {
                    //该优惠券按绝对时间过期
                    rebate.setValidStartTime(rebateBatch.getValidStartTime());
                    rebate.setValidEndTime(rebateBatch.getValidEndTime());
                } else if (rebateBatch.getValidType() == 2) {
                    //该优惠券按相对时间过期
                    Date currentDate = new Date();
                    rebate.setValidStartTime(currentDate);
                    rebate.setValidEndTime(DateUtils.addDays(currentDate, rebateBatch.getValidDays()));
                }
                rebateService.updateBySn(rebate);
                result.setCode(StatusCode.OK);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
            } else {
                logger.error("用户id为: " + userId + "的用户无法领取批次号为: " + batchId + "的优惠券，原因：该批次的券已被领取完");
                result.setCode(StatusCode.HAS_REACHE_MAXIMUM_NUMBER_OF_REBATE);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.HAS_REACHE_MAXIMUM_NUMBER_OF_REBATE));
                break;
            }
        }
        return result;
    }

    /**
     * 校验该优惠券是否有效
     * @param order
     * @param rebateReceiveId
     * @return
     */
    public Result validateRebate(Order order, int rebateReceiveId) {
        Result result = new Result();
        RebateReceive rebateReceive = detail(rebateReceiveId);
        //判断优惠券是否有效
        if (rebateReceive == null || rebateReceive.getStatus() != 1) {
            result.setCode(StatusCode.REBATE_RECEIVE_INVALIDITY);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.REBATE_RECEIVE_INVALIDITY));
            return result;
        }
        //判断是否符合使用规则
        RebateBatch rebateBatch = rebateBatchService.detail(rebateReceive.getRebateBatchId());
        if (rebateBatch.getRebateType() == 2) {//满减型
            if (order.getOrderAmount().compareTo(new BigDecimal(rebateBatch.getRebateLimit())) < 0) {
                result.setCode(StatusCode.NOT_MATCH_REBATE_BATCH_RULE);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.NOT_MATCH_REBATE_BATCH_RULE));
                return result;
            }
        }
        result.setCode(StatusCode.OK);
        result.setData(rebateReceive);
        return result;
    }

    /**
     * 获取优惠券分页列表
     * @param page
     * @param map
     * @return
     */
    public Page getRebateInfoListByPage(Page page, Map map) {
        int startPos = page.getStartPos();
        int pageSize = page.getPageSize();
        RowBounds rowBounds = new RowBounds(startPos, pageSize);
        List<Map<String, Object>> aList = rebateReceiveDao.getByPage(map, rowBounds);
        int totalCount = rebateReceiveDao.getCount(map);
        page.setTotalCount(totalCount);
        page.setList(aList);
        page.init();
        return page;
    }

}
