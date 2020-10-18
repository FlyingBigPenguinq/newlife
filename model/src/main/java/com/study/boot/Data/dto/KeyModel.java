package com.study.boot.Data.dto;

import lombok.Data;

/**
 * @ClassName Industry
 * @Description: TODO
 * @Author lxl
 * @Date 2020/10/12
 * @Version V1.0
 **/
@Data
public class KeyModel {
    private String name;
    private Integer value;

    public KeyModel(String name, Integer value) {
        super();
        this.name = name;
        this.value = value;
    }
}
