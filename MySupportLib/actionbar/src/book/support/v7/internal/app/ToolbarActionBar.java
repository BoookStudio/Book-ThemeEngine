/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package book.support.v7.internal.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowCompat;
import book.support.v7.app.ActionBar;
import book.support.v7.appcompat.R;
import book.support.v7.internal.view.menu.ListMenuPresenter;
import book.support.v7.internal.view.menu.MenuBuilder;
import book.support.v7.internal.view.menu.MenuPresenter;
import book.support.v7.internal.widget.DecorToolbar;
import book.support.v7.internal.widget.ToolbarWidgetWrapper;
import book.support.v7.view.ActionMode;
import book.support.v7.widget.Toolbar;
import book.support.v7.widget.WindowCallbackWrapper;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

/**
 * @hide
 */
public class ToolbarActionBar extends ActionBar {
    private Toolbar mToolbar;
    private DecorToolbar mDecorToolbar;
    private boolean mToolbarMenuPrepared;
    private WindowCallback mWindowCallback;
    private boolean mMenuCallbackSet;

    private boolean mLastMenuVisibility;
    private ArrayList<OnMenuVisibilityListener> mMenuVisibilityListeners =
            new ArrayList<OnMenuVisibilityListener>();

    private Window mWindow;
    private ListMenuPresenter mListMenuPresenter;

    private final Runnable mMenuInvalidator = new Runnable() {
        @Override
        public void run() {
            populateOptionsMenu();
        }
    };

    private final Toolbar.OnMenuItemClickListener mMenuClicker =
            new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return mWindowCallback.onMenuItemSelected(Window.FEATURE_OPTIONS_PANEL, item);
                }
            };

    public ToolbarActionBar(Toolbar toolbar, CharSequence title, Window window,
            WindowCallback windowCallback) {
        mToolbar = toolbar;
        mDecorToolbar = new ToolbarWidgetWrapper(toolbar, false);
        mWindowCallback = new ToolbarCallbackWrapper(windowCallback);
        mDecorToolbar.setWindowCallback(mWindowCallback);
        toolbar.setOnMenuItemClickListener(mMenuClicker);
        mDecorToolbar.setWindowTitle(title);

        mWindow = window;
    }

    public WindowCallback getWrappedWindowCallback() {
        return mWindowCallback;
    }

    @Override
    public void setCustomView(View view) {
        setCustomView(view, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void setCustomView(View view, LayoutParams layoutParams) {
        view.setLayoutParams(layoutParams);
        mDecorToolbar.setCustomView(view);
    }

    @Override
    public void setCustomView(int resId) {
        final LayoutInflater inflater = LayoutInflater.from(mToolbar.getContext());
        setCustomView(inflater.inflate(resId, mToolbar, false));
    }

    @Override
    public void setIcon(int resId) {
        mDecorToolbar.setIcon(resId);
    }

    @Override
    public void setIcon(Drawable icon) {
        mDecorToolbar.setIcon(icon);
    }

    @Override
    public void setLogo(int resId) {
        mDecorToolbar.setLogo(resId);
    }

    @Override
    public void setLogo(Drawable logo) {
        mDecorToolbar.setLogo(logo);
    }

    @Override
    public void setStackedBackgroundDrawable(Drawable d) {
        // This space for rent (do nothing)
    }

    @Override
    public void setSplitBackgroundDrawable(Drawable d) {
        // This space for rent (do nothing)
    }

    @Override
    public void setHomeButtonEnabled(boolean enabled) {
        // If the nav button on a Toolbar is present, it's enabled. No-op.
    }

    @Override
    public void setElevation(float elevation) {
        ViewCompat.setElevation(mToolbar, elevation);
    }

    @Override
    public float getElevation() {
        return ViewCompat.getElevation(mToolbar);
    }

    @Override
    public Context getThemedContext() {
        return mToolbar.getContext();
    }

    @Override
    public boolean isTitleTruncated() {
        return super.isTitleTruncated();
    }

    @Override
    public void setHomeAsUpIndicator(Drawable indicator) {
        mToolbar.setNavigationIcon(indicator);
    }

    @Override
    public void setHomeAsUpIndicator(int resId) {
        mToolbar.setNavigationIcon(resId);
    }

    @Override
    public void setHomeActionContentDescription(CharSequence description) {
        mDecorToolbar.setNavigationContentDescription(description);
    }

    @Override
    public void setDefaultDisplayHomeAsUpEnabled(boolean enabled) {
        // Do nothing
    }

    @Override
    public void setHomeActionContentDescription(int resId) {
        mDecorToolbar.setNavigationContentDescription(resId);
    }

    @Override
    public void setShowHideAnimationEnabled(boolean enabled) {
        // This space for rent; no-op.
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return mWindowCallback.startActionMode(callback);
    }

    @Override
    public void setListNavigationCallbacks(SpinnerAdapter adapter, OnNavigationListener callback) {
        mDecorToolbar.setDropdownParams(adapter, new NavItemSelectedListener(callback));
    }

    @Override
    public void setSelectedNavigationItem(int position) {
        switch (mDecorToolbar.getNavigationMode()) {
            case NAVIGATION_MODE_LIST:
                mDecorToolbar.setDropdownSelectedPosition(position);
                break;
            default:
                throw new IllegalStateException(
                        "setSelectedNavigationIndex not valid for current navigation mode");
        }
    }

    @Override
    public int getSelectedNavigationIndex() {
        return -1;
    }

    @Override
    public int getNavigationItemCount() {
        return 0;
    }

    @Override
    public void setTitle(CharSequence title) {
        mDecorToolbar.setTitle(title);
    }

    @Override
    public void setTitle(int resId) {
        mDecorToolbar.setTitle(resId != 0 ? mDecorToolbar.getContext().getText(resId) : null);
    }

    @Override
    public void setWindowTitle(CharSequence title) {
        mDecorToolbar.setWindowTitle(title);
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        mDecorToolbar.setSubtitle(subtitle);
    }

    @Override
    public void setSubtitle(int resId) {
        mDecorToolbar.setSubtitle(resId != 0 ? mDecorToolbar.getContext().getText(resId) : null);
    }

    @Override
    public void setDisplayOptions(@DisplayOptions int options) {
        setDisplayOptions(options, 0xffffffff);
    }

    @Override
    public void setDisplayOptions(@DisplayOptions int options, @DisplayOptions int mask) {
        final int currentOptions = mDecorToolbar.getDisplayOptions();
        mDecorToolbar.setDisplayOptions(options & mask | currentOptions & ~mask);
    }

    @Override
    public void setDisplayUseLogoEnabled(boolean useLogo) {
        setDisplayOptions(useLogo ? DISPLAY_USE_LOGO : 0, DISPLAY_USE_LOGO);
    }

    @Override
    public void setDisplayShowHomeEnabled(boolean showHome) {
        setDisplayOptions(showHome ? DISPLAY_SHOW_HOME : 0, DISPLAY_SHOW_HOME);
    }

    @Override
    public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
        setDisplayOptions(showHomeAsUp ? DISPLAY_HOME_AS_UP : 0, DISPLAY_HOME_AS_UP);
    }

    @Override
    public void setDisplayShowTitleEnabled(boolean showTitle) {
        setDisplayOptions(showTitle ? DISPLAY_SHOW_TITLE : 0, DISPLAY_SHOW_TITLE);
    }

    @Override
    public void setDisplayShowCustomEnabled(boolean showCustom) {
        setDisplayOptions(showCustom ? DISPLAY_SHOW_CUSTOM : 0, DISPLAY_SHOW_CUSTOM);
    }

    @Override
    public void setBackgroundDrawable(@Nullable Drawable d) {
        mToolbar.setBackgroundDrawable(d);
    }

    @Override
    public View getCustomView() {
        return mDecorToolbar.getCustomView();
    }

    @Override
    public CharSequence getTitle() {
        return mToolbar.getTitle();
    }

    @Override
    public CharSequence getSubtitle() {
        return mToolbar.getSubtitle();
    }

    @Override
    public int getNavigationMode() {
        return NAVIGATION_MODE_STANDARD;
    }

    @Override
    public void setNavigationMode(@NavigationMode int mode) {
        if (mode == ActionBar.NAVIGATION_MODE_TABS) {
            throw new IllegalArgumentException("Tabs not supported in this configuration");
        }
        mDecorToolbar.setNavigationMode(mode);
    }

    @Override
    public int getDisplayOptions() {
        return mDecorToolbar.getDisplayOptions();
    }

    @Override
    public Tab newTab() {
        throw new UnsupportedOperationException(
                "Tabs are not supported in toolbar action bars");
    }

    @Override
    public void addTab(Tab tab) {
        throw new UnsupportedOperationException(
                "Tabs are not supported in toolbar action bars");
    }

    @Override
    public void addTab(Tab tab, boolean setSelected) {
        throw new UnsupportedOperationException(
                "Tabs are not supported in toolbar action bars");
    }

    @Override
    public void addTab(Tab tab, int position) {
        throw new UnsupportedOperationException(
                "Tabs are not supported in toolbar action bars");
    }

    @Override
    public void addTab(Tab tab, int position, boolean setSelected) {
        throw new UnsupportedOperationException(
                "Tabs are not supported in toolbar action bars");
    }

    @Override
    public void removeTab(Tab tab) {
        throw new UnsupportedOperationException(
                "Tabs are not supported in toolbar action bars");
    }

    @Override
    public void removeTabAt(int position) {
        throw new UnsupportedOperationException(
                "Tabs are not supported in toolbar action bars");
    }

    @Override
    public void removeAllTabs() {
        throw new UnsupportedOperationException(
                "Tabs are not supported in toolbar action bars");
    }

    @Override
    public void selectTab(Tab tab) {
        throw new UnsupportedOperationException(
                "Tabs are not supported in toolbar action bars");
    }

    @Override
    public Tab getSelectedTab() {
        throw new UnsupportedOperationException(
                "Tabs are not supported in toolbar action bars");
    }

    @Override
    public Tab getTabAt(int index) {
        throw new UnsupportedOperationException(
                "Tabs are not supported in toolbar action bars");
    }

    @Override
    public int getTabCount() {
        return 0;
    }

    @Override
    public int getHeight() {
        return mToolbar.getHeight();
    }

    @Override
    public void show() {
        // TODO: Consider a better transition for this.
        // Right now use no automatic transition so that the app can supply one if desired.
        mToolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        // TODO: Consider a better transition for this.
        // Right now use no automatic transition so that the app can supply one if desired.
        mToolbar.setVisibility(View.GONE);
    }
/// book ADD BEGIN    
    @Override
    public void smoothScrollTabIndicator(int position, float positionOffset,
            int positionOffsetPixels) {

    }

    @Override
    public void setScrollState(int state) {

    }
    
    @Override
    public void showSplit() {

    }
    @Override
    public int getSplitHeight() {
        return 0;
    }
    
    @Override
    public void setHasSecondaryTab(int index) {

    }
    
    @Override
    public void hideSplit() {

    }

    @Override
    public void hideSplitNoAnimation() {

    }

    @Override
    public boolean isSplitVisible() {
        return false;
    }

    @Override
    public boolean isActionOptionMenuVisible() {
        return false;
    }
/// book ADD END
	
    @Override
    public boolean isShowing() {
        return mToolbar.getVisibility() == View.VISIBLE;
    }

    @Override
    public boolean openOptionsMenu() {
        return mToolbar.showOverflowMenu();
    }

    @Override
    public boolean invalidateOptionsMenu() {
        mToolbar.removeCallbacks(mMenuInvalidator);
        ViewCompat.postOnAnimation(mToolbar, mMenuInvalidator);
        return true;
    }

    @Override
    public boolean collapseActionView() {
        if (mToolbar.hasExpandedActionView()) {
            mToolbar.collapseActionView();
            return true;
        }
        return false;
    }

    void populateOptionsMenu() {
        final Menu menu = getMenu();
        final MenuBuilder mb = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
        if (mb != null) {
            mb.stopDispatchingItemsChanged();
        }
        try {
            menu.clear();
            if (!mWindowCallback.onCreatePanelMenu(Window.FEATURE_OPTIONS_PANEL, menu) ||
                    !mWindowCallback.onPreparePanel(Window.FEATURE_OPTIONS_PANEL, null, menu)) {
                menu.clear();
            }
        } finally {
            if (mb != null) {
                mb.startDispatchingItemsChanged();
            }
        }
    }

    @Override
    public boolean onMenuKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            openOptionsMenu();
        }
        return true;
    }

    public void addOnMenuVisibilityListener(OnMenuVisibilityListener listener) {
        mMenuVisibilityListeners.add(listener);
    }

    public void removeOnMenuVisibilityListener(OnMenuVisibilityListener listener) {
        mMenuVisibilityListeners.remove(listener);
    }

    public void dispatchMenuVisibilityChanged(boolean isVisible) {
        if (isVisible == mLastMenuVisibility) {
            return;
        }
        mLastMenuVisibility = isVisible;

        final int count = mMenuVisibilityListeners.size();
        for (int i = 0; i < count; i++) {
            mMenuVisibilityListeners.get(i).onMenuVisibilityChanged(isVisible);
        }
    }

    private View getListMenuView(Menu menu) {
        if (menu == null || mListMenuPresenter == null) {
            return null;
        }

        if (mListMenuPresenter.getAdapter().getCount() > 0) {
            return (View) mListMenuPresenter.getMenuView(mToolbar);
        }
        return null;
    }

    private class ToolbarCallbackWrapper extends WindowCallbackWrapper {
        public ToolbarCallbackWrapper(WindowCallback wrapped) {
            super(wrapped);
        }

        @Override
        public boolean onPreparePanel(int featureId, View view, Menu menu) {
            final boolean result = super.onPreparePanel(featureId, view, menu);
            if (result && !mToolbarMenuPrepared) {
                mDecorToolbar.setMenuPrepared();
                mToolbarMenuPrepared = true;
            }
            return result;
        }

        @Override
        public View onCreatePanelView(int featureId) {
            switch (featureId) {
                case Window.FEATURE_OPTIONS_PANEL:
                    if (!mToolbarMenuPrepared) {
                        // If the options menu isn't populated yet, do it now
                        populateOptionsMenu();
                        mToolbar.removeCallbacks(mMenuInvalidator);
                    }

                    if (mToolbarMenuPrepared && mWindowCallback != null) {
                        // If we are prepared, check to see if the callback wants a menu opened
                        final Menu menu = getMenu();

                        if (mWindowCallback.onPreparePanel(featureId, null, menu) &&
                                mWindowCallback.onMenuOpened(featureId, menu)) {
                            return getListMenuView(menu);
                        }
                    }
                    break;
            }
            return super.onCreatePanelView(featureId);
        }
    }

    private Menu getMenu() {
        if (!mMenuCallbackSet) {
            mToolbar.setMenuCallbacks(new ActionMenuPresenterCallback(), new MenuBuilderCallback());
            mMenuCallbackSet = true;
        }
        return mToolbar.getMenu();
    }

    public void setListMenuPresenter(ListMenuPresenter listMenuPresenter) {
        final Menu menu = getMenu();

        if (menu instanceof MenuBuilder) {
            MenuBuilder mb = (MenuBuilder) menu;

            if (mListMenuPresenter != null) {
                // We currently have a list menu presenter, remove it as our menu's presenter
                mListMenuPresenter.setCallback(null);
                mb.removeMenuPresenter(mListMenuPresenter);
            }

            mListMenuPresenter = listMenuPresenter;

            if (listMenuPresenter != null) {
                listMenuPresenter.setCallback(new PanelMenuPresenterCallback());
                mb.addMenuPresenter(listMenuPresenter);
            }
        }
    }

    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        private boolean mClosingActionMenu;

        @Override
        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            if (mWindowCallback != null) {
                mWindowCallback.onMenuOpened(WindowCompat.FEATURE_ACTION_BAR, subMenu);
                return true;
            }
            return false;
        }

        @Override
        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            if (mClosingActionMenu) {
                return;
            }

            mClosingActionMenu = true;
            mToolbar.dismissPopupMenus();
            if (mWindowCallback != null) {
                mWindowCallback.onPanelClosed(WindowCompat.FEATURE_ACTION_BAR, menu);
            }
            mClosingActionMenu = false;
        }
    }

    private final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        @Override
        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            if (mWindowCallback != null) {
                mWindowCallback.onPanelClosed(Window.FEATURE_OPTIONS_PANEL, menu);
            }

            // Close the options panel
            mWindow.closePanel(Window.FEATURE_OPTIONS_PANEL);
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            if (subMenu == null && mWindowCallback != null) {
                mWindowCallback.onMenuOpened(Window.FEATURE_OPTIONS_PANEL, subMenu);
            }
            return true;
        }
    }

    private final class MenuBuilderCallback implements MenuBuilder.Callback {

        @Override
        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            return false;
        }

        @Override
        public void onMenuModeChange(MenuBuilder menu) {
            if (mWindowCallback != null) {
                if (mToolbar.isOverflowMenuShowing()) {
                    mWindowCallback.onPanelClosed(WindowCompat.FEATURE_ACTION_BAR, menu);
                } else if (mWindowCallback.onPreparePanel(Window.FEATURE_OPTIONS_PANEL,
                        null, menu)) {
                    mWindowCallback.onMenuOpened(WindowCompat.FEATURE_ACTION_BAR, menu);
                }
            }
        }
    }
}
