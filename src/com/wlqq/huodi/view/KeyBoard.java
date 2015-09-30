package com.wlqq.huodi.view;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import com.wlqq.huodi.R;
import com.wlqq.huodi.adapter.KeyBoardWidgetAdapter;

/**
 * @author Cai
 *         Date 12-5-17
 */
@SuppressWarnings("unchecked")
public class KeyBoard {

    Activity activity;

    private final static String TAG = KeyBoard.class.getSimpleName();

    protected GridView mGridView;

    private String[] mKeyBoardDate;

    private KeyBoardWidgetAdapter mKeyBoardWidgetAdapter;

    private String ACTION = "";

    protected EditText mPlateNumEditView;
    private boolean isFirst = true;

    public void init(Activity activity, EditText view, GridView gridView) {
	this.activity = activity;
	this.mGridView = gridView;
	this.mPlateNumEditView = view;

	mKeyBoardDate = activity.getResources().getStringArray(R.array.regionArray);
	mKeyBoardWidgetAdapter = new KeyBoardWidgetAdapter(activity, mKeyBoardDate);
	mGridView.setAdapter(mKeyBoardWidgetAdapter);

	mGridView.setHorizontalSpacing(3);
	mKeyBoardWidgetAdapter.notifyDataSetChanged();

	registerListeners();
    }

    protected void registerListeners() {

	mPlateNumEditView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	    @Override
	    public void onFocusChange(View view, boolean b) {
		if (b) {
		    if (isFirst) {
			mGridView.setVisibility(View.GONE);
			isFirst = false;
		    } else {
			mGridView.setVisibility(View.VISIBLE);
		    }
		} else {
		    mGridView.setVisibility(View.GONE);
		}
	    }
	});

	mPlateNumEditView.addTextChangedListener(new TextWatcher() {
	    @Override
	    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
	    }

	    @Override
	    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		int index = mPlateNumEditView.getSelectionStart();
		int length = mPlateNumEditView.getText().toString().length();
//		if (length == 0) {
//		    mClearButton.setVisibility(View.GONE);
//		} else {
//		    mClearButton.setVisibility(View.VISIBLE);
//		}
		if (length == 0 || index == 0) {
		    mKeyBoardDate = activity.getResources().getStringArray(R.array.regionArray);
		    mKeyBoardWidgetAdapter.resetDate(mKeyBoardDate);
		} else {
		    mKeyBoardDate = activity.getResources().getStringArray(R.array.letterArray);
		    mKeyBoardWidgetAdapter.resetDate(mKeyBoardDate);
		}

	    }

	    @Override
	    public void afterTextChanged(Editable editable) {

		if (mPlateNumEditView.getText().toString().length() == 7) {
		    mGridView.setVisibility(View.GONE);
		}
	    }
	});


	mPlateNumEditView.setOnTouchListener(new View.OnTouchListener() {
	    @Override
	    public boolean onTouch(View view, MotionEvent event) {

		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}

		if (mGridView.getVisibility() == View.GONE) {
		    mGridView.setVisibility(View.VISIBLE);
		}

		mPlateNumEditView.requestFocus();

		Layout layout = ((EditText) view).getLayout();
		float x = event.getX() + mPlateNumEditView.getScrollX();
		if (layout != null) {
		    int offset = layout.getOffsetForHorizontal(0, x);
		    if (offset > 0)
			if (x > layout.getLineMax(0)) {
			    mPlateNumEditView.setSelection(offset);
			} else {
			    mPlateNumEditView.setSelection(offset - 1);
			}
		}


		final int selectionStart = mPlateNumEditView.getSelectionStart();
		int length = mPlateNumEditView.getText().toString().length();
		if (length == 0 || selectionStart == 0) {
		    mKeyBoardDate = activity.getResources().getStringArray(R.array.regionArray);
		    mKeyBoardWidgetAdapter.resetDate(mKeyBoardDate);
		} else {
		    mKeyBoardDate = activity.getResources().getStringArray(R.array.letterArray);
		    mKeyBoardWidgetAdapter.resetDate(mKeyBoardDate);
		}
		return true;
	    }
	});

	mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

		final int selectionStart = mPlateNumEditView.getSelectionStart();

		if (i < mKeyBoardDate.length) {
		    if (mKeyBoardDate[i].equals("deleteUp") || mKeyBoardDate[i].equals("deleteBottom")) {
			final int index = mPlateNumEditView.getSelectionStart();
			if (index > 0) {
			    mPlateNumEditView.getText().delete(index - 1, index);
			}
		    } else {
			mPlateNumEditView.getText().insert(selectionStart, mKeyBoardDate[i]);
		    }
		}
	    }
	});
    }
}