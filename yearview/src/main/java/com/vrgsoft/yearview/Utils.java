package com.vrgsoft.yearview;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
    public static int getColumnWidth(Context context) {
        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return 250;
            case DisplayMetrics.DENSITY_MEDIUM:
                return 250;
            case DisplayMetrics.DENSITY_HIGH:
                return 250;
            case DisplayMetrics.DENSITY_XHIGH:
                return 250;
            case DisplayMetrics.DENSITY_XXHIGH:
                return 350;
            case DisplayMetrics.DENSITY_XXXHIGH:
                return 520;
            default:
                return 350;
        }
    }
}
