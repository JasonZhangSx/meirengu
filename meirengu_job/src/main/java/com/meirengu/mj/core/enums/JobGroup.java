package com.meirengu.mj.core.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuxueli on 17/5/9.
 */
public enum JobGroup {

    TRADE_GROUP(1, "交易项目任务组"),

    CF_GROUP(2, "众筹项目任务组"),

    UC_GROUP(3, "用户中心项目任务组"),

    PAY_GROUP(4, "支付网关项目任务组");

    private final int id;

    private final String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private JobGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static JobGroup getJobGroupById(int id) {
        JobGroup[] array = JobGroup.values();
        for (JobGroup jobGroup : array) {
            if (jobGroup.getId() == id) {
                return jobGroup;
            }
        }
        return null;
    }

    public static List<Map<String, Object>> getJobGroupList() {
        List<Map<String, Object>> jobGroupList =  new ArrayList<Map<String, Object>>();
        JobGroup[] array = JobGroup.values();
        Map<String, Object> jobGroupMap = null;
        for (JobGroup jobGroup : array) {
            jobGroupMap = new HashMap<String, Object>();
            jobGroupMap.put("id", jobGroup.getId());
            jobGroupMap.put("name", jobGroup.getName());
            jobGroupList.add(jobGroupMap);
        }
        return jobGroupList;
    }

    public static void main(String[] args) {
        JobGroup[] array = JobGroup.values();
        for (JobGroup jobGroup : array) {
            System.out.println(jobGroup.getId()+"---"+jobGroup.getName());
        }

    }
}
