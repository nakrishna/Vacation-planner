package com.tripplanner.Presenter;

import org.json.JSONException;
import org.json.JSONObject;
import com.tripplanner.Enum.URL;
import com.tripplanner.Network.TripPlannerHttpClient;
import com.tripplanner.Ui.SaveBothDetailsView;
import com.tripplanner.Ui.StartingPlaceView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SaveBothDetailsPresenter {
	private SaveBothDetailsView mSaveBothDetailsView;

	public SaveBothDetailsPresenter(SaveBothDetailsView mSaveBothDetailsView) {
		// TODO Auto-generated constructor stub
		this.mSaveBothDetailsView = mSaveBothDetailsView;
	}

	public void CallSaveBothAuth(String user_id, String flight_id,
			String flight_name, String flight_fare, String travel_from,
			String travel_to, String start_date, String end_date, String hotel_id,
			String hotel_name, String hotel_fare, String location,
			String no_of_room, String date) {
		// TODO Auto-generated method stub
		new SaveBothWeb().execute(user_id, flight_name, flight_fare, travel_to,
				travel_from, start_date, hotel_name, hotel_fare, date);
	}

	public class SaveBothWeb extends AsyncTask<String, Void, Boolean> {
		ProgressDialog dialog;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(mSaveBothDetailsView, "",
					"Please wait...");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				JSONObject mJsonObject = new JSONObject();
				mJsonObject.put("user_id", params[0]);
				mJsonObject.put("flight_name", params[1]);
				mJsonObject.put("flight_fare", params[2]);
				mJsonObject.put("flight_to", params[3]);
				mJsonObject.put("flight_from", params[4]);
				mJsonObject.put("flight_date", params[5]);
				mJsonObject.put("hotel_name", params[6]);
				mJsonObject.put("hotel_fare", params[7]);
				mJsonObject.put("hotel_date", params[8]);
				Log.e("SEND", mJsonObject.toString());
				JSONObject json = TripPlannerHttpClient.SendHttpPost(
						URL.SAVE_BOTH.getURL(), mJsonObject);
				boolean status = json.getBoolean("status");

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
				Toast.makeText(mSaveBothDetailsView,
						"Data saved successfully...!!", Toast.LENGTH_SHORT).show();
				Intent startingPlaceView = new Intent(mSaveBothDetailsView,
						StartingPlaceView.class);
				startingPlaceView.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TASK);
				mSaveBothDetailsView.startActivity(startingPlaceView);
			} else {
				Toast.makeText(mSaveBothDetailsView,
						"Something wrong ..Try again!!", Toast.LENGTH_SHORT).show();
			}

		}
	}

}
