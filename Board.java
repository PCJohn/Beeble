package com.example.beeblebrox;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import android.content.Context; 
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

/*
 * View for the shared drawing board
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */
public class Board extends SurfaceView {
		
	private ArrayList<Path> path;
	private Paint paint;
	private Sender sender;
	private Context context;
	private float x = 0, y = 0;
	private boolean clear;
	private int background;
	private int color;
	
	int width, height;
	
	public Board(Context context){
		super(context);
		this.context = context;
		path = new ArrayList<Path>();
		path.add(new Path());	//Path for this device
		path.add(new Path());	//Path for the server
		for(Path p : path)
			p.moveTo(0,0);
		background = Color.BLACK;
		setBackgroundColor(color);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
	    paint.setStrokeWidth(3);
	    color = Color.WHITE;
	    paint.setColor(color);
	    sender = new Sender();
	    clear = false;
	    
	    DisplayMetrics m = context.getResources().getDisplayMetrics();
	    width = m.widthPixels;
	    height = m.heightPixels;
	}
	
	public void addClient(Socket socket) throws IOException{
		sender.addClient(socket);
	}
	
	public void removeClient(Socket socket){
		sender.removeClient(socket);
	}

	public void makeMove(Move m){
		int id = m.id;
		/*while(path.size() < m.id)
			path.add(new Path());*/
		id = 1;
		
		//Scaling to the device's screen size
		float mx = width*m.x/m.width;
		float my = height*m.y/m.height;
		switch(m.type){
			case Move.DOWN: path.get(id).moveTo(mx,my);
							break;
			case Move.UP:	path.get(id).lineTo(mx,my);
							break;
			case Move.MOVE:	path.get(id).lineTo(mx,my);
							break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		x = event.getX();
		y = event.getY();
		try{
			switch(event.getAction()){
				case MotionEvent.ACTION_DOWN: path.get(0).moveTo(x,y);
											  sender.send(new Move(x,y,width,height,Move.DOWN,1));
											  break;
				case MotionEvent.ACTION_MOVE: path.get(0).lineTo(x,y);
											  sender.send(new Move(x,y,width,height,Move.MOVE, 1));
											  break;
				case MotionEvent.ACTION_UP:	  path.get(0).lineTo(x,y);
											  sender.send(new Move(x,y,width,height,Move.UP, 1));
											  break;
			}
		}
		catch(Exception e){
			Toast.makeText(context, "ERROR: Can't send move: "+e.getClass(), Toast.LENGTH_SHORT).show();
		}
		return true;
	}
	
	public void clear(){
		clear = true;
	}
	
	public void onDraw(Canvas canvas){
		if(clear == true){
			canvas.drawColor(background);
			for(Path p : path)
				p.reset();
			clear = false;
		}
		else{
			for(Path p : path)
				canvas.drawPath(p, paint);
		}
		invalidate();
	}
}