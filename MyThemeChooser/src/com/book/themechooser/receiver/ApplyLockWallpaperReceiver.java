package com.book.themechooser.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.FileUtils;

import com.book.themechooser.ThemeApplication;
import com.book.themechooser.ThemeConstants;
import com.book.themechooser.ThemeStatus;
import com.book.themechooser.newmechanism.ApplyThemeHelp;
import com.book.themes.CustomType;
import com.book.themes.ThemeManager;
import com.book.themes.provider.PackageResources;
import com.book.themes.provider.ThemeItem;
import com.book.themes.provider.Themes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import util.ThemeUtil;

public class ApplyLockWallpaperReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        Bitmap photo = null;
        Uri icon_uri = intent.getData();
        if (!ThemeUtil.supportsLockWallpaper(context)) {
            return;
        }
        ThemeItem mappliedTheme = Themes.getAppliedTheme(context);
        if (null == mappliedTheme) {
            return;
        }
        Uri muri = Themes.getThemeUri(context
                , mappliedTheme.getPackageName(), mappliedTheme.getThemeId());
        mappliedTheme.close();
        Long mName = System.currentTimeMillis();

        //add for check dir is exist
        File dbDir = new File(ThemeConstants.THEME_LOCK_SCREEN_WALLPAPER);
        if (!dbDir.exists()) {
            dbDir.mkdir();
        }

        File f = new File(ThemeConstants.THEME_LOCK_SCREEN_WALLPAPER + "/com.book.pkg." + mName +
                (context.getContentResolver().getType(icon_uri).equals("image/png") ? ".png" : ".jpg"));
        try {
            if (!f.exists()) {
                boolean iscreate = f.createNewFile();
            }
            FileUtils.copyToFile(context.getContentResolver()
                    .openInputStream(icon_uri), f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent mintent = new Intent(ThemeManager.ACTION_CHANGE_THEME, muri);
        mintent.putExtra(ThemeManager.EXTRA_EXTENDED_THEME_CHANGE, true);
        mintent.putExtra(ThemeManager.EXTRA_LOCK_WALLPAPER_URI, PackageResources.convertFilePathUri(icon_uri));
        mintent.putExtra(CustomType.EXTRA_NAME, CustomType.LOCKSCREEN_WALLPAPER_TYPE);
        ApplyThemeHelp.changeTheme(context, mintent);
        ThemeApplication.sThemeStatus.setAppliedPkgName(
                "com.book.pkg." + mName, ThemeStatus.THEME_TYPE_LOCK_WALLPAPER);
    }

}
