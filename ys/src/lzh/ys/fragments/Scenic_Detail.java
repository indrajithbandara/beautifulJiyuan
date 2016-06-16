package lzh.ys.fragments;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LinearGradient;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import lzh.ys.HintActivity;
import lzh.ys.PhotoDetail;
import lzh.ys.R;
import lzh.ys.beans.Scenic_Detail_Bean;
import lzh.ys.views.ImageCycleView;
import lzh.ys.views.ImageCycleView.ImageCycleViewListener;

public class Scenic_Detail extends Fragment {
	Scenic_Detail_Bean bean;

	LinearLayout hscroll;
	TextView Tv_Name;
	ImageCycleView Iv_Detail;
	TextView Tv_Descript;
	private int NextID;

	public Scenic_Detail() {

		StrictMode.setThreadPolicy(new ThreadPolicy.Builder().detectNetwork().build());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.scenic_play, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		final View view = getView();

		view.findViewById(R.id.detail_play).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				((HintActivity) getActivity()).switchPlayer(v);
			}
		});
		view.findViewById(R.id.detail_next).setOnClickListener(new OnClickListener() {
			// 加载下一项数据
			@Override
			public void onClick(View v) {
				try {
					if (NextID != -1) {
						Toast.makeText(getActivity(), "加载数据中...", Toast.LENGTH_SHORT).show();

						((HintActivity) getActivity()).seeDetail(NextID);
					} else
						Toast.makeText(getActivity(), "没有下一项了", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getActivity(), "数据加载未完成，请稍后", Toast.LENGTH_SHORT).show();

				}
			}
		});

		Tv_Name = (TextView) view.findViewById(R.id.detail_name);
		Tv_Descript = (TextView) view.findViewById(R.id.detail_description);
		Iv_Detail = (ImageCycleView) view.findViewById(R.id.detail_tImg);
		hscroll = (LinearLayout) view.findViewById(R.id.hscroll);

	}

	public void setData(final Scenic_Detail_Bean bean) throws java.lang.NullPointerException {
		// 加载数据模块
		NextID = bean.getNext();
		Tv_Descript.setText(bean.getDescription());
		Tv_Name.setText(bean.getName());
		this.bean=bean;
		Iv_Detail.setImageResources((ArrayList<String>) bean.getUrl_list(), mAdCycleViewListener, 0);

		// hscroll.removeAllViews();
		// for (Bitmap bitmap : bean.getUrl_list()) {
		// ImageView imageView = new ImageView(getActivity());
		// imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT));
		// imageView.setScaleType(ScaleType.FIT_XY);
		// imageView.setImageBitmap(bitmap);
		// hscroll.addView(imageView);
		// }

	}

	// TODO 单击轮播图片处理事件
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {
		@Override
		public void onImageClick(int position, View imageView) {
			Intent intent = new Intent(getActivity(), PhotoDetail.class);
			intent.putExtra("url", bean.getUrl_list().get(position));
			startActivity(intent);
		}
	};
}
