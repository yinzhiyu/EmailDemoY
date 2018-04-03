package com.email.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.android.application.greendao.KeyWordDao;
import com.email.R;
import com.email.app.BaseApplication;
import com.email.app.BaseFragment;
import com.email.table.KeyWord;
import com.email.ui.adapter.KeyWordAdapter;
import com.email.utils.RecyclerViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 其他
 */

public class KeyWordFragment extends BaseFragment {
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.rv_home_recycler)
    RecyclerView rvHomeRecycler;
    @BindView(R.id.srl_home_swipe_refresh)
    SwipeRefreshLayout srlHomeSwipeRefresh;
    @BindView(R.id.ll_null)
    LinearLayout mLlNull;
    private LinearLayoutManager layoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private int pageNum = 1;
    private KeyWordAdapter mKeyWordAdapter;
    private List<KeyWord> mKeyWords;
    private KeyWordDao mKeyWordDao;

    public static KeyWordFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        KeyWordFragment fragment = new KeyWordFragment();
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
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(pageNum);
    }

    private void initView() {
        mKeyWordDao = BaseApplication.getInstance().getDaoSession().getKeyWordDao();
        srlHomeSwipeRefresh.setEnabled(true);
        srlHomeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                rvHomeRecycler.removeAllViews();
                getData(pageNum);
            }
        });
        rvHomeRecycler.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(), rvHomeRecycler,
                new RecyclerViewClickListener.OnItemClickListener() {

                    @Override
                    public void onItemLongClick(View view, final int position) {
                        new MaterialDialog.Builder(mContext)
                                .content("把此关键字移出黑名单？")
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
                                        mKeyWordDao.deleteByKey(mKeyWords.get(position).getKeyId());
                                        getData(pageNum);
//                                        mKeyWordAdapter.notifityData(mKeyWords);
                                    }
                                })
                                .show();
                    }
                }));
        setLayoutManager();
    }

    private void getData(int num) {
        mKeyWords = mKeyWordDao.loadAll();//查询全部数据
        if (srlHomeSwipeRefresh != null) {
            srlHomeSwipeRefresh.setRefreshing(false);
        }
        if (pageNum == 1) {
            if (mKeyWords.size() > 0) {
                mLlNull.setVisibility(View.GONE);
                srlHomeSwipeRefresh.setVisibility(View.VISIBLE);
                mKeyWordAdapter = new KeyWordAdapter(getActivity(), mKeyWords);
                rvHomeRecycler.setAdapter(mKeyWordAdapter);
            } else {
                srlHomeSwipeRefresh.setVisibility(View.GONE);
                mLlNull.setVisibility(View.VISIBLE);
            }

        } else {
            mKeyWordAdapter.notifityData(mKeyWords);
        }

    }

    private void setLayoutManager() {
        layoutManager = new GridLayoutManager(getActivity(),3);//new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
//        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, OrientationHelper.HORIZONTAL);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rvHomeRecycler.setLayoutManager(mStaggeredGridLayoutManager);
        rvHomeRecycler.setLayoutManager(layoutManager);
        rvHomeRecycler.setItemAnimator(new DefaultItemAnimator());
        rvHomeRecycler.setHasFixedSize(true);
//        rvHomeRecycler.addItemDecoration(new DividerListItemDecoration(getActivity(), R.drawable.shape_divide_line));
    }


    private void showInputDialog() {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(BaseApplication.getContext());
        editText.setTextColor(getResources().getColor(R.color.red));
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(getActivity());
        inputDialog.setTitle("请输入关键字 ：").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phone = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(phone)) {
                            Toast.makeText(getActivity(), "请输入需拦截的关键字", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        insertPhone(editText.getText().toString().trim());
                        getData(pageNum);
                    }
                }).show();
    }

    private void insertPhone(String keyWord) {
        KeyWord insertData = new KeyWord(null, keyWord);
        mKeyWordDao.insert(insertData);
    }

    @OnClick(R.id.iv_add)
    public void onViewClicked() {
        showInputDialog();
    }
}
