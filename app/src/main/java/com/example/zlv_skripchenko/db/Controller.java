package com.example.zlv_skripchenko.db;

import com.example.zlv_skripchenko.utils.Contract.Names;
import com.example.zlv_skripchenko.utils.TestItem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * 
 * @author Skripchenko Yevgen
 * @version 1.0
 */

public class Controller {
	
	 private static int maxRowsInNames = -1;
	 public static int getMaxRowsInNames() {return maxRowsInNames;}
	 public static void setMaxRowsInNames(int maxRowsInNames) {Controller.maxRowsInNames = maxRowsInNames;}
	 private static final String TAG = Controller.class.getSimpleName();
	
	 
		/**
		 */ 
	 
	 public static boolean write(String name, String surname, String email, String telephone) {
		 boolean result = false;
		 try {
			DatabaseOpenHelper dbhelper = DatabaseOpenHelper.getDatabaseOpenHelperInstance();
			SQLiteDatabase db = dbhelper.getWritableDatabase();
			int countRows = -1;
			String addValue = null;
			Cursor cursor = db.query(Names.TABLE_NAME,null, null, null, null,null,null);// Names.DEFAULT_SORT);new String[] { "count(*)" }
			if (cursor.moveToFirst())
			{
				countRows = cursor.getInt(0);
			}
			cursor.close();
			if ((maxRowsInNames == -1) || (maxRowsInNames >= countRows)) {
				addValue = "INSERT INTO "+Names.TABLE_NAME +"("+Names.NAME+","+Names.SURNAME+","+Names.EMAIL+","+Names.TELEPHONE+")VALUES("+name+","+surname+","+email+","+telephone+");";

				ContentValues values = new ContentValues();
				values.put(Names.NAME, name);
				values.put(Names.SURNAME,surname);
				values.put(Names.EMAIL, email);
				values.put(Names.TELEPHONE, telephone);

				db.beginTransaction();
				result = db.insert(Names.TABLE_NAME, null, values) > 0;
				db.setTransactionSuccessful();
				db.endTransaction();
				db.close();
			}
//			db.beginTransaction();
//			db.execSQL(addValue);
//            db.setTransactionSuccessful();
//            db.endTransaction();
//			db.close();
		} catch (SQLiteException e) {Log.e(TAG, "Failed open rimes database. ", e);
		} catch (SQLException e) {Log.e(TAG, "Failed to insert Names. ", e);}
		return result;
	 }
	 
	
	 
	 public static ArrayList<TestItem> read(){
		 ArrayList<TestItem> items = new ArrayList<>();
		 DatabaseOpenHelper dbhelper = DatabaseOpenHelper.getDatabaseOpenHelperInstance();
		 SQLiteDatabase db = dbhelper.getReadableDatabase();
		 Cursor cursor = db.query(Names.TABLE_NAME, null, null, null, null, null, null);
		 while(cursor.moveToNext()) {
			 long id = cursor.getLong(cursor.getColumnIndex(Names._ID));
			 String name = cursor.getString(cursor.getColumnIndex(Names.NAME));
			 String surname = cursor.getString(cursor.getColumnIndex(Names.SURNAME));
			 String email = cursor.getString(cursor.getColumnIndex(Names.EMAIL));
			 String telephone = cursor.getString(cursor.getColumnIndex(Names.TELEPHONE));
			 items.add(new TestItem(id,name,surname,email,telephone));
		 }
		 db.close();
		 return items;
	 }



	public static ArrayList<TestItem> readTest(String inputName,String inputSurname,String inputEmail,String inputNumber){
		ArrayList<TestItem> items = new ArrayList<>();
		DatabaseOpenHelper dbhelper = DatabaseOpenHelper.getDatabaseOpenHelperInstance();
		SQLiteDatabase db = dbhelper.getReadableDatabase();

		String whereClause = Names.NAME+" =? AND "+Names.SURNAME+" =? AND "+Names.EMAIL+" =? AND "+Names.TELEPHONE+" =?";
		String[] whereArgs = new String[]{inputName, inputSurname, inputEmail, inputNumber};

		Cursor cursor = db.query(Names.TABLE_NAME, null, whereClause, whereArgs, null, null, null);
		while(cursor.moveToNext()) {
			long id = cursor.getLong(cursor.getColumnIndex(Names._ID));
			String name = cursor.getString(cursor.getColumnIndex(Names.NAME));
			String surname = cursor.getString(cursor.getColumnIndex(Names.SURNAME));
			String email = cursor.getString(cursor.getColumnIndex(Names.EMAIL));
			String telephone = cursor.getString(cursor.getColumnIndex(Names.TELEPHONE));
			items.add(new TestItem(id,name,surname,email,telephone));
		}
		db.close();
		return items;
	}






	/**
		 */ 
	
	 public static void update(String name,String surname,String email,String number, long id) {
		  try {
              DatabaseOpenHelper dbhelper = DatabaseOpenHelper.getDatabaseOpenHelperInstance();
              SQLiteDatabase sqliteDB = dbhelper.getWritableDatabase();
              String quer = null;
              Cursor cursor = sqliteDB.query(Names.TABLE_NAME,null, null, null, null,null,null);
              if (cursor.moveToFirst()) {
                    cursor.getInt(0);
              }
              cursor.close();
              quer = String.format("UPDATE " + Names.TABLE_NAME + " SET " + 
            		  				Names.NAME + " = '" + name +"',"+
            		  				Names.SURNAME + " = '" + surname + "',"+
            		  				Names.EMAIL + " = '" + email + "',"+
            		  				Names.TELEPHONE + " = '" + number + 
            		  				"' WHERE " + BaseColumns._ID + " = " + id);
             
              sqliteDB.execSQL(quer);
              sqliteDB.close();
      } catch (SQLiteException e) {
              Log.e(TAG, "Failed open database. ", e);
      } catch (SQLException e) {
              Log.e(TAG, "Failed to update Names. ", e);
      }
	 }
	
		/**
		 */ 
	
	  public static void delete(TestItem item) {
          DatabaseOpenHelper dbhelper= DatabaseOpenHelper.getDatabaseOpenHelperInstance();
          SQLiteDatabase sqliteDB = dbhelper.getWritableDatabase();
          sqliteDB.delete(Names.TABLE_NAME, BaseColumns._ID  + " = " + item.id, null);
          sqliteDB.close();
	  }
	
	

	
	
}
