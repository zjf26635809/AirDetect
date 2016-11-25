package detect.air.com.airdetect.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import detect.air.com.airdetect.R;
import detect.air.com.airdetect.tools.DensityUtil;

public class AirClean extends View {

    private int mWidth;
    private int mHeight;

    private Paint mPaint = null;

    private Context mContext;

    private int bigRad = 0;
    private int smallRad = 0;

    private int bigRadDP = 240;
    private int smallRadDP = 180;

    private Bitmap airBitMap, smellBitMap;

    private boolean isActivation = false;

    private String totValue;
    private String airValue;
    private String smellValue;

    private int airLever;
    private int smellLever;

    public AirClean(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        mPaint = new Paint();
        isActivation = false;
        // setDate(0, 1);

    }

    public void setDate(String totValue, String airValue, String smellValue, int airLever, int smellLever) {
        isActivation = true;
        this.totValue = totValue;
        this.airValue = airValue;
        this.smellValue = smellValue;

        this.airLever = airLever;
        this.smellLever = smellLever;

        if (airLever == -1)
            airBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.pic_bad_air);
        else if (airLever == 0)
            airBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.pic_mid_air);
        else if (airLever == 1)
            airBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.pic_good_air);

        if (smellLever == -1)
            smellBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.pic_bad_smell);
        else if (smellLever == 0)
            smellBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.pic_mid_smell);
        else if (smellLever == 1)
            smellBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.pic_good_smell);

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // MeasureSpec.EXACTLY是精确尺寸，当我们将控件的layout_width或layout_height指定为具体数值时如andorid:layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
        // MeasureSpec.AT_MOST是最大尺寸，当控件的layout_width或layout_height指定为WRAP_CONTENT时，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
        // MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式。
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightSize <= 800) {
            bigRadDP = 180;
            smallRadDP = 140;
        }
        if (heightSize > 800 && heightSize <= 1280) {
            bigRadDP = 220;
            smallRadDP = 160;
        }


        bigRad = DensityUtil.dip2px(mContext, bigRadDP);
        smallRad = DensityUtil.dip2px(mContext, smallRadDP);
        mHeight = bigRad;
        mWidth = bigRad;

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        // drawCanvasBg(canvas);
        drawBg(canvas);
        drawValue(canvas);

    }

    int pidding;

    private void drawBg(Canvas canvas) {
        mPaint.reset();

        mPaint.setAntiAlias(true);
        mPaint.setTextSize(3);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);

        pidding = (bigRad - smallRad) / 2;

        if (isActivation) {// 激活

            if (airBitMap != null)
                canvas.drawBitmap(airBitMap, null, new Rect(0, 0, mWidth, mHeight), null);

            if (smellBitMap != null)
                canvas.drawBitmap(smellBitMap, null, new Rect(pidding, pidding, mWidth - pidding, mHeight - pidding),
                        null);
        } else {
            canvas.drawArc(new RectF(0, 0, mWidth, mHeight), 0, 360, false, mPaint);
            canvas.drawArc(new RectF(pidding, pidding, mWidth - pidding, mHeight - pidding), 0, 360, false, mPaint);
        }
    }

    private void drawValue(Canvas canvas) {
        int pxBig = DensityUtil.sp2px(mContext, 46);
        int pxMid = DensityUtil.sp2px(mContext, 15);
        int pxSmall = DensityUtil.sp2px(mContext, 12);

        int margen = DensityUtil.dip2px(mContext, 8);

        // Typeface typeFace
        // =Typeface.createFromAsset(mContext.getAssets(),”font/MONACO.ttf”);
        // mPaint.setTypeface(typeface)

        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(pxBig);

        if (isActivation)
            if (smellLever == -1)
                mPaint.setColor(0xafff0000);
            else if (smellLever == 0)
                mPaint.setColor(0xfffad713);
            else if (smellLever == 1)
                mPaint.setColor(0xff22dd0e);

        canvas.drawText(totValue, mWidth / 2, mHeight / 2 - pxBig / 3, mPaint);

        mPaint.setTextSize(pxSmall);
        canvas.drawText("μg/m²", mWidth / 2 - pxSmall - pxSmall / 2, mHeight / 2 + margen / 2, mPaint);

        mPaint.setTextSize(pxMid);
        if (airValue != null || !airValue.equals("")) {
            pxMid = DensityUtil.sp2px(mContext, 12);
        }
        if (!isActivation)
            canvas.drawText("室内空气  -" + airValue, mWidth / 2, mHeight / 2 + (int) (margen * 3.5), mPaint);
        else
            canvas.drawText("室内空气 " + airValue, mWidth / 2 + margen / 2, mHeight / 2 + (int) (margen * 3.5), mPaint);
        if (isActivation)
            if (airLever == -1)
                mPaint.setColor(0xafff0000);
            else if (airLever == 0)
                mPaint.setColor(0xfffad713);
            else if (airLever == 1)
                mPaint.setColor(0xff22dd0e);
        if (!isActivation)
            canvas.drawText("室内气味  -" + smellValue, mWidth / 2, mHeight / 2 + (int) (margen * 6), mPaint);
        else
            canvas.drawText("室内气味 " + smellValue, mWidth / 2 + margen / 2, mHeight / 2 + (int) (margen * 6), mPaint);
    }

    private static float MIN_ALPHA = 0.1f;
    private static float MAX_ALPHA = 1f;
    private AnimHandler animHandler;
    private int animCount = 0;
    private boolean isAdd = true;
    private float alpha = MIN_ALPHA;

    private float speed = 0.1f;

    public void startAnimation() {
        if (!isActivation)
            return;
        if (animHandler == null) {
            animHandler = new AnimHandler();
            animCount = 0;
            animHandler.sendEmptyMessage(0);
        }
    }

    private class AnimHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (isAdd)
                alpha = alpha + speed;
            else
                alpha = alpha - speed;

            if (alpha <= MIN_ALPHA) {
                alpha = MIN_ALPHA;
                isAdd = true;
            }

            if (alpha >= MAX_ALPHA) {
                alpha = MAX_ALPHA;
                isAdd = false;
                animCount++;
            }

            AirClean.this.setAlpha(alpha);
            if (animCount > 3 && alpha == MAX_ALPHA)
                animHandler = null;
            else
                animHandler.sendEmptyMessageDelayed(0, 100);
        }
    }
}
