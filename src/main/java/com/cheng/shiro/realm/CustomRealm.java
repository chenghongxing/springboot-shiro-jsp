package com.cheng.shiro.realm;

import com.cheng.shiro.config.MyByteSource;
import com.cheng.shiro.entity.Perms;
import com.cheng.shiro.entity.User;
import com.cheng.shiro.service.UserService;
import com.cheng.shiro.utils.ApplicationContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by chenghx on 2020/12/2 10:52
 * 自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        //根据主身份信息获取角色和权限信息
        //在工厂中获取Bean
        UserService userService = (UserService) ApplicationContextUtils.getBean("userServiceImpl");
        User userInfo = userService.getRolesByUserName(primaryPrincipal);
        if (!CollectionUtils.isEmpty(userInfo.getRoles())){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            //添加角色信息
            userInfo.getRoles().forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getName());
                //添加权限信息
                List<Perms> perms = userService.getPermsByRoleId(role.getId());
                if (!CollectionUtils.isEmpty(perms)){
                    perms.forEach(perm -> {
                        simpleAuthorizationInfo.addStringPermission(perm.getName());
                    });
                }
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        //根据用户名查询
        //在工厂中获取Bean
        UserService userService = (UserService) ApplicationContextUtils.getBean("userServiceImpl");
        User user = userService.getUserByUsername(principal);

        if (!ObjectUtils.isEmpty(user)){
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), new MyByteSource(user.getSalt()),this.getName());
        }
        return null;
    }
}
