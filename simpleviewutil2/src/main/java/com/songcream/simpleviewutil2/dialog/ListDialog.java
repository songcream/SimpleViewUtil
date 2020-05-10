package com.songcream.simpleviewutil2.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.songcream.simpleviewutil2.R;
import com.songcream.simpleviewutil2.bean.ListRecyclerViewBean;

import java.util.List;

public class ListDialog<T extends ListRecyclerViewBean> extends BaseDialog {
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private TextView textViewTitle;
    private ImageView imageViewClose;
    private RelativeLayout rlTitle;
    private OnItemClickListener onItemClickListener;
    private String dialogTitle;

    public ListDialog(Context context) {
        super(context);
    }

    public void setDialogTitle(String dialogTitle){
        this.dialogTitle=dialogTitle;
        rlTitle.setVisibility(View.VISIBLE);
        textViewTitle.setText(dialogTitle);
    }

    @Override
    public View initDialog(AlertDialog.Builder builder) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_dialog,null);
        rlTitle=view.findViewById(R.id.rl_title);
        textViewTitle=view.findViewById(R.id.title);
        imageViewClose=view.findViewById(R.id.imageView_close1);
        recyclerView =view.findViewById(R.id.maxHeightRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
        listAdapter=new ListAdapter(R.layout.item_list_title,null);
        recyclerView.setAdapter(listAdapter);
        listAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dismiss();
                if(onItemClickListener!=null) onItemClickListener.onItemClick(adapter,view,position);
            }
        });
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(true);
        return view;
    }

    private String selectTitle;
    public void showInBottom(String selectTitle){
        this.selectTitle=selectTitle;
        showWidthWithAnimBottom();
    }

    public void setData(List<T> data){
        listAdapter.setNewData(data);
    }

    public ListAdapter getListAdapter() {
        return listAdapter;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ListAdapter extends BaseQuickAdapter<T, BaseViewHolder>{
        public ListAdapter(int layoutResId, @Nullable List<T> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            TextView textView=helper.getView(R.id.textView_itemSupplierName);
            if(item.getTitle().equals(selectTitle)){
                textView.setSelected(true);
            }else{
                textView.setSelected(false);
            }
            textView.setText(item.getTitle());
        }
    }
}
