package com.tripplanner.Ui;

import java.util.HashMap;
import com.tripplanner.Presenter.SaveFlightDetailsPresenter;
import com.tripplanner.Utility.Global;
import com.tripplanner.Utility.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SaveFlightDetailsView extends Activity {
	private LinearLayout mllBack, mllSignOut;
	private TextView tv_mBudget, tv_mFlightFare, tv_mBalance;
	private TextView tv_mFlightName, tv_mTravelFrom, tv_mTravelTo,
			tv_mStartDate;
	private Session mSession;
	private String FlightFare, Balance;
	private float iFlightFare, iBalance;
	private Button btn_mSave, btn_mTryNext;
	private SaveFlightDetailsPresenter mSaveFlightDetailsPresenter;
	private String id = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_flight_details);
		mllBack = (LinearLayout) findViewById(R.id.ll_back_save_flight_details);
		mllSignOut = (LinearLayout) findViewById(R.id.ll_sign_out_save_flight_details);
		tv_mBudget = (TextView) findViewById(R.id.tv_budget_save_flight_details);
		tv_mFlightFare = (TextView) findViewById(R.id.tv_flight_fare_save_flight_details);
		tv_mBalance = (TextView) findViewById(R.id.tv_balance_save_flight_details);
		tv_mFlightName = (TextView) findViewById(R.id.tv_flight_name_save_flight_details);
		tv_mTravelFrom = (TextView) findViewById(R.id.tv_travel_from_save_flight_details);
		tv_mTravelTo = (TextView) findViewById(R.id.tv_travel_to_save_flight_details);
		tv_mStartDate = (TextView) findViewById(R.id.tv_start_date_save_flight_details);
		btn_mSave = (Button) findViewById(R.id.btn_save_save_flight_details);
		btn_mTryNext = (Button) findViewById(R.id.btn_try_next_save_flight_details);

		mSession = new Session(SaveFlightDetailsView.this);
		mSaveFlightDetailsPresenter = new SaveFlightDetailsPresenter(
				SaveFlightDetailsView.this);

		HashMap<String, String> user = mSession.getUserDetails();
		id = user.get(Session.KEY_ID);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			FlightFare = extras.getString("flight_fare");
			Log.e("FARE", FlightFare);
		}

		iFlightFare = Float.parseFloat(FlightFare);
		iBalance = StartingPlaceView.budget - iFlightFare;
		Balance = String.valueOf(iBalance);

		mllBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SaveFlightDetailsView.this.finish();
			}
		});

		mllSignOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						SaveFlightDetailsView.this);

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
												SaveFlightDetailsView.this, LoginView.class);
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

		btn_mSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mSaveFlightDetailsPresenter.CallSaveFlightAuth(id,
						Global.sFlightId, Global.sFlightName,Global.sFlightFare, Global.sFlightFrom,
						Global.sFlightTo, Global.sFlightStartDate,
						Global.sFlightEndDate);
			}
		});

		btn_mTryNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent startingPlaceView = new Intent(SaveFlightDetailsView.this,
						StartingPlaceView.class);
				startingPlaceView.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(startingPlaceView);
			}
		});

		tv_mBudget.setText(StartingPlaceView.budget + "");
		tv_mFlightFare.setText(FlightFare);
		// tv_mHotelFare.setText(HotelFare);
		tv_mBalance.setText(Balance);
		tv_mFlightName.setText(Global.sFlightName);
		tv_mTravelFrom.setText(Global.sFlightFrom);
		tv_mTravelTo.setText(Global.sFlightTo);
		tv_mStartDate.setText("DOJ: " + Global.sFlightStartDate);
		// tv_mHotelName.setText(Global.sHotelName);
		// tv_mLocation.setText(Global.sHotelLocation);
		// tv_mNoOfRoom.setText(Global.sHotelRoom);
		// tv_mDate.setText(Global.sHotelDate);
	}
}
