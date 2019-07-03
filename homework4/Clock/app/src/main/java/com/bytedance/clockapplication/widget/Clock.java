package com.bytedance.clockapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.net.CookieHandler;
import java.util.Calendar;
import java.util.Locale;

public class Clock extends View {

    private final static String TAG = "debugR";

    private static final int FULL_ANGLE = 360;

    private static final int CUSTOM_ALPHA = 140;
    private static final int FULL_ALPHA = 255;

    private static final int DEFAULT_PRIMARY_COLOR = Color.WHITE;
    private static final int DEFAULT_SECONDARY_COLOR = Color.LTGRAY;

    private static final float DEFAULT_DEGREE_STROKE_WIDTH = 0.010f;

    public final static int AM = 0;

    private static final int RIGHT_ANGLE = 90;

    private int mWidth, mCenterX, mCenterY, mRadius;

    /**
     * properties
     */
    private int centerInnerColor;
    private int centerOuterColor;

    private int secondsNeedleColor;
    private int hoursNeedleColor;
    private int minutesNeedleColor;

    private int degreesColor;

    private int hoursValuesColor;

    private int numbersColor;

    private boolean mShowAnalog = true;

    public Clock(Context context) {
        super(context);
        init(context, null);
    }

    public Clock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Clock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        if (widthWithoutPadding > heightWithoutPadding) {
            size = heightWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    private void init(Context context, AttributeSet attrs) {

        this.centerInnerColor = Color.LTGRAY;
        this.centerOuterColor = DEFAULT_PRIMARY_COLOR;

        this.secondsNeedleColor = DEFAULT_SECONDARY_COLOR;
        this.hoursNeedleColor = DEFAULT_PRIMARY_COLOR;
        this.minutesNeedleColor = DEFAULT_PRIMARY_COLOR;

        this.degreesColor = DEFAULT_PRIMARY_COLOR;

        this.hoursValuesColor = DEFAULT_PRIMARY_COLOR;

        numbersColor = Color.WHITE;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getHeight() > getWidth() ? getWidth() : getHeight();

        int halfWidth = mWidth / 2;
        mCenterX = halfWidth;
        mCenterY = halfWidth;
        mRadius = halfWidth;

        if (mShowAnalog) {
            drawDegrees(canvas);
            drawHoursValues(canvas);
            drawNeedles(canvas);
            drawCenter(canvas);
        } else {
            drawNumbers(canvas);
        }

    }

    private void drawDegrees(Canvas canvas) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(mWidth * DEFAULT_DEGREE_STROKE_WIDTH);
        paint.setColor(degreesColor);

        int rPadded = mCenterX - (int) (mWidth * 0.01f);
        int rEnd = mCenterX - (int) (mWidth * 0.05f);

        for (int i = 0; i < FULL_ANGLE; i += 6 /* Step */) {

            if ((i % RIGHT_ANGLE) != 0 && (i % 15) != 0)
                paint.setAlpha(CUSTOM_ALPHA);
            else {
                paint.setAlpha(FULL_ALPHA);
            }

            int startX = (int) (mCenterX + rPadded * Math.cos(Math.toRadians(i)));
            int startY = (int) (mCenterX - rPadded * Math.sin(Math.toRadians(i)));

            int stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(i)));
            int stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(i)));

            canvas.drawLine(startX, startY, stopX, stopY, paint);

        }
    }

    /**
     * @param canvas
     */
    private void drawNumbers(Canvas canvas) {

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(mWidth * 0.2f);
        textPaint.setColor(numbersColor);
        textPaint.setColor(numbersColor);
        textPaint.setAntiAlias(true);

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int amPm = calendar.get(Calendar.AM_PM);

        String time = String.format("%s:%s:%s%s",
                String.format(Locale.getDefault(), "%02d", hour),
                String.format(Locale.getDefault(), "%02d", minute),
                String.format(Locale.getDefault(), "%02d", second),
                amPm == AM ? "AM" : "PM");

        SpannableStringBuilder spannableString = new SpannableStringBuilder(time);
        spannableString.setSpan(new RelativeSizeSpan(0.3f), spannableString.toString().length() - 2, spannableString.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // se superscript percent

        StaticLayout layout = new StaticLayout(spannableString, textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1, 1, true);
        canvas.translate(mCenterX - layout.getWidth() / 2f, mCenterY - layout.getHeight() / 2f);
        layout.draw(canvas);
    }

    /**
     * Draw Hour Text Values, such as 1 2 3 ...
     *
     * @param canvas
     */
    private void drawHoursValues(Canvas canvas) {
        // Default Color:
        // - hoursValuesColor
        int rEnd = mCenterX - (int) (2 * mWidth * 0.05f);
        Paint hoursValuesPaint = new Paint();
        hoursValuesPaint.setColor(hoursValuesColor);
        hoursValuesPaint.setTextSize(60);
        hoursValuesPaint.setStyle(Paint.Style.FILL);
        hoursValuesPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = hoursValuesPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        for (int i = 90; i > -270; i = i - 30) {
            int baseLineX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(i)));
            int baseLineY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(i))
                    - 0.5 * top - 0.5 * bottom);
            int timeOnClock = ((90 - i) / 30) % 12;
            if (timeOnClock == 0) {
                timeOnClock = 12;
            }
            String textToPrint = String.valueOf(timeOnClock);
            canvas.drawText(textToPrint, baseLineX, baseLineY, hoursValuesPaint);
        }
    }

    /**
     * Draw hours, minutes needles
     * Draw progress that indicates hours needle disposition.
     *
     * @param canvas
     */
    private void drawNeedles(final Canvas canvas) {
        // Default Color:
        // - secondsNeedleColor
        // - hoursNeedleColor
        // - minutesNeedleColor
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);


        int rEnd = mCenterX - (int) (mWidth * 0.05f) - 70;

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        //int second = 20;
        double degree = 0;
        int startX, startY, stopX, stopY;
        //秒针
        paint.setColor(secondsNeedleColor);
        paint.setStrokeWidth(mWidth / 2.0f * DEFAULT_DEGREE_STROKE_WIDTH);
        degree = (second % 60) * 6;
        degree = (90 - degree) % 360;
        startX = (int) (mCenterX + 15 * Math.cos(Math.toRadians(degree)));
        startY = (int) (mCenterX - 15 * Math.sin(Math.toRadians(degree)));
        stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(degree)));
        stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(degree)));
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        //分针
        paint.setStrokeWidth(mWidth * DEFAULT_DEGREE_STROKE_WIDTH);
        paint.setColor(minutesNeedleColor);
        rEnd = mCenterX - (int) (mWidth * 0.05f) - 140;
        degree = (minute % 60) * 6 + (second % 60) * 0.1;
        degree = (90 - degree) % 360;
        startX = (int) (mCenterX + 15 * Math.cos(Math.toRadians(degree)));
        startY = (int) (mCenterX - 15 * Math.sin(Math.toRadians(degree)));
        stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(degree)));
        stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(degree)));
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        Log.d(TAG, "drawMinuteNeedles() called with: degree = [" + degree + "]");
        //时针
        paint.setColor(hoursValuesColor);
        paint.setStrokeWidth(mWidth * 1.2f * DEFAULT_DEGREE_STROKE_WIDTH);
        rEnd = mCenterX - (int) (mWidth * 0.05f) - 250;
        degree = (hour % 12) * 30 + (minute % 60) * 0.5;
        degree = (90 - degree) % 360;
        startX = (int) (mCenterX + 15 * Math.cos(Math.toRadians(degree)));
        startY = (int) (mCenterX - 15 * Math.sin(Math.toRadians(degree)));
        stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(degree)));
        stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(degree)));
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        Log.d(TAG, "drawMinuteNeedles() called with: degree = [" + degree + "]");
    }

    /**
     * Draw Center Dot
     *
     * @param canvas
     */
    private void drawCenter(Canvas canvas) {
        // Default Color:
        // - centerInnerColor
        // - centerOuterColor
        Paint centerPaint = new Paint();

        centerPaint.setColor(centerOuterColor);
        centerPaint.setStyle(Paint.Style.STROKE);
        centerPaint.setStrokeWidth(5);
        canvas.drawCircle(mCenterX, mCenterY, 15, centerPaint);

        centerPaint.setColor(centerInnerColor);
        centerPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCenterX, mCenterY, 10, centerPaint);

    }

    public void setShowAnalog(boolean showAnalog) {
        mShowAnalog = showAnalog;
        invalidate();
    }

    public boolean isShowAnalog() {
        return mShowAnalog;
    }

}