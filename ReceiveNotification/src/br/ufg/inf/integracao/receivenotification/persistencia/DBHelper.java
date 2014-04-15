package br.ufg.inf.integracao.receivenotification.persistencia;

import java.lang.reflect.Field;
import java.util.Date;

import br.ufg.inf.integracao.receivenotification.model.Notificacao;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("DefaultLocale")
public class DBHelper extends SQLiteOpenHelper{
	private static final String nomeDB = "RN.db";
	private static final int versaoDB = 1;
	public final static Class<Notificacao> cls = Notificacao.class;
	
	
	public DBHelper(Context context) {
		super(context, nomeDB, null, versaoDB);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Field fieldlist[] = cls.getFields();
		String scriptCreateDB = 
				"CREATE TABLE " + cls.getName() + " (";
		
		for (int i = 0; i < fieldlist.length; i++) {  
           Field fld = fieldlist[i];
           if (fld.getName() == "identificador") {
        	   scriptCreateDB += 
            		   "IDENTIFICADOR integer primary key autoincrement,";
           } else {
        	   if (fld.getType() == String.class) {
        		   scriptCreateDB += fld.getName().toUpperCase() +
        				   " text not null";
        	   } else if (fld.getType() == Date.class) {
        		   scriptCreateDB += fld.getName().toUpperCase() +
        				   " date not null";
        	   }
           }
	    }
		
		scriptCreateDB += ");";
		
		db.execSQL(scriptCreateDB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + nomeDB);
        onCreate(db);
	}


}
