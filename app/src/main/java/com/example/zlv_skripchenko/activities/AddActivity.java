package com.example.zlv_skripchenko.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.zlv_skripchenko.db.Controller;
import com.example.zlv_skripchenko.R;

public class AddActivity extends Activity implements OnClickListener{
	EditText edName;
	EditText edSurname;
	EditText edEmail;
	EditText edNumber;
	Button btnSave;
	Button btnCancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		edName = (EditText) findViewById(R.id.edName);
		edSurname = (EditText) findViewById(R.id.edSurname);
		edEmail = (EditText) findViewById(R.id.edEmail);
		edNumber = (EditText) findViewById(R.id.edNumber);
		
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btnSave)
		{

			Controller.write('"'+edName.getText().toString()+'"', '"'+edSurname.getText().toString()+'"', '"'+edEmail.getText().toString()+'"', '"'+edNumber.getText().toString()+'"');
			//Integer.parseInt(edNumber.getText().toString()));
			finish();
		}
		if(v.getId()==R.id.btnCancel){finish();}
	}
}
