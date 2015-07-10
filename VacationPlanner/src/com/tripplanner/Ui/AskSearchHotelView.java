package com.tripplanner.Ui;

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

public class AskSearchHotelView extends Activity {
	private LinearLayout mllBack, mllSignOut;
	private Button btn_mYes, btn_mNo;
	private TextView tv_mBudget, tv_mFlightFare, tv_mBalance;
	private String inFare;
	private float Budget, Fare, Balance;
	private Session mSession;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ask_search_hotel);
		mllBack = (LinearLayout) findViewById(R.id.ll_back_ask_search_hotel);
		mllSignOut = (LinearLayout) findViewById(R.id.ll_sign_out_ask_search_hotel);
		btn_mYes = (Button) findViewById(R.id.btn_yes_ask_search_hotel);
		btn_mNo = (Button) findViewById(R.id.btn_no_ask_search_hotel);
		tv_mBudget = (TextView) findViewById(R.id.tv_budget_ask_search_hotel);
		tv_mFlightFare = (TextView) findViewById(R.id.tv_flight_fare_ask_search_hotel);
		tv_mBalance = (TextView) findViewById(R.id.tv_balance_ask_search_hotel);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			inFare = extras.getString("fare");
			Log.e("FARE", inFare);
		}
		
		mSession = new Session(AskSearchHotelView.this);
		
		mllBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AskSearchHotelView.this.finish();
			}
		});
		
		mllSignOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						AskSearchHotelView.this);

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
												AskSearchHotelView.this, LoginView.class);
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

		btn_mYes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent searchHotelView = new Intent(AskSearchHotelView.this,
						SearchHotelView.class);
				searchHotelView.putExtra("fare", inFare);
				startActivity(searchHotelView);
			}
		});

		btn_mNo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent saveFlightDetailsView = new Intent(AskSearchHotelView.this,
						SaveFlightDetailsView.class);
				saveFlightDetailsView.putExtra("flight_fare", inFare);
				startActivity(saveFlightDetailsView);
			}
		});
	}

}
