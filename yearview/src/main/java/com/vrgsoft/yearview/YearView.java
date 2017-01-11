package com.vrgsoft.yearview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class YearView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private List<View> views = new ArrayList<>();
    private int checkedPosition = -1;
    private int mMaxYear;
    private int mMinYear;

    public YearView(Context context) {
        super(context);
        if (!this.isInEditMode()) {
            mContext = context;

            init();
        }
    }

    public YearView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!this.isInEditMode()) {
            mContext = context;
            init();
        }
    }

    public YearView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        if (!this.isInEditMode()) {
            mContext = context;
            init();
        }
    }

    private void init() {
        setOrientation(HORIZONTAL);
        addItem();
    }

    @Override
    public void onClick(View view) {

    }

    private void addItem() {
        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        for (int i = 0; i < mMaxYear - mMinYear; i++) {
            ItemHolder item = new ItemHolder(mContext, this);
            LayoutParams params = new LayoutParams(Utils.getColumnWidth(mContext), ViewGroup.LayoutParams.MATCH_PARENT);
            item.view.setLayoutParams(params);
            item.name.setText(mMinYear + i + "");
            views.add(item.view);
            addView(item.view);

        }

    }

    private static class ItemHolder {
        protected final View view;
        protected final TextView name;
        protected final ImageView container1;
        protected final ImageView container2;
        protected final CardView CardView1;
        protected final CardView CardView2;

        public ItemHolder(Context context, YearView group) {
            //View view = View.inflate(context, R.layout.item_expander_view, group);
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.year_layout, null);
            container1 = (ImageView) view.findViewById(R.id.image_container1);
            container2 = (ImageView) view.findViewById(R.id.image_container2);
            CardView1 = (CardView) view.findViewById(R.id.image_card1);
            CardView2 = (CardView) view.findViewById(R.id.image_card2);
            name = (TextView) view.findViewById(R.id.tx_year);


            this.view = view;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d("MyPos", l + " " + t);
    }

    private boolean isOnScreen(View v, Rect scrollBounds) {
        return v.getGlobalVisibleRect(scrollBounds);
    }

    private int getXInWindow(View v) {
        int[] coords = new int[2];
        v.getLocationInWindow(coords);
        return coords[0];
    }


    public interface ScrolPosition {
        void visiblePostion(int pos);
    }


    public void onDestroy() {

    }


    public void setCheckedPosition(int position) {
        if (checkedPosition != -1) {
            setNormal(checkedPosition);
        }
        this.checkedPosition = position;
        LayoutParams params = new LayoutParams(900, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = views.get(checkedPosition);
        view.setLayoutParams(params);

    }

    public void setNormal(int position) {
        LayoutParams params = new LayoutParams(Utils.getColumnWidth(mContext), ViewGroup.LayoutParams.MATCH_PARENT);
        View view = views.get(position);
        view.setLayoutParams(params);
        checkedPosition = -1;
    }

    public class Builder {
        public Builder() {
        }

        public Builder setMinYear(int minYear) {
            YearView.this.mMinYear = minYear;
            return this;
        }

        public Builder setMaxYear(int maxYear) {
            YearView.this.mMaxYear = maxYear;
            return this;
        }
    }

}