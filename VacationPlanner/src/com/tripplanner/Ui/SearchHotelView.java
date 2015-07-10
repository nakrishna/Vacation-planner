package com.tripplanner.Ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.tripplanner.Enum.URL;
import com.tripplanner.Network.TripPlannerHttpClient;
import com.tripplanner.Presenter.SearchHotelPresenter;
import com.tripplanner.Utility.Global;
import com.tripplanner.Utility.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchHotelView extends Activity {
	private LinearLayout mllBack, mllSignOut;
	private AutoCompleteTextView matv_Location;
	private EditText med_NoOfRooms;
	private TextView mtv_Date;
	private Button btn_mTryNext;
	public static List<String> list = new ArrayList<String>();
	private TextView tv_mBudget, tv_mFlightFare, tv_mBalance;
	private float Budget, Fare, Balance;
	private String inFare;
	private SearchHotelPresenter mSearchHotelPresenter;
	private String Location, NoOfRooms, Date;
	private int mYear, mMonth, mDay;
	private Session mSession;
	private String[] CITY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_hotel);
		mllBack = (LinearLayout) findViewById(R.id.ll_back_search_hotel);
		mllSignOut = (LinearLayout) findViewById(R.id.ll_sign_out_search_hotel);
		matv_Location = (AutoCompleteTextView) findViewById(R.id.atv_location_search_hotel);
		med_NoOfRooms = (EditText) findViewById(R.id.ed_no_of_rooms_search_hotel);
		mtv_Date = (TextView) findViewById(R.id.tv_from_date_search_hotel);
		btn_mTryNext = (Button) findViewById(R.id.btn_try_next_search_hotel);
		tv_mBudget = (TextView) findViewById(R.id.tv_budget_search_hotel);
		tv_mFlightFare = (TextView) findViewById(R.id.tv_flight_fare_search_hotel);
		tv_mBalance = (TextView) findViewById(R.id.tv_balance_search_hotel);

		mSearchHotelPresenter = new SearchHotelPresenter(SearchHotelView.this);
		mSession = new Session(SearchHotelView.this);

		new CityWeb().execute();

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			inFare = extras.getString("fare");
			Log.e("FARE", inFare);
		}

		mllBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SearchHotelView.this.finish();
			}
		});

		mllSignOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						SearchHotelView.this);

				// set title
				alertDialogBuilder.setTitle("Alert!");

				// set dialog message
				alertDialogBuilder
						.setMessage("Are you sure you want to Logout?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										// if this button is clicked, close
										// current activity
										mSession.createSession("none");
										Intent loginView = new Intent(
												SearchHotelView.this, LoginView.class);
										loginView.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
												| Intent.FLAG_ACTIVITY_CLEAR_TASK);
										startActivity(loginView);
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		});

		Budget = StartingPlaceView.budget;
		Fare = Float.parseFloat(inFare);
		Balance = Budget - Fare;

		tv_mBudget.setText("$ " + Budget);
		tv_mFlightFare.setText("$ " + Fare);
		tv_mBalance.setText("$ " + Balance);

		mtv_Date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final Calendar c = Calendar.getInstance();
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);

				// Launch Date Picker Dialog
				DatePickerDialog dpd = new DatePickerDialog(SearchHotelView.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								// Display Selected date in textbox
								mtv_Date.setText(year + "-" + (monthOfYear + 1) + "-"
										+ dayOfMonth);
							}
						}, mYear, mMonth, mDay);
				dpd.show();
			}
		});

		btn_mTryNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Location = matv_Location.getText().toString().trim();
				NoOfRooms = med_NoOfRooms.getText().toString().trim();
				Date = mtv_Date.getText().toString().trim();
				if (NoOfRooms.equals("")) {
					med_NoOfRooms
							.setError("Please enter the number of rooms you want");
				} else {
					Global.sHotelLocation = Location;
					Global.sHotelRoom = NoOfRooms;
					Global.sHotelDate = Date;
					med_NoOfRooms.setError(null);
					mSearchHotelPresenter.CallSearchAuth(Location, NoOfRooms, Date,
							"" + Budget, inFare);
				}

			}
		});

	}

	public class CityWeb extends AsyncTask<String, Void, Boolean> {
		ProgressDialog dialog;
		String ID, Name;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(SearchHotelView.this, "",
					"Please wait...");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				JSONObject mJsonObject = new JSONObject();
				JSONObject json = TripPlannerHttpClient.SendHttpPost(
						URL.CITY.getURL(), mJsonObject);
				boolean status = json.getBoolean("status");
				list.clear();
				if (status) {
					JSONArray jsonarray = new JSONArray(json.getString("City"));
					for (int i = 0; i < jsonarray.length(); i++) {
						JSONObject jsonObj = jsonarray.getJSONObject(i);
						Name = jsonObj.getString("name");

						list.add(Name);
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
				CITY = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					CITY[i] = list.get(i);
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						SearchHotelView.this,
						android.R.layout.simple_dropdown_item_1line, CITY);
				matv_Location.setAdapter(adapter);
				matv_Location.setThreshold(0);
			} else {
				Toast.makeText(SearchHotelView.this, "Something wrong!!",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

}
