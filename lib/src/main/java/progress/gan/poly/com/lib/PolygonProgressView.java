package progress.gan.poly.com.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PolygonProgressView extends View {
    //    MyShape myShape;

    int numberOfSides = 5; //default

    private Paint paint;
    private Paint progressPaint;
    private Path path;
    private Path progressPath;
    private int maxProgress = 100;
    private int progressValue = 50;
    private int strokeWidth = 4;
    private int strokeColor = Color.parseColor("#292929");
    private int progressColor = Color.parseColor("#292929");

    public void setProgressValue(int progressValue) {
        this.progressValue = progressValue;
        invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        invalidate();
    }
    public void setNumberOfSides(int pt) {
        numberOfSides = pt;
        invalidate();
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public PolygonProgressView(Context context) {
        super(context);

    }

    public PolygonProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initMyView(context, attrs);

    }

    public PolygonProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMyView(context, attrs);

    }

    public void initMyView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.PolygonProgressView, 0, 0);

        strokeColor = a.getInt(R.styleable.PolygonProgressView_ppv_stroke_color, android.R.color.holo_blue_light);
        progressColor = a.getInt(R.styleable.PolygonProgressView_ppv_fill_color, android.R.color.holo_blue_light);
        maxProgress = (int) a.getFloat(R.styleable.PolygonProgressView_ppv_max, 100f);
        progressValue = (int) a.getFloat(R.styleable.PolygonProgressView_ppv_progress_value, 0);
        numberOfSides = a.getInt(R.styleable.PolygonProgressView_ppv_num_sides, 5);
        strokeWidth = a.getInt(R.styleable.PolygonProgressView_ppv_stroke_width, 2);
        a.recycle();


        paint = new Paint();


        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        progressPaint = new Paint();

        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.FILL);
        path = new Path();
        progressPath = new Path();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(strokeColor);
        progressPaint.setColor(progressColor);
        int w = getWidth();
        int h = getHeight();
        if ((w == 0) || (h == 0)) {
            return;
        }
        float x = (float) w / 2.0f;
        float y = (float) h / 2.0f;
        float radius;
        if (w > h) {
            radius = h / 2;
        } else {
            radius = w / 2;
        }
        setPolygon(x, y, radius, numberOfSides, progressValue);
        canvas.drawPath(progressPath, progressPaint);
        canvas.drawPath(path, paint);
    }




    public void setPolygon(float x, float y, float radius, int numOfPt, int progressValue) {
        radius=radius-strokeWidth;
        if (numOfPt < 3)
            numOfPt = 3;
        double section = 2.0 * Math.PI / numOfPt;
        path.reset();
        progressPath.reset();
        double strokeOffset = section / 2;
        if (numOfPt % 2 != 0)
            strokeOffset = section / 4;
        path.moveTo(
                (float) (x + radius * Math.cos(0 - strokeOffset)),
                (float) (y + radius * Math.sin(0 - strokeOffset)));
        for (int i = 1; i < numOfPt; i++) {
            path.lineTo(
                    (float) (x + radius * Math.cos(section * i - strokeOffset)),
                    (float) (y + radius * Math.sin(section * i - strokeOffset)));
        }
        double progressRadian = (progressValue * 2.0 * Math.PI) / maxProgress;
        double numFilledSection = (progressRadian / section);
        double sectionHeight = radius * Math.cos(section / 2);
        double progressExtraFilled = 0;
        progressPath.moveTo(x, y);
        if (numOfPt % 2 == 0) {
            double sectionOffset = (numOfPt / 4) * section;
            if ((numOfPt - 2) % 4 == 0) {
                progressExtraFilled = progressRadian - (int) numFilledSection * section;
                progressPath.lineTo(
                        (float) (x + radius * Math.cos(0 - strokeOffset - (sectionOffset))),
                        (float) (y + radius * Math.sin(0 - strokeOffset - (sectionOffset))));
                for (int i = 1; i <= numFilledSection; i++) {
                    progressPath.lineTo(
                            (float) (x + radius * Math.cos(section * i - strokeOffset - (sectionOffset))),
                            (float) (y + radius * Math.sin(section * i - strokeOffset - (sectionOffset))));
                }
                radius = (float) (sectionHeight / Math.cos(progressExtraFilled - strokeOffset));
                progressPath.lineTo(
                        (float) (x + radius * Math.cos(progressRadian - strokeOffset - (sectionOffset))),
                        (float) (y + radius * Math.sin(progressRadian - strokeOffset - (sectionOffset))));
            } else {
                numFilledSection = ((progressRadian - section / 2) / section);
                progressExtraFilled = (progressRadian - (int) numFilledSection * section) - section / 2;
                progressPath.lineTo(
                        (float) (x + sectionHeight * Math.cos(0 - (sectionOffset))),
                        (float) (y + sectionHeight * Math.sin(0 - (sectionOffset))));
                progressPath.lineTo(
                        (float) (x + radius * Math.cos(0 + strokeOffset - (sectionOffset))),
                        (float) (y + radius * Math.sin(0 + strokeOffset - (sectionOffset))));
                for (int i = 1; i <= numFilledSection; i++) {
                    progressPath.lineTo(
                            (float) (x + radius * Math.cos(section * i - (sectionOffset) + strokeOffset)),
                            (float) (y + radius * Math.sin(section * i - (sectionOffset) + strokeOffset)));
                }
                if (section / 2 <= progressRadian) {
                    radius = (float) (sectionHeight / Math.cos(progressExtraFilled - section / 2));
                } else {
                    radius = (float) (sectionHeight / Math.cos(progressExtraFilled + section / 2));
                }
                progressPath.lineTo(
                        (float) (x + radius * Math.cos(progressRadian - (sectionOffset))),
                        (float) (y + radius * Math.sin(progressRadian - (sectionOffset))));
            }
        } else {
            double sectionOffset = ((numOfPt - 1) / 4) * section;
            if ((numOfPt - 1) % 4 == 0) {
                progressExtraFilled = progressRadian - (int) numFilledSection * section;
                progressPath.lineTo(
                        (float) (x + radius * Math.cos(0 - strokeOffset - sectionOffset)),
                        (float) (y + radius * Math.sin(0 - strokeOffset - sectionOffset)));
                for (int i = 1; i <= numFilledSection; i++) {
                    progressPath.lineTo(
                            (float) (x + radius * Math.cos(section * i - strokeOffset - sectionOffset)),
                            (float) (y + radius * Math.sin(section * i - strokeOffset - sectionOffset)));
                }
                radius = (float) (sectionHeight / Math.cos(progressExtraFilled - section / 2));
                progressPath.lineTo(
                        (float) (x + radius * Math.cos(progressRadian - strokeOffset - sectionOffset)),
                        (float) (y + radius * Math.sin(progressRadian - strokeOffset - sectionOffset)));
            } else {
                sectionOffset += section / 2;
                numFilledSection = ((progressRadian - section / 2) / section);
                progressExtraFilled = (progressRadian - (int) numFilledSection * section) - section / 2;
                progressPath.lineTo(
                        (float) (x + sectionHeight * Math.cos(-strokeOffset - (sectionOffset))),
                        (float) (y + sectionHeight * Math.sin(-strokeOffset - (sectionOffset))));
                progressPath.lineTo(
                        (float) (x + radius * Math.cos(0 + strokeOffset - (sectionOffset))),
                        (float) (y + radius * Math.sin(0 + strokeOffset - (sectionOffset))));
                for (int i = 1; i <= numFilledSection; i++) {
                    progressPath.lineTo(
                            (float) (x + radius * Math.cos(section * i + strokeOffset - sectionOffset)),
                            (float) (y + radius * Math.sin(section * i + strokeOffset - sectionOffset)));
                }
                if (section / 2 <= progressRadian) {
                    radius = (float) (sectionHeight / Math.cos(progressExtraFilled - section / 2));
                } else {
                    radius = (float) (sectionHeight / Math.cos(progressExtraFilled + section / 2));
                }
                progressPath.lineTo(
                        (float) (x + radius * Math.cos(progressRadian - strokeOffset - sectionOffset)),
                        (float) (y + radius * Math.sin(progressRadian - strokeOffset - sectionOffset)));
            }
            progressPath.lineTo(x, y);

        }
        progressPath.close();
        path.close();
    }

    public double toRadians(int angle) {
        return angle * (Math.PI / 180);
    }

    public int toDegrees(double angle) {
        return (int) (angle * (180 / Math.PI));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if (widthMeasureSpec < heightMeasureSpec)
//            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
//        else
//            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);

    }
}
