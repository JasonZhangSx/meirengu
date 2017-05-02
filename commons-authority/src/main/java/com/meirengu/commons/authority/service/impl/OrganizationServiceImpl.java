package com.meirengu.commons.authority.service.impl;

import com.meirengu.commons.authority.dao.OrganizationMapper;
import com.meirengu.commons.authority.model.Organization;
import com.meirengu.commons.authority.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/4/27 12:04.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationMapper organizationMapper;
    @Override
    public List<Organization> getAllOrganization(Organization record) {
        return organizationMapper.getAllOrganization(record);
    }
}
