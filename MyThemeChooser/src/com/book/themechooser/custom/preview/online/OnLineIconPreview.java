package com.book.themechooser.custom.preview.online;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.book.themechooser.R;
import com.book.themechooser.ThemeApplication;
import com.book.themechooser.ThemeConstants;
import com.book.themechooser.ThemeStatus;
import com.book.themechooser.newmechanism.ApplyThemeHelp;
import com.book.themechooser.pojos.ThemeBase;
import com.book.themechooser.preview.slide.adapter.OnlinePreviewIcons;
import com.book.themechooser.receiver.ThemeInstallService;
import com.book.themes.CustomType;
import com.book.themes.ThemeManager;
import com.book.themes.provider.ThemeItem;
import com.book.themes.provider.Themes;

import java.io.File;

import util.ThemeUtil;

public class OnLineIconPreview extends OnlinePreviewIcons {
    public OnLineIconPreview() {
        super();
        mThemeType = com.book.themechooser.ThemeStatus.THEME_TYPE_ICONS;
    }

    @Override
    protected ImageAdapter initAdapter() {
        return new ImageAdapter(this, mThemeBase);
    }

    protected void applyTheme() {
        if (Themes.getTheme(this, mThemeBase.getPackageName(), mThemeBase.getThemeId()) == null) {
            if (new File(new StringBuilder().append(ThemeConstants.THEME_LWT)
                    .append("/").append(mThemeBase.getPkg()).toString()).exists()) {
                Intent intent = new Intent(this, ThemeInstallService.class);
                intent.putExtra("THEME_PACKAGE", new StringBuilder().append(
                        ThemeConstants.THEME_LWT).append("/").append(mThemeBase.getPkg()).toString());
                intent.putExtra("APPLY", true);
                this.startService((intent));
                Toast.makeText(this, getString(R.string.init_install_theme), Toast.LENGTH_SHORT).show();
            }
        } else {
            ThemeItem appliedTheme = Themes.getAppliedTheme(this);
            if (null == appliedTheme) {
                return;
            }
            Uri uri = Themes.getThemeUri(this
                    , appliedTheme.getPackageName(), appliedTheme.getThemeId());
            appliedTheme.close();

            Intent i = new Intent(ThemeManager.ACTION_CHANGE_THEME, uri);
            ThemeItem mThemeBean = Themes.getTheme(this
                    , mThemeBase.getPackageName(), mThemeBase.getThemeId());
            i.putExtra(ThemeManager.EXTRA_EXTENDED_THEME_CHANGE, true);
            i.putExtra(ThemeManager.EXTRA_ICONS_URI, mThemeBean.getIconsUri());
            i.putExtra(CustomType.EXTRA_NAME, CustomType.DESKTOP_ICON_TYPE);
            mChangeHelper.beginChange(mThemeBean.getName());
            ThemeUtil.isChangeIcon = true;
            ApplyThemeHelp.changeTheme(this, i);
            ThemeApplication.sThemeStatus.setAppliedPkgName(mThemeBase.getPackageName(), ThemeStatus.THEME_TYPE_ICONS);
            mThemeBean.close();
        }
    }

    @Override
    protected String getLoadPath(ThemeBase themeBase, Context context) {
        return ThemeConstants.THEME_PATH;
    }

    @Override
    protected String getDeleteUsingThemeToastMessage() {
        return getString(R.string.delete_using_theme_icon);
    }

}
