package com.book.themechooser;

import android.app.Application;

import android.content.Intent;
import android.content.IntentFilter;
import com.book.themechooser.appwidget.receiver.WallpaperChangedReceiver;
import util.ThemeUtil;

import com.book.themes.ThemeClientApplication;

public class ThemeApplication extends ThemeClientApplication {

    public static final String IMAGE_CACHE_DIR = "thumbs";
    public static Application APPLICATION = null;
    public static ThemeStatus sThemeStatus = null;
    public static boolean sWallpaperChanged = false;
    public static WallpaperChangedReceiver mWallpaperChangedReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        ThemeUtil.initApplication(this);

        mWallpaperChangedReceiver = new WallpaperChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_WALLPAPER_CHANGED);
        registerReceiver(mWallpaperChangedReceiver, filter);
        ThemeUtil.updateDefaultTheme(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ThemeUtil.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(mWallpaperChangedReceiver);
    }
}
