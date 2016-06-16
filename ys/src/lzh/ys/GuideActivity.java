package lzh.ys;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.print.PrintAttributes.Margins;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import lzh.ys.adapters.GuideAdapter;

public class GuideActivity extends Activity implements OnClickListener {
	private ViewPager viewpager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		ImageView imageView = new ImageView(this);
		imageView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		imageView.setImageResource(R.drawable.guide1);
		imageView.setScaleType(ScaleType.FIT_XY);

		RelativeLayout layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		layout.addView(imageView);

		Button button = new Button(this);
		button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
 		button.setPadding(PxToDp(20), PxToDp(20), PxToDp(20), PxToDp(120));
		button.setTextSize(PxToDp(40));
		button.setBackgroundResource(R.drawable.im_experice);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		layoutParams.setMargins(PxToDp(20), PxToDp(20), PxToDp(20), PxToDp(150));
		button.setOnClickListener(this);
		layout.addView(button, layoutParams);
		List<View> list = new ArrayList<View>();

		list.add(layout);
		GuideAdapter adapter = new GuideAdapter(list);
		viewpager.setAdapter(adapter);
	}

	private int PxToDp(int px) {
		float scale = getResources().getDisplayMetrics().density;
		return (int) (px / scale);
	}

	@Override
	public void onClick(View v) {

		startActivity(new Intent(GuideActivity.this, MainHost.class));
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		finish();
	}
}
