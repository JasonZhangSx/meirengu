package com.meirengu.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.common.OrderException;
import com.meirengu.trade.common.OrderStateEnum;
import com.meirengu.trade.dao.OrderDao;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.rocketmq.Producer;
import com.meirengu.trade.service.OrderService;
import com.meirengu.trade.service.RebateReceiveService;
import com.meirengu.trade.service.RebateUsedService;
import com.meirengu.trade.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.utils.ObjectToFile;
import com.meirengu.utils.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

/**
 * Order服务实现层 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService{

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private RebateReceiveService rebateReceiveService;

    @Autowired
    private RebateUsedService rebateUsedService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private Producer producer;
    /**
     * 获取订单详情
     * @param orderSn
     * @return
     */
    public Map<String, Object> orderDetailBySn (String orderSn) throws IOException {
        Map<String, Object> map = orderDao.orderDetailBySn(orderSn);
        return map;
    }
    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    public Map<String, Object> orderDetail(int orderId) throws ParseException, IOException {
        Map<String, Object> map = orderDao.orderDetail(orderId);
        if (map != null && map.size()>0) {
            //如果是待支付订单，返回剩余支付时长
            int orderState = Integer.parseInt(map.get("orderState").toString());
            String beginTimeStr = null;
            if (orderState == OrderStateEnum.UNPAID.getValue()) {
                beginTimeStr = map.get("createTime").toString();
            } else if (orderState == OrderStateEnum.BOOK_ADUIT_PASS.getValue()) {
                //取updateTime作为订单变为待支付状态的时间
                beginTimeStr = map.get("updateTime").toString();
            }
            if (StringUtils.isNotBlank(beginTimeStr)) {
                Date beginTime = DateUtils.parseDate(beginTimeStr.substring(0,19),"yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                cal.setTime(beginTime);
                cal.add(Calendar.DATE, 1);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(new Date());
                long remainingTime = cal.getTimeInMillis() - cal2.getTimeInMillis();
                map.put("remainingTime", remainingTime);
                logger.debug("剩余支付时间是: {}" , remainingTime/1000);
            }
            //查询地址信息
            if (map.get("userAddressId") != null) {
                int userAddressId = (int)((long)map.get("userAddressId"));
                if (userAddressId != 0) {
                    //请求user_center服务获取用户地址信息
                    String addressUrl = ConfigUtil.getConfig("address.url") + "?" + "address_id="+ userAddressId;;
                    HttpResult addressHttpResult = HttpUtil.doGet(addressUrl);
                    logger.debug("Request: {} getResponse: {}", addressUrl, addressHttpResult);
                    if (addressHttpResult.getStatusCode() == HttpStatus.SC_OK) {
                        JSONObject resultJson = JSON.parseObject(addressHttpResult.getContent());
                        int code = resultJson.getIntValue("code");
                        if (code == StatusCode.OK) {
                            JSONArray addressArray = resultJson.getJSONArray("data");
                            for (int i = 0; i < addressArray.size(); i++) {
                                JSONObject addressJson = addressArray.getJSONObject(i);
                                int addressId = addressJson.getIntValue("addressId");
                                Map<String, Object> addressMap = new HashMap<String, Object>();
                                addressMap.put("userName", addressJson.getString("userName"));
                                addressMap.put("userPhone", addressJson.getString("userPhone"));
                                addressMap.put("province", addressJson.getString("province"));
                                addressMap.put("city", addressJson.getString("city"));
                                addressMap.put("areas", addressJson.getString("area"));
                                addressMap.put("userAddress", addressJson.getString("userAddress"));
                                map.putAll(addressMap);
                            }
                        }
                    }
                }
            }
            if (map.get("itemId") != null) {
                int itemId = (int)((long)map.get("itemId"));
                if (itemId != 0) {
                    //查询项目头图
                    String url = ConfigUtil.getConfig("item.url") + "/" + itemId + "?user_id=0";
                    HttpResult itemResult = HttpUtil.doGet(url);
                    logger.debug("Request: {} getResponse: {}", url, itemResult);
                    if (itemResult.getStatusCode() == HttpStatus.SC_OK) {
                        JSONObject resultJson = JSON.parseObject(itemResult.getContent());
                        int code = resultJson.getIntValue("code");
                        if (code == StatusCode.OK) {
                            JSONObject item = resultJson.getJSONObject("data");
                            String headerImagePath = item.getString("headerImage");
                            String itemStatus = item.getString("itemStatus");
                            int partnerId = item.getIntValue("partnerId");
                            map.put("headerImage", headerImagePath);
                            map.put("itemStatus", itemStatus);
                            map.put("partnerId", partnerId);
                        }
                    }
                }
            }
        }
        return map;
    }
    /**
     *获取后台订单列表
     * @param page
     * @param map
     * @return
     */
    public Page getSystemPage(Page page, Map map) throws IOException {
        page = getListByPage(page, map);
        List<Map<String, Object>> aList = page.getList();
        if (aList != null && aList.size() > 0) {
            //地址ID的set
            Set<Integer> addressIdSet = new HashSet<Integer>();
            //档位ID的set
            Set<Integer> itemLeavelIdSet = new HashSet<Integer>();
            //临时存放地址信息的Map
            Map<Integer, Map<String, Object>> addressListTemp = new HashMap<Integer, Map<String, Object>>();
            //临时存放档位信息的Map
            Map<Integer, Map<String, Object>> itemLevelListTemp = new HashMap<Integer, Map<String, Object>>();
            for (Map<String, Object> orderMap : aList){
                int addressId = (int)((long)orderMap.get("userAddressId"));
                addressIdSet.add(addressId);
                int itemLevelId = (int)((long)orderMap.get("itemLevelId"));
                itemLeavelIdSet.add(itemLevelId);
            }
            //请求user_center服务获取用户地址信息
            String addressIdsStr = addressIdSet.toString();
            String addressIds = addressIdsStr.substring(addressIdsStr.indexOf("[")+1,addressIdsStr.indexOf("]"));
            String addressUrl = ConfigUtil.getConfig("address.url") + "?" + "address_id="+ URLEncoder.encode(addressIds, "UTF-8");;

            HttpResult addressHttpResult = HttpUtil.doGet(addressUrl);
            logger.debug("Request: {} getResponse: {}", addressUrl, addressHttpResult);
            Map<String, Object> addressMap = null;
            if (addressHttpResult.getStatusCode() == HttpStatus.SC_OK) {
                JSONObject resultJson = JSON.parseObject(addressHttpResult.getContent());
                int code = resultJson.getIntValue("code");
                if (code == StatusCode.OK) {
                    JSONArray addressArray = resultJson.getJSONArray("data");
                    for (int i = 0; i < addressArray.size(); i++) {
                        JSONObject addressJson = addressArray.getJSONObject(i);
                        int addressId = addressJson.getIntValue("addressId");
                        addressMap = new HashMap<String, Object>();
                        addressMap.put("userName", addressJson.getString("userName"));
                        addressMap.put("userPhone", addressJson.getString("userPhone"));
                        addressMap.put("province", addressJson.getString("province"));
                        addressMap.put("city", addressJson.getString("city"));
                        addressMap.put("areas", addressJson.getString("areas"));
                        addressMap.put("userAddress", addressJson.getString("userAddress"));
                        addressListTemp.put(addressId, addressMap);
                    }
                }

            }
            //请求crowd_funding服务获取项目档位信息
            String itemLevelIdStr = itemLeavelIdSet.toString();
            String itemLevelIds = itemLevelIdStr.substring(itemLevelIdStr.indexOf("[")+1,itemLevelIdStr.indexOf("]"));
            String itemLevelUrl = ConfigUtil.getConfig("item.level.list.url") + "?" + "level_id="+ URLEncoder.encode(itemLevelIds, "UTF-8");;
            HttpResult itemLevelListHttpResult = HttpUtil.doGet(itemLevelUrl);
            logger.debug("Request: {} getResponse: {}", itemLevelUrl, itemLevelListHttpResult);
            Map<String, Object> itemLevelMap = null;
            if (itemLevelListHttpResult.getStatusCode() == HttpStatus.SC_OK) {
                JSONObject resultJson = JSON.parseObject(itemLevelListHttpResult.getContent());
                int code = resultJson.getIntValue("code");
                if (code == StatusCode.OK) {
                    JSONArray itemLevelArray = resultJson.getJSONArray("data");
                    for (int i = 0; i < itemLevelArray.size(); i++) {
                        JSONObject itemLevelJson = itemLevelArray.getJSONObject(i);
                        int levelId = itemLevelJson.getIntValue("levelId");
                        itemLevelMap = new HashMap<String, Object>();
                        itemLevelMap.put("isShareBonus", itemLevelJson.getIntValue("isShareBonus"));
                        itemLevelMap.put("yearRate", itemLevelJson.getBigDecimal("yearRate"));
                        itemLevelMap.put("investmentPeriod", itemLevelJson.getIntValue("investmentPeriod"));
                        itemLevelMap.put("shareBonusPeriod", itemLevelJson.getIntValue("shareBonusPeriod"));
                        itemLevelListTemp.put(levelId, itemLevelMap);
                    }
                }
            }
            for (Map<String, Object> orderMap : aList){
                int addressId = (int)((long)orderMap.get("userAddressId"));
                addressMap = addressListTemp.get(addressId);
                if (addressMap != null) {
                    orderMap.putAll(addressMap);
                }
                int itemLevelId = (int)((long)orderMap.get("itemLevelId"));
                itemLevelMap = itemLevelListTemp.get(itemLevelId);
                if (itemLevelMap != null) {
                    orderMap.putAll(itemLevelMap);
                }
            }
        }
        return page;
    }

    /**
     * 预约订单审核
     * @param order
     * @return
     */
    @Transactional
    public Result appointmentAudit(Order order) throws IOException, OrderException {
        Result result = new Result();
        Order orderDetail = detail(order.getOrderId());
        if (orderDetail == null || orderDetail.getOrderId() == null) {
            result.setCode(StatusCode.ORDER_NOT_EXIST);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.ORDER_NOT_EXIST));
            return result;
        }
        /**----逻辑改为点击预约后立即改变档位状态，预约审核改变订单状态，修改档位信息，预约的订单数据带到认购中
        //审核通过需要改变档位的份数
        if (order.getOrderState() == OrderStateEnum.BOOK_ADUIT_PASS.getValue()) {
            //1.查询该档位剩余份数
            result = isItemLevelNumEnough(orderDetail, OrderStateEnum.BOOK_ADUIT_PASS.getValue());
            if (result.getCode() != StatusCode.OK){
                return result;
            }
        }
        **/
        //2.修改订单状态
        int i = update(order);
        if (i == 1) {
            // 逻辑改为点击预约后立即改变档位状态
            // 预约审核改变订单状态，修改档位信息，预约的订单数据带到认购中
            //3.修改项目档位信息
            if (order.getOrderState() == OrderStateEnum.BOOK_ADUIT_PASS.getValue()) {
                orderDetail.setOrderState(OrderStateEnum.BOOK_ADUIT_PASS.getValue());
                itemLevelUpdate(orderDetail);
            }
            // 审核通过变为待支付状态，
            if (order.getOrderState() == OrderStateEnum.BOOK_ADUIT_PASS.getValue()) {
                // 订单号放入rocketmq延迟队列，24小时内未支付则订单失效
                sendRocketMQDeployQueue(order.getOrderSn());
                // 订单号放入rocketmq延迟队列，22小时内未支付则提示用户
                sendRocketMQDeployQueue4Sms(order.getOrderSn());
            }

        } else {
            result.setCode(StatusCode.ORDER_ERROR_UPDATE);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.ORDER_ERROR_UPDATE));
            return result;
        }
        return null;
    }

    /**
     * 新增认购订单
     * @param order
     * @param rebateReceiveId
     * @return
     * @throws IOException
     * @throws OrderException
     */
    @Transactional
    public Result insertSubscriptions(Order order, int rebateReceiveId)  throws IllegalAccessException, IOException, OrderException {
        Result result = new Result();

        //首先校验优惠券是否有效
        if (rebateReceiveId != 0) {
            result = rebateReceiveService.validateRebate(order, rebateReceiveId);
            if (result.getCode() != StatusCode.OK) {
                return result;
            }
        }

        //订单业务
        //1.查询该档位剩余份数
        result = isItemLevelNumEnough(order, OrderStateEnum.UNPAID.getValue());
        if (result.getCode() != StatusCode.OK){
            return result;
        }
        //2新增认购订单
        //设置itemName
        Map<String, Object> tempMap = (Map<String, Object>) result.getData();
        order.setItemName((String) tempMap.get("itemName"));
        int i = insert(order);
        if (i == 1) {
            //3.订单生成后附属操作
            // 优惠券置为已使用状态
            if (rebateReceiveId != 0) {
                rebateUsedService.rebateUse(rebateReceiveId, order.getOrderSn());
            }
            // 修改项目档位信息
            itemLevelUpdate(order);

            // 生成的订单号放入rocketmq延迟队列，24小时内未支付则订单失效
            sendRocketMQDeployQueue(order.getOrderSn());
            // 订单号放入rocketmq延迟队列，22小时内未支付则提示用户
            sendRocketMQDeployQueue4Sms(order.getOrderSn());

            // 组织数据返回给客户端
            Map<String, Object> orderMap = ObjectUtils.bean2Map(order);
            orderMap.put("partnerId", tempMap.get("partnerId"));
            result.setCode(StatusCode.OK);
            result.setData(orderMap);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
        } else {
            result.setCode(StatusCode.SUBSCRIPTIONS_ORDER_ERROR_INSERT);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.SUBSCRIPTIONS_ORDER_ERROR_INSERT));
            return result;
        }
        return result;
    }

    /**
     * 查询项目档位信息是否符合购买条件
     * @param order
     * @param nextState
     * @return
     * @throws IOException
     * @throws OrderException
     */
    public Result isItemLevelNumEnough(Order order, int nextState)throws IOException, OrderException {
        Result result = new Result();
        result.setCode(StatusCode.OK);
        result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
        int itemLevelId = order.getItemLevelId();
        String itemLevelInfoUrl = ConfigUtil.getConfig("item.level.url") + "/" + itemLevelId;
        HttpResult itemLevelInfoResult = HttpUtil.doGet(itemLevelInfoUrl);
        logger.debug("Request: {} getResponse: {}", itemLevelInfoUrl, itemLevelInfoResult);
        if (itemLevelInfoResult.getStatusCode() == HttpStatus.SC_OK) {
            JSONObject resultJson = JSON.parseObject(itemLevelInfoResult.getContent());
            int code = resultJson.getIntValue("code");
            if (code == StatusCode.OK) {
                JSONObject itemLevel = resultJson.getJSONObject("data");
                //先判断项目状态，再判断份数
                boolean itemFlag = false;
                int levelStatus = itemLevel.getIntValue("levelStatus");
                //'档位状态：1：预约中；2已预约满；3候补中；4认购中；5已完成',
                if (nextState == OrderStateEnum.BOOK.getValue()) {
                    if (levelStatus == Constant.LEVEL_APPOINTING) {
                        itemFlag = true;
                    }
                } else if (nextState == OrderStateEnum.UNPAID.getValue()) {
                    if (levelStatus == Constant.LEVEL_CROWDING) {
                        itemFlag = true;
                    }
                } else if (nextState == OrderStateEnum.BOOK_ADUIT_PASS.getValue()) {
                    if (levelStatus != Constant.LEVEL_COMPLETED) {
                        itemFlag = true;
                    }
                }
                //如果是预约订单，档位状态为预约满或候补中时
                if (nextState == OrderStateEnum.BOOK.getValue()
                        && (levelStatus == Constant.LEVEL_APPOINT_FULL || levelStatus == Constant.LEVEL_CANDIDITING)) {
                    result.setCode(StatusCode.ITEM_LEVEL_HAVE_ENOUGH);
                    result.setMsg(StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_HAVE_ENOUGH));
                    return result;
                }
                if(!itemFlag) {
                    result.setCode(StatusCode.ITEM_LEVEL_NOT_MATCH);
                    result.setMsg(StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_NOT_MATCH));
                    return result;
                }

                //总份数
                boolean numFlag = false;
                int totalNumber = itemLevel.getIntValue("totalNumber");
                if (totalNumber == 0) {
                    numFlag = true;
                } else {
                    //判断剩余份数是否大于可买份数
                    int remainNum = 0;
                    if (nextState == OrderStateEnum.BOOK.getValue()) {
                        //预约份数
                        int bookNumber = itemLevel.getIntValue("bookNumber");
                        remainNum = totalNumber - bookNumber;
                    } else if (nextState == OrderStateEnum.UNPAID.getValue() || nextState == OrderStateEnum.BOOK_ADUIT_PASS.getValue()) {
                        //完成份数
                        int completedNumber = itemLevel.getIntValue("completedNumber");
                        remainNum = totalNumber - completedNumber;
                    }
                    if (order.getItemNum() > remainNum) {
                        numFlag = false;
                    }else {
                        numFlag = true;
                    }
                }
                if (!numFlag) {
                    result.setCode(StatusCode.ITEM_LEVEL_NUM_ERROR);
                    result.setMsg(StatusCode.codeMsgMap.get(StatusCode.ITEM_LEVEL_NUM_ERROR));
                    return result;
                }
                //临时传值，后面流程使用
                Map<String, Object> tempMap = new HashMap<String, Object>();
                tempMap.put("itemName", itemLevel.getString("itemName"));
                tempMap.put("partnerId", itemLevel.getIntValue("partnerId"));
                result.setData(tempMap);
            } else {
                logger.error("businesscode: {}--msg: {}" , code, StatusCode.codeMsgMap.get(code));
                throw new OrderException("请求项目服务异常 -- StatusCode: " + code, code);
            }
        } else {
            logger.error("httpcode: {}--httpcontent: {}" , itemLevelInfoResult.getStatusCode(), itemLevelInfoResult.getContent());
            throw new OrderException("请求项目服务异常 -- httpCode: " + itemLevelInfoResult.getStatusCode(), itemLevelInfoResult.getStatusCode());
        }
        return result;
    }

    /**
     * 订单完成后修改项目档位信息
     * @param order
     * @return
     * @throws OrderException
     */
    public boolean itemLevelUpdate(Order order)throws OrderException {
        //修改项目档位信息，如果请求失败，则订单也失败
        Map<String, String> paramMap = new HashMap<String, String>();
        if (order.getOrderState() == OrderStateEnum.BOOK.getValue()) {
            paramMap.put("type", "2");
        } else if (order.getOrderState() == OrderStateEnum.BOOK_ADUIT_PASS.getValue()) {
            paramMap.put("type", "1");
        } else if (order.getOrderState() == OrderStateEnum.UNPAID.getValue()) {
            paramMap.put("type", "1");
        }
        paramMap.put("item_id", order.getItemId().toString());
        paramMap.put("level_id", order.getItemLevelId().toString());
        paramMap.put("level_amount", order.getItemLevelAmount().toString());
        paramMap.put("item_num", order.getItemNum().toString());
        paramMap.put("total_amount", order.getOrderAmount().toString());
        String url = ConfigUtil.getConfig("item.level.update.url");
        HttpResult httpResult = HttpUtil.doPostForm(url, paramMap);
        logger.debug("Request: {} getResponse: {}", url, httpResult);
        if (httpResult.getStatusCode() == HttpStatus.SC_OK) {
            JSONObject resultJson = JSON.parseObject(httpResult.getContent());
            int code = resultJson.getIntValue("code");
            if (code == StatusCode.OK) {
                return true;
            } else {
                logger.error("businesscode: {}--msg: {}" , code, StatusCode.codeMsgMap.get(code));
                throw new OrderException("请求项目服务异常 -- StatusCode: " + code, code);
            }
        } else {
            logger.error("httpcode: {}--httpcontent: {}" , httpResult.getStatusCode(), httpResult.getContent());
            throw new OrderException("请求项目服务异常 -- httpCode: " + httpResult.getStatusCode(), httpResult.getStatusCode());
        }
    }

    /**
     * 获取客户端订单列表
     * @param page
     * @param map
     * @return
     * @throws IOException
     */
    public Page getPage(Page page, Map map)  throws IOException {
        int needAvatar = (int)map.get("needAvatar");
        int itemId = map.get("itemId")==null ?  0 : (int)map.get("itemId");
        if (needAvatar == Constant.YES){
            //该请求为支持人数列表的请求
            //1.查询项目状态
            String url = ConfigUtil.getConfig("item.url") + "/" + itemId + "?user_id=0";
            HttpResult itemResult = HttpUtil.doGet(url);
            logger.debug("Request: {} getResponse: {}", url, itemResult);
            if (itemResult.getStatusCode() == HttpStatus.SC_OK) {
                JSONObject resultJson = JSON.parseObject(itemResult.getContent());
                int code = resultJson.getIntValue("code");
                if (code == StatusCode.OK) {
                    JSONObject item = resultJson.getJSONObject("data");
                    int itemStatus = item.getIntValue("itemStatus");
                    if (itemStatus == Constant.ITEM_PERHEARTING) {
                        map.put("orderState", OrderStateEnum.BOOK_ADUIT_PASS.getValue());
                    } else {
                        map.put("orderState", OrderStateEnum.PAID.getValue());
                    }
                }
            }
        }
        //2.查询列表
        page = getListByPage(page, map);
        //3.判断是否是支持人数列表，然后查询头像信息
        if (needAvatar == Constant.YES) {
            List<Map<String, Object>> aList = page.getList();
            if (aList != null && aList.size() > 0) {
                //用户ID的set
                Set<Integer> userIdsSet = new HashSet<Integer>();
                //临时存放用户信息的Map
                Map<Integer, Map<String, Object>> userListTemp = new HashMap<Integer, Map<String, Object>>();
                for (Map<String, Object> orderMap : aList){
                    int userId = (int)((long)orderMap.get("userId"));
                    userIdsSet.add(userId);
                }
                //请求user_center服务获取用户地址信息
                String userIdsStr = userIdsSet.toString();
                String userIds = userIdsStr.substring(userIdsStr.indexOf("[")+1,userIdsStr.indexOf("]"));
                String userIdsUrl = ConfigUtil.getConfig("avatar.list.url") + "?" + "user_ids="+ URLEncoder.encode(userIds, "UTF-8");
                HttpResult avatarListHttpResult = HttpUtil.doGet(userIdsUrl);
                logger.debug("Request: {} getResponse: {}", userIdsUrl, avatarListHttpResult);
                if (avatarListHttpResult.getStatusCode() == HttpStatus.SC_OK) {
                    JSONObject resultJson = JSON.parseObject(avatarListHttpResult.getContent());
                    int code = resultJson.getIntValue("code");
                    if (code == StatusCode.OK) {
                        JSONArray avatarArray = resultJson.getJSONArray("data");
                        Map<String, Object> avatarMap = null;
                        for (int i = 0; i < avatarArray.size(); i++) {
                            JSONObject avatarJson = avatarArray.getJSONObject(i);
                            int userId = avatarJson.getIntValue("userId");
                            avatarMap = new HashMap<String, Object>();
                            avatarMap.put("userId", avatarJson.getIntValue("userId"));
                            avatarMap.put("avatar", avatarJson.getString("avatar"));
                            userListTemp.put(userId, avatarMap);
                        }
                        //将获取的信息组装到原data中
                        for (Map<String, Object> orderMap : aList){
                            int userId = (int)((long)orderMap.get("userId"));
                            avatarMap = userListTemp.get(userId);
                            orderMap.putAll(avatarMap);
                        }
                    }
                }
            }
        // 我的订单列表需要项目头图
        } else {
            List<Map<String, Object>> aList = page.getList();
            if (aList != null && aList.size() > 0) {
                //项目ID的set
                Set<Integer> itemIdsSet = new HashSet<Integer>();
                //临时存放用户信息的Map
                Map<Integer, Map<String, Object>> itemsTemp = new HashMap<Integer, Map<String, Object>>();
                for (Map<String, Object> orderMap : aList){
                    int itemIds = (int)((long)orderMap.get("itemId"));
                    itemIdsSet.add(itemIds);
                }
                String itemIdsStr = itemIdsSet.toString();
                String itemIds = itemIdsStr.substring(itemIdsStr.indexOf("[")+1,itemIdsStr.indexOf("]"));
                String url = ConfigUtil.getConfig("item.url") + "?item_id=" + URLEncoder.encode(itemIds, "UTF-8");
                HttpResult itemResult = HttpUtil.doGet(url);
                logger.debug("Request: {} getResponse: {}", url, itemResult);
                if (itemResult.getStatusCode() == HttpStatus.SC_OK) {
                    JSONObject resultJson = JSON.parseObject(itemResult.getContent());
                    int code = resultJson.getIntValue("code");
                    if (code == StatusCode.OK) {
                        JSONArray itemArray = resultJson.getJSONArray("data");
                        Map<String, Object> itemsMap = null;
                        for (int i = 0; i < itemArray.size(); i++) {
                            JSONObject itemJson = itemArray.getJSONObject(i);
                            int itemIdVal = itemJson.getIntValue("itemId");
                            itemsMap = new HashMap<String, Object>();
                            itemsMap.put("itemId", itemIdVal);
                            itemsMap.put("headerImage", itemJson.getString("headerImage"));
                            itemsTemp.put(itemIdVal,itemsMap);
                        }
                        //将获取的信息组装到原data中
                        for (Map<String, Object> orderMap : aList){
                            int itemIdVal = (int)((long)orderMap.get("itemId"));
                            itemsMap = itemsTemp.get(itemIdVal);
                            orderMap.putAll(itemsMap);
                        }
                    }
                }
            }

        }
        return page;
    }

    /**
     * 新增预约订单
     * @param order
     * @param rebateReceiveId
     * @return
     * @throws IOException
     * @throws OrderException
     */
    @Transactional
    public Result insertAppointment(Order order, int rebateReceiveId)  throws IllegalAccessException, IOException, OrderException {
        Result result = new Result();
        //首先校验优惠券是否有效
        if (rebateReceiveId != 0) {
            result = rebateReceiveService.validateRebate(order, rebateReceiveId);
            if (result.getCode() != StatusCode.OK) {
                return result;
            }
        }
        //订单业务
        //1.查询该档位剩余份数是否满足购买要求
        result = isItemLevelNumEnough(order, OrderStateEnum.BOOK.getValue());
        if (result.getCode() != StatusCode.OK){
            return result;
        }
        //设置itemName
        Map<String, Object> tempMap = (Map<String, Object>) result.getData();
        order.setItemName((String) tempMap.get("itemName"));
        //2.新增订单
        int i = insert(order);
        if (i == 1) {
            //3.订单生成后附属操作
            //优惠券置为已使用状态
            if (rebateReceiveId != 0) {
                rebateUsedService.rebateUse(rebateReceiveId, order.getOrderSn());
            }

            //修改档位信息
            itemLevelUpdate(order);

            // 组织数据返回给客户端
            Map<String, Object> orderMap = ObjectUtils.bean2Map(order);
            result.setData(orderMap);
        } else {
            result.setCode(StatusCode.APPOINTMENT_ORDER_ERROR_INSERT);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.APPOINTMENT_ORDER_ERROR_INSERT));
        }
        return result;
    }

    /**
     * 取消预约
     * @param order
     * @return
     */
    public Result appointmentCancel(Order order) {
        Result result = new Result();
        Order orderDetail = detail(order.getOrderId());
        //如果该订单已经审核，不可取消预约
        if (orderDetail.getOrderState() == OrderStateEnum.BOOK.getValue()) {
            int i = update(order);
            if (i == 1) {
                result.setCode(StatusCode.OK);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
            } else {
                result.setCode(StatusCode.APPOINTMENT_CANCEL_ERROR);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.APPOINTMENT_CANCEL_ERROR));
            }
        } else {
            result.setCode(StatusCode.APPOINTMENT_HAVE_AUDIT);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.APPOINTMENT_HAVE_AUDIT));
        }
        return result;
    }

    /**
     * 删除订单
     * @param order
     * @return
     */
    public Result deleteOrder(Order order) {
        Result result = new Result();
        Order orderDetail = detail(order.getOrderId());
        //只有已失效和已退款的订单可以删除
        if (orderDetail.getOrderState() == OrderStateEnum.BOOK_ADUIT_FAIL.getValue()
                || orderDetail.getOrderState() == OrderStateEnum.LOSS_EFFICACY.getValue()
                || orderDetail.getOrderState() == OrderStateEnum.REFUND_SUCCESS.getValue()) {
            int i = update(order);
            if (i == 1) {
                result.setCode(StatusCode.OK);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
            } else {
                result.setCode(StatusCode.DELETE_ORDER_ERROR);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.DELETE_ORDER_ERROR));
            }
        } else {
            result.setCode(StatusCode.ORDER_STATUS_NOT_DELETE);
            result.setMsg(StatusCode.codeMsgMap.get(StatusCode.ORDER_STATUS_NOT_DELETE));
        }
        return result;
    }

    /**
     * 根据条件获取总条数
     * @param map
     * @return
     */
    public int getCount(Map map){
        return orderDao.getCount(map);
    }

    /**
     * 通过订单编号更新订单消息
     * @param order
     * @return
     */
    public int updateBySn(Order order) {
        return orderDao.updateBySn(order);
    }
    /**
     * 用户已购份数查询
     * @param param
     */
    public int getHasPurchaseCount(Map<String, Object> param){
        List<Map<String, Object>> result = getList(param);
        int count = 0;
        if (result != null && result.size() > 0) {
            for (Map<String, Object> orderMap : result) {
                if (orderMap != null) {
                    int itemNum = Integer.parseInt(orderMap.get("itemNum").toString());
                    count += itemNum;
                }
            }
        }
        return  count;
    }

    /**
     * 生成3天订单txt文件
     */
    public void generateOrderTxt() throws IOException {
        //查询下单3天的订单
        Map<String, Object> paramMap = new HashMap<String, Object>();
        //获取4天前的日期
        Date currentDate = new Date();
        Date beforeDate = DateUtils.addDays(currentDate,-4);
        paramMap.put("finishedTimeBegin", com.meirengu.utils.DateUtils.getDayBeginTime(beforeDate));
        paramMap.put("finishedTimeEnd", com.meirengu.utils.DateUtils.getDayEndTime(beforeDate));
        paramMap.put("orderState", OrderStateEnum.PAID.getValue());
        List<Map<String, Object>> orderList = getList(paramMap);
        Map<Integer, BigDecimal> resultMap = new HashMap<Integer, BigDecimal>();
        if (orderList != null && orderList.size() > 0) {
            for (Map<String, Object> order : orderList) {
                if (order != null) {
                    int userId = Integer.parseInt(order.get("userId").toString());
                    BigDecimal costAmount = (BigDecimal)order.get("costAmount");
                    if (resultMap.get(userId) != null) {
                        BigDecimal alreadyCostAmount = (BigDecimal)resultMap.get(userId);
                        resultMap.put(userId, alreadyCostAmount.add(costAmount));
                    } else {
                        resultMap.put(userId, costAmount);
                    }
                }
            }

            String orderPath = ConfigUtil.getConfig("order.txt.path");
            String txtName = "order." + com.meirengu.utils.DateUtils.dateToStr(currentDate, "yyyy-MM-dd") + ".txt";
            String fileNameStr = orderPath + txtName;
            File fileName = new File(fileNameStr);
            if (fileName.exists()) {
                fileName.delete();
                logger.debug( "{}已删除", fileName);
            }
            fileName.createNewFile();
            logger.debug( "{}已创建", fileName);
            List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> tempMap = new HashMap<String, String>();
            for(Integer key : resultMap.keySet()){
                tempMap.put(key.toString(), resultMap.get(key).toString());
            }
            mapList.add(tempMap);
            ObjectToFile.writeObject(mapList, fileNameStr);

            //请求用户服务
            String url = ConfigUtil.getConfig("invite.reward.notify.url") + "?file_name=" + URLEncoder.encode(fileNameStr, "UTF-8");
            HttpResult itemResult = HttpUtil.doGet(url);
            logger.debug("Request: {} getResponse: {}", url, itemResult);
            //后续处理对方处理失败重新请求
        }
    }

    /**
     * 生成的订单号放入rocketmq延迟队列，24小时内未支付则订单失效
     * @param orderSn
     */
    private void sendRocketMQDeployQueue(String orderSn) {
        Message msg = new Message("deploy", "orderLoseEfficacy", orderSn.getBytes());
        msg.setKeys("OSE" + orderSn);
        //1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h 22h 1d
        msg.setDelayTimeLevel(20);
        SendResult sendResult = null;
        try {
            logger.debug("发送消息：tag:orderLoseEfficacy: {}", orderSn);
            sendResult = producer.getDefaultMQProducer().send(msg);
            logger.info("sendResult: {}", sendResult);
        } catch (MQClientException e) {
            logger.error(e.getMessage() + String.valueOf(sendResult));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 当消息发送失败时如何处理
        if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
            // TODO
            logger.error("发送消息：tag:orderLoseEfficacy: {}失败", orderSn);
            logger.error(sendResult.toString());
        }
    }
    /**
     * 生成的订单号放入rocketmq延迟队列，22小时内未支付发消息提醒
     * @param orderSn
     */
    private void sendRocketMQDeployQueue4Sms(String orderSn) {
        Message msg = new Message("deploy", "orderRemindForPay", orderSn.getBytes());
        msg.setKeys("ORFP" + orderSn);
        //1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h 22h 1d
        msg.setDelayTimeLevel(19);
        SendResult sendResult = null;
        try {
            logger.debug("发送消息：tag:orderRemindForPay: {}", orderSn);
            sendResult = producer.getDefaultMQProducer().send(msg);
            logger.info("sendResult: {}", sendResult);
        } catch (MQClientException e) {
            logger.error(e.getMessage() + String.valueOf(sendResult));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 当消息发送失败时如何处理
        if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
            // TODO
            logger.debug("发送消息：tag:orderRemindForPay: {}失败", orderSn);
            logger.error(sendResult.toString());
        }
    }
    /**
     * 订单失效
     * @return
     */
    public void orderLoseEfficacy(String orderSn) throws IOException {
        //订单在24小时内未支付，置失效
        Map<String, Object> orderMap = orderDao.orderDetailBySn(orderSn);
        Map<String, String> params = null;
        if (orderMap != null) {
            params = new HashMap<String, String>();
            int orderState = Integer.parseInt(orderMap.get("orderState").toString());
            //如果已支付，则请求用户服务修改被邀请人的投资时间
            if (orderState == OrderStateEnum.PAID.getValue()) {
                String url = ConfigUtil.getConfig("invite.invest.notify.url");
                params.put("invited_user_id", orderMap.get("userId").toString());
                params.put("invited_user_phone", orderMap.get("userPhone").toString());
                params.put("invest_time", orderMap.get("finishedTime").toString());
                HttpResult inviterResult = HttpUtil.doPostForm(url, params);
                logger.debug("Request: {} getResponse: {}", url, inviterResult);
            }else if (orderState == OrderStateEnum.BOOK_ADUIT_PASS.getValue()
                    || orderState == OrderStateEnum.UNPAID.getValue()) {
                Order order = new Order();
                order.setOrderSn(orderSn);
                order.setOrderState(OrderStateEnum.LOSS_EFFICACY.getValue());
                orderDao.updateBySn(order);

                //请求项目服务修改档位状态
                params = new HashMap<String, String>();
                params.put("item_id", orderMap.get("itemId").toString());
                params.put("level_id", orderMap.get("itemLevelId").toString());
                params.put("level_amount", orderMap.get("itemLevelAmount").toString());
                params.put("item_num", orderMap.get("itemNum").toString());
                params.put("total_amount", orderMap.get("orderAmount").toString());
                String url = ConfigUtil.getConfig("item.level.rollback.url");
                HttpResult httpResult = HttpUtil.doPostForm(url, params);
                logger.debug("Request: {} getResponse: {}", url, httpResult);

                // 发送短信提醒用户
                // 1790354=【美人谷】亲，您的#item_name#项目订单由于超时未支付，订单已关闭了。您可以登录APP重新认购。感谢你支持#item_name#项目。
                params = new HashMap<String, String>();
                params.put("tpl_id", Integer.toString(1790354));
                params.put("mobile", orderMap.get("userPhone").toString());
                params.put("item_name", orderMap.get("itemName").toString());
                String smsurl = ConfigUtil.getConfig("single.send.tpl.url");
                HttpResult smsHttpResult = HttpUtil.doPostForm(smsurl, params);
                logger.debug("Request: {} getResponse: {}", smsurl, smsHttpResult);

                // 发送消息提醒用户
                // 14986215=您的#item_name#项目订单由于超时未支付，订单已关闭了。
                params = new HashMap<String, String>();
                params.put("sender", Integer.toString(0));// 0默认为系统发送
                params.put("tpl_id", Integer.toString(14986215));
                params.put("type", Integer.toString(2));// 消息类型：1公告Announce；2提醒Remind；3信息、私信Message
                params.put("receiver", orderMap.get("userId").toString());
                params.put("item_name", orderMap.get("itemName").toString());
                String msgurl = ConfigUtil.getConfig("notify.send.tpl.url");
                HttpResult msgHttpResult = HttpUtil.doPostForm(msgurl, params);
                logger.debug("Request: {} getResponse: {}", msgurl, msgHttpResult);
            }
        } else {
            logger.error("该订单号：{}不存在", orderSn);
        }
    }

    /**
     * 订单失效前提醒
     * @return
     */
    public void orderRemindForPay(String orderSn) throws IOException {
        Map<String, Object> orderMap = orderDao.orderDetailBySn(orderSn);
        Map<String, String> params = null;
        if (orderMap != null) {
            params = new HashMap<String, String>();
            int orderState = Integer.parseInt(orderMap.get("orderState").toString());
            if (orderState == OrderStateEnum.BOOK_ADUIT_PASS.getValue()
                    || orderState == OrderStateEnum.UNPAID.getValue()) {

                /**未支付与抵扣券短信目前无法发送
                // 发送短信提醒用户
                // 1790353=【美人谷】亲，您的#item_name#项目订单还未支付，请登录APP立即支付，超时未支付将会关闭自动订单。
                params = new HashMap<String, String>();
                params.put("tpl_id", Integer.toString(1790353));
                params.put("mobile", orderMap.get("userPhone").toString());
                params.put("item_name", orderMap.get("itemName").toString());
                String smsurl = ConfigUtil.getConfig("single.send.tpl.url");
                HttpResult smsHttpResult = HttpUtil.doPostForm(smsurl, params);
                logger.debug("Request: {} getResponse: {}", smsurl, smsHttpResult);
                */

                // 发送消息提醒用户
                // 14986214=亲，您的#item_name#项目订单还未支付，请登录APP立即支付，超时未支付将会关闭自动订单。
                params.put("sender", Integer.toString(0));// 0默认为系统发送
                params.put("tpl_id", Integer.toString(14986214));
                params.put("type", Integer.toString(2));// 消息类型：1公告Announce；2提醒Remind；3信息、私信Message
                params.put("receiver", orderMap.get("userId").toString());
                params.put("item_name", orderMap.get("itemName").toString());
                String msgurl = ConfigUtil.getConfig("notify.send.tpl.url");
                HttpResult msgHttpResult = HttpUtil.doPostForm(msgurl, params);
                logger.debug("Request: {} getResponse: {}", msgurl, msgHttpResult);
            }
        } else {
            logger.error("该订单号：{}不存在", orderSn);
        }
    }


}
