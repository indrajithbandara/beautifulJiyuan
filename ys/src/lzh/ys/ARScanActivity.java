package lzh.ys;

import java.io.File;

import com.metaio.sdk.ARViewActivity;
import com.metaio.sdk.MetaioDebug;
import com.metaio.sdk.jni.IGeometry;
import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.TrackingValues;
import com.metaio.sdk.jni.TrackingValuesVector;
import com.metaio.tools.io.AssetsManager;
import com.squareup.okhttp.internal.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import lzh.ys.utils.Utils;

public class ARScanActivity extends ARViewActivity {
	private IGeometry mMetaioMan;
	File trackingConfigFile;
	ProgressDialog dialog;
	private MetaioSDKCallbackHandler mCallbackHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog = ProgressDialog.show(this, null, "准备数据中。。。");
		mCallbackHandler = new MetaioSDKCallbackHandler();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mCallbackHandler.delete();
		mCallbackHandler = null;
	}

	@Override
	protected int getGUILayout() {
		// TODO Auto-generated method stub
		return R.layout.arscan;
	}

	@Override
	protected IMetaioSDKCallback getMetaioSDKCallbackHandler() {
		// TODO Auto-generated method stub
		return mCallbackHandler;
	}

	@Override
	protected void loadContents() {
		// TODO Auto-generated method stub
		try {
			trackingConfigFile = new File(Environment.getExternalStorageDirectory() + "/config.xml");
			// Load the desired tracking configuration
			// trackingConfigFile =
			// AssetsManager.getAssetPathAsFile(getApplicationContext(),
			// "config.xml");
			Log.i("1212", "" + (trackingConfigFile.exists()));
			final boolean result = metaioSDK.setTrackingConfiguration(trackingConfigFile);
			MetaioDebug.log("Tracking configuration loaded: " + result);

			// Load all the geometries. First - Model
			// final File metaioManModel =
			// AssetsManager.getAssetPathAsFile(getApplicationContext(),
			// "/assets/metaioman.md2");
			File metaioManModel = new File(Environment.getExternalStorageDirectory() + "/metaioman.md2");
			if (metaioManModel != null) {
				mMetaioMan = metaioSDK.createGeometry(metaioManModel);
				if (mMetaioMan != null) {
					// Set geometry properties
					mMetaioMan.setScale(4f);
					mMetaioMan.setVisible(false);
					MetaioDebug.log("Loaded geometry " + metaioManModel);
				} else
					MetaioDebug.log(Log.ERROR, "Error loading geometry: " + metaioManModel);
			}

		} catch (Exception e) {
			MetaioDebug.log(Log.ERROR, "Error loading contents!");
			MetaioDebug.printStackTrace(Log.ERROR, e);
			Toast.makeText(getApplicationContext(), e.toString(), 0).show();
		}
	}

	@Override
	protected void onGeometryTouched(IGeometry geometry) {
		// TODO Auto-generated method stub

	}

	final class MetaioSDKCallbackHandler extends IMetaioSDKCallback {

		@Override
		public void onSDKReady() {
			// show GUI
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// mGUIView.setVisibility(View.VISIBLE);
					dialog.dismiss();
				}
			});
		}

		@Override
		public void onTrackingEvent(TrackingValuesVector trackingValues) {
			// if we detect any target, we bind the loaded geometry to this
			// target
			if (mMetaioMan != null) {
				for (int i = 0; i < trackingValues.size(); i++) {
					final TrackingValues tv = trackingValues.get(i);
					final int id = tv.getCoordinateSystemID();
					if (tv.isTrackingState()) {
						mMetaioMan.setCoordinateSystemID(tv.getCoordinateSystemID());
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// Utils.getPlayer(ARScanActivity.this).start();
								if (id != 0) {
									Intent intent = new Intent(ARScanActivity.this, HintActivity.class);
									intent.putExtra("value", id);
									startActivity(intent);
									overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
								}

							}
						});
						break;
					}
				}
			}

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
}
