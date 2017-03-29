package com.meirengu.trade.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Page;
import com.meirengu.model.Result;
import com.meirengu.trade.common.Constant;
import com.meirengu.trade.common.OrderRpcException;
import com.meirengu.trade.common.OrderStateEnum;
import com.meirengu.trade.dao.OrderDao;
import com.meirengu.trade.model.Order;
import com.meirengu.trade.service.OrderService;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.trade.service.RebateReceiveService;
import com.meirengu.trade.service.RebateUsedService;
import com.meirengu.trade.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.utils.ObjectUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
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
    public Map<String, Object> orderDetail(int orderId) throws IOException {
        Map<String, Object> map = orderDao.orderDetail(orderId);
        if (map != null && map.size()>0) {
            //查询地址信息
            if (map.get("userAddressId") != null) {
                int userAddressId = (int)((long)map.get("userAddressId"));
                if (userAddressId != 0) {
                    //请求user_center服务获取用户地址信息
                    String addressUrl = ConfigUtil.getConfig("address.url") + "?" + "address_id="+ userAddressId;;
                    HttpResult addressHttpResult = HttpUtil.doGet(addressUrl);
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
                                addressMap.put("areas", addressJson.getString("areas"));
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
                    if (itemResult.getStatusCode() == HttpStatus.SC_OK) {
                        JSONObject resultJson = JSON.parseObject(itemResult.getContent());
                        int code = resultJson.getIntValue("code");
                        if (code == StatusCode.OK) {
                            JSONObject item = resultJson.getJSONObject("data");
                            String headerImagePath = item.getString("headerImage");
                            map.put("headerImage", headerImagePath);
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
    public Result appointmentAudit(Order order) throws IOException, OrderRpcException {
        Result result = new Result();
        Order orderDetail = detail(order.getOrderId());
        //审核通过需要改变档位的份数
        if (order.getOrderState() == OrderStateEnum.BOOK_ADUIT_PASS.getValue()) {
            //1.查询该档位剩余份数
            result = isItemLevelNumEnough(orderDetail, OrderStateEnum.BOOK_ADUIT_PASS.getValue());
            if (result.getCode() != StatusCode.OK){
                return result;
            }
        }

        //2.修改订单状态
        int i = update(order);
        if (i == 1) {
            //3.修改项目档位信息
            if (order.getOrderState() == OrderStateEnum.BOOK_ADUIT_PASS.getValue()) {
                boolean updateFlag = itemLevelUpdate(orderDetail);
                if (updateFlag) {
                    result.setCode(StatusCode.OK);
                    result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
                    return result;
                }
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
     * @throws OrderRpcException
     */
    @Transactional
    public Result insertSubscriptions(Order order, int rebateReceiveId)  throws IllegalAccessException, IOException, OrderRpcException{
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
            //3.修改项目档位信息
            boolean updateFlag = itemLevelUpdate(order);
            if (updateFlag) {
                result.setCode(StatusCode.OK);
                Map<String, Object> orderMap = ObjectUtils.bean2Map(order);
                //返给客户端partnerId,供客户调支付传参数
                orderMap.put("partnerId", tempMap.get("partnerId"));
                result.setData(orderMap);
                result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
            }
            //异步去使用优惠券
            if (rebateReceiveId != 0) {
                rebateUsedService.rebateUse(rebateReceiveId, order.getOrderSn());
            }
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
     * @throws OrderRpcException
     */
    public Result isItemLevelNumEnough(Order order, int nextState)throws IOException, OrderRpcException{
        Result result = new Result();
        result.setCode(StatusCode.OK);
        result.setMsg(StatusCode.codeMsgMap.get(StatusCode.OK));
        int itemLevelId = order.getItemLevelId();
        String itemLevelInfoUrl = ConfigUtil.getConfig("item.level.url") + "/" + itemLevelId;
        HttpResult itemLevelInfoResult = HttpUtil.doGet(itemLevelInfoUrl);
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
                tempMap.put("partnerId", itemLevel.getString("partnerId"));
                result.setData(tempMap);
            } else {
                logger.error("businesscode: " + code + "--msg: " + StatusCode.codeMsgMap.get(code));
                throw new OrderRpcException("请求项目服务异常 -- StatusCode: " + code, code);
            }
        } else {
            logger.error("httpcode: " + itemLevelInfoResult.getStatusCode() + "--httpcontent: " + itemLevelInfoResult.getContent());
            throw new OrderRpcException("请求项目服务异常 -- httpCode: " + itemLevelInfoResult.getStatusCode(), itemLevelInfoResult.getStatusCode());
        }
        return result;
    }

    /**
     * 订单完成后修改项目档位信息
     * @param order
     * @return
     * @throws OrderRpcException
     */
    public boolean itemLevelUpdate(Order order)throws OrderRpcException{
        //异步请求项目服务，修改项目档位信息，不得影响订单流程
        //目前设为同步请求，如果修改错误，则重新操作
        Map<String, String> paramMap = new HashMap<String, String>();
        if (order.getOrderState() == OrderStateEnum.BOOK.getValue()) {
            paramMap.put("type", "2");
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
        if (httpResult.getStatusCode() == HttpStatus.SC_OK) {
            JSONObject resultJson = JSON.parseObject(httpResult.getContent());
            int code = resultJson.getIntValue("code");
            if (code == StatusCode.OK) {
                return true;
            } else {
                logger.error("businesscode: " + code + "--msg: " + StatusCode.codeMsgMap.get(code));
                throw new OrderRpcException("请求项目服务异常 -- StatusCode: " + code, code);
            }
        } else {
            logger.error("httpcode: " + httpResult.getStatusCode() + "--httpcontent: " + httpResult.getContent());
            throw new OrderRpcException("请求项目服务异常 -- httpCode: " + httpResult.getStatusCode(), httpResult.getStatusCode());
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
                String userIdsUrl = ConfigUtil.getConfig("avatar.list.url") + "?" + "user_ids="+ URLEncoder.encode(userIds, "UTF-8");;
                HttpResult avatarListHttpResult = HttpUtil.doGet(userIdsUrl);
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
        }
        return page;
    }

    /**
     * 新增预约订单
     * @param order
     * @param rebateReceiveId
     * @return
     * @throws IOException
     * @throws OrderRpcException
     */
    @Transactional
    public Result insertAppointment(Order order, int rebateReceiveId)  throws IOException, OrderRpcException{
        Result result = new Result();
        //首先校验优惠券是否有效
        if (rebateReceiveId != 0) {
            result = rebateReceiveService.validateRebate(order, rebateReceiveId);
            if (result.getCode() != StatusCode.OK) {
                return result;
            }
        }
        //订单业务
        //查询该档位剩余份数是否满足购买要求
        result = isItemLevelNumEnough(order, OrderStateEnum.BOOK.getValue());
        if (result.getCode() != StatusCode.OK){
            return result;
        }
        //设置itemName
        Map<String, Object> tempMap = (Map<String, Object>) result.getData();
        order.setItemName((String) tempMap.get("itemName"));
        int i = insert(order);
        if (i == 1) {
            //异步去使用优惠券
            if (rebateReceiveId != 0) {
                rebateUsedService.rebateUse(rebateReceiveId, order.getOrderSn());
            }
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
}
