package com.bit.flickertestproject.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
    public static int convertDipToPx(Context mContext,float dip) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return (int) (dip * dm.density);

    }

}
