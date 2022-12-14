/*
 * Copyright (C) 2013 The Android Open Source Project
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
import android.os.Build;
import android.support.v4.util.SimpleArrayMap;
import book.support.v7.internal.view.menu.MenuWrapperFactory;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

/**
 * Wraps a support {@link book.support.v7.view.ActionMode} as a framework
 * {@link android.view.ActionMode}.
 *
 * @hide
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SupportActionModeWrapper extends ActionMode {

    final MenuInflater mInflater;

    final book.support.v7.view.ActionMode mWrappedObject;

    public SupportActionModeWrapper(Context context,
            book.support.v7.view.ActionMode supportActionMode) {
        mWrappedObject = supportActionMode;
        mInflater = new SupportMenuInflater(context);
    }

    @Override
    public Object getTag() {
        return mWrappedObject.getTag();
    }

    @Override
    public void setTag(Object tag) {
        mWrappedObject.setTag(tag);
    }

    @Override
    public void setTitle(CharSequence title) {
        mWrappedObject.setTitle(title);
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        mWrappedObject.setSubtitle(subtitle);
    }

    @Override
    public void invalidate() {
        mWrappedObject.invalidate();
    }

    @Override
    public void finish() {
        mWrappedObject.finish();
    }

    @Override
    public Menu getMenu() {
        return MenuWrapperFactory.createMenuWrapper(mWrappedObject.getMenu());
    }

    @Override
    public CharSequence getTitle() {
        return mWrappedObject.getTitle();
    }

    @Override
    public void setTitle(int resId) {
        mWrappedObject.setTitle(resId);
    }

    @Override
    public CharSequence getSubtitle() {
        return mWrappedObject.getSubtitle();
    }

    @Override
    public void setSubtitle(int resId) {
        mWrappedObject.setSubtitle(resId);
    }

    @Override
    public View getCustomView() {
        return mWrappedObject.getCustomView();
    }

    @Override
    public void setCustomView(View view) {
        mWrappedObject.setCustomView(view);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return mInflater;
    }

    @Override
    public boolean getTitleOptionalHint() {
        return mWrappedObject.getTitleOptionalHint();
    }

    @Override
    public void setTitleOptionalHint(boolean titleOptional) {
        mWrappedObject.setTitleOptionalHint(titleOptional);
    }

    @Override
    public boolean isTitleOptional() {
        return mWrappedObject.isTitleOptional();
    }

    /**
     * @hide
     */
    public static class CallbackWrapper implements book.support.v7.view.ActionMode.Callback {
        final Callback mWrappedCallback;
        final Context mContext;

        final SimpleArrayMap<book.support.v7.view.ActionMode, SupportActionModeWrapper>
                mActionModes;

        public CallbackWrapper(Context context, Callback supportCallback) {
            mContext = context;
            mWrappedCallback = supportCallback;
            mActionModes = new SimpleArrayMap<>();
        }

        @Override
        public boolean onCreateActionMode(book.support.v7.view.ActionMode mode, Menu menu) {
            return mWrappedCallback.onCreateActionMode(getActionModeWrapper(mode),
                    MenuWrapperFactory.createMenuWrapper(menu));
        }

        @Override
        public boolean onPrepareActionMode(book.support.v7.view.ActionMode mode, Menu menu) {
            return mWrappedCallback.onPrepareActionMode(getActionModeWrapper(mode),
                    MenuWrapperFactory.createMenuWrapper(menu));
        }

        @Override
        public boolean onActionItemClicked(book.support.v7.view.ActionMode mode,
                android.view.MenuItem item) {
            return mWrappedCallback.onActionItemClicked(getActionModeWrapper(mode),
                    MenuWrapperFactory.createMenuItemWrapper(item));
        }

        @Override
        public void onDestroyActionMode(book.support.v7.view.ActionMode mode) {
            mWrappedCallback.onDestroyActionMode(getActionModeWrapper(mode));
        }

        private ActionMode getActionModeWrapper(book.support.v7.view.ActionMode mode) {
            // First see if we already have a wrapper for this mode
            SupportActionModeWrapper wrapper = mActionModes.get(mode);
            if (wrapper != null) {
                return wrapper;
            }

            // If we reach here then we haven't seen this mode before. Create a new wrapper and
            // add it to our collection
            wrapper = new SupportActionModeWrapper(mContext, mode);
            mActionModes.put(mode, wrapper);
            return wrapper;
        }
    }
}
