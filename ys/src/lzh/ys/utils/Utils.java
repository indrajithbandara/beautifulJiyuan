package lzh.ys.utils;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import lzh.ys.ARScanActivity;
import lzh.ys.R;

public class Utils {
	public static long mExitTime = 0;

	public static final String BMOB_File_BASE_URL = "http://file.bmob.cn/";
	public static MediaPlayer player = null;
	public static final String SCENIC_GENERAL_TABNAME = "Scenic_General";

	public static MediaPlayer getPlayer(Context context) {
		if (player != null) {
			return player;
		} else
			player = new MediaPlayer();
		try {
			AssetFileDescriptor afd = context.getAssets().openFd("5354.wav");
			player.setDataSource(afd.getFileDescriptor());
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return player;
	}

}
