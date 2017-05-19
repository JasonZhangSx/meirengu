package com.meirengu.trade.service.impl;
import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.common.OrderException;
import com.meirengu.trade.dao.RebateReceiveDao;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.model.Rebate;
import com.meirengu.trade.model.RebateBatch;
import com.meirengu.trade.model.RebateReceive;
import com.meirengu.trade.service.RebateBatchService;
import com.meirengu.trade.service.RebateReceiveService;
import com.meirengu.trade.service.RebateService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * RebateReceive服务实现层 
 * @author 建新
 * @create Thu Mar 23 18:18:22 CST 2017
 */
@Service
public class RebateReceiveServiceImpl extends BaseServiceImpl<RebateReceive> implements RebateReceiveService{

    private static final Logger logger = LoggerFactory.getLogger(RebateReceiveServiceImpl.class);

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private RebateService rebateService;
    @Autowired
    private RebateBatchService rebateBatchService;

    @Autowired
    private RebateReceiveDao rebateReceiveDao;

    /**
     * 领取优惠券
     * 1.判断用户是否登录
     * 2.判断批次是否可用
     * 3.判断该用户是否已超过该优惠券的领取次数
     * 4.给用户账号发送优惠券
     *      -----通过预先生成优惠券存放到队列，领取时从队列POP取出优惠券，再和用户绑定完成发券操作
     *           当队列pop不到优惠券时，就代表券发完了
     * 5.给用户发送消息提示优惠券已到账
     * @param userId
     * @param userPhone
     * @param batchIdList
     * @param activityIdentification
     * @return
     */
    @Transactional
    public Result receiveRebate(int userId, String userPhone, List<Integer> batchIdList, String activityIdentification) {
        Result result = new Result();

        List<String> list = null;
        String rebateSn = null;
        RebateReceive rebateReceive = null;
        Map<String, Object> paramMap = null;
        Rebate rebate = null;
        RebateBatch rebateBatch = null;
        Integer code = null;
        Date currentDate = new Date();
        for (Integer batchId : batchIdList) {
            rebateBatch = rebateBatchService.detail(batchId);
            //验证批次是否有效
            if (rebateBatch == null || rebateBatch.getBatchStatue() == 0) {
                logger.error("用户id为: " + userId + "的用户无法领取批次号为: " + batchId + "的优惠券，原因：没有有效的该批次的优惠券");
                result.setCode(StatusCode.REBATE_BATCH_INVALIDITY);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.REBATE_BATCH_INVALIDITY));
                continue;
            }
            //领取次数验证
            Integer rebateLimit = rebateBatch.getRebateLimit();
            //每天一次暂不处理
            //永久一次
            if (rebateLimit.intValue() == Constant.REBATE_LIMIT_SINGLE) {
                paramMap = new HashMap<String, Object>();
                paramMap.put("userId", userId);
                paramMap.put("rebateBatchId", batchId);
                int totalCount = rebateReceiveDao.getCount(paramMap);
                if (totalCount != 0) {
                    logger.error("用户id为: " + userId + "的用户无法领取批次号为: " + batchId + "的优惠券，原因：该用户领取该优惠券以打次数上限");
                    result.setCode(StatusCode.HAS_REACHE_REBATE_LIMIT);
                    result.setMsg(StatusCode.codeMsgMap.get(StatusCode.HAS_REACHE_REBATE_LIMIT));
                    continue;
                }
            }

            //验证该批次下是否有可用优惠券
            boolean flag = false;
            //redis中取出优惠券号
            list = redisClient.blpop(1, "rebate_batch_" + batchId);
            if (list != null && list.size() > 1) {
                rebateSn = list.get(1);
                if (StringUtils.isNotBlank(rebateSn)) {
                    flag = true;
                }
            }
            if (flag) {
                //用户绑定优惠券
                rebateReceive = new RebateReceive();
                rebateReceive.setUserId(userId);
                rebateReceive.setUserPhone(userPhone);
                rebateReceive.setRebateSn(rebateSn);
                rebateReceive.setRebateBatchId(rebateBatch.getBatchId());
                rebateReceive.setRebateMark(rebateBatch.getRebateMark());
                rebateReceive.setActivityIdentification(activityIdentification);
                rebateReceive.setReceiveTime(currentDate);
                rebateReceive.setStatus(Constant.REBATE_RECEIVE_UNUSED);//未使用
                int i = insert(rebateReceive);
                if (i != 1) {
                    code = StatusCode.REBATE_RECEIVE_INSERT_ERROR;
                    throw new OrderException("优惠券领取信息插入异常 -- StatusCode: " + code, code);
                }
                //改变该优惠券过期时间
                rebate = new Rebate();
                rebate.setRebateSn(rebateSn);
                if (rebateBatch.getValidType() == Constant.REBATE_EXPIRE_BY_ABSOLUTE_TIME) {
                    //该优惠券按绝对时间过期
                    rebate.setValidStartTime(rebateBatch.getValidStartTime());
                    rebate.setValidEndTime(rebateBatch.getValidEndTime());
                } else if (rebateBatch.getValidType() == Constant.REBATE_EXPIRE_BY_RELATIVE_TIME) {
                    //该优惠券按相对时间过期
                    rebate.setValidStartTime(currentDate);
                    rebate.setValidEndTime(DateUtils.addDays(currentDate, rebateBatch.getValidDays()));
                }
                int j = rebateService.updateBySn(rebate);
                if (j != 1) {
                    code = StatusCode.REBATE_UPDATE_ERROR;
                    throw new OrderException("优惠券信息更新异常 -- StatusCode: " + code, code);
                }
                result.setCode(StatusCode.OK);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
            } else {
                logger.error("用户id为: " + userId + "的用户无法领取批次号为: " + rebateBatch.getBatchId() + "的优惠券，原因：该批次的券已被领取完");
                result.setCode(StatusCode.HAS_REACHE_MAXIMUM_NUMBER_OF_REBATE);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.HAS_REACHE_MAXIMUM_NUMBER_OF_REBATE));
                continue;
            }
        }
        return result;
    }

    /**
     * 根据标识领取优惠券
     * 1.判断用户是否登录
     * 2.读取该标识下的可用优惠券
     * 3.判断该用户是否已超过该优惠券的领取次数
     * 4.给用户账号发送优惠券
     *      -----通过预先生成优惠券存放到队列，领取时从队列POP取出优惠券，再和用户绑定完成发券操作
     *           当队列pop不到优惠券时，就代表券发完了
     * 5.给用户发送消息提示优惠券已到账
     * @param userId
     * @param userPhone
     * @param rebateMark
     * @param activityIdentification
     * @return
     */
    @Transactional
    public Result receiveRebateByMark(int userId, String userPhone, int rebateMark, String activityIdentification) {
        Result result = new Result();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        //查询该标签下的有效的批次信息
        paramMap = new HashMap<String, Object>();
        paramMap.put("rebateMark", rebateMark);
        paramMap.put("batchStatue", Constant.YES);
        List<RebateBatch> rebateBatchList = rebateBatchService.findByCondition(paramMap);
        if (rebateBatchList != null && rebateBatchList.size()>0) {
            RebateBatch rebateBatchLimit = rebateBatchList.get(0);
            if (rebateBatchLimit != null) {
                //领取次数验证
                Integer rebateLimit = rebateBatchLimit.getRebateLimit();
                //每天一次暂不处理
                //永久一次
                if (rebateLimit.intValue() == Constant.REBATE_LIMIT_SINGLE) {
                    paramMap = new HashMap<String, Object>();
                    paramMap.put("userId", userId);
                    paramMap.put("rebateMark", rebateMark);
                    int totalCount = rebateReceiveDao.getCount(paramMap);
                    if (totalCount != 0) {
                        logger.error("用户id为: " + userId + "的用户无法领取标签类型为: " + rebateMark + "的优惠券，原因：用户领取该优惠券已达限领次数");
                        result.setCode(StatusCode.HAS_REACHE_REBATE_LIMIT);
                        result.setMsg(StatusCode.codeMsgMap.get(StatusCode.HAS_REACHE_REBATE_LIMIT));
                        return result;
                    }
                }
            }
            //发券操作
            List<String> list = null;
            String rebateSn = null;
            RebateReceive rebateReceive = null;
            Rebate rebate = null;
            Date currentDate = new Date();
            Integer code = null;
            for (RebateBatch rebateBatch : rebateBatchList) {
                //验证该redis队列中是否有可用优惠券
                boolean flag = false;
                //redis中取出优惠券号
                list = redisClient.blpop(1, "rebate_batch_" + rebateBatch.getBatchId());
                if (list != null && list.size() > 1) {
                    rebateSn = list.get(1);
                    if (StringUtils.isNotBlank(rebateSn)) {
                        flag = true;
                    }
                }
                if (flag) {
                    //用户绑定优惠券
                    rebateReceive = new RebateReceive();
                    rebateReceive.setUserId(userId);
                    rebateReceive.setUserPhone(userPhone);
                    rebateReceive.setRebateSn(rebateSn);
                    rebateReceive.setRebateBatchId(rebateBatch.getBatchId());
                    rebateReceive.setRebateMark(rebateBatch.getRebateMark());
                    rebateReceive.setActivityIdentification(activityIdentification);
                    rebateReceive.setReceiveTime(currentDate);
                    rebateReceive.setStatus(Constant.REBATE_RECEIVE_UNUSED);//未使用
                    int i = insert(rebateReceive);
                    if (i != 1) {
                        code = StatusCode.REBATE_RECEIVE_INSERT_ERROR;
                        throw new OrderException("优惠券领取信息插入异常 -- StatusCode: " + code, code);
                    }
                    //改变该优惠券过期时间
                    rebate = new Rebate();
                    rebate.setRebateSn(rebateSn);
                    if (rebateBatch.getValidType() == Constant.REBATE_EXPIRE_BY_ABSOLUTE_TIME) {
                        //该优惠券按绝对时间过期
                        rebate.setValidStartTime(rebateBatch.getValidStartTime());
                        rebate.setValidEndTime(rebateBatch.getValidEndTime());
                    } else if (rebateBatch.getValidType() == Constant.REBATE_EXPIRE_BY_RELATIVE_TIME) {
                        //该优惠券按相对时间过期
                        rebate.setValidStartTime(currentDate);
                        rebate.setValidEndTime(DateUtils.addDays(currentDate, rebateBatch.getValidDays()));
                    }
                    int j = rebateService.updateBySn(rebate);
                    if (j != 1) {
                        code = StatusCode.REBATE_UPDATE_ERROR;
                        throw new OrderException("优惠券信息更新异常 -- StatusCode: " + code, code);
                    }
                    result.setCode(StatusCode.OK);
                    result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
                } else {
                    logger.error("用户id为: " + userId + "的用户无法领取批次号为: " + rebateBatch.getBatchId() + "的优惠券，原因：该批次的券已被领取完");
                    result.setCode(StatusCode.HAS_REACHE_MAXIMUM_NUMBER_OF_REBATE);
                    result.setMsg(StatusCode.codeMsgMap.get(StatusCode.HAS_REACHE_MAXIMUM_NUMBER_OF_REBATE));
                    continue;
                }
            }
        } else {
            logger.error("用户id为: " + userId + "的用户无法领取标签类型为: " + rebateMark + "的优惠券，原因：没有有效的该标识下的优惠券");
            result.setCode(StatusCode.REBATE_BATCH_INVALIDITY);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.REBATE_BATCH_INVALIDITY));
            return result;
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
        List<Map<String, Object>> aList = rebateReceiveDao.getRebateInfoByPage(map, rowBounds);
        //上述是通过多表连接查询的，不适合在sql中排序，so在service中对查询的结果进行排序
        if (map.get("sortBy") != null && map.get("order") != null) {
            String sortBy = map.get("sortBy").toString();
            String order = map.get("order").toString();
            Collections.sort(aList, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    //按金额排序
                    if ("rebateAmount".equals(sortBy)) {
                        BigDecimal rebateAmount1 = new BigDecimal(o1.get("rebateAmount").toString());
                        BigDecimal rebateAmount2 = new BigDecimal(o2.get("rebateAmount").toString());
                        if ("DESC".equalsIgnoreCase(order)) {
                            return rebateAmount2.compareTo(rebateAmount1);
                        } else {
                            return rebateAmount1.compareTo(rebateAmount2);
                        }
                    }
                    return 0;
                }
            });

        }
        int totalCount = rebateReceiveDao.getRebateInfoCount(map);
        page.setTotalCount(totalCount);
        page.setList(aList);
        page.init();
        return page;
    }

}
