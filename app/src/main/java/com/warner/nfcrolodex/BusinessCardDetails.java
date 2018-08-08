package com.warner.nfcrolodex;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.warner.nfcrolodex.data.BusinessCard;

public class BusinessCardDetails extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_card_details);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        BusinessCard card = (BusinessCard) getIntent().getSerializableExtra("BusinessCard");
        
        TextView nameTextView = (TextView) findViewById(R.id.detailsNameTextView);
        nameTextView.setText(card.getName());
        
        TextView emailTextView = (TextView) findViewById(R.id.detailsEmailTextView);
        emailTextView.setText(card.getEmail());
        
        TextView phoneNumberTextView = (TextView) findViewById(R.id.detailsPhoneNumberTextView);
        phoneNumberTextView.setText(card.getPhoneNumber());
        
        TextView websiteTextView = (TextView) findViewById(R.id.detailsWebsiteTextView);
        websiteTextView.setText(Html.fromHtml("<a href=\"" + card.getWebsite() + "\">" + card.getWebsite() + "</a>"));
        websiteTextView.setMovementMethod(LinkMovementMethod.getInstance());
        
        
        
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_business_card_details, menu);
        return true;
    }*/

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
