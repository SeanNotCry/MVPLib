package com.wt.sean.mvplib;

/**
**  类名：MVPDataLsitener   创建目的： "数据隔离监听"
* @author  作者：wangtong
* @date  时间:"2019/5/14 0014 13:25"
*/
public interface MVPDataLsitener<T>  {

    void dataSuccess(T data);
    void dataError();

}
