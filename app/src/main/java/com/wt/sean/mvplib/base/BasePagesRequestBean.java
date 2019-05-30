package com.wt.sean.mvplib.base;

public class BasePagesRequestBean extends BaseReqestBean {

    private String page;
    private String size;

    public BasePagesRequestBean(String page, String size) {
        this.page = page;
        this.size = size;
    }
}
