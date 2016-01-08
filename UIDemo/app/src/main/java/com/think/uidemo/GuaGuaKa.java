package com.think.uidemo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GuaGuaKa extends View
{
	private Paint mOutterPaint;
	private Path mPath;
	private Canvas mCanvas;
	private Bitmap mBitmap;

	private int mLastX;
	private int mLastY;

	private Bitmap mOutterBitmap;

	// -------------------------------

	// private Bitmap bitmap;

	private String mText;
	private Paint mBackPaint;


	private volatile boolean mComplete = false;

	/**
	 *
	 * @author zhy
	 * 
	 */
	public interface OnGuaGuaKaCompleteListener
	{
		void complete();
	}

	private OnGuaGuaKaCompleteListener mListener;

	public void setOnGuaGuaKaCompleteListener(
			OnGuaGuaKaCompleteListener mListener)
	{
		this.mListener = mListener;
	}

	public GuaGuaKa(Context context)
	{
		this(context, null);
	}

	public GuaGuaKa(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public GuaGuaKa(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();

	}

	public void setText(String mText)
	{
		this.mText = mText;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);

		setupOutPaint();

		// mCanvas.drawColor(Color.parseColor("#c0c0c0"));
		mCanvas.drawRoundRect(new RectF(0, 0, width, height), 30, 30,
				mOutterPaint);
		mCanvas.drawBitmap(mOutterBitmap, null, new Rect(0, 0, width, height),
				null);

	}



	/**
	 */
	private void setupOutPaint()
	{
		mOutterPaint.setColor(Color.parseColor("#c0c0c0"));
		mOutterPaint.setAntiAlias(true);
		mOutterPaint.setDither(true);
		mOutterPaint.setStrokeJoin(Paint.Join.ROUND);
		mOutterPaint.setStrokeCap(Paint.Cap.ROUND);
		mOutterPaint.setStyle(Style.FILL);
		mOutterPaint.setStrokeWidth(20);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int action = event.getAction();

		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (action)
		{
		case MotionEvent.ACTION_DOWN:

			mLastX = x;
			mLastY = y;
			mPath.moveTo(mLastX, mLastY);
			break;
		case MotionEvent.ACTION_MOVE:

			int dx = Math.abs(x - mLastX);
			int dy = Math.abs(y - mLastY);

			if (dx > 3 || dy > 3)
			{
				mPath.lineTo(x, y);
			}

			mLastX = x;
			mLastY = y;

			break;
		case MotionEvent.ACTION_UP:

			break;
		}
			invalidate();
		return true;

	}


	@Override
	protected void onDraw(Canvas canvas)
	{
		// canvas.drawBitmap(bitmap, 0 , 0, null);



		if (!mComplete)
		{
			drawPath();
			canvas.drawBitmap(mBitmap, 0, 0, null);
		}

		if (mComplete)
		{
			if (mListener != null)
			{
				mListener.complete();
			}
		}

	}

	private void drawPath()
	{
		mOutterPaint.setStyle(Style.STROKE);
		mOutterPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
		mCanvas.drawPath(mPath, mOutterPaint);
	}

	/**
	 */
	private void init()
	{
		mOutterPaint = new Paint();
		mPath = new Path();
		// bitmap = BitmapFactory.decodeResource(getResources(),
		// com.imooc.guaguaka.R.drawable.t2);
		mOutterBitmap = BitmapFactory.decodeResource(getResources(),
				R.mipmap.fg_guaguaka);


	}

}
