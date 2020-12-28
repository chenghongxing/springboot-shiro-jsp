package com.cheng.shiro.config;

import com.cheng.shiro.cache.RedisCacheManager;
import com.cheng.shiro.realm.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenghx on 2020/12/2 10:37
 * 用来整合shiro框架相关的配置类
 */
@Configuration
public class ShiroConfig {

    //1.创建shiroFilter 负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        filterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //设置认证界面路径
        filterFactoryBean.setLoginUrl("/login.jsp");
        

        //配置系统的受限资源
        //配置系统公共资源
        Map<String,String> map = new HashMap<>();
        map.put("/user/login","anon");
        map.put("/user/register","anon");
        map.put("/register.jsp","anon");
        map.put("/index.jsp","authc");//authc请求这个资源需要认证和授权
        filterFactoryBean.setFilterChainDefinitionMap(map);
        return  filterFactoryBean;
    }

    //2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(CustomRealm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }
    //3.创建自定义realm
    @Bean
    public CustomRealm getRealm(){
        CustomRealm customRealm = new CustomRealm();
        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customRealm.setCredentialsMatcher(credentialsMatcher);


        //开启缓存管理
        customRealm.setCacheManager(new RedisCacheManager());
        customRealm.setCachingEnabled(true);//开启全局缓存
        customRealm.setAuthenticationCachingEnabled(true);//开启认证缓存
        customRealm.setAuthenticationCacheName("authenticationCache");
        customRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
        customRealm.setAuthorizationCacheName("authorizationCache");
        return customRealm;
    }


}
