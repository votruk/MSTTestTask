package ru.kurtov.msttesttask;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		TabsAdapter pagerAdapter = new TabsAdapter(getSupportFragmentManager(), MainActivity.this);
		viewPager.setAdapter(pagerAdapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

		tabLayout.setTabsFromPagerAdapter(pagerAdapter);
		tabLayout.setupWithViewPager(viewPager);


		for (int i = 0; i < tabLayout.getTabCount(); i++) {
			TabLayout.Tab tab = tabLayout.getTabAt(i);
			tab.setCustomView(pagerAdapter.getTabView(i));
		}

	}




}
