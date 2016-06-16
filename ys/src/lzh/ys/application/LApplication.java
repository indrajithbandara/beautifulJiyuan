package lzh.ys.application;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class LApplication extends Application {
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		Log.i("xiaohong", newConfig.toString());
	}
}
