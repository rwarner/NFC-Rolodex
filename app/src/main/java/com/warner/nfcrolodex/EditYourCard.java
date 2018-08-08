package com.warner.nfcrolodex;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class EditYourCard extends Activity {

	private SharedPreferences mPreferences;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_your_card);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.edit_card_icon);
        
        mPreferences = this.getSharedPreferences("com.warner.nfcrolodex", Context.MODE_PRIVATE);
        
        fillFields();
        
        
        
        
        
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    
    
    private void fillFields()
    {
    	//Get appropriate fields
    	EditText editTextName = (EditText) findViewById(R.id.editNameEditText);
    	EditText editTextEmail = (EditText) findViewById(R.id.editEmailEditText);
    	EditText editTextPhoneNumber = (EditText) findViewById(R.id.editPhoneNumberEditText);
    	EditText editTextWebsite = (EditText) findViewById(R.id.editWebsiteEditText);
    	
    	
    	//Obtain those strings from our preferences
    	String stringBusinessCardName = mPreferences.getString("BusinessCardName", "");
        String stringBusinessCardEmail = mPreferences.getString("BusinessCardEmail", "");
        String stringBusinessCardPhoneNumber = mPreferences.getString("BusinessCardPhoneNumber", "");
        String stringBusinessCardWebsite = mPreferences.getString("BusinessCardWebsite", "");
        
        //Set the fields appropriately
        editTextName.setText(stringBusinessCardName);
        editTextEmail.setText(stringBusinessCardEmail);
        editTextPhoneNumber.setText(stringBusinessCardPhoneNumber);
        editTextWebsite.setText(stringBusinessCardWebsite);

        
    }
    
    public void saveFields(View v)
    {
    	//Open the preferences
    	SharedPreferences.Editor editor = mPreferences.edit();
    	
    	//Save Name
    	EditText nameEditText = (EditText) findViewById(R.id.editNameEditText);
    	
    	//Save Email
    	EditText emailEditText = (EditText) findViewById(R.id.editEmailEditText);
    	
    	//Save Phone Number
    	EditText phoneEditText = (EditText) findViewById(R.id.editPhoneNumberEditText);
    	
    	//Save Website URL
    	EditText websiteEditText = (EditText) findViewById(R.id.editWebsiteEditText);
    	

    	
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
    		
	    	//Commit
	    	editor.commit();
	    	
	    	Toast.makeText(getApplication(), "Business Card Changed", Toast.LENGTH_SHORT).show();
	    	
	    	//Back to Main Menu
	    	Intent intent = new Intent(this, MainMenu.class);
	    	startActivity(intent);
	    	finish();
    	}
    	
    }

}
