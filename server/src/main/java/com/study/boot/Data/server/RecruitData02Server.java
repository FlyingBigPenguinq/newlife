package com.study.boot.Data.server;

import cn.hutool.core.date.DateTime;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.study.boot.Data.Util.Data02ChartListener;
import com.study.boot.Data.Util.LineChartListener;
import com.study.boot.Data.dto.KeyModel;
import com.study.boot.Data.dto.LineChartEntity;
import com.study.boot.Data.dto.LineChartEntity02;
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

/**
 * @ClassName RecruitData03Server
 * @Description: TODO: 招聘数据02的处理
 * @Author lxl
 * @Date 2020/10/15
 * @Version V1.0
 **/
@Service
public class RecruitData02Server implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(RecruitData02Server.class);

    private Map<Date, Integer> mainMap = new HashMap<>();

    private Map<String, Integer> industryFieldMap = new HashMap<>();

    private Map<String, Integer> jobSalaryMap = new HashMap<>();

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HashRedisService hashRedisService;

    @Autowired
    private Environment env;


    public Map<String, List> getJobCountAndDate() {
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        Map<String, Integer> jobMap = hashOperations.entries(RedisChartStatus.MAIN_LINE_CHART_V2);
        Map<String, List> ans = new HashMap<>();
        List<String> timeList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        List<String> skipTime = new ArrayList<>();   //每间隔十天输出一个日期
        //整理X轴　　Y轴
        jobMap.forEach((s, integer) -> {
            timeList.add(s);
        });
        timeList.sort((o1, o2) -> {
            Date d1 = DateTime.of(o1, "yyyy-MM-dd");
            Date d2 = DateTime.of(o2, "yyyy-MM-dd");
            if ((d1.getTime() - d2.getTime()) > 0
            )
                return 1;
            return -1;
        });
        timeList.stream().forEach(s -> numList.add(jobMap.get(s)));
        int c = 0;
        for (String s:timeList) {
            if (c++%10 == 0){
                skipTime.add(s);
            }
        }
        ans.put("timeline", skipTime);
        ans.put("nums", numList);
        return ans;
    }
    /**
     * TODO: 四月之前的小数据
     * @return
     */
    public Map<String, List> getJobCountAndDate_v1() {
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        Map<String, Integer> jobMap = hashOperations.entries(RedisChartStatus.MAIN_LINE_CHART_V2);
        Map<String, List> ans = new HashMap<>();
        List<String> timeList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        //整理X轴　　Y轴
        jobMap.forEach((s, integer) -> {
            timeList.add(s);
        });
        timeList.sort((o1, o2) -> {
            Date d1 = DateTime.of(o1, "yyyy-MM-dd");
            Date d2 = DateTime.of(o2, "yyyy-MM-dd");
            if ((d1.getTime() - d2.getTime()) > 0
            )
                return 1;
            return -1;
        });
        timeList.stream().skip(14).limit(80).forEach(s -> numList.add(jobMap.get(s)));
        ans.put("timeline", timeList);
        ans.put("nums", numList);
        return ans;
    }

    /**
     * TODO: 三月到四月的大数据
     * @return
     */
    public Map<String, List> getJobCountAndDate_v2() {
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        Map<String, Integer> jobMap = hashOperations.entries(RedisChartStatus.MAIN_LINE_CHART_V2);
        Map<String, List> ans = new HashMap<>();
        List<String> timeList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        //整理X轴　　Y轴
        jobMap.forEach((s, integer) -> {
            timeList.add(s);
        });
        timeList.sort((o1, o2) -> {
            Date d1 = DateTime.of(o1, "yyyy-MM-dd");
            Date d2 = DateTime.of(o2, "yyyy-MM-dd");
            if ((d1.getTime() - d2.getTime()) > 0
            )
                return 1;
            return -1;
        });
        timeList.stream().skip(80).forEach(s -> numList.add(jobMap.get(s)));
        ans.put("timeline", timeList);
        ans.put("nums", numList);
        return ans;
    }

    public List<KeyModel> getIndustryFieldMap() {
        List<KeyModel> industryList = new ArrayList<>();
        industryFieldMap = redisTemplate.opsForHash().entries(RedisChartStatus.INDUSTRY_FIELD_V2);
        List<KeyModel> industries = new ArrayList<>();
        industryFieldMap.forEach((s, integer) -> industries.add(new KeyModel(s, integer)));
        industries.sort((o1, o2) -> o2.getValue() - o1.getValue());
        Integer others = 0;
        for (int i = 0; i < industries.size(); i++) {
            if (i < 20) {
                industryList.add(industries.get(i));
            }
            others += industries.get(i).getValue();
        }
        industryList.add(new KeyModel("其他工业", others));
        return industryList;
    }

    public List<KeyModel> getJobSalaryMap() {
        List<KeyModel> keyModels = new LinkedList<>();
        if (jobSalaryMap == null || jobSalaryMap.size() == 0) {
            jobSalaryMap = redisTemplate.opsForHash().entries(RedisChartStatus.JOB_SALARY_V2);
        }
        jobSalaryMap.forEach((s, integer) -> keyModels.add(new KeyModel(s, integer)));
        return keyModels;
    }

    public void ReadExcel02() {
        log.info("开始读取文件");
        String fileName = env.getProperty("sly.data.job02");
        ExcelReader excelReader = null;
        try {
            Data02ChartListener data02ChartListener = new Data02ChartListener();
            excelReader = EasyExcel.read(fileName, LineChartEntity02.class, data02ChartListener).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
            mainMap = data02ChartListener.getMainMap();
            jobSalaryMap = data02ChartListener.getJobSalaryMap();
            industryFieldMap = data02ChartListener.getIndustryFieldMap();
        } catch (Exception e) {
            log.info("ReadExcel 02 数据失败：　{}", e.getMessage());
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
        log.info("{}　文件已经读取完毕", "02文件");
    }

    @Override
    public void run(String... args) throws Exception {
       /* new Thread() {

            @Override
            public void run() {
                log.info("开始了02的读取工作");
                ReadExcel02();
                hashRedisService.addMainLineChart_V２(mainMap);
                hashRedisService.addIndustryField_V2(industryFieldMap);
                hashRedisService.addJobSalary_V2(jobSalaryMap);
            }
        }.start();
        */

    }
}
