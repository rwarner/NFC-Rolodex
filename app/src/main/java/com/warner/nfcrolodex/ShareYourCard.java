package com.warner.nfcrolodex;

import java.nio.charset.Charset;

import org.apache.http.util.ByteArrayBuffer;

import com.warner.nfcrolodex.database.BusinessCardsDataSource;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class ShareYourCard extends Activity implements CreateNdefMessageCallback {
	
	private SharedPreferences mPreferences;
	NfcAdapter nfcAdapter;
	
	private BusinessCardsDataSource businessCardDataSource;

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_your_card);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mPreferences = this.getSharedPreferences("com.warner.nfcrolodex", Context.MODE_PRIVATE);
        
        //Check for Availability of NFC
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null)
        {
        	Toast.makeText(this, "NFC Not availabile", Toast.LENGTH_SHORT).show();
        	finish();
        }
        
        nfcAdapter.setNdefPushMessageCallback(this, this);
        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_share_your_card, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	Intent intent = new Intent(this, MainMenu.class);
            	startActivity(intent);
            	finish();
                return true;
            case R.id.menu_settings:
            	Intent nfcIntent = new Intent();
            	nfcIntent.setAction(Settings.ACTION_NFC_SETTINGS);
            	startActivity(nfcIntent);
            	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public NdefMessage createNdefMessage(NfcEvent event)
    {
    	return setupMessageToSend();
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()))
    	{
    		processIntent(getIntent());
    	}
    }
    
    @Override
    public void onNewIntent(Intent intent)
    {
    	setIntent(intent);
    }
    
    private void processIntent(Intent intent)
    {
    	Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
    	NdefMessage msg = (NdefMessage) rawMsgs[0];
    	
    	String businessCardName = new String(msg.getRecords()[0].getPayload());
    	String businessCardEmail = new String(msg.getRecords()[1].getPayload());
    	String businessCardPhoneNumber = new String(msg.getRecords()[2].getPayload());
    	String businessCardWebsite = new String(msg.getRecords()[3].getPayload());
    	/***Strings above are transferred correctly***/
    	
    	//Open database, write the values to a new business card
    	businessCardDataSource = new BusinessCardsDataSource(this);
        businessCardDataSource.open();
    	businessCardDataSource.createBusinessCard(businessCardName, businessCardEmail, businessCardPhoneNumber, businessCardWebsite);
    	businessCardDataSource.close();
    	
    	//Show the user that it's been recieved    	
    	//Toast.makeText(this, businessCardName + "'s business card recieved", Toast.LENGTH_SHORT).show();
    	TextView instructions = (TextView) findViewById(R.id.instructionsTextView);
    	instructions.setText(businessCardName + "'s card recieved, share yours?");
    	
    	
    }
    

    
    private NdefMessage setupMessageToSend()
    {
    	//Obtain strings from our preferences
    	String stringBusinessCardName = mPreferences.getString("BusinessCardName", "");
        String stringBusinessCardEmail = mPreferences.getString("BusinessCardEmail", "");
        String stringBusinessCardPhoneNumber = mPreferences.getString("BusinessCardPhoneNumber", "");
        String stringBusinessCardWebsite = mPreferences.getString("BusinessCardWebsite", "");
    	
    	byte[] nameBytes = stringBusinessCardName.getBytes();
    	byte[] emailBytes = stringBusinessCardEmail.getBytes();
    	byte[] phoneNumberBytes = stringBusinessCardPhoneNumber.getBytes();
    	byte[] websiteBytes = stringBusinessCardWebsite.getBytes();
    	
    	
    	NdefRecord nameRecord = createMimeRecord("application/com.warner.nfcrolodex", nameBytes);
    	NdefRecord emailRecord = createMimeRecord("application/com.warner.nfcrolodex", emailBytes);
    	NdefRecord phoneNumberRecord = createMimeRecord("application/com.warner.nfcrolodex", phoneNumberBytes);
    	NdefRecord websiteRecord = createMimeRecord("application/com.warner.nfcrolodex", websiteBytes);
    	   	
    	
    	
    	NdefRecord[] records = { nameRecord, emailRecord, phoneNumberRecord, websiteRecord, NdefRecord.createApplicationRecord("com.warner.nfcrolodex") };
    	NdefMessage nfcMessageToSend = new NdefMessage(records);
    	
    	return nfcMessageToSend;
    	
    }
    
    /**
     * Creates a custom MIME type encapsulated in an NDEF record
     */
    public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
        NdefRecord mimeRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
        return mimeRecord;
    }
}
