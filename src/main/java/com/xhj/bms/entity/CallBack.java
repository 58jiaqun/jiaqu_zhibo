package com.xhj.bms.entity;

/**
 * Created by wujian on 2017-05-17.
 */
public class CallBack {
  private  String  appId;
  private  String  from;
  private  String  to;
  private  String  userData;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }
}
