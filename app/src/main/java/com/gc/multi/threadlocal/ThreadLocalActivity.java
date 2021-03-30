package com.gc.multi.threadlocal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gc.multi.Instance;
import com.gc.multi.MainActivity;
import com.gc.multi.R;
import com.gc.multi.asynctask.MainAsyncTaskActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ThreadLocalActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_local);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void initView() {

    }

    @OnClick({R.id.tv_thread_local})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_thread_local:
                Toast.makeText(ThreadLocalActivity.this, "xx", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
