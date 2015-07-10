package com.tripplanner.Utility;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {

	// give your server registration url here
	static final String SERVER_URL = "";

	// Google project id
	public static final String SENDER_ID = "97405235758";

	/**
	 * Tag used on log messages.
	 */
	public static final String TAG = "AndroidHive GCM";

	public static final String DISPLAY_MESSAGE_ACTION = "com.getout.View.DISPLAY_MESSAGE";

	public static final String EXTRA_MESSAGE = "message";

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * @param context
	 *           application's context.
	 * @param message
	 *           message to be displayed.
	 */
	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}
}
