package com.wt.sean.mvplib.base;

import java.util.List;

import io.reactivex.Observable;

/**
 * *  类名：BaseModel   创建目的： "model  基类"
 *
 * @author 作者：wangtong
 * @date 时间:"2019/5/14 0014 10:37"
 *  T  列表中的model
 *  B  详情model
 *  R  请求bean
 */
public interface BaseModelInter< T extends BaseBen, B extends BaseBen,R extends BaseReqestBean>{

    /**
     * 获取分页数据
     * @param requestBean  请求bean
     */
     Observable<BaseResponse<List<T>>> getPageData(R requestBean);
    /**
     * 获取详情数据
     * @param requestBean  请求bean
     */
    Observable<BaseResponse<B>> getDetailData(R requestBean);
    /**
     * 进行某种操作,只需正确结果
     * @param requestBean  请求bean
     */
    Observable<BaseResponse<String>> doSomeOperation(R requestBean);

}
