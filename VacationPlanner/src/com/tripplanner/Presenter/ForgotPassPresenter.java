package com.tripplanner.Presenter;

import org.json.JSONException;
import org.json.JSONObject;

import com.tripplanner.Enum.URL;
import com.tripplanner.Network.TripPlannerHttpClient;
import com.tripplanner.Ui.LoginView;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ForgotPassPresenter {
	private LoginView mLoginView;

	public ForgotPassPresenter(LoginView loginView) {
		// TODO Auto-generated constructor stub
		this.mLoginView = loginView;
	}

	public void CallLoginAuth(String UserName) {
		// TODO Auto-generated method stub
		new LoginWeb().execute(UserName);
	}

	public class LoginWeb extends AsyncTask<String, Void, Boolean> {
		// protected void onPreExecute() {
		// mLoginView.doShowLoading("Loading...");
		// }

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				JSONObject jsonObjSend = new JSONObject();
				jsonObjSend.put("Email", params[0]);
				Log.e("SEND", jsonObjSend.toString());
				JSONObject json = TripPlannerHttpClient.SendHttpPost(
						URL.FORGOT_PASS.getURL(), jsonObjSend);
				boolean status = json.getBoolean("status");

				return status;

			} catch (JSONException e) {
				e.printStackTrace();

				return false;

			}
		}

		protected void onPostExecute(Boolean status) {
			// mLoginView.doRemoveLoading();
			if (status) {
				// mLoginView.showToast("Login Successfull");
				Toast.makeText(mLoginView,
						"Please check your mail for password...", Toast.LENGTH_SHORT)
						.show();
				// mLoginView.goToDashboard(username, password);
			} else {
				// mLoginView.showToast("Username/password not matched");
				Toast.makeText(mLoginView, "This email-id is not registered...!!",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

}
