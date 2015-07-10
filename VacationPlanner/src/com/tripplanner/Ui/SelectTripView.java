package com.tripplanner.Ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SelectTripView extends Activity {
	private Button btn_mNational, btn_mInternational;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_trip);
		// TODO Auto-generated method stub

		btn_mNational = (Button) findViewById(R.id.btn_national_select_trip);
		btn_mInternational = (Button) findViewById(R.id.btn_international_select_trip);

		btn_mNational.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SelectTripView.this, StartingPlaceView.class);
				startActivity(i);
			}
		});

		btn_mInternational.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(SelectTripView.this, "Wait for updated version",
						Toast.LENGTH_LONG).show();
			}
		});

	}

}
