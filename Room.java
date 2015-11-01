package com.example.beeblebrox;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle; 
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/*
 * Activity for the chat room.
 * Waits for clients and sets up IO streams on connection.
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class Room extends Activity {
	
	public static int 	 PORT 		 = 8080;
	public static String CLIENT_MODE = "CLIENT";
	public static String HOST_MODE 	 = "HOST";
	
	private String userName;
	private String ipAddr;
	private String mode;
	private Board board;
	
	private ServerThread serverThread;
	private Receiver receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String userInfo = getIntent().getStringExtra("User");
		mode = userInfo.split(" ")[0];
		userName = userInfo.split(" ")[1];

		board = new Board(this);
        setContentView(board);

        if(mode.compareTo(Room.CLIENT_MODE) == 0){
        	//Get the IP address of the remote host - assume the user provides it for now
        	ipAddr = userName;
            Toast.makeText(getApplication(), "connecting to "+userName, Toast.LENGTH_SHORT).show();
            //Connect to a remote host
        	try{
        		receiver = new Receiver(this,ipAddr,board);
        		receiver.execute();
    		}
        	catch(Exception e){
        		Toast.makeText(this,"ERROR: Couldn't connect "+e.getClass(),Toast.LENGTH_SHORT).show();
        	}
        }
        else{
        	//Get the current IP address
        	ipAddr = getIpAddress();
        	Toast.makeText(this, "IP: "+ipAddr, Toast.LENGTH_SHORT).show();
        	//Start a server
        	serverThread = new ServerThread(this,board);
        	serverThread.execute();
        }

		setTitle(userName);
		getContentResolver();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.room, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		if(serverThread != null)
			serverThread.cancel(true);
		if(receiver != null)
			receiver.cancel(true);
		finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.action_clear: board.clear();
					  return true;
			//case R.id.action_setBackground:item;
			//		  return true;
			default:
		        return super.onOptionsItemSelected(item);
		}
	}

	 public String getIpAddress(){
		WifiManager wifiMan = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInf = wifiMan.getConnectionInfo();
		int ipAddress = wifiInf.getIpAddress();
		String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));
		return ip;
	 }
}