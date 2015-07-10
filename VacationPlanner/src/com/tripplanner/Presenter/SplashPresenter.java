package com.tripplanner.Presenter;

import java.util.HashMap;
import com.tripplanner.Ui.LoginView;
import com.tripplanner.Ui.SelectTripView;
import com.tripplanner.Ui.SplashView;
import com.tripplanner.Ui.StartingPlaceView;
import com.tripplanner.Utility.Session;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

public class SplashPresenter {
	private SplashView mSplashView;
	private static final int WAIT_TIME = 3000;
	private static final int HANDLER_MSG = 1;
	private Session session;
	private String id = "";

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HANDLER_MSG:

				HashMap<String, String> user = session.getUserDetails();
				id = user.get(Session.KEY_ID);

				if (id == null || id.equals("none")) {
					mSplashView.startActivity(new Intent(mSplashView.getContext(),
							LoginView.class));
					mSplashView.getActivity().finish();
				} else {
					mSplashView.startActivity(new Intent(mSplashView.getContext(),
							SelectTripView.class));
					mSplashView.getActivity().finish();
				}
				break;

			default:
				break;
			}
		}
	};

	public SplashPresenter(SplashView mSplashView) {
		this.mSplashView = mSplashView;
		this.session = new Session(mSplashView);
		callHandler();
	}

	public void callHandler() {
		handler.sendEmptyMessageDelayed(HANDLER_MSG, WAIT_TIME);
	}

}
