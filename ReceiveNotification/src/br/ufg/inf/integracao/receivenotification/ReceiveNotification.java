package br.ufg.inf.integracao.receivenotification;

import android.app.Application;
import android.content.Context;

public class ReceiveNotification  extends Application{

	private static Context context;
	
	public void onCreate(){
        super.onCreate();
        ReceiveNotification.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ReceiveNotification.context;
    }
    
}
