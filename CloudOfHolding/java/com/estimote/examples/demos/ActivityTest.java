package com.estimote.examples.demos;

import java.util.HashMap;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.NotificationManager;
import android.app.Service;
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
		    
		    
		    
		    
		    Intent i= new Intent(this, pickupPollingService.class);
		 // potentially add data to the intent
		 //i.putExtra("KEY1", "Value to be used by the service");
		 this.startService(i); 
		 
		   
		   //startService(new Intent(this, pickupPollingService.class));
		   
			System.out.println(isMyServiceRunning(pickupPollingService.class));
			
		    
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

private boolean isMyServiceRunning(Class<?> serviceClass) {
    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
        if (serviceClass.getName().equals(service.service.getClassName())) {
            return true;
        }
    }
    return false;
}



}

