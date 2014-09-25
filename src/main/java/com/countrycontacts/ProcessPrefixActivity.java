package com.countrycontacts;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nationalcontacts.R;

public class ProcessPrefixActivity extends Activity {

	private ListView contactListView;
	private Button prefixButton;
	private EditText prefixEdit;
	private ProcessPrefixActivity thisActivity;
	
	private ContactsToPrefixListAdapter adapter;
	
	private ArrayList<Contact> contactList;
	
	private boolean dialog_confirm_showed = false;
    private boolean dialog_progress_showed = false;

	static final int DIALOG_PROGRESS = 1;
	static final int DIALOG_CONFIRM = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		thisActivity = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.processprefix_activity);
		contactListView = (ListView) findViewById(R.id.notPrefixedContactList);
		prefixButton = (Button) findViewById(R.id.prefixButton);
		prefixEdit = (EditText) findViewById(R.id.prefixEdit);
		
		TextView txtInfo = (TextView) findViewById(R.id.instructionText);
		txtInfo.setPadding(0, 5, 0, 0);
		txtInfo.setBackgroundColor(Color.DKGRAY);
		txtInfo.setTextColor(Color.GRAY);
		txtInfo.setGravity(Gravity.CENTER_HORIZONTAL);
		
		new ProcessNotPrefixedContactsTask().execute();	
		
		prefixEdit.setGravity(Gravity.CENTER_VERTICAL);
		prefixButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (((prefixEdit.getText().length()==2) 
						&& (prefixEdit.getText().charAt(0) == '+') 
						&& (Character.isDigit(prefixEdit.getText().charAt(1)))) 
					|| ((prefixEdit.getText().length()==3) 
						&& ((prefixEdit.getText().charAt(0) == '+') 
						&& (Character.isDigit(prefixEdit.getText().charAt(1)))
						&& (Character.isDigit(prefixEdit.getText().charAt(2))))
					|| ((prefixEdit.getText().length()==4) 
						&& ((prefixEdit.getText().charAt(0) == '+') 
						&& (Character.isDigit(prefixEdit.getText().charAt(1)))
						&& (Character.isDigit(prefixEdit.getText().charAt(2)))
						&& (Character.isDigit(prefixEdit.getText().charAt(3))))))) {
					
					if (adapter.checked.size() > 0) {
						if (dialog_confirm_showed) removeDialog(DIALOG_CONFIRM);
						dialog_confirm_showed = showDialog(DIALOG_CONFIRM, null);
					} else {
						Toast.makeText(thisActivity, "First pick numbers to prefix", Toast.LENGTH_LONG).show();
					}	
				} else {
					Toast.makeText(thisActivity, "Prefix must be on format '+XX'", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	protected Dialog onCreateDialog(int id, final Bundle arguments) {
		switch(id) {
		case DIALOG_PROGRESS:
			ProgressDialog progressDialog = new ProgressDialog(ProcessPrefixActivity.this);
            //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Loading. Please wait...");
            progressDialog.setIndeterminate(true);
            return progressDialog;
            
		case DIALOG_CONFIRM :
			LayoutInflater factory = LayoutInflater.from(this);
	        View alertDialogView = factory.inflate(R.layout.alertdialog, null);
			
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			//On affecte la vue personnalisé que l'on a crée à notre AlertDialog
	        adb.setView(alertDialogView);
	 
	        //On donne un titre à l'AlertDialog
	        adb.setTitle("Modify contacts as shown below, confirm?");
	        //On modifie l'icône de l'AlertDialog pour le fun ;)
	        adb.setIcon(android.R.drawable.ic_dialog_alert);
	 
			final ArrayList<Contact> contacts_to_modify =  new ArrayList<Contact>();
			Iterator<Integer> iter = adapter.checked.keySet().iterator();
			while (iter.hasNext()) {
				Integer position = iter.next();
				contacts_to_modify.add(contactList.get(position));
			}
			
			String numbers = "";
			for (Contact contact : contacts_to_modify) {
				numbers = numbers+"\n"+contact.getName()+" "+prefixEdit.getText()+contact.getNumber().substring(1);
			} 
			
			TextView tv = (TextView) alertDialogView.findViewById(R.id.tv);
			tv.setText(numbers);
	        
	        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
	        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener()  {
	        	@Override
	        	@SuppressWarnings("unchecked")
				public void onClick(DialogInterface dialog, int item) {
						new UpdateContactsTask().execute(contacts_to_modify);
				}
			});

	        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
	        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	//Log.d("CANCEL", "No number(s) modified");
	            } });
	        
			AlertDialog alert_actions = adb.create();
			return alert_actions;
		default : break;
		}
		return null;
	}
	
	public void updateContact(String contactId, String newNumber, int type_int) {

	    //ASSERT: @contactId already has a phone number of the specified type 
	    ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>(); 
	    String selectPhone = Data.CONTACT_ID + "=? AND " + Data.MIMETYPE + "='"  + 
	                    Phone.CONTENT_ITEM_TYPE + "'" + " AND " + Phone.TYPE + "=?";
	    String[] phoneArgs = new String[]{contactId, String.valueOf(type_int)}; 
	    ops.add(ContentProviderOperation.newUpdate(Data.CONTENT_URI)
	            .withSelection(selectPhone, phoneArgs)
	            .withValue(Phone.NUMBER, newNumber)
	            .build()); 
	    try {
			this.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			Log.e("RemoteException", e.getLocalizedMessage());
		} catch (OperationApplicationException e) {
			Log.e("OperationApplicationException", e.getLocalizedMessage());
		}
	}
	
	private ArrayList<Contact> getContactsNotPrefixed() {    

		ArrayList<Contact> contactList = ContactsList.thisActivity.getAllContacts();
		ArrayList<Country> countries = CountriesList.getOriginCountriesOfContacts(contactList);		
		
		ArrayList<Contact> contacts = CountriesList.getNotPrefixedContacts(countries, contactList);
		
		return contacts;
	}
	
	private class ProcessNotPrefixedContactsTask extends AsyncTask<Void, Void, ArrayList<Contact>> {
		
	    protected ArrayList<Contact> doInBackground(Void... urls) {

	    	contactList = getContactsNotPrefixed();
			return contactList;
	    }
	    
	    protected void onPreExecute() {
			if (dialog_progress_showed) removeDialog(DIALOG_PROGRESS);
			dialog_progress_showed = showDialog(DIALOG_PROGRESS, null);
	    }
	    
	    protected void onPostExecute(ArrayList<Contact> result) {
	    	
			adapter = new ContactsToPrefixListAdapter(ProcessPrefixActivity.this, result);
			contactListView.setAdapter(adapter); 
						
			dismissDialog(DIALOG_PROGRESS);
	    }
	}
	
	private class UpdateContactsTask extends AsyncTask<ArrayList<Contact>, Void, Boolean> {
		
	    protected Boolean doInBackground(ArrayList<Contact>... contacts) {
			for (Contact contact : contacts[0]) {
				updateContact(contact.getContact_id(), prefixEdit.getText()+contact.getNumber().substring(1), contact.getTypeInt());
			}
			return true;
	    }
	    
	    protected void onPreExecute() {
			if (dialog_progress_showed) removeDialog(DIALOG_PROGRESS);
			dialog_progress_showed = showDialog(DIALOG_PROGRESS, null);
	    }
	    
	    protected void onPostExecute(Boolean result) {
			dismissDialog(DIALOG_PROGRESS);
			
			Intent intent = new Intent(ProcessPrefixActivity.this, ContactsList.class); 
			startActivity(intent);
	    }
	}
}
