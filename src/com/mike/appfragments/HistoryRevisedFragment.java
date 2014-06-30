package com.mike.appfragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.mike.adapters.ViewPagerAdapter;
import com.mike.adapters.ViewPagerSyncAdapter;
import com.mike.adapters.ViewPagerSyncRevisedAdapter;
import com.mike.appmodel.Model;
import com.mike.customlistview.NestedListView;
import com.mike.mylocationrevised.R;
import com.mike.utils.AppUtils;

/**
 * @author mickey20142014
 * 
 */
public class HistoryRevisedFragment extends Fragment {

	private static final String gasURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
			+ "location=42.29543187,-71.47470374&"
			+ "radius=10000&"
			+ "types=gas_station&"
			+ "key=AIzaSyAaDaMUimX4NRgapY-keH18ZYnAmHRNnn4&sensor=true";

	private NestedListView mListView2;

	private ViewPager mViewPager;

	private ViewPagerSyncAdapter mViewPagerSyncAdapter;

	private ViewPagerSyncRevisedAdapter mViewPagerSyncRevisedAdapter;

	private AppUtils mAppUtils;

	private Model mAppModel;

	private ListAdapter mAdapter2;

	// Previous Code

	ArrayList<Model> placeNameArray = new ArrayList<Model>();
	ArrayList<Model> placeTypeArray = new ArrayList<Model>();
	ArrayList<Model> placeAddressArray = new ArrayList<Model>();
	ArrayList<Model> placeLatitudeArray = new ArrayList<Model>();
	ArrayList<Model> placeLongitudeArray = new ArrayList<Model>();
	ArrayList<Model> placeIconArray = new ArrayList<Model>();

	ArrayList<LinkedHashMap<String, String>> mHashArray = new ArrayList<LinkedHashMap<String, String>>();

	ArrayList<Map<String, String>> mSyncHashArray = new ArrayList<Map<String, String>>();

	ArrayList<Map<String, String>> mModelSyncArrayList = new ArrayList<Map<String, String>>();

	LinkedHashMap<String, String> mHashMap = new LinkedHashMap<String, String>();

	Map<String, String> mSyncHashMap = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());

	Map<String, String> mModelSyncHashMap = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());

	private BackTask mBackTask;

	public static String PLACE_NAME = "PLACE NAME";
	public static String PLACE_ADDRESS = "PLACE ADDRESS";
	public static String PLACE_TYPE = "PLACE TYPE";
	public static String PLACE_LATITUDE = "PLACE LATITUDE";
	public static String PLACE_LONGITUDE = "PLACE LONGITUDE";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View historyView = inflater.inflate(R.layout.history_layout, container,
				false);

		return historyView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mAppModel = new Model();

		mListView2 = (NestedListView) getActivity().findViewById(
				R.id.historyListView);

		mViewPager = (ViewPager) getActivity().findViewById(R.id.places_pager);

		mBackTask = new BackTask(gasURL);
		Thread.currentThread().setContextClassLoader(
				mBackTask.getClass().getClassLoader());
		mBackTask.execute();

	}

	private void getGasLocationsUpdate(String gasURL) {

		String placeiconUrl = null;
		String placeName = null;
		String placeAddress = null;
		String placeType = null;
		String placeLatitude = null;
		String placeLongitude = null;

		mAppUtils = new AppUtils(getActivity().getApplicationContext());
		try {

			JSONObject mainJsonObject = new JSONObject(
					mAppUtils.loadJSON(gasURL));

			JSONArray mainJsonArray = mainJsonObject.getJSONArray("results");

			for (int i = 0; i < mainJsonArray.length(); i++) {

				JSONObject insidemainJsonArray = mainJsonArray.getJSONObject(i);
				JSONObject geometryJsonObject = mainJsonArray.getJSONObject(i);
				placeName = insidemainJsonArray.getString("name");
				placeAddress = insidemainJsonArray.getString("vicinity");
				placeiconUrl = insidemainJsonArray.getString("icon");
				placeType = insidemainJsonArray.getString("types");

				if (mAppModel == null) {
					mAppModel = new Model();
				}

				if (geometryJsonObject != null) {
					JSONObject geometryJSONObject = geometryJsonObject
							.getJSONObject("geometry");
					JSONObject locationJSONObject = geometryJSONObject
							.getJSONObject("location");

					placeLatitude = locationJSONObject.getString("lat");
					placeLongitude = locationJSONObject.getString("lng");

				}

				mAppModel.setPlaceName(placeName);
				mAppModel.setAddressList(placeAddress);
				mAppModel.setWeb_imageUrls(placeiconUrl);
				mAppModel.setPlaceType(placeType);
				mAppModel.setLatitude(placeLatitude);
				mAppModel.setLongitude(placeLongitude);

				placeNameArray.add(new Model(mAppModel.getPlaceName()));
				placeTypeArray.add(new Model(mAppModel.getPlaceType()));
				placeAddressArray.add(new Model(mAppModel.getAddressList()));
				placeLatitudeArray.add(new Model(mAppModel.getLatitude()));
				placeLongitudeArray.add(new Model(mAppModel.getLongitude()));
				placeIconArray.add(new Model(mAppModel.getWeb_imageUrls()));

				mHashMap.put("PLACE NAME", mAppModel.getPlaceName());
				mHashMap.put("PLACE ADDRESS", mAppModel.getAddressList());
				mHashMap.put("PLACE ICON", mAppModel.getWeb_imageUrls());
				mHashMap.put("PLACE TYPE", mAppModel.getPlaceType());
				mHashMap.put("PLACE LATITUDE", mAppModel.getLatitude());
				mHashMap.put("PLACE LONGITUDE", mAppModel.getLongitude());

				mSyncHashMap.put("PLACE NAME", placeName);
				mSyncHashMap.put("PLACE ADDRESS", placeAddress);
				mSyncHashMap.put("PLACE ICON", placeiconUrl);
				mSyncHashMap.put("PLACE TYPE", placeType);
				mSyncHashMap.put("PLACE LATITUDE", placeLatitude);
				mSyncHashMap.put("PLACE LONGITUDE", placeLongitude);

				mModelSyncHashMap.put("PLACE NAME", mAppModel.getPlaceName());
				mModelSyncHashMap.put("PLACE ADDRESS",
						mAppModel.getAddressList());
				mModelSyncHashMap.put("PLACE ICON",
						mAppModel.getWeb_imageUrls());
				mModelSyncHashMap.put("PLACE TYPE", mAppModel.getPlaceType());
				mModelSyncHashMap
						.put("PLACE LATITUDE", mAppModel.getLatitude());
				mModelSyncHashMap.put("PLACE LONGITUDE",
						mAppModel.getLongitude());

				mHashArray.add(mHashMap);
				mSyncHashArray.add(mSyncHashMap);
				mModelSyncArrayList.add(mModelSyncHashMap);

				String mStringHash = String.valueOf(mSyncHashArray.get(0));

				Log.d("HASH ARRAY STRING : ", mStringHash);

				Iterator mIterator = mHashMap.keySet().iterator();

				while (mIterator.hasNext()) {
					String key = (String) mIterator.next();
					String value = (String) mHashMap.get(key);

					Log.d("HASH MAP ITERATOR : ", "KEY : " + key + "\n"
							+ "VALUE : " + value);

				}

				Iterator mSyncIterator = mSyncHashMap.keySet().iterator();

				while (mSyncIterator.hasNext()) {
					String key = (String) mSyncIterator.next();
					String value = (String) mSyncHashMap.get(key);
					Log.d("HASH MAP SYNC ITERATOR : ", "KEY : " + key + "\n"
							+ "VALUE : " + value);
				}

				Iterator mModelSyncIterator = mModelSyncHashMap.keySet()
						.iterator();

				while (mModelSyncIterator.hasNext()) {
					String key = (String) mModelSyncIterator.next();
					String value = (String) mModelSyncHashMap.get(key);
					Log.d("HASH MAP MODEL SYNC ITERATOR : ", "KEY : " + key
							+ "\n" + "VALUE : " + value);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public class BackTask extends AsyncTask<Void, Integer, Void> {

		String URL;

		public BackTask(String URL) {
			super();
			this.URL = URL;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {

			getGasLocationsUpdate(URL);

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			mViewPagerSyncRevisedAdapter = new ViewPagerSyncRevisedAdapter(
					getActivity().getApplicationContext(), placeNameArray, placeTypeArray,
					placeAddressArray, placeLatitudeArray, placeLongitudeArray,
					placeIconArray);
			mViewPager.setAdapter(mViewPagerSyncRevisedAdapter);
		}
	}

}
