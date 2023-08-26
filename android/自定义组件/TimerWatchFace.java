package com.ztftrue.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 打卡时钟界面
 * path: src/main/res/values/attrs.xml
 * <declare-styleable name="TimerWatchFace">
 * <attr name="donut_finished_color" format="color" />
 * <attr name="donut_finished_stroke_width" format="dimension" />
 * <attr name="donut_text_size" format="dimension" />
 * <attr name="donut_text_color" format="color" />
 * <attr name="donut_text" format="string" />
 * <attr name="donut_background_color" format="color" />
 * <attr name="donut_description_text_color" format="color" />
 * <attr name="donut_description_text" format="string" />
 * <attr name="donut_description_text_size" format="dimension" />
 * </declare-styleable>
 */

public class TimerWatchFace extends View {
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_TEXT = "text";
    private static final String INSTANCE_STROKE_COLOR = "stroke_color";
    private static final String INSTANCE_STROKE_WIDTH = "stroke_width";
    private static Timer timer;
    private final float default_stroke_width;
    private final int default_finished_color = Color.rgb(66, 145, 241);
    private final int default_text_color = Color.rgb(66, 145, 241);
    private final float default_text_size;
    private final int min_size;
    protected Paint textTimePaint;
    protected Paint textDescriptionPaint;
    private Paint circlePaint;
    private float textSize;
    @ColorInt
    private int textColor;
    private float textDescriptionSize;
    @ColorInt
    private int textDescriptionColor;
    private boolean countTime = true;
    private boolean scaleView = true;
    private int strokeColor;
    private float strokeWidth;
    private String textTime = "";
    private String textDescription = "";
    private float y;
    private float textHeight;

    public TimerWatchFace(Context context) {
        this(context, null);
    }

    public TimerWatchFace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    public TimerWatchFace(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        default_text_size = sp2px(getResources(), 18);
        min_size = (int) dp2px(getResources(), 100);
        default_stroke_width = dp2px(getResources(), 10);

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimerWatchFace,
                defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();
        initPainters();
        viewScale();
        if (countTime) {
            this.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {

                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    timer.cancel();
                    timer = null;
                }
            });
        }
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        viewScale();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void viewScale() {
        if (scaleView && isClickable()) {
            this.setOnTouchListener((view, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float scalingFactor = 0.9f; // scale down to half the size
                    view.setScaleX(scalingFactor);
                    view.setScaleY(scalingFactor);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    float scalingFactor = 1f; // scale down to half the size
                    view.setScaleX(scalingFactor);
                    view.setScaleY(scalingFactor);
                }
                return false;
            });
        } else {
            this.setOnTouchListener(null);
        }
    }

    public boolean isScaleView() {
        return scaleView;
    }

    public void setScaleView(boolean scaleView) {
        this.scaleView = scaleView;
    }

    public boolean isCountTime() {
        return countTime;
    }

    public void setCountTime(boolean countTime) {
        this.countTime = countTime;
    }

    protected void initPainters() {
        textTimePaint = new TextPaint();
        textTimePaint.setColor(textColor);
        textTimePaint.setTextSize(textSize);
        textTimePaint.setAntiAlias(true);

        textDescriptionPaint = new TextPaint();
        textDescriptionPaint.setColor(textDescriptionColor);
        textDescriptionPaint.setTextSize(textDescriptionSize);
        textDescriptionPaint.setAntiAlias(true);

        circlePaint = new Paint();
        circlePaint.setColor(strokeColor);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(strokeWidth);
    }

    protected void initByAttributes(TypedArray attributes) {
        strokeColor = attributes.getColor(R.styleable.TimerWatchFace_donut_finished_color, default_finished_color);

        strokeWidth = attributes.getDimension(R.styleable.TimerWatchFace_donut_finished_stroke_width,
                default_stroke_width);

        if (attributes.getString(R.styleable.TimerWatchFace_donut_text) != null) {
            textTime = attributes.getString(R.styleable.TimerWatchFace_donut_text);
        }
        if (attributes.getString(R.styleable.TimerWatchFace_donut_text) != null) {
            textDescription = attributes.getString(R.styleable.TimerWatchFace_donut_description_text);
        }
        textColor = attributes.getColor(R.styleable.TimerWatchFace_donut_text_color, default_text_color);
        textSize = attributes.getDimension(R.styleable.TimerWatchFace_donut_text_size, default_text_size);
        textDescriptionColor = attributes.getColor(R.styleable.TimerWatchFace_donut_description_text_color,
                default_text_color);
        textDescriptionSize = attributes.getDimension(R.styleable.TimerWatchFace_donut_description_text_size,
                default_text_size);
    }

    public float getTextDescriptionSize() {
        return textDescriptionSize;
    }

    public void setTextDescriptionSize(float textDescriptionSize) {
        this.textDescriptionSize = textDescriptionSize;
        measureTextPosition();
        this.invalidate();
    }

    public int getTextDescriptionColor() {
        return textDescriptionColor;
    }

    public void setTextDescriptionColor(int textDescriptionColor) {
        this.textDescriptionColor = textDescriptionColor;
        measureTextPosition();
        this.invalidate();
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        this.invalidate();
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setDescriptionText(String descriptionText) {
        this.textDescription = descriptionText;
        measureTextPosition();
        invalidate();
    }

    // for data binding
    public void donut_description_text(String descriptionText) {
        this.textDescription = descriptionText;
        measureTextPosition();
        invalidate();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        measureTextPosition();
        this.invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        this.invalidate();
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        this.invalidate();
    }

    public String getTextTime() {
        return textTime;
    }

    public void setTextTime(String textTime) {
        this.textTime = textTime;
        measureTextPosition();
        this.invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = widthMeasureSpec = Math.min(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    private int measure(int measureSpec) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = min_size;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (timer == null && countTime) {
            measureTextPosition();
            timer = new Timer();
            timer.purge();
            timer.schedule(new TimerTask() {
                @SuppressLint("DefaultLocale")
                @Override
                public void run() {
                    Calendar calendar = Calendar.getInstance();
                    setTextTime(String.format("%s:%s:%s", String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)),
                            String.format("%02d", calendar.get(Calendar.MINUTE)),
                            String.format("%02d", calendar.get(Calendar.SECOND))));
                }
            }, 0, 1000);
        }
        int hei = getHeight();
        canvas.drawCircle(hei / 2.0f, hei / 2.0f, (hei - strokeWidth) / 2f, circlePaint);
        if (textTime != null) {
            canvas.drawText(textTime, (hei - textTimePaint.measureText(textTime)) / 2.0f, y, textTimePaint);
        }
        if (textDescription != null) {
            canvas.drawText(textDescription, (hei - textDescriptionPaint
                    // TODO strokeWidth 是间距
                    .measureText(textDescription)) / 2.0f, (y + Math.abs(textHeight)) + strokeWidth,
                    textDescriptionPaint);
        }
    }

    private void measureTextPosition() {
        textHeight = textTimePaint.descent() + textTimePaint.ascent();
        int hei = getHeight();
        if (TextUtils.isEmpty(textDescription)) {
            y = (hei - textHeight) / 2.0f;
        } else {
            // TODO
            y = (hei - textHeight) / 2.0f - Math.abs(textTimePaint.ascent() / 4);
        }
    }

    public void destroy() {
        timer.cancel();
        timer = null;
    }

    @Override
    public void addOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
        super.addOnAttachStateChangeListener(listener);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getTextSize());
        bundle.putInt(INSTANCE_STROKE_COLOR, getStrokeColor());
        bundle.putString(INSTANCE_TEXT, getTextTime());
        bundle.putFloat(INSTANCE_STROKE_WIDTH, getStrokeWidth());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            textColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            textSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            strokeColor = bundle.getInt(INSTANCE_STROKE_COLOR);
            strokeWidth = bundle.getFloat(INSTANCE_STROKE_WIDTH);
            initPainters();
            textTime = bundle.getString(INSTANCE_TEXT);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
}
