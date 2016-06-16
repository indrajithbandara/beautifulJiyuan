package lzh.ys;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.impl.client.EntityEnclosingRequestWrapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.metaio.sdk.jni.IRadar;
import com.squareup.okhttp.internal.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import lzh.ys.utils.Utils;
import android.widget.TextView.OnEditorActionListener;

public class SearchDetail extends Activity {
	private ListView list;
	private EditText edit;
	private ArrayList<HashMap<String, Object>> datalists;
	private int isDownOK = 0;
	private String[] tmpKey;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchlist);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		tmpKey = getResources().getStringArray(R.array.scenics);
		list = (ListView) findViewById(R.id.search_result_list);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				TextView tv = (TextView) arg1.findViewById(R.id.search_txt_list);
				String text = tv.getText().toString();
				if (text.contains("王屋山")) {
					Intent intent = new Intent(SearchDetail.this, HintActivity.class);
					intent.putExtra("value", 1);
					startActivity(intent);
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				} else if (text.contains("五龙口")) {
					Intent intent = new Intent(SearchDetail.this, HintActivity.class);
					intent.putExtra("value", 2);
					startActivity(intent);
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				} else if (text.contains("世纪")) {
					Intent intent = new Intent(SearchDetail.this, HotelDetail.class);

					startActivity(intent);
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				} else {
					Toast.makeText(SearchDetail.this, "暂无详情页", Toast.LENGTH_SHORT).show();
				}
			}
		});
		edit = (EditText) findViewById(R.id.s_search_edit);
		datalists = new ArrayList<HashMap<String, Object>>();
		edit.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				String keyword = v.getText().toString();

				if (actionId == EditorInfo.IME_ACTION_SEARCH && !keyword.equals("")) {
					findDataList(keyword);
					return true;
				}
				return false;
			}

		});
	}

	@SuppressWarnings("rawtypes")
	private void findDataList(String keyword) {
		// 隐藏输入法
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
		dialog = ProgressDialog.show(this, null, "加载中");
		BmobQuery query = new BmobQuery("Scenic_General");
		query.addWhereContains("name", keyword);
		query.findObjects(this, new FindCallback() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(final JSONArray array) {
				// TODO Auto-generated method stub
				datalists.clear();
				isDownOK = array.length();
				if (isDownOK == 0) {
					Toast.makeText(SearchDetail.this, "没有找到结果", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
				for (int i = 0; i < array.length(); i++) {
					try {

						final JSONObject object = array.getJSONObject(i);

						new Thread(new Runnable() {

							@Override
							public void run() {
								try {

									setlistData(object.getString("name"),
											Utils.BMOB_File_BASE_URL + object.getJSONObject("pic1").getString("url"));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (MalformedURLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}).start();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

	}

	private void setlistData(String txt, String url) throws MalformedURLException, IOException {

		Bitmap bitmap = BitmapFactory.decodeStream(new URL(url).openStream());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("txt", txt);
		map.put("img", bitmap);
		datalists.add(map);
		Log.i("json", "download is ok" + isDownOK);

		if (--isDownOK <= 0) {
			Log.i("json", "download is ok" + isDownOK);

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					dialog.dismiss();
					SimpleAdapter adapter = new SimpleAdapter(SearchDetail.this, datalists, R.layout.search_list_items,
							new String[] { "img", "txt" }, new int[] { R.id.search_img_list, R.id.search_txt_list });
					adapter.setViewBinder(new ViewBinder() {

						@Override
						public boolean setViewValue(View view, Object data, String textRepresentation) {
							if (view instanceof ImageView && data instanceof Bitmap) {
								ImageView imageView = (ImageView) view;
								imageView.setImageBitmap((Bitmap) data);
								return true;
							}
							return false;
						}
					});

					list.setAdapter(adapter);

				}
			});

		}
	}

	public void onBtnBack(View v) {
		finish();
	}
}
