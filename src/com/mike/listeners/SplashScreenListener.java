package com.mike.listeners;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;

public class SplashScreenListener extends AsyncTask<Void, Integer, Void> {

	public interface TaskFinishedListener {

		void onTaskFinished();

	}

	private final TaskFinishedListener mTaskFinishedListener;
	private final ProgressBar progressBar;
	private int progress = 0;

	private void showProgress() {

		while (progress < 100) {
			progress++;
			publishProgress(progress);
			SystemClock.sleep(50);
		}
	}

	public SplashScreenListener(ProgressBar mProgressBar,
			TaskFinishedListener mTaskFinishedListener) {
		this.progressBar = mProgressBar;
		this.mTaskFinishedListener = mTaskFinishedListener;

	}

	private boolean isResourceExists() {
		// Here you would query your app's internal state to see if this download had been performed before
		// Perhaps once checked save this in a shared preference for speed of access next time
		return true; // returning true so we show the splash every time
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		if(isResourceExists()){
			//Do Stuff
			showProgress();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		mTaskFinishedListener.onTaskFinished();
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		progressBar.setProgress(values[0]);
	}

}
