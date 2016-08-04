package com.john.gooplay;

import android.support.v7.app.ActionBar;

public class DetailActivity extends BaseActivity{

	@Override
	protected void initActionBar() {
		ActionBar actionBar = getSupportActionBar();//拿到actionbar对象
		actionBar.setDisplayHomeAsUpEnabled(true);//设置返回按钮
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_detail);
	}

	
	
	
}
