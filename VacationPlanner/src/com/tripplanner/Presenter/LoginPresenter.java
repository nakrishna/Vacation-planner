package com.tripplanner.Presenter;

import org.json.JSONException;
import org.json.JSONObject;
import com.tripplanner.Enum.URL;
import com.tripplanner.Network.TripPlannerHttpClient;
import com.tripplanner.Ui.LoginView;
import com.tripplanner.Ui.SelectTripView;
import com.tripplanner.Ui.SplashView;
import com.tripplanner.Utility.Session;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class LoginPresenter {
	private LoginView mLoginView;
	private Session session;

	public LoginPresenter(LoginView loginView) {
		// TODO Auto-generated constructor stub
		this.mLoginView = loginView;
		this.session = new Session(loginView);
	}

	public void CallLoginAuth(String UserName, String Password) {
		// TODO Auto-generated method stub
		new LoginWeb().execute(UserName, Password);
	}

	public class LoginWeb extends AsyncTask<String, Void, Boolean> {
		ProgressDialog dialog;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(mLoginView, "", "Please wait...");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				JSONObject jsonObjSend = new JSONObject();
				jsonObjSend.put("Email", params[0]);
				jsonObjSend.put("Password", params[1]);
				jsonObjSend.put("gcm_id", SplashView.GCMId);
				Log.e("SEND", jsonObjSend.toString());
				JSONObject json = TripPlannerHttpClient.SendHttpPost(
						URL.LOGIN.getURL(), jsonObjSend);
				boolean status = json.getBoolean("status");
				if (status) {
					LoginView.user_id = json.getString("user_id");
					session.createSession(json.getString("user_id"));
				}

				return status;

			} catch (JSONException e) {
				e.printStackTrace();
				dialog.dismiss();
				return false;
			}
		}

		protected void onPostExecute(Boolean status) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			if (status) {
				Intent i = new Intent(mLoginView, SelectTripView.class);
				mLoginView.startActivity(i);
			} else {
				Toast.makeText(mLoginView, "Login Failure...Try Again!!",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
