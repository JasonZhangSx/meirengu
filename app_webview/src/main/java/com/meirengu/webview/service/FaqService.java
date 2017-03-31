package com.meirengu.webview.service;

import com.alibaba.fastjson.JSON;

/**
 * @author Marvin
 * @create 2017-03-30 下午9:28
 */
public interface FaqService {

    /**
     * 获取FAQ分类
     * @return
     */
    public JSON faqClasses();

    /**
     * 获取分类FAQ问题
     * @param classId
     * @return
     */
    public JSON faqByClassId(int classId);

}
