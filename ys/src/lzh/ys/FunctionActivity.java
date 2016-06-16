package lzh.ys;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import lzh.ys.utils.Utils;

public class FunctionActivity extends Activity {
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funcitonactivity);
		list = (ListView) findViewById(R.id.function_list);
		list.setAdapter(new ArrayAdapter<String>(this, R.layout.function_item,
				Arrays.asList(getResources().getStringArray(R.array.functionarray))));
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0) {
					startActivity(new Intent(FunctionActivity.this, SearchActivity.class));
					overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - Utils.mExitTime > 2000) {
			Toast.makeText(this, "再按一次退出！", Toast.LENGTH_SHORT).show();
			Utils.mExitTime = System.currentTimeMillis();
		} else

			super.onBackPressed();
	}
}
