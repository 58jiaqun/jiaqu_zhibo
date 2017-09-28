package com.xhj.bms.util;

import java.io.Serializable;

/**
 * Created by Projack
 * 对外返回的实体对象
 */
public class ReplyMsg implements Serializable{

    private static final long serialVersionUID = -199982507829161706L;

    public static final Integer REPLY_STATUS_SUCCESS = 200;
    public static final Integer REPLY_STATUS_FAIL = 500;

    private Integer status;     //执行状态

    private String msg;         //提示消息

    private Object obj;         //返回的实体


    public ReplyMsg() {
    }

    public ReplyMsg(Integer status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}