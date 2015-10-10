package hcb.tc.sj.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import hcb.tc.sj.R;

public class XiangQing extends	Activity{

	@Override
	public	void	onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_xiangqing);
		
		final	Activity activity=this;
		Toolbar toolbar=(Toolbar) findViewById(R.id.activity_xiangqing_toolbar);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
		
		toolbar.setTitle("详情");
	}
}
