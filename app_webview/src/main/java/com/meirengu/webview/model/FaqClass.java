package com.meirengu.webview.model;

import com.meirengu.model.BaseObject;

import java.util.Date;

/**
 * Created by huoyan403 on 3/10/2017.
 */
public class FaqClass extends BaseObject{

    private Integer classId;
    /** 常见问题分类索引id **/
    private String className;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
