package com.tripplanner.Ui;

import java.util.ArrayList;
import com.tripplanner.Adapter.SearchHotelResultAdapter;
import com.tripplanner.Bean.SearchHotelResultBean;
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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchHotelResultView extends Activity {
	private LinearLayout mllBack, mllSignOut;
	private ListView lv_mList;
	private TextView tv_mNotFound;
	private String inFare;
	private TextView tv_mBudget, tv_mFlightFare, tv_mBalance;
	private float Budget, Fare, Balance;
	public static ArrayList<SearchHotelResultBean> mSearchHotelResultBean = new ArrayList<SearchHotelResultBean>();
	private Session mSession;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_hotel_result);
		mllBack = (LinearLayout) findViewById(R.id.ll_back_search_hotel_result);
		mllSignOut = (LinearLayout) findViewById(R.id.ll_sign_out_search_hotel_result);
		lv_mList = (ListView) findViewById(R.id.lv_list_search_hotel_result);
		tv_mNotFound = (TextView) findViewById(R.id.tv_not_found_search_hotel_result);
		tv_mBudget = (TextView) findViewById(R.id.tv_budget_search_hotel_result);
		tv_mFlightFare = (TextView) findViewById(R.id.tv_flight_fare_search_hotel_result);
		tv_mBalance = (TextView) findViewById(R.id.tv_balance_search_hotel_result);

		mSession = new Session(SearchHotelResultView.this);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			inFare = extras.getString("fare");
			Log.e("FARE", inFare);
		}

		mllBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SearchHotelResultView.this.finish();
			}
		});

		mllSignOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						SearchHotelResultView.this);

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
												SearchHotelResultView.this, LoginView.class);
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

		lv_mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent saveDetailsView = new Intent(SearchHotelResultView.this,
						SaveBothDetailsView.class);
				saveDetailsView.putExtra("flight_fare", inFare);
				saveDetailsView.putExtra("hotel_fare",
						mSearchHotelResultBean.get(arg2).getHotelFare());
				Global.sHotelId = mSearchHotelResultBean.get(arg2).getHotelId();
				Global.sHotelName = mSearchHotelResultBean.get(arg2).getHotelName();
				Global.sHotelFare = mSearchHotelResultBean.get(arg2).getHotelFare();
				startActivity(saveDetailsView);
			}
		});

		Budget = StartingPlaceView.budget;
		Fare = Float.parseFloat(inFare);
		Balance = Budget - Fare;

		tv_mBudget.setText("$ " + Budget);
		tv_mFlightFare.setText("$ " + Fare);
		tv_mBalance.setText("$ " + Balance);

		if (mSearchHotelResultBean.size() > 0) {
			lv_mList.setVisibility(View.VISIBLE);
			tv_mNotFound.setVisibility(View.GONE);
			lv_mList.setAdapter(new SearchHotelResultAdapter(
					SearchHotelResultView.this, R.layout.row_search_hotel_result,
					mSearchHotelResultBean));
		} else {
			lv_mList.setVisibility(View.GONE);
			tv_mNotFound.setVisibility(View.VISIBLE);
		}

	}

}
