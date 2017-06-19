package com.example.zlv_skripchenko.activities;


import com.example.zlv_skripchenko.utils.Contract.Names;
import com.example.zlv_skripchenko.db.DatabaseOpenHelper;
import com.example.zlv_skripchenko.R;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ReadActivity extends Activity implements OnClickListener{

	TextView textName;
	TextView textSurname;
	TextView textEmail;
	TextView textPhone;
	Button btnCancelRead;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read);
		
		textName = (TextView) findViewById(R.id.textName);
		textSurname = (TextView) findViewById(R.id.textSurname);
		textEmail = (TextView) findViewById(R.id.textEmail);
		textPhone = (TextView) findViewById(R.id.textPhone);
		
		 long id = getIntent().getLongExtra("_id",0);
	     DatabaseOpenHelper dbhelper = DatabaseOpenHelper.getDatabaseOpenHelperInstance();
	     SQLiteDatabase sqliteDB = dbhelper.getReadableDatabase();
	     Cursor c = sqliteDB.query(Names.TABLE_NAME, null, BaseColumns._ID + "=" + id, null, null, null, null);
		
	        if (c.moveToFirst()) {
	        	textName.setText("Name: "+c.getString(c.getColumnIndex(Names.NAME)));
	        	textSurname.setText("Surname: "+c.getString(c.getColumnIndex(Names.SURNAME)));
	        	textEmail.setText("Email: "+c.getString(c.getColumnIndex(Names.EMAIL)));
	        	textPhone.setText("Phone: "+c.getString(c.getColumnIndex(Names.TELEPHONE)));
	        }
	        sqliteDB.close();
		
		btnCancelRead = (Button) findViewById(R.id.btnCancelRead);
		btnCancelRead.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btnCancelRead){
			
			finish();
		}
		
	}
	
}
