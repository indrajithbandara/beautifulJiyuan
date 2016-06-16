package lzh.ys;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.InflaterInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.okhttp.internal.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.GetCallback;
import lzh.ys.beans.Scenic_Detail_Bean;
import lzh.ys.beans.Scenic_General_Beans;
import lzh.ys.fragments.Scenic_Detail;
import lzh.ys.fragments.Scenic_General;
import lzh.ys.fragments.Scenic_List;
import lzh.ys.utils.Utils;

public class HintActivity extends FragmentActivity {
	FragmentManager fm;
	FragmentTransaction ft;
	private String[] scenics = null;
	private AnimationDrawable animationDrawable;
	Handler handler = new Handler();
	private TextView titleTv;
	List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
	int list_length;
	List<String> nextId;
	Scenic_General general_fragment;
	Scenic_Detail detail_fragment;
	ProgressDialog dialog;
	private MediaPlayer player;
	private Timer timer;
	private Button timerBtn;
	TimerTask task;

	@SuppressWarnings("rawtypes")
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_dialog);
		titleTv = (TextView) findViewById(R.id.activity_title);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		fm = this.getSupportFragmentManager();
		startAnim();
		player = new MediaPlayer();
		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				if (timerBtn != null) {
					timerBtn.setText("播放语音");
					task.cancel();
				}

			}
		});

		scenics = getResources().getStringArray(R.array.scenics);
		String scenic_name = scenics[getIntent().getIntExtra("value", 1) - 1];
		Toast.makeText(this, scenic_name, Toast.LENGTH_SHORT).show();
		titleTv.setText(scenic_name);
		timer = new Timer();
		// 下载数据加载之Fragment
		BmobQuery query = new BmobQuery(Utils.SCENIC_GENERAL_TABNAME);
		query.addWhereEqualTo("name", scenic_name);

		query.findObjects(this, new FindCallback() {
			@SuppressLint("NewApi")
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(HintActivity.this);
				builder.setMessage(arg1);
				builder.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub
						HintActivity.this.finish();
					}
				});
				builder.show();
			}

			@Override
			public void onSuccess(JSONArray array) {
				// TODO Auto-generated method stub
				Scenic_General_Beans beans = new Scenic_General_Beans();
				String objectid = null;
				try {
					JSONObject object = array.getJSONObject(0);
					beans.setAddress(object.getString("location"));
					beans.setPhone(object.getString("phone"));
					beans.setPrice(object.getString("price"));
					objectid = object.getString("objectId");
					ArrayList<String> list = beans.getImg_lists();
					list.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic1").getString("url"));
					list.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic2").getString("url"));
					list.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic3").getString("url"));
					list.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic4").getString("url"));
					list.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic5").getString("url"));

				} catch (JSONException e) {

					e.printStackTrace();
				}
				if (beans.getImg_lists().size() > 0)
					;
				else {
					Toast.makeText(getApplicationContext(), "该景点资料未完成输入，不可查看", Toast.LENGTH_SHORT).show();
					finish();
				}
				stopAnim();
				try {
					general_fragment = new Scenic_General(beans, objectid);
					ft = fm.beginTransaction();
					ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

					ft.replace(R.id.hint_linear, general_fragment);
					ft.commit();
				} catch (Exception exception) {

				}
			}
		});

	}

	// 点击收听按钮所对应的启动景点列表页面
	public void listenMusic(String value) {
		// empty_Fragment = new Empty_Fragment();
		// ft = fm.beginTransaction();
		// ft.replace(R.id.hint_linear, empty_Fragment);
		// ft.addToBackStack(null);
		// ft.commit();
		// startAnim();
		dialog = ProgressDialog.show(this, null, "加载中。。。");
		nextId = new ArrayList<String>();
		BmobQuery query = new BmobQuery("Scenic_List");
		query.addWhereEqualTo("parent", value);
		query.findObjects(this, new FindCallback() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(HintActivity.this);
				builder.setMessage(arg1);

				builder.show();
			}

			@Override
			public void onSuccess(JSONArray array) {
				list_length = array.length();
				nextId.clear();
				lists.clear();
				for (int i = 0; i < array.length(); i++) {
					try {
						final JSONObject object = array.getJSONObject(i);
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									DownloadBitmap(
											Utils.BMOB_File_BASE_URL + object.getJSONObject("picture").getString("url"),
											object.getString("name"),
											object.getJSONObject("detail").getString("objectId"));
								} catch (MalformedURLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JSONException e) {
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
	// 播放动画

	private void startAnim() {
		findViewById(R.id.anim_linear).setVisibility(View.VISIBLE);

		animationDrawable = (AnimationDrawable) findViewById(R.id.load_img).getBackground();
		animationDrawable.start();
	}
	// 停止动画

	private void stopAnim() {
		findViewById(R.id.anim_linear).setVisibility(View.GONE);
		animationDrawable.stop();
	}

	public void onBtnBack(View v) {
		onBackPressed();
	}

	// 下载图片
	private void DownloadBitmap(String url, String text, String next) throws MalformedURLException, IOException {
		InputStream inputStream = new URL(url).openStream();
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("text", text);
		hashMap.put("img", bitmap);
		hashMap.put("next", next);
		lists.add(hashMap);
		if (list_length == lists.size()) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					stopAnim();
					dialog.dismiss();
					Scenic_List general = new Scenic_List(lists, nextId);
					ft = fm.beginTransaction();
					ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

					ft.replace(R.id.hint_linear, general);
					ft.addToBackStack(null);
					ft.commit();

				}
			});

		}
	}

	public void seeDetail(final int index) {
		String id = (String) lists.get(index).get("next");
		// dialog = ProgressDialog.show(this, null, "加载中。。。");
		if (player != null && player.isPlaying()) {
			player.stop();
			player.release();

		}
		@SuppressWarnings("rawtypes")
		BmobQuery bmobQuery = new BmobQuery("Scenic_Detail");
		bmobQuery.getObject(this, id, new GetCallback() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(final JSONObject object) {
				final Scenic_Detail_Bean bean = new Scenic_Detail_Bean();
				// Toast.makeText(HintActivity.this, object.toString(),
				// Toast.LENGTH_LONG).show();

				try {
					bean.setDescription(object.getString("description"));
					bean.setName(object.getString("name"));

					bean.setmUrl(Utils.BMOB_File_BASE_URL + object.getJSONObject("music").getString("url"));
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								player = new MediaPlayer();
								player.setDataSource(HintActivity.this, Uri.parse(bean.getmUrl()));

								player.prepare();
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}).start();

					if (index < lists.size() - 1)
						bean.setNext(index + 1);
					else
						bean.setNext(-1);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Bitmap bitmap_t;
							try {
								bitmap_t = BitmapFactory.decodeStream(new URL(
										Utils.BMOB_File_BASE_URL + object.getJSONObject("pic_t").getString("url"))
												.openStream());

								bean.setUrl_t(bitmap_t);

								List<String> list = bean.getUrl_list();
								list.clear();
								list.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic1").getString("url"));
								list.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic2").getString("url"));
								list.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic3").getString("url"));
								list.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic4").getString("url"));
								list.add(Utils.BMOB_File_BASE_URL + object.getJSONObject("pic5").getString("url"));
								bean.setUrl_list(list);

							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (Exception e) {
								// TODO: handle exception
							}

							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// dialog.dismiss();
									try {
										detail_fragment.setData(bean);

									} catch (Exception exception) {

									}
								}
							});

						}
					}).start();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (detail_fragment == null || !detail_fragment.isAdded()) {
					detail_fragment = new Scenic_Detail();

					ft = fm.beginTransaction();
					ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

					ft.addToBackStack(null);
					ft.replace(R.id.hint_linear, detail_fragment);
					ft.commit();
				} else {
					try {
						detail_fragment.setData(bean);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});

	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();
		if (player != null && player.isPlaying()) {
			task.cancel();
			player.stop();
			player.release();
			player = null;

		}
	}

	public void switchPlayer(View btn) {
		if (timerBtn == null) {
			timerBtn = (Button) btn;
		}
		if (player != null) {
			if (player.isPlaying()) {
				player.pause();

				task.cancel();
				((Button) btn).setText("播放语音");

			} else {

				player.start();
				task = null;
				task = new TimerTask() {

					@Override
					public void run() {
						timerBtn.post(new Runnable() {

							@Override
							public void run() {
								int mills = player.getDuration() - player.getCurrentPosition();
								int minute = mills / 60000;
								int mill = mills % 60000 / 1000;

								timerBtn.setText("" + minute + ":" + mill);
							}
						});
					}
				};
				timer.schedule(task, 1000, 1000);
			}
		}
	}
}
