package ru.kurtov.msttesttask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;

public class GreyFragment extends Fragment {
	private ImageView mAndroidImageView;
	private int count = 0;
	private boolean mIsCurrentImageColor;

	private TextView mTimerTextView;
	private Future longRunningTaskFuture;

	private Handler mHandler;

	private Timer mTimer;

	private int mInterval = 5000;

	private long elapsed;
	private final static long INTERVAL = 100;
	private final static long TIMEOUT = 5000;

	private boolean isRunnableRunning;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_grey, container, false);

		mTimerTextView = (TextView) v.findViewById(R.id.greyTextView);

		mAndroidImageView = (ImageView) v.findViewById(R.id.greyImageView);
		mAndroidImageView.setImageResource(R.drawable.android);


// Execute a runnable task as soon as possible


//		startRepeatingTask();
		return v;
	}


	private final Runnable mStatusChecker = new Runnable() {
		@Override
		public void run() {

			mIsCurrentImageColor = count % 2 == 1;
			new ToGreyScale().execute(mIsCurrentImageColor);
			count++;

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					elapsed += INTERVAL;
					if (elapsed >= TIMEOUT) {
						this.cancel();
						elapsed = 0;
					}
					int second = (int) (elapsed / 100 - ((elapsed / 100) % 10)) / 10;
					int millSec = (int) (elapsed / 100 - second * 10);
					displayText(getString(R.string.seconds_elapsed) + " " + second + "." + millSec);
				}
			};
			mTimer = new Timer();
			mTimer.scheduleAtFixedRate(task, INTERVAL, INTERVAL);

			mHandler.postDelayed(mStatusChecker, mInterval);
		}
	};

	void startRepeatingTask() {

		isRunnableRunning = true;
		mStatusChecker.run();
	}

	void stopRepeatingTask() {
		isRunnableRunning = false;
		mHandler.removeCallbacks(mStatusChecker);
	}



	private void displayText(final String text) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mTimerTextView.setText(text);
			}
		});
	}



	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			mHandler = new Handler();
			startRepeatingTask();
		} else {
			if (isRunnableRunning) {
				stopRepeatingTask();
				count = 0;
				elapsed = 0;
				mTimer.cancel();
			}
		}
	}


	@Override
	public void onPause() {
		if (isRunnableRunning) {
			stopRepeatingTask();
			count = 0;
			elapsed = 0;
			mTimer.cancel();
		}
		super.onPause();

	}

	private class ToGreyScale extends AsyncTask<Boolean, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(Boolean... params) {
			Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(),
					R.drawable.android);
			if (params[0]) {
				int width, height;
				Bitmap bmpOriginal = BitmapFactory.decodeResource(getActivity().getResources(),
						R.drawable.android);
				height = bmpOriginal.getHeight();
				width = bmpOriginal.getWidth();

				bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
				Canvas c = new Canvas(bitmap);
				Paint paint = new Paint();
				ColorMatrix cm = new ColorMatrix();
				cm.setSaturation(0);
				ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
				paint.setColorFilter(f);
				c.drawBitmap(bmpOriginal, 0, 0, paint);
			}

			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			mAndroidImageView.setImageBitmap(bitmap);
		}
	}

}
