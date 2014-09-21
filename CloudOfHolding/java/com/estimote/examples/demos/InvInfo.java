package com.estimote.examples.demos;

import java.util.Date;

import android.util.EventLogTags.Description;

public class InvInfo{
	
	
	
	public String iD;
	public String itemName;
	public String urlHi;
	Date timePickedUp;
	Date timeDropped;
	public String Description;
	boolean equipped;
	
	
	
	public InvInfo(String iD, String itemName, String urlHi, Date timePickedUp, Date timeDropped, String Description ,boolean equipped) {
	this.iD =iD;
	this.itemName = itemName;
	this.urlHi = urlHi;
	this.timePickedUp = timePickedUp;
	this.timeDropped = timeDropped;
	this.Description = Description;
	this.equipped = equipped;
	}
	
	public String getID(){
		return iD;
	}
	
	public void setInvInfo(String iD, String itemName, String urlHi, Date timePickedUp, Date timeDropped, String Description ,boolean equipped){
		this.iD =iD;
		this.itemName = itemName;
		this.urlHi = urlHi;
		this.timePickedUp = timePickedUp;
		this.timeDropped = timeDropped;
		this.Description = Description;
		this.equipped = equipped;
		}
		
	}


