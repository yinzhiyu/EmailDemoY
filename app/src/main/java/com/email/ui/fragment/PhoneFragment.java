package com.email.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.email.R;
import com.email.app.BaseFragment;
import com.email.utils.DividerListItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PhoneFragment extends BaseFragment {

    @BindView(R.id.rv_home_recycler)
    RecyclerView rvHomeRecycler;
    @BindView(R.id.srl_home_swipe_refresh)
    SwipeRefreshLayout srlHomeSwipeRefresh;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    Unbinder unbinder;
    private LinearLayoutManager layoutManager;
    private int pageNum = 1;
//    private PhoneAdapter phoneAdapter;
//    private List<Phone> smsList;

    public static PhoneFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        PhoneFragment fragment = new PhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_phone, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(pageNum);
    }

    private void initView() {

        srlHomeSwipeRefresh.setEnabled(true);
        srlHomeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                getData(pageNum);
            }
        });
        setLayoutManager();
    }

    private void getData(int num) {
//        PhoneDao phoneDao = BaseApplication.getInstance().getDaoSession().getPhoneDao();
//        smsList = phoneDao.queryBuilder()
//                .where(PhoneDao.Properties.Id.eq(SharePreferenceUtil.getInfoLong(getActivity(), SharePreferenceUtil.ID))).build().list();
//        if (srlHomeSwipeRefresh != null) {
//            srlHomeSwipeRefresh.setRefreshing(false);
//        }
//        if (pageNum == 1) {
//            if (smsList.size() > 0) {
//                mLlNull.setVisibility(View.GONE);
//                srlHomeSwipeRefresh.setVisibility(View.VISIBLE);
//                phoneAdapter = new PhoneAdapter(getActivity(), smsList);
//                phoneAdapter.setCallback(this);
//                rvHomeRecycler.setAdapter(phoneAdapter);
//            } else {
//                srlHomeSwipeRefresh.setVisibility(View.GONE);
//                mLlNull.setVisibility(View.VISIBLE);
//            }
//
//        } else {
//            phoneAdapter.notifityData(smsList);
//        }

    }

    private void setLayoutManager() {
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHomeRecycler.setLayoutManager(layoutManager);
        rvHomeRecycler.setItemAnimator(new DefaultItemAnimator());
        rvHomeRecycler.setHasFixedSize(true);
        rvHomeRecycler.addItemDecoration(new DividerListItemDecoration(getActivity(), R.drawable.shape_divide_line));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    private void insertPhone(String phone) {
//        PhoneDao phoneDao = BaseApplication.getInstance().getDaoSession().getPhoneDao();
//        Phone insertData = new Phone(null, SharePreferenceUtil.getInfoLong(getActivity(), SharePreferenceUtil.ID), phone);
//        phoneDao.insert(insertData);
//    }
//
//    private void showInputDialog() {
//    /*@setView 装入一个EditView
//     */
//        final EditText editText = new EditText(BaseApplication.getContext());
//        editText.setTextColor(getResources().getColor(R.color.color_FF0000));
//        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
//        AlertDialog.Builder inputDialog =
//                new AlertDialog.Builder(getActivity());
//        inputDialog.setTitle("请输入手机号：").setView(editText);
//        inputDialog.setPositiveButton("确定",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String phone = editText.getText().toString().trim();
//                        if (TextUtils.isEmpty(phone)) {
//                            SnackbarUtil.showLongSnackbar(cl, "请输入手机号", SnackbarUtil.WHITE, SnackbarUtil.ORANGE);
//                            return;
//                        }
//                        insertPhone(editText.getText().toString().trim());
//                        getData(pageNum);
//                    }
//                }).show();
//    }

}
