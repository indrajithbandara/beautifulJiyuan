package lzh.ys;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.GetCallback;
import lzh.ys.fragments.LunBo_Fragment;
import lzh.ys.utils.Utils;
import lzh.ys.views.ImageCycleView;
import lzh.ys.views.ImageCycleView.ImageCycleViewListener;

public class SearchActivity extends FragmentActivity implements OnClickListener {
	private ArrayList<String> imageUrls = new ArrayList<String>();
	ProgressDialog dialog;
	private LinearLayout scenic_1;
	private LinearLayout scenic_2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		InitViews();
		InitDatas();

	}

	private void InitDatas() {
		@SuppressWarnings("rawtypes")
		BmobQuery query = new BmobQuery("Search_LunBo");
		query.getObject(this, "PmDi6667", new GetCallback() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(JSONObject object) {
				try {
					ImageView imageView = new ImageView(SearchActivity.this);
					imageView.setImageResource(R.drawable.hotel_01);
					imageView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
					imageUrls.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic1").getString("url"));
					imageUrls.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic2").getString("url"));
					imageUrls.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic3").getString("url"));
					imageUrls.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic4").getString("url"));
					imageUrls.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic5").getString("url"));
					try {

						getSupportFragmentManager().beginTransaction()
								.replace(R.id.search_imageviews, new LunBo_Fragment(imageUrls)).commit();
					} catch (Exception e) {
						Toast.makeText(SearchActivity.this, "轮播图加载异常", Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	private void InitViews() {
		scenic_1 = (LinearLayout) findViewById(R.id.scenics_linear_1);
		scenic_2 = (LinearLayout) findViewById(R.id.scenics_linear_2);
		scenic_1.setOnClickListener(this);
		scenic_2.setOnClickListener(this);

	}

	// 打开搜索Activity
	public void searchMethod(View v) {

		startActivity(new Intent(SearchActivity.this, SearchDetail.class));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.scenics_linear_1:
			Intent intent = new Intent(SearchActivity.this, HintActivity.class);
			intent.putExtra("value", 1);
			startActivity(intent);
			overridePendingTransition(R.anim.scale_translate_rotate, R.anim.my_alpha_action);
			break;
		case R.id.scenics_linear_2:
			Intent intent2 = new Intent(SearchActivity.this, HintActivity.class);
			intent2.putExtra("value", 2);
			startActivity(intent2);
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			break;
		default:
			break;
		}

	}

	@Override
	public void onBackPressed() {
		
		if (System.currentTimeMillis() - Utils.mExitTime > 2000) {
			Toast.makeText(this, "再按一次退出！", Toast.LENGTH_SHORT).show();
			Utils.mExitTime = System.currentTimeMillis();
		} else

			super.onBackPressed();
	}

	public void shijidian(View v) {
		startActivity(new Intent(SearchActivity.this, HotelDetail.class));
	}
}
