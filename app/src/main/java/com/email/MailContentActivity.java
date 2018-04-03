
package com.email;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.application.greendao.MailDao;
import com.email.app.BaseApplication;
import com.email.table.Mail;

import java.lang.ref.WeakReference;
import java.util.List;

public class MailContentActivity extends Activity {

    private TextView tv_addr, tv_mailsubject, tv_mailcontent;
    private ListView lv_mailattachment;
    private WebView wv_mailcontent;
    private Button btn_cancel, btn_relay;
    //    private ArrayList<InputStream> attachmentsInputStreams;
    private String messageID;
    private Handler handler;
    private List<Mail> smsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_mailcontent);
        messageID = getIntent().getStringExtra("messageID");
//        attachmentsInputStreams = ((BaseApplication) getApplication()).getAttachmentsInputStreams();
        getData();
    }

    private void getData() {
        MailDao smsDao = BaseApplication.getInstance().getDaoSession().getMailDao();
       smsList = smsDao.queryBuilder().where(MailDao.Properties.MessageID.eq(messageID)).build().list();
        init();
    }

    private void init() {
        handler = new MyHandler(this);
        tv_addr = (TextView) findViewById(R.id.tv_addr);
        tv_mailsubject = (TextView) findViewById(R.id.tv_mailsubject);
        tv_mailcontent = (TextView) findViewById(R.id.tv_mailcontent);
//        if (email.getAttachments().size() > 0) {
//            lv_mailattachment = (ListView) findViewById(R.id.lv_mailattachment);
//            lv_mailattachment.setVisibility(View.VISIBLE);
//            lv_mailattachment.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, email.getAttachments()));
//            lv_mailattachment.setOnItemClickListener(new OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                    new Thread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            handler.obtainMessage(0, "开始下载\"" + email.getAttachments().get(position) + "\"").sendToTarget();
//                            InputStream is = attachmentsInputStreams.get(position);
//                            String path = new IOUtil().stream2file(is, Environment.getExternalStorageDirectory().toString() + "/temp/" + email.getAttachments().get(position));
//                            if (path == null) {
//                                handler.obtainMessage(0, "下载失败！").sendToTarget();
//                            } else {
//                                handler.obtainMessage(0, "文件保存在：" + path).sendToTarget();
//                            }
//                        }
//                    }).start();
//                }
//            });
//        }

        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_relay = (Button) findViewById(R.id.btn_relay);

        tv_addr.setText(smsList.get(0).getFrom());
        tv_mailsubject.setText(smsList.get(0).getSubject());
        if (smsList.get(0).getHtml()) {
            wv_mailcontent = (WebView) findViewById(R.id.wv_mailcontent);
            wv_mailcontent.setVisibility(View.VISIBLE);
            wv_mailcontent.loadDataWithBaseURL(null, smsList.get(0).getContent(), "text/html", "utf-8", null);
           // wv_mailcontent.getSettings().setLoadWithOverviewMode(true);
           // wv_mailcontent.getSettings().setUseWideViewPort(true);
            //设置缩放
            wv_mailcontent.getSettings().setBuiltInZoomControls(true);
            
            //网页适配
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int scale = dm.densityDpi;
            if (scale == 240) {
                wv_mailcontent.getSettings().setDefaultZoom(ZoomDensity.FAR);
            } else if (scale == 160) {
                wv_mailcontent.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);
            } else {
                wv_mailcontent.getSettings().setDefaultZoom(ZoomDensity.CLOSE);
            }
            wv_mailcontent.setWebChromeClient(new WebChromeClient());
            tv_mailcontent.setVisibility(View.GONE);
        } else {
            tv_mailcontent.setText(smsList.get(0).getContent());
        }

        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MailContentActivity.this.finish();
            }
        });

        btn_relay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                Toast.makeText(MailContentActivity.this, "修改中...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MailContentActivity.this, MailEditActivity.class).putExtra("EMAIL", smsList.get(0).getFrom()).putExtra("TYPE", 1));
            }
        });       
        /*btn_relay.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MailContentActivity.this, MailEditActivity.class).putExtra("EMAIL", email).putExtra("type", 2));
                return true;
            }
        });*/
    }

    private static class MyHandler extends Handler {

        private WeakReference<MailContentActivity> wrActivity;

        public MyHandler(MailContentActivity activity) {
            this.wrActivity = new WeakReference<MailContentActivity>(activity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            final MailContentActivity activity = wrActivity.get();
            switch (msg.what) {
                case 0:
                    Toast.makeText(activity.getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        };
    };

}
