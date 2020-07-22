package com.zhaoning.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhaoning
 * @date 2020/7/21 - 13:30
 */

@Configuration
public class ShiroConfig {



    //3. ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager );

        //添加shiro的内置过滤器

        /*
        * anon:无需认证就可以访问
        * authc：必须认证了才能访问
        * user: 必须拥有 记住我功能才能用
        * perms：拥有对某个资源的权限才能访问
        * role：拥有某个角色权限才能访问
        *
        * */

        //登录拦截
        Map<String , String > filterMap = new LinkedHashMap<>();

        //授权 ,正常情况下，没有授权会跳转到未授权页面
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");


        //filterMap.put("/user/add","authc");
        filterMap.put("/user/*","authc");
        bean.setFilterChainDefinitionMap(filterMap);

        //设置登录请求
        bean.setLoginUrl("/toLogin");
        //设置未授权页面
        bean.setUnauthorizedUrl("/noauth");

        return bean;
    }


    //2. DefaultWebSecurityManager

    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //关联UserRealm
        securityManager.setRealm(userRealm);

        return securityManager;
    }


    //1. 创建realm对象 ， 需要自定义类
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合ShiroDialect： 用来整合shiro thymeleaf

    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
