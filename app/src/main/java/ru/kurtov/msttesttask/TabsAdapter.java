package ru.kurtov.msttesttask;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by KURT on 29.06.2015.
 */
public class TabsAdapter extends FragmentPagerAdapter {

	final int PAGE_COUNT = 3;
	private String tabTitles[] = new String[] { "YANDEX", "CALC", "GREY" };
	private Context context;

	public TabsAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public Fragment getItem(int position) {

		switch (position) {
			case 0:
				return new YandexFragment();
			case 1:
				return new CalculationFragment();
			case 2:
				return new GreyFragment();
		}
		return null;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return tabTitles[position];
	}

	public View getTabView(int position) {
		View v = LayoutInflater.from(context).inflate(R.layout.fragment_page, null);
		TextView tv = (TextView) v.findViewById(R.id.simpleTextView);
		tv.setText(tabTitles[position]);
		return v;
	}
}
