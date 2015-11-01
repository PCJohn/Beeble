package com.example.beeblebrox;

import java.util.ArrayList; 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

/*
 * List adapter for the contact list.
 * 
 * Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class UserListAdapter extends BaseAdapter implements ListAdapter{
	
	private ArrayList<String> list;
	private Context context;
	
	public UserListAdapter(ArrayList<String> list, Context context){
		this.list = list;
		this.context = context;
	}
	
	@Override
	public int getCount() { 
	    return list.size(); 
	}

	@Override
	public Object getItem(int pos) { 
	    return list.get(pos); 
	} 

	@Override
	public long getItemId(int pos) { 
	    //return list.get(pos).getId();
	    return 0;
		//just return 0 if your list items do not have an Id variable.
	} 

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    if (view == null) {
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	        view = inflater.inflate(R.layout.list_element, null);
	    } 

	    //Handle TextView and display string from your list
	    TextView listItemText = (TextView)view.findViewById(R.id.user_name); 
	    listItemText.setText(list.get(position));

	    //Handle buttons and add onClickListeners
	    Button chatButton = (Button)view.findViewById(R.id.user_button);
	    chatButton.setOnClickListener(new UserClickListener(this.context, list.get(position)));
	    return view;
	}
}
