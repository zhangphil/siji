package com.wlqq.huodi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.umeng.update.UmengUpdateAgent;
import com.wlqq.huodi.R;
import com.wlqq.huodi.data.SavedCredential;
import com.wlqq.huodi.exception.ErrorCode;
import com.wlqq.huodi.handler.ToastHandler;
import com.wlqq.huodi.task.SignInTask;
import com.wlqq.huodi.task.TaskParams;
import com.wlqq.huodi.utils.HuoDiConstants;
import com.wlqq.huodi.utils.ValidationUtils;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caitiancai on 14/11/19.
 */
public class LoginActivity extends FinalActivity {

	private Activity activity;

	@ViewInject(id = R.id.etUsername)
	EditText mUsernameEditText;

	@ViewInject(id = R.id.etPassword)
	EditText mPasswordEditText;

	@ViewInject(id = R.id.btnRegister)
	Button mRegisteButton;

	@ViewInject(id = R.id.btnLogin)
	Button mLoginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;

		setContentView(R.layout.login);
		UmengUpdateAgent.setUpdateAutoPopup(true);
		UmengUpdateAgent.update(activity);

		registerListener();
	}

	private void registerListener() {
		mRegisteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			}
		});

		mLoginButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				final String username = mUsernameEditText.getText().toString();
				final String password = mPasswordEditText.getText().toString();

				if (StringUtils.isBlank(username)) {
					Toast.makeText(LoginActivity.this, getString(R.string.err_username_required), Toast.LENGTH_LONG).show();
					return;
				}

				if (StringUtils.isBlank(password)) {
					Toast.makeText(LoginActivity.this, getString(R.string.err_password_required), Toast.LENGTH_LONG).show();
					return;
				}

				if (!ValidationUtils.isValidUsername(username)) {
					Toast.makeText(LoginActivity.this, getString(R.string.err_invalid_username), Toast.LENGTH_LONG).show();
					return;
				}

				if (!ValidationUtils.isValidPassword(password)) {
					Toast.makeText(LoginActivity.this, getString(R.string.err_invalid_password), Toast.LENGTH_LONG).show();
					return;
				}

				Map<String, Object> httpParams = new HashMap<String, Object>();
				httpParams.put(HuoDiConstants.HTTP_PARAM_USERNAME, username);
				httpParams.put(HuoDiConstants.HTTP_PARAM_PASSWORD, password);

				new SignInTask(LoginActivity.this) {
					@Override
					protected String getRemoteServiceAPIUrl() {

						return HuoDiConstants.API_DRIVER_URL_LOGIN;
					}

				}.registerExceptionHandler(ErrorCode.USERNAME_OR_PWD_WRONG, ToastHandler.getInstance()).execute(new TaskParams(httpParams));
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		final SavedCredential savedCredential = SavedCredential.getInstance();
		if (savedCredential.isNotNull() && SavedCredential.AuthType.WULIUQQ.equals(savedCredential.getAuthType())) {
			mUsernameEditText.setText(savedCredential.getPrincipal());
			mPasswordEditText.setText(savedCredential.getCredential());
		}
	}
}
