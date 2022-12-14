package com.book.themechooser.custom.preview.local;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.book.themechooser.R;
import com.book.themechooser.ThemeApplication;
import com.book.themechooser.ThemeStatus;
import com.book.themechooser.newmechanism.ApplyThemeHelp;
import com.book.themechooser.preview.slide.local.PreviewIconsActivity;
import com.book.themes.CustomType;
import com.book.themes.ThemeManager;
import com.book.themes.provider.ThemeItem;
import com.book.themes.provider.ThemeItem.PreviewsType;
import com.book.themes.provider.Themes;

import java.io.File;

import util.ThemeUtil;

public class FontsPreview extends PreviewIconsActivity {
    @Override
    protected void doApply(ThemeItem bean) {
        ThemeItem appliedTheme = Themes.getAppliedTheme(this);
        if (null == appliedTheme) {
            return;
        }
        if (!ThemeUtil.isDataHasSpace(30)) {
            removeDialog(0);
            Toast.makeText(this, getString(R.string.data_no_size), Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Themes.getThemeUri(this, appliedTheme.getPackageName(), appliedTheme.getThemeId());
        appliedTheme.close();
        Intent i = new Intent(ThemeManager.ACTION_CHANGE_THEME, uri);
        i.putExtra(ThemeManager.EXTRA_EXTENDED_THEME_CHANGE, true);
        i.putExtra(ThemeManager.EXTRA_FONT_URI, bean.getFontUril());
        ThemeUtil.isKillProcess = true;
        if (bean.getPackageName().equals("com.book.theme.bookDefaultTheme")
                && bean.getThemeId().equals("bookDefaultTheme")) {
            i.putExtra(ThemeManager.DEFAULT_FONT, true);
            if (ThemeApplication.sThemeStatus.isApplied("com.book.theme.bookDefaultTheme"
                    , com.book.themechooser.ThemeStatus.THEME_TYPE_FONT)) {
                ThemeUtil.isKillProcess = false;
            } else {
                ThemeUtil.isKillProcess = true;
            }
        }

        //RC48063-jianwu.gao add begin
        //fix bug : reset wallpaper to default after set font
        String wallpaperUriString = ThemeApplication.sThemeStatus.getAppliedThumbnail(ThemeStatus.THEME_TYPE_WALLPAPER);
        if(null != wallpaperUriString && !TextUtils.isEmpty(wallpaperUriString)) {
            File file;
            Uri wallpaperUri = null;
            if (!wallpaperUriString.startsWith("file")) {
                file = new File(wallpaperUriString);
                wallpaperUri = Uri.fromFile(file);
            } else {
                wallpaperUri = Uri.parse(wallpaperUriString);
            }
            i.putExtra("wallpaperUri", wallpaperUri);
        }
        //RC48063-jianwu.gao add end

        i.putExtra(CustomType.EXTRA_NAME, CustomType.FONT_TYPE);
        ApplyThemeHelp.changeTheme(this, i);
        ThemeApplication.sThemeStatus.setAppliedPkgName(bean.getPackageName(), com.book.themechooser.ThemeStatus.THEME_TYPE_FONT);
    }

    protected boolean isThemeApplied() {
        return mThemeStatus.isApplied(mThemeItem.getPackageName(), ThemeStatus.THEME_TYPE_FONT);
    }

    public ImageAdapter initAdapter() {
        return new ImageAdapter(this, mThemeItem, PreviewsType.FONTS);
    }

    @Override
    protected String getDeleteUsingThemeToastMessage() {
        return getString(R.string.delete_using_theme_font);
    }

    @Override
    protected String getDeleteToast() {
        return getString(R.string.fonts_delete_success);
    }
}
