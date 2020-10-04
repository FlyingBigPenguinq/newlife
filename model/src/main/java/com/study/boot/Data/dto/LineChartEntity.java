package com.study.boot.Data.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName LineChartEntity
 * @Description: TODO
 * @Author lxl
 * @Date 2020/10/3
 * @Version V1.0
 **/
@Data
public class LineChartEntity {

    @ExcelProperty(index = 5)
    private String comNum;

    @ExcelProperty(index = 3)
    private String comIndustryField;

    @DateTimeFormat("yyyy/MM/dd")
    @ExcelProperty(index = 9)
    private Date jobPubtime;

    @ExcelProperty(index = 10)
    private String jobSalary;
}
