package com.tripplanner.Bean;

public class ViewPrevTripBean {
	private String FlightName;
	private String FlightDate;
	private String FlightFrom;
	private String FlightTo;
	private String HotelName;

	public ViewPrevTripBean(String FlightName, String FlightDate,
			String FlightFrom, String FlightTo, String HotelName) {
		this.FlightName = FlightName;
		this.FlightDate = FlightDate;
		this.FlightFrom = FlightFrom;
		this.FlightTo = FlightTo;
		this.HotelName = HotelName;
	}

	public String getFlightName() {
		return FlightName;
	}

	public void setFlightName(String flightName) {
		FlightName = flightName;
	}

	public String getFlightDate() {
		return FlightDate;
	}

	public void setFlightDate(String flightDate) {
		FlightDate = flightDate;
	}

	public String getFlightFrom() {
		return FlightFrom;
	}

	public void setFlightFrom(String flightFrom) {
		FlightFrom = flightFrom;
	}

	public String getFlightTo() {
		return FlightTo;
	}

	public void setFlightTo(String flightTo) {
		FlightTo = flightTo;
	}

	public String getHotelName() {
		return HotelName;
	}

	public void setHotelName(String hotelName) {
		HotelName = hotelName;
	}

}
