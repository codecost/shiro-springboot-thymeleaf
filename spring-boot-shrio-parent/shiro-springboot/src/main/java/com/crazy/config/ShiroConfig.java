package com.crazy.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShrioFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(securityManager);

        //添加shiro的内置过滤器
        /**
         * anon: 无需认证
         * authc: 必须认证
         * user: 必须拥有 记住我功能
         * perms: 拥有对某个资源的权限才能访问
         * role: 拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        //授权,正常情况下，没授权会跳转到未授权页面
        filterMap.put("/user/add", "perms[user:add]");//必须是user用户，而且有add权限
        filterMap.put("/user/update", "perms[user:update]");
        filterMap.put("/user/add", "authc");
        filterMap.put("/user/update", "authc");
        //设置登出
        filterMap.put("/logout", "logout");

        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录的请求
        bean.setLoginUrl("/toLogin");
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }
    //DefaultWebSecurityManager
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联reamlm
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    //1.创建realm对象，需要自定义类
    @Bean(name="userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合shiroDialect，整合shiro和thymeleaf
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }


}
