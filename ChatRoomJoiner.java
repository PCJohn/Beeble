package com.example.beeblebrox;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Class to join a chat room.
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class ChatRoomJoiner implements OnClickListener{
	
	private String mode;
	private EditText hostNameField;
	private Context context;
	
	public ChatRoomJoiner(Context context, EditText userNameField, String mode){
		this.mode = mode;
		this.hostNameField = userNameField;
		this.context = context;
	}
	
	@Override
	public void onClick(View v){
		Intent intent = new Intent(context, Room.class);
		String user = hostNameField.getText().toString();
		if(user == null || user.length() == 0){
			Toast.makeText(context, "Please enter a user name", Toast.LENGTH_SHORT).show();
		}
		else{
			intent.putExtra("User", mode+" "+user);
			context.startActivity(intent);
		}
	}
}
