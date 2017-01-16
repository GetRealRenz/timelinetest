package com.vrgsoft.yearview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class YearLayout extends LinearLayout {
    private YearView mYearView;
    private ClickView mClickView;
    private ExpanderView mExpanderView;
    private HorizontalScrollView mScrollView;
    private Context mContext;
    private int pos;

    public YearLayout(Context context) {
        super(context);
        mContext = context;
    }

    public YearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    private void initViews(final Context context, final Builder builder) {
        for (int i = builder.getMinYear(); i < builder.getMaxYear(); i++) {
            builder.getYearsInts().add(i);
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        addView(inflater.inflate(R.layout.year_view, null));

        mExpanderView = (ExpanderView) findViewById(R.id.view);
        mExpanderView.setMinYear(builder.getMinYear());
        mExpanderView.setMaxYear(builder.getMaxYear());
        mExpanderView.setYearModel(builder.getYears());
        mExpanderView.setYears(builder.getMinYear(), builder.getMinYear() + Utils.getXInWindow(builder.getActivity())
                / Utils.getColumnWidth(mContext));

        mClickView = (ClickView) findViewById(R.id.click);
        mClickView.setMinYear(builder.getMinYear());
        mClickView.setYearsCount(builder.getMaxYear() - builder.getMinYear());
        mClickView.setOnRowClick(builder.getOnRowClick());

        mYearView = (YearView) findViewById(R.id.year);
        mYearView.setMaxYear(builder.getMaxYear());
        mYearView.setMinYear(builder.getMinYear());



        mScrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (mScrollView != null) {
                    int scrollX = mScrollView.getScrollX();
                    mExpanderView.setPadX(scrollX);
                    pos = scrollX / Utils.getColumnWidth(context);
                    if (pos > 0 && pos < builder.getYears().size() - Utils.getXInWindow(builder.getActivity()) / Utils.getColumnWidth(context)) {
                        mExpanderView.setYears(builder.getYearsInts().get(pos),
                                builder.getYearsInts().get(pos + Utils.getXInWindow(builder.getActivity()) / (Utils.getColumnWidth(context))));
                    }
                    if (pos == 0) {
                        mExpanderView.setYears(builder.getMinYear(), builder.getMinYear()
                                + Utils.getXInWindow(builder.getActivity()) / (Utils.getColumnWidth(context)));
                    }
                }
            }
        });

    }

    public void setBuilder(Builder builder) {
        initViews(mContext, builder);
    }

    public void setCheckedPosition(int position) {
        mYearView.setCheckedPosition(position);
        mClickView.setCheckedPosition(pos);
    }

    public void setNormal(int yearPosition) {
        mYearView.setNormal(yearPosition);
        mClickView.setNormal(yearPosition);
    }

    public static class Builder {
        private List<YearModel> mYears;
        private Activity mActivity;
        private List<Integer> mYearsInts = new ArrayList<>();
        private int mMinYear;
        private int mMaxYear;
        private OnRowClick mOnRowClick;


        public Builder setYears(List<YearModel> years) {
            mYears = years;
            return this;
        }

        public Builder setMinYear(int minYear) {
            mMinYear = minYear;
            return this;
        }

        public Builder setMaxYear(int maxYear) {
            mMaxYear = maxYear;
            return this;
        }

        public Builder attachToActivity(Activity activity) {
            mActivity = activity;
            return this;
        }

        public Builder setOnRowClick(OnRowClick onRowClick) {
            mOnRowClick = onRowClick;
            return this;
        }

        public Builder create() {
            return this;
        }

        public List<YearModel> getYears() {
            return mYears;
        }

        public Activity getActivity() {
            return mActivity;
        }

        public List<Integer> getYearsInts() {
            return mYearsInts;
        }

        public int getMinYear() {
            return mMinYear;
        }

        public int getMaxYear() {
            return mMaxYear;
        }

        public OnRowClick getOnRowClick() {
            return mOnRowClick;
        }
    }
}
