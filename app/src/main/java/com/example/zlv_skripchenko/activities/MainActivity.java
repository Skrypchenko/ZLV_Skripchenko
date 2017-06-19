package com.example.zlv_skripchenko.activities;

import com.example.zlv_skripchenko.db.Controller;
import com.example.zlv_skripchenko.R;
import com.example.zlv_skripchenko.utils.TestItem;
import com.example.zlv_skripchenko.views.ActionBarView;
import com.example.zlv_skripchenko.views.ActionBarView.OnDispatchClickListener;
import com.example.zlv_skripchenko.adapters.ContactListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;


@SuppressLint("NewApi")
public class MainActivity extends Activity implements ContactListAdapter.OnItemLongClickListener, OnDispatchClickListener {

	private RecyclerView lv;
	private ContactListAdapter adapter;
	private ActionBarView mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mActionBar = (ActionBarView) findViewById(R.id.actionbar);

		lv = (RecyclerView) findViewById(R.id.listView1);


		List<TestItem>list = Controller.read();
		adapter = new ContactListAdapter(list);
		lv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
		lv.setAdapter(adapter);

		adapter.setOnItemLongClickListener(this);

		mActionBar.setOnDispatchClickListener(this);


//		Task task = new Task();
//		task.execute();


	}

	@Override
	public void onLongClick(final TestItem mitem) {
		final CharSequence[] items = { "Read", "Update", "Delete" };

		AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
		dialog.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int item) {

				switch (item) {
					case 0:
						read(mitem.id);
						break;
					case 1:
						update(mitem.id);
						break;
					case 2:
						delete(mitem);
						break;
				}
			}
		});
		dialog.show();
	}


//	class Task extends AsyncTask<Void,Void,Void>{
//
//		@Override
//		protected Void doInBackground(Void... voids) {
//			saveToFile("test.txt","Hello world");
//			return null;
//		}
//	}
//	public static String saveToFile(String filename, String contents) {
//		String storageState = Environment.getExternalStorageState();
//		if(!storageState.equals(Environment.MEDIA_MOUNTED)) {
//		//	throw new IllegalStateException("Media must be mounted");
//		}
//		File directory = Environment.getExternalStorageDirectory();
//		File file = new File(directory, filename);
//		FileWriter fileWriter;
//		try {
//			fileWriter = new FileWriter(file, false);
//			fileWriter.write(contents);
//			fileWriter.close();
//			return file.getAbsolutePath();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//


	@Override
	protected void onResume() {
		adapter.setItems(Controller.read());
		super.onResume();
	}





	public void delete(TestItem item) {
		Controller.delete(item);
		adapter.setItems(Controller.read());
	}
	

	public void update(long id) {
		Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
		intent.putExtra("_id", id);
		startActivity(intent);
	}


	public void read(long id) {
		Intent intent = new Intent(MainActivity.this, ReadActivity.class);
		intent.putExtra("_id", id);
		startActivity(intent);
	}

	@Override
	public void onDispatchClick(int id) {
	}

}
