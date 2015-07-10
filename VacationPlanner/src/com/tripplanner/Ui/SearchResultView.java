package com.tripplanner.Ui;

import java.util.ArrayList;
import com.tripplanner.Adapter.SearchResultAdapter;
import com.tripplanner.Bean.SearchResultBean;
import com.tripplanner.Utility.Global;
import com.tripplanner.Utility.Session;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class SearchResultView extends Activity {
	private LinearLayout mllBack, mllSignOut;
	private ListView lv_mSearch;
	private TextView tv_mNotFound;
	private TextView tv_mFrom, tv_mTo, tv_mPassenger, tv_mClassName,
			tv_mStartingDate, tv_mEndDate;
	public static ArrayList<SearchResultBean> mSearchResultBean = new ArrayList<SearchResultBean>();
	private String travel_from = "", travel_to = "", no_passenger = "",
			classes = "", starting_date = "", end_date = "", region = "";
	private Session mSession;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);

		mllBack = (LinearLayout) findViewById(R.id.ll_back_search_result);
		mllSignOut = (LinearLayout) findViewById(R.id.ll_sign_out_search_result);
		lv_mSearch = (ListView) findViewById(R.id.lv_search_result);
		tv_mNotFound = (TextView) findViewById(R.id.tv_not_found_search_result);
		tv_mFrom = (TextView) findViewById(R.id.tv_from_search_result);
		tv_mTo = (TextView) findViewById(R.id.tv_to_search_result);
		tv_mPassenger = (TextView) findViewById(R.id.tv_passenger_search_result);
		tv_mClassName = (TextView) findViewById(R.id.tv_class_name_search_result);
		tv_mStartingDate = (TextView) findViewById(R.id.tv_starting_date_search_result);
		tv_mEndDate = (TextView) findViewById(R.id.tv_end_date_search_result);

		mSession = new Session(SearchResultView.this);

		mllBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SearchResultView.this.finish();
			}
		});

		lv_mSearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent askSearchHotelView = new Intent(SearchResultView.this,
						AskSearchHotelView.class);
				askSearchHotelView.putExtra("fare", mSearchResultBean.get(arg2)
						.getFlightFare());
				Global.sFlightId = mSearchResultBean.get(arg2).getFlightId();
				Global.sFlightName = mSearchResultBean.get(arg2).getFlightName();
				Global.sFlightStartDate = mSearchResultBean.get(arg2).getDeptDate()
						+ " " + mSearchResultBean.get(arg2).getDeptTime();
				Global.sFlightFare = mSearchResultBean.get(arg2).getFlightFare();
				Global.sFlightTo = mSearchResultBean.get(arg2).getDestination();
				startActivity(askSearchHotelView);
			}
		});

		mllSignOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						SearchResultView.this);

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
												SearchResultView.this, LoginView.class);
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

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			travel_from = extras.getString("travel_from");
			region = extras.getString("region");
			no_passenger = extras.getString("no_passenger");
			classes = extras.getString("classes");
			starting_date = extras.getString("starting_date");
			end_date = extras.getString("end_date");
		}

		tv_mFrom.setText(travel_from);
		tv_mTo.setText(region);
		tv_mPassenger.setText("Passenger : " + no_passenger);
		tv_mClassName.setText(classes);
		tv_mStartingDate.setText(starting_date);
		tv_mEndDate.setText(end_date);

		if (mSearchResultBean.size() > 0) {
			lv_mSearch.setVisibility(View.VISIBLE);
			tv_mNotFound.setVisibility(View.GONE);
			lv_mSearch.setAdapter(new SearchResultAdapter(SearchResultView.this,
					R.layout.row_search_result, mSearchResultBean));
		} else {
			lv_mSearch.setVisibility(View.GONE);
			tv_mNotFound.setVisibility(View.VISIBLE);
		}

	}
}
