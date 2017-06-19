package com.example.zlv_skripchenko.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.zlv_skripchenko.R;
import com.example.zlv_skripchenko.activities.AddActivity;
import com.example.zlv_skripchenko.activities.NetworkTestRequest;

/**
 * 
 * @author Skripchenko Yevgen
 * @version 1.0
 */
public class ActionBarView extends LinearLayout implements OnClickListener{

	private View mConvertView;
	private ImageButton btnAdd;
	private ImageButton id_goto_network;

	private OnDispatchClickListener mListenerClick;
	
	public interface OnDispatchClickListener{
		public void onDispatchClick(int id);
	}
	
	public ActionBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mConvertView = LayoutInflater.from(context).inflate(R.layout.action_bar, this);
		
		btnAdd = (ImageButton)mConvertView.findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(this);

		id_goto_network = (ImageButton)mConvertView.findViewById(R.id.id_goto_network);
		id_goto_network.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		
		
        switch (v.getId()) {
        case R.id.btnAdd:
        	
        	Intent intent = new Intent(v.getContext(),AddActivity.class);
			v.getContext().startActivity(intent);
         break;
			case R.id.id_goto_network:
				Intent mintent = new Intent(v.getContext(),NetworkTestRequest.class);
				v.getContext().startActivity(mintent);
				break;
        default:
                break;
        }
        if(mListenerClick != null)
                mListenerClick.onDispatchClick(v.getId());   
                
	}
	
	
	public void setOnDispatchClickListener(OnDispatchClickListener v){
		mListenerClick = v;
	}

}
