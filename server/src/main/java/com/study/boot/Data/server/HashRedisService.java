package com.study.boot.Data.server;

import cn.hutool.core.date.DateTime;
import com.study.boot.Data.enums.RedisChartStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName HashRedisServer
 * @Description: TODO
 * @Author lxl
 * @Date 2020/10/3
 * @Version V1.0
 **/
@Service
public class HashRedisService {

    private static final Logger log= LoggerFactory.getLogger(HashRedisService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void addMainLineChart(Map<Date, Integer> maps){
        Map<String,Integer> hashmap;
        try {
            HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
            hashmap = hashOperations.entries(RedisChartStatus.MAIN_LINE_CHART);
            if (hashmap == null || hashmap.size() == 0) {
                //TODO: 如果在这个KEY　下面没有数据　把这个数据加入进去
                Map<String, Integer> finalHashmap = hashmap;
                maps.forEach((date, integer) -> {
                    //这里面肯定已经有数据了
                    finalHashmap.put(DateTime.of(date).toDateStr(), integer);
                });
                //最后把加工完成的数据再放回去
                hashOperations.putAll(RedisChartStatus.MAIN_LINE_CHART, finalHashmap);
            }
        }catch (Exception e){
            log.info("处理主页的折线图Redis Hash数据发生异常:{}",e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addMainLineChart_V２(Map<Date, Integer> maps){
        Map<String,Integer> hashmap;
        try {
            HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
            hashmap = hashOperations.entries(RedisChartStatus.MAIN_LINE_CHART_V2);
            if (hashmap == null || hashmap.size() == 0) {
                //TODO: 如果在这个KEY　下面没有数据　把这个数据加入进去
                Map<String, Integer> finalHashmap = hashmap;
                maps.forEach((date, integer) -> {
                    //这里面肯定已经有数据了
                    finalHashmap.put(DateTime.of(date).toDateStr(), integer);
                });
                //最后把加工完成的数据再放回去
                hashOperations.putAll(RedisChartStatus.MAIN_LINE_CHART_V2, finalHashmap);
            }
        }catch (Exception e){
            log.info("处理主页的折线图Redis Hash数据发生异常:{}",e.getMessage());
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void addJobSalary(Map<String, Integer> maps){
        Map<String,Integer> hashmap = null;
        try {
            HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
            //hashmap = hashOperations.entries(RedisChartStatus.JOB_SALARY);
            if (hashmap == null || hashmap.size() == 0) {
                hashOperations.putAll(RedisChartStatus.JOB_SALARY, maps);
            }
            log.info("处理工资统计图完成");
        }catch (Exception e){
            log.info("处理工资的统计图Redis Hash数据发生异常:{}",e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addJobSalary_V2(Map<String, Integer> maps){
        Map<String,Integer> hashmap;
        try {
            HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
            hashmap = hashOperations.entries(RedisChartStatus.JOB_SALARY_V2);
            if (hashmap == null || hashmap.size() == 0) {
                hashOperations.putAll(RedisChartStatus.JOB_SALARY_V2, maps);
            }
            log.info("处理工资统计图完成");
        }catch (Exception e){
            log.info("处理工资的统计图Redis Hash数据发生异常:{}",e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addIndustryField(Map<String, Integer> maps){
        Map<String,Integer> hashmap;
        try {
            HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
            hashmap = hashOperations.entries(RedisChartStatus.INDUSTRY_FIELD);
            if (hashmap == null || hashmap.size() == 0) {
                //TODO: 不墨迹了　直接覆盖了
                hashOperations.putAll(RedisChartStatus.INDUSTRY_FIELD, maps);
            }
            log.info("行业统计Redis添加完毕");
        }catch (Exception e){
            log.info("处理行业的统计图Redis Hash数据发生异常:{}",e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addIndustryField_V2(Map<String, Integer> maps){
        Map<String,Integer> hashmap;
        try {
            HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
            hashmap = hashOperations.entries(RedisChartStatus.INDUSTRY_FIELD_V2);
            if (hashmap == null || hashmap.size() == 0) {
                //TODO: 不墨迹了　直接覆盖了
                hashOperations.putAll(RedisChartStatus.INDUSTRY_FIELD_V2, maps);
            }
            log.info("行业统计Redis添加完毕");
        }catch (Exception e){
            log.info("处理行业的统计图Redis Hash数据发生异常:{}",e.getMessage());
        }
    }
    public Map<Date, Integer> getMainLineChart(){
        Map<Date, Integer> map = null;
        try{
            HashOperations<String, Date, Integer> hashOperations = redisTemplate.opsForHash();
            map = hashOperations.entries(RedisChartStatus.MAIN_LINE_CHART);
        }catch (Exception e){
            log.info("Redis读取MainLineChart出现错误:{}",e.getMessage());
        }
        return map;
    }
}
