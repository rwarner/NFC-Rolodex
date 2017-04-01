package com.warner.nfcrolodex.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.warner.nfcrolodex.data.*;


public class BusinessCardsDataSource {
	
	//Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_BUSINESSCARD_NAME, MySQLiteHelper.COLUMN_BUSINESSCARD_EMAIL,
									MySQLiteHelper.COLUMN_BUSINESSCARD_PHONENUMBER, MySQLiteHelper.COLUMN_BUSINESSCARD_WEBSITE };
	
	public BusinessCardsDataSource(Context context)
	{
		dbHelper = new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close()
	{
		dbHelper.close();
	}
	
	public BusinessCard createBusinessCard(String name, String email, String phoneNumber, String website)
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_BUSINESSCARD_NAME, name);
		values.put(MySQLiteHelper.COLUMN_BUSINESSCARD_EMAIL, email);
		values.put(MySQLiteHelper.COLUMN_BUSINESSCARD_PHONENUMBER, phoneNumber);
		values.put(MySQLiteHelper.COLUMN_BUSINESSCARD_WEBSITE, website);
		
		long insertId = database.insert(MySQLiteHelper.TABLE_BUSINESSCARDS, null, values);
		
		Cursor cursor = database.query(	MySQLiteHelper.TABLE_BUSINESSCARDS,
										allColumns,
										MySQLiteHelper.COLUMN_ID + " = " + insertId,
										null,
										null,
										null,
										null);
		cursor.moveToFirst();
		BusinessCard newBusinessCard = cursorToBusinessCard(cursor);
		cursor.close();
	
		return newBusinessCard;
	}
	
	public void deleteBusinessCard(long id)
	{
		//long id = businessCard.getID();
		//System.out.println("Business Card deleted with id: " + id);
		database.delete(	MySQLiteHelper.TABLE_BUSINESSCARDS,
							MySQLiteHelper.COLUMN_ID + "=" + id,
							null);
	}
	
	public List<BusinessCard> getAllBusinessCards()
	{
		List<BusinessCard> businessCards = new ArrayList<BusinessCard>();
		
		Cursor cursor = database.query(	MySQLiteHelper.TABLE_BUSINESSCARDS,
										allColumns,
										null,
										null,
										null,
										null,
										null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast())
		{
			BusinessCard businessCard = cursorToBusinessCard(cursor);
			businessCards.add(businessCard);
			cursor.moveToNext();
		}
		
		//Closing the Cursor... now
		cursor.close();
		return businessCards; //Returning a list of business cards available from the Table
	}
	
	private BusinessCard cursorToBusinessCard(Cursor cursor)
	{
		BusinessCard businessCard = new BusinessCard();
		businessCard.setID(cursor.getLong(0));
		businessCard.setName(cursor.getString(1));
		businessCard.setEmail(cursor.getString(2));
		businessCard.setPhoneNumber(cursor.getString(3));
		businessCard.setWebsite(cursor.getString(4));
		return businessCard;
	}

}
