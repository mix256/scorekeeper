package com.widepixelgames.scorekeeper.qr;

import com.widepixelgames.scorekeeper.properties.GlobalProperties;

public class QrFormatHandler {

	String qrFormat;
	
	public QrFormatHandler(String qrFormat) {
	
		this.qrFormat = qrFormat;
		
		String[] keyValuePairs = qrFormat.split("&");
		for(String keyValuePair : keyValuePairs){
			String[] keyValues = keyValuePair.split("=");
			GlobalProperties.getInstance().put(keyValues[0], keyValues[1]);
			System.out.println("QR format handler added key: " +  keyValues[0] + " with value " + keyValues[1]);
		}
	}

	public String getkeyName(String key){
		return GlobalProperties.getInstance().getString(key, "not_found:" + key);
	}
	
	public boolean isOfFormat(String scanned){
		
		String[] keyValuePairsFormat = qrFormat.split("&");
		String[] keyValuePairsScanned = scanned.split("&");
		if(keyValuePairsFormat.length != keyValuePairsScanned.length){
			System.out.println("keyValuePairsFormat.length != keyValuePairsScanned.length " + keyValuePairsFormat.length + " " + keyValuePairsScanned.length);
			return false;
		}
	
		for(String keyValuePair : keyValuePairsFormat){
			String[] keyValues = keyValuePair.split("=");
			if(!scanned.contains(keyValues[0])){
				System.out.println("Didn't find " + keyValues[0] + " in " + scanned);
				return false;
			}
		}
		return true;
	}

}
