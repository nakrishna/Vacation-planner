package com.tripplanner.Presenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.tripplanner.Bean.SearchResultBean;
import com.tripplanner.Enum.URL;
import com.tripplanner.Network.TripPlannerHttpClient;
import com.tripplanner.Ui.SearchResultView;
import com.tripplanner.Ui.StartingPlaceView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class StartingPlacePresenter {
	private StartingPlaceView mStartingPlaceView;
	private String tFrom = "", tTo = "", nPassenger = "", classes = "",
			sDate = "", eDate = "", sRegion = "";

	public StartingPlacePresenter(StartingPlaceView mStartingPlaceView) {
		// TODO Auto-generated constructor stub
		this.mStartingPlaceView = mStartingPlaceView;
	}

	public void CallSearchAuth(String travel_from, String travel_to,
			String no_passenger, String classes, String starting_date,
			String end_date, String budget, String sou_code, String des_code,
			String region) {
		// TODO Auto-generated method stub
		tFrom = travel_from;
		tTo = travel_to;
		nPassenger = no_passenger;
		this.classes = classes;
		sDate = starting_date;
		eDate = end_date;
		sRegion = region;
		new SearchWeb().execute(travel_from, travel_to, no_passenger, classes,
				starting_date, end_date, budget, sou_code, region);
	}

	public class SearchWeb extends AsyncTask<String, Void, Boolean> {
		private String starting_date;
		ProgressDialog dialog;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(mStartingPlaceView, "", "Please wait...");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				JSONObject mJsonObject = new JSONObject();
				mJsonObject.put("from_location", params[0]);
				mJsonObject.put("to_location", params[1]);
				mJsonObject.put("number_of_passenger", params[2]);
				mJsonObject.put("class", params[3]);
				mJsonObject.put("start_date", params[4]);
				mJsonObject.put("end_date", params[5]);
				mJsonObject.put("budget", params[6]);
				mJsonObject.put("sou_code", params[7]);
				mJsonObject.put("region", params[8]);
				Log.e("SEND", mJsonObject.toString());
				JSONObject json = TripPlannerHttpClient.SendHttpPost(
						URL.SEARCH.getURL(), mJsonObject);
				boolean status = json.getBoolean("status");
				SearchResultView.mSearchResultBean.clear();
				if (status) {
					JSONArray my_event_list = json.getJSONArray("Trip");
					for (int i = 0; i < my_event_list.length(); i++) {
						JSONObject c = my_event_list.getJSONObject(i);
						String flight_id = c.getString("id");
						String plane_name = c.getString("plane_name");
						String fare = c.getString("fare");
						String arrivalDate = c.getString("arrivalDate");
						String arrivalTime = c.getString("arrivalTime");
						starting_date = c.getString("departureDate");
						String departureTime = c.getString("departureTime");
						String origin = c.getString("origin");
						String destination = c.getString("destination");

						SearchResultView.mSearchResultBean.add(new SearchResultBean(
								flight_id, plane_name, fare, arrivalDate, arrivalTime,
								starting_date, departureTime, origin, destination));
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
				Intent searchResultView = new Intent(mStartingPlaceView,
						SearchResultView.class);
				searchResultView.putExtra("travel_from", tFrom);
				searchResultView.putExtra("region", sRegion);
				searchResultView.putExtra("no_passenger", nPassenger);
				searchResultView.putExtra("classes", classes);
				searchResultView.putExtra("starting_date", sDate);
				searchResultView.putExtra("end_date", eDate);
				mStartingPlaceView.startActivity(searchResultView);
			} else {
				Intent searchResultView = new Intent(mStartingPlaceView,
						SearchResultView.class);
				searchResultView.putExtra("travel_from", tFrom);
				searchResultView.putExtra("region", sRegion);
				searchResultView.putExtra("no_passenger", nPassenger);
				searchResultView.putExtra("classes", classes);
				searchResultView.putExtra("starting_date", sDate);
				searchResultView.putExtra("end_date", eDate);
				mStartingPlaceView.startActivity(searchResultView);
				// Toast.makeText(mStartingPlaceView,
				// "No flights found...Try Again!",
				// Toast.LENGTH_SHORT).show();
			}

		}
	}

}
