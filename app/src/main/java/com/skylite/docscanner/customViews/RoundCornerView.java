package com.skylite.docscanner.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.skylite.docscanner.R;

public class RoundCornerView extends CustomView {

    private final RectF rectF = new RectF();
    //region border
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF borderRectF = new RectF();
    private final Path borderPath = new Path();
    private float topLeftRadius = 0f;
    private float topRightRadius = 0f;
    private float bottomRightRadius = 0f;
    private float bottomLeftRadius = 0f;
    @ColorInt
    private int borderColor = Color.WHITE;

    private float borderWidthPx = 0f;
    //endregion

    public RoundCornerView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public RoundCornerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundCornerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundCorner);
            topLeftRadius = attributes.getDimensionPixelSize(R.styleable.RoundCorner_topLeftCornerRadius, (int) topLeftRadius);
            topRightRadius = attributes.getDimensionPixelSize(R.styleable.RoundCorner_topRightCornerRadius, (int) topRightRadius);
            bottomLeftRadius = attributes.getDimensionPixelSize(R.styleable.RoundCorner_bottomLeftCornerRadius, (int) bottomLeftRadius);
            bottomRightRadius = attributes.getDimensionPixelSize(R.styleable.RoundCorner_bottomRightCornerRadius, (int) bottomRightRadius);
            attributes.recycle();
        }
        borderPaint.setStyle(Paint.Style.STROKE);
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                rectF.set(0, 0, width, height);
                return generatePath(rectF,
                        limitSize(topLeftRadius, width, height),
                        limitSize(topRightRadius, width, height),
                        limitSize(bottomRightRadius, width, height),
                        limitSize(bottomLeftRadius, width, height)
                );
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }

    protected float limitSize(float from, final float width, final float height) {
        return Math.min(from, Math.min(width, height));
    }


    private Path generatePath(RectF rect, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        return generatePath(false, rect, topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius);
    }

    private Path generatePath(boolean useBezier, RectF rect, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        final Path path = new Path();

        final float left = rect.left;
        final float top = rect.top;
        final float bottom = rect.bottom;
        final float right = rect.right;

        final float maxSize = Math.min(rect.width() / 2f, rect.height() / 2f);

        float topLeftRadiusAbs = Math.abs(topLeftRadius);
        float topRightRadiusAbs = Math.abs(topRightRadius);
        float bottomLeftRadiusAbs = Math.abs(bottomLeftRadius);
        float bottomRightRadiusAbs = Math.abs(bottomRightRadius);

        if (topLeftRadiusAbs > maxSize) {
            topLeftRadiusAbs = maxSize;
        }
        if (topRightRadiusAbs > maxSize) {
            topRightRadiusAbs = maxSize;
        }
        if (bottomLeftRadiusAbs > maxSize) {
            bottomLeftRadiusAbs = maxSize;
        }
        if (bottomRightRadiusAbs > maxSize) {
            bottomRightRadiusAbs = maxSize;
        }

        path.moveTo(left + topLeftRadiusAbs, top);
        path.lineTo(right - topRightRadiusAbs, top);

        //float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean forceMoveTo
        if (useBezier) {
            path.quadTo(right, top, right, top + topRightRadiusAbs);
        } else {
            final float arc = topRightRadius > 0 ? 90 : -270;
            path.arcTo(new RectF(right - topRightRadiusAbs * 2f, top, right, top + topRightRadiusAbs * 2f), -90, arc);
        }
        path.lineTo(right, bottom - bottomRightRadiusAbs);
        if (useBezier) {
            path.quadTo(right, bottom, right - bottomRightRadiusAbs, bottom);
        } else {
            final float arc = bottomRightRadiusAbs > 0 ? 90 : -270;
            path.arcTo(new RectF(right - bottomRightRadiusAbs * 2f, bottom - bottomRightRadiusAbs * 2f, right, bottom), 0, arc);
        }
        path.lineTo(left + bottomLeftRadiusAbs, bottom);
        if (useBezier) {
            path.quadTo(left, bottom, left, bottom - bottomLeftRadiusAbs);
        } else {
            final float arc = bottomLeftRadiusAbs > 0 ? 90 : -270;
            path.arcTo(new RectF(left, bottom - bottomLeftRadiusAbs * 2f, left + bottomLeftRadiusAbs * 2f, bottom), 90, arc);
        }
        path.lineTo(left, top + topLeftRadiusAbs);
        if (useBezier) {
            path.quadTo(left, top, left + topLeftRadiusAbs, top);
        } else {
            final float arc = topLeftRadiusAbs > 0 ? 90 : -270;
            path.arcTo(new RectF(left, top, left + topLeftRadiusAbs * 2f, top + topLeftRadiusAbs * 2f), 180, arc);
        }
        path.close();

        return path;
    }

    public float getTopLeftRadius() {
        return topLeftRadius;
    }

    public void setTopLeftRadius(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
        requiresShapeUpdate();
    }

    public float getTopLeftRadiusDp() {
        return pxToDp(getTopLeftRadius());
    }

    public void setTopLeftRadiusDp(float topLeftRadius) {
        setTopLeftRadius(dpToPx(topLeftRadius));
    }

    public float getTopRightRadius() {
        return topRightRadius;
    }

    public void setTopRightRadius(float topRightRadius) {
        this.topRightRadius = topRightRadius;
        requiresShapeUpdate();
    }

    public float getTopRightRadiusDp() {
        return pxToDp(getTopRightRadius());
    }

    public void setTopRightRadiusDp(float topRightRadius) {
        setTopRightRadius(dpToPx(topRightRadius));
    }

    public float getBottomRightRadius() {
        return bottomRightRadius;
    }

    public void setBottomRightRadius(float bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
        requiresShapeUpdate();
    }

    public float getBottomRightRadiusDp() {
        return pxToDp(getBottomRightRadius());
    }

    public void setBottomRightRadiusDp(float bottomRightRadius) {
        setBottomRightRadius(dpToPx(bottomRightRadius));
    }

    public float getBottomLeftRadius() {
        return bottomLeftRadius;
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
        requiresShapeUpdate();
    }

    public float getBottomLeftRadiusDp() {
        return pxToDp(getBottomLeftRadius());
    }

    public void setBottomLeftRadiusDp(float bottomLeftRadius) {
        setBottomLeftRadius(dpToPx(bottomLeftRadius));
    }

    public float getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        requiresShapeUpdate();
    }


}