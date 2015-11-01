package com.example.beeblebrox;

import android.content.Context; 
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Class to start a chat room.
 * 
 *Author: Prithvijit Chakrabarty (prithvichakra@gmail.com) 
 **/
public class ChatRoomStarter implements OnClickListener{
	
	private String mode;
	private EditText userNameField;
	private Context context;
	
	public ChatRoomStarter(Context context, EditText userNameField, String mode){
		this.mode = mode;
		this.userNameField = userNameField;
		this.context = context;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context, Room.class);
		String user = userNameField.getText().toString();
		if(user == null || user.length() == 0){
			Toast.makeText(context, "Please enter a user name", Toast.LENGTH_SHORT).show();
		}
		else{
			intent.putExtra("User", mode+" "+user);
			context.startActivity(intent);
		}
	}

}