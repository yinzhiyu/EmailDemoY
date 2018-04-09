package com.email;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.application.greendao.WhiteWordDao;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.email.app.BaseActivity;
import com.email.app.BaseApplication;
import com.email.service.MailHelper;
import com.email.table.WhiteWord;
import com.email.ui.fragment.InboxFragment;
import com.email.ui.fragment.KeyWordFragment;
import com.email.ui.fragment.PhoneFragment;
import com.email.ui.fragment.RubbishBoxFragment;
import com.email.ui.fragment.SettingFragment;
import com.email.utils.FilterUtil;
import com.email.utils.SharePreferenceUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {


    @BindView(R.id.tb)
    FrameLayout tb;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.parent)
    LinearLayout parent;
    private FragmentManager mFragmentManager;
    //      private BadgeItem mHomeNumberBadgeItem, mMusicNumberBadgeItem;
    private InboxFragment mInboxFragment;
    private RubbishBoxFragment mRubbishBoxFragment;
    private PhoneFragment mPhoneFragment;
    private KeyWordFragment mMeFragment;
    private SettingFragment mSettingFragment;
    private int lastSelectedPosition;
    private int tabIndex;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        setDefaultFragment();
        setIndoxData();
        setRefresh();
    }

    private void setIndoxData() {
        new MyTask().execute();
        dialog = new ProgressDialog(this);
        dialog.setMessage("加載中...");
        dialog.show();
    }

    public void newInstance(int num) {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (num) {
            case 0:
                mInboxFragment = InboxFragment.newInstance("");
                transaction.add(R.id.tb, mInboxFragment);
                transaction.commit();
                break;
            case 1:
                mRubbishBoxFragment = RubbishBoxFragment.newInstance("");
                transaction.add(R.id.tb, mRubbishBoxFragment);
                transaction.commit();
                break;
            case 2:
                break;
        }
    }

    private void initView() {
        /**
         *添加标签的消息数量
         */
//        mHomeNumberBadgeItem = new BadgeItem()
//                .setBorderWidth(2)
//                .setBackgroundColor(Color.RED)
//                .setText("4")
//                .setHideOnSelect(false); // TODO 控制便签被点击时 消失 | 不消失
        /**
         *添加标签的消息数量
         */
//        mMusicNumberBadgeItem = new BadgeItem()
//                .setBorderWidth(2)
//                .setBackgroundColor(Color.RED)
//                .setText("99+")
//                .setHideOnSelect(true);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        //  设置模式
        bottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setInActiveColor(R.color.gray_5);
        //  设置背景色样式
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.indox, "收件箱"))
                .addItem(new BottomNavigationItem(R.drawable.trash_can, "垃圾箱"))
                .addItem(new BottomNavigationItem(R.drawable.blacklist, "黑名单"))
                .addItem(new BottomNavigationItem(R.drawable.key_woed, "关键字"))
                .addItem(new BottomNavigationItem(R.drawable.user_setting, "设置"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        // TODO 设置 BadgeItem 默认隐藏 注意 这句代码在添加 BottomNavigationItem 之后
//        mHomeNumberBadgeItem.hide();
    }

    private void setDefaultFragment() {

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        mInboxFragment = InvestFragment.newInstance(LoanType.home);
        mInboxFragment = InboxFragment.newInstance("");
        transaction.add(R.id.tb, mInboxFragment);
        transaction.commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;

        //开启事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragment(transaction);

        /**
         * fragment 用 add + show + hide 方式
         * 只有第一次切换会创建fragment，再次切换不创建
         *
         * fragment 用 replace 方式
         * 每次切换都会重新创建
         *
         */
        switch (position) {
            case 0:
                if (mInboxFragment == null) {
                    mInboxFragment = InboxFragment.newInstance("");
                    transaction.add(R.id.tb, mInboxFragment);
                } else {
                    transaction.show(mInboxFragment);
                }
//                mHomeNumberBadgeItem.hide();
                break;
            case 1:
                if (mRubbishBoxFragment == null) {
                    mRubbishBoxFragment = RubbishBoxFragment.newInstance("");
                    transaction.add(R.id.tb, mRubbishBoxFragment);
                } else {
                    mRubbishBoxFragment.onResume();
                    transaction.show(mRubbishBoxFragment);
                }
                break;
            case 2:
                //原始逻辑
                if (mPhoneFragment == null) {
                    mPhoneFragment = PhoneFragment.newInstance("mBlacklistFragment");
                    transaction.add(R.id.tb, mPhoneFragment);
                } else {
                    transaction.show(mPhoneFragment);
                }
//                    mMusicNumberBadgeItem.hide();
                break;
            case 3:
                if (mMeFragment == null) {
                    mMeFragment = KeyWordFragment.newInstance("mMeFragment");
                    transaction.add(R.id.tb, mMeFragment);
                } else {
                    transaction.show(mMeFragment);
                }
                break;
            case 4:
                if (mSettingFragment == null) {
                    mSettingFragment = SettingFragment.newInstance("mSettingFragment");
                    transaction.add(R.id.tb, mSettingFragment);
                } else {
                    transaction.show(mSettingFragment);
                }
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


    /**
     * 隐藏当前fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mInboxFragment != null) {
            transaction.hide(mInboxFragment);
        }
        if (mRubbishBoxFragment != null) {
            transaction.hide(mRubbishBoxFragment);
        }
        if (mPhoneFragment != null) {
            transaction.hide(mPhoneFragment);
        }
        if (mMeFragment != null) {
            transaction.hide(mMeFragment);
        }
        if (mSettingFragment != null) {
            transaction.hide(mSettingFragment);
        }
    }


    /**
     * fragment防止重叠
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (mInboxFragment == null && fragment instanceof InboxFragment) {
            mInboxFragment = (InboxFragment) fragment;
        } else if (mRubbishBoxFragment == null && fragment instanceof RubbishBoxFragment) {
            mRubbishBoxFragment = (RubbishBoxFragment) fragment;
        } else if (mPhoneFragment == null && fragment instanceof PhoneFragment) {
            mPhoneFragment = (PhoneFragment) fragment;
        } else if (mMeFragment == null && fragment instanceof KeyWordFragment) {
            mMeFragment = (KeyWordFragment) fragment;
        } else if (mSettingFragment == null && fragment instanceof SettingFragment) {
            mSettingFragment = (SettingFragment) fragment;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    public void onExit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    private void setRefresh() {

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                try {
                    MailHelper.mailRefresh("INBOX");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        //从现在起，过1分钟以后，每隔1分钟执行一次。
        timer.schedule(task, 1000 * 60, 1000 * 60);

    }

    class MyTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... integers) {
            try {
                SharePreferenceUtil.saveInfo(MainActivity.this, SharePreferenceUtil.BLACKNUM, "200");//黑名单
                SharePreferenceUtil.saveInfo(MainActivity.this, SharePreferenceUtil.WHITENUM, "200");//白名单
                MailHelper.getAllMailForData("INBOX");
                //查
                WhiteWordDao smsDao = BaseApplication.getInstance().getDaoSession().getWhiteWordDao();
                List<WhiteWord> users = smsDao.loadAll();
                if (users.size() <= 0) {
                    FilterUtil.getBlackList(0);
                    FilterUtil.getBlackList(1);
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return "3Q";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            mInboxFragment.onResume();
        }
    }
}
