package com.example.beeblebrox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/*
 * Class to handle the fragment to start and end a chat room.
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class ChatList extends Fragment{
	private Button startRoom;
	private Button joinRoom;
	private EditText userName;
	private EditText hostName;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View chatList = inflater.inflate(R.layout.chat_frag, container, false);
		
		startRoom = (Button)chatList.findViewById(R.id.roomStartButton);
		joinRoom = (Button)chatList.findViewById(R.id.chatJoinButton);
		userName = (EditText)chatList.findViewById(R.id.userNameField);
		hostName = (EditText)chatList.findViewById(R.id.chatHostField);
		
		//I might load the default user name and hint it here
		
		startRoom.setOnClickListener(new ChatRoomStarter(getActivity(),userName,Room.HOST_MODE));
		joinRoom.setOnClickListener(new ChatRoomJoiner(getActivity(),hostName,Room.CLIENT_MODE));
		
		return chatList;
	}
}