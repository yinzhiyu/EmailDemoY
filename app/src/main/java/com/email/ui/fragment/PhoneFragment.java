package com.email.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.application.greendao.MailBListDao;
import com.email.R;
import com.email.app.BaseApplication;
import com.email.app.BaseFragment;
import com.email.table.MailBList;
import com.email.ui.adapter.PhoneAdapter;
import com.email.utils.DividerListItemDecoration;
import com.email.utils.RecyclerViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PhoneFragment extends BaseFragment {

    @BindView(R.id.rv_home_recycler)
    RecyclerView rvHomeRecycler;
    @BindView(R.id.srl_home_swipe_refresh)
    SwipeRefreshLayout srlHomeSwipeRefresh;
    @BindView(R.id.ll_null)
    LinearLayout mLlNull;
    Unbinder unbinder;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    private LinearLayoutManager layoutManager;
    private int pageNum = 1;
    private PhoneAdapter phoneAdapter;
    private List<MailBList> mMailBLists;
    private MailBListDao mailBListDao;

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
        mailBListDao = BaseApplication.getInstance().getDaoSession().getMailBListDao();
        srlHomeSwipeRefresh.setEnabled(true);
        srlHomeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                getData(pageNum);
            }
        });
        rvHomeRecycler.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(), rvHomeRecycler,
                new RecyclerViewClickListener.OnItemClickListener() {

                    @Override
                    public void onItemLongClick(View view, final int position) {
                        new MaterialDialog.Builder(mContext)
                                .content("把此手机号移出黑名单？")
                                .positiveText("再看看")
                                .negativeText("确定")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        mailBListDao.deleteByKey(mMailBLists.get(position).getMailBId());
                                        getData(pageNum);
                                    }
                                })
                                .show();
                    }
                }));
        setLayoutManager();
    }

    private void getData(int num) {
        mMailBLists = mailBListDao.loadAll();//查询全部数据
        if (srlHomeSwipeRefresh != null) {
            srlHomeSwipeRefresh.setRefreshing(false);
        }
        if (pageNum == 1) {
            if (mMailBLists.size() > 0) {
                mLlNull.setVisibility(View.GONE);
                srlHomeSwipeRefresh.setVisibility(View.VISIBLE);
                phoneAdapter = new PhoneAdapter(getActivity(), mMailBLists);
                rvHomeRecycler.setAdapter(phoneAdapter);
            } else {
                srlHomeSwipeRefresh.setVisibility(View.GONE);
                mLlNull.setVisibility(View.VISIBLE);
            }

        } else {
            phoneAdapter.notifityData(mMailBLists);
        }

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

    @OnClick(R.id.iv_add)
    public void onViewClicked() {
        showInputDialog();
    }

    private void showInputDialog() {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(BaseApplication.getContext());
        editText.setTextColor(getResources().getColor(R.color.red));
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(getActivity());
        inputDialog.setTitle("请输入邮箱 ：").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phone = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(phone)) {
                            Toast.makeText(getActivity(), "请输入邮箱", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        insertPhone(editText.getText().toString().trim());
                        getData(pageNum);
                    }
                }).show();
    }

    private void insertPhone(String phone) {
        MailBList insertData = new MailBList(null, phone);
        mailBListDao.insert(insertData);
    }
}
