package com.study.boot.Data.jobEntity;

import java.io.Serializable;

/**
 * @ClassName Message
 * @Description: TODO
 * @Author lxl
 * @Date 2020/11/23
 * @Version V1.0
 **/
public class Message implements Serializable {

    private static final long serialVersionUID = -90000090L;

    private String from;
    private String to;
    private String msg;

    public Message() {
        super();
    }

    public Message(String from, String to, String msg) {
        this.from = from;
        this.to = to;
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
