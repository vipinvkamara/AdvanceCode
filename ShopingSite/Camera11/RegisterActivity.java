package com.imrokraft.todo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.devspark.appmsg.AppMsg;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {
	public static ParseFile userImg;
	ParseImageView profileImageView;
	private ProgressDialog pd = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	public void generateRandomPIN(final View v){
		v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_anim));
		String randPIN=(new Random().nextInt(999999))+"";
		for (;randPIN.length() < 4;) {
			randPIN="0"+randPIN;
		}
		((EditText) findViewById(R.id.register_pin)).setText(randPIN);
		((EditText) findViewById(R.id.register_repeat_pin)).setText(randPIN);
		((EditText) findViewById(R.id.register_pin)).setInputType(InputType.TYPE_CLASS_NUMBER);
		((EditText) findViewById(R.id.register_repeat_pin)).setInputType(InputType.TYPE_CLASS_NUMBER);
		((Button)findViewById(R.id.showPIN)).setText("HIDE");
	}

	public void showPIN(final View v){
		v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_anim));
		String currentButtonText=((Button)v).getText().toString();	
		if (currentButtonText.equals("SHOW")) {
			((Button)v).setText("HIDE");
			((EditText) findViewById(R.id.register_pin)).setInputType(InputType.TYPE_CLASS_NUMBER);
			((EditText) findViewById(R.id.register_repeat_pin)).setInputType(InputType.TYPE_CLASS_NUMBER);
		}else{
			((Button)v).setText("SHOW");
			((EditText) findViewById(R.id.register_pin)).setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
			((EditText) findViewById(R.id.register_repeat_pin)).setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
		}
	}
	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, RegisterMenuActivity.class));
		finish();
		super.onBackPressed();
	}
	public void register(final View v){
		v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_anim));
		final ProgressBar pBarconnectionStatus;
		final EditText mNameField, mUsernameField,mPasswordField, mRepeatPasswordField, mEmailField, mPinField, mRepeatPinField;
		boolean errorFound=false;

		pBarconnectionStatus=(ProgressBar) findViewById(R.id.progressBarSignUp);
		mNameField = (EditText) findViewById(R.id.register_name);	
		mUsernameField = (EditText) findViewById(R.id.register_username);
		mPasswordField = (EditText) findViewById(R.id.register_password_confirm);
		mRepeatPasswordField = (EditText) findViewById(R.id.register_password);
		mEmailField = (EditText) findViewById(R.id.register_email);
		mPinField = (EditText) findViewById(R.id.register_pin);
		mRepeatPinField = (EditText) findViewById(R.id.register_repeat_pin);

		String errorFields="";
		if(mNameField.getText().length()==0 || mUsernameField.getText().length() == 0 || mPasswordField.getText().length() == 0 ||mRepeatPasswordField.getText().length() == 0 || mEmailField.getText().length() == 0 || mPinField.getText().length() == 0 || mRepeatPinField.getText().length() == 0 ){
			errorFound=true;
			if(mUsernameField.getText().length() == 0 ){
				mUsernameField.requestFocus();
				mUsernameField.setError("Required");
				if(errorFields.length()<=0){
					errorFields="Email";
				}else{
					errorFields=errorFields+", email";
				}			}
			if(mPasswordField.getText().length() == 0){
				mPasswordField.requestFocus();
				mPasswordField.setError("Required");
				if(errorFields.length()<=0){
					errorFields="Password";
				}else{
					errorFields=errorFields+", password";
				}
			}
			if(mRepeatPasswordField.getText().length() == 0){
				mRepeatPasswordField.requestFocus();
				mRepeatPasswordField.setError("Required");
				if(errorFields.length()<=0){
					errorFields="Repeat Password";
				}else{
					errorFields=errorFields+", repeat password";
				}
			}
			if(mEmailField.getText().length() == 0){
				mEmailField.requestFocus();
				mEmailField.setError("Required");
				if(errorFields.length()<=0){
					errorFields="Email";
				}else{
					errorFields=errorFields+", email";
				}
			}
			if(mPinField.getText().length() == 0){
				mPinField.requestFocus();
				mPinField.setError("Required");
				if(errorFields.length()<=0){
					errorFields="Pin";
				}else{
					errorFields=errorFields+", pin";
				}
			}
			if(mRepeatPinField.getText().length() == 0){
				mRepeatPinField.requestFocus();
				mRepeatPinField.setError("Required");
				if(errorFields.length()<=0){
					errorFields="Repeat Pin";
				}else{
					errorFields=errorFields+", repeat pin";
				}
			}else if (mPinField.getText().length()<4 || mRepeatPinField.getText().length()<4) {
				mPinField.setError("Min PIN Length 4");
				mRepeatPinField.setError("Min PIN Length 4");
			}else if (! mPinField.getText().equals(mRepeatPinField.getText())) {
				mPinField.setError("PIN Mismatch");
				mRepeatPinField.setError("PIN Mismatch");
			}


			if(errorFields.contains(",") && !errorFields.endsWith(",")){
				errorFields=errorFields.replace(""+errorFields.substring(errorFields.lastIndexOf(',')), "and "+errorFields.substring(errorFields.lastIndexOf(',')+1));
			}

			Message.showMessage((Activity) v.getContext(), AppMsg.STYLE_ALERT, "Please enter "+errorFields, AppMsg.PRIORITY_HIGH, false,false,false);
			return;			
		}

		//checking repeat password values
		if(! mPasswordField.getText().toString().equals(mRepeatPasswordField.getText().toString())){
			errorFound=true;
			Message.showMessage((Activity) v.getContext(), AppMsg.STYLE_ALERT, "Passwords do not match\nPlease check again and enter correctly", AppMsg.PRIORITY_HIGH, false,false,false);
		}

		//checking repeat pin values
		if(! mPinField.getText().toString().equals(mRepeatPinField.getText().toString())){
			Message.showMessage((Activity) v.getContext(), AppMsg.STYLE_ALERT, "Pins do not match\nPlease check again and enter correctly", AppMsg.PRIORITY_HIGH, false,false,false);
			errorFound=true;
		}

		if(errorFound){
			return;
		}
		pBarconnectionStatus.setVisibility(ProgressBar.VISIBLE);
		v.setEnabled(false);
		try{
			Parse.initialize(getApplicationContext(), TaskListFragment.APP_ID, TaskListFragment.CLIENT_KEY);
			ParseAnalytics.trackAppOpened(getIntent());
			ParseObject.registerSubclass(Task.class);
		}catch(Exception ex){
			System.out.println("Error Unable to init Parse library");
		}
		ParseUser newUser = new ParseUser();

		System.out.println("All fields Ok.\n Connecting to server");
		// Show the ProgressDialog on this thread
		this.pd = ProgressDialog.show(this, "Saving Profile Image..", "Uploading Image to server...", true, false);
		try {
			try {
				if (userImg == null) {
					//Adding dummy user image to prevent null value online database
					InputStream is = getContentResolver().openInputStream(Uri.parse("android.resource://com.imrokraft.todo/drawable/dummy_user_image"));
					Bitmap bitmapImage = BitmapFactory.decodeStream(is);
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, os);
					byte[] data = os.toByteArray();
					os.flush();
					is.close();
					os.close();
					userImg = new ParseFile(new Date().getTime()+".png", data);
				}
			} catch (Exception exx) {
				System.out.println("Unable to save dummy Image to ParseFile Object\n"+exx);
			}
			try {
				userImg.saveInBackground();
			} catch (Exception ex2) {
				System.out.println("Unable to save Image to ParseFile to parse.com\n"+ex2);
			}
			newUser.put("name", mNameField.getText().toString());
			newUser.setUsername(mUsernameField.getText().toString());
			newUser.setPassword(mPasswordField.getText().toString());
			newUser.setEmail(mEmailField.getText().toString());
			newUser.put("userpin", Integer.parseInt(mPinField.getText().toString()));
			if(userImg.isDirty())
				userImg.save();
			newUser.put("userimage", userImg);
		} catch (Exception e) {
			System.out.println("Unable to save user detials to Object during registration\n"+e);
		}

		newUser.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					pBarconnectionStatus.setVisibility(ProgressBar.INVISIBLE);
					if (RegisterActivity.this.pd!=null) {
						pd.dismiss();
					}
					startActivity(new Intent(RegisterActivity.this, MainActivity.class));
					finish();
				} else {
					// Signup failed. Look at the ParseException to see what happened.
					pBarconnectionStatus.setVisibility(ProgressBar.INVISIBLE);

					switch(e.getCode()){
					case ParseException.USERNAME_TAKEN:
						Message.showMessage((Activity) v.getContext(), AppMsg.STYLE_ALERT, "Username already in use.\n Provide any another username", AppMsg.PRIORITY_HIGH, false,false,false);
						mUsernameField.setError("Enter New Username");
						break;
					case ParseException.USERNAME_MISSING:
						Message.showMessage((Activity) v.getContext(), AppMsg.STYLE_ALERT, "Please Enter Username", AppMsg.PRIORITY_HIGH, false,false,false);
						mUsernameField.setError("Required");
						break;
					case ParseException.PASSWORD_MISSING:
						Message.showMessage((Activity) v.getContext(), AppMsg.STYLE_ALERT, "Please Enter Password", AppMsg.PRIORITY_HIGH, false,false,false);
						mPasswordField.setError("Required");
						break;
					case ParseException.INVALID_EMAIL_ADDRESS:
						Message.showMessage((Activity) v.getContext(), AppMsg.STYLE_ALERT, "Please Enter Email correctly", AppMsg.PRIORITY_HIGH, false,false,false);
						mEmailField.setError("Invalid Email");
						break;
					case ParseException.EMAIL_TAKEN:
						Message.showMessage((Activity) v.getContext(), AppMsg.STYLE_ALERT, "Email ID already registered\nPlease login or use alternate email", AppMsg.PRIORITY_HIGH, false,false,false);
						mPasswordField.setError("Required");
						break;

					default:
						Message.showMessage((Activity) v.getContext(), AppMsg.STYLE_ALERT, "Unable to connect to Imrokraft Webserver. \nPlease check your internet connection!", AppMsg.PRIORITY_HIGH, false,false,false);
						System.err.println("Error !!");
						e.printStackTrace();
						break;
					}
					v.setEnabled(true);
				}
			}
		});
	}

	public void showLogin(View v) {
		v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_anim));
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}

	public void chooseCameraClicked(View v) {
		startActivityForResult(new Intent(this, CameraActivity.class), 1);
	}

	public void choosePictureClicked(View v) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Choose Profile Image"), 2);
		//Createchooser == 
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			System.out.println("Result OK Got");
			profileImageView=(ParseImageView) findViewById(R.id.registration_userImage);
			profileImageView.setPlaceholder(getResources().getDrawable(R.drawable.dummy_user_image));

			if (requestCode == 1) {
				try {
					userImg=new ParseFile(""+new Date().getSeconds()+".JPG",CameraActivity.scaledData);
					profileImageView.setParseFile(userImg);
					profileImageView.loadInBackground();
					System.out.println("Image Set from camera");
				} catch (Exception e) {
					System.out.println("Exception from Register Activity Result - From Camera");
					e.printStackTrace();
				}
			}else if (requestCode == 2) {
				try {
					UserPicture usrPic=new UserPicture(data.getData(), getContentResolver());
					userImg=new ParseFile(""+new Date().getSeconds()+".JPG",bitmapToByteArray(usrPic.getBitmap()));
					profileImageView.setParseFile(userImg);
					profileImageView.loadInBackground();
					System.out.println("Image Set from Gallery");
				} catch (Exception e) {
					System.out.println("Exception from Register Activity Result - From Gallery");
					e.printStackTrace();
				}
			}				
			else{
				System.out.println("Invalid request Code");
			}
		}
	}
	public static byte[] bitmapToByteArray(Bitmap bmp){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		return stream.toByteArray();
	}
	/**
	 * helper to retrieve the path of an image URI
	 */
	public String getPath(Uri uri) {
		// just some safety built in 
		if( uri == null ) {
			System.out.println("Null URI obtained");
			return null;
		}
		// try to retrieve the image from the media store first
		// this will only work for images selected from gallery
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if( cursor != null ){
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		// this is our fallback here
		return uri.getPath();
	}
}