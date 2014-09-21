package com.estimote.examples.demos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

public class pickupPollingService extends Service {

	
	public BeaconManager beaconManager;
	public Region region = new Region("around",null,null,null);
	public BeaconManager.RangingListener listener;
	public List<Beacon> beacons;
	public HashMap<String,String> inventory;
	
	
	public void onCreate(Bundle savedInstanceState){
	
	try {
		beaconManager.startRanging(region);
		beaconManager.setRangingListener(listener);
		
		listener.onBeaconsDiscovered(region, beacons);
		
		
		
		
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	
	

	}
	
	public void onStart(Bundle savedInstanceState){
	
		
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		
		intent.getExtras();
		
		
		return startId;
	    
		
		
	}
	
	
	public void pollForPickup(){
		
		for(Beacon b : beacons){
			
			double range = Utils.computeAccuracy(b);
			if(range < 0.5 && !inventory.containsValue(b.getProximityUUID()) ){
				//THEN IT NEEDS TO BE ADDED TO 
				
				System.out.println("IT WORKS!!!!");
			}
			
		}
		
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
