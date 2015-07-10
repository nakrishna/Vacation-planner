package com.tripplanner.Adapter;

import java.util.ArrayList;
import com.tripplanner.Bean.SearchHotelResultBean;
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
import android.widget.ImageView;
import android.widget.TextView;

public class SearchHotelResultAdapter extends
		ArrayAdapter<SearchHotelResultBean> {
	private ArrayList<SearchHotelResultBean> arr = new ArrayList<SearchHotelResultBean>();
	private ViewHolder mHolder;
	private ImageLoader imageLoader;
	private Activity activity;

	public SearchHotelResultAdapter(Activity activity, int textViewResourceId,
			ArrayList<SearchHotelResultBean> items) {
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
			v = vi.inflate(R.layout.row_search_hotel_result, null);
			mHolder = new ViewHolder();
			mHolder.iv_HotelImage = (ImageView) v
					.findViewById(R.id.iv_hotel_image_row_search_hotel_result);
			mHolder.tv_HotelName = (TextView) v
					.findViewById(R.id.tv_hotel_name_row_search_hotel_result);
			mHolder.tv_HotelTime = (TextView) v
					.findViewById(R.id.tv_hotel_time_row_search_hotel_result);
			mHolder.tv_HotelFare = (TextView) v
					.findViewById(R.id.tv_hotel_fare_row_search_hotel_result);
			mHolder.tv_RoomAvail = (TextView) v
					.findViewById(R.id.tv_room_avail_row_search_hotel_result);
			mHolder.iv_RoomAvailImage = (ImageView) v
					.findViewById(R.id.iv_room_avail_row_search_hotel_result);
			v.setTag(mHolder);

		} else {
			mHolder = (ViewHolder) v.getTag();
		}

		imageLoader.DisplayImage(arr.get(position).getHotelImage(),
				R.drawable.loader, mHolder.iv_HotelImage);
		mHolder.iv_HotelImage.setBackground(activity.getResources().getDrawable(
				R.drawable.logo));

		mHolder.tv_HotelName.setText(arr.get(position).getHotelName());
		mHolder.tv_HotelTime.setText(arr.get(position).getHotelTime());
		if (Integer.parseInt(arr.get(position).getRoomAvail()) > 0) {
			mHolder.tv_RoomAvail.setText(arr.get(position).getRoomAvail()
					+ " room(s) available");
		} else {
			mHolder.tv_RoomAvail.setText("No room available");
		}
		mHolder.tv_HotelFare.setText("$ " + arr.get(position).getHotelFare());

		if (Integer.parseInt(arr.get(position).getRoomAvail()) > 0) {
			mHolder.iv_RoomAvailImage.setBackground(activity.getResources()
					.getDrawable(R.drawable.available));
		} else {
			mHolder.iv_RoomAvailImage.setBackground(activity.getResources()
					.getDrawable(R.drawable.not_available));
		}

		return v;
	}

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public class ViewHolder {
		public ImageView iv_HotelImage;
		public TextView tv_HotelName;
		public TextView tv_HotelTime;
		public TextView tv_RoomAvail;
		public TextView tv_HotelFare;
		public ImageView iv_RoomAvailImage;
	}

}
