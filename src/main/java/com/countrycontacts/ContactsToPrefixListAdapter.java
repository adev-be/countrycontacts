package com.countrycontacts;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.nationalcontacts.R;

public class ContactsToPrefixListAdapter extends BaseAdapter {

	private Context main_context;
	private ArrayList<Contact> alc;
	private LayoutInflater inflater;

	public HashMap<Integer, String> checked = new HashMap<Integer, String>();

	public ContactsToPrefixListAdapter (Context context, ArrayList<Contact> a) {
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
	public View getView(int position, View view, ViewGroup parent) {

		final ViewHolder viewHolder;
		View rowView = view;

		if (rowView == null) {

			rowView = inflater.inflate(R.layout.contact_entry_toprefix, parent, false);

			viewHolder = new ViewHolder();

			viewHolder.nameTextView= (TextView)rowView.findViewById(R.id.nameText);
			viewHolder.phoneTextView = (TextView)rowView.findViewById(R.id.phoneText);
			viewHolder.checkBox = (CheckBox)rowView.findViewById(R.id.checkbox);

			rowView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) rowView.getTag();
		}

		viewHolder.ref = position;
		//Log.d("InfoLog","viewHolder.ref = position; "+viewHolder.ref);
		viewHolder.nameTextView.setText(alc.get(position).getName());
		viewHolder.phoneTextView.setText(alc.get(position).getNumber()); 
		viewHolder.checkBox.setClickable(true);


		if (checked.containsKey(viewHolder.ref)) {  ///if this id is present as key in hashmap   
			Log.d("InfoLog","checked.containsKey "+viewHolder.ref);
			if (checked.get(viewHolder.ref).equals("true")) { //also check whether it is true or false to check/uncheck checkbox 
				Log.d("InfoLog","checked.get(position) "+viewHolder.ref); 
				viewHolder.checkBox.setChecked(true);
			} else {
				viewHolder.checkBox.setChecked(false);
			}
		} else {
			viewHolder.checkBox.setChecked(false);
		}
			
		viewHolder.checkBox.setOnCheckedChangeListener(new OncheckchangeListener(viewHolder));
		return rowView;
	}

	class ViewHolder {

		private TextView nameTextView = null;
		private TextView phoneTextView = null;
		private CheckBox checkBox = null;

		int ref;
	}

	class OncheckchangeListener implements OnCheckedChangeListener {

		ViewHolder viewHolder = null; 
		public OncheckchangeListener(ViewHolder viHolder) {
			viewHolder =  viHolder;  
		}
		@Override 
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {

			if (viewHolder.checkBox.equals(buttonView)) {       
				if (!isChecked) {
					//Log.d("InfoLog","checked.get before "+checked.get(viewHolder.ref));
					checked.remove(viewHolder.ref);
					//Log.d("InfoLog","checked.get after "+checked.get(viewHolder.ref));
				} else {
					checked.put(viewHolder.ref,"true");
				}

			} else
				Log.d("InfoLog","i m in checkchange ");
		}
	}
}
