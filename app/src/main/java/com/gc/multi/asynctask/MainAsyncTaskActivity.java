package com.gc.multi.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.multi.R;

import java.lang.ref.WeakReference;

public class MainAsyncTaskActivity extends Activity {
    TextView mTextViewStart;
    TextView mTextViewCancel;
    TextView mTextViewCancelWithInterrupt;
    ProgressView mProgressView;
    AsyncTask mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void initView() {
        mProgressView = (ProgressView) findViewById(R.id.task_progress);
        mTextViewStart = (TextView) findViewById(R.id.tv_start);
        mTextViewCancelWithInterrupt = (TextView) findViewById(R.id.tv_cancel);

        mTextViewStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAsyncTask = new ProgressBarAsyncTask(MainAsyncTaskActivity.this).execute(10);
            }
        });
        mTextViewCancelWithInterrupt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mAsyncTask.cancel(true);//会产生中断
                mAsyncTask.cancel(false);
            }
        });
    }

    public void doEndAsyncTask(String result) {
        Toast.makeText(this, "任务结束!", Toast.LENGTH_SHORT).show();
    }

    public void doProgressUpdate(int value) {
        mProgressView.setCurrentValue(value);
    }

    static class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String> {
        private final static String TAG = "ProgressBarAsyncTask";
        //允许被gc回收
        private final WeakReference<MainAsyncTaskActivity> weakActivity;

        public ProgressBarAsyncTask(MainAsyncTaskActivity weakActivity) {
            this.weakActivity = new WeakReference<>(weakActivity);
        }

        @Override
        protected String doInBackground(Integer... params) {
            NetOperator netOperator = new NetOperator(1000);
            int value = 10;
            if (params[0] != null)
                value = params[0].intValue();
            for (; value <= 100; value += 10) {
//                if (isCancelled()) {
//                    break;
//                }
                Log.d("testa", value + "");
                publishProgress(value);
                netOperator.operator();
            }
            return value + "";
        }

        boolean isActivityOut() {
            MainAsyncTaskActivity activity = weakActivity.get();
            return activity == null || activity.isFinishing() || (Build.VERSION.SDK_INT >= 17 && activity.isDestroyed());
        }

        //重写 结束任务
        @Override
        protected void onPostExecute(String result) {
            if (isActivityOut()) {
                return;
            }
            weakActivity.get().doEndAsyncTask(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int value = values[0];
            if (isActivityOut()) {
                return;
            }
            weakActivity.get().doProgressUpdate(value);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }


}
