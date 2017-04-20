package com.meirengu.erp.service;

import com.meirengu.erp.model.Partner;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-03-29 21:33
 */
public interface PartnerService {

    List getPartnerList();

    List getPartnerClassList();

    boolean partnerAdd(Partner partner);

}
