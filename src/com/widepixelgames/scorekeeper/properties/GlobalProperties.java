package com.widepixelgames.scorekeeper.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class GlobalProperties {

	private Map<String, String> variables = new HashMap<String, String>();
	private static GlobalProperties instance = null;
	
	private GlobalProperties(){
	}

	public static GlobalProperties getInstance(){
		if(instance == null){
			instance = new GlobalProperties();
		}
		return instance ;
	}

	public void load(InputStream openRawResource) {
		
		Properties properties = new Properties();
		try {
			properties.load(openRawResource);
			for(Object pkey : properties.keySet()){
				String key = "${" + pkey + "}";
				String value = properties.getProperty((String)pkey);
				variables.put(key, value.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		
		String value = variables.get(key);
		if(value != null){
			return "true".equals(value);
		} else {
			return defaultValue;
		}		
	}
	
	
	public String getString(String key, String defaultValue){
		String value = variables.get(key);
		if(value != null){
			return value;
		} else {
			return defaultValue;
		}
	}

	public void put(String key, String value) {
		variables.put(key, value);
		
	}

	public String resolve(String rawtextContainingProperties) {

		String finalString = rawtextContainingProperties;
		boolean done = false;
		int startSearchAt = 0;
		while(!done){
			int startOfKeyResultAt = rawtextContainingProperties.indexOf("${", startSearchAt);
			if(startOfKeyResultAt >= 0){
				int endOfKeyResultAt = rawtextContainingProperties.indexOf("}", startOfKeyResultAt);
				String key = rawtextContainingProperties.substring(startOfKeyResultAt, endOfKeyResultAt + 1);
				String value = getString(key, "not_found:" + key);
				finalString = finalString.replaceAll(Pattern.quote(key), value);
				
				startSearchAt = startOfKeyResultAt + 2;
			} else {
				done = true;
			}
		}

		return finalString;
	}

	public void printAll(){
		System.out.println("printAll variables:");
		for(String s : variables.keySet()){
			System.out.println(s + " : " + variables.get(s));
		}
	}

}
