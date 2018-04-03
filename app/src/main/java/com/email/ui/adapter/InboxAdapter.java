package com.email.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.email.MailContentActivity;
import com.email.R;
import com.email.table.Mail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InboxAdapter extends RecyclerView.Adapter {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ViewHolder viewHolder;
    private List<Mail> list;
    private OnListener mOnListener;

    public InboxAdapter(Context context, List<Mail> list) {
        this.list = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void notifityData(List<Mail> list) {
        if (list != null && list.size() > 0) {
            this.list.addAll(list);
            notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "没有更多数据...", Toast.LENGTH_SHORT).show();
        }
    }

    //    给接口变量赋值
    public void setCallback(OnListener callBack) {
        this.mOnListener = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder holder;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_indox, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        // 给ViewHolder设置元素
        final Mail mail = list.get(position);
        if (holder instanceof ViewHolder) {
            viewHolder = (ViewHolder) holder;
            viewHolder.tvPhone.setText("发件人：" + mail.getFrom());
            viewHolder.tvContent.setText(mail.getSubject());
            viewHolder.tvTime.setText("时间：" + mail.getSentdata());
            viewHolder.mRlClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MailContentActivity.class).putExtra("messageID", mail.getMessageID());
                    mContext.startActivity(intent);
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_click)
        RelativeLayout mRlClick;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}