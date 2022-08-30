package book.support.v7.app;

import book.support.v7.appcompat.R;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ActionBarListActivity extends ActionBarActivity {

	private ListView mListView;

	protected ListView getListView() {
		if (mListView == null) {
			mListView = (ListView) findViewById(android.R.id.list);
		}
		if (mListView == null) {
			 setContentView(R.layout.list_content_simple);
			 mListView = (ListView) findViewById(android.R.id.list);
		}
		mListView.setOnItemClickListener(mOnClickListener);
		return mListView;
	}

	protected void setListAdapter(ListAdapter adapter) {
		getListView().setAdapter(adapter);
	}

	protected ListAdapter getListAdapter() {
		ListAdapter adapter = getListView().getAdapter();
		if (adapter instanceof HeaderViewListAdapter) {
			return ((HeaderViewListAdapter) adapter).getWrappedAdapter();
		} else {
			return adapter;
		}
	}
	
//	protected void onListItemClick(ListView lv, View v, int position, long id) {
//	    getListView().getOnItemClickListener().onItemClick(lv, v, position, id);
//	}
//	
//	 /**
//     * This field should be made private, so it is hidden from the SDK.
//     * {@hide}
//     */
//    protected ListAdapter mAdapter;
//    /**
//     * This field should be made private, so it is hidden from the SDK.
//     * {@hide}
//     */
//    protected ListView mList;
//
//    private Handler mHandler = new Handler();
//    private boolean mFinishedStart = false;
//
//    private Runnable mRequestFocus = new Runnable() {
//        public void run() {
//            mList.focusableViewAvailable(mList);
//        }
//    };

    /**
     * This method will be called when an item in the list is selected.
     * Subclasses should override. Subclasses can call
     * getListView().getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param l The ListView where the click happened
     * @param v The view that was clicked within the ListView
     * @param position The position of the view in the list
     * @param id The row id of the item that was clicked
     */
    protected void onListItemClick(ListView l, View v, int position, long id) {
    }

//    /**
//     * Ensures the list view has been created before Activity restores all
//     * of the view states.
//     *
//     *@see Activity#onRestoreInstanceState(Bundle)
//     */
//    @Override
//    protected void onRestoreInstanceState(Bundle state) {
//        ensureList();
//        super.onRestoreInstanceState(state);
//    }
//
//    /**
//     * @see Activity#onDestroy()
//     */
//    @Override
//    protected void onDestroy() {
//        mHandler.removeCallbacks(mRequestFocus);
//        super.onDestroy();
//    }
//
//    /**
//     * Updates the screen state (current list and other views) when the
//     * content changes.
//     *
//     * @see Activity#onContentChanged()
//     */
//    @Override
//    public void onContentChanged() {
//        super.onContentChanged();
//        View emptyView = findViewById(com.android.internal.R.id.empty);
//        mList = (ListView)findViewById(com.android.internal.R.id.list);
//        if (mList == null) {
//            throw new RuntimeException(
//                    "Your content must have a ListView whose id attribute is " +
//                    "'android.R.id.list'");
//        }
//        if (emptyView != null) {
//            mList.setEmptyView(emptyView);
//        }
//        mList.setOnItemClickListener(mOnClickListener);
//        if (mFinishedStart) {
//            setListAdapter(mAdapter);
//        }
//        mHandler.post(mRequestFocus);
//        mFinishedStart = true;
//    }
//
//    /**
//     * Provide the cursor for the list view.
//     */
//    public void setListAdapter(ListAdapter adapter) {
//        synchronized (this) {
//            ensureList();
//            mAdapter = adapter;
//            mList.setAdapter(adapter);
//        }
//    }
//
//    /**
//     * Set the currently selected list item to the specified
//     * position with the adapter's data
//     *
//     * @param position
//     */
//    public void setSelection(int position) {
//        mList.setSelection(position);
//    }
//
//    /**
//     * Get the position of the currently selected list item.
//     */
//    public int getSelectedItemPosition() {
//        return mList.getSelectedItemPosition();
//    }
//
//    /**
//     * Get the cursor row ID of the currently selected list item.
//     */
//    public long getSelectedItemId() {
//        return mList.getSelectedItemId();
//    }
//
//    /**
//     * Get the activity's list view widget.
//     */
//    public ListView getListView() {
//        ensureList();
//        return mList;
//    }
//
//    /**
//     * Get the ListAdapter associated with this activity's ListView.
//     */
//    public ListAdapter getListAdapter() {
//        return mAdapter;
//    }
//
//    private void ensureList() {
//        if (mList != null) {
//            return;
//        }
//        setContentView(R.layout.list_content_simple);
//
//    }

    private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id)
        {
            onListItemClick((ListView)parent, v, position, id);
        }
    };
}
