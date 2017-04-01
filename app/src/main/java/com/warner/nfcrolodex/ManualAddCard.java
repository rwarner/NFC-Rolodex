package com.warner.nfcrolodex;

import com.warner.nfcrolodex.database.BusinessCardsDataSource;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class ManualAddCard extends Activity {

	private BusinessCardsDataSource businessCardDataSource;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add_card);
        getActionBar().setDisplayHomeAsUpEnabled(true);        
        getActionBar().setIcon(R.drawable.add_person);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_manual_add_card, menu);
        return true;
    }*/

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    
    
    public void saveFields(View v)
    {
    	EditText nameEditText = (EditText) findViewById(R.id.manualNameEditText);
    	EditText emailEditText = (EditText) findViewById(R.id.manualEmailEditText);
    	EditText phoneEditText = (EditText) findViewById(R.id.manualPhoneNumberEditText);
    	EditText websiteEditText = (EditText) findViewById(R.id.manualWebsiteEditText);
    	
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
    		//Open database, write the values to a new business card
	    	businessCardDataSource = new BusinessCardsDataSource(this);
	        businessCardDataSource.open();
	    	businessCardDataSource.createBusinessCard(nameString, emailString, phoneString, websiteString);
	    	businessCardDataSource.close();
	    	
	    	//Finish the activity and return to the list
	    	NavUtils.navigateUpFromSameTask(this);
	    	finish();
    	}
    }
}
