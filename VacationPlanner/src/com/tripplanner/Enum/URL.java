package com.tripplanner.Enum;

public enum URL {	
	LOGIN("http://android.demostock.co.in/trip_planner/android/login_check"),
	FORGOT_PASS("http://android.demostock.co.in/trip_planner/android/forgot_pass"),
	REGISTRATION("http://android.demostock.co.in/trip_planner/android/user_registration"),
	SAVE_BOTH("http://android.demostock.co.in/trip_planner/android/save_flight_hotel"),
	SAVE_FLIGHT("http://android.demostock.co.in/trip_planner/android/save_flight"),
	CITY("http://android.demostock.co.in/trip_planner/android/get_hotel_city_list"),
	AIRPORT("http://android.demostock.co.in/trip_planner/android/get_airport_list"),
	REGION("http://android.demostock.co.in/trip_planner/android/get_airport_list_region"),
	SEARCH("http://android.demostock.co.in/trip_planner/android/flight_api2"),
	VIEW_PREV_TRIP("http://android.demostock.co.in/trip_planner/android/show_trip_history"),
	HOTEL_SEARCH("http://android.demostock.co.in/trip_planner/android/search_hotel"),
	BILLING("http://www.allcurbappeal.com/development/allcurbappeal/android_service/user-registration-step2.php");
	public String mURL;
	
	URL(String mURL) {
		this.mURL = mURL;
	}
	
	public String getURL() {
		return mURL;
	}
}
