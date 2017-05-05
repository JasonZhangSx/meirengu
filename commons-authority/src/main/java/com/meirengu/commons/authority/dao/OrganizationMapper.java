package com.meirengu.commons.authority.dao;


import com.meirengu.commons.authority.model.Organization;

import java.util.List;

public interface OrganizationMapper {
    int deleteByPrimaryKey(Integer organizationId);

    int insert(Organization record);

    int insertSelective(Organization record);

    Organization getOrganization(Integer organizationId);

    List<Organization> getAllOrganization(Organization record);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);
}