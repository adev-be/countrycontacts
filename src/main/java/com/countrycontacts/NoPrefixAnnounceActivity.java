package com.countrycontacts;

import com.nationalcontacts.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class NoPrefixAnnounceActivity extends Activity {
	
	private ImageView announce;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.no_prefix_announce);
        announce = (ImageView) findViewById(R.id.announce);
	}
}
