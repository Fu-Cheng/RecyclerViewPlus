package com.chengfu.recyclerviewplus.loadmore;

import com.chengfu.recyclerviewplus.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 默认加载更多View
 *
 * @author ChengFu
 *
 */
public class DefaultLoadMoreView extends LinearLayout implements ILoadMoreView {


	private ProgressBar progressBar;
	private TextView textView;
	private View reLoad;

	public DefaultLoadMoreView(Context context) {
		super(context);
		initView(context,null,0);
	}

	public DefaultLoadMoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context,attrs,0);
	}

	public DefaultLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context,attrs,defStyleAttr);
	}

	private void initView(Context context, AttributeSet attrs, int defStyleAttr){
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER);

		progressBar=new ProgressBar(context, attrs, android.R.attr.progressBarStyleSmall);
		reLoad=new View(context, attrs, defStyleAttr);
		textView=new TextView(context, attrs, defStyleAttr);

		textView.setText(context.getString(R.string.loadmore_loading_hint));
		reLoad.setBackgroundResource(R.drawable.ic_refresh_black);


		addView(progressBar,new LinearLayout.LayoutParams(100, 100));
		addView(reLoad,new LinearLayout.LayoutParams(100, 100));
		addView(textView);

		show();
	}

	@Override
	public void show() {
		textView.setText(getContext().getString(R.string.loadmore_loading_hint));
		setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		reLoad.setVisibility(View.GONE);
	}

	@Override
	public void hide() {
		setVisibility(View.GONE);
	}

	@Override
	public void loadFailed() {
		textView.setText(getContext().getString(R.string.loadmore_failed_hint));
		progressBar.setVisibility(View.GONE);
		reLoad.setVisibility(View.VISIBLE);
	}

}
