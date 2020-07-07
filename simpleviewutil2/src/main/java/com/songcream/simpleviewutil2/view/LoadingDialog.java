package com.songcream.simpleviewutil2.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.songcream.simpleviewutil2.R;
import com.songcream.simpleviewutil2.dialog.BaseDialog;

import androidx.appcompat.app.AlertDialog;

public class LoadingDialog extends BaseDialog {
    private TextView textViewContent;
    private SpinKitView spinKitView;

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    public View initDialog(AlertDialog.Builder builder) {
        View view= LayoutInflater.from(context).inflate(R.layout.loading_dialog,null);
        textViewContent=view.findViewById(R.id.textView_dialogLoading_text);
        spinKitView=view.findViewById(R.id.imageView_dialogLoading_src);
        builder.setView(view);
        builder.setCancelable(true);
        setAnimStyle(-1);
        return view;
    }

    public void show(String text){
        setCancelOnOutsideTouch(false);
        textViewContent.setText(text);
        textViewContent.setVisibility(View.VISIBLE);
        showWithAnim();
    }

    public void show(){
        setCancelOnOutsideTouch(false);
        textViewContent.setText("");
        textViewContent.setVisibility(View.GONE);
        showWithAnim();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
