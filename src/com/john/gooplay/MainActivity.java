package com.john.gooplay;


import Fragment.AppFragment;
import Fragment.FragmentFactory;
import Fragment.HomeFragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends BaseActivity implements OnQueryTextListener{

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private ViewPager mViewPager;
	private String[] tab_names;
	@Override
	protected void initActionBar() {
		 /**
         * 给actionbar添加tab
         */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		drawerToggle = new ActionBarDrawerToggle(this,
				mDrawerLayout, R.drawable.ic_drawer_am, R.string.open_drawer,
				R.string.close_drawer){

					@Override
					public void onDrawerClosed(View drawerView) {
						super.onDrawerClosed(drawerView);
						Toast.makeText(getApplicationContext(), "抽屉关闭了", 0).show();
					}

					@Override
					public void onDrawerOpened(View drawerView) {
						super.onDrawerOpened(drawerView);
						Toast.makeText(getApplicationContext(), "抽屉打开了", 0).show();
					}
			
		};
		mDrawerLayout.setDrawerListener(drawerToggle);
		//  让开关和actionbar建立关系 
		drawerToggle.syncState();
	}

	@Override
	protected void initView() {
		 setContentView(R.layout.activity_main);
	      mDrawerLayout = (DrawerLayout) findViewById(R.id.dl);
	      mViewPager = (ViewPager) findViewById(R.id.viewPager);
	      mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
	}
	
	@Override
	protected void init() {
		tab_names = getResources().getStringArray(R.array.tab_names);
	}
    
    
    public class MyFragmentAdapter  extends FragmentStatePagerAdapter{

		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return FragmentFactory.createFragment(position);
		}

		@Override
		public int getCount() {
			return tab_names.length;
		}
		
		//返回每个条目的标题
		@Override
		public CharSequence getPageTitle(int position) {
			return tab_names[position];
		}
		
    }


    //菜单创建的时候调用
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        
			SearchView searchView = (SearchView) menu.findItem(
					R.id.action_search).getActionView();
			searchView.setOnQueryTextListener(this);//设置文字输入框文字监听
        return true;
    }

    //菜单条目的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return drawerToggle.onOptionsItemSelected(item)|super.onOptionsItemSelected(item);
    }
    
    

	@Override
	public boolean onQueryTextChange(String arg0) {
		Toast.makeText(this,arg0, 0).show();
		return false;
	}
	

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		
		return false;
	}
    
 
    
}
