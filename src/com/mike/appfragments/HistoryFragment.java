/**
 * 
 */
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
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.mike.adapters.ViewPagerAdapter;
import com.mike.appmodel.Model;
import com.mike.customlistview.NestedListView;
import com.mike.mylocationrevised.R;
import com.mike.utils.AppUtils;

/**
 * @author mickey20142014
 * 
 */
public class HistoryFragment extends Fragment {

	private static final String gasURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
			+ "location=42.29543187,-71.47470374&"
			+ "radius=10000&"
			+ "types=gas_station&"
			+ "key=AIzaSyAaDaMUimX4NRgapY-keH18ZYnAmHRNnn4&sensor=true";

	private String testCommit;
	
	private Spinner mSpinner;

	private NestedListView mListView2;

	private ViewPager mViewPager;

	private AppUtils mAppUtils;

	private Model mAppModel;

	private ListAdapter mAdapter2;

	private ViewPagerAdapter mViewPagerAdapter;

	private ArrayList<Model> infoArrayList = new ArrayList<Model>();

	private ArrayList<Model> placeArrayList = new ArrayList<Model>();

	private ArrayList<Model> mPlaceNameArrayList = new ArrayList<Model>();

	private ArrayList<Model> mPlacetypeArrayList = new ArrayList<Model>();

	private ArrayList<Model> mPlaceAddressArrayList = new ArrayList<Model>();

	private ArrayList<Model> mPlacelatitudeArrayList = new ArrayList<Model>();

	private ArrayList<Model> mPlacelongitudeArrayList = new ArrayList<Model>();

	private ArrayList<Model> mPlaceIconArrayList = new ArrayList<Model>();

	private BackTask mBackTask;

	private ProgressBar mProgressBar;

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

		mProgressBar = (ProgressBar) getActivity().findViewById(
				R.id.viewPagerProgress);

		mListView2 = (NestedListView) getActivity().findViewById(
				R.id.historyListView);

		mViewPager = (ViewPager) getActivity().findViewById(R.id.places_pager);

		mBackTask = new BackTask(gasURL);
		Thread.currentThread().setContextClassLoader(
				mBackTask.getClass().getClassLoader());
		mBackTask.execute();

	}

	public void getGasLocationsUpdate(String gasURL) {

		String placeiconUrl = null;
		String placeName = null;
		String placeAddress = null;
		String placeType = null;
		String placeLatitude = null;
		String placeLongitude = null;

		mPlaceNameArrayList = new ArrayList<Model>();

		mPlacetypeArrayList = new ArrayList<Model>();

		mPlaceAddressArrayList = new ArrayList<Model>();

		mPlacelatitudeArrayList = new ArrayList<Model>();

		mPlacelongitudeArrayList = new ArrayList<Model>();

		mPlaceIconArrayList = new ArrayList<Model>();

		ArrayList<LinkedHashMap<String, String>> mHashArray = new ArrayList<LinkedHashMap<String, String>>();

		ArrayList<Map<String, String>> mSyncHashArray = new ArrayList<Map<String, String>>();

		ArrayList<Map<String, String>> mModelSyncArrayList = new ArrayList<Map<String, String>>();

		LinkedHashMap<String, String> mHashMap = new LinkedHashMap<String, String>();

		Map<String, String> mSyncHashMap = Collections
				.synchronizedMap(new LinkedHashMap<String, String>());

		Map<String, String> mModelSyncHashMap = Collections
				.synchronizedMap(new LinkedHashMap<String, String>());

		mAppUtils = new AppUtils(getActivity().getApplicationContext());
		try {

			JSONObject mainJsonObject = new JSONObject(
					mAppUtils.loadJSON(gasURL));

			JSONArray mainJsonArray = mainJsonObject.getJSONArray("results");

			for (int i = 0; i < mainJsonArray.length(); i++) {

				JSONObject insidemainJsonArray = mainJsonArray.getJSONObject(i);
				placeName = insidemainJsonArray.getString("name");
				placeAddress = insidemainJsonArray.getString("vicinity");
				placeiconUrl = insidemainJsonArray.getString("icon");

				if (mAppModel == null) {
					mAppModel = new Model();
				}

				mAppModel.setPlaceName(placeName);
				mAppModel.setAddressList(placeAddress);
				mAppModel.setWeb_imageUrls(placeiconUrl);

				mHashMap = new LinkedHashMap<String, String>();

				mHashMap.put("PLACE NAME", placeName);
				mHashMap.put("PLACE ADDRESS", placeAddress);
				mHashMap.put("PLACE ICON", placeiconUrl);

				mSyncHashMap.put("PLACE NAME", placeName);
				mSyncHashMap.put("PLACE ADDRESS", placeAddress);
				mSyncHashMap.put("PLACE ICON", placeiconUrl);

				mModelSyncHashMap.put("PLACE NAME", mAppModel.getPlaceName());
				mModelSyncHashMap.put("PLACE ADDRESS",
						mAppModel.getAddressList());
				mModelSyncHashMap.put("PLACE ICON",
						mAppModel.getWeb_imageUrls());

				mHashArray.add(mHashMap);
				mSyncHashArray.add(mSyncHashMap);
				mModelSyncArrayList.add(mModelSyncHashMap);

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

	public void getGasLocations(String gasURL) {

		String iconUrl = null;
		String placeName = null;
		String placeAddress = null;
		String value = null;
		mAppUtils = new AppUtils(getActivity().getApplicationContext());
		try {

			JSONObject mainJSONObject = new JSONObject(
					mAppUtils.loadJSON(gasURL));
			JSONArray mainJSONArray = mainJSONObject.getJSONArray("results");

			for (int ij = 0; ij < mainJSONArray.length(); ij++) {

				JSONObject obj = mainJSONArray.getJSONObject(ij);

				value = obj.getString("types");

				/*
				 * StringBuilder sb = new StringBuilder(value);
				 * 
				 * replaceAll(sb, "[", "]");
				 */

				Log.d("PLACE TYPES ARRAY VALUE : ", value);

				mAppModel.setPlaceType(value);

				mPlacetypeArrayList.add(new Model(mAppModel.getPlaceType()));

			}

			for (int i = 0; i < mainJSONArray.length(); i++) {

				JSONObject insideMainJSONArray = mainJSONArray.getJSONObject(i);
				if (insideMainJSONArray != null) {
					iconUrl = insideMainJSONArray.getString("icon");
					mAppModel.setSomeItem(iconUrl);

					placeName = insideMainJSONArray.getString("name");
					mAppModel.setPlaceName(placeName);

					placeAddress = insideMainJSONArray.getString("vicinity");
					mAppModel.setAddressList(placeAddress);

					infoArrayList.add(new Model(mAppModel.getSomeItem()));
					placeArrayList.add(new Model(mAppModel.getPlaceName()));
					mPlaceAddressArrayList.add(new Model(mAppModel
							.getAddressList()));

					for (int j = 0; j < mainJSONArray.length(); j++) {

						JSONObject geometryJsonObject = mainJSONArray
								.getJSONObject(j);

						if (geometryJsonObject != null) {

							JSONObject geometry = geometryJsonObject
									.getJSONObject("geometry");

							JSONObject locationJsonObject = geometry
									.getJSONObject("location");

							String latitudeString = locationJsonObject
									.getString("lat");

							String longitudeString = locationJsonObject
									.getString("lng");

							mAppModel.setLatitude(latitudeString);
							mAppModel.setLongitude(longitudeString);

							mPlacelatitudeArrayList.add(new Model(mAppModel
									.getLatitude()));
							mPlacelatitudeArrayList.add(new Model(mAppModel
									.getLongitude()));

						}

					}

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
			mProgressBar.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {

			// getGasLocations(URL);
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

			// mAdapter2 = new com.mike.adapters.ListAdapter(getActivity(),
			// infoArrayList, placeArrayList, mPlaceAddressArrayList);
			// mViewPagerAdapter = new ViewPagerAdapter(getActivity(),
			// infoArrayList, mPlaceAddressArrayList, placeArrayList,
			// mPlacelatitudeArrayList, mPlacelatitudeArrayList,
			// mPlacetypeArrayList);
			// mViewPager.setAdapter(mViewPagerAdapter);
			// mListView2.setAdapter(mAdapter2);
			// mProgressBar.setVisibility(View.INVISIBLE);

		}
	}

}
