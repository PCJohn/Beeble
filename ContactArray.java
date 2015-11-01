package com.example.beeblebrox;

import java.util.ArrayList; 

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/*
 * Class to load contacts from the standard contact db.
 * 
 * Author: Prithvijit Chakrabarty (prithvichakra@gmail.com)
 * */

public class ContactArray extends Fragment{
	
	public ArrayList<String> fetchContacts(){
		ArrayList<String> contactList = new ArrayList<String>();
		String phoneNumber = null;
		String email = null;
		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
		Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		String DATA = ContactsContract.CommonDataKinds.Email.DATA;
		StringBuffer output = new StringBuffer();
		ContentResolver contentResolver = this.getActivity().getContentResolver();
		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null); 
		// Loop for every contact in the phone
		if (cursor.getCount() > 0){
			while (cursor.moveToNext()){
				String contact_id = cursor.getString(cursor.getColumnIndex( _ID));
				String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
				output = new StringBuffer();
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
				if (hasPhoneNumber > 0){
					output.append(name);
					// Query and loop for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID+" = ?", new String[]{contact_id}, null);
					while (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
						output.append(" "+phoneNumber);
					}
					phoneCursor.close();
					// Query and loop for every email of the contact
					Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID+" = ?", new String[]{contact_id}, null);
					while(emailCursor.moveToNext()){
						email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
						//Append the e-mail ID if you want to
						//output.append(" Email:"+email);
					}
				emailCursor.close();
				}
				contactList.add(output.toString());
			}
		}
		return contactList;
	}
	
	public ArrayList<String> loadContacts(){
		ArrayList<String> contacts = new ArrayList<String>();
		//Get a list of phone contacts
		//UNCOMMENT THE NEXT LINE TO MAKE THE CONTACTS WORK
		//contacts = fetchContacts();
		
		//TEMPORARY - SO THE TESTING IS FASTER!
		contacts = new ArrayList<String>();
		
		//Get the list of display names - from the server
		//LOAD FROM SERVER HERE!!!!!
		return contacts;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contactArrayView = inflater.inflate(R.layout.contact_frag, container, false);
        ArrayList<String> contacts = loadContacts();
        UserListAdapter adapter = new UserListAdapter(contacts,this.getActivity());
        ListView lv = (ListView)contactArrayView.findViewById(R.id.contact_list);
        lv.setAdapter(adapter);
        return contactArrayView;
	}
}
