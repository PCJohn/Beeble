package com.example.beeblebrox;

import android.content.Context; 
import android.content.Intent;
import android.view.View;

/*
 * Click listener for every button to chat with a contact.
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class UserClickListener implements View.OnClickListener{

	private Context context;
	private String user;
	
	public UserClickListener(Context context, String user){
		this.context = context;
		this.user = user;
	}
	
	private boolean isOnline(String user){
		return true;
	}
	
	@Override
	public void onClick(View v) {
		//First, check if the user is online!
		//TODO: CHECK IF USER IS ONLINE
		//String ipAddr = "NOT_ONLINE";
		if(isOnline(user)){
			//Get the IP address of the host here
			//GETTING IP ADDRESS
			String userIpAddr = "192.168.1.43";
			user = user+" "+userIpAddr;
			
		}
		else{
			//Scream out - "The user isn't online!"
		}
		//Switch to new activity here - start/load chat with user
		Intent intent = new Intent(context, Room.class);
		intent.putExtra("User", Room.CLIENT_MODE+" "+user.split(" ")[0]);
		context.startActivity(intent);
	}
}