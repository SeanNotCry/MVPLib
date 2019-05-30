package com.wt.sean.mvplib.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wt.sean.mvplib.R;
import com.wt.sean.mvplib.util.AppUtil;
import com.wt.sean.mvplib.util.ColorUtil;
import com.wt.sean.mvplib.widget.LodindDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import butterknife.ButterKnife;

/**
 * *  类名：BaseActivity   创建目的： "基类"
 *
 * @author 作者：wangtong
 * @date 时间:"2019/5/14 0014 14:54"
 */
public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity implements BaseView {

    public P mPresenter;
    public M mModel;

    public LinearLayout barView;

    public View footView;
    public TextView tvFoot;

    public boolean hadMoreData = true;
    public boolean isLoading = false;

    public int mPageNum = 1;

    protected int page_size = 10;//每页显示多少条
    protected LodindDialog baseload;
    protected Activity mActivity;
    private InputMethodManager imm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mActivity = this;
        footView = LayoutInflater.from(this).inflate(R.layout.item_foot, null, false);
        tvFoot = footView.findViewById(R.id.tv_foot);
        mPresenter = getT(this, 0);
        mModel = getT(this, 1);
        mPresenter.bindModeAndView(mModel, this);
        ButterKnife.bind(this);
        initBarView();
        initView();
        registerBroad();
    }

    private void initBarView() {
        barView = findViewById(R.id.bar_view);
        if (null != barView) {
            barView.getLayoutParams().height = getStatusBarHeight(this);
            barView.setVisibility(View.VISIBLE);
            try {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) barView.getLayoutParams();
                layoutParams.height = getStatusBarHeight(this);
                barView.setLayoutParams(layoutParams);
            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }

    private void initView() {
        addListener();
        initEveryOne();
    }

    protected abstract void addListener();

    protected abstract void initEveryOne();

    protected abstract int getLayoutId();


    /**
     * 跳转到下一个页面
     *
     * @param gotoActivty
     */
    protected void starActivity(Class<?> gotoActivty) {
        startActivity(new Intent(mActivity, gotoActivty));
    }

    /**
     * 跳转到下一个页面
     *
     * @param gotoActivty
     */
    protected void starActivity(Class<?> gotoActivty, String key, int value) {
        Intent intent = new Intent(mActivity, gotoActivty);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    /**
     * 跳转到下一个页面
     *
     * @param intent
     */
    protected void starActivity(Intent intent) {
        startActivity(intent);
    }

    /**
     * 隐藏软键盘
     */
    protected void saveEditTextAndCloseIMM() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return;
    }

    /**
     * 打开软键盘
     */
    protected void openEditTextIMM() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void shortToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }


    protected void showLoad(String string) {
        if (mActivity == null || mActivity.isDestroyed() || mActivity.isFinishing()) {
            return;
        }
        if (baseload != null && baseload.isShowing()) {
            baseload.dismiss();
        }
        baseload = new LodindDialog(this, string);
        baseload.showDialog();

    }

    protected void changeLoad(String content) {
        if (mActivity == null || mActivity.isDestroyed() || mActivity.isFinishing()) {
            return;
        }
        if (baseload != null && baseload.isShowing()) {
            baseload.changeContent(content);
        } else {
            baseload = new LodindDialog(this, content);
            baseload.showDialog();
        }
    }

    public void dismissload() {
        if (baseload != null && baseload.isShowing()) {
            baseload.dismiss();
        }
    }


    /**
     * 广播部分
     */
    IntentFilter filter;

    //注册广播
    public void registerBroad() {
        String[] actions = filterActions();
        if (actions == null || actions.length == 0) {
            return;
        }

        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        for (String action : actions) {
            filter.addAction(action);
        }
        filter.addCategory(getPackageName());
        registerReceiver(receiver, filter);
    }

    //注销广播
    public void unRegister() {
        if (filter != null) {
            unregisterReceiver(receiver);
            filter = null;
        }
    }

    public String[] filterActions() {
        return null;
    }

    public void onReceive(Context context, Intent intent) {

    }

    BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            BaseActivity.this.onReceive(context, intent);
        }
    };


    public static <T> T getT(Object o, int i) {

        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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


    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    protected void setImmersiveStatusBar(boolean fontIconDark, int statusBarColor) {
        setTranslucentStatus();
        if (fontIconDark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    || AppUtil.isMIUI()
                    || AppUtil.isFlyme()) {
                setStatusBarFontIconDark(true);
            } else {
                if (statusBarColor == Color.WHITE) {
                    statusBarColor = 0xffcccccc;
                }
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
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体是否为深色
     */
    private void setStatusBarFontIconDark(boolean dark) {
        // 小米MIUI
        try {
            Window window = getWindow();
            Class clazz = getWindow().getClass();
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
            Window window = getWindow();
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
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    @Override
    protected void onDestroy() {
        unRegister();
        baseload = null;
        super.onDestroy();
    }


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

