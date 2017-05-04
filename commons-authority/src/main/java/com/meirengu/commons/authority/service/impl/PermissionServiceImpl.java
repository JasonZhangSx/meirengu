package com.meirengu.commons.authority.service.impl;

import com.meirengu.commons.authority.dao.PermissionMapper;
import com.meirengu.commons.authority.model.Permission;
import com.meirengu.commons.authority.model.Role;
import com.meirengu.commons.authority.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/16 17:30.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findPermission(List<Role> roleList) {
        return tree(permissionMapper.findPermission(roleList),0);
    }
    @Override
    public List<Permission> getAllPermission() {
        return tree(permissionMapper.getAllPermission(),0);
    }

    @Override
    public Permission selectById(Long id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public int insert(Permission permission) {
        return permissionMapper.insert(permission);
    }

    @Override
    public int update(Permission permission) {
        return permissionMapper.update(permission);
    }

    @Override
    public int delete(Long id) {
        return permissionMapper.delete(id);
    }

    /**
     * 递归遍历树形
     * @param list 查询结果
     * @param icParentId 父节点
     * @return 树形结构
     */
    public List<Permission> tree(List<Permission> list, int icParentId){
        List<Permission> permissionList = new ArrayList<>();
        for (Permission vo : list){
            if (vo.getParentId()==icParentId){
                List<Permission> voList=tree(list,vo.getId().intValue());
                vo.setList(voList);
                permissionList.add(vo);
            }
        }
        return permissionList;
    }
}
