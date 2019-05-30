package com.wt.sean.mvplib.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wt.sean.mvplib.R;
import com.wt.sean.mvplib.widget.LodindDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import butterknife.Unbinder;

/**
 * *  类名：BaseFragment   创建目的： "fragment基类"
 *
 * @author 作者：wangtong
 * @date 时间:"2019/5/14 0014 17:44"
 */
public abstract class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment implements BaseView {


    public BaseActivity mActivity;
    public P mPresenter;
    public M mModel;
    private LinearLayout barView;

    protected LodindDialog baseload;

    public View footView;
    public TextView tvFoot;
    public boolean hadMoreData = true;
    public boolean isLoading = false;
    public int mPageNum = 1;
    public int page_size = 10;

    protected boolean isViewCreated = false;
    protected boolean isUiVisible = false;

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        footView = inflater.inflate(R.layout.item_foot,null,false);
        tvFoot = footView.findViewById(R.id.tv_foot);
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        mPresenter = getT(this, 0);
        mModel = getT(this, 1);
        mPresenter.bindModeAndView(mModel, this);
        unbinder = ButterKnife.bind(this, view);
        isViewCreated = true;
        initBarView();
        initEveryOne();
        registerBroad();
    }

    private void initBarView() {
        barView = mActivity.findViewById(R.id.bar_view);
        if (null != barView) {
            barView.getLayoutParams().height = getStatusBarHeight(mActivity);
            barView.setVisibility(View.VISIBLE);
            try {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) barView.getLayoutParams();
                layoutParams.height = getStatusBarHeight(mActivity);
                barView.setLayoutParams(layoutParams);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    /**
     * Short toast.
     *
     * @param content
     */
    public void shortToast(String content) {
        try {
            Toast.makeText(mActivity, content, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 得到状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected void showLoad(String string) {
        if (baseload != null && baseload.isShowing()) {
            baseload.dismiss();
        }
        baseload = new LodindDialog(mActivity, string);
        baseload.showDialog();

    }

    protected void dismissload() {
        if (baseload != null && baseload.isShowing()) {
            baseload.dismiss();
        }
    }

    protected abstract void initEveryOne();

    public static <T> T getT(Object o, int i) {

        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 广播部分
     */
    IntentFilter filter;

    BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            BaseFragment.this.onReceive(context, intent);
        }
    };

    public String[] filterActions() {
        return null;
    }

    public void onReceive(Context context, Intent intent) {

    }

    public void registerBroad() {
        if (mActivity != null) {
            String[] actions = filterActions();
            if (actions == null || actions.length == 0) {
                return;
            }
            filter = new IntentFilter();
            filter.addCategory(mActivity.getPackageName());
            for (String action : actions) {
                filter.addAction(action);
            }
            mActivity.registerReceiver(receiver, filter);
        }
    }

    public void unRegister() {
        if (mActivity != null && filter != null) {
            mActivity.unregisterReceiver(receiver);
            filter = null;
        }
    }


    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    protected void setImmersiveStatusBar(boolean fontIconDark, int statusBarColor) {
        setTranslucentStatus();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setStatusBarDarkTheme(mActivity, fontIconDark);
        } else {
            if (statusBarColor == Color.WHITE) {
                statusBarColor = 0xffcccccc;
            }
        }
        setStatusBarPlaceColor(statusBarColor);
    }

    private void setStatusBarPlaceColor(int statusColor) {
        if (barView != null) {
            barView.setBackgroundColor(statusColor);
        }
    }

    /**
     * 设置状态栏透明
     */
    private void setTranslucentStatus() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mActivity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体是否为深色
     */
    public void setStatusBarFontIconDark(boolean dark) {
        // 小米MIUI
        try {
            Window window = mActivity.getWindow();
            Class clazz = mActivity.getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {       //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 魅族FlymeUI
        try {
            Window window = mActivity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // android6.0+系统
        // 这个设置和在xml的style文件中用这个<item name="android:windowLightStatusBar">true</item>属性是一样的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                mActivity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUiVisible = true;
            lazyLoad();
        } else {
            isUiVisible = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isUiVisible = true;
            lazyLoad();
        } else {
            isUiVisible = false;
        }
    }

    private void lazyLoad() {
        if (isViewCreated && isUiVisible) {
            pullData();
            isUiVisible = false;
            isViewCreated = false;
        }
    }

    protected  void pullData(){

    }

    protected void starNexActivty(Class<?> next) {
        mActivity.startActivity(new Intent(mActivity, next));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegister();
        dismissload();
        unbinder.unbind();
    }

    protected abstract int getLayoutId();


    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void getModelSucc(BaseBen data) {

    }

    @Override
    public void getListUscc(List datas) {

    }

    @Override
    public void getDataError(String message) {

    }

    @Override
    public void doSomethingSucc() {

    }

    @Override
    public void doSomethingError() {

    }
}
