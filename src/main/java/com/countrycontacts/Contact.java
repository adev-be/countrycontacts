package com.countrycontacts;

import java.io.InputStream;

public class Contact {
	private String data_id;
	private String contact_id;
	private String name;
	private String number;
	private InputStream photo;
	private int type_int;
	private String type_string;

	public Contact(String d, String c, String na, String nu, int ty_i, String ty_s) {
		data_id = d;
		contact_id = c;
		name = na;
		number = nu;
		type_int = ty_i;
		type_string = ty_s;
		photo = null;
	}

	public String getData_id() {
		return data_id;
	}

	public String getContact_id() {
		return contact_id;
	}
	
	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}
	
	public int getTypeInt() {
		return type_int;
	}
	
	public String getTypeString() {
		return type_string;
	}
	
	public boolean comparePrefix(String prefix) {
		if (number.length() >= prefix.length()) {
			if (number.charAt(0) == '+') {
				boolean same = true;
				for (int i=1; i<prefix.length(); i++) {
					if (!Character.isDigit(number.charAt(i)) 
							&& number.length() > i+1) {
						//Log.e("comparePrefix", number+" - number.length() : "+number.length()+", i : "+i);
						number = number.substring(0, i-1)+number.substring(i+1, number.length());
					}
					else if (number.charAt(i) != prefix.charAt(i)) {
						same = false;
						break;
					}
				}
				return same;
			} else if ((number.charAt(0) == '0') 
					&& (number.charAt(1) == '0')) {
				boolean same = true;
				prefix = "00"+prefix.substring(1);
				for (int i=2; i<prefix.length(); i++) {
					if (!Character.isDigit(number.charAt(i))
							&& number.length() > i+1) {
						number = number.substring(0, i-1)+number.substring(i+1, number.length());
					}
					else if (number.charAt(i) != prefix.charAt(i)) {
						same = false;
					}
				}
				return same;
			}
			return false;
		}
		return false;
	}
	
	public InputStream getPhoto() {
		return photo;
	}
	
	public void setPhoto(InputStream p) {
		photo = p;
	}
}
