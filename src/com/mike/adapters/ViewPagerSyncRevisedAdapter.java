package com.mike.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mike.appfragments.HistoryRevisedFragment;
import com.mike.appmodel.Model;
import com.mike.imageutils.ImageLoader;
import com.mike.mylocationrevised.MainActivity;
import com.mike.mylocationrevised.R;
import com.mike.sdcardutils.ImageManager;

public class ViewPagerSyncRevisedAdapter extends PagerAdapter {

	private Context context;

	private ImageManager mImageManager;

	private ImageLoader mImageLoader;

	private static String splitChars;

	private StringBuilder sb;
	
	private ArrayList<LinkedHashMap<String, String>> mHashArray = new ArrayList<LinkedHashMap<String, String>>();

	private LinkedHashMap<String, String> mLinkedHashMap = new LinkedHashMap<String, String>();

	private ArrayList<Map<String, String>> mSyncHashArray = new ArrayList<Map<String, String>>();

	private ArrayList<Map<String, String>> mModelSyncArrayList = new ArrayList<Map<String, String>>();

	private Map<String, String> mModelSyncHashMap = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());

	private String[] mKeys;

	ArrayList<Model> placeNameArray = new ArrayList<Model>();
	ArrayList<Model> placeTypeArray = new ArrayList<Model>();
	ArrayList<Model> placeAddressArray = new ArrayList<Model>();
	ArrayList<Model> placeLatitudeArray = new ArrayList<Model>();
	ArrayList<Model> placeLongitudeArray = new ArrayList<Model>();
	ArrayList<Model> placeIconArray = new ArrayList<Model>();

	public ViewPagerSyncRevisedAdapter() {
		super();
	}

	public ViewPagerSyncRevisedAdapter(Context context,
			ArrayList<LinkedHashMap<String, String>> mHashArray) {
		super();
		this.context = context;
		this.mHashArray = mHashArray;
	}

	public ViewPagerSyncRevisedAdapter(Context context,
			ArrayList<Model> placeNameArray, ArrayList<Model> placeTypeArray,
			ArrayList<Model> placeAddressArray,
			ArrayList<Model> placeLatitudeArray,
			ArrayList<Model> placeLongitudeArray,
			ArrayList<Model> placeIconArray) {
		super();
		this.context = context;
		this.placeNameArray = placeNameArray;
		this.placeTypeArray = placeTypeArray;
		this.placeAddressArray = placeAddressArray;
		this.placeLatitudeArray = placeLatitudeArray;
		this.placeLongitudeArray = placeLongitudeArray;
		this.placeIconArray = placeIconArray;

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
		return placeNameArray.size();
	}

	// public Object getItem(int position) {
	// return mModelSyncHashMap.get(mKeys[position]);
	// }

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
			mViewHolder.placeaddressText = (TextView) view
					.findViewById(R.id.places_row_textview_address);
			mViewHolder.placeplaceNameText = (TextView) view
					.findViewById(R.id.places_name_textview);
			mViewHolder.placeplaceTypeText = (TextView) view
					.findViewById(R.id.places_row_textview_placetype);
			mViewHolder.placelatitudeText = (TextView) view
					.findViewById(R.id.places_row_latitude_textview);
			mViewHolder.placelongitudeText = (TextView) view
					.findViewById(R.id.places_row_longitude_textview);
			mViewHolder.placetypeImg = (ImageView) view
					.findViewById(R.id.places_history_imageView1);

			view.setTag(mViewHolder);
		}

		ViewHolder mViewHolder = (ViewHolder) view.getTag();

		TextView mPlaceNameTextView = mViewHolder.placeplaceNameText;
		TextView mPlaceTypeTextView = mViewHolder.placeplaceTypeText;
		TextView mPlaceAddressTextView = mViewHolder.placeaddressText;
		TextView mPlaceLatitudeTextView = mViewHolder.placelatitudeText;
		TextView mPlaceLongitudeTextView = mViewHolder.placelongitudeText;
		ImageView mPlaceIconImageView = mViewHolder.placetypeImg;

		try {

			mPlaceNameTextView.setText(placeNameArray.get(position)
					.getSomeItem());
			mPlaceTypeTextView.setText(placeTypeArray.get(position)
					.getSomeItem());
			
			splitChars = placeTypeArray.get(position).getSomeItem();

			//Replacing the String from the place type json array
			sb = new StringBuilder(splitChars);
			sb.replace(0, 2, "");
			sb.replace(
					Integer.valueOf(mPlaceTypeTextView.getText().length() - 4),
					Integer.valueOf(mPlaceTypeTextView.getText().length()), "");
			
			Log.d("TEXTVIEW CHARS : ", splitChars);

			if (mPlaceTypeTextView != null) {
				mPlaceTypeTextView.setText(sb);
			}
			
			mPlaceAddressTextView.setText(placeAddressArray.get(position)
					.getSomeItem());
			mPlaceLatitudeTextView.setText(placeLatitudeArray.get(position)
					.getSomeItem());
			mPlaceLongitudeTextView.setText(placeLongitudeArray.get(position)
					.getSomeItem());
			mImageLoader.DisplayImage(placeIconArray.get(position)
					.getSomeItem(), R.drawable.ic_drawer,
					mPlaceIconImageView);

		} catch (Exception e) {
			e.printStackTrace();
		}

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
