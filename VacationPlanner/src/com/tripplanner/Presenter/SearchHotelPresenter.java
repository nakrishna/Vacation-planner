package com.tripplanner.Presenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.tripplanner.Bean.SearchHotelResultBean;
import com.tripplanner.Enum.URL;
import com.tripplanner.Network.TripPlannerHttpClient;
import com.tripplanner.Ui.SearchHotelResultView;
import com.tripplanner.Ui.SearchHotelView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class SearchHotelPresenter {
	private SearchHotelView mSearchHotelView;
	private String inFare;

	public SearchHotelPresenter(SearchHotelView mSearchHotelView) {
		// TODO Auto-generated constructor stub
		this.mSearchHotelView = mSearchHotelView;
	}

	public void CallSearchAuth(String location, String no_of_rooms, String date,
			String budget, String fare) {
		// TODO Auto-generated method stub
		this.inFare = fare;
		new SearchWeb().execute(location, no_of_rooms, date, budget);
	}

	public class SearchWeb extends AsyncTask<String, Void, Boolean> {
		ProgressDialog dialog;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(mSearchHotelView, "", "Please wait...");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				JSONObject mJsonObject = new JSONObject();
				mJsonObject.put("location", params[0]);
				mJsonObject.put("no_of_rooms", params[1]);
				mJsonObject.put("date", params[2]);
				mJsonObject.put("budget", params[3]);
				Log.e("SEND", mJsonObject.toString());
				JSONObject json = TripPlannerHttpClient.SendHttpPost(
						URL.HOTEL_SEARCH.getURL(), mJsonObject);
				boolean status = json.getBoolean("status");
				SearchHotelResultView.mSearchHotelResultBean.clear();
				if (status) {
					JSONArray my_event_list = json.getJSONArray("Hotel");
					for (int i = 0; i < my_event_list.length(); i++) {
						JSONObject c = my_event_list.getJSONObject(i);
						String hotel_id = c.getString("id");
						String hotel_image = c.getString("image");
						String hotel_name = c.getString("name");
						String hotel_time = c.getString("date");
						String room_avail = c.getString("no_of_room_available");
						String hotel_fare = c.getString("fare");

						SearchHotelResultView.mSearchHotelResultBean
								.add(new SearchHotelResultBean(hotel_id, hotel_image,
										hotel_name, hotel_time, room_avail, hotel_fare));
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
				Intent searchHotelResultView = new Intent(mSearchHotelView,
						SearchHotelResultView.class);
				searchHotelResultView.putExtra("fare", inFare);
				mSearchHotelView.startActivity(searchHotelResultView);

			} else {
				Intent searchHotelResultView = new Intent(mSearchHotelView,
						SearchHotelResultView.class);
				searchHotelResultView.putExtra("fare", inFare);
				mSearchHotelView.startActivity(searchHotelResultView);
				// Toast.makeText(mSearchHotelView, "No hotels found...Try Again!",
				// Toast.LENGTH_SHORT).show();
			}

		}
	}

}
