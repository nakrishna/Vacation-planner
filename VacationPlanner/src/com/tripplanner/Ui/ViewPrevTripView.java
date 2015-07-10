package com.tripplanner.Ui;

import java.util.ArrayList;
import java.util.HashMap;
import com.tripplanner.Adapter.ViewPrevTripAdapter;
import com.tripplanner.Bean.ViewPrevTripBean;
import com.tripplanner.Presenter.ViewPrevTripPresenter;
import com.tripplanner.Utility.Session;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class ViewPrevTripView extends Activity {
	private LinearLayout mllBack, mllSignOut;
	private ListView lv_mShowList;
	private TextView tv_mNotFound;
	private ViewPrevTripPresenter mViewPrevTripPresenter;
	public static ArrayList<ViewPrevTripBean> mViewPrevTripBean = new ArrayList<ViewPrevTripBean>();
	private Session mSession;
	private String id = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_prev_trip);

		mllBack = (LinearLayout) findViewById(R.id.ll_back_view_prev_trip);
		mllSignOut = (LinearLayout) findViewById(R.id.ll_sign_out_view_prev_trip);
		lv_mShowList = (ListView) findViewById(R.id.lv_show_list_view_prev_trip);
		tv_mNotFound = (TextView) findViewById(R.id.tv_not_found_view_prev_trip);

		mSession = new Session(ViewPrevTripView.this);
		HashMap<String, String> user = mSession.getUserDetails();
		id = user.get(Session.KEY_ID);

		mViewPrevTripPresenter = new ViewPrevTripPresenter(ViewPrevTripView.this);

		mViewPrevTripPresenter.CallSearchAuth(id);

		mllBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ViewPrevTripView.this.finish();
			}
		});

		mllSignOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						ViewPrevTripView.this);

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
												ViewPrevTripView.this, LoginView.class);
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

	}

	public void setArr() {
		if (mViewPrevTripBean.size() > 0) {
			lv_mShowList.setVisibility(View.VISIBLE);
			tv_mNotFound.setVisibility(View.GONE);
			lv_mShowList.setAdapter(new ViewPrevTripAdapter(ViewPrevTripView.this,
					R.layout.row_view_prev_trip, mViewPrevTripBean));
		} else {
			lv_mShowList.setVisibility(View.GONE);
			tv_mNotFound.setVisibility(View.VISIBLE);
		}
	}
}
