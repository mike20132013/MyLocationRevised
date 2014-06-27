package com.mike.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mike.customedittext.CustomEditText;
import com.mike.customedittext.CustomEditText.onSetCallBack;
import com.mike.listeners.SplashScreenListener;
import com.mike.listeners.SplashScreenListener.TaskFinishedListener;
import com.mike.mylocationrevised.MainActivity;
import com.mike.mylocationrevised.R;

public class SplashActivity extends Activity implements TaskFinishedListener {

	CustomEditText mCustomEditText;
	ImageView mImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);

		final Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
		
		mCustomEditText = (CustomEditText) findViewById(R.id.splash_customEditText);
		mImageView = (ImageView)findViewById(R.id.splash_imageView1);
		ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.splash_progressBar1);
		mCustomEditText.AnimateText(mCustomEditText.getText().toString());
		mCustomEditText.setCallBack(new onSetCallBack() {

			@Override
			public void onCharacterTyped(char Character) {
				Log.d("TypeWriterView", "onCharacterTyped: " + Character);			
				
			}

			@Override
			public void onAnimationFinished() {
				Log.d("TypeWriterView", "onAnimationEnd");
			}
		});
		
		//mCustomEditText.startAnimation(mAnimation);

		mImageView.startAnimation(mAnimation);
		new SplashScreenListener(mProgressBar, this).execute();

	}

	public void finishSplash() {

		startApp();
		finish();

	}

	public void startApp() {

		Intent startApp = new Intent(this, MainActivity.class);
//		startApp.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//		startApp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startApp);
		finish();

	}

	@Override
	public void onTaskFinished() {
		//finishSplash();
		startApp();
	}

}
