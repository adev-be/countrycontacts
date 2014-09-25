package com.countrycontacts;

import java.util.ArrayList;

import com.nationalcontacts.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactsListAdapter extends BaseAdapter {

	private Context main_context;
	private ArrayList<Contact> alc;
	private LayoutInflater inflater;
	
	public ContactsListAdapter (Context context, ArrayList<Contact> a) {
		main_context = context;
		alc = a;
		inflater = (LayoutInflater) main_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return alc.size();
	}

	@Override
	public Object getItem(int position) {
		//Log.e("ContactsListAdapter", "getItem : "+position);
		return alc.get(position);
	}

	@Override
	public long getItemId(int position) {
		//Log.e("ContactsListAdapter", "getItemId : "+position);
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		v = inflater.inflate(R.layout.contact_entry, parent, false);

		TextView nameText = (TextView) v.findViewById(R.id.nameText);  
		nameText.setText(alc.get(position).getName()); 
		
		TextView phoneText = (TextView) v.findViewById(R.id.phoneText);  
		phoneText.setText(alc.get(position).getNumber());

		TextView typeText = (TextView) v.findViewById(R.id.typeText);  
		typeText.setText(alc.get(position).getTypeString());
		
		//ImageView contactImage = (ImageView) v.findViewById(R.id.contactImage);
		
		//Bitmap image = BitmapFactory.decodeStream(alc.get(position).getPhoto());
		//contactImage.setImageBitmap(image);
		return v;
	}
}
