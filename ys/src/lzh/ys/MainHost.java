package lzh.ys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

public class MainHost extends TabActivity {
	private TabHost tabHost;
	private TabWidget mTabWidget;
	private FrameLayout mFrameLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 锟斤拷锟絋abActivity锟斤拷锟叫碉拷TabHost锟斤拷锟斤拷
		tabHost = getTabHost();
		// 透锟斤拷状态锟斤拷
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		addTab("act1", getString(R.string.tab_arscan), ARScanActivity.class, 1);
		 addTab("act3", getString(R.string.tab_search), SearchActivity.class, 2);
		addTab("act2", getString(R.string.tab_function), FunctionActivity.class, 3);
		movetabBottom();
		setContentView(tabHost);
 	}

	private void movetabBottom() {

		// 锟斤拷锟絋abActivity锟叫碉拷TabWidget锟斤拷锟斤拷
		mTabWidget = getTabWidget();
		// 锟斤拷锟紽rameLayout锟斤拷锟斤拷锟斤拷实锟斤拷锟角憋拷签锟叫伙拷锟斤拷锟斤拷锟捷诧拷锟街碉拷锟斤拷悴硷拷侄锟斤拷锟�
		mFrameLayout = tabHost.getTabContentView();
		mTabWidget.setDividerDrawable(null);
		// 锟斤拷签锟叫憋拷锟斤拷锟斤拷锟紽rameLayout锟斤拷实锟角凤拷锟斤拷一锟斤拷LinearLayout锟叫碉拷
		// 默锟斤拷锟斤拷锟斤拷卤锟角╋拷斜锟斤拷诳锟绞嘉伙拷锟�
		LinearLayout layout = (LinearLayout) tabHost.getChildAt(0);
		// 锟斤拷锟斤拷锟斤拷愿谋锟斤拷签锟叫憋拷谋锟斤拷锟斤拷锟斤拷锟揭拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷签锟叫憋拷谋锟斤拷锟斤拷锟斤拷锟侥憋拷
		// layout.setBackgroundColor(getResources().getColor(R.color.tab_background));
		// 锟斤拷帽锟角╋拷斜锟斤拷UI锟斤拷锟斤拷
		View view = layout.getChildAt(0);
		// 锟斤拷锟斤拷签锟叫憋拷锟斤拷螅锟斤拷锟斤拷圆锟斤拷锟斤拷锟斤拷瞥锟�
		layout.removeViewAt(0);
		// 锟斤拷锟斤拷签锟叫憋拷锟劫达拷追锟接碉拷锟斤拷锟皆诧拷锟斤拷锟斤拷
		// 锟斤拷锟斤拷锟侥伙拷锟斤拷锟斤拷签锟叫憋拷捅锟角╋拷锟斤拷莸锟紽rameLayout锟斤拷位锟矫撅拷锟斤拷锟剿斤拷锟斤拷
		// 锟斤拷锟斤拷示锟斤拷锟斤拷锟侥撅拷锟角憋拷签锟斤拷锟斤拷锟矫碉拷锟剿斤拷锟斤拷牡撞锟�
		layout.addView(view);

	}

	private void addTab(String tag, String title, Class clazz, int index) {
		TabSpec tabSpec = tabHost.newTabSpec(tag);

		View v = LayoutInflater.from(this).inflate(R.layout.mainhostradiobutton, null);
		ImageView tab_img = (ImageView) v.findViewById(R.id.tab_img);
		TextView tab_txt = (TextView) v.findViewById(R.id.tab_txt);
		switch (index) {
		case 1:
			tab_img.setImageResource(R.drawable.tab_ar_select);

			tab_txt.setText(getString(R.string.tab_arscan));
			break;
		case 2:
			tab_img.setImageResource(R.drawable.tab_search_select);

			tab_txt.setText(getString(R.string.tab_search));
			break;
		case 3:
			tab_img.setImageResource(R.drawable.tab_function_select);

			tab_txt.setText(getString(R.string.tab_function));
			break;

		}

		tabSpec.setIndicator(v);

		// tabSpec.setIndicator(title,
		// getResources().getDrawable(R.drawable.arscan_ico));
		Intent intent = new Intent(getApplicationContext(), clazz);
		tabSpec.setContent(intent);
		tabHost.addTab(tabSpec);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		moveTaskToBack(true);
	}


}
