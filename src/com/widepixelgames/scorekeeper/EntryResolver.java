package com.widepixelgames.scorekeeper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.widepixelgames.scorekeeper.properties.GlobalProperties;
import com.widepixelgames.scorekeeper.qr.QrFormatHandler;

public class EntryResolver {

	public static void resolve(QrFormatHandler qrFormatHandler, String qrText){

		System.out.println("qrText " + qrText);
		String qrTextDecoded = "";
		try {
			qrTextDecoded = URLDecoder.decode(qrText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}

		String[] kvps = qrTextDecoded.split("&");
		if(kvps.length >= 1){
			for(String kvp : kvps){
				String[] pair = kvp.split("=");
				if(pair.length == 2){
					String keyName = qrFormatHandler.getkeyName(pair[0]);
					GlobalProperties.getInstance().put(keyName, pair[1]);
				}
			}
		}
	}

}
