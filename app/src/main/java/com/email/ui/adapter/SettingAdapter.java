package com.email.ui.adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.email.HomeActivity;
import com.email.MailCaogaoxiangActivity;
import com.email.MailConstactsActivity;
import com.email.MailEditActivity;
import com.email.R;
import com.email.app.BaseActivity;
import com.email.app.BaseApplication;
import com.email.utils.EmailFormatUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingAdapter extends RecyclerView.Adapter {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ViewHolder phoneHolder;
    private List<String> list;

    public SettingAdapter(Context context, List<String> list) {
        this.list = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder holder;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_setting, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        // 给ViewHolder设置元素
        final String title = list.get(position);
        if (holder instanceof ViewHolder) {
            phoneHolder = (ViewHolder) holder;
            phoneHolder.tvTitle.setText(title);
            phoneHolder.llTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    switch (title) {
                        case "添加联系人":
                            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                            builder.setTitle("添加联系人");

                            View view=mLayoutInflater.inflate(R.layout.email_add_address, null);
                            final EditText name=(EditText) view.findViewById(R.id.name);
                            final EditText addr=(EditText) view.findViewById(R.id.address);

                            builder.setView(view);
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    insertAddress(name.getText().toString().trim(),addr.getText().toString().trim());
                                }
                            });
                            builder.setNegativeButton("取消", null);
                            builder.show();
                            break;
                        case "联系人":
                            intent = new Intent(mContext, MailConstactsActivity.class);
                            mContext.startActivity(intent);
                            break;
                        case "写邮件":
                            intent = new Intent(mContext, MailEditActivity.class);
                            mContext.startActivity(intent);
                            break;
                        case "草稿箱":
                            intent = new Intent(mContext, MailCaogaoxiangActivity.class);
                            mContext.startActivity(intent);
                            break;
                        case "退出登录":
                            BaseActivity.logout(mContext);
                            break;
                    }
                }
            });
        }
    }
    /**
     * 添加联系人
     */
    private void insertAddress(String user,String address){
        if(user==null){
            Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();
        }else{
            if(!EmailFormatUtil.emailFormat(address)){
                Toast.makeText(mContext, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
            }else{
                Uri uri=Uri.parse("content://com.emailconstantprovider");
                ContentValues values=new ContentValues();
                values.put("mailfrom", BaseApplication.info.getUserName());
                values.put("name", user);
                values.put("address", address);
                mContext.getContentResolver().insert(uri, values);

                Toast.makeText(mContext, "添加数据成功", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_title)
        LinearLayout llTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}