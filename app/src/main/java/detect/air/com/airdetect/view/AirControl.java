package detect.air.com.airdetect.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import detect.air.com.airdetect.R;
import detect.air.com.airdetect.tools.DensityUtil;

public class AirControl extends View {
    private int mWidth;
    private int mHeight;

    private Paint mPaint = null;

    private Context mContext;

    private Bitmap mBitmap1, mBitmap2, mBitmap3;

    public AirControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

        mContext = context;

        mPaint = new Paint();

        mPaint.setAntiAlias(true);

        setDate(true, 1, true);

    }

    private void setDate(boolean anionON, int light, boolean lockON) {
/**
        if (anionON)
            mBitmap1 = BitmapFactory.decodeResource(mContext.getResources(),
                    R.mipmap.icon_light_30);
        else
            mBitmap1 = BitmapFactory.decodeResource(mContext.getResources(),
                    R.mipmap.icon_light_30);

        if (light == 0)
            mBitmap2 = BitmapFactory.decodeResource(mContext.getResources(),
                    R.mipmap.icon_light_30);
        else if (light == 1)
            mBitmap2 = BitmapFactory.decodeResource(mContext.getResources(),
                    R.mipmap.icon_light_30);
        else if (light == 2)
            mBitmap2 = BitmapFactory.decodeResource(mContext.getResources(),
                    R.mipmap.icon_light_30);
        else if (light == 3)
            mBitmap2 = BitmapFactory.decodeResource(mContext.getResources(),
                    R.mipmap.icon_light_30);

        if (lockON)
            mBitmap3 = BitmapFactory.decodeResource(mContext.getResources(),
                    R.mipmap.icon_light_30);
        else
            mBitmap3 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_light_30);
**/
		if (!anionON)
			mBitmap1 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_anion_off);
		else
			mBitmap1 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_anion_on);

		if (light == 0)
			mBitmap2 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_light_0);
		else if (light == 1)
			mBitmap2 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_light_30);
		else if (light == 2)
			mBitmap2 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_light_60);
		else if (light == 3)
			mBitmap2 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_light_100);

		if (!lockON)
			mBitmap3 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_childlock_off);
		else
			mBitmap3 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_childlock_on);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = DensityUtil.dip2px(mContext, 300);
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            mHeight = mWidth - DensityUtil.dip2px(mContext, 15);
            ;
        } else {
            mHeight = DensityUtil.dip2px(mContext, 300);
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        // drawCanvasBg(canvas);
        drawBg(canvas);
        drawItemImage(canvas);

    }

    int add;
    int surplus;

    private void drawBg(Canvas canvas) {
        mPaint.reset();

        add = mWidth * 8 / 9;
        surplus = mWidth * 2 / 3;

        Shader mRadialGradient = new RadialGradient(mWidth / 2, (mWidth - add) / 2, (mWidth + surplus) / 2,
                new int[]{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, 0xFFF2F2F2},
                null, Shader.TileMode.REPEAT);

        mPaint.setAntiAlias(true);
        // �û�����ͼ���ǿ��ĵ�
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(mRadialGradient);

        // startAngle��sweepAngle��Ϊfloat���ͣ��ֱ��ʾԲ����ʼ �ǶȺ�Բ������
        int startAngle = 30;
        int sweepAngle = 360;

        // ����������Բ��view�� �ǵü�ȥ���ʿ�ȵ�һ�� ��Ϊ�뾶�Ǵ�Բ�ĵ����ʿ�ȵ��м���ģ��������ﻭ���ľ����� new
        // RectF(strokeWidth, strokeWidth, mWidth - strokeWidth, mHeight
        // -strokeWidth)
        canvas.drawArc(new RectF(-surplus, -surplus, mWidth + surplus, mWidth + surplus), startAngle, sweepAngle, false,
                mPaint);
    }

    private void drawItemImage(Canvas canvas) {
        int px = DensityUtil.dip2px(mContext, 12);

        int surplusX = mWidth / 2;
        int surplusY = mWidth / 2 - surplus / 2 - 50;

        mPaint.setColor(0xFF8E8E8E);

        float rAngle = 360 / 15;

        System.out.println("surplusY=" + surplusY);

        double a = getRadWidth(rAngle, mWidth, surplusY);

        if (mBitmap2 != null) {
            int left = mWidth / 2 - mBitmap2.getWidth() / 3;
            int top = mWidth * 7 / 8 - mBitmap2.getHeight() / 3;
            int right = mWidth / 2 + mBitmap2.getWidth() / 3;
            int bottom = mWidth * 7 / 8 + mBitmap2.getHeight() / 3;

            canvas.drawBitmap(mBitmap2, null, new Rect(left, top, right, bottom), null);
        }
        if (mBitmap1 != null) {
            int left = mWidth / 2 - mBitmap1.getWidth() / 3;
            int top = mWidth * 7 / 8 - mBitmap1.getHeight() / 3;
            int right = mWidth / 2 + mBitmap1.getWidth() / 3;
            int bottom = mWidth * 7 / 8 + mBitmap1.getHeight() / 3;

            canvas.rotate(rAngle * 1, surplusX, surplusY);
            canvas.drawBitmap(mBitmap1, null, new Rect(left, top, right, bottom), null);
            canvas.rotate(-rAngle * 1, surplusX, surplusY);
        }
        if (mBitmap3 != null) {
            int left = mWidth / 2 - mBitmap3.getWidth() / 3;
            int top = mWidth * 7 / 8 - mBitmap3.getHeight() / 3;
            int right = mWidth / 2 + mBitmap3.getWidth() / 3;
            int bottom = mWidth * 7 / 8 + mBitmap3.getHeight() / 3;

            canvas.rotate(-rAngle * 1, surplusX, surplusY);
            canvas.drawBitmap(mBitmap3, null, new Rect(left, top, right, bottom), null);
            canvas.rotate(rAngle * 1, surplusX, surplusY);

        }

    }

    private double getRadWidth(float rAngle, float targetY, float surplusY) {

        float lenght = Math.abs(targetY - surplusY);
        return Math.sin(rAngle * Math.PI / 180) * lenght;

    }

    private double getRadHeight(float rAngle, float targetY, float surplusY) {

        float lenght = Math.abs(targetY - surplusY);
        return lenght - Math.cos(rAngle * Math.PI / 180) * lenght;

    }

}
