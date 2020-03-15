package com.crazy.config;

import com.crazy.pojo.User;
import com.crazy.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义Realm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserServiceImpl userService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");
        //拿到当前的用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        info.addStringPermission(user.getPerms());
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证");

        UsernamePasswordToken userToken = (UsernamePasswordToken)token;

        //连接真实的数据库
        User user = userService.queryUserByName(userToken.getUsername());
        if(user==null){//没有这个用户
            return null;
        }

        Subject subject1 = SecurityUtils.getSubject();
        Session session = subject1.getSession();
        session.setAttribute("loginUser", user);

        //密码认证，shiro做,密码可以加密，正常md5加密，还可以md5盐值加密
        return new SimpleAuthenticationInfo(user, user.getPwd(), "");
    }
}
