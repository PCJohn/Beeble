package com.example.beeblebrox;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/*
 * Thread to run a socket server.
 * Run as an async task that accepts clients and adds them to the board move recipients.
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class ServerThread extends AsyncTask<Object,Object,Object>{

	private Board board;
	private ServerSocket server;
	private Context context;

	static ArrayList<Socket> clients;

	public ServerThread(Context c, Board board){
		this.board = board;
		this.context = c;
		clients = new ArrayList<Socket>();
	}
	
	@Override
	protected void onPreExecute(){
		try{
			this.server = new ServerSocket(Room.PORT);
			//server.setReuseAddress(true);
			publishProgress("Server up");
		}
		catch(BindException be){
			
		}
		catch(Exception e){
			publishProgress("ERROR:Couldn't create server: "+e.getClass());
		}
	}

	@Override
	protected Object doInBackground(Object... param){
		while(true){
			if(isCancelled())
				break;
			try{
				Socket socket = server.accept();
				board.addClient(socket);
				clients.add(socket);
				publishProgress("start receiver",socket);
			}
			catch(Exception e){
				publishProgress("ERROR:Server accept error: "+e.getClass());
			}
		}
		return 0;
	}
	
	@Override
	protected void onProgressUpdate(Object... param){
		if(param[0].toString().equals("start receiver")){
			try{
				(new Receiver(context,(Socket)param[1],board,clients.size())).execute();
				Toast.makeText(context, "Client Connected", Toast.LENGTH_SHORT).show();
			}
			catch(Exception e){
				Toast.makeText(context, "ERROR: Server couldn't start receiver", Toast.LENGTH_SHORT).show();
			}
		}
		else{
			Toast.makeText(context, param[0].toString(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onPostExecute(Object obj){
		try{
			publishProgress("Closing server");
			server.close();
		}
		catch(IOException ioe){
			publishProgress("ERROR: Server close error");
		}
	}
	
}