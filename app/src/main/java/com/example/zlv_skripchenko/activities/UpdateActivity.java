package com.example.zlv_skripchenko.activities;



import com.example.zlv_skripchenko.utils.Contract.Names;
import com.example.zlv_skripchenko.db.Controller;
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
import android.widget.EditText;

public class UpdateActivity extends Activity implements OnClickListener{

	Button buttonUpdate , buttonCancel;
	EditText edName,edSurname,edEmail,edNumber;
	long id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		edName = (EditText) findViewById(R.id.edUName);
		edSurname = (EditText) findViewById(R.id.edUSurname);
		edEmail = (EditText) findViewById(R.id.edUEmail);
		edNumber = (EditText) findViewById(R.id.edUNumber);

		 this.id = getIntent().getLongExtra("_id",0);
	     DatabaseOpenHelper dbhelper = DatabaseOpenHelper.getDatabaseOpenHelperInstance();
	     SQLiteDatabase sqliteDB = dbhelper.getReadableDatabase();
	     Cursor c = sqliteDB.query(Names.TABLE_NAME, null, BaseColumns._ID + "=" + id, null, null, null, null);
		
	        if (c.moveToFirst()) {
	        	edName.setText(c.getString(c.getColumnIndex(Names.NAME)));
	        	edSurname.setText(c.getString(c.getColumnIndex(Names.SURNAME)));
	        	edEmail.setText(c.getString(c.getColumnIndex(Names.EMAIL)));
	        	edNumber.setText(c.getString(c.getColumnIndex(Names.TELEPHONE)));
	        }
	        sqliteDB.close();
		
		
		
		buttonUpdate = (Button)findViewById(R.id.buttonUpdate);
		buttonUpdate.setOnClickListener(this);
		
		buttonCancel = (Button)findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonUpdate:
	        Controller.update(edName.getText().toString(),
				        		edSurname.getText().toString(), 
				        		edEmail.getText().toString(), 
				        		edNumber.getText().toString(), id);
			
			finish();
			break;
		case R.id.buttonCancel:
			finish();
			break;
		default:
			break;
		}
	}
}