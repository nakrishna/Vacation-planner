package com.tripplanner.Ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseView extends FragmentActivity {
	protected String mActivityName = null;

	protected void onCreate(Bundle savedInstanceState, String activityName) {
		super.onCreate(savedInstanceState);
		setActivityName(activityName);
		// Global.setCurrentContext(this);
		getWindow().addFlags(
				android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@SuppressWarnings("unused")
	private String getActivityName() {
		return mActivityName;
	}

	private void setActivityName(String activityName) {
		this.mActivityName = activityName;
	}

	/*
	 * public AppStorage app = null; public boolean flg = true; protected void
	 * onStart() { super.onStart(); if(flg){ app = (AppStorage) getApplication();
	 * app.setAppSettings(new AppSettings(this)); flg = false; } init(); }
	 * 
	 * private void init() { // TODO Auto-generated method stub
	 * 
	 * }
	 */

	public void finishActivity() {

		finish();
		super.finish();
		onDestroy();
		super.onDestroy();
	}

	public Context getContext() {

		return this;
	}

	public Activity getActivity() {
		return this;
	}

	public void startNewActivity(Class<? extends BaseView> classname,
			Bundle extras, Boolean flag) {

		Intent newActivity = new Intent(this, classname);
		if (flag) {
			newActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			finish();
		}
		if (extras != null)
			newActivity.putExtras(extras);
		startActivity(newActivity);
	}

	public void startNewActivityForResult(Class<? extends BaseView> classname,
			Bundle extras, int requestCode) {
		Intent newActivity = new Intent(this, classname);
		if (extras != null)
			newActivity.putExtras(extras);
		startActivityForResult(newActivity, requestCode);

	}

}
