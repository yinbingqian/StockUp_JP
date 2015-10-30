package com.sxit.activity.adviser.th;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lnpdit.lntv.tradingtime.R;
import com.sxit.activity.adviser.th.item.InteractionMsg_Activity;
import com.sxit.activity.adviser.th.item.PublishMsgList_Activity;
import com.sxit.activity.adviser.th.item.QuestionList_Activity;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.th.TabHostAbstractActivity;
import com.sxit.activity.th.TabHostItem;
import com.sxit.activity.th.item.AMyZone_Activity;
import com.sxit.activity.th.item.Analyst_Activity;
import com.sxit.activity.th.item.AskQuestion_Activity;
import com.sxit.activity.th.item.Information_Activity;
import com.sxit.activity.th.item.MyZone_Activity;

public class AMainTabHostActivity extends TabHostAbstractActivity {

	public static List<TabHostItem> mItems;
	private List<ImageView> myList = new ArrayList();
	private int locationCur;

	/**
	 * 在初始化TabWidget前调用 和TabWidget有关的必须在这里初始化
	 */
	@Override
	protected void prepare() {
		TabHostItem info = new TabHostItem(getString(R.string.tabHost_frist_a), // title
				R.drawable.tab_icon_1_n, // icon
				R.drawable.maintab_toolbar_bg, // background
				new Intent(this, InteractionMsg_Activity.class)); // intent

		TabHostItem analyst = new TabHostItem(getString(R.string.tabHost_second_a), R.drawable.tab_icon_2_u, R.drawable.maintab_toolbar_bg, new Intent(this, PublishMsgList_Activity.class));

		TabHostItem ask = new TabHostItem(getString(R.string.tabHost_thrid_a), R.drawable.tab_icon_3_u, R.drawable.maintab_toolbar_bg, new Intent(this, QuestionList_Activity.class));

		TabHostItem myzone = new TabHostItem(getString(R.string.tabHost_fourth_a), R.drawable.tab_icon_4_u, R.drawable.maintab_toolbar_bg, new Intent(this, AMyZone_Activity.class));

		mItems = new ArrayList<TabHostItem>();
		mItems.add(info);
		mItems.add(analyst);
		mItems.add(ask);
		mItems.add(myzone);

		// 设置分割线
		// TabWidget tabWidget = getTabWidget();
		// tabWidget.setDividerDrawable(R.drawable.tabhost_divider);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication myApplication = MyApplication.getInstance();
		myApplication.aMainTabHostActivity = this;
		Intent intent = getIntent();
		locationCur = intent.getIntExtra("locationCur", 0);
		switch (locationCur) {
		case 1:
			restore(0);
			myList.get(3).setBackgroundResource(R.drawable.tab_icon_4_n);
			setCurrentTab(3);
			break;
		case 0:
			setCurrentTab(0);
			break;
		default:
			setCurrentTab(0);
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 自定义点击三颜色切换，文字(textView)颜色变化写在XML中，背景(ll)颜色变化写在XML 图片(imgTabItem)颜色变化写在代码中，position对应位置
	 */
	@Override
	protected void setTabItemTextView(TextView textView, final ImageView imgTabItem, LinearLayout ll, final int position) {
		// 可调整图片太靠顶问题
		// textView.setPadding(3, 8, 3, 3);
		textView.setText(mItems.get(position).getTitle());
		// textView.setBackgroundResource(mItems.get(position).getBg());
		ll.setBackgroundResource(mItems.get(position).getBg());
		// textView.setCompoundDrawablesWithIntrinsicBounds(0,
		// mItems.get(position).getIcon(), 0, 0);
		imgTabItem.setBackgroundResource(mItems.get(position).getIcon());
		myList.add(imgTabItem);
		switch (position) {
		case 0:
			ll.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						imgTabItem.setBackgroundResource(R.drawable.tab_icon_1_u);
					} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
						imgTabItem.setBackgroundResource(R.drawable.tab_icon_1_u);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						for (int i = 0; i < myList.size(); i++) {
							if (i != position) {
								restore(i);
							}
						}
						imgTabItem.setBackgroundResource(R.drawable.tab_icon_1_n);
						setCurrentTab(position);
					}
					return true;
				}
			});
			break;
		case 1:
			ll.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						imgTabItem.setBackgroundResource(R.drawable.tab_icon_2_u);
					} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
						imgTabItem.setBackgroundResource(R.drawable.tab_icon_2_u);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						for (int i = 0; i < myList.size(); i++) {
							if (i != position) {
								restore(i);
							}
						}
						imgTabItem.setBackgroundResource(R.drawable.tab_icon_2_n);
						setCurrentTab(1);
					}
					return true;
				}
			});
			break;
		case 2:
			ll.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						imgTabItem.setBackgroundResource(R.drawable.tab_icon_3_u);
					} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
						imgTabItem.setBackgroundResource(R.drawable.tab_icon_3_u);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						for (int i = 0; i < myList.size(); i++) {
							if (i != position) {
								restore(i);
							}
						}
						imgTabItem.setBackgroundResource(R.drawable.tab_icon_3_n);
						setCurrentTab(position);
					}
					return true;
				}
			});
			break;
		case 3:
			ll.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						imgTabItem.setBackgroundResource(R.drawable.tab_icon_4_u);
					} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
						imgTabItem.setBackgroundResource(R.drawable.tab_icon_4_u);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						for (int i = 0; i < myList.size(); i++) {
							if (i != position) {
								restore(i);
							}
						}
						imgTabItem.setBackgroundResource(R.drawable.tab_icon_4_n);
						setCurrentTab(position);
					}
					return true;
				}
			});
			break;
		default:
			break;
		}
	}

	/**
	 * 重置颜色
	 * 
	 * @param position
	 */
	public void restore(int position) {
		switch (position) {
		case 0:
			myList.get(position).setBackgroundResource(R.drawable.tab_icon_1_u);
			break;
		case 1:
			myList.get(position).setBackgroundResource(R.drawable.tab_icon_2_u);
			break;
		case 2:
			myList.get(position).setBackgroundResource(R.drawable.tab_icon_3_u);
			break;
		case 3:
			myList.get(position).setBackgroundResource(R.drawable.tab_icon_4_u);
			break;
		default:
			break;
		}
	}

	/** tab唯一的id */
	@Override
	protected String getTabItemId(int position) {
		return mItems.get(position).getTitle();
	}

	/** 点击tab时触发的事件 */
	@Override
	protected Intent getTabItemIntent(int position) {
		return mItems.get(position).getIntent();
	}

	@Override
	protected int getTabItemCount() {
		return mItems.size();
	}

}
