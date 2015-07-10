package com.tripplanner.Ui;

import com.tripplanner.Presenter.ForgotPassPresenter;
import com.tripplanner.Presenter.LoginPresenter;
import com.tripplanner.Validator.EmailValidator;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginView extends Activity {
	private Button btn_mLogin, btn_mRegistration;
	private LoginPresenter mLoginPresenter;
	private ForgotPassPresenter mForgotPassPresenter;
	private EditText mLogin, mPassword;
	private LinearLayout mllJoin, mllForgotPass;
	public static String user_id = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		btn_mLogin = (Button) findViewById(R.id.btn_login);
		// btn_mRegistration = (Button) findViewById(R.id.btn_registration);
		mLogin = (EditText) findViewById(R.id.ed_email_login);
		mPassword = (EditText) findViewById(R.id.ed_pass_login);
		mllJoin = (LinearLayout) findViewById(R.id.ll_join_login);
		mllForgotPass = (LinearLayout) findViewById(R.id.ll_forgot_pass_login);

		mLoginPresenter = new LoginPresenter(LoginView.this);
		mForgotPassPresenter = new ForgotPassPresenter(LoginView.this);

		btn_mLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String username = mLogin.getText().toString().trim();
				String password = mPassword.getText().toString().trim();

				EmailValidator emailValidator = new EmailValidator();

				if (emailValidator.validate(username)) {
					mLoginPresenter.CallLoginAuth(username, password);
				} else {
					Toast.makeText(getApplicationContext(),
							"Please Enter a Valid Email", Toast.LENGTH_SHORT).show();
				}
			}
		});

		// btn_mRegistration.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// Intent registration = new Intent(LoginView.this,
		// RegistrationView.class);
		// startActivity(registration);
		// }
		// });

		mllJoin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent registrationView = new Intent(LoginView.this,
						RegistrationView.class);
				startActivity(registrationView);
			}
		});

		mllForgotPass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LayoutInflater li = LayoutInflater.from(LoginView.this);
				View promptsView = li.inflate(R.layout.prompts, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						LoginView.this);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final EditText medUsername = (EditText) promptsView
						.findViewById(R.id.ed_username_login_view);

				// set dialog message
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {

										// Validation

										String username = medUsername.getText()
												.toString();

										EmailValidator emailValidator = new EmailValidator();

										if (emailValidator.validate(username)) {
											mForgotPassPresenter.CallLoginAuth(username);
										} else {
											Toast.makeText(getApplicationContext(),
													"Please Enter a Valid Email",
													Toast.LENGTH_LONG).show();
										}
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										dialog.cancel();
									}
								});

				// Dialog Builder
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});

	}

}
