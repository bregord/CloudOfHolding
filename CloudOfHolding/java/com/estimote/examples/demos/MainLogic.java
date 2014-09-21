package com.estimote.examples.demos;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.estimote.sdk.utils.L;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class MainLogic extends Activity{

	 private static final String TAG = ListBeaconsActivity.class.getSimpleName();

	  public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
	  public static final String EXTRAS_BEACON = "extrasBeacon";

	  private static final int REQUEST_ENABLE_BT = 1234;
	  private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

	  private BeaconManager beaconManager;
	  private LeDeviceListAdapter adapter;
	  
	  ArrayList<InvInfo> inventoryData = new ArrayList<InvInfo>();
	  
	  InvInfo entry1 = new InvInfo("E1:DF:62:FE:C5:79","Wallet",null,null,null,null, true);
	  InvInfo entry2 = new InvInfo("F1:EC:36:0F:0F:05","Keys",null,null,null,null, true);
	  InvInfo entry3 = new InvInfo("D9:86:3E:79:95:EF9","Food",null,null,null,null, true);
	  
	 
	  
	  
	  public void checkForItems(List<Beacon> beacons){
		  
		  
		  for(Beacon b: beacons){
			for(InvInfo info:  inventoryData) { 
			  if(Utils.computeAccuracy(b) < 0.5 && info.getID() == b.getProximityUUID()) {
				  
				  //If it is unequipped, but still in our inventory, there is a fragment that allows you to reequip it.
				  return; 
			  
			  } 
				
			  }
			}
		  System.out.println("Looks like your thing isn't in the inventory!");
		  
		  }
		  
	  
	  
	  
	  
	  
	  public void toServer(){
		  
		  
	  }
	  
	  
	  public void checkForLost(List<Beacon> beacons){
		  for(Beacon b: beacons){
			  for(InvInfo info:  inventoryData) { 
			  
				  if(Utils.computeAccuracy(b) > 2 && info.getID()== b.getProximityUUID()) {
				  

					  new AlertDialog.Builder(this)
					  .setTitle("Item has fallen out of inventory")
					  .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int which){
					  }
					  }).setIcon(android.R.drawable.ic_dialog_alert).show();
			  
				  }
			  
			  }
		  }
		  }
		  
	  
	  

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		  
		  inventoryData.add(entry1);
		  inventoryData.add(entry2);
		  inventoryData.add(entry3);
		  
		  
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    getActionBar().setDisplayHomeAsUpEnabled(true);

	    // Configure device list.
	    adapter = new LeDeviceListAdapter(this);
	    //ListView list = (ListView) findViewById(R.id.device_list);
	    //list.setAdapter(adapter);
	    //list.setOnItemClickListener(createOnItemClickListener());

	    // Configure verbose debug logging.
	    L.enableDebugLogging(true);

	    // Configure BeaconManager.
	    beaconManager = new BeaconManager(this);
	    beaconManager.setRangingListener(new BeaconManager.RangingListener() {
	      @Override
	      public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
	        // Note that results are not delivered on UI thread.
	        runOnUiThread(new Runnable() {
	          @Override
	          public void run() {
	        	  checkForItems(beacons);
	        	  
	        	  checkForLost(beacons);
	        	  
	            // Note that beacons reported here are already sorted by estimated
	            // distance between device and beacon.
	            
	        	  //getActionBar().setSubtitle("Found beacons: " + beacons.size());
	            //adapter.replaceWith(beacons);
	          }
	        });
	      }
	    });
	  }

	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.scan_menu, menu);
	    MenuItem refreshItem = menu.findItem(R.id.refresh);
	    refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
	    return true;
	  }

	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    if (item.getItemId() == android.R.id.home) {
	      finish();
	      return true;
	    }
	    return super.onOptionsItemSelected(item);
	  }
	  
	  

	  @Override
	  protected void onDestroy() {
	    beaconManager.disconnect();

	    super.onDestroy();
	  }

	  @Override
	  protected void onStart() {
	    super.onStart();

	    // Check if device supports Bluetooth Low Energy.
	    if (!beaconManager.hasBluetooth()) {
	      Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
	      return;
	    }

	    // If Bluetooth is not enabled, let user enable it.
	    if (!beaconManager.isBluetoothEnabled()) {
	      Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	      startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	    } else {
	      connectToService();
	    }
	  }

	  @Override
	  protected void onStop() {
	    try {
	      beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
	    } catch (RemoteException e) {
	      Log.d(TAG, "Error while stopping ranging", e);
	    }

	    super.onStop();
	  }

	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_ENABLE_BT) {
	      if (resultCode == Activity.RESULT_OK) {
	        connectToService();
	      } else {
	        Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
	        getActionBar().setSubtitle("Bluetooth not enabled");
	      }
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	  }

	  private void connectToService() {
	    getActionBar().setSubtitle("Scanning...");
	    adapter.replaceWith(Collections.<Beacon>emptyList());
	    beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
	      @Override
	      public void onServiceReady() {
	        try {
	          beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
	        } catch (RemoteException e) {
	          Toast.makeText(MainLogic.this, "Cannot start ranging, something terrible happened",
	              Toast.LENGTH_LONG).show();
	          Log.e(TAG, "Cannot start ranging", e);
	        }
	      }
	    });
	  }

	  private AdapterView.OnItemClickListener createOnItemClickListener() {
	    return new AdapterView.OnItemClickListener() {
	      @Override
	      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        if (getIntent().getStringExtra(EXTRAS_TARGET_ACTIVITY) != null) {
	          try {
	            Class<?> clazz = Class.forName(getIntent().getStringExtra(EXTRAS_TARGET_ACTIVITY));
	            Intent intent = new Intent(MainLogic.this, clazz);
	            intent.putExtra(EXTRAS_BEACON, adapter.getItem(position));
	            startActivity(intent);
	          } catch (ClassNotFoundException e) {
	            Log.e(TAG, "Finding class by name failed", e);
	          }
	        }
	      }
	    };
	  }
	
}

