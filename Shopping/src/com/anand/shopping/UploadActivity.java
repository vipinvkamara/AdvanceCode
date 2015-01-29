package com.anand.shopping;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UploadActivity extends Activity {
	public static String name="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.upload);
	}
	public void upload(View v)
	{
		SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		int id_saved=sp.getInt("pid", 0);
		if(id_saved==0)
		{
			Editor e=sp.edit();
			e.putInt("pid", 1);
		}

		else

		{
			int id_now=id_saved+1;
			Editor e=sp.edit();
			e.putInt("pid",id_now);
		}
		id_saved=sp.getInt("pid", 0);
		TextView prdtid=(TextView)findViewById(R.id.pid_text);
		prdtid.setText(id_saved);

		EditText prdtname_entered=(EditText)findViewById(R.id.prdtname_text);
		String prdtname_enter=prdtname_entered.getText().toString();

		EditText prdtqty_entered=(EditText)findViewById(R.id.prdtqnty_text);
		String prdtqty_enter=prdtqty_entered.getText().toString();
		int prdtqty=Integer.parseInt(prdtqty_enter);

		EditText prdtprice_entered=(EditText)findViewById(R.id.prdtprice_text);
		String prdtprice_enter=prdtprice_entered.getText().toString();
		float prdtprc=Float.parseFloat(prdtprice_enter);

		Editor e=sp.edit();
		e.putString("prdt_name",prdtname_enter);
		e.putInt("prdt_qty",prdtqty);
		e.putFloat("prdt_price",prdtprc);

		Toast.makeText(getApplicationContext(),"Upload Succesfully",Toast.LENGTH_LONG).show();
	}
	public void home(View v)
	{
		startActivity(new Intent(this,HomeActivity.class));
	}

}
