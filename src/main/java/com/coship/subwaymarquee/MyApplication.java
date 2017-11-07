package com.coship.subwaymarquee;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {
	private static Context context;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context=this;
	}
	
	public static Context getApplication(){
		return context;
	}

   
}
