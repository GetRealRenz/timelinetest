package com.vrgsoft.yearview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpanderView extends LinearLayout implements View.OnClickListener {
    private List<TextView> mItemsText = new ArrayList<>();
    private Context mContext;
    private ItemHolder mLastItem;
    private OnClickPlus onClickPlusListener;
    private int prevMrg = 1896;
    private int relocationOffset;
    private int prevYear = 0;
    private List<View> viewList;
    private List<YearModel> mYearModels;
    private HashMap<YearModel, ItemHolder> mYearViews = new HashMap<>();

    private int yeareMax = -1;

    private int lastYear;
    private int lastVisYear;
    private int firstYear;
    private int coordX;

    public List<YearModel> getCarModel() {
        return mYearModels;
    }

    public void setYearModel(List<YearModel> carModel) {
        this.mYearModels = carModel;
        for (int i = 0; i < carModel.size(); i++) {
            ItemHolder item = new ItemHolder(mContext, this);
            YearModel car = carModel.get(i);
            prevYear = car.start_date();
            int width = ((car.end_date() > 2016 ? 2016 : car.end_date()) - car.start_date())
                    * Utils.getColumnWidth(mContext) + (Utils.getColumnWidth(mContext) - 50);
            int margin = (car.start_date() - prevMrg) * Utils.getColumnWidth(mContext) + 37;
            LayoutParams params = new LayoutParams(width, 40);
            params.setMargins(margin, 15, 0, 0);
            item.view.setLayoutParams(params);
            item.name.setText(car.title());
            item.year.setText(" '" + String.valueOf(car.start_date()));
            item.name.setTextSize(9);
            item.name.setGravity(Gravity.CENTER_VERTICAL);
            mYearViews.put(carModel.get(i), item);
        }

    }

    public ExpanderView(Context context) {
        super(context);
        if (!this.isInEditMode()) {
            mContext = context;
            init();
        }
    }

    public ExpanderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!this.isInEditMode()) {
            mContext = context;
            init();
        }
    }

    public ExpanderView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        if (!this.isInEditMode()) {
            mContext = context;
            init();
        }
    }

    private void init() {
        setOrientation(VERTICAL);
        viewList = new ArrayList<>();
        addItem();
    }

    @Override
    public void onClick(View view) {
        addItem();
    }


    private void addItem() {

        if (mYearModels != null) {

            removeAllViews();
            viewList.clear();
            for (int i = 0; i < mYearModels.size(); i++) {
                if ((mYearModels.get(i).start_date() < firstYear && mYearModels.get(i).end_date() > lastVisYear)
                        || (mYearModels.get(i).end_date() > firstYear && mYearModels.get(i).end_date() < lastVisYear)
                        || (mYearModels.get(i).start_date() < firstYear - 1 && mYearModels.get(i).end_date() > firstYear - 1
                        && mYearModels.get(i).start_date() < lastVisYear) || (mYearModels.get(i).start_date() < lastVisYear
                        && mYearModels.get(i).end_date() > lastVisYear) || (mYearModels.get(i).end_date() <= firstYear
                        && mYearModels.get(i).end_date() >= firstYear) || (mYearModels.get(i).start_date() <= lastVisYear + 1
                        && mYearModels.get(i).end_date() >= lastVisYear) || (mYearModels.get(i).start_date() >= firstYear
                        && mYearModels.get(i).end_date() <= lastVisYear) || (mYearModels.get(i).start_date() >= firstYear
                        && mYearModels.get(i).start_date() < lastVisYear)) {
                    Utils.getColumnWidth(mContext);
                    YearModel car = mYearModels.get(i);
                    prevYear = car.start_date();
                    LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);


                    if (firstYear >= car.start_date()) {

                        int padd = coordX - (car.start_date() - 1896) * Utils.getColumnWidth(mContext);
                        if (padd + 60 > mYearViews.get(mYearModels.get(i)).view.getWidth()) {

                            mYearViews.get(mYearModels.get(i)).name.setPadding(padd - mYearViews.get(mYearModels.get(i)).name.getWidth(), 0, 0, 0);
                        } else {
                            mYearViews.get(mYearModels.get(i)).name.setPadding(padd, 0, 0, 0);
                        }
                    } else {
                        mYearViews.get(mYearModels.get(i)).name.setPadding(10, 0, 0, 0);
                    }


                    params.setMargins(40, 0, 0, 0);
                    mYearViews.get(mYearModels.get(i)).name.setLayoutParams(params);

                    addView(mYearViews.get(mYearModels.get(i)).view, 0);
                    viewList.add(0, mYearViews.get(mYearModels.get(i)).view);
                    lastYear = mYearModels.get(i).start_date();
                }
                if (mYearModels.get(i).start_date() > lastVisYear + 2) {
                    break;
                }
            }

        }
    }

    public List<TextView> getItems() {
        return mItemsText;
    }

    public String[] getStringFromList() {

        int size = mItemsText.size();
        String[] text = new String[size];
        for (int i = 0; i < size; i++) {
            if (mItemsText.get(i).getText().length() > 0) {
                text[i] = mItemsText.get(i).getText().toString();
            }
        }

        return text;

    }


    public void setOnClickPlusListener(OnClickPlus onClickPlusListener) {
        this.onClickPlusListener = onClickPlusListener;
    }

    public void setPadX(int scrollX) {
        coordX = scrollX;
    }

    public  interface OnClickPlus {
        public void onClickPlus(String last);
    }

    private static class ItemHolder {
        protected final View view;
        protected final TextView name;
        protected final TextView year;

        public ItemHolder(Context context, ExpanderView group) {
            //View view = View.inflate(context, R.layout.item_expander_view, group);
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.item_expander_view, null);
            name = (TextView) view.findViewById(R.id.tx_name);
            year = (TextView) view.findViewById(R.id.tx_year);
            this.view = view;
        }
    }

    public int getMaxCount() {
        return yeareMax;
    }

    public void setMaxCount(int maxCount) {
//        this.yeareMax = maxCount;
//        addItem();

    }

    public void setYears(int first, int last) {
        firstYear = first;
        lastVisYear = last;
        addItem();
    }

    private boolean isOnScreen(View v, Rect scrollBounds) {
        return v.getGlobalVisibleRect(scrollBounds);
    }

    private int getXInWindow(View v) {
        int[] coords = new int[2];
        v.getLocationInWindow(coords);
        return coords[0];
    }
}
