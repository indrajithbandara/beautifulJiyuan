package lzh.ys.fragments;

import java.util.ArrayList;

import org.w3c.dom.Text;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import lzh.ys.HintActivity;
import lzh.ys.PhotoDetail;
import lzh.ys.R;
import lzh.ys.beans.Scenic_General_Beans;
import lzh.ys.views.ImageCycleView;
import lzh.ys.views.ImageCycleView.ImageCycleViewListener;

public class Scenic_General extends Fragment implements OnClickListener {
	private ImageCycleView mAdView;
	private Scenic_General_Beans beans;
	private String objectId;

	public Scenic_General(Scenic_General_Beans beans, String objectId) {
		this.beans = beans;
		this.objectId = objectId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.scenic_total, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		View view = getView();
		mAdView = (ImageCycleView) view.findViewById(R.id.ad_view);
		mAdView.setImageResources(beans.getImg_lists(), mAdCycleViewListener, 0);
		TextView Tv_Address = (TextView) view.findViewById(R.id.total_address);
		Tv_Address.setText(beans.getAddress());
		Tv_Address.setOnClickListener(this);
		TextView Tv_Phone = (TextView) view.findViewById(R.id.total_phone);
		Tv_Phone.setText(beans.getPhone());
		Tv_Phone.setOnClickListener(this);
		TextView Tv_Price = (TextView) view.findViewById(R.id.total_price);
		Tv_Price.setOnClickListener(this);
		Tv_Price.setText(beans.getPrice());
		view.findViewById(R.id.total_music).setOnClickListener(this);
	}

	// TODO 单击轮播图片处理事件
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {
		@Override
		public void onImageClick(int position, View imageView) {
			Intent intent = new Intent(getActivity(), PhotoDetail.class);
			intent.putExtra("url", beans.getImg_lists().get(position));
			startActivity(intent);
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.total_phone:
			try {
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + beans.getPhone()));
				startActivity(intent);
			} catch (Exception e) {
				Toast.makeText(getActivity(), "没有权限或电话功能哦！" + e.toString(), Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.total_music:
			((HintActivity) getActivity()).listenMusic(objectId);
			break;
		default:
			break;
		}
	}
}
