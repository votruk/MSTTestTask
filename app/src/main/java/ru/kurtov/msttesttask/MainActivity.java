package ru.kurtov.msttesttask;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

	TabLayout tabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get the ViewPager and set it's PagerAdapter so that it can display items
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		TabsAdapter pagerAdapter = new TabsAdapter(getSupportFragmentManager(), MainActivity.this);
		viewPager.setAdapter(pagerAdapter);

		// Give the TabLayout the ViewPager
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		tabLayout.setTabsFromPagerAdapter(pagerAdapter);
//		tabLayout.addTab(tabLayout.newTab().setText("Yandex"));
//		tabLayout.addTab(tabLayout.newTab().setText("Calc"));
//		tabLayout.addTab(tabLayout.newTab().setText("GreyScale"));
		tabLayout.setupWithViewPager(viewPager);


		// Iterate over all tabs and set the custom view
		for (int i = 0; i < tabLayout.getTabCount(); i++) {
			TabLayout.Tab tab = tabLayout.getTabAt(i);
			tab.setCustomView(pagerAdapter.getTabView(i));
		}
//		for (int i = 0; i < tabLayout.getTabCount(); i++) {
//
//			TabLayout.Tab tab = tabLayout.getTabAt(i);
//
//			tab.setCustomView(pagerAdapter.getTabView(i));
//		}

	}




}
