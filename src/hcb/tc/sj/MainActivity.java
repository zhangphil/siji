package hcb.tc.sj;

import android.app.Activity;
import android.os.Bundle;

import hcb.tc.sj.models.Account;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		boolean isLogin = Account.getUserLoginStatus(this);
		if (isLogin) {

		} else {

		}
	}
}
