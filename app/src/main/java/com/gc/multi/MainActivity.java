package com.gc.multi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.gc.multi.deadlock.DeadLockUtils;

import com.gc.multi.asynctask.MainAsyncTaskActivity;
import com.gc.multi.threadlocal.ThreadLocalActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.btn_volatile)
    Button btnVolatile;
    @BindView(R.id.btn_wait_notify)
    Button btnWaitNotify;
    @BindView(R.id.btn_lock)
    Button btnLock;
    @BindView(R.id.tv_log)
    TextView tvLog;
    @BindView(R.id.btn_synchronized_1)
    Button btnSynchronized1;
    @BindView(R.id.btn_synchronized_2)
    Button btnSynchronized2;
    @BindView(R.id.btn_synchronized_3)
    Button btnSynchronized3;
    @BindView(R.id.btn_synchronized_4)
    Button btnSynchronized4;
    @BindView(R.id.ll_nav)
    LinearLayout llNav;

    H mH = new H();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        LogUtil.getInstance().init(tvLog);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.getInstance().destory();
    }


    @OnClick({R.id.btn_volatile, R.id.btn_wait_notify, R.id.btn_lock, R.id.btn_lock_interrupt,
            R.id.btn_interrupt, R.id.btn_dead_lock, R.id.btn_synchronized_1, R.id.btn_synchronized_2,
            R.id.btn_synchronized_3, R.id.btn_synchronized_4, R.id.btn_yeild, R.id.btn_join,
            R.id.btn_alternate,R.id.btn_thread_local, R.id.btn_asynctask})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_synchronized_1:
                Instance.testDifObjNormalLock();
                break;
            case R.id.btn_synchronized_2:
                Instance.testEqualObjDifChunk();
                break;
            case R.id.btn_synchronized_3:
                Instance.testStaDifObj();
                break;
            case R.id.btn_synchronized_4:
                Instance.testStaDifObjChunk();
                break;
            case R.id.btn_volatile:
                Instance.startNoVolatile();
                break;
            case R.id.btn_wait_notify:
                Instance.startWaitNotify();
                break;
            case R.id.btn_lock:
                Instance.startLock();
                break;
            case R.id.btn_lock_interrupt:
                Instance.startLockInterruptibly();
                break;
            case R.id.btn_interrupt:
                Instance.startInterruptibly();
                break;
            case R.id.btn_join:
                Instance.join();
                break;
            case R.id.btn_yeild:
                Instance.yeild();
                break;
            case R.id.btn_alternate:
                Instance.alternatePrint();
                break;
            case R.id.btn_asynctask:
                startActivity(new Intent(MainActivity.this, MainAsyncTaskActivity.class));
                break;
            case R.id.btn_thread_local:
                startActivity(new Intent(MainActivity.this, ThreadLocalActivity.class));
                break;
            case R.id.btn_dead_lock:
//                DeadLockUtils.deadLock();
                break;
        }
    }


    private class H extends Handler {

        @Override
        public void handleMessage(Message msg) {

            String value = (String) msg.obj;
            tvLog.setText(value);


        }
    }

}
