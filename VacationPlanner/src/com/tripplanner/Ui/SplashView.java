package com.tripplanner.Ui;

import com.google.android.gcm.GCMRegistrar;
import com.tripplanner.Presenter.SplashPresenter;
import static com.tripplanner.Utility.CommonUtilities.SENDER_ID;
import android.os.Bundle;
import android.util.Log;

public class SplashView extends BaseView {
	public static String GCMId = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_view);
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		if (regId.equals("")) {
			// Registration is not present, register now with GCM
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			GCMId = regId;
			Log.e("Present", GCMId);
		}
		new SplashPresenter(this);
	}

}
