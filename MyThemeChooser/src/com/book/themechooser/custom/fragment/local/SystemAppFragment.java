package com.book.themechooser.custom.fragment.local;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.book.themechooser.ThemeApplication;
import com.book.themechooser.custom.preview.local.SystemAppPreview;
import com.book.themes.provider.ThemeItem;
import com.book.themes.provider.ThemeItem.PreviewsType;

public class SystemAppFragment extends LocalBase {
    @Override
    protected int getType() {
        return SYSTEM_APP;
    }

    @Override
    public void startActivity(View v) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), SystemAppPreview.class);
        Bundle mExtras = new Bundle();
        mExtras.putStringArrayList("themes_uri", uriList);
        intent.putExtra("extras_themes_uri", mExtras);
        intent.setData((Uri) v.getTag());
        getActivity().startActivity(intent);
    }

    @Override
    public boolean isApplied(ThemeItem themeItem) {
        return ThemeApplication.sThemeStatus.isApplied(themeItem.getPackageName()
                , com.book.themechooser.ThemeStatus.THEME_TYPE_STYLE);
    }


    @Override
    public PreviewsType getThumbnailType() {
        return PreviewsType.FRAMEWORK_APPS;
    }
}
