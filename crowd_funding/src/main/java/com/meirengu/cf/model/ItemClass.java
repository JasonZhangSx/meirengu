package com.meirengu.cf.model;

import com.meirengu.model.BaseObject;

/**
 * 项目分类实体
 *
 * @author 建新
 * @create 2017-03-13 11:22
 */
public class ItemClass extends BaseObject{

    /** 众筹项目分类索引ID */
    private int classId;
    /** 众筹分类名称 */
    private String className;
    /** 父ID */
    private String classParentId;
    /** 排序，数字越小权重越大 */
    private int classSort;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassParentId() {
        return classParentId;
    }

    public void setClassParentId(String classParentId) {
        this.classParentId = classParentId;
    }

    public int getClassSort() {
        return classSort;
    }

    public void setClassSort(int classSort) {
        this.classSort = classSort;
    }
}
