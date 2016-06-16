package lzh.ys.fragments;

import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import lzh.ys.HintActivity;
import lzh.ys.R;

public class Scenic_List extends Fragment {
	private ListView listView;
	private List<HashMap<String, Object>> lists;
	List<String> nextId;
	public Scenic_List(List<HashMap<String, Object>> lists, List<String> nextId) {
		this.nextId=nextId;
		this.lists = lists;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.scenic_list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		listView = (ListView) getView().findViewById(R.id.scenic_list);
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), lists, R.layout.list_items,
				new String[] { "img", "text" }, new int[] { R.id.img_list, R.id.txt_list });
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
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				((HintActivity)getActivity()).seeDetail(arg2);
				
			}
		});
	}
}
