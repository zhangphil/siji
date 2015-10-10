package hcb.tc.sj.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import hcb.tc.sj.R;
import hcb.tc.sj.R.id;
import hcb.tc.sj.R.layout;
import hcb.tc.sj.models.Account;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		boolean isLogin = Account.getUserLoginStatus(this);
		if (isLogin) {

		} else {

		}
		
		final	Activity activity=this;
		Button loginButton=(Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(activity,Home.class);
				activity.startActivity(intent);
				activity.finish();
			}
		});
	}
}
