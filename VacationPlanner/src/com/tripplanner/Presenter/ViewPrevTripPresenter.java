package com.tripplanner.Presenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.tripplanner.Bean.ViewPrevTripBean;
import com.tripplanner.Enum.URL;
import com.tripplanner.Network.TripPlannerHttpClient;
import com.tripplanner.Ui.ViewPrevTripView;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class ViewPrevTripPresenter {
	private ViewPrevTripView mViewPrevTripView;

	public ViewPrevTripPresenter(ViewPrevTripView mViewPrevTripView) {
		// TODO Auto-generated constructor stub
		this.mViewPrevTripView = mViewPrevTripView;
	}

	public void CallSearchAuth(String user_id) {
		// TODO Auto-generated method stub
		new SearchWeb().execute(user_id);
	}

	public class SearchWeb extends AsyncTask<String, Void, Boolean> {
		ProgressDialog dialog;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(mViewPrevTripView, "", "Please wait...");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				JSONObject mJsonObject = new JSONObject();
				mJsonObject.put("user_id", params[0]);
				Log.e("SEND", mJsonObject.toString());
				JSONObject json = TripPlannerHttpClient.SendHttpPost(
						URL.VIEW_PREV_TRIP.getURL(), mJsonObject);
				boolean status = json.getBoolean("status");
				ViewPrevTripView.mViewPrevTripBean.clear();
				if (status) {
					JSONArray my_event_list = json.getJSONArray("Trip");
					for (int i = 0; i < my_event_list.length(); i++) {
						JSONObject c = my_event_list.getJSONObject(i);
						String flight_name = c.getString("flight_name");
						String flight_date = c.getString("flight_date");
						String start_location = c.getString("start_location");
						String end_location = c.getString("end_location");
						String hotel_name = c.getString("hotel_name");

						ViewPrevTripView.mViewPrevTripBean.add(new ViewPrevTripBean(
								flight_name, flight_date, start_location, end_location,
								hotel_name));
					}
				}

				return status;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

		protected void onPostExecute(Boolean status) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			if (status) {
				mViewPrevTripView.setArr();
			} else {
				mViewPrevTripView.setArr();
			}

		}
	}

}
