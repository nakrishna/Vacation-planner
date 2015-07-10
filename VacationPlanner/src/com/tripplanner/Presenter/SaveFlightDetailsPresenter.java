package com.tripplanner.Presenter;

import org.json.JSONException;
import org.json.JSONObject;
import com.tripplanner.Enum.URL;
import com.tripplanner.Network.TripPlannerHttpClient;
import com.tripplanner.Ui.SaveFlightDetailsView;
import com.tripplanner.Ui.StartingPlaceView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SaveFlightDetailsPresenter {
	private SaveFlightDetailsView mSaveFlightDetailsView;

	public SaveFlightDetailsPresenter(
			SaveFlightDetailsView mSaveFlightDetailsView) {
		// TODO Auto-generated constructor stub
		this.mSaveFlightDetailsView = mSaveFlightDetailsView;
	}

	public void CallSaveFlightAuth(String user_id, String flight_id,
			String flight_name, String flight_fare, String travel_from,
			String travel_to, String start_date, String end_date) {
		// TODO Auto-generated method stub
		new SaveFlightWeb().execute(user_id, flight_name, flight_fare, travel_to,
				travel_from, start_date);
	}

	public class SaveFlightWeb extends AsyncTask<String, Void, Boolean> {
		ProgressDialog dialog;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(mSaveFlightDetailsView, "",
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
				Log.e("SEND", mJsonObject.toString());
				JSONObject json = TripPlannerHttpClient.SendHttpPost(
						URL.SAVE_FLIGHT.getURL(), mJsonObject);
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
				Toast.makeText(mSaveFlightDetailsView,
						"Data saved successfully...!!", Toast.LENGTH_SHORT).show();
				Intent startingPlaceView = new Intent(mSaveFlightDetailsView,
						StartingPlaceView.class);
				startingPlaceView.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TASK);
				mSaveFlightDetailsView.startActivity(startingPlaceView);
			} else {
				Toast.makeText(mSaveFlightDetailsView,
						"Something wrong ..Try again!!", Toast.LENGTH_SHORT).show();
			}

		}
	}

}
