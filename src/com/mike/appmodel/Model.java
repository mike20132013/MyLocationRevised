/**
 * 
 */
package com.mike.appmodel;

/**
 * @author mickey20142014
 *
 */
public class Model {

	String someItem;
	private int ID;
	private String date;
	private String latitude;
	private String longitude;
	private String zip_code;
	private String street_address;
	private String locality;
	private String device_imageUrls;
	private String web_imageUrls;
	private String infoList;
	private String addressList;
	private String placeName;
	private String placeType;
	
	public Model(){
		
		super();
	}
	
	/**
	 * @param someItem
	 */
	public Model(String someItem) {
		super();
		this.someItem = someItem;
	}

	

	/**
	 * @return the someItem
	 */
	public String getSomeItem() {
		return someItem;
	}

	/**
	 * @param someItem the someItem to set
	 */
	public void setSomeItem(String someItem) {
		this.someItem = someItem;
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the zip_code
	 */
	public String getZip_code() {
		return zip_code;
	}

	/**
	 * @param zip_code the zip_code to set
	 */
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	/**
	 * @return the street_address
	 */
	public String getStreet_address() {
		return street_address;
	}

	/**
	 * @param street_address the street_address to set
	 */
	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}

	/**
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}

	/**
	 * @param locality the locality to set
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}

	/**
	 * @return the device_imageUrls
	 */
	public String getDevice_imageUrls() {
		return device_imageUrls;
	}

	/**
	 * @param device_imageUrls the device_imageUrls to set
	 */
	public void setDevice_imageUrls(String device_imageUrls) {
		this.device_imageUrls = device_imageUrls;
	}

	/**
	 * @return the web_imageUrls
	 */
	public String getWeb_imageUrls() {
		return web_imageUrls;
	}

	/**
	 * @param web_imageUrls the web_imageUrls to set
	 */
	public void setWeb_imageUrls(String web_imageUrls) {
		this.web_imageUrls = web_imageUrls;
	}

	/**
	 * @return the infoList
	 */
	public String getInfoList() {
		return infoList;
	}

	/**
	 * @param infoList the infoList to set
	 */
	public void setInfoList(String infoList) {
		this.infoList = infoList;
	}

	/**
	 * @return the addressList
	 */
	public String getAddressList() {
		return addressList;
	}

	/**
	 * @param addressList the addressList to set
	 */
	public void setAddressList(String addressList) {
		this.addressList = addressList;
	}

	/**
	 * @return the placeName
	 */
	public String getPlaceName() {
		return placeName;
	}

	/**
	 * @param placeName the placeName to set
	 */
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	/**
	 * @return the placeType
	 */
	public String getPlaceType() {
		return placeType;
	}

	/**
	 * @param placeType the placeType to set
	 */
	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}
	
}
