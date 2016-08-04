package Fragment;

import java.util.Random;

import tools.ViewUtils;

import com.john.gooplay.R;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	public static final int STATE_UNKOWN = 0;//未知状态
	public static final int STATE_LOADING = 1;//加载状态
	public static final int STATE_ERROR = 2;//错误状态
	public static final int STATE_EMPTY = 3;//空的状态
	public static final int STATE_SUCCESS = 4;//成功状态
	public static int state = STATE_ERROR;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (frameLayout == null) {  
			frameLayout = new FrameLayout(getActivity());
			init(); 
		}else{
			ViewUtils.removeParent(frameLayout);
		}
		show();
		
	
		return frameLayout;  
	}

	private View loadingView;
	private View errorView;
	private View emptyView;
	private View successView;
	private FrameLayout frameLayout;

	
	/*
	 * 添加几种不同的界面到帧布局上
	 */
	private void init() {
		loadingView = createLoadingView();
		if (loadingView != null) {
			frameLayout.addView(loadingView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		errorView = createErrorView();
		if (errorView != null) {
			frameLayout.addView(errorView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		emptyView = createEmptyView(); 
		if (emptyView != null) {
			frameLayout.addView(emptyView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		showPage();
	}

	//根据不同状态显示不同界面
	private void showPage() {
		if (loadingView != null) {
			loadingView.setVisibility(state == STATE_UNKOWN//加载中界面只在未知状态和加载状态下显示，否则不显示
					|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		if (errorView != null) {//错误界面
			errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (emptyView != null) {//加载为空界面
			emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (state == STATE_SUCCESS) {//加载成功界面
			successView = createSuccessView();
			if (successView != null) {
				frameLayout.addView(successView, new FrameLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				successView.setVisibility(View.VISIBLE);
			}
		}
	}

	private View createSuccessView() {
		TextView tv = new TextView(getActivity());
		tv.setText("加载成功");
		tv.setTextSize(30);
		return tv;
	}

	public enum LoadResult {
		error(2), empty(3), success(4);

		int value;

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	private void show() {
		if (state == STATE_ERROR || state == STATE_EMPTY) {
			state = STATE_LOADING;
		}
		new Thread() {
			public void run() {
				SystemClock.sleep(2000);
				final LoadResult result = load();
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (result != null) {
								state = result.getValue();
								showPage(); 
							}
						}
					});
				}
			};
		}.start();
		showPage();

	}

	private LoadResult load() {

		return LoadResult.success;
	}

	private View createEmptyView() {
		View view = View.inflate(getActivity(), R.layout.loadpage_empty, null);
		return view;
	}

	private View createErrorView() {
		View view = View.inflate(getActivity(), R.layout.loadpage_error, null);
		Button page_bt = (Button) view.findViewById(R.id.page_bt);
		page_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show();
			}
		});
		return view;
	}

	private View createLoadingView() {
		View view = View
				.inflate(getActivity(), R.layout.loadpage_loading, null);
		return view;
	}

}
