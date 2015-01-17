package com.senthil.homework;
import java.util.StringTokenizer;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);
	}
	public void submittClicked(View v){
		EditText edt1=((EditText)findViewById(R.id.username_register_editText));
		String username_register_saved=edt1.getText().toString();
		EditText edt2=((EditText)findViewById(R.id.firstname_register_editText));
		String firstname_register_saved=edt2.getText().toString();
		EditText edt3=((EditText)findViewById(R.id.password_register_editText));
		String password_register_saved=edt3.getText().toString();
		EditText edt4=((EditText)findViewById(R.id.email_register_editText));
		String email_register_saved=edt4.getText().toString();






		boolean values=true;






		if (username_register_saved.equals("")) {
			edt1.setError("field is required");
			values=false;
			return;
		}else {
			///username is entered and we are checking if its already taken
			String alllDetails=(PreferenceManager.getDefaultSharedPreferences(getApplicationContext())).getString("previous_values","");
			if (alllDetails.contains(username_register_saved)) {
				StringTokenizer token1=new StringTokenizer(alllDetails,"|");
				StringTokenizer token2=null;
				int no_of_users=token1.countTokens();
				String name="";
				for (int i = 0; i < no_of_users; i++) {

					token2=new StringTokenizer(token1.nextToken(),",");
					name=(token2.nextToken());		
					if (name.equalsIgnoreCase(username_register_saved)) {
						Toast.makeText(getApplicationContext(), "Username Already exists! \n Please enter new username.", Toast.LENGTH_SHORT).show();
						values=false;
						return;
					}

				}

			}
		}

		if (firstname_register_saved.equals("")) {
			edt2.setError("field is required");

			values=false;
			return;
		}
		else {	
			///usernameee is entered and we are checking if its already taken
			String alllDetails=(PreferenceManager.getDefaultSharedPreferences(getApplicationContext())).getString("previous_values","");
			if (alllDetails.contains(username_register_saved)) {
				StringTokenizer token1=new StringTokenizer(alllDetails,"|");
				StringTokenizer token2=null;
				int no_of_users=token1.countTokens();
				String name="";
				String firstname="";
				

				for (int i = 0; i < no_of_users; i++) {
					token2=new StringTokenizer(token1.nextToken(),",");
					name=(token2.nextToken());
					firstname=(token2.nextToken());
					if (firstname.equalsIgnoreCase(firstname_register_saved)) {
						Toast.makeText(getApplicationContext(), "firstname Already exists! \n Please enter new username.", Toast.LENGTH_SHORT).show();
						values=false;
						return;
					}

				}

			}
		}
		if (password_register_saved.equals("")) {
			edt3.setError("field is required");
			values=false;
			return;
		}
		else {
			///user name is entered and we are checking if its already taken
			String alllDetails=(PreferenceManager.getDefaultSharedPreferences(getApplicationContext())).getString("previous_values","");
			if (alllDetails.contains(username_register_saved)) {
				StringTokenizer token1=new StringTokenizer(alllDetails,"|");
				StringTokenizer token2=null;
				int no_of_users=token1.countTokens();
				String firstname="";
				String name="";
				String password="";
				for (int i = 0; i < no_of_users; i++) {
					token2=new StringTokenizer(token1.nextToken(),",");
					name=(token2.nextToken());	
					firstname=(token2.nextToken());
					password=(token2.nextToken());
					if (password.equalsIgnoreCase(password_register_saved)) {
						Toast.makeText(getApplicationContext(), "password Already exists! \n Please enter new username.", Toast.LENGTH_SHORT).show();
						values=false;
						return;
					}

				}

			}
		}

		if (email_register_saved.equals("")) {
			edt4.setError("feild is required");
			values=false;
			return;
		}
		else{
			String alllDetails=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("previous values", "") ;
			if (alllDetails.contains(email_register_saved)) {
				StringTokenizer token1=new StringTokenizer(alllDetails,"|");
				StringTokenizer token2=null;
				int no_of_users=token1.countTokens();
				String firstname="";
				String name="";
				String password="";
				String email;
				for (int i = 0; i <no_of_users; i++) {
					token2=new StringTokenizer(token1.nextToken(),",");
					name=(token2.nextToken());
					firstname=(token2.nextToken());
					password=(token2.nextToken());
					email=(token2.nextToken());
					if (email.contains(email_register_saved)) {
						Toast.makeText(getApplicationContext(), "Email id.already exists" , Toast.LENGTH_SHORT).show();
						values=false;
						return;
					}

				}
			}

		}



		if(values){
			SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			Editor edt=sp.edit();
			edt.putString("username_saved", username_register_saved);
			edt.putString("firstname_saved", firstname_register_saved);
			edt.putString("password_saved", password_register_saved);
			edt.putString("email_saved", email_register_saved);

			String allDetails=username_register_saved+","+firstname_register_saved+","+password_register_saved+","+email_register_saved;

			if (!sp.getString("previous_values","").equals("")) {
				allDetails=sp.getString("previous_values", "")+"|"+allDetails;
			}
			edt.putString("previous_values",allDetails);



			edt.commit();

			Toast.makeText(getApplicationContext(), "Registered Succesfully. \n Please login to continue", Toast.LENGTH_SHORT).show();

			finish();

		}





	}
}

