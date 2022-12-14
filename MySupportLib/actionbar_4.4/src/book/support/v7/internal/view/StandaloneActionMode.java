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
package book.support.v7.internal.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import book.support.v7.internal.view.menu.MenuBuilder;
import book.support.v7.internal.view.menu.MenuPopupHelper;
import book.support.v7.internal.view.menu.SubMenuBuilder;
import book.support.v7.internal.widget.ActionBarContextView;
import book.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import java.lang.ref.WeakReference;

/**
 * @hide
 */
public class StandaloneActionMode extends ActionMode implements MenuBuilder.Callback {
    private Context mContext;
    private ActionBarContextView mContextView;
    private ActionMode.Callback mCallback;
    private WeakReference<View> mCustomView;
    private boolean mFinished;
    private boolean mFocusable;

    private MenuBuilder mMenu;

    public StandaloneActionMode(Context context, ActionBarContextView view,
            ActionMode.Callback callback, boolean isFocusable) {
        mContext = context;
        mContextView = view;
        mCallback = callback;

        mMenu = new MenuBuilder(context).setDefaultShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        mMenu.setCallback(this);
        mFocusable = isFocusable;
    }

    @Override
    public void setTitle(CharSequence title) {
        mContextView.setTitle(title);
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        mContextView.setSubtitle(subtitle);
    }

    @Override
    public void setTitle(int resId) {
        setTitle(mContext.getString(resId));
    }

    @Override
    public void setSubtitle(int resId) {
        setSubtitle(mContext.getString(resId));
    }

    @Override
    public void setTitleOptionalHint(boolean titleOptional) {
        super.setTitleOptionalHint(titleOptional);
        mContextView.setTitleOptional(titleOptional);
    }

    @Override
    public boolean isTitleOptional() {
        return mContextView.isTitleOptional();
    }

    @Override
    public void setCustomView(View view) {
        mContextView.setCustomView(view);
        mCustomView = view != null ? new WeakReference<View>(view) : null;
    }

    @Override
    public void invalidate() {
        mCallback.onPrepareActionMode(this, mMenu);
    }

    @Override
    public void finish() {
        if (mFinished) {
            return;
        }
        mFinished = true;

        mContextView.sendAccessibilityEvent(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED);
        mCallback.onDestroyActionMode(this);
    }

    @Override
    public Menu getMenu() {
        return mMenu;
    }

    @Override
    public CharSequence getTitle() {
        return mContextView.getTitle();
    }

    @Override
    public CharSequence getSubtitle() {
        return mContextView.getSubtitle();
    }

    @Override
    public View getCustomView() {
        return mCustomView != null ? mCustomView.get() : null;
    }

    @Override
    public MenuInflater getMenuInflater() {
        return new MenuInflater(mContext);
    }

    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
        return mCallback.onActionItemClicked(this, item);
    }

    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
        if (!subMenu.hasVisibleItems()) {
            return true;
        }

        new MenuPopupHelper(mContext, subMenu).show();
        return true;
    }

    public void onCloseSubMenu(SubMenuBuilder menu) {
    }

    public void onMenuModeChange(MenuBuilder menu) {
        invalidate();
        mContextView.showOverflowMenu();
    }

    public boolean isUiFocusable() {
        return mFocusable;
    }
    
///book ADD BEGIN
	@Override
	public void setRightActionButtonResource(int id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRightActionButtonVisibility(int visibility) {
		
	}
	
	@Override
	public void setRightActionButtonDrawable(Drawable drawable) {
		
	}
///book ADD END
}
