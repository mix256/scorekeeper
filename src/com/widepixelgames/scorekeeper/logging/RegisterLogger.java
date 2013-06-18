package com.widepixelgames.scorekeeper.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;

public class RegisterLogger {
	
	public static String getStorageState(){
		return Environment.getExternalStorageState();
	}
	
	public static void log(String userName, String message) {

		File root = Environment.getExternalStorageDirectory();
		System.out.println("Root " + root.toString());
		if (root.canWrite()) {
			System.out.println("Root.canWrite()!");
			try {
				File logFile = new File(root, "scorekeeper_" + userName + "_log.txt");
				FileWriter logWriter;
				logWriter = new FileWriter(logFile, true);
				BufferedWriter out = new BufferedWriter(logWriter);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
				String date = format.format(new Date());
				out.write(date + " - " + message + "\n");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Root cannot write!");
		}
	}	
}
