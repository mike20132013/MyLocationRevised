package com.mike.adapters;

import java.util.ArrayList;

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

import com.mike.appmodel.Model;
import com.mike.imageutils.ImageLoader;
import com.mike.mylocationrevised.R;

public class ViewPagerAdapter extends PagerAdapter {

	private Context context;

	private static String splitChars;

	private StringBuilder sb;
	
	private String[] words;

	private ArrayList<Model> infoArrayList = new ArrayList<Model>();

	private ArrayList<Model> addressArrayList = new ArrayList<Model>();

	private ArrayList<Model> placenameArrayList = new ArrayList<Model>();

	private ArrayList<Model> latitudeArrayList = new ArrayList<Model>();

	private ArrayList<Model> longitudeArrayList = new ArrayList<Model>();

	private ArrayList<Model> placetypeArrayList = new ArrayList<Model>();

	private ImageLoader mImageLoader;

	public ViewPagerAdapter() {
		super();
	}

	public ViewPagerAdapter(Context context,
			ArrayList<Model> latitudeArrayList,
			ArrayList<Model> longitudeArrayList) {
		super();
		this.context = context;
		this.latitudeArrayList = latitudeArrayList;
		this.longitudeArrayList = longitudeArrayList;
		mImageLoader = new ImageLoader(context.getApplicationContext());
	}

	public ViewPagerAdapter(Context context, ArrayList<Model> infoArrayList,
			ArrayList<Model> addressArrayList,
			ArrayList<Model> placenameArrayList,
			ArrayList<Model> latitudeArrayList,
			ArrayList<Model> longitudeArrayList,
			ArrayList<Model> placetypeArrayList) {
		super();
		this.context = context;
		this.infoArrayList = infoArrayList;
		this.addressArrayList = addressArrayList;
		this.placenameArrayList = placenameArrayList;
		this.latitudeArrayList = latitudeArrayList;
		this.longitudeArrayList = longitudeArrayList;
		this.placetypeArrayList = placetypeArrayList;
		mImageLoader = new ImageLoader(context.getApplicationContext());
	}

	@Override
	public int getCount() {
		return placenameArrayList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

	public static class ViewHolder {

		TextView latText, lngText, infoText, addText, placeType, placeNameText;
		ImageView typeImg;

	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = container;

		if (view != null) {

			ViewHolder mViewHolder = new ViewHolder();
			// Inflating layout
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			view = inflater.inflate(R.layout.history_list_item2, null);

			mViewHolder.typeImg = (ImageView) view.findViewById(R.id.history_imageView1);
			mViewHolder.latText = (TextView) view.findViewById(R.id.places_row_latitude_textview);
			mViewHolder.lngText = (TextView) view.findViewById(R.id.places_row_longitude_textview);
			mViewHolder.placeNameText = (TextView) view.findViewById(R.id.places_row_textview);
			mViewHolder.addText = (TextView) view.findViewById(R.id.places_row_textview_address);
			mViewHolder.placeType = (TextView) view.findViewById(R.id.places_row_textview_placetype);
			
			view.setTag(mViewHolder);

		}
		ViewHolder mViewHolder = (ViewHolder) view.getTag();

		TextView latitudeTextView = mViewHolder.latText;
		TextView longitudeTextView = mViewHolder.latText;
		TextView addressTextView = mViewHolder.addText;
		TextView placeTypeTextView = mViewHolder.placeType;
		TextView placeNameTextView = mViewHolder.placeNameText;
		ImageView placeImage = mViewHolder.typeImg;
		

		try {

			placeTypeTextView.setText(placetypeArrayList.get(position)
					.getSomeItem());
			latitudeTextView.setText(latitudeArrayList.get(position)
					.getSomeItem());
			longitudeTextView.setText(longitudeArrayList.get(position).getSomeItem());
			splitChars = placetypeArrayList.get(position).getSomeItem();

			//Replacing the String from the place type json array
			sb = new StringBuilder(splitChars);
			sb.replace(0, 2, "");
			sb.replace(
					Integer.valueOf(placeTypeTextView.getText().length() - 4),
					Integer.valueOf(placeTypeTextView.getText().length()), "");
			
			Log.d("TEXTVIEW CHARS : ", splitChars);

			if (placeTypeTextView != null) {
				placeTypeTextView.setText(sb);
			}
			placeNameTextView.setText(placenameArrayList.get(position).getSomeItem());
			addressTextView.setText(addressArrayList.get(position).getSomeItem());
			mImageLoader.DisplayImage(
					infoArrayList.get(position).getSomeItem(),R.drawable.ic_drawer, placeImage);
			
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
