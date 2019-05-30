package com.wt.sean.mvplib.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wt.sean.mvplib.R;


/**
 * Created by Administrator on 2017/4/24.
 */

public class LodindDialog extends Dialog {
    private Context context;
    private ImageView load_img_view;
    private TextView loading_text_view;
    private String load_st = "";
    private Window window;
    private Animation operatingAnim;
    private LinearLayout root_view;

    public LodindDialog(Context context) {
        super(context);
        this.context = context;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
    }

    public LodindDialog(Context context, String load_st) {
        super(context);
        this.context = context;
        this.load_st = load_st;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
    }

    public void showDialog() {
        setContentView(R.layout.loading_layout);
        initView();
        window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.mydialog_Animation); //设置窗口弹出动画
        show();

    }

    public void changeContent(String content) {
        loading_text_view.setText(content);
    }

    private void initView() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        int height = metric.heightPixels; // 屏幕高度（像素）
        root_view = findViewById(R.id.root_view);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width - ((width / 5) * 1), ViewGroup.LayoutParams.WRAP_CONTENT);
        root_view.setLayoutParams(layoutParams);
        load_img_view =  findViewById(R.id.load_img_view);
        loading_text_view =  findViewById(R.id.loading_text_view);
        if (!TextUtils.isEmpty(load_st)) {
            loading_text_view.setText(load_st);

        }
        operatingAnim = AnimationUtils.loadAnimation(context, R.anim.loading_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (operatingAnim != null) {
            load_img_view.startAnimation(operatingAnim);
        }

    }

    private void diss() {
        if (operatingAnim != null) {
            load_img_view.clearAnimation();
        }
        dismiss();
    }
}
