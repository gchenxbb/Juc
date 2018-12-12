package com.gc.multi;

import android.text.TextUtils;
import android.view.TextureView;
import android.widget.TextView;

public class LogUtil {

    private static StringBuilder stringBuilder_s;
    private static TextView mLogView_s;

    private StringBuilder stringBuilder;
    private TextView mLogView;

    public LogUtil() {
        stringBuilder = new StringBuilder();
    }

    private static class LogUtilHolder {
        public static final LogUtil sInstance = new LogUtil();
    }

    public static LogUtil getInstance() {
        return LogUtilHolder.sInstance;
    }

    public void init(TextView mLogView) {
        this.mLogView = mLogView;
    }

    public void destory() {
        mLogView_s = null;
        mLogView = null;
    }

    //只有主线程可以
    public void log(String s) {
        if (!TextUtils.isEmpty(s)) {
            stringBuilder.append(s);
            stringBuilder.append("\n");
            mLogView.setText(stringBuilder);
        }
    }


    public static void logStatic(TextView mLogView, String s) {

        if (mLogView_s == null)
            mLogView_s = mLogView;
        if (stringBuilder_s == null)
            stringBuilder_s = new StringBuilder();
        if (!TextUtils.isEmpty(s) && mLogView_s != null) {
            stringBuilder_s.append(s);
            stringBuilder_s.append("\n");
            mLogView_s.setText(stringBuilder_s);
        }
    }
}
