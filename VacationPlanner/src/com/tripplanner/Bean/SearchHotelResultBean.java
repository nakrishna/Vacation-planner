package com.tripplanner.Bean;

public class SearchHotelResultBean {
	private String HotelId;
	private String HotelImage;
	private String HotelName;
	private String HotelTime;
	private String RoomAvail;
	private String HotelFare;

	public SearchHotelResultBean(String HotelId, String HotelImage,
			String HotelName, String HotelTime, String RoomAvail, String HotelFare) {
		this.HotelId = HotelId;
		this.HotelImage = HotelImage;
		this.HotelName = HotelName;
		this.HotelTime = HotelTime;
		this.RoomAvail = RoomAvail;
		this.HotelFare = HotelFare;
	}

	public String getHotelId() {
		return HotelId;
	}

	public void setHotelId(String hotelId) {
		HotelId = hotelId;
	}

	public String getHotelImage() {
		return HotelImage;
	}

	public void setHotelImage(String hotelImage) {
		HotelImage = hotelImage;
	}

	public String getHotelName() {
		return HotelName;
	}

	public void setHotelName(String hotelName) {
		HotelName = hotelName;
	}

	public String getHotelTime() {
		return HotelTime;
	}

	public void setHotelTime(String hotelTime) {
		HotelTime = hotelTime;
	}

	public String getRoomAvail() {
		return RoomAvail;
	}

	public void setRoomAvail(String roomAvail) {
		RoomAvail = roomAvail;
	}

	public String getHotelFare() {
		return HotelFare;
	}

	public void setHotelFare(String hotelFare) {
		HotelFare = hotelFare;
	}

}
