package com.study.boot.Data.controller;

import com.study.boot.Data.response.StatusCode;
import com.study.boot.Data.response.BaseResponse;
import com.study.boot.Data.server.RecruitData01Server;
import com.study.boot.Data.server.RecruitData02Server;
import com.study.boot.Data.server.RecruitData03Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private RecruitData02Server recruitData02Server;

    @Autowired
    private RecruitData01Server recruitData01Server;
    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public BaseResponse test(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            System.out.println("进来了");
            baseResponse.setData("请求成功");
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
    @RequestMapping(value = "main_v1", method = RequestMethod.GET)
    public BaseResponse getMainChart_v1(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(recruitData03Server.getJobCountAndDate_v1());
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return baseResponse;
    }
    @ResponseBody
    @RequestMapping(value = "main_v2", method = RequestMethod.GET)
    public BaseResponse getMainChart_v2(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(recruitData03Server.getJobCountAndDate_v2());
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

    @ResponseBody
    @RequestMapping(value = "main02_v1", method = RequestMethod.GET)
    public BaseResponse get02Chart_v1(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(recruitData02Server.getJobCountAndDate_v1());
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return baseResponse;
    }
    @ResponseBody
    @RequestMapping(value = "main02_v2", method = RequestMethod.GET)
    public BaseResponse get02Chart_v2(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(recruitData02Server.getJobCountAndDate_v2());
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "jobSalary02", method = RequestMethod.GET)
    public BaseResponse getJobSalary02(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(recruitData02Server.getJobSalaryMap());
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "industry02", method = RequestMethod.GET)
    public BaseResponse getIndustryField02(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(recruitData02Server.getIndustryFieldMap());
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "main03_v1", method = RequestMethod.GET)
    public BaseResponse get03MainChart_v1(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(recruitData01Server.getJobCountAndDate_v1());
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "main03_v2", method = RequestMethod.GET)
    public BaseResponse get03MainChart_v2(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            baseResponse.setData(recruitData01Server.getJobCountAndDate_v2());
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "mixChart", method = RequestMethod.GET)
    public BaseResponse getMixChart(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        Map<String, Map> map = new HashMap<>();
        try {
            map.put("chart1", recruitData01Server.getJobCountAndDate_v1());
            map.put("chart2", recruitData02Server.getJobCountAndDate_v1());
            map.put("chart3", recruitData03Server.getJobCountAndDate_v1());
            baseResponse.setData(map);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return baseResponse;
    }
}
