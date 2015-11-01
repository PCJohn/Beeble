package com.example.beeblebrox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/*
 * Background task to handle the reading activity from the client.
 * Constantly reads the socket and modifies the canvas
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class Receiver extends AsyncTask<Object,Object,Object>{
	
	private Socket socket;
	private Board board;
	private Context context;
	private ObjectInputStream objIn;
	private String ip;
	private int socketSet;
	private int recId;
		
	public Receiver(Context c, String ip, Board board) throws IOException{
		this.ip = ip;
		this.socket = null;
		this.board = board;
		this.context = c;
		this.socketSet = 0;
		this.recId = -1;
	}

	public Receiver(Context c, Socket socket, Board board, int id) throws IOException{
		this.socket = socket;
		this.board = board;
		this.context = c;
		this.socketSet = 1;
		this.recId = id;
	}

	@Override
	protected void onPreExecute(){
		//No preprocessing
	}
	
	//Receive coordinates from the other end
	@Override
	protected Object doInBackground(Object... params){
		if(socketSet == 0){
			try{
				if(socket == null){
					socket = new Socket(ip,Room.PORT);
				}
				objIn = new ObjectInputStream(socket.getInputStream());
				board.addClient(socket);
				socketSet = 2;
			}
			catch(Exception e){
				publishProgress("ERROR: Couldn't establish receiver: "+e.getClass());
			}
		}
		else if(socketSet == 1){
			try{
				objIn = new ObjectInputStream(socket.getInputStream());
				board.addClient(socket);
				socketSet = 2;
			}
			catch(Exception e){
				publishProgress("ERROR: Couldn't establish server receiver: "+e.getMessage());
			}
		}

		while(true){
			if(isCancelled())
				break;
			try{
				Move m = (Move)objIn.readObject();
				//Add the move to this board
				board.makeMove(m);
				//If on the server - send to everyone else
				
				/*if(recId != -1){
					m.id = recId;
					for(int i = 0; i < ServerThread.clients.size(); i++){
						(new ObjectOutputStream(ServerThread.clients.get(i).getOutputStream())).writeObject(m);
					}
				}*/
			}
			catch(Exception e){
				publishProgress("ERROR: Receiver connection error: "+e.getClass());
			}
		}
		return 0;
	}

	@Override
	protected void onProgressUpdate(Object... param){
		Toast.makeText(context, param[0].toString(), Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onPostExecute(Object obj){
		try{
			objIn.close();
			socket.close();
			board.removeClient(socket);
		}
		catch(IOException ioe){}
	}
}