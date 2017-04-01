package com.warner.nfcrolodex;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.support.v4.app.NavUtils;

import com.warner.nfcrolodex.data.BusinessCard;
import com.warner.nfcrolodex.database.*;

public class BusinessCards extends ListActivity{
	
	//Everything for our business cards database
	private BusinessCardsDataSource businessCardDataSource;
	List<BusinessCard> values;
	
	private static final int DELETE_ID = Menu.FIRST + 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_cards);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        businessCardDataSource = new BusinessCardsDataSource(this);
        businessCardDataSource.open();
        refreshData();
        
        registerForContextMenu(getListView());
        		
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_business_cards, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.addACard:
            	Intent intent = new Intent(this, ManualAddCard.class);
            	startActivity(intent);
            	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onResume()
    {
    	businessCardDataSource.open();
    	refreshData();
    	super.onResume();
    }
    
    @Override
    protected void onPause()
    {
    	businessCardDataSource.close();
    	super.onPause();
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	super.onListItemClick(l, v, position, id);
    	
    	BusinessCard card = (BusinessCard) getListAdapter().getItem(position);
    	
    	Intent intent = new Intent(this, BusinessCardDetails.class);
    	intent.putExtra("BusinessCard", card);
    	startActivity(intent);
    	
    }
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,  ContextMenuInfo menuInfo) 
    {
      super.onCreateContextMenu(menu, v, menuInfo);
      menu.add(0, DELETE_ID, 0, "Delete Card");
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) 
    {
    	ArrayAdapter<BusinessCard> adapter = (ArrayAdapter<BusinessCard>) getListAdapter();
    	
	    switch (item.getItemId()) 
	    {
	      case DELETE_ID:
	    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	        BusinessCard card = (BusinessCard) getListAdapter().getItem(info.position);
	    	businessCardDataSource.deleteBusinessCard(card.getID());
	    	adapter.remove(card);
	    	refreshData();
	        return true;
	    }
	    return super.onContextItemSelected(item);
    }
    
    private void refreshData()
    {
    	
    	values = businessCardDataSource.getAllBusinessCards();
        
        //Showing the elements in the designated list view
        ArrayAdapter<BusinessCard> adapter = new ArrayAdapter<BusinessCard>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }


}
