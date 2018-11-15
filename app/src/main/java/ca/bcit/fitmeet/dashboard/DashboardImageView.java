package ca.bcit.fitmeet.dashboard;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class DashboardImageView extends AppCompatImageView {

    public DashboardImageView(Context context) {
        super(context);
    }

    public DashboardImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DashboardImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}
