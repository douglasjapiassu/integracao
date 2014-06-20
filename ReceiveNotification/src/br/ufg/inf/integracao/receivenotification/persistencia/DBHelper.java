package br.ufg.inf.integracao.receivenotification.persistencia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("DefaultLocale")
public class DBHelper extends SQLiteOpenHelper{
	private static final String nomeDB = "RN.db";
	private static final int versaoDB = 1;
	public final String tableNotificacoes = "NOTIFICACOES";
	public final String tableUsuarios = "USUARIO";
	
	
	public DBHelper(Context context) {
		super(context, nomeDB, null, versaoDB);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String scriptCreateDBNotificacoes = 
				"CREATE TABLE " + tableNotificacoes + " (" +
				"IDENTIFICADOR integer primary key autoincrement," +
				"MENSAGEM text not null," +
				"DATA_RECEBIMENTO long not null," +
				"DATA datetime default current_timestamp" +
				");";
		
		String scriptCreateDBUsuarios = 
				"CREATE TABLE " + tableUsuarios + " (" +
				"IDENTIFICADOR integer primary key autoincrement," +
				"NOME text not null," +
				"EMAIL not null," +
				"DATA datetime default current_timestamp" +
				");";
		
		db.execSQL(scriptCreateDBNotificacoes);
		db.execSQL(scriptCreateDBUsuarios);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + tableNotificacoes);
		db.execSQL("DROP TABLE IF EXISTS " + tableUsuarios);
        onCreate(db);
	}


}
