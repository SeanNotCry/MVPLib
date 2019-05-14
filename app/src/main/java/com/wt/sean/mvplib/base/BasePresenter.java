package com.wt.sean.mvplib.base;

import java.lang.ref.WeakReference;

public class BasePresenter <M,V> {

    private M mModel;
    private WeakReference<V> mViewRef;

    public void bindModeAndView(M mode,V view){
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
}
