package com.gc.multi.asynctask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.gc.multi.R;

//进度条View
public class ProgressView extends View {
    private Paint paint = new Paint(); // 绘制背景灰色线条画笔
    private Paint paintText = new Paint(); // 绘制下载进度画笔
    private float offset = 0f; // 下载偏移量
    private float maxvalue = 100f;
    private float currentValue = 0f; // 进度
    private Rect mBound = new Rect(); // 获取百分比数字的长宽
    private String percentValue = "0%"; // 要显示的现在百分比
    private int textSize = 36;
    private float offsetTop = 18f;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attribute) {
        this(context, attribute, 0);

    }

    public ProgressView(Context context, AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getTextWidth();
        setBackgroundColor(Color.parseColor("#fffccc"));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //mWidth = getWidth();
        offsetTop = getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //底色
        paint.setColor(getResources().getColor(R.color.color_gray));
        paint.setStrokeWidth(4);
        canvas.drawLine(0, offsetTop, getWidth(), offsetTop, paint);

        //进度条颜色
        paint.setColor(getResources().getColor(R.color.color_ff6602));
        paint.setStrokeWidth(4);
        canvas.drawLine(0, offsetTop, offset, offsetTop, paint);

        //进度值
        paintText.setColor(getResources().getColor(R.color.color_ff6602));
        paintText.setTextSize(textSize);
        paintText.setAntiAlias(true);
        paintText.getTextBounds(percentValue, 0, percentValue.length(), mBound);
        canvas.drawText(percentValue, offset < mBound.width() ? 0 : offset - mBound.width(), offsetTop + mBound.height() + 3, paintText);
    }

    /**
     * 设置当前进度值
     *
     * @param currentValue
     */
    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
        int value = (int) (this.currentValue / maxvalue * 100);
        if (value < 100) {
            percentValue = value + "%";
        } else {
            percentValue = "100%";
        }
        if (currentValue == 0) {
            offset = mBound.width();
        } else if (currentValue < maxvalue) {
            offset = (getWidth()) * currentValue / maxvalue;
        } else if (currentValue == maxvalue) {
            offset = getWidth();
        }
        invalidate();
    }

    public void getTextWidth() {
//        Paint paint = new Paint();
//        Rect rect = new Rect();
//        paint.setTextSize(textSize);
//        paint.setAntiAlias(true);
//        paint.getTextBounds("100%", 0, "100%".length(), rect);
    }
}
