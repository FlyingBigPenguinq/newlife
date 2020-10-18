package com.study.boot.Data.server;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import com.study.boot.Data.enums.RedisChartStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName RecritData01Server
 * @Description: TODO
 * @Author lxl
 * @Date 2020/10/18
 * @Version V1.0
 **/
@Service
public class RecruitData01Server {

    @Autowired
    private RedisTemplate redisTemplate;

    public Map<String, Integer> getLineChart(){
        HashOperations hashOperations = redisTemplate.opsForHash();
        Map<String, Integer> firstMap = new HashMap<>();
        Map<String, Integer> SecondMap = hashOperations.entries(RedisChartStatus.MAIN_LINE_CHART_V2);
        Map<String, Integer> ThirdMap = hashOperations.entries(RedisChartStatus.MAIN_LINE_CHART);
        SecondMap.forEach((s, integer) -> {
            if (ThirdMap.containsKey(s)){
                firstMap.put(s, (integer + ThirdMap.get(s))/2 + RandomUtil.randomInt(-1000000, 1000000));
            }
        });
        return firstMap;
    }

    public Map<String, List> getJobCountAndDate_v1() {
        Map<String, Integer> jobMap = getLineChart();
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
        timeList.stream().limit(80).forEach(s -> numList.add(jobMap.get(s)));
        ans.put("timeline", timeList);
        ans.put("nums", numList);
        return ans;
    }

    public Map<String, List> getJobCountAndDate_v2() {
        Map<String, Integer> jobMap = getLineChart();
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

}
