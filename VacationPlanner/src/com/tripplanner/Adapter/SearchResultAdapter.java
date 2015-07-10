package com.tripplanner.Adapter;

import java.util.ArrayList;
import com.tripplanner.Bean.SearchResultBean;
import com.tripplanner.Ui.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SearchResultAdapter extends ArrayAdapter<SearchResultBean> {
	private ArrayList<SearchResultBean> arr = new ArrayList<SearchResultBean>();
	private ViewHolder mHolder;
	// private ImageLoader imageLoader;
	private Activity activity;

	public SearchResultAdapter(Activity activity, int textViewResourceId,
			ArrayList<SearchResultBean> items) {
		super(activity, textViewResourceId, items);
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.arr = items;
		// this.imageLoader = new ImageLoader(activity);
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_search_result, null);
			mHolder = new ViewHolder();
			mHolder.tv_FlightName = (TextView) v
					.findViewById(R.id.tv_flight_name_row_search_result);
			mHolder.tv_FlightFare = (TextView) v
					.findViewById(R.id.tv_flight_fare_row_search_result);
			mHolder.tv_ArrDate = (TextView) v
					.findViewById(R.id.tv_arr_date_row_search_result);
			mHolder.tv_ArrTime = (TextView) v
					.findViewById(R.id.tv_arr_time_row_search_result);
			mHolder.tv_DeptDate = (TextView) v
					.findViewById(R.id.tv_dept_date_row_search_result);
			mHolder.tv_DeptTime = (TextView) v
					.findViewById(R.id.tv_dept_time_row_search_result);
			mHolder.tv_Origin = (TextView) v
					.findViewById(R.id.tv_origin_row_search_result);
			mHolder.tv_Dest = (TextView) v
					.findViewById(R.id.tv_destination_row_search_result);
			v.setTag(mHolder);

		} else {
			mHolder = (ViewHolder) v.getTag();
		}

		mHolder.tv_FlightName.setText(arr.get(position).getFlightName());
		mHolder.tv_FlightFare.setText(arr.get(position).getFlightFare());
		mHolder.tv_ArrDate.setText("Arr Date: " + arr.get(position).getArrDate());
		mHolder.tv_ArrTime.setText("Arr Time: " + arr.get(position).getArrTime());
		mHolder.tv_DeptDate.setText("Dept Date: "
				+ arr.get(position).getDeptDate());
		mHolder.tv_DeptTime.setText("Dept Time: "
				+ arr.get(position).getDeptTime());
		mHolder.tv_Origin.setText(arr.get(position).getOrigin());
		mHolder.tv_Dest.setText(arr.get(position).getDestination());

		return v;
	}

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public class ViewHolder {
		public TextView tv_FlightName;
		public TextView tv_FlightFare;
		public TextView tv_ArrDate;
		public TextView tv_ArrTime;
		public TextView tv_DeptDate;
		public TextView tv_DeptTime;
		public TextView tv_Origin;
		public TextView tv_Dest;
	}

}
