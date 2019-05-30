package com.wt.sean.mvplib.base;

import com.wt.sean.mvplib.http.BaseSchedulerProvider;
import com.wt.sean.mvplib.http.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BasePresenter<T extends BaseBen, B extends BaseBen, R extends BaseReqestBean, M extends BaseModel<T, B, R>, V extends BaseView<T, B>> {

    private M mModel;
    private WeakReference<V> mViewRef;
    protected BaseSchedulerProvider schedulerProvider = SchedulerProvider.getInstance();

    //获取列表数据类型
    public static final int REQUEST_TYPE_LIST = 1;
    //获取详情数据类型
    public static final int REQUEST_TYPE_MODEL = 2;
    //获取无数据类型
    public static final int REQUEST_TYPE_NORMAL = 3;

    protected CompositeDisposable mDisposable = new CompositeDisposable();

    public void despose() {
        mDisposable.dispose();
    }

    public void bindModeAndView(M mode, V view) {
        this.mModel = mode;
        mViewRef = new WeakReference<>(view);
    }

    public boolean isAttach() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public V getView() {
        if (isAttach()) {
            return mViewRef.get();
        } else {
            return null;
        }
    }

    public void onDettach() {
        if (null != mViewRef) {
            mViewRef.clear();
            mViewRef = null;
        }
    }


    public void sendHttpRequest(int type, R requestBean) {
        getView().showLoading();
        switch (type) {
            case REQUEST_TYPE_LIST:
                Disposable disposableList = mModel.getPageData(requestBean)
                        .compose(ResponseTransformer.handleResult())
                        .compose(schedulerProvider.applySchedulers())
                        .subscribe(new Consumer<List<T>>() {
                            @Override
                            public void accept(List<T> baseBens) throws Exception {
                                getView().dismissLoading();
                                getView().getListUscc(baseBens);
                            }
                        }, new MyErrorConsumer() {
                            @Override
                            public void doWhileOurExcepction(String displayMessage) {
                                getView().dismissLoading();
                                getView().getDataError(displayMessage);
                            }
                        });
                mDisposable.add(disposableList);
                break;

            case REQUEST_TYPE_MODEL:

                Disposable disposableMode = mModel.getDetailData(requestBean)
                        .compose(ResponseTransformer.handleResult())
                        .compose(schedulerProvider.applySchedulers())
                        .subscribe(new Consumer<B>() {
                            @Override
                            public void accept(B baseBen) throws Exception {
                                getView().dismissLoading();
                                getView().getModelSucc(baseBen);
                            }
                        }, new MyErrorConsumer() {
                            @Override
                            public void doWhileOurExcepction(String displayMessage) {
                                getView().dismissLoading();
                                getView().getDataError(displayMessage);
                            }
                        });
                mDisposable.add(disposableMode);
                break;

            case REQUEST_TYPE_NORMAL:
                Disposable disposableNormal = mModel.doSomeOperation(requestBean)
                        .compose(ResponseTransformer.handleResult())
                        .compose(schedulerProvider.applySchedulers())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                getView().dismissLoading();
                                getView().doSomethingSucc();
                            }
                        }, new MyErrorConsumer() {
                            @Override
                            public void doWhileOurExcepction(String displayMessage) {
                                getView().dismissLoading();
                                getView().doSomethingError();
                                getView().getDataError(displayMessage);
                            }
                        });
                mDisposable.add(disposableNormal);
                break;

            default:
                break;
        }
    }
}

