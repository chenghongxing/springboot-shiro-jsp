package com.cheng.shiro.service;

import com.cheng.shiro.entity.Perms;
import com.cheng.shiro.entity.User;

import java.util.List;

/**
 * Created by chenghx on 2020/12/4 16:33
 */
public interface UserService {
    void register(User user);

    User getUserByUsername(String username);

    User getRolesByUserName(String username);

    List<Perms> getPermsByRoleId(String roleId);

}
