package com.example.beeblebrox;

import java.io.Serializable;

/*
 * Class to describe a move made on the drawing board.
 * This is used to describe moves across the network
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class Move implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int DOWN = 0;
	public static final int UP = 1;
	public static final int MOVE = 2;
	public static final int ID = 3;

	
	float x;
	float y;
	int type;
	int id;		//Identify the client sending the message
	int width;
	int height;
	
	public Move(float x, float y, int width, int height, int type, int id){
		this.x = x;
		this.y = y;
		this.type = type;
		this.id = id;
		this.width = width;
		this.height = height;
	}
}
