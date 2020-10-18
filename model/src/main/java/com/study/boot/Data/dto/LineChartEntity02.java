package com.study.boot.Data.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName LineChartEntity02
 * @Description: TODO
 * @Author lxl
 * @Date 2020/10/15
 * @Version V1.0
 **/
@Data
public class LineChartEntity02 {
    @ExcelProperty(index = 13)
    private String comNum;

    @ExcelProperty(index = 14)
    private String comIndustryField;

    @DateTimeFormat("yyyy/MM/dd")
    @ExcelProperty(index = 5)
    private Date jobPubtime;

    @ExcelProperty(index = 4)
    private String jobSalary;
}
