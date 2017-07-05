package com.meirengu.commons.authority.service.impl;
import com.meirengu.commons.authority.model.Account;
import com.meirengu.commons.authority.model.LogOperationDetail;
import com.meirengu.commons.authority.service.LogOperationDetailService;
import com.meirengu.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderCandidate服务实现层 
 * @author 建新
 * @create Tue Mar 14 17:15:51 CST 2017
 */
@Service
public class LogOperationDetailServiceImpl extends BaseServiceImpl<LogOperationDetail> implements LogOperationDetailService{

    private static final Logger logger = LoggerFactory.getLogger(LogOperationDetailServiceImpl.class);



}
