package com.songcream.simpleviewutil2.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.songcream.simpleviewutil2.R;

import utils.UIUtils;

public abstract class BaseDialog {
    public View contentView;
    public Context context;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private int animStyle= R.style.DialogAnimBtT;        //默认动画从下到上

    public BaseDialog(Context context) {
        this.context=context;
        builder=new AlertDialog.Builder(context,R.style.BaseDialog);
        contentView=initDialog(builder);
        alertDialog=builder.create();
    }

    public boolean isShowing(){
        return alertDialog.isShowing();
    }

    /**
     * 可以自定义dialog的主题
     * @param context
     * @param themeResId
     */
    public BaseDialog(Context context, int themeResId) {
        this.context=context;
        builder=new AlertDialog.Builder(context,themeResId);
        contentView=initDialog(builder);
        alertDialog=builder.create();
    }

    public abstract View initDialog(AlertDialog.Builder builder);

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAnimStyle(int animStyle){
        this.animStyle=animStyle;
    }

    public void showWithAnim(){
        if(animStyle!=0) {
            //获取Diloag所在的Window
            Window window = getAlertDialog().getWindow();
            //为Window设置动画
            window.setWindowAnimations(animStyle);
        }
        alertDialog.show();
    }

    public void setCancelOnOutsideTouch(boolean cancelable){
        alertDialog.setCanceledOnTouchOutside(cancelable);
    }

    /**
     * 宽度以屏幕的多少倍去显示
     * @param scale
     */
    public void showWidthWithAnim(float scale){
        showWithAnim();
        ViewGroup.LayoutParams layoutParams =  contentView.getLayoutParams();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        layoutParams.width = (int) (dm.widthPixels*scale);
//        layoutParams.height = dm.heightPixels;
        contentView.setLayoutParams(layoutParams);
    }

    /**
     * 宽度满屏，高度以屏幕的多少倍去显示
     * @param scale
     */
    public void showWidthWithAnimHeight(float scale){
        showWithAnim();
        ViewGroup.LayoutParams layoutParams =  contentView.getLayoutParams();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params =getAlertDialog().getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;//dialog从哪里弹出
        manager.getDefaultDisplay().getMetrics(dm);
        layoutParams.width = dm.widthPixels;
        layoutParams.height =(int) (dm.heightPixels*scale);
        getAlertDialog().getWindow().setAttributes(params);
        contentView.setLayoutParams(layoutParams);
    }

    /**
     * 从底部弹出，高度自适应
     */
    public void showWidthWithAnimBottom(){
        showWithAnim();
        ViewGroup.LayoutParams layoutParams =  contentView.getLayoutParams();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params =getAlertDialog().getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;//dialog从哪里弹出
        manager.getDefaultDisplay().getMetrics(dm);
        layoutParams.width = (int) dm.widthPixels;
        getAlertDialog().getWindow().setAttributes(params);
        contentView.setLayoutParams(layoutParams);
    }

    public void dismiss(){
        alertDialog.dismiss();
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener){
        if(alertDialog!=null){
            alertDialog.setOnDismissListener(onDismissListener);
        }
    }

    public void setCancelable(boolean b) {
        alertDialog.setCancelable(b);
    }


}
