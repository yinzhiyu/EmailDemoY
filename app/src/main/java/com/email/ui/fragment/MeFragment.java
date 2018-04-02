package com.email.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.email.R;
import com.email.app.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 其他
 */

public class MeFragment extends BaseFragment {

    public static MeFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initView();
    }


//    private void insertSMS(String sender, String content, String time, int userfulType) {
//        SMSDao smsDao = BaseApplication.getInstance().getDaoSession().getSMSDao();
//        SMS insertData = new SMS(null,SharePreferenceUtil.getInfoLong(getActivity(), SharePreferenceUtil.ID), sender, content, time, 0, userfulType);
//        smsDao.insert(insertData);
//    }

}
