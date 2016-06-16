package lzh.ys.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import lzh.ys.R;
import lzh.ys.views.ImageCycleView;
import lzh.ys.views.ImageCycleView.ImageCycleViewListener;

public class LunBo_Fragment extends Fragment {

	private ImageCycleView luoboluobo;
	private ArrayList<String> lists;

	public LunBo_Fragment(ArrayList<String> lists) {
		this.lists = lists;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.luobo, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		luoboluobo = (ImageCycleView) getView().findViewById(R.id.luoboluobo);
		luoboluobo.setImageResources(lists, mAdCycleViewListener, 0);
	}

	// TODO 单击轮播图片处理事件
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {
		@Override
		public void onImageClick(int position, View imageView) {

		}
	};
}
