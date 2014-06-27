/**
 * 
 */
package com.mike.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mike.appfragments.HistoryFragment;
import com.mike.appfragments.HomeFragment;

/**
 * @author mickey20142014
 * 
 */
public class AllPagesAdapter extends FragmentStatePagerAdapter{

	public AllPagesAdapter(FragmentManager fm) {

		super(fm);

	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {

		case 0: 
			
			return new HomeFragment(); 
			
		
		case 1:

			return new HistoryFragment();
			
		}
		return null;

	}

	@Override
	public int getCount() {
		return 2;
	}

}
