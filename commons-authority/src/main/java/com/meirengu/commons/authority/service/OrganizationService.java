package com.meirengu.commons.authority.service;

import com.meirengu.commons.authority.model.Organization;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/4/27 12:02.
 */
public interface OrganizationService {

    List<Organization> getAllOrganization(Organization record);
}
