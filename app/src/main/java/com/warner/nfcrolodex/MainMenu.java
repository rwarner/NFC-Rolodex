package com.warner.nfcrolodex;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainMenu extends Activity {
	
	/*
	 * Listing of Activities
	 * 
	 * On first launch MainMenu.java (Detect first launch) -> CreateBusinessCard.java then -> MainMenu.java
	 * 
	 * Select Business Cards -> BusinessCards.java
	 * Select Share your card -> ShareYourCard.java (Place for NFC to come into play)
	 * Select Edit your card -> CreateBusinessCard.java (Re-use for editing purposes)
	 * 
	 */
	
	/****To Do*****/
	/*
	 * 
	 * XML
	 * ---------
	 * Improve main menu look (buttons)
	 * 
	 * 
	 * 
	 */
	/**************/

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        checkFirstRun();	//Check if ran for the first time
        
        
    }
    
    private void checkFirstRun()
    {
    	
    	/***Checking first time ran***/
    	SharedPreferences mPreferences = this.getSharedPreferences("com.warner.nfcrolodex", Context.MODE_PRIVATE);
        boolean firstTimeRan = mPreferences.getBoolean("firstTime", true);
        
       if(firstTimeRan)
        {
    	    //We don't save the boolean here in case the user exits the app in the process
    	   
        	//Start Create Business Card
        	Intent intent = new Intent(this, CreateBusinessCard.class);
        	startActivity(intent);
        	finish();
        }
    	
    	
    }
    
    public void switchToBusinessCards(View v)
    {
    	Intent intent = new Intent(this, BusinessCards.class);
    	startActivity(intent);
    }
    
    public void switchToShareYourCard(View v)
    {
    	Intent intent = new Intent(this, ShareYourCard.class);
    	startActivity(intent);
    }
    
    public void switchToEditYourCard(View v)
    {
    	Intent intent = new Intent(this, EditYourCard.class);
    	startActivity(intent);
    }


}
