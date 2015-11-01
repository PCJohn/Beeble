package com.example.beeblebrox;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/*
 * Class to handle sending to multiple clients.
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class Sender {

	private ArrayList<Socket> socketList;
	private ArrayList<ObjectOutputStream> objOut;

	public Sender(){
		socketList = new ArrayList<Socket>();
		objOut = new ArrayList<ObjectOutputStream>();
	}

	public void addClient(Socket socket) throws IOException{
		socketList.add(socket);
		objOut.add(new ObjectOutputStream(socket.getOutputStream()));
	}

	//Send the message to all connected clients
	public void send(Move m) throws IOException{
		for(ObjectOutputStream stream : objOut){
			stream.writeObject(m);
		}
	}
	
	public void removeClient(Socket socket){
		int i = socketList.indexOf(socket);
		try{
			objOut.get(i).close();
			objOut.remove(i);
			socketList.get(i).close();
			socketList.remove(socket);
		}
		catch(IOException ioe){}
	}
}