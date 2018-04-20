package com.lifelineconnect.m8thubadmin.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.lifelineconnect.m8thubadmin.Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class DB_Countrylist {

	 protected static final String TAG = "DB_callSetting";
		private final Context mContext;
	    private SQLiteDatabase mDb;
	    private DataBaseHelper mDbHelper;
	    static ArrayList<HashMap<String, String>> locallist=null;
	    static DB_Countrylist dbc=null;
	    
	    public DB_Countrylist(Context context)
	    {
	    	 this.mContext = context; 
	           mDbHelper = new DataBaseHelper(mContext); 
	          if(context !=null)
	          {
	        	// new Log(mContext);
	          }
	    }
	public Cursor getcountryPostionId(String code)
	{
		try
		{
			String sql ="SELECT countryID,countryprefix,countryname,images,alpha2code,countrycode FROM countries where  alpha2code LIKE '"+code+"'";

			Cursor mCur = mDb.rawQuery(sql, null);

			if (mCur!=null)
			{
				mCur.moveToNext();
			}
			return mCur;
		}
		catch (SQLException mSQLException)
		{
			Utils.showlog(TAG, "getTestData >>"+ mSQLException.toString());
			throw mSQLException;
		}
	}


	public DB_Countrylist CreateDataBase() throws SQLException
	    {
	    	
	    	try  
	        { 
	            mDbHelper.createDataBase(); 
	           
	        }  
	        catch (IOException mIOException)
	        {
				Utils.showlog(TAG, mIOException.toString() + " UnableToCreateDatabase");
	            throw new Error("UnableToCreateDatabase");
	        } 
	        return this; 
	    	
	    	
	    }
	    
	    public DB_Countrylist open() throws SQLException
	    {
	    	 try  
	         { 
	             mDbHelper.openDataBase(); 
	             mDbHelper.close(); 
	             mDb = mDbHelper.getReadableDatabase(); 
	         }  
	         catch (SQLException mSQLException)
	         {
				 Utils.showlog(TAG, "open >>"+ mSQLException.toString());
	             throw mSQLException; 
	         } 
	         return this; 
	    }
	    
	    public void close()  
	    { 
	        mDbHelper.close(); 
	    } 
	    
	    public Cursor getCountrylist()
	    {
	    	try 
	         { 
	             String sql ="SELECT * FROM countries";

	             Cursor mCur = mDb.rawQuery(sql, null);
	        	              
	             if (mCur!=null) 
	             { 
	                mCur.moveToNext(); 
	             } 
	             return mCur; 
	         } 
	         catch (SQLException mSQLException)
	         {
				 Utils.showlog(TAG, "getTestData >>"+ mSQLException.toString());
	             throw mSQLException; 
	         } 
	    }
	    


	final   static void opendDb( Context context)
	   {
		   dbc= new DB_Countrylist(context);
		   dbc.CreateDataBase();
		   dbc.open();
		   
	   }
	 final  static void closedDb()
	   {
		   if(dbc !=null)
		   {
			   dbc.close();
			   dbc =null;
		   }
	   }
	   
	 final   static void clearArray()
	    {
	    	  if(locallist ==null)
	           {
	        	   locallist = new ArrayList<HashMap<String,String>>();
	           }
	           else locallist.clear();
	    }
	    
	    public static final  void delocArrar()
	    {
	    	locallist=null;
	    }
	   
	   
	   //=============================================================
	   
	    public static String GetColumnValue(Cursor cur, String ColumnName) {
			try {
				return cur.getString(cur.getColumnIndex(ColumnName));
			} catch (Exception ex) {
				return "";
			}
		}
	    
	    
}