package com.study.boot.Data.server;

import cn.hutool.core.date.DateTime;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.study.boot.Data.Util.LineChartListener;
import com.study.boot.Data.dto.LineChartEntity;
import com.study.boot.Data.enums.RedisChartStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @ClassName RecruitData03Server
 * @Description: TODO: 招聘数据03的处理
 * @Author lxl
 * @Date 2020/10/3
 * @Version V1.0
 **/
@Service
public class RecruitData03Server implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(RecruitData03Server.class);

    private Map<Date, Integer> mainMap = new HashMap<>();

    private Map<String, Integer> industryFieldMap = new HashMap<>();

    private Map<String, Integer> jobSalaryMap = new HashMap<>();

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HashRedisService hashRedisService;

    @Autowired
    private Environment env;

    public Map<String, List> getJobCountAndDate(){
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        Map<String, Integer> jobMap = hashOperations.entries(RedisChartStatus.MAIN_LINE_CHART);
        Map<String, List> ans = new HashMap<>();
        List<String> timeList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        //整理X轴　　Y轴
        jobMap.forEach((s, integer) -> {
            timeList.add(s);
        });
        timeList.sort((o1, o2) -> {
            Date d1 = DateTime.of(o1,"yyyy-MM-dd");
            Date d2 = DateTime.of(o2, "yyyy-MM-dd");
            if ((d1.getTime() - d2.getTime()) > 0)
                return 1;
            return -1;
        });
        timeList.stream().forEach(s -> numList.add(jobMap.get(s)));
        ans.put("timeline",timeList);
        ans.put("nums",numList);
        return ans;
    }

    public Map<String, Integer> getIndustryFieldMap(){
        if (industryFieldMap == null || industryFieldMap.size() == 0){
            industryFieldMap = redisTemplate.opsForHash().entries(RedisChartStatus.INDUSTRY_FIELD);
        }
        return industryFieldMap;
    }

    public Map<String, Integer> getJobSalaryMap(){
        if (jobSalaryMap == null || jobSalaryMap.size() == 0){
            jobSalaryMap = redisTemplate.opsForHash().entries(RedisChartStatus.JOB_SALARY);
        }
        return jobSalaryMap;
    }
    public void ReadExcel03(){
        log.info("开始读取文件");
        String fileName = env.getProperty("sly.data.job");
        ExcelReader excelReader = null;
        try {
            LineChartListener lineChartListener = new LineChartListener();
            excelReader = EasyExcel.read(fileName, LineChartEntity.class, lineChartListener).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
            mainMap = lineChartListener.getMainMap();
            jobSalaryMap = lineChartListener.getJobSalaryMap();
            industryFieldMap = lineChartListener.getIndustryFieldMap();
        }catch (Exception e){
            log.info("ReadExcel 03 数据失败：　{}", e.getMessage());
        }finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
        log.info("{}　文件已经读取完毕","03文件");
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread() {

            @Override
            public void run() {
                log.info("开始了");
                ReadExcel03();
                hashRedisService.addMainLineChart(mainMap);
                hashRedisService.addIndustryField(industryFieldMap);
                hashRedisService.addJobSalary(jobSalaryMap);
            }
        }.start();

    }
}
