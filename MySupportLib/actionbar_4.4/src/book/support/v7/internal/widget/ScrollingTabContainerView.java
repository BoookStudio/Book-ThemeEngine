/*
 * Copyright (C) 2011 The Android Open Source Project
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
package book.support.v7.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import book.support.v7.app.ActionBar;
import book.support.v7.appcompat.R;
import book.support.v7.internal.view.ActionBarPolicy;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

/**
 * This widget implements the dynamic action bar tab behavior that can change across different
 * configurations or circumstances.
 *
 * @hide
 */
public class ScrollingTabContainerView extends HorizontalScrollView
        implements AdapterViewICS.OnItemClickListener {

    private static final String TAG = "ScrollingTabContainerView";
    Runnable mTabSelector;
    private TabClickListener mTabClickListener;

    private LinearLayout mTabLayout;
    private SpinnerICS mTabSpinner;
    private boolean mAllowCollapse;

    private final LayoutInflater mInflater;

    int mMaxTabWidth;
    int mStackedTabMaxWidth;
    private int mContentHeight;
    private int mSelectedTabIndex;

///book BEGIN
	//@bookHook(bookHook.bookHookType.NEW_FIELD)
    private boolean mHasRoundTab = false;
	
	//@bookHook(bookHook.bookHookType.NEW_FIELD)
    private Drawable mBackgroundLeft, mBackgroundRight, mBackgroundMiddle;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
    public static final int SCROLL_STATE_IDLE = 0;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    public static final int SCROLL_STATE_DRAGGING = 1;

   // @bookHook(bookHook.bookHookType.NEW_FIELD)
    public static final int SCROLL_STATE_SETTLING = 2;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private static final int INDICATOR_HEIGHT = 2; 

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private static final float INDICATOR_POSITIONOFFSET = 0.6f;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private static final float INDICATOR_WIDTH_SCAL_MIN = 1.5f;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private static final float INDICATOR_WIDTH_SCAL_MAX = 0.7f;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private Drawable mBackground;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private ImageView mIndicatorView;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private int mIndicatorHeight;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private FrameLayout mRootLayout;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private int mMaxIndicatorWidth;

    ////@bookHook(bookHook.bookHookType.NEW_FIELD)
    private int mMinIndicatorWidth;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private float mRateDrag;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private int mStateIndicator;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private int mStateScroll;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private int mLastStateScroll;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private int mTabViewWidth;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private int mCurrentPosition = 0;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private float mLastPositionOffset;

    //@bookHook(bookHook.bookHookType.NEW_FIELD)
    private int mDurationTrans;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private static float mInActiveTextSize;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private static float mActiveTextSize;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private float mThresHoldSize;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private int mThresHoldAlpha;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private static ColorSet mInActiveTextColor;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private static ColorSet mActiveTextColor;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private boolean mHasScrolled;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private static final float DEFAULT_INACTIVE_TEXTSIZE = 15f;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private static final float DEFAULT_ACTIVE_TEXTSIZE = 15f;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private static final int DEFAULT_INACTIVE_TRANSPARENCY = 40;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private static int mIconMargin;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private int mInactiveTransparency;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private ArrayList<Integer> mHasSecondaryTabIndexs = new ArrayList<Integer>();

	///@bookHook(bookHook.bookHookType.NEW_FIELD)
	private int mColorfulMode;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private int mTabHeight;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private boolean mIsImmersiveStatusbar;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private int mStatusbarHeight;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private int mActionbarHeight;
	
	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private static final int SECONDARY_INDICATOR_TRANSPARENCY = 0xff;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private static final int NORMAL_INDICATOR_TRANSPARENCY = 0x66;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private static final int THRESHOLD_INDICATOR_TRANSPARENCY = SECONDARY_INDICATOR_TRANSPARENCY - NORMAL_INDICATOR_TRANSPARENCY;

	//@bookHook(bookHook.bookHookType.NEW_FIELD)
	private boolean mIsTabAdjust;

///book  END

    //@bookHook(bookHook.bookHookType.CHANGE_CODE)
    public ScrollingTabContainerView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);

        setHorizontalScrollBarEnabled(false);

        ActionBarPolicy abp = ActionBarPolicy.get(context);
        setContentHeight(abp.getTabContainerHeight());
        mStackedTabMaxWidth = abp.getStackedTabMaxWidth();

///book BEGIN
		Injector.adjustContainerHeight(context, abp, this);
///book END

        //mTabLayout = createTabLayout();
        mTabLayout = (LinearLayout) mInflater.inflate(R.layout.abc_action_bar_tabbar, this, false);
///book BEGIN
        //addView(mTabLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                //ViewGroup.LayoutParams.MATCH_PARENT));
		if (true) {
			Injector.initView(context, this);
		} else {
			addView(mTabLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
		}
///book END
		
///book BEGIN
		Injector.initial(context, this);
///book END

/*        mTabLayout = (LinearLayout) mInflater.inflate(R.layout.abc_action_bar_tabbar, this, false);
        addView(mTabLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.FILL_PARENT));*/
    }

/*    private LinearLayout createTabLayout() {
        final LinearLayout tabLayout = new LinearLayout(getContext(), null,
                com.android.internal.R.attr.actionBarTabBarStyle);
        tabLayout.setMeasureWithLargestChildEnabled(true);
        tabLayout.setGravity(Gravity.CENTER);
        tabLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return tabLayout;
    }*/

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
        setFillViewport(lockedExpanded);

        final int childCount = mTabLayout.getChildCount();
        if (childCount > 1 &&
                (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
            if (childCount > 2) {
                mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
            } else {
                mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
            }
            mMaxTabWidth = Math.min(mMaxTabWidth, mStackedTabMaxWidth);
        } else {
            mMaxTabWidth = -1;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mContentHeight, MeasureSpec.EXACTLY);

        final boolean canCollapse = !lockedExpanded && mAllowCollapse;
        if (canCollapse) {
            // See if we should expand
            mTabLayout.measure(MeasureSpec.UNSPECIFIED, heightMeasureSpec);
            if (mTabLayout.getMeasuredWidth() > MeasureSpec.getSize(widthMeasureSpec)) {
                performCollapse();
            } else {
                performExpand();
            }
        } else {
            performExpand();
        }

        final int oldWidth = getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int newWidth = getMeasuredWidth();

        if (lockedExpanded && oldWidth != newWidth) {
            // Recenter the tab display if we're at a new (scrollable) size.
            setTabSelected(mSelectedTabIndex);
        }

///book  BEGIN
        Injector.setIndicator(getContext(), this, childCount);
///book  END
    }

    /**
     * Indicates whether this view is collapsed into a dropdown menu instead of traditional tabs.
     *
     * @return true if showing as a spinner
     */
    private boolean isCollapsed() {
        return mTabSpinner != null && mTabSpinner.getParent() == this;
    }

    public void setAllowCollapse(boolean allowCollapse) {
        mAllowCollapse = allowCollapse;
    }

    private void performCollapse() {
        if (isCollapsed()) {
            return;
        }

        if (mTabSpinner == null) {
            mTabSpinner = createSpinner();
        }
        removeView(mTabLayout);
        if (this.getChildCount() != 0) {
            this.removeAllViews();
        }

        addView(mTabSpinner, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        if (mTabSpinner.getAdapter() == null) {
            mTabSpinner.setAdapter(new TabAdapter());
        }
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
            mTabSelector = null;
        }
        mTabSpinner.setSelection(mSelectedTabIndex);
    }

    private boolean performExpand() {
        if (!isCollapsed()) {
            return false;
        }

        removeView(mTabSpinner);
        //pr1044917 lkzhou start
        try {
          
            if ((this != null) && (this.getChildCount() != 0)) {
                removeAllViews();
            }

            if (mTabLayout != null) {
                ViewGroup viewGroup = (ViewGroup)(mTabLayout.getParent());
                if (viewGroup != null) {
                  
                    viewGroup.removeView(mTabLayout);
                }
            } 

        } catch(Exception e) {

        }
        //pr1044917 lkzhou end
        addView(mTabLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        setTabSelected(mTabSpinner.getSelectedItemPosition());
        return false;
    }

    public void setTabSelected(int position) {
        mSelectedTabIndex = position;
        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            final boolean isSelected = i == position;
            child.setSelected(isSelected);
            if (isSelected) {
                animateToTab(position);
///book  BEGIN
                Injector.setIndicatorLayout(getContext(), this);
///book  END
            }
        }
		
///book BEGIN
		Injector.confirmTabsTextStyleIfNeeded(getContext(), this);
///book END
		
        if (mTabSpinner != null && position >= 0) {
            mTabSpinner.setSelection(position);
        }
    }

    public void setContentHeight(int contentHeight) {
        mContentHeight = contentHeight;
        requestLayout();
        Log.d("simply","-----setContentHeight:"+mContentHeight);
    }

    private SpinnerICS createSpinner() {
        final SpinnerICS spinner = new SpinnerICS(getContext(), null,
                R.attr.actionDropDownStyle);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT));
        spinner.setOnItemClickListenerInt(this);
        return spinner;
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        ActionBarPolicy abp = ActionBarPolicy.get(getContext());
        // Action bar can change size on configuration changes.
        // Reread the desired height from the theme-specified style.
        setContentHeight(abp.getTabContainerHeight() + mStatusbarHeight);
        mStackedTabMaxWidth = abp.getStackedTabMaxWidth();
    }

    public void animateToTab(final int position) {
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                smoothScrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            // Re-post the selector we saved
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

    private TabView createTabView(ActionBar.Tab tab, boolean forAdapter) {
        final TabView tabView = (TabView) mInflater.inflate(R.layout.abc_action_bar_tab, mTabLayout,
                false);
        tabView.attach(this, tab, forAdapter);

        if (forAdapter) {
            tabView.setBackgroundDrawable(null);
            tabView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT,
                    mContentHeight));
        } else {
            tabView.setFocusable(true);
            tabView.setBackgroundDrawable(null);
            if (mTabClickListener == null) {
                mTabClickListener = new TabClickListener();
            }
            tabView.setOnClickListener(mTabClickListener);
        }
///book BEGIN
		Injector.setViewPadding(this, tabView, getPaddingLeft(), getPaddingTop() + mStatusbarHeight, 
				getPaddingRight(), getPaddingBottom());
///book END
        return tabView;
    }

    public void addTab(ActionBar.Tab tab, boolean setSelected) {
        TabView tabView = createTabView(tab, false);
///book BEGIN
        //mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0,
                //LayoutParams.MATCH_PARENT, 1));
        if (true) {
			Injector.addTab(this, tab, tabView);
        } else {
        	mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0,
                LayoutParams.MATCH_PARENT, 1));
        }
///book END
        if (mTabSpinner != null) {
            ((TabAdapter) mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (setSelected) {
            tabView.setSelected(true);
        }
        if (mAllowCollapse) {
            requestLayout();
        }
///book BEGIN
        Injector.updateTabBackground(getContext(), this);
///book END
    }

    public void addTab(ActionBar.Tab tab, int position, boolean setSelected) {
        final TabView tabView = createTabView(tab, false);
///book BEGIN
		//mTabLayout.addView(tabView, position, new LinearLayout.LayoutParams(
                //0, LayoutParams.MATCH_PARENT, 1));
		if (true) {
			Injector.addTab(this, tab, tabView);
		} else {
			mTabLayout.addView(tabView, position, new LinearLayout.LayoutParams(
                0, LayoutParams.MATCH_PARENT, 1));
		}
///book END
        if (mTabSpinner != null) {
            ((TabAdapter) mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (setSelected) {
            tabView.setSelected(true);
        }
        if (mAllowCollapse) {
            requestLayout();
        }
///book BEGIN
        Injector.updateTabBackground(getContext(), this);
///book END
    }

    public void updateTab(int position) {
        ((TabView) mTabLayout.getChildAt(position)).update();
        if (mTabSpinner != null) {
            ((TabAdapter) mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (mAllowCollapse) {
            requestLayout();
        }
    }

    public void removeTabAt(int position) {
        mTabLayout.removeViewAt(position);
        if (mTabSpinner != null) {
            ((TabAdapter) mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (mAllowCollapse) {
            requestLayout();
        }
    }

    public void removeAllTabs() {
        mTabLayout.removeAllViews();
        if (mTabSpinner != null) {
            ((TabAdapter) mTabSpinner.getAdapter()).notifyDataSetChanged();
        }
        if (mAllowCollapse) {
            requestLayout();
        }
    }

    @Override
    public void onItemClick(AdapterViewICS<?> parent, View view, int position, long id) {
        TabView tabView = (TabView) view;
        tabView.getTab().select();
    }

    /**
     * @hide
     */
    public static class TabView extends LinearLayout {

        private ActionBar.Tab mTab;
        private TextView mTextView;
        private ImageView mIconView;
        private View mCustomView;
        private ScrollingTabContainerView mParent;

        //@bookHook(bookHook.bookHookType.NEW_FIELD)
        private boolean isActive;

        //@bookHook(bookHook.bookHookType.NEW_FIELD)
        private MotionEvent mCurrentDownEvent;

        public TabView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        void attach(ScrollingTabContainerView parent, ActionBar.Tab tab, boolean forList) {
            mParent = parent;
            mTab = tab;

            if (forList) {
                setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            }
            setGravity(Gravity.CENTER_HORIZONTAL);
            update();
        }

        public void bindTab(ActionBar.Tab tab) {
            mTab = tab;
            update();
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            int maxTabWidth = mParent != null ? mParent.mMaxTabWidth : 0;

            // Re-measure if we went beyond our maximum size.
            if (maxTabWidth > 0 && getMeasuredWidth() > maxTabWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(maxTabWidth, MeasureSpec.EXACTLY),
                        heightMeasureSpec);
            }

///book BEGIN

			// Re-measure if the tab content is a drawable
			Drawable ic = mTab.getIcon();
			if (ic != null) {
				if (ic instanceof Drawable) {
					super.onMeasure(MeasureSpec.makeMeasureSpec(ic.getIntrinsicWidth() + mIconMargin, MeasureSpec.EXACTLY),
						heightMeasureSpec);
				} else if (ic instanceof StateListDrawable) {
					Drawable ic0 = null;//((StateListDrawable)ic).;
					if (ic0 != null) {
						super.onMeasure(MeasureSpec.makeMeasureSpec(ic0.getIntrinsicWidth() + mIconMargin, MeasureSpec.EXACTLY),
							heightMeasureSpec);
					}
				}
            }
///book END		
        }

        public void update() {
            final ActionBar.Tab tab = mTab;
            final View custom = tab.getCustomView();
            if (custom != null) {
                final ViewParent customParent = custom.getParent();
                if (customParent != this) {
                    if (customParent != null) {
                        ((ViewGroup) customParent).removeView(custom);
                    }
                    addView(custom);
                }
                mCustomView = custom;
                if (mTextView != null) {
                    mTextView.setVisibility(GONE);
                }
                if (mIconView != null) {
                    mIconView.setVisibility(GONE);
                    mIconView.setImageDrawable(null);
                }
            } else {
                if (mCustomView != null) {
                    removeView(mCustomView);
                    mCustomView = null;
                }

                final Drawable icon = tab.getIcon();
                final CharSequence text = tab.getText();

                if (icon != null) {
                    if (mIconView == null) {
                        ImageView iconView = new ImageView(getContext());
                        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT);
                        lp.gravity = Gravity.CENTER_VERTICAL;
                        iconView.setLayoutParams(lp);
                        addView(iconView, 0);
                        mIconView = iconView;
                    }
                    mIconView.setImageDrawable(icon);
                    mIconView.setVisibility(VISIBLE);
                } else if (mIconView != null) {
                    mIconView.setVisibility(GONE);
                    mIconView.setImageDrawable(null);
                }

                if (text != null) {
                    if (mTextView == null) {
                        TextView textView = new CompatTextView(getContext(), null,
                                R.attr.actionBarTabTextStyle);
                        /// M: Long string setting
                        //textView.setEllipsize(TruncateAt.END);
                        Injector.setTabTextViewStyle(textView);

                        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT);
                        lp.gravity = Gravity.CENTER_VERTICAL;
                        textView.setLayoutParams(lp);
                        addView(textView);
                        mTextView = textView;
                    }
                    mTextView.setText(text);
                    mTextView.setVisibility(VISIBLE);
                } else if (mTextView != null) {
                    mTextView.setVisibility(GONE);
                    mTextView.setText(null);
                }

                if (mIconView != null) {
                    mIconView.setContentDescription(tab.getContentDescription());
                }
            }
        }

        public ActionBar.Tab getTab() {
            return mTab;
        }

///book  BEGIN
        //@bookHook(bookHook.bookHookType.NEW_METHOD)
        public TextView getTextView() {
            return mTextView;
        }

        //@bookHook(bookHook.bookHookType.NEW_METHOD)
        public ImageView getIconView() {
            return mIconView;
        }

        ////@bookHook(bookHook.bookHookType.NEW_METHOD)
        public View getCustomView() {
            return mCustomView;
        }

		//@bookHook(bookHook.bookHookType.NEW_METHOD)
        public int getCustomViewWidth() {
            return mTab.getCustomViewWidth();
        }
		
		//@bookHook(bookHook.bookHookType.NEW_METHOD)
		public void confirmTextStyle(boolean isSelected) {
			if (mTextView == null) {
				return;
			}
		    //mTextView.setScaleX(1);
			//mTextView.setScaleY(1);
			if (isSelected) {
				mTextView.setTextSize(mActiveTextSize);
				mTextView.setTextColor(mActiveTextColor.getColor());
			} else {
				mTextView.setTextSize(mInActiveTextSize);
				mTextView.setTextColor(mInActiveTextColor.getColor());
			}
		}

        //@bookHook(bookHook.bookHookType.NEW_METHOD)
		@Override
    	public boolean onTouchEvent(MotionEvent event) {
        	final int action = event.getAction();
	        final float x = event.getX();
    	    final float y = event.getY();

        	switch (action) {
			case MotionEvent.ACTION_DOWN:
				if (mCurrentDownEvent != null) {
					mCurrentDownEvent.recycle();
				}
				mCurrentDownEvent = MotionEvent.obtain(event);
				if (mTextView != null) {
				    int color = mTextView.getCurrentTextColor();
    				if (color == mActiveTextColor.getColor()) {
	    				mTextView.setTextColor(mActiveTextColor.getColor(0x80));
		    			isActive = true;
			    	} else {
				        mTextView.setTextColor(mInActiveTextColor.getColor(0x80));
					    isActive = false;
    				}
				}
				if (mIconView != null) {
					mIconView.setAlpha(0x80);
				}
	            break;
			case MotionEvent.ACTION_MOVE:
				final int deltaX = (int) (x - mCurrentDownEvent.getX());
				final int deltaY = (int) (y - mCurrentDownEvent.getY());
				int distance = (deltaX * deltaX) + (deltaY * deltaY);

				if (distance == 0) {
					break;
				}

				if (mTextView != null) {
					if (isActive) {
    					mTextView.setTextColor(mActiveTextColor.getColor());
					} else {
					    mTextView.setTextColor(mInActiveTextColor.getColor());
					}
				}
				if (mIconView != null) {
					mIconView.setAlpha(0xff);
				}
				break;
			case MotionEvent.ACTION_UP:
				if (mIconView != null) {
					mIconView.setAlpha(0xff);
				}
				break;
	        }
			
    	    return super.onTouchEvent(event);
	    }
///book  END
    }

    private class TabAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mTabLayout.getChildCount();
        }

        @Override
        public Object getItem(int position) {
            return ((TabView) mTabLayout.getChildAt(position)).getTab();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = createTabView((ActionBar.Tab) getItem(position), true);
            } else {
                ((TabView) convertView).bindTab((ActionBar.Tab) getItem(position));
            }
            return convertView;
        }
    }

    private class TabClickListener implements OnClickListener {

        public void onClick(View view) {
            TabView tabView = (TabView) view;
            tabView.getTab().select();
            final int tabCount = mTabLayout.getChildCount();
            for (int i = 0; i < tabCount; i++) {
                final View child = mTabLayout.getChildAt(i);
                child.setSelected(child == view);
            }
///book BEGIN
			Injector.confirmTabsTextStyle(getContext(), ScrollingTabContainerView.this);
///book END

        }
    }

//book  BEGIN
	@Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

		Injector.onLayout(getContext(), this, l, t, r, b);
	}

    public void setScrollState(int state) {
        mLastStateScroll = mStateScroll;
        mStateScroll = state;

        if (state != SCROLL_STATE_SETTLING) {
            mStateIndicator = state;
        }
        if (state == SCROLL_STATE_IDLE
            || mLastStateScroll == SCROLL_STATE_SETTLING && mStateScroll == SCROLL_STATE_DRAGGING) {
            if (mLastStateScroll == SCROLL_STATE_SETTLING && mStateScroll == SCROLL_STATE_DRAGGING) {
                mStateIndicator = SCROLL_STATE_IDLE;
            }
            mCurrentPosition = mSelectedTabIndex;
            mLastPositionOffset = SCROLL_STATE_IDLE;
            Injector.setIndicatorLayout(getContext(), this);
///book BEGIN
			Injector.confirmTabsTextStyle(getContext(), this);
///book END
            if (mLastStateScroll == SCROLL_STATE_SETTLING && mStateScroll == SCROLL_STATE_DRAGGING) {
                mStateIndicator = SCROLL_STATE_DRAGGING;
            }
        }
    }

    Drawable drawable = new ColorDrawable(Color.RED);
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        drawable.draw(canvas);
    }

    public void smoothScrollTabIndicator(int position, float positionOffset, int positionOffsetPixels) {
        //Log.d("jxli", "mStateIndicator = " + mStateIndicator + ", mSelectedTabIndex = " + mSelectedTabIndex + ", position = " + position + ", mCurrentPosition = " + mCurrentPosition
        //    + ", positionOffset = " + positionOffset + ", mRateDrag = " + mRateDrag);
        //Log.v("jxli", "mLastPositionOffset = " + mLastPositionOffset + ", mDurationTrans = " + mDurationTrans);
        int width = mIndicatorView.getMeasuredWidth();
        if ((mStateIndicator == SCROLL_STATE_DRAGGING)&& positionOffset != 0) {
            if (mCurrentPosition == position) {
                TabView tabViewCurr = (TabView) mTabLayout.getChildAt(position);
                TextView textViewCurr = (TextView) tabViewCurr.getTextView();
                float widthCurr = (float) (textViewCurr.getMeasuredWidth() * INDICATOR_WIDTH_SCAL_MIN);
                TabView tabViewNext = (TabView) mTabLayout.getChildAt(position+1);
                TextView textViewNext = (TextView) tabViewNext.getTextView();
                float widthNext = (float) (textViewNext.getMeasuredWidth() * INDICATOR_WIDTH_SCAL_MIN);

                if (positionOffset <= INDICATOR_POSITIONOFFSET) {
                    mIndicatorView.setPivotX(0);
                    mIndicatorView.setScaleX(1 + positionOffset * mRateDrag);
                    mLastPositionOffset = positionOffset;
                    mDurationTrans = (int) (mTabViewWidth + widthCurr / 2 + widthNext / 2 - mMaxIndicatorWidth);

					if (mIsTabAdjust) {
						mIsTabAdjust = false;
						textViewCurr.setTextColor(mActiveTextColor.getColor());
						textViewNext.setTextColor(mInActiveTextColor.getColor());
					}
        
                } else {
                    //Log.v("jxli", "111111222222, scaleX = " + (widthNext/widthCurr + (mMaxIndicatorWidth-widthNext)/widthCurr
                    //                            * (1 - (positionOffset-mLastPositionOffset) / (1-mLastPositionOffset))));
                    //Log.v("jxli", "111111222222, translationX = " + ((mMaxIndicatorWidth - widthCurr) + (positionOffset - mLastPositionOffset) / (1-mLastPositionOffset)*mDurationTrans));
                    mIndicatorView.setPivotX(width);
                    mIndicatorView.setScaleX(widthNext/widthCurr + (mMaxIndicatorWidth-widthNext)/widthCurr
                                            * (1 - (positionOffset-mLastPositionOffset) / (1-mLastPositionOffset)));
                    mIndicatorView.setTranslationX((mMaxIndicatorWidth - widthCurr) + (positionOffset - mLastPositionOffset) / (1-mLastPositionOffset)*mDurationTrans);

					if (!mIsTabAdjust) {
						mIsTabAdjust = true;
						textViewCurr.setTextColor(mInActiveTextColor.getColor());
						textViewNext.setTextColor(mActiveTextColor.getColor());
					}
                }

				if (mHasSecondaryTabIndexs.contains(position) && !mHasSecondaryTabIndexs.contains(position + 1)) {
					int alpha = SECONDARY_INDICATOR_TRANSPARENCY - (int)(positionOffset * THRESHOLD_INDICATOR_TRANSPARENCY);
					mIndicatorView.setAlpha(alpha);
				}
				if (!mHasSecondaryTabIndexs.contains(position) && mHasSecondaryTabIndexs.contains(position + 1)) {
					int alpha = NORMAL_INDICATOR_TRANSPARENCY + (int)(positionOffset * THRESHOLD_INDICATOR_TRANSPARENCY);
					mIndicatorView.setAlpha(alpha);
				}

				/*
				float size1 = map(positionOffset, 1f, 0f, mInActiveTextSize, mActiveTextSize);
				float size2 = map(positionOffset, 1f, 0f, mActiveTextSize, mInActiveTextSize);
				textViewCurr.setTextSize(size1);
				textViewNext.setTextSize(size2);
				*/				
				//int alpha1 = (int)map(positionOffset, 1f, 0f, (float)mInActiveTextColor.alpha, (float)mActiveTextColor.alpha);
				//int alpha2 = (int)map(positionOffset, 1f, 0f, (float)mActiveTextColor.alpha, (float)mInActiveTextColor.alpha);
/*				int alpha1 = mActiveTextColor.alpha - (int)(mThresHoldAlpha * positionOffset);
				int alpha2 = mInActiveTextColor.alpha + (int)(mThresHoldAlpha * positionOffset);
				textViewCurr.setTextColor(mActiveTextColor.getColor(alpha1));
				textViewNext.setTextColor(mInActiveTextColor.getColor(alpha2));

				textViewCurr.setScaleX((mActiveTextSize - mThresHoldSize * positionOffset) / mActiveTextSize);
				textViewCurr.setScaleY((mActiveTextSize - mThresHoldSize * positionOffset) / mActiveTextSize);
				textViewNext.setScaleX((mInActiveTextSize + mThresHoldSize * positionOffset) / mInActiveTextSize);
				textViewNext.setScaleY((mInActiveTextSize + mThresHoldSize * positionOffset) / mInActiveTextSize);*/
				

				/*float size1 = mActiveTextSize - mThresHoldSize * positionOffset;
				float size2 = mInActiveTextSize + mThresHoldSize * positionOffset;
				int alpha1 = mActiveTextColor.alpha - (int)(mThresHoldAlpha * positionOffset);
				int alpha2 = mInActiveTextColor.alpha + (int)(mThresHoldAlpha * positionOffset);

				textViewCurr.book_setTextSizeAndColor(size1, mActiveTextColor.getColor(alpha1));
				textViewNext.book_setTextSizeAndColor(size2, mActiveTextColor.getColor(alpha2));*/
				
            } else {
                float realPositionOffset = 1 - positionOffset;
                TabView tabViewCurr = (TabView) mTabLayout.getChildAt(position+1);
                TextView textViewCurr = (TextView) tabViewCurr.getTextView();
                float widthCurr = (float) (textViewCurr.getMeasuredWidth() * INDICATOR_WIDTH_SCAL_MIN);
                TabView tabViewNext = (TabView) mTabLayout.getChildAt(position);
                TextView textViewNext = (TextView) tabViewNext.getTextView();
                float widthNext = (float) (textViewNext.getMeasuredWidth() * INDICATOR_WIDTH_SCAL_MIN);
                
                if (realPositionOffset <= INDICATOR_POSITIONOFFSET) {
                    mIndicatorView.setPivotX(width);
                    mIndicatorView.setScaleX(1 + realPositionOffset * mRateDrag);
                    mLastPositionOffset = realPositionOffset;
                    mDurationTrans = (int) (mTabViewWidth + widthCurr / 2 + widthNext / 2 - mMaxIndicatorWidth);

					if (mIsTabAdjust) {
						mIsTabAdjust = false;
						textViewCurr.setTextColor(mActiveTextColor.getColor());
						textViewNext.setTextColor(mInActiveTextColor.getColor());
					}

                } else {
                    //Log.v("jxli", "2222222111111, scaleX = " + (widthNext/widthCurr + (mMaxIndicatorWidth-widthNext)/widthCurr
                    //                            * (1 - (realPositionOffset-mLastPositionOffset) / (1-mLastPositionOffset))));
                    //Log.v("jxli", "2222222222222, translationX = " + ((mMaxIndicatorWidth - widthCurr) + (realPositionOffset - mLastPositionOffset) / (1-mLastPositionOffset)*mDurationTrans));
                    mIndicatorView.setPivotX(0);
                    mIndicatorView.setScaleX(widthNext/widthCurr + (mMaxIndicatorWidth-widthNext)/widthCurr
                                            * (1 - (realPositionOffset-mLastPositionOffset) / (1-mLastPositionOffset)));
                    mIndicatorView.setTranslationX(-((mMaxIndicatorWidth - widthCurr) + (realPositionOffset - mLastPositionOffset) / (1-mLastPositionOffset)*mDurationTrans));

					if (!mIsTabAdjust) {
						mIsTabAdjust = true;
						textViewCurr.setTextColor(mInActiveTextColor.getColor());
						textViewNext.setTextColor(mActiveTextColor.getColor());
					}
                }

				if (mHasSecondaryTabIndexs.contains(position + 1) && !mHasSecondaryTabIndexs.contains(position)) {
					int alpha = SECONDARY_INDICATOR_TRANSPARENCY - (int)(realPositionOffset * THRESHOLD_INDICATOR_TRANSPARENCY);
					mIndicatorView.setAlpha(alpha);
				}
				if (!mHasSecondaryTabIndexs.contains(position + 1) && mHasSecondaryTabIndexs.contains(position)) {
					int alpha = NORMAL_INDICATOR_TRANSPARENCY + (int)(realPositionOffset * THRESHOLD_INDICATOR_TRANSPARENCY);
					mIndicatorView.setAlpha(alpha);
				}

				/*
				float size1 = map(realPositionOffset, 1f, 0f, mInActiveTextSize, mActiveTextSize);
				float size2 = map(realPositionOffset, 1f, 0f, mActiveTextSize, mInActiveTextSize);
				textViewCurr.setTextSize(size1);
				textViewNext.setTextSize(size2);
				*/
				//int alpha1 = (int)map(realPositionOffset, 1f, 0f, (float)mInActiveTextColor.alpha, (float)mActiveTextColor.alpha);
				//int alpha2 = (int)map(realPositionOffset, 1f, 0f, (float)mActiveTextColor.alpha, (float)mInActiveTextColor.alpha);
/*				int alpha1 = mActiveTextColor.alpha - (int)(mThresHoldAlpha * realPositionOffset);
				int alpha2 = mInActiveTextColor.alpha + (int)(mThresHoldAlpha * realPositionOffset);
				textViewCurr.setTextColor(mActiveTextColor.getColor(alpha1));
				textViewNext.setTextColor(mInActiveTextColor.getColor(alpha2));

				textViewCurr.setScaleX((mActiveTextSize - mThresHoldSize * realPositionOffset) / mActiveTextSize);
				textViewCurr.setScaleY((mActiveTextSize - mThresHoldSize * realPositionOffset) / mActiveTextSize);
				textViewNext.setScaleX((mInActiveTextSize + mThresHoldSize * realPositionOffset) / mInActiveTextSize);
				textViewNext.setScaleY((mInActiveTextSize + mThresHoldSize * realPositionOffset) / mInActiveTextSize);*/
				

/*				float size1 = mActiveTextSize - mThresHoldSize * realPositionOffset;
				float size2 = mInActiveTextSize + mThresHoldSize * realPositionOffset;
				int alpha1 = mActiveTextColor.alpha - (int)(mThresHoldAlpha * realPositionOffset);
				int alpha2 = mInActiveTextColor.alpha + (int)(mThresHoldAlpha * realPositionOffset);

				textViewCurr.book_setTextSizeAndColor(size1, mActiveTextColor.getColor(alpha1));
				textViewNext.book_setTextSizeAndColor(size2, mActiveTextColor.getColor(alpha2));*/
            }
///book BEGIN
			mHasScrolled = true;
///book END
        }
    }

	public void setHasSecondaryTab(int index) {
		mHasSecondaryTabIndexs.add(index);
	}
///book END

	static class ColorSet {
        public int alpha;
        public int red;
        public int green;
        public int blue;

        ColorSet(int color) {
            setColor(color);
        }

		ColorSet(int a, int color) {
			setColor(a, color);
		}

        public void setColor(int color) {
            alpha = Color.alpha(color);
            red = Color.red(color);
            green = Color.green(color);
            blue = Color.blue(color);
        }

		public void setColor(int a, int color) {
			alpha = a;
			red = Color.red(color);
            green = Color.green(color);
            blue = Color.blue(color);
		}

        public int getColor() {
            return Color.argb(alpha, red, green, blue);
        }

        public int getColor(int alphaOverride) {
            return Color.argb(alphaOverride, red, green, blue);
        }
    }

	///book BEGIN
	static class Injector {
		static void adjustContainerHeight(Context context, ActionBarPolicy abp, ScrollingTabContainerView stcv) {
			stcv.mIsImmersiveStatusbar = true;//bookUiUtil.isImmersiveStatusbar(context);
			stcv.mStatusbarHeight = (int)context.getResources().getDimension(R.dimen.android_status_bar_height);
			stcv.mActionbarHeight = abp.get(context).getTabContainerHeight();

			setTabHeight(stcv, abp.getTabContainerHeight());
			if (stcv.mIsImmersiveStatusbar) {
				stcv.setContentHeight(stcv.mStatusbarHeight + stcv.mActionbarHeight);
        	}
		}

		static void initView(Context context, ScrollingTabContainerView stcv) {
			stcv.mRootLayout = createRootLayout(context);
	        stcv.mRootLayout.addView(stcv.mTabLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));

			final float density = context.getResources().getDisplayMetrics().density;
    	    stcv.mIndicatorHeight = (int) (INDICATOR_HEIGHT * density);
        	stcv.mBackground = context.getResources().getDrawable(R.drawable.action_bar_tab_indicator);
        
    	    stcv.mIndicatorView = new ImageView(context);
	        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(0, stcv.mIndicatorHeight);
    	    //lp.setMargins(0, 0, 0, mIndicatorHeight);
        	lp.gravity = Gravity.BOTTOM;
	        stcv.mIndicatorView.setLayoutParams(lp);
    	    stcv.mIndicatorView.setScaleType(ImageView.ScaleType.FIT_XY);
        	stcv.mIndicatorView.setImageDrawable(stcv.mBackground);
	        stcv.mRootLayout.addView(stcv.mIndicatorView);
			stcv.mIndicatorView.setVisibility(View.GONE);
        
        	stcv.addView(stcv.mRootLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.FILL_PARENT));
		}

		static void initial(Context context, ScrollingTabContainerView stcv) {
			
/*			try {
            	android.content.res.TypedArray a = context.obtainStyledAttributes(null,
                    com.android.internal.R.styleable.TextView,
                    com.android.internal.R.attr.actionBarTabStyle, 0);
	            stcv.mBackgroundMiddle = a
                    .getDrawable(com.android.internal.R.styleable.TextView_drawableTop);
    	        stcv.mBackgroundLeft = a
                    .getDrawable(com.android.internal.R.styleable.TextView_drawableLeft);
        	    stcv.mBackgroundRight = a
                    .getDrawable(com.android.internal.R.styleable.TextView_drawableRight);
            	a.recycle();
	            if (stcv.mBackgroundLeft != null && stcv.mBackgroundRight != null) {
    	            stcv.mHasRoundTab = true;
        	    }
	        } catch (Exception e) {
    	    }*/
		
			try {
/*            	android.content.res.TypedArray ta = context.obtainStyledAttributes(null,
                    com.book.internal.R.styleable.ScrollTabContainer,
                    com.book.internal.R.attr.book_ScrollTabContainer, 0);*/
				stcv.mInActiveTextSize = dpToPxCoord(context,
					//ta.getDimension(com.book.internal.R.styleable.ScrollTabContainer_tab_inactive_textSize, stcv.DEFAULT_INACTIVE_TEXTSIZE),
					stcv.DEFAULT_INACTIVE_TEXTSIZE,
					stcv.DEFAULT_INACTIVE_TEXTSIZE);
				stcv.mActiveTextSize = dpToPxCoord(context,
					//ta.getDimension(com.book.internal.R.styleable.ScrollTabContainer_tab_active_textSize, stcv.DEFAULT_ACTIVE_TEXTSIZE),
					stcv.DEFAULT_ACTIVE_TEXTSIZE,
					stcv.DEFAULT_ACTIVE_TEXTSIZE);
				stcv.mIconMargin = 5;//(int)ta.getDimension(com.book.internal.R.styleable.ScrollTabContainer_tab_icon_margin, 0);
				stcv.mInactiveTransparency = stcv.DEFAULT_INACTIVE_TRANSPARENCY;
				//stcv.mActiveTextColor = new ColorSet(ta.getColor(com.book.internal.R.styleable.ScrollTabContainer_tab_active_textColor, Color.BLUE));
				//stcv.mInActiveTextColor = new ColorSet(ta.getColor(com.book.internal.R.styleable.ScrollTabContainer_tab_inactive_textColor, Color.DKGRAY));
                stcv.mActiveTextColor = new ColorSet(Color.WHITE);
                stcv.mInActiveTextColor = new ColorSet(0x66ffffff);

                //ta.recycle();
        	} catch (Exception e) {
				Log.e(TAG, "Load ScrollTabAnimation Exception");
	        }
			
/*			int mainColor = 0;
			try {
				mainColor = System.getInt(context.getContentResolver(), Settings.book_COLORVIEW_MAINCOLOR_SETTINGS);
				stcv.mColorfulMode = System.getInt(context.getContentResolver(), Settings.book_COLORVIEW_MODE);
			} catch (SettingNotFoundException e) {
				e.printStackTrace();
			}*/

//			if (mColorfulMode != Settings.book_COLORVIEW_MODE_NONE) {

/*				android.content.res.bookColorfulResources.bookColorfulStyle.ColorfulNode colorfulStyle =
					((android.content.res.bookResources) stcv.getResources()).getColorfulResources().getColorfulStyle(context);*/

            if (/*colorfulStyle != null*/false) {
                int activeTextColor = Color.BLUE;//colorfulStyle.getThirdTextColor();
                int transparency = Color.RED;//colorfulStyle.getTransparency();
                stcv.mActiveTextColor.setColor(0xff, activeTextColor);
                stcv.mInActiveTextColor
                        .setColor((int) (0xff * transparency / 100), activeTextColor);
            }
/*			} else {
				mBackground = context.getResources().getDrawable(com.book.internal.R.drawable.action_bar_tab_indicator_default);
				mIndicatorView.setImageDrawable(mBackground);			
			}*/

			stcv.mThresHoldSize = stcv.mActiveTextSize - stcv.mInActiveTextSize;
			stcv.mThresHoldAlpha = stcv.mActiveTextColor.alpha - stcv.mInActiveTextColor.alpha;
		}

		static void setIndicator(Context context, ScrollingTabContainerView stcv, int childCount) {
			if (childCount > 0) {
	            setIndicatorLayout(context, stcv);
			}
		}

		static void setIndicatorLayout(Context context, ScrollingTabContainerView stcv) {

            if (stcv.mStateIndicator == stcv.SCROLL_STATE_IDLE) {
                stcv.mCurrentPosition = stcv.mSelectedTabIndex;
                
                TabView tabView = (TabView) stcv.mTabLayout.getChildAt(stcv.mSelectedTabIndex);
                stcv.mTabViewWidth = tabView.getMeasuredWidth();
                TextView textView = (TextView) tabView.getTextView();
                ImageView iconView = (ImageView) tabView.getIconView();
                View customView = tabView.getCustomView();
                int width = 0;
                if (textView != null && textView.getVisibility() == View.VISIBLE) width += textView.getMeasuredWidth();
                if (iconView != null && iconView.getVisibility() == View.VISIBLE) width += iconView.getMeasuredWidth();
                if (customView != null && customView.getVisibility() == View.VISIBLE) width += customView.getMeasuredWidth();
                stcv.mMinIndicatorWidth = (int) (width * stcv.INDICATOR_WIDTH_SCAL_MIN);            
                stcv.mMaxIndicatorWidth = (int) (stcv.mTabViewWidth * stcv.INDICATOR_WIDTH_SCAL_MAX);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) stcv.mIndicatorView.getLayoutParams();
                lp.width = stcv.mMinIndicatorWidth;
                //lp.leftMargin = (int) (mSelectedTabIndex * mTabViewWidth + (mTabViewWidth - mMinIndicatorWidth) / 2);
                if ((tabView.getLeft() == 0 && stcv.mSelectedTabIndex != 0) || stcv.mTabViewWidth == 0) {
					return;
                }
                lp.leftMargin = (int)(tabView.getLeft() + (stcv.mTabViewWidth - stcv.mMinIndicatorWidth) / 2);
				stcv.mIndicatorView.setVisibility(VISIBLE);
                stcv.mIndicatorView.setLayoutParams(lp);
                stcv.mIndicatorView.setTranslationX(0);
                stcv.mIndicatorView.setScaleX(1);
                if (stcv.mMinIndicatorWidth != 0) {
                    stcv.mRateDrag = ((float)stcv.mMaxIndicatorWidth - stcv.mMinIndicatorWidth) / stcv.mMinIndicatorWidth / stcv.INDICATOR_POSITIONOFFSET;
                }

				stcv.mIsTabAdjust = false;
	        }
    	}

		static void setTabTextViewStyle(TextView textView) {
			textView.setSingleLine(true);
            textView.setEllipsize(TruncateAt.MARQUEE);
            textView.setMarqueeRepeatLimit(2);
            textView.setHorizontalFadingEdgeEnabled(true);
		}

		static void onLayout(Context context, ScrollingTabContainerView stcv, int l, int t, int r, int b) {

			for (int i = 0; i < stcv.mTabLayout.getChildCount(); i++) {
				TabView tv = (TabView) stcv.mTabLayout.getChildAt(i);
				if (tv.getTab().getIcon() != null) {
					if (i == 0) {
						tv.layout(l + stcv.mIconMargin, t, tv.getMeasuredWidth() + stcv.mIconMargin, b);
					}
					if (i == stcv.mTabLayout.getChildCount() - 1) {
						tv.layout(r - tv.getMeasuredWidth() - stcv.mIconMargin, t, r - stcv.mIconMargin, b);
					}
				}
			}

			stcv.requestLayout();
		}
 
		static void setViewPadding(ScrollingTabContainerView stcv, View view, int l, int t, int r, int b) {
			if (stcv.mIsImmersiveStatusbar) {
				view.setPadding(l, t, r, b);
			}
		}

		static void confirmTabsTextStyleIfNeeded(Context context, ScrollingTabContainerView stcv) {
			if (!stcv.mHasScrolled) {
				confirmTabsTextStyle(context, stcv);
			}
		}

		static void confirmTabsTextStyle(Context context, ScrollingTabContainerView stcv) {
			for (int i = 0; i < stcv.mTabLayout.getChildCount(); i++) {
				TabView child = (TabView)stcv.mTabLayout.getChildAt(i);
				boolean isSelected = i == stcv.mSelectedTabIndex;
				child.confirmTextStyle(isSelected);
				if (i == stcv.mSelectedTabIndex) {
					if (child.getTab().getIcon() != null) {
						stcv.mIndicatorView.setVisibility(View.GONE);
					} else if (child.getTab().getCustomView() != null) {
					    if (child.getTab().isHideIndicator()) {
							stcv.mIndicatorView.setVisibility(View.GONE);
					    } else {
							if (stcv.mHasSecondaryTabIndexs.contains(i)) {
								stcv.mIndicatorView.setAlpha(stcv.SECONDARY_INDICATOR_TRANSPARENCY);
							} else {
							    stcv.mIndicatorView.setAlpha(stcv.NORMAL_INDICATOR_TRANSPARENCY);
							}
							if ((child.getLeft() == 0 && i != 0) || stcv.mTabViewWidth == 0) {
								continue;
			                }
					        stcv.mIndicatorView.setVisibility(View.VISIBLE);
					    }
					} else {
						if (stcv.mHasSecondaryTabIndexs.contains(i)) {
							stcv.mIndicatorView.setAlpha(stcv.SECONDARY_INDICATOR_TRANSPARENCY);
						} else {
							stcv.mIndicatorView.setAlpha(stcv.NORMAL_INDICATOR_TRANSPARENCY);
						}
						if ((child.getLeft() == 0 && i != 0) || stcv.mTabViewWidth == 0) {
							continue;
		    	        }
						stcv.mIndicatorView.setVisibility(View.VISIBLE);
					}
    	        }
        	}
    	}

		static void updateTabBackground(Context context, ScrollingTabContainerView stcv) {
			
			if (stcv.mHasRoundTab) {
            	int count = stcv.mTabLayout.getChildCount();
	            if (count > 0) {
    	            if (count == 1) {
        	            stcv.mTabLayout.getChildAt(0).setBackground(stcv.mBackgroundMiddle);
            	    } else {
                	    for (int i = 0; i < count; i++) {
                    	    View child = stcv.mTabLayout.getChildAt(i);
                        	if (i == 0) {
                            	child.setBackground(stcv.mBackgroundLeft);
	                        } else if (i == count - 1) {
    	                        child.setBackground(stcv.mBackgroundRight);
        	                } else {
            	                child.setBackground(stcv.mBackgroundMiddle.getConstantState().newDrawable());
                	        }
                    	}
	                }
    	        }
        	}
    	}

		static void addTab(ScrollingTabContainerView stcv, ActionBar.Tab tab, TabView tabView) {
			if (tab.getIcon() != null) {
				stcv.mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0,
					LayoutParams.MATCH_PARENT));
			} else if (tab.getCustomView() != null) {
		    	stcv.mTabLayout.addView(tabView, new LinearLayout.LayoutParams(tab.getCustomViewWidth(),
					LayoutParams.MATCH_PARENT));
			} else {
		        stcv.mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0,
        	        LayoutParams.MATCH_PARENT, 1));
			}
		}
				
		static void setTabHeight(ScrollingTabContainerView stcv, int tabHeight) {
			stcv.mTabHeight = tabHeight;
			stcv.requestLayout();
		}

	    static FrameLayout createRootLayout(Context context) {
    	    FrameLayout layout = new FrameLayout(context);
        	layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
	        return layout;
    	}

		static float dpToPxCoord(Context context, float oriSize, float exception) {
			if (oriSize == exception) {
				return oriSize;
			}
		
			float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
			if (scaledDensity == 0) {
				return oriSize;
			}

			return oriSize / scaledDensity;
		}
	}
	///book ADD END
}
