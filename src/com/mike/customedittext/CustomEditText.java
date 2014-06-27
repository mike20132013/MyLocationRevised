package com.mike.customedittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.mike.mylocationrevised.R;

@SuppressLint("NewApi")
public class CustomEditText extends EditText {

	private long DELAY_TIME = 25;
	private CharSequence text;
	private int TEXTINDEX;
	private Handler mHandler = new Handler();
	private boolean TASK_DONE = true;
	private onSetCallBack mCallBack;
	private int startIndex = 0;

	private Runnable charAdder = new Runnable() {

		@Override
		public void run() {

			setText(text.subSequence(startIndex, TEXTINDEX));
			setSelection(TEXTINDEX);

			if (mCallBack != null && text.length() > 0
					&& TEXTINDEX > text.length()) {
				mCallBack.onCharacterTyped(text.charAt(TEXTINDEX));
			}
			TEXTINDEX++;
			if (TEXTINDEX <= text.length()) {

				mHandler.postDelayed(charAdder, DELAY_TIME);

			} else {

				TASK_DONE = true;
				if (mCallBack != null) {
					mCallBack.onAnimationFinished();
				}

			}

		}
	};

	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);

	}

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public CustomEditText(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {

		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
		setRawInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		setCursorVisible(false);

		if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			setBackgroundDrawable(null);
		} else {
			setBackground(null);
		}
		if (attrs != null) {

			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.MyTextView);
			String fontName = a.getString(R.styleable.MyTextView_fontName);
			if (fontName != null) {

				Typeface myTypeface = Typeface.createFromAsset(getContext()
						.getAssets(), "fonts/" + fontName);
				setTypeface(myTypeface);

			}
			a.recycle();

		}

	}

	public void AnimateText(CharSequence charText) {

		this.text = charText;
		TEXTINDEX = 0;
		TASK_DONE = false;
		getText().clear();

		requestFocus();

		mHandler.removeCallbacks(charAdder);
		mHandler.postDelayed(charAdder, DELAY_TIME);

	}

	public boolean isAnimationDone() {
		return TASK_DONE;
	}

	public void setCharacterDelay(long millis) {
		DELAY_TIME = millis;
	}

	public void setCallBack(onSetCallBack callback) {
		this.mCallBack = callback;
	}

	public interface onSetCallBack {

		public void onAnimationFinished();

		public void onCharacterTyped(char Character);

	}

}
