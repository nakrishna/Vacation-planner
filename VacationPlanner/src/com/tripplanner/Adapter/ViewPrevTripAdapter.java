package com.tripplanner.Adapter;

import java.util.ArrayList;
import com.tripplanner.Bean.ViewPrevTripBean;
import com.tripplanner.Ui.R;
import com.tripplanner.Utility.ImageLoader;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ViewPrevTripAdapter extends ArrayAdapter<ViewPrevTripBean> {
	private ArrayList<ViewPrevTripBean> arr = new ArrayList<ViewPrevTripBean>();
	private ViewHolder mHolder;
	private ImageLoader imageLoader;
	private Activity activity;

	public ViewPrevTripAdapter(Activity activity, int textViewResourceId,
			ArrayList<ViewPrevTripBean> items) {
		super(activity, textViewResourceId, items);
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.arr = items;
		this.imageLoader = new ImageLoader(activity);
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_view_prev_trip, null);
			mHolder = new ViewHolder();
			mHolder.tv_FlightName = (TextView) v
					.findViewById(R.id.tv_flight_name_row_view_prev_trip);
			mHolder.tv_FlightDate = (TextView) v
					.findViewById(R.id.tv_flight_date_row_view_prev_trip);
			mHolder.tv_FlightFrom = (TextView) v
					.findViewById(R.id.tv_flight_from_row_view_prev_trip);
			mHolder.tv_FlightTo = (TextView) v
					.findViewById(R.id.tv_flight_to_row_view_prev_trip);
			mHolder.tv_HotelName = (TextView) v
					.findViewById(R.id.tv_hotel_name_row_view_prev_trip);
			v.setTag(mHolder);

		} else {
			mHolder = (ViewHolder) v.getTag();
		}

		mHolder.tv_FlightName.setText(arr.get(position).getFlightName());
		mHolder.tv_FlightDate.setText(arr.get(position).getFlightDate());
		mHolder.tv_FlightFrom.setText(arr.get(position).getFlightFrom());
		mHolder.tv_FlightTo.setText(arr.get(position).getFlightTo());
		if (arr.get(position).getHotelName().equals("")) {
			mHolder.tv_HotelName.setText("Hotel not booked");
		} else {
			mHolder.tv_HotelName.setText("Hotel name "
					+ arr.get(position).getHotelName());
		}

		return v;
	}

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public class ViewHolder {
		public TextView tv_FlightName;
		public TextView tv_FlightDate;
		public TextView tv_FlightFrom;
		public TextView tv_FlightTo;
		public TextView tv_HotelName;
	}

}
