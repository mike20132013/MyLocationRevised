/**
 * 
 */
package com.mike.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * @author mickey20142014
 * 
 */
public class AppUtils {

	Context context;
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;

	double[] gps;
	
	String address;
	private static final String NA = "-NA-";

	public AppUtils(Context newContext) {

		super();
		this.context = newContext;

	}

	public boolean servicesOK() {

		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(context);
		if (isAvailable == ConnectionResult.SUCCESS) {
			
			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {

			Toast.makeText(context, "Cant connect!!", Toast.LENGTH_SHORT)
			.show();
//			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,
//					activity, GPS_ERRORDIALOG_REQUEST);
//			dialog.show();
		} else {

			Toast.makeText(context, "Cant connect!!", Toast.LENGTH_SHORT)
					.show();

		}
		return false;

	}
	
	public String getLocation(Context context) {

		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);
		Location l = null;
		Location locationGPS = lm
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}
		gps = new double[2];
		List<Address> list = null;
		Geocoder geocoder = new Geocoder(context.getApplicationContext(),
				Locale.getDefault());
		try {
			list = geocoder.getFromLocation(l.getLatitude(), l.getLongitude(),
					3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (l != null && list.size() > 0 && list != null) {
			gps[0] = l.getLatitude();
			gps[1] = l.getLongitude();
			String zipCode = list.get(0).getPostalCode();
			String AddressLine = list.get(0).getAddressLine(0);
			String locality = list.get(0).getLocality();
			//Main
//			address = "Latitude : " + String.valueOf(gps[0]) + "\n"
//					+ "Longitude: " + String.valueOf(gps[1]) + "\n" 
//					+ "ZIP : " + zipCode + "\n"
//					+ "ADDRESS : " + AddressLine + "\n" + "LOCALITY : " + locality;
			
			if(zipCode==null){
				
			address = "ZIP : " + NA + "\n"
						+ "ADDRESS : " + AddressLine + "\n" + "LOCALITY : " + locality;
				
			}else if(AddressLine==null){
				
				address = "ZIP : " + zipCode + "\n"
						+ "ADDRESS : " + NA + "\n" + "LOCALITY : " + locality;
				
			}else if(locality==null){
				
				address = "ZIP : " + zipCode + "\n"
						+ "ADDRESS : " + AddressLine + "\n" + "LOCALITY : " + NA;
				
			}else{
				
				address = "ZIP : " + zipCode + "\n"
						+ "ADDRESS : " + AddressLine + "\n" + "LOCALITY : " + locality;
			
			}
			
			//Toast.makeText(context, address, Toast.LENGTH_LONG).show();
			Log.d("Data : ", address);

		} else if (locationGPS != null) {

			try {
				list = geocoder.getFromLocation(locationGPS.getLatitude(),
						locationGPS.getLongitude(), 3);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (list.size() > 0 && list != null) {

				String zipCode = list.get(0).getPostalCode();
				String AddressLine = list.get(0).getAddressLine(0);
				String locality = list.get(0).getLocality();
				address = "\n" + String.valueOf(gps[0]) + "\n"
						+ String.valueOf(gps[1]) + "\n" + "ZIP : " + zipCode
						+ "\n" + "ADDRESS : " + AddressLine + "\n" + "LOCALITY : "
						+ locality;
				
				
				if(zipCode==null){
					
					address = "ZIP : " + NA + "\n"
								+ "ADDRESS : " + AddressLine + "\n" + "LOCALITY : " + locality;
						
					}else if(AddressLine==null){
						
						address = "ZIP : " + zipCode + "\n"
								+ "ADDRESS : " + NA + "\n" + "LOCALITY : " + locality;
						
					}else if(locality==null){
						
						address = "ZIP : " + zipCode + "\n"
								+ "ADDRESS : " + AddressLine + "\n" + "LOCALITY : " + NA;
						
					}else{
						
						address = "ZIP : " + zipCode + "\n"
								+ "ADDRESS : " + AddressLine + "\n" + "LOCALITY : " + locality;
					
					}
	
				//Toast.makeText(context, "GPS Data: " + address,
						//Toast.LENGTH_LONG).show();
				Log.d("Gps Data : ", address);

			} else {

				Toast.makeText(context, "No available connections",
						Toast.LENGTH_LONG).show();

			}

		} else {

			Toast.makeText(context, "No Data connection", Toast.LENGTH_LONG)
					.show();
		}

		return address;
	}
	
	public String loadJSON(String someURL) {

        String json = null;
        HttpClient mHttpClient = new DefaultHttpClient();
        HttpGet mHttpGet = new HttpGet(someURL);

        try {

            HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
            StatusLine statusline = mHttpResponse.getStatusLine();
            int statusCode = statusline.getStatusCode();
            if (statusCode != 200) {

                return null;

            }
            InputStream jsonStream = mHttpResponse.getEntity().getContent();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(jsonStream));

            StringBuilder builder = new StringBuilder();
            String line;
            //jsonStream.close();
            while ((line = reader.readLine()) != null) {

                builder.append(line);

            }

            json = builder.toString();

        } catch (IOException ex) {

            ex.printStackTrace();

            return null;
        }
        mHttpClient.getConnectionManager().shutdown();
        return json;


    }

}
