package com.songcream.simpleviewutil2.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.ybq.android.spinkit.SpinKitView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.songcream.simpleviewutil2.R;

public class EmptyView extends RelativeLayout {
    public ImageView imageViewSrc;
    public TextView textViewContent,btnAction;
    public ImageView imageViewLoading;
    private SpinKitView spinKitView;
    private View attachView;
    private static int srcDefNoData =R.drawable.no_data;
    private static String strDefNoData = "暂无数据";
    private static int animDefLoading =R.drawable.logo_loading;
    private static int srcDefaultNetError =R.drawable.net_error;
    private static AnimationDrawable animationDrawable;
    private boolean useDrawableAnimation =true;

    public static void initDefault(int loadingDrawable,int emptySrc,String emptyString,int errorSrc){
        srcDefNoData =emptySrc;
        strDefNoData =emptyString;
        animDefLoading =loadingDrawable;
        srcDefaultNetError =errorSrc;
    }

    public EmptyView(Context context) {
        super(context);
        init(context,null,0);
    }

    public EmptyView(Context context, boolean center) {
        super(context);
        initCenter(context,null,0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void initCenter(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.empty_view_center,this);
        imageViewSrc=findViewById(R.id.imageView_emptyView_src);
        textViewContent=findViewById(R.id.textView_emptyView_text);
        imageViewLoading=findViewById(R.id.imageView_emptyView_loading);
        btnAction = findViewById(R.id.btn_action);
        spinKitView=findViewById(R.id.spinKitView);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context,R.layout.empty_view,this);
        imageViewSrc=findViewById(R.id.imageView_emptyView_src);
        textViewContent=findViewById(R.id.textView_emptyView_text);
        imageViewLoading=findViewById(R.id.imageView_emptyView_loading);
        btnAction = findViewById(R.id.btn_action);
        spinKitView=findViewById(R.id.spinKitView);
    }

    public void attachView(View view){
        if(view.getParent() instanceof ViewGroup) {
            this.attachView=view;
            ViewGroup viewParent = (ViewGroup) view.getParent();
            EmptyViewParent rlEmptyParent=new EmptyViewParent
                    (getContext());
            int index=viewParent.indexOfChild(view);

            //这里修复smartRefreshlayout动态添加view后无法识别content的bug
            if(viewParent instanceof SmartRefreshLayout){
                ViewGroup.LayoutParams rlEmptyParentParams=new ViewGroup.LayoutParams(view.getLayoutParams());
                rlEmptyParent.setLayoutParams(rlEmptyParentParams);
                ((SmartRefreshLayout)viewParent).setRefreshContent(rlEmptyParent);
            }
            //如果父布局是RelativeLayout要设置id防止其他的控件位置错乱
            else if(viewParent instanceof RelativeLayout){
                RelativeLayout.LayoutParams rlParams= (LayoutParams) view.getLayoutParams();
                LayoutParams rlEmptyParentParams=new LayoutParams(rlParams);
                for(int verb=0;verb<rlParams.getRules().length;verb++){
                    rlEmptyParentParams.addRule(verb,rlParams.getRules()[verb]);
                }
                rlEmptyParentParams.setMargins(0,0,0,0);
                rlEmptyParent.setLayoutParams(rlEmptyParentParams);
                rlEmptyParent.setId(view.getId());
                viewParent.addView(rlEmptyParent,index,rlEmptyParentParams);
            }else{
                ViewGroup.LayoutParams rlEmptyParentParams=new ViewGroup.LayoutParams(view.getLayoutParams());
                rlEmptyParent.setLayoutParams(rlEmptyParentParams);
                viewParent.addView(rlEmptyParent,index,rlEmptyParentParams);
            }

            viewParent.removeView(view);
            rlEmptyParent.addView(view,view.getLayoutParams());
            rlEmptyParent.addView(this);
            this.setVisibility(GONE);
        }
    }

    public void showEmptyView(int src, String content){
        if(attachView!=null){
            attachView.setVisibility(INVISIBLE);
            this.setVisibility(VISIBLE);
        }
        spinKitView.setVisibility(INVISIBLE);
        imageViewLoading.setVisibility(INVISIBLE);
        imageViewSrc.setVisibility(VISIBLE);
        textViewContent.setVisibility(VISIBLE);

        imageViewSrc.setImageResource(src);
        textViewContent.setText(content);
    }

    /**
     * 如果加载数据为空需要刷新按钮或跳转到其他界面等则用此方法
     * @param src 空的提示图片
     * @param content 显示内容为空提示信息
     * @param btnText 按钮显示文字
     * @param onClickListener 按钮触发操作
     */
    public void showEmptyView(int src, String content, String btnText, OnClickListener onClickListener){
        if(attachView!=null){
            attachView.setVisibility(INVISIBLE);
            this.setVisibility(VISIBLE);
        }
        spinKitView.setVisibility(INVISIBLE);
        imageViewLoading.setVisibility(INVISIBLE);
        imageViewSrc.setVisibility(VISIBLE);
        textViewContent.setVisibility(VISIBLE);

        imageViewSrc.setImageResource(src);
        textViewContent.setText(content);

        if(TextUtils.isEmpty(btnText))
            return;
        btnAction.setVisibility(VISIBLE);
        btnAction.setText(btnText);
        if(onClickListener!=null)
            btnAction.setOnClickListener(onClickListener);
    }

    public void showEmptyLoading(){
        if(attachView!=null){
            attachView.setVisibility(INVISIBLE);
            this.setVisibility(VISIBLE);
        }
        if(useDrawableAnimation){
            imageViewLoading.setVisibility(VISIBLE);
            if(animationDrawable==null) animationDrawable= (AnimationDrawable) getResources().getDrawable(animDefLoading);
            imageViewLoading.setImageDrawable(animationDrawable);
            animationDrawable.start();
        }else{
            spinKitView.setVisibility(VISIBLE);
        }
        imageViewSrc.setVisibility(INVISIBLE);
        textViewContent.setVisibility(INVISIBLE);
    }

    public void showEmptyView(){
        if(attachView!=null){
            attachView.setVisibility(INVISIBLE);
            this.setVisibility(VISIBLE);
        }
        spinKitView.setVisibility(INVISIBLE);
        imageViewLoading.setVisibility(INVISIBLE);
        imageViewSrc.setImageResource(srcDefNoData);//恢复默认
        imageViewSrc.setVisibility(VISIBLE);
        textViewContent.setText(strDefNoData);//恢复默认
        textViewContent.setVisibility(VISIBLE);
    }

    public void showNetErrorView(){
        showEmptyView(srcDefaultNetError,"网络开小差了");
    }

    public void showErrorView(String err){showEmptyView(srcDefaultNetError,err);}

    /**
     * 如果加载失败需要刷新按钮等需求则用此方法
     * @param err 错误信息
     * @param btnText 按钮显示文字
     * @param onClickListener 按钮触发操作
     */
    public void showErrorView(String err, String btnText, OnClickListener onClickListener){
        showEmptyView(R.drawable.net_error,err);
        if(TextUtils.isEmpty(btnText))
            return;
        btnAction.setVisibility(VISIBLE);
        btnAction.setText(btnText);
        if (onClickListener!=null)
            btnAction.setOnClickListener(onClickListener);
    }

    public void hideEmptyView(){
        if(attachView!=null){
            attachView.setVisibility(VISIBLE);
            this.setVisibility(INVISIBLE);
        }
        if(animationDrawable!=null) animationDrawable.stop();
    }

    public boolean isUseDrawableAnimation() {
        return useDrawableAnimation;
    }

    public void setUseDrawableAnimation(boolean useDrawableAnimation) {
        this.useDrawableAnimation = useDrawableAnimation;
    }

    public SpinKitView getSpinKitView() {
        return spinKitView;
    }

    private class EmptyViewParent extends RelativeLayout {
        protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
            if (!(this.getParent() instanceof RelativeLayout)) {
                super.dispatchRestoreInstanceState(container);
            }
        }

        public EmptyViewParent(Context context) {
            super(context);
        }

        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            for(int i = 0; i < this.getChildCount(); ++i) {
                View child = this.getChildAt(i);
                child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
            }
        }

        //动态的测量targetView的高度，然后设置emptyView高度一致
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            View targetView = null;
            View emptyView = null;

            for(int i = 0; i < this.getChildCount(); ++i) {
                View child = this.getChildAt(i);
                if (!(child instanceof EmptyView)) {
                    targetView = child;
                } else {
                    emptyView = child;
                }
            }

            if (targetView == null) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                targetView.measure(widthMeasureSpec, heightMeasureSpec);
                if (emptyView != null) {
                    emptyView.measure(MeasureSpec.makeMeasureSpec(targetView.getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(targetView.getMeasuredHeight(), MeasureSpec.EXACTLY));
                }

                this.setMeasuredDimension(targetView.getMeasuredWidth(), targetView.getMeasuredHeight());
            }

        }
    }
}
