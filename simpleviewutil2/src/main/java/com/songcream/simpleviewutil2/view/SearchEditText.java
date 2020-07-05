package com.songcream.simpleviewutil2.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.songcream.simpleviewutil2.R;

public class SearchEditText extends RelativeLayout {
    private EditText editText;
    private ImageView imageViewDelete;

    public SearchEditText(Context context) {
        super(context);
        init(context,null,0);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.edittext_layout,this);
        editText=findViewById(R.id.editText_activitySearch_search);
        imageViewDelete=findViewById(R.id.imageView_editText_delete);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(editText.getText().toString())){
                    imageViewDelete.setVisibility(INVISIBLE);
                }else{
                    imageViewDelete.setVisibility(VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        imageViewDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

    public EditText getEditText(){
        return editText;
    }
}
