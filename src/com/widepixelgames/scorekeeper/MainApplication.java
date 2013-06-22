package com.widepixelgames.scorekeeper;

import java.lang.annotation.Annotation;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import com.widepixelgames.scorekeeper.properties.GlobalProperties;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {

	@Override
    public void onCreate() {
		
		ReportsCrashes reportsCrashes = new ReportsCrashes() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int socketTimeout() {
				//default value from ReportsCrashes.java
				return 3000;
			}
			
			@Override
			public String sharedPreferencesName() {
				//default value from ReportsCrashes.java
				return "";
			}
			
			@Override
			public int sharedPreferencesMode() {
				//default value from ReportsCrashes.java 
				return Context.MODE_PRIVATE;
			}
			
			@Override
			public int resToastText() {
				//default value from ReportsCrashes.java 
				return 0;
			}
			
			@Override
			public int resNotifTitle() {
				//default value from ReportsCrashes.java 
				return 0;
			}
			
			@Override
			public int resNotifTickerText() {
				//default value from ReportsCrashes.java 
				return 0;
			}
			
			@Override
			public int resNotifText() {
				//default value from ReportsCrashes.java 
				return 0;
			}
			
			@Override
			public int resNotifIcon() {
				//default value from ReportsCrashes.java 
				return android.R.drawable.stat_notify_error;
			}
			
			@Override
			public int resDialogTitle() {
				//default value from ReportsCrashes.java 
				return 0;
			}
			
			@Override
			public int resDialogText() {
				//default value from ReportsCrashes.java 
				return 0;
			}
			
			@Override
			public int resDialogOkToast() {
				//default value from ReportsCrashes.java 
				return 0;
			}
			
			@Override
			public int resDialogIcon() {
				//default value from ReportsCrashes.java 
				return android.R.drawable.ic_dialog_alert;
			}
			
			@Override
			public int resDialogEmailPrompt() {
				//default value from ReportsCrashes.java 
				return 0;
			}
			
			@Override
			public int resDialogCommentPrompt() {
				//default value from ReportsCrashes.java 
				return 0;
			}
			
			@Override
			public ReportingInteractionMode mode() {
				return ReportingInteractionMode.SILENT;
			}
			
			@Override
			public int maxNumberOfRequestRetries() {
				//default value from ReportsCrashes.java 
				return 3;
			}
			
			@Override
			public String mailTo() {
				return GlobalProperties.getInstance().getString("${crash_report_email}", "no email address defined");
			}
			
			@Override
			public String[] logcatArguments() {
				//default value from ReportsCrashes.java
				return new String[] { "-t", "200", "-v", "time" };
			}
			
			@Override
			public boolean includeDropBoxSystemTags() {
				//default value from ReportsCrashes.java 
				return false;
			}
			
			@Override
			public String formUriBasicAuthPassword() {
				//default value from ReportsCrashes.java 
				return ACRA.NULL_VALUE;
			}
			
			@Override
			public String formUriBasicAuthLogin() {			
				//default value from ReportsCrashes.java 
				return ACRA.NULL_VALUE;
			}
			
			@Override
			public String formUri() {				
				return "";
			}
			
			@Override
			public String formKey() {				
				return "";
			}
			
			@Override
			public boolean forceCloseDialogAfterToast() {
				//default value from ReportsCrashes.java 
				return false;
			}
			
			@Override
			public int dropboxCollectionMinutes() {
				//default value from ReportsCrashes.java  
				return 5;
			}
			
			@Override
			public boolean deleteUnapprovedReportsOnApplicationStart() {
				//default value from ReportsCrashes.java 
				return true;
			}
			
			@Override
			public ReportField[] customReportContent() {
				//default value from ReportsCrashes.java 
				return new ReportField[] {};
			}
			
			@Override
			public int connectionTimeout() {
				//default value from ReportsCrashes.java
				return 3000;
			}
			
			@Override
			public String[] additionalSharedPreferences() {
				//default value from ReportsCrashes.java
				return new String[] {};
			}
			
			@Override
			public String[] additionalDropBoxTags() {
				//default value from ReportsCrashes.java
				return new String[] {};
			}
		};
		
        ACRA.init(this, reportsCrashes);
        super.onCreate();
    }
	
}
