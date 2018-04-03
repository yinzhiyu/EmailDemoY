package com.email.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.email.R;
import com.email.table.MailBList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PhoneAdapter extends RecyclerView.Adapter {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ViewHolder phoneHolder;
    private List<MailBList> list;
    private OnListener mOnListener;

    public PhoneAdapter(Context context, List<MailBList> list) {
        this.list = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    //    给接口变量赋值
    public void setCallback(OnListener callBack) {
        this.mOnListener = callBack;
    }

    public void notifityData(List<MailBList> list) {
        if (list != null && list.size() > 0) {
            this.list.addAll(list);
            notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "没有更多数据...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder holder;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_phone, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        // 给ViewHolder设置元素
        final MailBList mailBList = list.get(position);
        if (holder instanceof ViewHolder) {
            phoneHolder = (ViewHolder) holder;
            phoneHolder.mTvSender.setText("黑名单：" + mailBList.getMailAdress());
//            phoneHolder.trashCan.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    new MaterialDialog.Builder(mContext)
//                            .content("确认删除？")
//                            .positiveText("再看看")
//                            .negativeText("确定")
//                            .onPositive(new MaterialDialog.SingleButtonCallback() {
//                                @Override
//                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//
//                                }
//                            })
//                            .onNegative(new MaterialDialog.SingleButtonCallback() {
//                                @Override
//                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                    PhoneDao phoneDao = BaseApplication.getInstance().getDaoSession().getPhoneDao();
//                                    phoneDao.delete(sms);
//                                    mOnListener.onPListener();
//
//                                }
//                            })
//                            .show();
//                }
//            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_sender)
        TextView mTvSender;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}