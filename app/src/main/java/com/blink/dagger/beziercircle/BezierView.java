package com.blink.dagger.beziercircle;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lucky on 2017/4/6.
 */
public class BezierView extends View {

    private int mWidth;
    private int mHeight;
    private int mRadius = 60;
    private float offset = 0;
    //固定参数,类似于pi
    private float bezFactor = 0.551915024494f;


    Path circlePath;
    Paint circlePaint;

    //控制三阶贝塞尔的始末点、控制点
    private PointF p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;

    public BezierView(Context context) {
        this(context,null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //// TODO: 2017/4/6  init
        circlePath = new Path();
        circlePaint = new Paint();
        circlePaint.setDither(true);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint.setColor(Color.parseColor("#59c3e2"));

        ValueAnimator animator = ValueAnimator.ofFloat(0,60f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(1500).start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY){
            int result = Math.min(widthSize,heightSize);
            setMeasuredDimension(result,result);
        }else if (widthMode == MeasureSpec.EXACTLY){
            setMeasuredDimension(widthSize,widthSize);
        }else if (heightMode == MeasureSpec.EXACTLY){
            setMeasuredDimension(heightSize,heightSize);
        }else {
            setMeasuredDimension(400,400);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        p0 = new PointF(0+offset, -mRadius+0.2f*offset);//mRadius圆的半径
        p6 = new PointF(0+offset, mRadius-0.2f*offset);

        p1 = new PointF(mRadius * bezFactor+offset, -mRadius+0.2f*offset);//bezFactor即0.5519...
        p5 = new PointF(mRadius * bezFactor+offset, mRadius-0.2f*offset);

        p2 = new PointF(mRadius+2*offset, -mRadius * bezFactor);
        p4 = new PointF(mRadius+2*offset, mRadius * bezFactor);

        p3 = new PointF(mRadius+2*offset, 0);
        p9 = new PointF(-mRadius+offset, 0);

        p11 = new PointF(-mRadius * bezFactor+offset, -mRadius+0.2f*offset);
        p7 = new PointF(-mRadius * bezFactor+offset, mRadius-0.2f*offset);

        p10 = new PointF(-mRadius+offset, -mRadius * bezFactor);
        p8 = new PointF(-mRadius+offset, mRadius * bezFactor);

        canvas.translate(mWidth/2,mHeight/2);
        circlePath.reset();
        circlePath.moveTo(p0.x,p0.y);
        circlePath.cubicTo(p1.x,p1.y,p2.x,p2.y,p3.x,p3.y);
        circlePath.cubicTo(p4.x,p4.y,p5.x,p5.y,p6.x,p6.y);
        circlePath.cubicTo(p7.x,p7.y,p8.x,p8.y,p9.x,p9.y);
        circlePath.cubicTo(p10.x,p10.y,p11.x,p11.y,p0.x,p0.y);
        circlePath.close();
        canvas.drawPath(circlePath,circlePaint);
    }
}
