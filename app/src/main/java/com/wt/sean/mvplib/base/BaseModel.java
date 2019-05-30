package com.wt.sean.mvplib.base;

import java.util.List;

import io.reactivex.Observable;

/**
**  类名：BaseModel   创建目的： "model  基类"
* @author  作者：wangtong
* @date  时间:"2019/5/14 0014 10:37"
 *
 * T List dataModel  if dont need  as BaseBen
 * B detail dataModel if dont need as BaseBen
 * R requestBean 采用json方式提交数据  若服务器采用表单提交 请自行修改
*/
public  class BaseModel<T extends BaseBen, B extends BaseBen, R extends BaseReqestBean> implements BaseModelInter<T, B, R> {

    @Override
    public Observable<BaseResponse<List<T>>> getPageData(R requestBean) {
        return getListData(requestBean);
    }

    @Override
    public Observable<BaseResponse<B>> getDetailData(R requestBean) {
        return getModelData(requestBean);
    }

    @Override
    public Observable<BaseResponse<String>> doSomeOperation(R requestBean) {
        return getNormalData(requestBean);
    }


    private Observable<BaseResponse<List<T>>> getListData(R requestBean) {
        return null;
    }

    private Observable<BaseResponse<B>> getModelData(R requestBean) {
        return null;
    }

    private Observable<BaseResponse<String>> getNormalData(R requestBean) {
        return null;
    }
}