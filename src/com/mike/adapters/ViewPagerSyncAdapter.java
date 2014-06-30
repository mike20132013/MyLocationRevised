package com.mike.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.adapters.ViewPagerAdapter.ViewHolder;
import com.mike.imageutils.ImageLoader;
import com.mike.mylocationrevised.R;

public class ViewPagerSyncAdapter extends PagerAdapter {

	private Map<String, String> mModelSyncHashMap = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	
	ArrayList<Map<String, String>> mModelSyncArrayList = new ArrayList<Map<String, String>>();
	
	//New Style(Using Collections)
	private ArrayList<Map<String, String>> mModelPlaceTypeSyncArrayList = new ArrayList<Map<String, String>>();
	private ArrayList<Map<String, String>> mModelPlaceAddressSyncArrayList = new ArrayList<Map<String, String>>();
	private ArrayList<Map<String, String>> mModelPlaceLatitudeSyncArrayList = new ArrayList<Map<String, String>>();
	private ArrayList<Map<String, String>> mModelPlaceLongitudeSyncArrayList = new ArrayList<Map<String, String>>();
	private ArrayList<Map<String, String>> mModelPlaceImageSyncArrayList = new ArrayList<Map<String, String>>();
	private ArrayList<Map<String, String>> mModelPlaceNameSyncArrayList = new ArrayList<Map<String, String>>();
	
	//Using Map
	private Map<String, String> mModelPlaceNameSyncTypeHashMap = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());

	private Map<String, String> mModelPlaceTypeSyncTypeHashMap = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());

	private Map<String, String> mModelPlaceAddressSyncTypeHashMap = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());

	private Map<String, String> mModelPlaceLatitudeSyncTypeHashMap = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	
	private Map<String, String> mModelPlaceLongitudeSyncTypeHashMap = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());

	private Map<String, String> mModelPlaceImageIconSyncTypeHashMap = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	
	
	public ViewPagerSyncAdapter(
			ArrayList<Map<String, String>> mModelPlaceNameSyncArrayList,
			ArrayList<Map<String, String>> mModelPlaceTypeSyncArrayList,
			ArrayList<Map<String, String>> mModelPlaceAddressSyncArrayList,
			ArrayList<Map<String, String>> mModelPlaceLatitudeSyncArrayList,
			ArrayList<Map<String, String>> mModelPlaceLongitudeSyncArrayList,
			ArrayList<Map<String, String>> mModelPlaceImageSyncArrayList,
			Context context) {
		super();
		this.mModelPlaceNameSyncArrayList = mModelPlaceNameSyncArrayList;
		this.mModelPlaceTypeSyncArrayList = mModelPlaceTypeSyncArrayList;
		this.mModelPlaceAddressSyncArrayList = mModelPlaceAddressSyncArrayList;
		this.mModelPlaceLatitudeSyncArrayList = mModelPlaceLatitudeSyncArrayList;
		this.mModelPlaceLongitudeSyncArrayList = mModelPlaceLongitudeSyncArrayList;
		this.mModelPlaceImageSyncArrayList = mModelPlaceImageSyncArrayList;
		this.context = context;
	}

	
	
	public ViewPagerSyncAdapter(
			Map<String, String> mModelPlaceNameSyncTypeHashMap,
			Map<String, String> mModelPlaceTypeSyncTypeHashMap,
			Map<String, String> mModelPlaceAddressSyncTypeHashMap,
			Map<String, String> mModelPlaceLatitudeSyncTypeHashMap,
			Map<String, String> mModelPlaceLongitudeSyncTypeHashMap,
			Map<String, String> mModelPlaceImageIconSyncTypeHashMap,
			Context context) {
		super();
		this.mModelPlaceNameSyncTypeHashMap = mModelPlaceNameSyncTypeHashMap;
		this.mModelPlaceTypeSyncTypeHashMap = mModelPlaceTypeSyncTypeHashMap;
		this.mModelPlaceAddressSyncTypeHashMap = mModelPlaceAddressSyncTypeHashMap;
		this.mModelPlaceLatitudeSyncTypeHashMap = mModelPlaceLatitudeSyncTypeHashMap;
		this.mModelPlaceLongitudeSyncTypeHashMap = mModelPlaceLongitudeSyncTypeHashMap;
		this.mModelPlaceImageIconSyncTypeHashMap = mModelPlaceImageIconSyncTypeHashMap;
		this.context = context;
	}



	public ViewPagerSyncAdapter(
			ArrayList<Map<String, String>> mModelSyncArrayList, Context context) {
		super();
		this.mModelSyncArrayList = mModelSyncArrayList;
		this.context = context;
	}

	private Context context;
	private ImageLoader mImageLoader;

	public ViewPagerSyncAdapter() {
		super();
	}

	public ViewPagerSyncAdapter(Map<String, String> mModelSyncHashMap,
			Context context) {
		super();
		this.mModelSyncHashMap = mModelSyncHashMap;
		this.context = context;
		mImageLoader = new ImageLoader(context.getApplicationContext());
	}

	public static class ViewHolder {

		TextView placelatitudeText;
		TextView placelongitudeText;
		TextView placeaddressText;
		TextView placeplaceTypeText;
		TextView placeplaceNameText;
		ImageView placetypeImg;

	}

	@Override
	public int getCount() {
		return mModelPlaceNameSyncTypeHashMap.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		View view = container;
		
		if (view != null) {
			ViewHolder mViewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.history_sync_item, null);
			mViewHolder.placeaddressText = (TextView)view.findViewById(R.id.places_row_textview_address);
			mViewHolder.placeplaceNameText = (TextView)view.findViewById(R.id.places_name_textview);
			mViewHolder.placeplaceTypeText = (TextView)view.findViewById(R.id.places_row_textview_placetype);
			mViewHolder.placelatitudeText = (TextView)view.findViewById(R.id.places_row_latitude_textview);
			mViewHolder.placelongitudeText = (TextView)view.findViewById(R.id.places_row_longitude_textview);
			mViewHolder.placetypeImg = (ImageView)view.findViewById(R.id.places_history_imageView1);
			
			view.setTag(mViewHolder);
		}
		
		ViewHolder mViewHolder = (ViewHolder) view.getTag();
		
		TextView mPlaceNameTextView = mViewHolder.placeplaceNameText;
		TextView mPlaceTypeTextView = mViewHolder.placeplaceTypeText;
		TextView mPlaceAddressTextView = mViewHolder.placeaddressText;
		TextView mPlaceLatitudeTextView = mViewHolder.placelatitudeText;
		TextView mPlaceLongitudeTextView = mViewHolder.placelongitudeText;
		ImageView mPlaceIconImageView = mViewHolder.placetypeImg;
		
		//Using Collections
//		try {
//
//			mPlaceNameTextView.setText(String
//					.valueOf(mModelPlaceNameSyncArrayList.get(position)));
//			mPlaceTypeTextView.setText(String
//					.valueOf(mModelPlaceTypeSyncArrayList.get(position)));
//			mPlaceAddressTextView.setText(String
//					.valueOf(mModelPlaceAddressSyncArrayList.get(position)));
//			mPlaceLatitudeTextView.setText(String
//					.valueOf(mModelPlaceLatitudeSyncArrayList.get(position)));
//			mPlaceLongitudeTextView.setText(String
//					.valueOf(mModelPlaceLongitudeSyncArrayList.get(position)));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		//Trying with hashmap
		
//		try{
//			
//			Iterator placeNameIterator = mModelPlaceNameSyncTypeHashMap.keySet().iterator();
//			while(placeNameIterator.hasNext()){
//				
//				String key = (String) placeNameIterator.next();
//				String value = (String) mModelPlaceNameSyncTypeHashMap.get(key);
//				
//				mPlaceNameTextView.setText(value);
//				
//			}
//
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}

		((ViewPager) container).addView(view, 0);
		
		return view;

	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

}
