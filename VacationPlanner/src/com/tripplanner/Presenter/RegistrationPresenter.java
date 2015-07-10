package com.tripplanner.Presenter;

import org.json.JSONException;
import org.json.JSONObject;
import com.tripplanner.Enum.URL;
import com.tripplanner.Network.TripPlannerHttpClient;
import com.tripplanner.Ui.RegistrationView;
import com.tripplanner.Ui.SplashView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class RegistrationPresenter {
	private RegistrationView mRegistrationView;

	public RegistrationPresenter(RegistrationView mRegistrationView) {
		// TODO Auto-generated constructor stub
		this.mRegistrationView = mRegistrationView;
	}

	public void CallSignUpAuth(String screenname, String email, String password,
			String phone, String country) {
		// TODO Auto-generated method stub
		new SignUpWeb().execute(screenname, email, password, phone, country);
	}

	public class SignUpWeb extends AsyncTask<String, Void, Boolean> {
		ProgressDialog dialog;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(mRegistrationView, "", "Please wait...");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				JSONObject mJsonObject = new JSONObject();
				mJsonObject.put("screen_name", params[0]);
				mJsonObject.put("email", params[1]);
				mJsonObject.put("password", params[2]);
				mJsonObject.put("phone", params[3]);
				mJsonObject.put("country", params[4]);
				mJsonObject.put("gcm_id", SplashView.GCMId);
				JSONObject json = TripPlannerHttpClient.SendHttpPost(
						URL.REGISTRATION.getURL(), mJsonObject);
				boolean status = json.getBoolean("status");
				// Global.sUserId = json.getInt("last_id");

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
				Toast.makeText(mRegistrationView, "Registration Successfull...!!",
						Toast.LENGTH_SHORT).show();
				// Intent billingInfoView = new Intent(mRegistrationView,
				// BillingInfoView.class);
				// mRegistrationView.startActivity(billingInfoView);
			} else {
				Toast.makeText(mRegistrationView,
						"UserName Already Exists.Try Again!", Toast.LENGTH_SHORT)
						.show();
			}

		}
	}

}
