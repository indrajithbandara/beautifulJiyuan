package lzh.ys;

import com.loopj.android.image.WebImage;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.View;
import android.view.View.OnClickListener;
import uk.co.senab.photoview.PhotoView;

public class PhotoDetail extends Activity implements OnClickListener {
	PhotoView photoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Black_NoTitleBar);
		photoView = new PhotoView(this);
		setContentView(photoView);
		getWindow().getDecorView().setClickable(true);
		getWindow().getDecorView().setOnClickListener(this);
		String url = getIntent().getStringExtra("url");
		photoView.setImageBitmap(new WebImage(url).getBitmap(this));
	}

	@Override
	public void onClick(View v) {

		PhotoDetail.this.finish();
	}

}
