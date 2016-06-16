package lzh.ys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

public class Splash_Activity extends Activity {
	private ImageView Iv_splash;
	private String xmlpath = "http://file.bmob.cn/M02/30/25/oYYBAFYmSh2AZwKxAAAhvEiwZ2Q978.xml";
	private String pic1 = "http://file.bmob.cn/M02/30/25/oYYBAFYmShaAfJCeAAARZr6Beuw022.jpg";
	private String pic2 = "http://file.bmob.cn/M02/2F/EB/oYYBAFYmQwqAZf_hAAAW2pkFnZc832.jpg";
	private String pic3 = "http://file.bmob.cn/M02/30/3F/oYYBAFYmT4SARqWbAAC80XqPjRw598.jpg";
	private String mman = "http://file.bmob.cn/M02/4E/D7/oYYBAFYwPZaAC9ErAALItKRcrZ4695.jpg";
	private String md2 = "http://file.bmob.cn/M02/57/69/oYYBAFYy3LOAWA8yAAEU1JL2weI705.MD2";
	private File xmlfile = new File(Environment.getExternalStorageDirectory() + "/config.xml");
	private File pic1f = new File(Environment.getExternalStorageDirectory() + "/wws.jpg");
	private File pic2f = new File(Environment.getExternalStorageDirectory() + "/xgb.jpg");
	private File pic3f = new File(Environment.getExternalStorageDirectory() + "/nssl.jpg");
	private File md2f = new File(Environment.getExternalStorageDirectory() + "/metaioman.md2");
	private File mmanf = new File(Environment.getExternalStorageDirectory() + "/metaioman.png");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// ²¥·Å¶¯»­
		setContentView(R.layout.activity_splash_);
		Bmob.initialize(this, "f35784f7b19f248ce11517a83307f02f");
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				downloadFile(client, xmlfile, xmlpath);
				downloadFile(client, pic1f, pic1);
				downloadFile(client, pic2f, pic2);
				downloadFile(client, pic3f, pic3);
				downloadFile(client, md2f, md2);
				downloadFile(client, mmanf, mman);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						startActivity(new Intent(Splash_Activity.this, GuideActivity.class));
						overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
						finish();
					}
				});
			}
		}).start();
		Iv_splash = (ImageView) findViewById(R.id.Iv_splash);
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(1000);
		Iv_splash.setAnimation(animation);

	}

	void downloadFile(HttpClient client, File out, String url) {
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream is = response.getEntity().getContent();
				OutputStream os = new FileOutputStream(out);
				int length = 0;
				byte b[] = new byte[1024];
				for (; (length = is.read(b)) != -1;) {
					os.write(b, 0, length);
				}
				os.flush();
				os.close();
				is.close();
			} else
				Log.i("123", "" + response.getStatusLine().getStatusCode());

		} catch (ClientProtocolException e) {
			Toast.makeText(Splash_Activity.this, e.toString(), 0).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(Splash_Activity.this, e.toString(), 0).show();
			e.printStackTrace();
		}
	}

}
