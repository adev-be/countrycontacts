package com.countrycontacts;

public class Country {
	private final String name;
	private final String alpha2;
	private final String prefix;
	
	public Country (String n, String a, String p) {
		name = n;
		alpha2 = a;
		prefix = p;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAlpha2() {
		return alpha2;
	}
	
	public String getPrefix() {
		return prefix;
	}
}
