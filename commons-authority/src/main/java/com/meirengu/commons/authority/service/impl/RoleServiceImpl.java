package com.meirengu.commons.authority.service.impl;


import com.meirengu.commons.authority.dao.PermissionDao;
import com.meirengu.commons.authority.dao.RoleDao;
import com.meirengu.commons.authority.dao.UserDao;
import com.meirengu.commons.authority.model.Page;
import com.meirengu.commons.authority.model.Role;
import com.meirengu.commons.authority.model.User;
import com.meirengu.commons.authority.service.PageService;
import com.meirengu.commons.authority.service.RoleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static Log log = LogFactory.getLog(RoleServiceImpl.class);
    @Resource
    RoleDao roleDao;

    @Resource
    UserDao userDao;

    @Resource
    PermissionDao permissionDao;

    //@Resource
    //MemcachedClient memcachedClient;

    @Resource
    PageService<Role> pageService;

    @Override
    public List<Role> getRolesByUsername(String name) {
        return roleDao.getRolesByUsername(name);
    }

    @Override
    public Page<Role> getPageList(Page<Role> page, Role entity) {
        return pageService.getListByPage(page, entity, roleDao);
    }

    @Override
    public List<Role> getAllRole() {
        return roleDao.getAllRole();
    }

    @Override
    public int assignRoles(Integer[] roleId, Integer accountId, String accountName) {
        roleDao.deleteRelated(accountId);
        int i = 0;
        if (roleId != null) {
            User user = new User();
            List<Role> roleList = new ArrayList<Role>();
            for (Integer id : roleId) {
                roleList.add(new Role(id));
            }
            user.setRoleList(roleList);
            user.setId(accountId);
            i = roleDao.saveRelated(user);

            if (!clearRoleAndPermData(accountName)) {
                i = 0;
            }

        } else {
            log.info(accountName + "doesn't  assign any roles...");
            i = 1;
        }

        return i;
    }

    @Override
    public boolean saveRole(Role role) {
        try {
            roleDao.saveRole(role);
            return true;
        } catch (Exception e) {
            log.error("roleService save role error", e);
        }
        return false;
    }

    @Override
    public boolean deleteRole(List<String> idList) {
        try {
            roleDao.deleteRole(idList);
            roleDao.deleteRelatedA(idList);
            roleDao.deleteRelatedP(idList);
            return true;
        } catch (Exception e) {
            log.error("roleService delete role error", e);
        }
        return false;

    }

    @Override
    public Role selectRole(Integer id) {
        return roleDao.selectRole(id);
    }

    @Override
    public boolean modifyRole(Role role) {
        try {
            roleDao.updateRole(role);
            return true;
        } catch (Exception e) {
            log.error("roleService modify role error", e);
        }
        return false;
    }


    /**
     * 清理缓存
     *
     * @param loginName
     * @return
     * @Description:
     */
    /**
    private boolean clearRoleAndPermData(String loginName) {
        try {
            memcachedClient.delete(loginName + "_UserRoleData");
            return true;
        } catch (Exception e) {
            log.error("clearRoleAndPermData error", e);
        }
        return false;
    }
     */

}
