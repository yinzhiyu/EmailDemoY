package com.email.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.application.greendao.MailDao;
import com.email.R;
import com.email.app.BaseApplication;
import com.email.app.BaseFragment;
import com.email.table.Mail;
import com.email.ui.adapter.InboxAdapter;
import com.email.ui.adapter.RubbishAdapter;
import com.email.utils.DividerListItemDecoration;
import com.email.utils.RecyclerViewClickListener;
import com.email.utils.SharePreferenceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 垃圾箱
 */

//public class RubbishBoxFragment extends BaseFragment implements OnListener {
public class RubbishBoxFragment extends BaseFragment {

    @BindView(R.id.rv_home_recycler)
    RecyclerView rvHomeRecycler;
    @BindView(R.id.srl_home_swipe_refresh)
    SwipeRefreshLayout srlHomeSwipeRefresh;
    @BindView(R.id.ll_null)
    LinearLayout mLlNull;
    private boolean isShowToolbar = true;
    //--------------------
    private static final int SCROLL_DISTANCE = 50;
    private LinearLayoutManager layoutManager;
    private boolean isShow = true;
    private int totalScrollDistance;
    private int pageNum = 1;
    private RubbishAdapter mRubbishAdapter;
    private int DataNum;
    public static RubbishBoxFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        RubbishBoxFragment fragment = new RubbishBoxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
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
//        rvHomeRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    //获取最后一个完全显示的ItemPosition
//                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
//                    int totalItemCount = layoutManager.getItemCount();
//                    // 判断是否滚动到底部，并且是向右滚动
//                    if (lastVisibleItem == (totalItemCount - 1) && !isShow) {// (totalItemCount - 1) && isSlidingToLast
//                        //加载更多功能的代码
//                        pageNum++;
//                        getMore(pageNum);
//                    }
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int firstVisableItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
//                if (firstVisableItem == 0) {
//                    return;
//                }
//                if ((dy > 0 && isShow) || (dy < 0 && !isShow)) {
//                    totalScrollDistance += dy;
//                }
//                if (totalScrollDistance > SCROLL_DISTANCE && isShow) {
//                    isShow = false;
//                    totalScrollDistance = 0;
//                } else if (totalScrollDistance < -SCROLL_DISTANCE && !isShow) {
//                    isShow = true;
//                    totalScrollDistance = 0;
//                }
//            }
//        });
        setLayoutManager();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(pageNum);
    }

    private void getData(int num) {
        MailDao smsDao = BaseApplication.getInstance().getDaoSession().getMailDao();
        //limit(int)  限制查询返回的数据条数。
        //offset(int) 设置查询跳过的条数，offset(int)必须和limit(int)一起使用。
//        List<Mail> smsList = smsDao.queryBuilder().where(MailDao.Properties.UsefulType.eq("0")).limit(10).offset(0).build().list();
        List<Mail> smsList = smsDao.queryBuilder().where(MailDao.Properties.UsefulType.eq(0)).build().list();
        if (srlHomeSwipeRefresh != null) {
            srlHomeSwipeRefresh.setRefreshing(false);
        }
        if (pageNum == 1) {
            if (smsList.size() > 0) {
                mLlNull.setVisibility(View.GONE);
                srlHomeSwipeRefresh.setVisibility(View.VISIBLE);
                mRubbishAdapter = new RubbishAdapter(getActivity(), smsList);
                rvHomeRecycler.setAdapter(mRubbishAdapter);
            } else {
                srlHomeSwipeRefresh.setVisibility(View.GONE);
                mLlNull.setVisibility(View.VISIBLE);
            }
        } else {

            mRubbishAdapter.notifityData(smsList);
        }

    }


    private void getMore(int num) {
        int getNum = num * 10;
        if (DataNum > getNum) {
            MailDao smsDao = BaseApplication.getInstance().getDaoSession().getMailDao();
            //limit(int)  限制查询返回的数据条数。
            //offset(int) 设置查询跳过的条数，offset(int)必须和limit(int)一起使用。
            List<Mail> smsList = smsDao.queryBuilder().where(MailDao.Properties.UsefulType.eq("0")).limit(10).offset(getNum).build().list();
            if (srlHomeSwipeRefresh != null) {
                srlHomeSwipeRefresh.setRefreshing(false);
            }
            mRubbishAdapter.notifityData(smsList);
        }else {
            Toast.makeText(activity, "我是有底线的...", Toast.LENGTH_SHORT).show();
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

}
