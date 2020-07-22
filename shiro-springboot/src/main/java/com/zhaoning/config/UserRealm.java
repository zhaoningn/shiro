package com.zhaoning.config;

import com.zhaoning.pojo.User;
import com.zhaoning.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhaoning
 * @date 2020/7/21 - 13:40
 */

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("执行了 =》 授权 doGetAuthorizationInfo");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //info.addStringPermission("user:add");

        //拿到当前登录的这个对象
       // Subject subject = SecurityUtils.getSubject();
        //User currentUser = (User)subject.getPrincipal();  // 拿到user对象
        User currentUser = (User)principalCollection.getPrimaryPrincipal(); // 拿到user对象

        //设置当前用户的权限
        info.addStringPermission(currentUser.getPerms());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了 =》 认证 doGetAuthenticationInfo");



        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        //连接真实的数据库
        User user = userService.queryUserByName(userToken.getUsername());

        //没有这个人
        if(user == null){
            return null;  //抛出异常
        }

        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser",user);

        //密码认证，shiro做
        //可以加密  MD5 MD5盐值加密

        return new SimpleAuthenticationInfo(user,user.getPwd(),"");
    }
}
