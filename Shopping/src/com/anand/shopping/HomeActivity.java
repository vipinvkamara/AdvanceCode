package com.anand.shopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends Activity {
	public static String name="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);



		TextView names=(TextView)findViewById(R.id.name_text);
		names.setText(name);



	}
	public void upload(View v)
	{
		startActivity(new Intent(this,UploadActivity.class));
	}
	

}
