package com.study.boot.Data.Util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.study.boot.Data.dto.LineChartEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @ClassName LineChartListener
 * @Description: TODO
 * @Author lxl
 * @Date 2020/10/3
 * @Version V1.0
 **/
public class LineChartListener extends AnalysisEventListener<LineChartEntity> {

    private static Logger log = LoggerFactory.getLogger(LineChartListener.class);

    private Map<Date, Integer> mainMap = new HashMap<>();

    private Map<String, Integer> industryFieldMap = new HashMap<>();

    private Map<String, Integer> jobSalaryMap = new HashMap<>();

    @Override
    public void invoke(LineChartEntity lineChartEntity, AnalysisContext analysisContext) {
        log.info("解析到一条数据：{}", JSON.toJSONString(lineChartEntity));
        try {
            //处理主页折线图的逻辑
            String comNum = lineChartEntity.getComNum();
            Integer num = mainMap.get(lineChartEntity.getJobPubtime());
            if (num == null) {
                mainMap.put(lineChartEntity.getJobPubtime(), ConvertString(comNum));
            }
            mainMap.put(lineChartEntity.getJobPubtime(), num + ConvertString(comNum));

            //处理员工工资的逻辑
            String jobSalary = lineChartEntity.getJobSalary();
            if (jobSalary != null) {
                insertSalary(convertSalary(jobSalary));
            }
            //处理企业领域的逻辑
            String industry = lineChartEntity.getComIndustryField();
            if (industry != null || industry.length() != 0) {
                industryMapAdd(industry);
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //在读完所有的数据之后，不到一百个还会剩下不少
    }

    private Integer ConvertString (final String str){
        if (!str.contains("-")){
            if (str.contains("2000")){
                return 2000;
            }else {
                return 15;
            }
        }else{
            String s = str.split("-")[0];
            s.replace("k","000");
            return Integer.parseInt(s);
        }
    }

    private Integer convertSalary(String str){
        if (str.contains("-")) {
            String salary = str.replace("k", "000");
            String h = salary.split("-")[0];
            String t = salary.split("-")[1];
            return (Integer.parseInt(t) + Integer.parseInt(h))/2;
        }else {
            // null 的话则不出处理数据
            return null;
        }
    }

    private void insertSalary(final Integer salary){
        if (salary == null){
            return;
        }
        if (salary <= 5000){
            salaryMapAdd("salary<= 5000");
        }else if ( salary>5000 && salary <= 12000){
            salaryMapAdd("5000 < salary <= 12000");
        }else if (salary > 12000 && salary <= 25000){
            salaryMapAdd("12000 < salary <= 25000");
        }else if (salary > 25000 && salary <=35000){
            salaryMapAdd("25000 < salary <= 35000");
        }else if (salary > 35000 && salary <= 55000){
            salaryMapAdd("35000 < salary <= 80000");
        }else {
            salaryMapAdd("salary > 80000");
        }
    }
   /**
    * @MethodName: salaryMapAdd
    * @Description: TODO: 把指定的Key中的Value + 1
    * @Param: [key]
    * @Return: void
    * @Author: lxl
    * @Date: 下午8:04
   **/
    private void salaryMapAdd(final String key){
        Integer i = jobSalaryMap.get(key);
        if (i == null){
            i = 0;
        }
        i++;
        jobSalaryMap.put(key, i);
    }

    /**
     * @MethodName: salaryMapAdd
     * @Description: TODO: 领域Map　给指定的key　value + 1
     * @Param: [key]
     * @Return: void
     * @Author: lxl
     * @Date: 下午8:05
    **/
    private void industryMapAdd(final String key){
        Integer i = industryFieldMap.get(key);
        if (i == null){
            i = 0;
        }
        i++;
        industryFieldMap.put(key, i);
    }

    public Map<Date, Integer> getMainMap(){
        return mainMap;
    }

    public Map<String, Integer> getIndustryFieldMap(){
        return industryFieldMap;
    }

    public Map<String, Integer> getJobSalaryMap(){
        return jobSalaryMap;
    }
}
