package com.email;

import android.app.ProgressDialog;
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

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.email.service.MailHelper;
import com.email.ui.fragment.InboxFragment;
import com.email.ui.fragment.MeFragment;
import com.email.ui.fragment.PhoneFragment;
import com.email.ui.fragment.RubbishBoxFragment;

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
    private MeFragment mMeFragment;
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
    }

    private void setIndoxData() {
        new MyTask().execute();
        dialog=new ProgressDialog(this);
        dialog.setMessage("加載中...");
        dialog.show();
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
                    mMeFragment = MeFragment.newInstance("mMeFragment");
                    transaction.add(R.id.tb, mMeFragment);
                } else {
                    transaction.show(mMeFragment);
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
        } else if (mMeFragment == null && fragment instanceof MeFragment) {
            mMeFragment = (MeFragment) fragment;
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
    class MyTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... integers) {
            try {
                MailHelper.getAllMailForData("INBOX");
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
