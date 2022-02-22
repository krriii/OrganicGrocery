package com.example.organicgrocery.utils;

import android.app.Activity;
import android.view.View;

public class UserInterfaceUtils {
    public static void statusBarIcon(Boolean isDark, Activity activity){
        if (isDark)
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        else
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


    }
}
