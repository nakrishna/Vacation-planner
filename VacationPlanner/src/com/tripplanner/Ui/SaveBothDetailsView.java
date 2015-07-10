package com.tripplanner.Ui;

import java.util.HashMap;

import com.tripplanner.Presenter.SaveBothDetailsPresenter;
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

public class SaveBothDetailsView extends Activity {
	private LinearLayout mllBack, mllSignOut;
	private TextView tv_mBudget, tv_mFlightFare, tv_mHotelFare, tv_mBalance;
	private TextView tv_mFlightName, tv_mTravelFrom, tv_mTravelTo,
			tv_mStartDate;
	private TextView tv_mHotelName, tv_mLocation, tv_mNoOfRoom, tv_mDate;
	private Session mSession;
	private String HotelFare, FlightFare, Balance;
	private float iHotelFare, iFlightFare, iBalance;
	private Button btn_mSave, btn_mTryNext;
	private SaveBothDetailsPresenter mSaveBothDetailsPresenter;
	private String id = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_both_details);
		mllBack = (LinearLayout) findViewById(R.id.ll_back_save_details);
		mllSignOut = (LinearLayout) findViewById(R.id.ll_sign_out_save_details);
		tv_mBudget = (TextView) findViewById(R.id.tv_budget_save_details);
		tv_mFlightFare = (TextView) findViewById(R.id.tv_flight_fare_save_details);
		tv_mHotelFare = (TextView) findViewById(R.id.tv_hotel_fare_save_details);
		tv_mBalance = (TextView) findViewById(R.id.tv_balance_save_details);
		tv_mFlightName = (TextView) findViewById(R.id.tv_flight_name_save_details);
		tv_mTravelFrom = (TextView) findViewById(R.id.tv_travel_from_save_details);
		tv_mTravelTo = (TextView) findViewById(R.id.tv_travel_to_save_details);
		tv_mStartDate = (TextView) findViewById(R.id.tv_start_date_save_details);
		tv_mHotelName = (TextView) findViewById(R.id.tv_hotel_name_save_details);
		tv_mLocation = (TextView) findViewById(R.id.tv_location_save_details);
		tv_mNoOfRoom = (TextView) findViewById(R.id.tv_no_of_rooms_save_details);
		tv_mDate = (TextView) findViewById(R.id.tv_date_save_details);
		btn_mSave = (Button) findViewById(R.id.btn_save_save_details);
		btn_mTryNext = (Button) findViewById(R.id.btn_try_next_save_details);

		mSession = new Session(SaveBothDetailsView.this);
		mSaveBothDetailsPresenter = new SaveBothDetailsPresenter(
				SaveBothDetailsView.this);

		HashMap<String, String> user = mSession.getUserDetails();
		id = user.get(Session.KEY_ID);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			FlightFare = extras.getString("flight_fare");
			HotelFare = extras.getString("hotel_fare");
			Log.e("FARE", FlightFare + "   " + HotelFare);
		}

		iFlightFare = Float.parseFloat(FlightFare);
		iHotelFare = Float.parseFloat(HotelFare);
		iBalance = StartingPlaceView.budget - (iFlightFare + iHotelFare);
		if (iBalance < 0) {
			Balance = "You have to pay $" + Math.round(-(iBalance)) + " more";
		} else {
			Balance = "$" + iBalance;
		}

		mllBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SaveBothDetailsView.this.finish();
			}
		});

		mllSignOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						SaveBothDetailsView.this);

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
												SaveBothDetailsView.this, LoginView.class);
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
				mSaveBothDetailsPresenter.CallSaveBothAuth(id, Global.sFlightId,
						Global.sFlightName, Global.sFlightFare, Global.sFlightFrom,
						Global.sFlightTo, Global.sFlightStartDate,
						Global.sFlightEndDate, Global.sHotelId, Global.sHotelName,
						Global.sHotelFare, Global.sHotelLocation, Global.sHotelRoom,
						Global.sHotelDate);
			}
		});

		btn_mTryNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent startingPlaceView = new Intent(SaveBothDetailsView.this,
						StartingPlaceView.class);
				startingPlaceView.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(startingPlaceView);
			}
		});

		tv_mBudget.setText("$" + StartingPlaceView.budget);
		tv_mFlightFare.setText("$" + FlightFare);
		tv_mHotelFare.setText("$" + HotelFare);
		tv_mBalance.setText(Balance);
		tv_mFlightName.setText(Global.sFlightName);
		tv_mTravelFrom.setText(Global.sFlightFrom);
		tv_mTravelTo.setText(Global.sFlightTo);
		tv_mStartDate.setText("DOJ: " + Global.sFlightStartDate);
		tv_mHotelName.setText(Global.sHotelName);
		tv_mLocation.setText(Global.sHotelLocation);
		tv_mNoOfRoom.setText(Global.sHotelRoom);
		tv_mDate.setText(Global.sHotelDate);
	}

}
