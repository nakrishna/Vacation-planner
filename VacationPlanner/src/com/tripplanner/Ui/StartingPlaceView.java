package com.tripplanner.Ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.tripplanner.Enum.URL;
import com.tripplanner.Network.TripPlannerHttpClient;
import com.tripplanner.Presenter.StartingPlacePresenter;
import com.tripplanner.Utility.Global;
import com.tripplanner.Utility.Session;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class StartingPlaceView extends Activity {
	private LinearLayout mllBack, mllSignOut;
	private EditText medNoPassenger, medBudget;
	private Spinner mspClass, mspRegion;
	private AutoCompleteTextView matvTravelFrom, matvTravelTo;
	private TextView mtvStartingDate, mtvEndDate;
	private String TravelFrom = "", TravelTo = "", NoPassenger = "", Class = "",
			StartingDate = "", EndDate = "", Budget = "", SouCode = "",
			DesCode = "", Region = "";
	private Button btn_mSearch, btn_mPreTrip;
	private StartingPlacePresenter mStartingPlacePresenter;
	private int mYear, mMonth, mDay;
	public static List<String> list = new ArrayList<String>();
	public static List<String> code = new ArrayList<String>();
	public static List<String> list2 = new ArrayList<String>();
	public static List<String> code2 = new ArrayList<String>();
	private Session mSession;
	public static float budget = 0;
	private String[] AIRPORT;
	private String[] AIRPORT2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.starting_place);

		mllBack = (LinearLayout) findViewById(R.id.ll_back_starting_place);
		mllSignOut = (LinearLayout) findViewById(R.id.ll_sign_out_starting_place);
		btn_mSearch = (Button) findViewById(R.id.btn_search_starting_place);
		btn_mPreTrip = (Button) findViewById(R.id.btn_pre_trip_starting_place);
		matvTravelFrom = (AutoCompleteTextView) findViewById(R.id.atv_travel_from_starting_place);
		// matvTravelTo = (AutoCompleteTextView)
		// findViewById(R.id.atv_travel_to_starting_place);
		medNoPassenger = (EditText) findViewById(R.id.ed_no_passenger_starting_place);
		medBudget = (EditText) findViewById(R.id.ed_budget_starting_place);
		mspClass = (Spinner) findViewById(R.id.sp_class_starting_place);
		mspRegion = (Spinner) findViewById(R.id.sp_region_starting_place);
		mtvStartingDate = (TextView) findViewById(R.id.tv_starting_date_starting_place);
		mtvEndDate = (TextView) findViewById(R.id.tv_end_date_starting_place);

		mStartingPlacePresenter = new StartingPlacePresenter(
				StartingPlaceView.this);

		mSession = new Session(StartingPlaceView.this);

		new CityWeb().execute();
		// new RegionWeb().execute("MID-WEST");

		mllBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StartingPlaceView.this.finish();
			}
		});

		// mspRegion.setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// // TODO Auto-generated method stub
		// Log.e("+++++++++++++++", mspRegion.getSelectedItem().toString());
		// new RegionWeb().execute(mspRegion.getSelectedItem().toString());
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		mllSignOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						StartingPlaceView.this);

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
												StartingPlaceView.this, LoginView.class);
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

		btn_mSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				TravelFrom = matvTravelFrom.getText().toString().trim();
				Region = mspRegion.getSelectedItem().toString();
				// TravelTo = matvTravelTo.getText().toString().trim();
				NoPassenger = medNoPassenger.getText().toString().trim();
				Class = mspClass.getSelectedItem().toString().trim();
				StartingDate = mtvStartingDate.getText().toString().trim();
				EndDate = mtvEndDate.getText().toString().trim();
				Budget = medBudget.getText().toString().trim();
				for (int i = 0; i < list.size(); i++) {
					if (TravelFrom.equals(list.get(i))) {
						SouCode = code.get(i);
					}
				}
				for (int i = 0; i < list2.size(); i++) {
					if (TravelTo.equals(list2.get(i))) {
						DesCode = code2.get(i);
					}
				}

				if (mspRegion.getSelectedItem().toString().equals("None")) {
					Toast.makeText(getApplicationContext(),
							"Please select any Region", Toast.LENGTH_LONG).show();
				} else if (!TravelFrom.equals("") && !NoPassenger.equals("")
						&& !Class.equals("") && !StartingDate.equals("")
						&& !EndDate.equals("") && !Budget.equals("")) {
					Global.sFlightFrom = TravelFrom;
					// Global.sFlightTo = TravelTo;
					Global.sFlightEndDate = EndDate;
					Global.sRegion = Region;
					mStartingPlacePresenter.CallSearchAuth(TravelFrom, TravelTo,
							NoPassenger, Class, StartingDate, EndDate, Budget,
							SouCode, DesCode, Region);

					budget = Float.parseFloat(Budget);

				} else {
					Toast.makeText(getApplicationContext(),
							"Please fill out all the fields", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		btn_mPreTrip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent viewPrevTripView = new Intent(StartingPlaceView.this,
						ViewPrevTripView.class);
				startActivity(viewPrevTripView);
			}
		});

		mtvStartingDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final Calendar c = Calendar.getInstance();
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);

				// Launch Date Picker Dialog
				DatePickerDialog dpd = new DatePickerDialog(StartingPlaceView.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								// Display Selected date in textbox
								mtvStartingDate.setText(year + "-" + (monthOfYear + 1)
										+ "-" + dayOfMonth);
							}
						}, mYear, mMonth, mDay);
				dpd.show();
			}
		});

		mtvEndDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Calendar c = Calendar.getInstance();
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);

				// Launch Date Picker Dialog
				DatePickerDialog dpd = new DatePickerDialog(StartingPlaceView.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								// Display Selected date in textbox
								mtvEndDate.setText(year + "-" + (monthOfYear + 1) + "-"
										+ dayOfMonth);

							}
						}, mYear, mMonth, mDay);
				dpd.show();
			}
		});
	}

	public class CityWeb extends AsyncTask<String, Void, Boolean> {
		ProgressDialog dialog;
		String Code, Name;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(StartingPlaceView.this, "",
					"Please wait...");
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				JSONObject mJsonObject = new JSONObject();
				JSONObject json = TripPlannerHttpClient.SendHttpPost(
						URL.AIRPORT.getURL(), mJsonObject);
				boolean status = json.getBoolean("status");
				list.clear();
				if (status) {
					JSONArray jsonarray = new JSONArray(json.getString("Airport"));
					for (int i = 0; i < jsonarray.length(); i++) {
						JSONObject jsonObj = jsonarray.getJSONObject(i);
						Name = jsonObj.getString("name");
						Code = jsonObj.getString("code");
						list.add(Name);
						code.add(Code);
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
				AIRPORT = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					AIRPORT[i] = list.get(i);
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						StartingPlaceView.this,
						android.R.layout.simple_dropdown_item_1line, AIRPORT);
				matvTravelFrom.setAdapter(adapter);
				matvTravelFrom.setThreshold(0);
				// matvTravelTo.setAdapter(adapter);
				// matvTravelTo.setThreshold(0);
			} else {
				Toast.makeText(StartingPlaceView.this, "Something wrong!!",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	// public class RegionWeb extends AsyncTask<String, Void, Boolean> {
	// ProgressDialog dialog;
	// String Code, Name;
	//
	// protected void onPreExecute() {
	// // TODO Auto-generated method stub
	// super.onPreExecute();
	// dialog = ProgressDialog.show(StartingPlaceView.this, "",
	// "Please wait...");
	// }
	//
	// @Override
	// protected Boolean doInBackground(String... params) {
	// try {
	// JSONObject mJsonObject = new JSONObject();
	// mJsonObject.put("region", params[0]);
	// JSONObject json = TripPlannerHttpClient.SendHttpPost(
	// URL.REGION.getURL(), mJsonObject);
	// boolean status = json.getBoolean("status");
	// list2.clear();
	// if (status) {
	// JSONArray jsonarray = new JSONArray(json.getString("Airport"));
	// for (int i = 0; i < jsonarray.length(); i++) {
	// JSONObject jsonObj = jsonarray.getJSONObject(i);
	// Name = jsonObj.getString("name");
	// Code = jsonObj.getString("code");
	// list2.add(Name);
	// code2.add(Code);
	// }
	// }
	//
	// return status;
	//
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// dialog.dismiss();
	// return false;
	// }
	// }
	//
	// protected void onPostExecute(Boolean status) {
	// if (dialog.isShowing()) {
	// dialog.dismiss();
	// }
	// if (status) {
	// AIRPORT2 = new String[list2.size()];
	// for (int i = 0; i < list2.size(); i++) {
	// AIRPORT2[i] = list2.get(i);
	// }
	// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
	// StartingPlaceView.this,
	// android.R.layout.simple_dropdown_item_1line, AIRPORT2);
	// // matvTravelFrom.setAdapter(adapter);
	// // matvTravelFrom.setThreshold(0);
	// matvTravelTo.setText("");
	// matvTravelTo.setAdapter(adapter);
	// matvTravelTo.setThreshold(0);
	// } else {
	// Toast.makeText(StartingPlaceView.this, "Something wrong!!",
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// }
	// }
}
