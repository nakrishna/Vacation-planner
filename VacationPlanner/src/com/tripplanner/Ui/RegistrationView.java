package com.tripplanner.Ui;

import com.tripplanner.Presenter.RegistrationPresenter;
import com.tripplanner.Validator.EmailValidator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class RegistrationView extends Activity {
	private LinearLayout mllBack;
	private EditText medScreenName, medEmail, medPassword, medPhone, medCountry;
	private Button btn_mSignUp;
	private String ScreenName, Email, Password, Phone, Country;
	private RegistrationPresenter mRegistrationPresenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		// TODO Auto-generated method stub

		mllBack = (LinearLayout) findViewById(R.id.ll_back_registration);
		btn_mSignUp = (Button) findViewById(R.id.btn_sign_up_registration);
		medScreenName = (EditText) findViewById(R.id.ed_screen_name_registration);
		medEmail = (EditText) findViewById(R.id.ed_email_registration);
		medPassword = (EditText) findViewById(R.id.ed_password_registration);
		medPhone = (EditText) findViewById(R.id.ed_phone_registration);
		medCountry = (EditText) findViewById(R.id.ed_country_registration);

		mRegistrationPresenter = new RegistrationPresenter(RegistrationView.this);

		mllBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RegistrationView.this.finish();
			}
		});

		btn_mSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				ScreenName = medScreenName.getText().toString().trim();
				Email = medEmail.getText().toString().trim();
				Password = medPassword.getText().toString().trim();
				Phone = medPhone.getText().toString().trim();
				Country = medCountry.getText().toString().trim();

				// if (Password.equals(RePassword)) {
				EmailValidator emailValidator = new EmailValidator();
				if (!emailValidator.validate(Email)) {
					Toast.makeText(getApplicationContext(),
							"Please Enter Valid Email", Toast.LENGTH_SHORT).show();
				} else if (Password.length() < 8) {
					Toast.makeText(getApplicationContext(),
							"Minimum Password Length is 8", Toast.LENGTH_LONG).show();
				} else if (emailValidator.validate(Email) && Password.length() > 7
						&& ScreenName != "" && Phone != "" && Country != "") {

					mRegistrationPresenter.CallSignUpAuth(ScreenName, Email,
							Password, Phone, Country);
				} else {
					Toast.makeText(getApplicationContext(),
							"Please fill out all fields", Toast.LENGTH_LONG).show();
				}
				// } else {
				// Toast.makeText(getApplicationContext(),
				// "Password and Re-Enter Password didn't match",
				// Toast.LENGTH_LONG).show();
				// }

			}
		});

	}

}
