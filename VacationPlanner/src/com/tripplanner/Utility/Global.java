package com.tripplanner.Utility;

import android.content.Context;

public class Global {
	private static Context mCtx;
	public static int sUserId = 0;
	public static String sFlightId = "";
	public static String sFlightName = "";
	public static String sFlightFare = "";
	public static String sFlightFrom = "";
	public static String sFlightTo = "";
	public static String sFlightStartDate = "";
	public static String sFlightEndDate = "";
	public static String sHotelId = "";
	public static String sHotelName = "";
	public static String sHotelFare = "";
	public static String sHotelLocation = "";
	public static String sHotelRoom = "";
	public static String sHotelDate = "";
	public static String sRegion = "";

	public static void setCurrentContext(Context ctx) {
		mCtx = ctx;
	}

	public static Context getCurrentContext() {
		return mCtx;
	}
}
