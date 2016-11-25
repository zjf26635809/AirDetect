package detect.air.com.airdetect.view;

import java.util.Calendar;
import java.util.Random;


import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import detect.air.com.airdetect.R;
import detect.air.com.airdetect.tools.DensityUtil;

public class AirDetect extends View {

    private int mWidth;
    private int mHeight;

    int mSelector = 0;

    private Paint mPaint = null;

    private Context mContext;

    private Paint pointPaint = null;

    private Paint nullPaint = null;

    private Bitmap bitmap_low_green, bitmap_low_yellow, bitmap_low_red;

    private Bitmap bitmap_solid_green, bitmap_solid_yellow, bitmap_solid_red;

    public AirDetect(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

        mContext = context;

        mPaint = new Paint();
        nullPaint = new Paint();
        pointPaint = new Paint();

        pointPaint.setAntiAlias(true);
        nullPaint.setAntiAlias(true);
        nullPaint.setColor(Color.TRANSPARENT);
        bitmap_low_green = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_low_green);
        bitmap_low_yellow = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_low_yellow);
        bitmap_low_red = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_low_red);

        bitmap_solid_green = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_solid_green);
        bitmap_solid_yellow = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_solid_yellow);
        bitmap_solid_red = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_solid_red);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // MeasureSpec.EXACTLY是精确尺寸，当我们将控件的layout_width或layout_height指定为具体数值时如andorid:layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
        // MeasureSpec.AT_MOST是最大尺寸，当控件的layout_width或layout_height指定为WRAP_CONTENT时，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
        // MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式。

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
            mHeight = mWidth;
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
        drawScaleFont(canvas);
        drawStateFont(canvas);
        drawStateValue(canvas);

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
        // 让画出的图形是空心的
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(mRadialGradient);

        // startAngle和sweepAngle均为float类型，分别表示圆弧起始 角度和圆弧度数
        int startAngle = 30;
        int sweepAngle = 360;

        // 如果想让这个圆在view里 记得减去画笔宽度的一半 因为半径是从圆心到画笔宽度的中间算的，所以这里画弧的矩形是 new
        // RectF(strokeWidth, strokeWidth, mWidth - strokeWidth, mHeight
        // -strokeWidth)
        canvas.drawArc(new RectF(-surplus, -surplus, mWidth + surplus, mHeight + surplus), startAngle, sweepAngle,
                false, mPaint);
    }

    /**
     * 绘制刻度字体！
     */
    private Rect rectFnot;

    private Rect rectDay;

    private Rect rectWeek;

    private Rect rectMonth;

    private Rect rectYear;

    private Paint fontBgPaint = new Paint();

    private int[] selectID = {0, 0, 0, 0};
    Shader mShader;

    private void drawScaleFont(Canvas canvas) {
        int px = DensityUtil.dip2px(mContext, 16);

        for (int i = 0; i < selectID.length; i++) {
            if (i == mSelector)
                selectID[i] = 1;
            else
                selectID[i] = 0;
        }

        int surplusX = mWidth / 2;
        int surplusY = mWidth / 2 - surplus / 2;

        rectFnot = new Rect(0, surplus, mWidth, mWidth);
        int textColor = 0xFF8E8E8E;
        int selectColor = 0xFF22dd0e;

        mPaint.reset();
        mPaint.setColor(textColor);
        // 绘制第一条最上面的刻度
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(px);
        mPaint.setAntiAlias(true);

        fontBgPaint.reset();
        fontBgPaint.setAntiAlias(true);

        int fontbgR = (px * 3) / 4;

        mShader = new RadialGradient(mWidth / 2, mWidth - 25 - px / 3, fontbgR, new int[]{0xFFA6FFA6, Color.WHITE},
                null, Shader.TileMode.REPEAT);
        fontBgPaint.setShader(mShader);

        // 旋转的角度
        float rAngle = 360 / 22;
        // canvas.drawLine(surplusX, surplusY, surplusX, mWidth, mPaint);

        for (int i = 0; i < 4; i++) {
            float myAngle = 0;
            Rect rr;
            if (i == 0)
                myAngle = -rAngle - rAngle / 2 + 1;
            else if (i == 1)
                myAngle = -rAngle / 2;
            else if (i == 2)
                myAngle = rAngle / 2;
            else if (i == 3)
                myAngle = rAngle + rAngle / 2 - 1;

            int offsetWidth = (int) getRadWidth(myAngle, mWidth, surplusY);
            int offsetHeight = (int) getRadHeight(myAngle, mWidth, surplusY);
            rr = new Rect(mWidth / 2 + offsetWidth - px, mWidth - offsetHeight - px * 2, mWidth / 2 + px + offsetWidth,
                    mWidth - offsetHeight);
            if (i == 0)
                rectDay = rr;
            else if (i == 1)
                rectWeek = rr;
            else if (i == 2)
                rectMonth = rr;
            else if (i == 3)
                rectYear = rr;

            canvas.drawRect(rr, nullPaint);
        }

        canvas.rotate(rAngle / 2, surplusX, surplusY);

        if (selectID[1] == 1) {
            mPaint.setColor(selectColor);
            canvas.drawCircle(mWidth / 2, mWidth - 25 - px / 3, fontbgR, fontBgPaint);
        }
        canvas.drawText("周", mWidth / 2 - px / 2, mWidth - 25, mPaint);
        canvas.rotate(-rAngle / 2, surplusX, surplusY);

        mPaint.setColor(textColor);

        canvas.rotate(-rAngle / 2, surplusX, surplusY);
        if (selectID[2] == 1) {
            mPaint.setColor(selectColor);
            canvas.drawCircle(mWidth / 2, mWidth - 25 - px / 3, fontbgR, fontBgPaint);
        }

        canvas.drawText("月", mWidth / 2 - px / 2, mWidth - 25, mPaint);
        canvas.rotate(rAngle / 2, surplusX, surplusY);

        mPaint.setColor(textColor);

        canvas.rotate(rAngle + rAngle / 2, surplusX, surplusY);
        if (selectID[0] == 1) {
            mPaint.setColor(selectColor);
            canvas.drawCircle(mWidth / 2, mWidth - 25 - px / 3, fontbgR, fontBgPaint);
        }
        canvas.drawText("日", mWidth / 2 - px / 2, mWidth - 25, mPaint);
        canvas.rotate(-(rAngle + rAngle / 2), surplusX, surplusY);

        mPaint.setColor(textColor);

        canvas.rotate(-(rAngle + rAngle / 2), surplusX, surplusY);
        if (selectID[3] == 1) {
            mPaint.setColor(selectColor);
            canvas.drawCircle(mWidth / 2, mWidth - 25 - px / 3, fontbgR, fontBgPaint);

        }
        canvas.drawText("年", mWidth / 2 - px / 2, mWidth - 25, mPaint);
        canvas.rotate(rAngle + rAngle / 2, surplusX, surplusY);

        drawScaleFont2(canvas);
        // handler.sendEmptyMessageDelayed(0, 3000);

    }

    /**
     * 绘制刻度字体2(上面的数据)！
     */

    private Rect rectFnot2;

    private int lenght = 0;

    private void drawScaleFont2(Canvas canvas) {
        lenght = 0;
        int px = DensityUtil.dip2px(mContext, 12);

        int surplusX = mWidth / 2;
        int surplusY = mWidth / 2 - surplus / 2 - 90;

        mPaint.setColor(0xFF8E8E8E);
        mPaint.setTextSize(px);

        float rAngle = 0;
        String[] array = null;
        if (selectID[0] == 1) {// 日
            rAngle = 360 / 18;
            array = new String[]{"上午12点", "下午12点", "上午12点"};
        } else if (selectID[1] == 1) {// 周
            rAngle = 360 / 40;
            array = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        } else if (selectID[2] == 1) {//
            rAngle = 360 / 72;
            array = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        } else if (selectID[3] == 1) {// 年
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            rAngle = 360 / 32;
            array = new String[]{year - 4 + "", year - 3 + "", year - 2 + "", year - 1 + "", year + ""};
        }

        int half = array.length / 2;
        int count = 0;

        if (array.length % 2 == 1) {

            double a = getRadWidth(rAngle, mWidth, surplusY);

            for (int i = 0; i <= half; i++) {
                if (count == 0)
                    canvas.drawText(array[half], mWidth / 2 - (array[half].length() - 1) * px / 2, mWidth * 6 / 7,
                            mPaint);
                else {
                    canvas.rotate(rAngle * count, surplusX, surplusY);
                    canvas.drawText(array[half - count], mWidth / 2 - (array[half].length() - 1) * px / 2,
                            mWidth * 6 / 7, mPaint);
                    canvas.rotate(-rAngle * count, surplusX, surplusY);

                    canvas.rotate(-rAngle * count, surplusX, surplusY);
                    canvas.drawText(array[half + count], mWidth / 2 - (array[half].length() - 1) * px / 2,
                            mWidth * 6 / 7, mPaint);
                    canvas.rotate(rAngle * count, surplusX, surplusY);
                }
                count++;
            }
            lenght = (int) (a * half);
            lenght = lenght - half * px / 2;
        } else {
            float firstAngle = rAngle / 2;

            double a = getRadWidth(rAngle, mWidth, surplusY);

            double b = getRadWidth(firstAngle, mWidth, surplusY);

            for (int i = 0; i < half; i++) {
                canvas.rotate(rAngle * count + firstAngle, surplusX, surplusY);
                canvas.drawText(array[half - count - 1], mWidth / 2 - (array[half].length()) * px / 2, mWidth * 6 / 7,
                        mPaint);
                canvas.rotate(-(rAngle * count + firstAngle), surplusX, surplusY);

                canvas.rotate(-(rAngle * count + firstAngle), surplusX, surplusY);
                canvas.drawText(array[half + count], mWidth / 2 - (array[half].length()) * px / 2, mWidth * 6 / 7,
                        mPaint);
                canvas.rotate(rAngle * count + firstAngle, surplusX, surplusY);

                count++;
            }

            lenght = (int) (a * (half - 1));
            lenght = (int) (lenght + b);
            lenght = lenght - (half - 1) * px / 2 + px;
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

    Rect rectStateFnot;

    private void drawStateFont(Canvas canvas) {
        int px = DensityUtil.sp2px(mContext, 13);
        rectStateFnot = new Rect(mWidth / 25, mWidth / 6, px + 10 + mWidth / 25, mWidth / 6 + mWidth / 2);
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setTextSize(px);
        canvas.drawRect(rectStateFnot, mPaint);

        mPaint.setColor(0xffefe162);
        canvas.drawText("中", rectStateFnot.centerX() - px / 2, rectStateFnot.centerY() + px / 2, mPaint);

        mPaint.setColor(0xff22dd0e);
        canvas.drawText("优", rectStateFnot.centerX() - px / 2,
                rectStateFnot.centerY() - rectStateFnot.height() / 2 + px, mPaint);

        mPaint.setColor(0x8ff93e62);
        canvas.drawText("差", rectStateFnot.centerX() - px / 2, rectStateFnot.centerY() + rectStateFnot.height() / 2 - 8,
                mPaint);

    }

    Rect rectStateValue;

    private void drawStateValue(Canvas canvas) {
        int add = DensityUtil.dip2px(mContext, 20);
        mPaint.reset();
        int top = mWidth / 6;
        int left = mWidth / 2 - lenght + add;
        int right = mWidth / 2 + lenght;
        int bottom = mWidth / 6 + mWidth / 2;

        rectStateValue = new Rect(left, top, right, bottom);
        mPaint.setColor(Color.TRANSPARENT);
        canvas.drawRect(rectStateValue, mPaint);

        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            double x = r.nextDouble() * 0.7 + 0.2;

            float offset = (float) (r.nextFloat() * 0.1);

            float[] pos = calculPos((float) x, (float) (i * 0.1), left, bottom, right, top);//计算实心空心坐标

            float[] pos1 = calculPos((float) x + offset, (float) (i * 0.1), left, bottom, right, top);//计算实心圆圈坐标

            drawOneStateValue(canvas, pos, pos1, x, add);
            // canvas.drawCircle(pos[0], pos[1] + add, add, pointPaint);
        }
    }

    int disOffset = 0;

    private void drawOneStateValue(Canvas canvas, float[] pos, float[] pos1, double x, int add) {
        Rect pointRectF = new Rect((int) (pos[0] - add / 2), (int) (pos[1] - add / 2), (int) (pos[0] + add / 2),
                (int) (pos[1] + add / 2));
        RectF pointRectF1 = new RectF(pos1[0] - add / 2, pos1[1] - add / 2, pos1[0] + add / 2, pos1[1] + add / 2);

        if (x >= 0.75) {
            // pointPaint.setColor(0xff22dd0e);// 优：改变颜色
            canvas.drawBitmap(bitmap_low_green, null, pointRectF, null);
            canvas.drawBitmap(bitmap_solid_green, null, pointRectF1, null);

        } else if (x <= 0.25) {
            // pointPaint.setColor(0x8ff93e62);// 差：改变颜色
            // canvas.drawBitmap(bitmap_low_yellow, null, pointRectF,
            // mPaint);
            canvas.drawBitmap(bitmap_low_red, null, pointRectF, null);
            canvas.drawBitmap(bitmap_solid_red, null, pointRectF1, null);

        } else {
            // pointPaint.setColor(0xffefe162);// 中：改变颜色
            // canvas.drawBitmap(bitmap_low_red, null, pointRectF, mPaint);

            canvas.drawBitmap(bitmap_low_yellow, null, pointRectF, null);
            canvas.drawBitmap(bitmap_solid_yellow, null, pointRectF1, null);
        }

    }

    private Calendar calendar;

    private float[] calculPos(float dataPesent, float timePesent, int left, int bottom, int right, int top) {

        int lenY = Math.abs(bottom - top);
        int lenX = Math.abs(right - left);

        int posY = (int) (bottom - lenY * dataPesent);
        int posX = (int) (left + lenX * timePesent);

        return new float[]{posX, posY};

    }

    private void getPesentForDay() {

    }

    private void getPesentForWeek() {

    }

    private double getPesentForMonth(String month, String day) {
        if (calendar == null)
            calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);

        calendar.set(year, Integer.parseInt(month) - 1, Integer.parseInt(day));
        int j = calendar.get(Calendar.DAY_OF_MONTH);

        if (checkLeapYear(year))
            return j / 31622400;
        else
            return j / 31536000;
    }

    // private int getPesentForYear(int targetYear) {
    //
    //
    //
    // }

    public boolean checkLeapYear(int year) {// 【判断是否是闰年
        boolean flag = false;
        if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) {
            flag = true;
        }
        return flag;
    }

    private void change(int selecter) {
        if (selecter > 3)
            selecter = 0;
        if (selecter < 0)
            selecter = 3;

        mSelector = selecter;

        for (int i = 0; i < selectID.length; i++) {
            if (i == selecter)
                selectID[i] = 1;
            else
                selectID[i] = 0;
        }

        handler.sendEmptyMessage(0);
    }

    private int lastX;
    private int lastY;

    public boolean onTouchEvent(MotionEvent event) {
        // 获取到手指处的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        int offX = 0;
        int offY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算移动的距离
                break;

            case MotionEvent.ACTION_UP:
                offX = x - lastX;
                offY = y - lastY;
                if (rectDay.contains(lastX, lastY) && offX < 25 && offY < 25) {
                    mSelector = 0;
                    change(mSelector);
                } else if (rectWeek.contains(lastX, lastY) && offX < 25 && offY < 25) {
                    mSelector = 1;
                    change(mSelector);
                } else if (rectMonth.contains(lastX, lastY) && offX < 25 && offY < 25) {
                    mSelector = 2;
                    change(mSelector);
                } else if (rectYear.contains(lastX, lastY) && offX < 25 && offY < 25) {
                    mSelector = 3;
                    change(mSelector);
                } else {

                    if (offX < -25) {
                        change(--mSelector);
                    } else if (offX > 25) {
                        change(++mSelector);
                    }
                }

        }
        return true;
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            invalidate();
        }

        ;
    };
}
