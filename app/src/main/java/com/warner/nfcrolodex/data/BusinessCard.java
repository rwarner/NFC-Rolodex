package com.warner.nfcrolodex.data;

import java.io.Serializable;

public class BusinessCard implements Serializable{
	
	/*
	Information:

				Name
				Email
				Phone Number
				Website
	*/
	
	private long id;
	private String name, email, phoneNumber, website;
	
	/***ID***/
	public long getID()
	{
		return id;
	}
	
	public void setID(long newID)
	{
		this.id = newID;
	}
	
	
	/***Name***/
	public String getName()
	{
		return name;
	}
	
	public void setName(String newName)
	{
		this.name = newName;
	}
	/**********/
	
	/***Email***/
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String newEmail)
	{
		this.email = newEmail;
	}
	/***********/
	
	
	/***PhoneNumber***/
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String newPhoneNumber)
	{
		this.phoneNumber = newPhoneNumber;
	}
	/******************/
	
	
	/***Website***/
	public String getWebsite()
	{
		return website;
	}
	
	public void setWebsite(String newWebsite)
	{
		this.website = newWebsite;
	}
	/*************/
	
	
	//Somehow used in the ArrayAdapter for the listView?
	@Override
	public String toString()
	{
		return name;
	}
	
	

}
