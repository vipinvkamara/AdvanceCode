package com.example.bluetoothexample;

import java.util.ArrayList;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	public final int REQUEST_ENABLE_BT_CONST=1;
	BroadcastReceiver mReceiver ;
	BluetoothAdapter mBluetoothAdapter;
	boolean bluetoothEnabled=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()){
			bluetoothEnabled=true;
			((TextView)findViewById(R.id.activity_main_bt_status_txtview)).setText("Bluetooth is ON");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch ( item.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(getApplicationContext(), "You pressed Settings",Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_list_pired_devices:
			Toast.makeText(getApplicationContext(), "You pressed List Paired Devices",Toast.LENGTH_SHORT).show();
			listPairedDevices();
			return true;

		case R.id.action_scan_devices:
			Toast.makeText(getApplicationContext(), "You pressed Scan",Toast.LENGTH_SHORT).show();
			scanBluetoothDevices();
			return true;

		case R.id.action_turn_on:
			Toast.makeText(getApplicationContext(), "You pressed Turn On/Off",Toast.LENGTH_SHORT).show();
			checkBluetoothStatus();
			return true;

		default:
			break;
		}

		return true;
	}

	private void checkBluetoothStatus(){
		if (mBluetoothAdapter == null) {
			// Device does not support Bluetooth
			Toast.makeText(getApplicationContext(), "No Bluetooth found",Toast.LENGTH_SHORT).show();
			bluetoothEnabled=false;
		}else if (!mBluetoothAdapter.isEnabled()) {
			startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT_CONST);
			Toast.makeText(getApplicationContext(), "Please Turn on Bluetooth", Toast.LENGTH_LONG).show();

		}else if(mBluetoothAdapter!=null && mBluetoothAdapter.isEnabled()){
			Toast.makeText(getApplicationContext(), "Turning off Bluetooth", Toast.LENGTH_LONG).show();
			mBluetoothAdapter.disable();
		}


	}

	private void listPairedDevices() {
		//Bluetooth should be available and turned on
		if(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled() && bluetoothEnabled){
			//scanning for previously paired devices
			ArrayList<BluetoothDevice> listOfDevices = new ArrayList<BluetoothDevice>(mBluetoothAdapter.getBondedDevices());
			if (listOfDevices.size() > 0) {
				DevicesListViewAdapter mArrayAdapter=new DevicesListViewAdapter(getApplicationContext(), listOfDevices);
				ListView myListview = (ListView)findViewById(R.id.activity_main_paired_listView);
				myListview .setAdapter(mArrayAdapter);
			}
		}else{
			Toast.makeText(getApplicationContext(), "Paired Device is Empty",Toast.LENGTH_LONG).show();

		}
	}

	private void scanBluetoothDevices() {
		if(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled() && bluetoothEnabled){

			ArrayList<BluetoothDevice> listOfDevices = new ArrayList<BluetoothDevice>();
			final DevicesListViewAdapter mArrayAdapter=new DevicesListViewAdapter(getApplicationContext(), listOfDevices);
			ListView myListview = (ListView)findViewById(R.id.activity_main_new_listView);
			myListview .setAdapter(mArrayAdapter);
			mBluetoothAdapter.startDiscovery();

			// Create a BroadcastReceiver for ACTION_FOUND
			if(mReceiver==null){
				mReceiver = new BroadcastReceiver() {
					@Override
					public void onReceive(Context context, Intent intent) {
						String action = intent.getAction();
						// When discovery finds a device
						if (BluetoothDevice.ACTION_FOUND.equals(action)) {
							// Get the BluetoothDevice object from the Intent
							BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
							// Add the name and address to an array adapter to show in a ListView
							mArrayAdapter.add(device);
							mArrayAdapter.notifyDataSetChanged();
						}
					}
				};
				// Register the BroadcastReceiver
				IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
				registerReceiver(mReceiver, filter);
			}
		}else{

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK && requestCode==REQUEST_ENABLE_BT_CONST){
			if(mBluetoothAdapter!=null && mBluetoothAdapter.isEnabled()){
				bluetoothEnabled=true;
				((TextView)findViewById(R.id.activity_main_bt_status_txtview)).setText("Bluetooth is ON");
			}else{
				bluetoothEnabled=false;
				((TextView)findViewById(R.id.activity_main_bt_status_txtview)).setText("Bluetooth is OFF");
			}
		}
	}

	@Override
	protected void onDestroy() {
		// UnRegister the BroadcastReceiver
		if(mReceiver!=null){
			unregisterReceiver(mReceiver );
		}
		super.onDestroy();
	}

}
