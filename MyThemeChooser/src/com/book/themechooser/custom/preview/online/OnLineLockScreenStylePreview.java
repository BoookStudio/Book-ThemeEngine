package com.book.themechooser.custom.preview.online;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.book.themechooser.R;
import com.book.themechooser.ThemeApplication;
import com.book.themechooser.ThemeConstants;
import com.book.themechooser.newmechanism.ApplyThemeHelp;
import com.book.themechooser.pojos.ThemeBase;
import com.book.themechooser.preview.slide.adapter.OnlinePreviewIcons;
import com.book.themechooser.receiver.ThemeInstallService;
import com.book.themes.CustomType;
import com.book.themes.ThemeManager;
import com.book.themes.provider.ThemeItem;
import com.book.themes.provider.Themes;

import java.io.File;

public class OnLineLockScreenStylePreview extends OnlinePreviewIcons {
    public OnLineLockScreenStylePreview() {
        super();
        mThemeType = com.book.themechooser.ThemeStatus.THEME_TYPE_LOCK_SCREEN;
    }

    protected ImageAdapter initAdapter() {
        return new ImageAdapter(this, mThemeBase);
    }

    protected void applyTheme() {
        if (Themes.getTheme(this, mThemeBase.getPackageName(), mThemeBase.getThemeId()) == null) {
            if (new File(new StringBuilder().append(ThemeConstants.THEME_LWT).append("/").append(mThemeBase.getPkg()).toString()).exists()) {
                Intent intent = new Intent(this, ThemeInstallService.class);
                intent.putExtra("THEME_PACKAGE", new StringBuilder().append(ThemeConstants.THEME_LWT).append("/").append(mThemeBase.getPkg()).toString());
                intent.putExtra("APPLY", true);
                this.startService((intent));
                Toast.makeText(this, getString(R.string.init_install_theme), Toast.LENGTH_SHORT).show();
            }
        } else {
            ThemeItem appliedTheme = Themes.getAppliedTheme(this);
            if (null == appliedTheme) {
                return;
            }
            Uri uri = Themes.getThemeUri(this, appliedTheme.getPackageName(), appliedTheme.getThemeId());
            appliedTheme.close();
            Intent i = new Intent(ThemeManager.ACTION_CHANGE_THEME, uri);
            ThemeItem mThemeBean = Themes.getTheme(this, mThemeBase.getPackageName(), mThemeBase.getThemeId());
            i.putExtra(ThemeManager.EXTRA_EXTENDED_THEME_CHANGE, true);
            i.putExtra(ThemeManager.EXTRA_LOCKSCREEN_URI, mThemeBean.getLockscreenUri());
            if (mThemeBean.getLockWallpaperUri(this) != null) {
                i.putExtra(ThemeManager.EXTRA_LOCK_WALLPAPER_URI, mThemeBean.getLockWallpaperUri(this));
            }
//                i.putExtra(CustomType.EXTRA_NAME, ThemeConstants.LOCKSCREEN_STYLE_TYPE);
            i.putExtra(CustomType.EXTRA_NAME, CustomType.THEME_DETAIL);
            mChangeHelper.beginChange(mThemeBase.getName());
            ApplyThemeHelp.changeTheme(this, i);

            ThemeApplication.sThemeStatus.setAppliedPkgName(mThemeBase.getPackageName(),
                    com.book.themechooser.ThemeStatus.THEME_TYPE_LOCK_SCREEN);
            ThemeApplication.sThemeStatus.setAppliedPkgName(""
                    , com.book.themechooser.ThemeStatus.THEME_TYPE_LOCK_WALLPAPER);
        }
    }

    @Override
    protected String getLoadPath(ThemeBase themeBase, Context context) {
        return ThemeConstants.THEME_PATH;
    }


    @Override
    protected String getDeleteUsingThemeToastMessage() {
        return getString(R.string.delete_using_theme_lock_screen_style);
    }
}
