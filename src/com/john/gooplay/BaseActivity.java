package com.john.gooplay;

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity{

	//管理运行的所有activity
	public static final List<BaseActivity> mActivities = new LinkedList<BaseActivity>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		synchronized (mActivities) {
			mActivities.add(this);
		}
		init();
		initView();
		initActionBar();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		synchronized (mActivities) {
		mActivities.remove(this);
		}
	}
	
	public void killAll() {
		List<BaseActivity> copy;
		synchronized (mActivities) {
			copy = new LinkedList<BaseActivity>();
			}
		for (BaseActivity activity : copy) {
			activity.finish();
		}
	}

	protected void initActionBar() {
		
	}

	protected void initView() {
		
	}

	protected void init() {
		
	}
	
	
	
	
}
