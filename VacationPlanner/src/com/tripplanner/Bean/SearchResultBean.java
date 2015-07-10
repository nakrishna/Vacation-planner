package com.tripplanner.Bean;

public class SearchResultBean {
	private String FlightId;
	private String FlightName;
	private String FlightFare;
	private String ArrDate;
	private String ArrTime;
	private String DeptDate;
	private String DeptTime;
	private String Origin;
	private String Destination;

	public SearchResultBean(String FlightId, String FlightName,
			String FlightFare, String ArrDate, String ArrTime, String DeptDate,
			String DeptTime, String Origin, String Destination) {
		this.FlightId = FlightId;
		this.FlightName = FlightName;
		this.FlightFare = FlightFare;
		this.ArrDate = ArrDate;
		this.ArrTime = ArrTime;
		this.DeptDate = DeptDate;
		this.DeptTime = DeptTime;
		this.Origin = Origin;
		this.Destination = Destination;
	}

	public String getFlightId() {
		return FlightId;
	}

	public void setFlightId(String flightId) {
		FlightId = flightId;
	}

	public String getFlightName() {
		return FlightName;
	}

	public void setFlightName(String flightName) {
		FlightName = flightName;
	}

	public String getFlightFare() {
		return FlightFare;
	}

	public void setFlightFare(String flightFare) {
		FlightFare = flightFare;
	}

	public String getArrDate() {
		return ArrDate;
	}

	public void setArrDate(String arrDate) {
		ArrDate = arrDate;
	}

	public String getArrTime() {
		return ArrTime;
	}

	public void setArrTime(String arrTime) {
		ArrTime = arrTime;
	}

	public String getDeptDate() {
		return DeptDate;
	}

	public void setDeptDate(String deptDate) {
		DeptDate = deptDate;
	}

	public String getDeptTime() {
		return DeptTime;
	}

	public void setDeptTime(String deptTime) {
		DeptTime = deptTime;
	}

	public String getOrigin() {
		return Origin;
	}

	public void setOrigin(String origin) {
		Origin = origin;
	}

	public String getDestination() {
		return Destination;
	}

	public void setDestination(String destination) {
		Destination = destination;
	}

}
