package com.h520t.wangyinews.splashScreen;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.h520t.wangyinews.R;

/**
 * @author Administrator
 * @des ${TODO}
 * @Version $Rev$
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class TimeView extends View {
    private TextPaint mTextPaint;
    String content = "跳过";
    float  wordLength ;
    int padding = 10;
    int innerDiameter;//内径
    int outerDiameter;//内径

    private Paint innPaint;
    private Paint outPaint;

    private RectF mRectF;
    private float sweepAngle = 0f;

    private OnTimeClick mOnTimeClick;

    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeView);
        int innerColor = typedArray.getInt(R.styleable.TimeView_innerColor, Color.BLUE);
        int outerColor = typedArray.getInt(R.styleable.TimeView_outerColor, Color.GREEN);

        init(innerColor,outerColor);
        typedArray.recycle();
    }

    private void init(int innerColor,int outerColor) {


        //初始化画字笔//抗锯齿
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(Color.WHITE);

        innPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innPaint.setColor(innerColor);

        outPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outPaint.setColor(outerColor);
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setStrokeWidth(padding);

        wordLength = mTextPaint.measureText(content);
        innerDiameter = (int) wordLength+2*padding;
        outerDiameter = innerDiameter+2*padding;

        mRectF = new RectF(padding/2,padding/2,outerDiameter-padding/2,outerDiameter-padding/2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(outerDiameter, outerDiameter);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle(outerDiameter/2,outerDiameter/2,innerDiameter/2,innPaint);
        //startAngle以3点钟为0度
        canvas.drawArc(mRectF,-90,sweepAngle,false,outPaint);

        int height = canvas.getHeight()/2;
        float descent = mTextPaint.descent();//baseLine下的距离是正的
        float ascent = mTextPaint.ascent();//baseLine上的距离是负的
        Log.i("h520it", "descent: "+descent+"ascent: "+ascent);
        canvas.drawText(content,2*padding,height-(descent+ascent)/2,mTextPaint);


    }

    public void setProgress(int total,int now){
        int space = (360)/total;
        sweepAngle = space*now;

        //更新ui线程
        invalidate();

//        postInvalidate();更新子线程
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setAlpha(0.3f);
                break;

            case MotionEvent.ACTION_UP:
                setAlpha(1.0f);
                if (mOnTimeClick!=null){
                    mOnTimeClick.OnClickTime(this);
                }
                break;
        }
        return true;
    }

    public void setListener(OnTimeClick onTimeClick){
        this.mOnTimeClick = onTimeClick;
    }
}
