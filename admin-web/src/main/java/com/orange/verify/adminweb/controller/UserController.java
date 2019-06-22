package com.orange.verify.adminweb.controller;

import com.orange.verify.adminweb.annotation.Open;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统管理账号
 */
@Controller
@RequestMapping(value = "user")
public class UserController {

    @Open(explain = "系统管理员登陆")
    @RspHandle
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Response login(@RequestBody User user) {

        try {
            // 从SecurityUtils里边创建一个 subject
            Subject subject = SecurityUtils.getSubject();
            // 在认证提交前准备 token（令牌）
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            // 执行认证登陆
            subject.login(token);
        }catch (Exception e) {
            return Response.build(ResponseCode.LOGIN_ERROR);
        }

        return Response.build(ResponseCode.LOGIN_SUCCESS);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "logout",method = RequestMethod.POST)
    @ResponseBody
    public Response logout() {

        try {

            Subject subject = SecurityUtils.getSubject();
            subject.logout();

        }catch (Exception e) {
            return Response.build(ResponseCode.LOGOUT_ERROR);
        }

        return Response.build(ResponseCode.LOGOUT_SUCCESS);
    }

}
