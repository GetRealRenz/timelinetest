package com.vrgsoft.yearview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class YearLayout extends LinearLayout {
    private YearView mYearView;
    private ClickView mClickView;
    private ExpanderView mExpanderView;

    public YearLayout(Context context) {
        super(context);
        initViews(context);
    }

    public YearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        addView(inflater.inflate(R.layout.year_view, null));
        mYearView = (YearView) findViewById(R.id.year);
        mClickView = (ClickView) findViewById(R.id.click);
        mExpanderView = (ExpanderView) findViewById(R.id.view);


    }
}
