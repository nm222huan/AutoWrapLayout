package com.example.fish.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

@SuppressLint("AppCompatCustomView")
public class TagTextView extends TextView {

    /**
     * 画笔对象的引用
     */
    private float textSize;
    private float paddingLeft;
    private float marginTop;
    private float marginLeft;
    private float paddingBottom;
    private float textShowWidth;
    private float drawRectHeight;
    
    private Handler mHandler;

    List<TagInterestBean> mBeanList;
    
    public TagTextView(Context context) {
	this(context, null);
    }

    public TagTextView(Context context, AttributeSet attrs) {
	super(context, attrs);
	textSize = ScreenUtils.dip2px(28);
	paddingLeft = ScreenUtils.dip2px(12);
	marginTop = ScreenUtils.dip2px(6);
	marginLeft = ScreenUtils.dip2px(6);
	paddingBottom = ScreenUtils.dip2px(6);
	drawRectHeight = ScreenUtils.dip2px(60);
	textShowWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() - ScreenUtils.dip2px(46);
	mBeanList = new ArrayList<TagInterestBean>();
	initHandler();
    }

    private void initHandler() {
	mHandler = new Handler(new Callback() {
	    
	    @Override
	    public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case 0: {
		    invalidate();
		    break;
		}

		default:
		    break;
		}
		return false;
	    }
	});
    }
    
    private boolean mIsDraw = false;

    @Override
    protected void onDraw(Canvas canvas) {
	if (mBeanList == null || mBeanList.size() <= 0) {
	    return;
	}
//	super.onDraw(canvas);
	int lineCount = 0;
	// 已绘的宽度
	float drawedWidth = marginLeft;
	float charWidth;
	float drawedHeight = marginTop;
	for (int i = 0; i < mBeanList.size(); i++) {
	    Paint textPaint = getTextPaint(mBeanList.get(i).getType());
	    Paint backgroundPaint = getBackGroundPaint(mBeanList.get(i).getType());
	    charWidth = textPaint.measureText(mBeanList.get(i).getName());
	    if (textShowWidth - drawedWidth < charWidth + paddingLeft * 3) {
		lineCount++;
		drawedWidth = marginLeft;
		drawedHeight += drawRectHeight + marginTop;
	    }
	    RectF r2 = new RectF();
	    r2.left = drawedWidth;
	    r2.right = r2.left + charWidth + paddingLeft * 2;
	    r2.top = drawedHeight;
	    r2.bottom = r2.top + drawRectHeight;
	    FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
	    int baseline = (int)(drawRectHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;  
	    canvas.drawRoundRect(r2, ScreenUtils.dip2px(8), ScreenUtils.dip2px(8), backgroundPaint);
	    canvas.drawText(mBeanList.get(i).getName(), paddingLeft + r2.left, baseline + r2.top, textPaint);

	    drawedWidth += charWidth + paddingLeft * 2 + marginLeft;
	}
	drawedHeight += drawRectHeight + marginTop;
	if (!mIsDraw) {
	    setHeight((int)Math.ceil(drawedHeight));
	}
	mIsDraw = true;
    }
    
    public void setText(List<TagInterestBean> beanList) {
	if (beanList != null && beanList.size() > 0) {
	    mBeanList = beanList;
//	    mBeanList.addAll(beanList);
	    mIsDraw = false;
	    invalidate();
	}
    }

    public Paint getTextPaint(int type) {
	Paint paint = new Paint();
	paint.setTextSize(textSize);
//	paint.setStrokeWidth(6);
	paint.setTextScaleX(1.0f);
//	Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
//	paint.setTypeface(Typeface.MONOSPACE);
	switch (type) {
	case TagInterestBean.TAG_TYPE_BOOK: {
	    paint.setColor(Color.parseColor("#a08324"));
	    break;
	}
	case TagInterestBean.TAG_TYPE_FLAG: {
	    paint.setColor(Color.parseColor("#365062"));
	    break;
	}
	case TagInterestBean.TAG_TYPE_FOOD: {
	    paint.setColor(Color.parseColor("#b3311d"));
	    break;
	}
	case TagInterestBean.TAG_TYPE_MOVIE: {
	    paint.setColor(Color.parseColor("#4d4b75"));
	    break;
	}
	case TagInterestBean.TAG_TYPE_MUSIC: {
	    paint.setColor(Color.parseColor("#314c9c"));
	    break;
	}
	case TagInterestBean.TAG_TYPE_MY_TOUR: {
	    paint.setColor(Color.parseColor("#3f728d"));
	    break;
	}
	case TagInterestBean.TAG_TYPE_SPORT: {
	    paint.setColor(Color.parseColor("#135e2b"));
	    break;
	}

	default:
	    break;
	}
	return paint;
    }

    public Paint getBackGroundPaint(int type) {
	Paint paint = new Paint();
	paint.setTextSize(textSize);
	switch (type) {
	case TagInterestBean.TAG_TYPE_BOOK: {
	    paint.setColor(Color.parseColor("#fceebd"));
	    break;
	}
	case TagInterestBean.TAG_TYPE_FLAG: {
	    paint.setColor(Color.parseColor("#b4b6b5"));
	    break;
	}
	case TagInterestBean.TAG_TYPE_FOOD: {
	    paint.setColor(Color.parseColor("#f5b0a3"));
	    break;
	}
	case TagInterestBean.TAG_TYPE_MOVIE: {
	    paint.setColor(Color.parseColor("#c9c9e1"));
	    break;
	}
	case TagInterestBean.TAG_TYPE_MUSIC: {
	    paint.setColor(Color.parseColor("#b5c0dc"));
	    break;
	}
	case TagInterestBean.TAG_TYPE_MY_TOUR: {
	    paint.setColor(Color.parseColor("#d3dee2"));
	    break;
	}
	case TagInterestBean.TAG_TYPE_SPORT: {
	    paint.setColor(Color.parseColor("#cdead6"));
	    break;
	}

	default:
	    break;
	}
	return paint;
    }

}
