package com.cheng.shiro.dao;

import com.cheng.shiro.entity.Perms;
import com.cheng.shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by chenghx on 2020/12/4 16:20
 */
@Mapper
public interface UserDao {
    int insert(User user);

    User getUserByUsername(String username);

    User getRolesByUserName(String username);

    //根据角色id查询权限集合
    List<Perms> getPermsByRoleId(String roleId);

}
