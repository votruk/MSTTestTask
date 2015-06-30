package ru.kurtov.msttesttask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by KURT on 29.06.2015.
 */
public class YandexFragment extends Fragment {
	private static final String YANDEX_URL = "https://www.yandex.ru/";
	private TextView mSourceCodeTextView;
	private TextView mTitleTextView;
	private ProgressBar mDownloadProgressBar;



	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_yandex, container, false);

		mSourceCodeTextView = (TextView) v.findViewById(R.id.yandexTextView);

		mTitleTextView = (TextView) v.findViewById(R.id.yandexTitleTextView);

		mDownloadProgressBar = (ProgressBar) v.findViewById(R.id.yandexProgressBar);
		mDownloadProgressBar.setVisibility(View.INVISIBLE);


//		tv.setText("AAAAAAAAA");

		new SourceCodeAsyncLoad().execute();




		return v;
	}



	private class SourceCodeAsyncLoad extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDownloadProgressBar.setVisibility(View.VISIBLE);
			mTitleTextView.setText("Downloading Yandex source code");
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				URL yandex = null;
				yandex = new URL(YANDEX_URL);
				URLConnection yc = yandex.openConnection();
//				yc.addRequestProperty("User-Agent", "Mozilla/6.0 (Windows NT 6.2; WOW64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1");
				BufferedReader in = new BufferedReader(new InputStreamReader(
						yc.getInputStream(), "UTF-8"));
				String inputLine;
				StringBuilder a = new StringBuilder();
				while ((inputLine = in.readLine()) != null)
					a.append(inputLine);
				in.close();
				return a.toString();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			mDownloadProgressBar.setVisibility(View.INVISIBLE);
			mTitleTextView.setText("Yandex.ru source code");
			mSourceCodeTextView.setText(s);
		}
	}

}
