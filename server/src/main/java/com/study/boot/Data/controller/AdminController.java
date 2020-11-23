package com.study.boot.Data.controller;

import com.study.boot.Data.Util.TokenUtils;
import com.study.boot.Data.response.BaseResponse;
import com.study.boot.Data.response.StatusCode;
import com.study.boot.Data.server.AdminServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("admin")
@CrossOrigin
public class AdminController extends AbstractController{

    @Autowired
    private AdminServer adminServer;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResponse adminLogin(String username, String password){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try{
            int target = adminServer.isLogin(username, password);
            if (target == 0){
                baseResponse = new BaseResponse(StatusCode.Fail.getCode(), "No Register");
            }
            if (target == 1){
                baseResponse = new BaseResponse(StatusCode.Fail.getCode(), "Wrong Password");
           }
            baseResponse.setData(TokenUtils.token(username, password));
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return baseResponse;
    }

    @RequestMapping(value = "addServer", method = RequestMethod.POST)
    public BaseResponse addServer(String from, String to){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try{
            if (adminServer.addServerMes(from, to) == 0){
                baseResponse = new BaseResponse(StatusCode.Fail.getCode(), "il to add Admin Server");
            }
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return baseResponse;
    }
}
