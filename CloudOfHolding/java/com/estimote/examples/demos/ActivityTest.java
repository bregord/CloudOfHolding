package com.estimote.examples.demos;

import java.util.HashMap;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.view.MenuItem;



public class ActivityTest extends Activity{

	  private static final String TAG = NotifyDemoActivity.class.getSimpleName();
	  private static final int NOTIFICATION_ID = 123;

	  private BeaconManager beaconManager;
	  private NotificationManager notificationManager;
	  private Region findingRegion;
	  private Region lostRegion;
	  
	  public HashMap UUIDs = new HashMap();
	   
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.main);
		    getActionBar().setDisplayHomeAsUpEnabled(true);
		    
		   Log.d("WORKING", " IT IS WORKING");
		
		/*    

		    Beacon beacon = getIntent().getParcelableExtra(ListBeaconsActivity.EXTRAS_BEACON);
		    
		    findingRegion = new Region("regionId", beacon.getProximityUUID(), beacon.getMajor(), beacon.getMinor());
		    lostRegion = new Region("regionId", beacon.getProximityUUID(), beacon.getMajor(), beacon.getMinor());
		   
		    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		    beaconManager = new BeaconManager(this);

		   
		    
		    double range = Utils.computeAccuracy(beacon);
		    while(true){
		    
		    	if(range < 0.5){
		    	//This is where we prompt to see if we want to pick it up.
		    	//promptPickUp
		    	
		    	
		    } else if (range > 2) {
		    	//in posession?
		    
		    }
		    
		    }
		    
		    */
		    
	}
	
	void onStart(Bundle savedInstanceState){
		
		
		
		
	}
	

	void onPause(Bundle savedInstanceState){}
	

	void onDestroy(Bundle savedInstanceState){}
	



@Override
public boolean onOptionsItemSelected(MenuItem item) {
  if (item.getItemId() == android.R.id.home) {
    finish();
    return true;
  }
  return super.onOptionsItemSelected(item);
}
}

