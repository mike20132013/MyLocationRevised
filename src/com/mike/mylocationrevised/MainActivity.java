package com.mike.mylocationrevised;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;

import com.mike.adapters.AllPagesAdapter;

@SuppressLint("NewApi") public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	
	private ActionBar mActionBar;
	private ViewPager mViewPager;
	private AllPagesAdapter mPagesAdapter;
	private String[] tabs = { "Map Home", "Nearby Places" };
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity_layout);
    
        mViewPager = (ViewPager) findViewById(R.id.pager);
		mActionBar = getActionBar();
		mPagesAdapter = new AllPagesAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagesAdapter);
		mActionBar.setHomeButtonEnabled(false);
		mActionBar.setSubtitle("Save your location now");
		mActionBar.setIcon(R.drawable.cameraicon);
		mActionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#CD6600")));
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (String tab_name : tabs) {

			mActionBar.addTab(mActionBar.newTab().setText(tab_name)
					.setTabListener(this));

		}

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				mActionBar.setSelectedNavigationItem(position);

			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int position) {
				// TODO Auto-generated method stub

			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		mViewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
    
}
