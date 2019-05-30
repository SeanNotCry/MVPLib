package com.wt.sean.mvplib.base;

import java.util.List;

/**
**  类名：BaseView   创建目的： "视图基类"
* @author  作者：wangtong
* @date  时间:"2019/5/14 0014 13:19"
*/
public interface BaseView<T extends BaseBen, B extends BaseBen> {
    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 隐藏加载框
     */
    void dismissLoading();

    /**
     * 获取非列表类型数据成功
     */
    void getModelSucc(B data);

    /**
     * 获取列表类型数据成功
     */
    void getListUscc(List<T> datas);

    /**
     * 获取数据错误
     *
     * @param message 错误信息  见ApiException
     */
    void getDataError(String message);

    /**
     * 执行某种操作成功
     */
    void doSomethingSucc();

    /**
     * 执行某种操作失败
     */
    void doSomethingError();
}
