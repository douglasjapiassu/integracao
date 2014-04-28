package br.ufg.inf.integracao.receivenotification.persistencia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("DefaultLocale")
public class DBHelper extends SQLiteOpenHelper{
	private static final String nomeDB = "RN.db";
	private static final int versaoDB = 1;
	public final String nomeTable = "NOTIFICACOES";
	
	
	public DBHelper(Context context) {
		super(context, nomeDB, null, versaoDB);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String scriptCreateDB = 
				"CREATE TABLE " + nomeTable + " (" +
				"IDENTIFICADOR integer primary key autoincrement," +
				"MENSAGEM text not null," +
				"DATA_RECEBIMENTO long not null," +
				"DATA datetime default current_timestamp" +
				");";
		
		db.execSQL(scriptCreateDB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + nomeTable);
        onCreate(db);
	}


}
