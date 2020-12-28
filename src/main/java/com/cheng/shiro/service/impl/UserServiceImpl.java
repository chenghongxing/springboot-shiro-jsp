package com.cheng.shiro.service.impl;

import com.cheng.shiro.dao.UserDao;
import com.cheng.shiro.entity.Perms;
import com.cheng.shiro.entity.User;
import com.cheng.shiro.service.UserService;
import com.cheng.shiro.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenghx on 2020/12/4 16:35
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void register(User user) {
        //对密码进行MD5 + utils + hash散列
        String salt = SaltUtils.getSalt(8);
        user.setSalt(salt);
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024);
        user.setPassword(md5Hash.toHex());
        userDao.insert(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public User getRolesByUserName(String username) {
        return userDao.getRolesByUserName(username);
    }

    @Override
    public List<Perms> getPermsByRoleId(String roleId) {
        return userDao.getPermsByRoleId(roleId);
    }
}
