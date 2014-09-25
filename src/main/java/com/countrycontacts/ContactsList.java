package com.countrycontacts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.nationalcontacts.R;

public class ContactsList extends Activity {

	public static ContactsList thisActivity;

	private HashMap<String, ListView> contactsListViews;

	private TelephonyManager telephonyManager;

	private HashMap<String, ArrayList<Contact>> listsContacts;
	private TabHost tabs;
	private HashMap<String, TabHost.TabSpec> listSpec;
	
    private boolean dialog_progress_showed = false;
	private boolean dialog_action_showed = false; 
	private boolean dialog_add_showed = false;
	private boolean dialog_remove_showed = false;

	static final int DIALOG_PROGRESS = 4;
	static final int DIALOG_REMOVE_COUNTRY = 3;
	static final int DIALOG_ADD_COUNTRY = 2;
	static final int DIALOG_ACTIONS_WITH_SKYPE = 1;
	static final int DIALOG_CONTACT_ACTIONS = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Eula.show(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contact_list_activity);

		listsContacts = new HashMap<String, ArrayList<Contact>>();
		contactsListViews = new HashMap<String, ListView>();
		listSpec = new HashMap<String, TabHost.TabSpec>();

		tabs = (TabHost) findViewById(R.id.tabhost);
		tabs.setup();
	}

	@Override
	public void onStart() {
		super.onStart();
		thisActivity = this;

		telephonyManager =((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE));
		String country_iso = telephonyManager.getNetworkCountryIso();
		String prefix = CountriesList.getPrefixFromISO(country_iso);

		if (prefix == null) {
			prefix = "+00";
			Intent intent = new Intent(ContactsList.this, NoPrefixAnnounceActivity.class); 
			startActivity(intent); 
		}

		constructTabsAndFillMaps();

		final String name = CountriesList.getNameFromISO(country_iso);

		String name_in_properties = null;
		for (String country : getCountriesTabs()) {
			if (country.equals(name)) {
				name_in_properties = country;
			}
		}
		if (name_in_properties == null) {
			if (listSpec.size() < 3) {
				Toast.makeText(this, "Tab for local country ("+name+") added", Toast.LENGTH_LONG).show();
				addCountryTab(listSpec.size(), name, prefix);
				saveProperty(name);
			} else {
				Toast.makeText(this, "Remove a tab and restart app to add a tab for current country", Toast.LENGTH_LONG).show();
			}
		}
	}

	private void constructTabsAndFillMaps() {

		final String[] countries_names = getCountriesTabs();
		
		listSpec.clear(); // remove it from memory
		listsContacts.clear();
		contactsListViews.clear();
		
		tabs.setCurrentTab(0);
		tabs.clearAllTabs();

		for(int i=0; i<countries_names.length; i++) {
			addCountryTab(i, countries_names[i], CountriesList.getPrefixFromName(countries_names[i]));
		}
	}

	private void dialContact(ArrayList<Contact> list, long id) {	
		String number = list.get((int) id).getNumber();
		Uri number_uri = Uri.parse("tel:"+number);

		Intent intent = new Intent(Intent.ACTION_DIAL, number_uri); 
		startActivity(intent); 
	}

	public ArrayList<Contact> removeDuplicateContacts(ArrayList<Contact> alc_in) {
		ArrayList<Contact> alc_out = new ArrayList<Contact>();
		Iterator<Contact> iter = alc_in.iterator();

		Contact contact_previous = new Contact("$*$","$*$","$*$","$*$",-1,"$*$");

		while (iter.hasNext()) {	
			Contact contact_current = iter.next();
			if (!contact_previous.getNumber().equals(contact_current.getNumber())) alc_out.add(contact_current); // On rempli alc_out
			contact_previous = contact_current;
		}

		return alc_out;
	}

	public ArrayList<Contact> getContactsFromPrefix(String plus_prefix) {    
		String zero_prefix = "00"+plus_prefix.substring(1);

		Uri uri = Data.CONTENT_URI;

		String[] projection = new String[] {BaseColumns._ID, Data.CONTACT_ID, Data.DISPLAY_NAME, Phone.NUMBER, Phone.TYPE};

		String selection = Data.MIMETYPE+"='"+Phone.CONTENT_ITEM_TYPE +"' AND "
		+Data.IN_VISIBLE_GROUP+"='1' AND "
		+Phone.NUMBER+" like '"+plus_prefix+"%' OR "
		+Phone.NUMBER+" like '"+zero_prefix+"%'";

		String sortOrder = Data.DISPLAY_NAME+ " ASC";

		ContentResolver contentresolver = getContentResolver();

		Cursor cursor = contentresolver.query(uri, projection, selection, null, sortOrder);  

		String data_id, contact_id, name, number, type_string;
		int type_int;

		ArrayList<Contact> contacts = new ArrayList<Contact>();

		if (cursor.moveToFirst()) {
			do {
				data_id = cursor.getString(cursor.getColumnIndex(BaseColumns._ID));
				//Log.e("DATA_ID", data_id);
				contact_id = cursor.getString(cursor.getColumnIndex(Data.CONTACT_ID));
				//Log.e("CONTACT_ID", contact_id);
				name = cursor.getString(cursor.getColumnIndex(Data.DISPLAY_NAME));
				number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
				type_int = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Phone.TYPE)));
				type_string = typeFromIntValue(type_int);

				contacts.add(new Contact(data_id, contact_id, name, number, type_int, type_string));

			} while (cursor.moveToNext());
		}
		cursor.close();
		return contacts;
	}

	public ArrayList<Contact> getAllContacts() {    

		Uri uri = Data.CONTENT_URI;

		String[] projection = new String[] {BaseColumns._ID, Data.CONTACT_ID, Data.DISPLAY_NAME, Phone.NUMBER, Phone.TYPE};

		String selection = Data.MIMETYPE+"='"+Phone.CONTENT_ITEM_TYPE +"' AND "
		+Data.IN_VISIBLE_GROUP+"='1'";

		String sortOrder = Data.DISPLAY_NAME+ " ASC";

		ContentResolver cr = getContentResolver();

		Cursor cursor = cr.query(uri, projection, selection, null, sortOrder); 

		ArrayList<Contact> alc_in = new ArrayList<Contact>();
		String data_id, contact_id, name, number, type_string;
		int type_int;

		if (cursor.moveToFirst()) {
			do {
				data_id = cursor.getString(cursor.getColumnIndex(BaseColumns._ID));
				//Log.e("DATA_ID", data_id);
				contact_id = cursor.getString(cursor.getColumnIndex(Data.CONTACT_ID));
				//Log.e("CONTACT_ID", contact_id);
				name = cursor.getString(cursor.getColumnIndex(Data.DISPLAY_NAME));
				number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
				type_int = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Phone.TYPE)));
				type_string = typeFromIntValue(type_int);

				alc_in.add(new Contact(data_id, contact_id, name, number, type_int, type_string));

			} while (cursor.moveToNext());
		}
		cursor.close();

		// On vérifie qu'il n'y a qu'une fois chaque contact
		ArrayList<Contact> alc_out = new ArrayList<Contact>();
		Iterator<Contact> iter = alc_in.iterator();

		Contact contact_previous = new Contact("$*$","$*$","$*$","$*$",-1,"$*$");

		while (iter.hasNext()) {	
			Contact contact_current = iter.next();
			if (!contact_previous.getNumber().equals(contact_current.getNumber())) alc_out.add(contact_current);
			contact_previous = contact_current;
		}

		return alc_out;
	}
	
	private String typeFromIntValue(int value) {
		switch (value) {
		case 1 : return "HOME";
		case 2 : return "MOBILE";
		case 3 : return "WORK";
		case 7 : return "OTHER";
		default : return "OTHER";
		}
	}

	public String[] getCountriesTabs() {

		SharedPreferences preferences = getSharedPreferences("Tabs", Context.MODE_PRIVATE);

		Map<String,?> map = preferences.getAll();
		Collection<?> objects = map.values();

		ArrayList<String> list = new ArrayList<String>();
		Iterator<?> iter = objects.iterator();
		while(iter.hasNext()) {
			String temp = (String) iter.next();
			list.add(temp);
		}
		String[] tabs = new String[list.size()];
		tabs = list.toArray(tabs);

		return tabs;
	}

	/**
	 * !!! tabNum begin to 1 !!!
	 * 
	 * @param tabNum
	 * @param country
	 */
	public void saveProperty(String country) {

		SharedPreferences preferences = getSharedPreferences("Tabs", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		editor.putString(country, country);
		editor.commit();
	}

	public void removeProperty(String country) {

		SharedPreferences preferences = getSharedPreferences("Tabs", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		String[] countriesTabs = getCountriesTabs();
		for (int i=0; i<countriesTabs.length; i++) {
			if (countriesTabs[i].equals(country)) {
				editor.remove(country);
				editor.commit();
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id, final Bundle arguments) {
		switch(id) {
		
		case DIALOG_PROGRESS:
			ProgressDialog progressDialog = new ProgressDialog(ContactsList.this);
            //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Loading. Please wait...");
            progressDialog.setIndeterminate(true);
            return progressDialog;
            
		case DIALOG_CONTACT_ACTIONS:
			final CharSequence[] items = {"View "+arguments.getString("name")+" information", 
					"Send a message to "+arguments.getString("name"), "Copy "+arguments.getString("number")+" to clipboard"};

			AlertDialog.Builder builder_actions = new AlertDialog.Builder(this);
			builder_actions.setTitle("Choose an action");

			builder_actions.setItems(items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {

					switch (item) {
					case 0 : 
						long contact_id = Integer.parseInt(arguments.getString("contact_id"));
						Uri person_uri = (ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contact_id));
						Intent info_intent = new Intent(Intent.ACTION_VIEW, person_uri); 
						startActivity(info_intent); 
						dialog.cancel();
						break;
					case 1 :
						String number = arguments.getString("number");
						Uri message_uri = Uri.parse("smsto:"+number);
						Intent message_intent = new Intent(Intent.ACTION_VIEW, message_uri); 
						startActivity(message_intent); 
						dialog.cancel();
						break;
					case 2 : 
						ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
						cm.setText(arguments.getString("number"));

						Toast.makeText(getApplicationContext(), arguments.getString("number")
								+" copied to clipboard", Toast.LENGTH_SHORT).show();
					default : break;
					}
				}
			});

			AlertDialog alert_actions = builder_actions.create();
			return alert_actions;

		case DIALOG_ADD_COUNTRY:
			final CharSequence[] countries_items = arguments.getCharSequenceArray("countries");
			// 2 - - - - - - - > Build and display a list of countries available
			AlertDialog.Builder builder_addTab = new AlertDialog.Builder(this);

			builder_addTab.setTitle("Choose a country");
			builder_addTab.setItems(countries_items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					int tabsCount = listSpec.size();
					if(tabsCount >= 3) {
						Toast.makeText(thisActivity, "Three tabs maximum", Toast.LENGTH_LONG).show();
					} 
					else {
						CharSequence selected_name = countries_items[item];
						addCountryTab(tabsCount+1, selected_name.toString(), CountriesList.getPrefixFromName(selected_name.toString()));
						saveProperty(selected_name.toString());
					} 
				}
			});
						
			AlertDialog alert_addTab = builder_addTab.create();
			return alert_addTab;

		case DIALOG_REMOVE_COUNTRY:
			final CharSequence[] countries_items3 = getCountriesTabs();

			AlertDialog.Builder builder_removeTab = new AlertDialog.Builder(this);
			builder_removeTab.setTitle("Choose tab to remove");

			builder_removeTab.setItems(countries_items3, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					removeCountryTab((String) countries_items3[item]);
					removeProperty((String) countries_items3[item]);
				}
			});
			AlertDialog alert_removeTab = builder_removeTab.create();
			return alert_removeTab;
		default : break;
		}
		return null;
	}

	private void addCountryTab(int number, String country_name, String country_prefix) {
		ListView listview = new ListView(thisActivity);
		listview.setId(number);

		ArrayList<Contact> list_temp = getContactsFromPrefix(country_prefix);
		final ArrayList<Contact> list_temp_unique = removeDuplicateContacts(list_temp); 

		ContactsListAdapter adapter = new ContactsListAdapter(thisActivity, list_temp_unique);

		listview.setAdapter(adapter);
		listview.setTextFilterEnabled(true);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dialContact(list_temp_unique, id);
			}
		});

		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id) {
				//Log.e("ContactsList", "onItemLongClick : "+id);
				if (dialog_action_showed) removeDialog(DIALOG_CONTACT_ACTIONS);

				Bundle arguments = new Bundle(3);
				arguments.putString("contact_id", list_temp_unique.get((int) id).getContact_id());
				arguments.putString("name", list_temp_unique.get((int) id).getName());
				arguments.putString("number", list_temp_unique.get((int) id).getNumber());			

				dialog_action_showed = showDialog(DIALOG_CONTACT_ACTIONS, arguments);

				return true;
			}
		});

		listsContacts.put(country_name, list_temp_unique);
		contactsListViews.put(country_name, listview);

		tabs.getTabContentView().addView(listview);
		
		//TabHost.TabSpec spec = tabs.newTabSpec("tag"+number).setIndicator(country_name).setContent(listview.getId()); 
		TabHost.TabSpec spec;

		// Location info
		TextView txtTabInfo = new TextView(this);
		txtTabInfo.setText(country_name);
		txtTabInfo.setPadding(0, 5, 0, 0);
		txtTabInfo.setTextSize(20);

		txtTabInfo.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab));
		txtTabInfo.setTextColor(Color.GRAY);
		txtTabInfo.setGravity(Gravity.CENTER_HORIZONTAL);
		txtTabInfo.setHeight(45);
		spec = tabs.newTabSpec("tag"+number);
		spec.setContent(listview.getId());
		spec.setIndicator(txtTabInfo);

		tabs.addTab(spec);
		listSpec.put(country_name, spec);
	}

	private void removeCountryTab(String country) {
		listSpec.remove(country); // remove it from memory
		listsContacts.remove(country);
		contactsListViews.remove(country);

		tabs.setCurrentTab(0);
		tabs.clearAllTabs();  // clear all tabs from the tabhost  
		for(TabHost.TabSpec spec : listSpec.values()) {// add all that you remember back
			//tabs.setup();
			tabs.addTab(spec);
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "Add a country tab").setIcon(R.drawable.tag);
		menu.add(0, 2, 2, "Remove a country tab").setIcon(R.drawable.undo);
		menu.add(0, 3, 3, "Suppress all tabs").setIcon(R.drawable.flash);
		menu.add(0, 4, 4, "Prefix numbers").setIcon(R.drawable.options);
		return true;
	}	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:  
			new ProcessCountriesListTask().execute();
			return dialog_add_showed;
		case 2:  
			if (dialog_remove_showed) removeDialog(DIALOG_REMOVE_COUNTRY);
			dialog_remove_showed = showDialog(DIALOG_REMOVE_COUNTRY, null);
			return dialog_remove_showed;
		case 3:
			listSpec.clear(); // remove it from memory
			listsContacts.clear();
			contactsListViews.clear();
			
			tabs.setCurrentTab(0);
			tabs.clearAllTabs();

			SharedPreferences preferences = getSharedPreferences("Tabs", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();

			editor.clear();
			editor.commit();
			return true;
		case 4:
			Intent intent = new Intent(ContactsList.this, ProcessPrefixActivity.class); 
			startActivity(intent);
		}
		return false;
	}
	
	private class ProcessCountriesListTask extends AsyncTask<Void, Void, CharSequence[]> {
		
	    /** The system calls this to perform work in a worker thread and
	      * delivers it the parameters given to AsyncTask.execute() */
	    protected CharSequence[] doInBackground(Void... urls) {
			// 1 - - - - - - - > Process to know from which countries are contacts
			ArrayList<Contact> contactList = getAllContacts();
			final ArrayList<Country> countries = CountriesList.getOriginCountriesOfContacts(contactList);

			ArrayList<String> countries_names = new ArrayList<String>();
			String[] countriesTabs = getCountriesTabs();
			for(int i = 0; i<countries.size(); i++) {
				boolean alreadyTab = false;
				for (String tab : countriesTabs) {
					if (tab.equals(countries.get(i).getName())) {
						alreadyTab = true;
					}
				}
				if ((!alreadyTab) 
						// On veut savoir s'il y a effectivement des contacts pour le pays
						&& (getContactsFromPrefix(countries.get(i).getPrefix())).size() > 0) 
					// S'il y en a, on l'ajoute à la liste des listes disponibles
					countries_names.add(countries.get(i).getName());
			}

	        return countries_names.toArray(new CharSequence[]{});
	    }
	    
	    protected void onPreExecute() {
			if (dialog_progress_showed) removeDialog(DIALOG_PROGRESS);
			dialog_progress_showed = showDialog(DIALOG_PROGRESS, null);
	    }
	    
	    /** The system calls this to perform work in the UI thread and delivers
	      * the result from doInBackground() */
	    protected void onPostExecute(CharSequence[] result) {
	    	Bundle bundle = new Bundle();
	    	bundle.putCharSequenceArray("countries", result);
	    	
	    	dismissDialog(DIALOG_PROGRESS);
	    	
	    	if (dialog_add_showed) removeDialog(DIALOG_ADD_COUNTRY);
			dialog_add_showed = showDialog(DIALOG_ADD_COUNTRY, bundle);
			
	    }
	}
}