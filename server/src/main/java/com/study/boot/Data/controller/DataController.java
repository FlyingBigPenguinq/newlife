package com.study.boot.Data.controller;

import com.study.boot.Data.response.StatusCode;
import com.study.boot.Data.response.BaseResponse;
import com.study.boot.Data.server.RecruitData03Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DataController
 * @Description: TODO
 * @Author lxl
 * @Date 10/2/20
 * @Version V1.0
 **/
@Controller
@RequestMapping("data")
@CrossOrigin
public class DataController extends AbstractController{

    @Autowired
    private RecruitData03Server recruitData03Server;

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public BaseResponse test(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);

        try {
            Map<String, Integer> data = new HashMap<>();
            data.put("jobCount",100);
            data.put("salaryCount",5000);
            baseResponse.setData(data);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return baseResponse;
    }


    @ResponseBody
    @RequestMapping(value = "main", method = RequestMethod.GET)
    public BaseResponse getMainChart(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(recruitData03Server.getJobCountAndDate());
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "jobSalary", method = RequestMethod.GET)
    public BaseResponse getJobSalary(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(recruitData03Server.getJobSalaryMap());
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "industry", method = RequestMethod.GET)
    public BaseResponse getIndustryField(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(recruitData03Server.getIndustryFieldMap());
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return baseResponse;
    }
}
