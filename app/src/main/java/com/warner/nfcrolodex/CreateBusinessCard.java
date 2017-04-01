package com.warner.nfcrolodex;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class CreateBusinessCard extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_business_card);
    }
    
    public void saveFields(View v)
    {
    	//Open the preferences
    	SharedPreferences mPreferences = this.getSharedPreferences("com.warner.nfcrolodex", Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = mPreferences.edit();
    	
    	//Save Name
    	EditText nameEditText = (EditText) findViewById(R.id.createNameEditText);
    	
    	
    	//Save Email
    	EditText emailEditText = (EditText) findViewById(R.id.createEmailEditText);
    	
    	
    	//Save Phone Number
    	EditText phoneEditText = (EditText) findViewById(R.id.createPhoneNumberEditText);
    	
    	
    	//Save Website URL
    	EditText websiteEditText = (EditText) findViewById(R.id.createWebsiteEditText);
    	

    	/****Name String checks****/
    	String nameString = nameEditText.getText().toString();
    	/***************************/
    	
    	/****Email String checks****/
    	String emailString = emailEditText.getText().toString();
    	if(emailString.contains(" "))
    		emailString = emailString.replace(" ", "");
    	/***************************/
    	
    	/****Phone String Checks****/
    	String phoneString = phoneEditText.getText().toString();
    	if(phoneString.contains(" "))
    		phoneString = phoneString.replace(" ", "");
    	/***************************/
    	
    	/****Website String Checks****/
    	String websiteString = websiteEditText.getText().toString();
    	if(websiteString.contains(" "))
    		websiteString = websiteString.replace(" ", "");
    	else if (!websiteString.startsWith("http://"))
    		websiteString = "http://" + websiteString;
    	/*****************************/
    	
    	
    	if(nameString.isEmpty())
    	{
    		Toast.makeText(this,"Name is Required", Toast.LENGTH_SHORT).show();
    	}
    	else
    	{
    		editor.putString("BusinessCardName", nameString);
        	editor.putString("BusinessCardEmail", emailString);
        	editor.putString("BusinessCardPhoneNumber", phoneString);
        	editor.putString("BusinessCardWebsite", websiteString);
        	
        	
        	//Confirm that the business card has been submitted and save the boolean
        	editor.putBoolean("firstTime", false);
        	editor.commit();
        	
        	Toast.makeText(getApplication(), "Business Card Created", Toast.LENGTH_SHORT).show();
        	
        	
        	//Back to Main Menu
        	Intent intent = new Intent(this, MainMenu.class);
        	startActivity(intent);
        	finish();
    	}
    	
    	
    }


}
