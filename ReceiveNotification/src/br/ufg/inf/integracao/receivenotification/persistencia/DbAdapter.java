package br.ufg.inf.integracao.receivenotification.persistencia;

import br.ufg.inf.integracao.receivenotification.model.Notificacao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbAdapter {
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	String[] colunas = new String[] { "IDENTIFICADOR", "MENSAGEM"};

	public DbAdapter(Context context) {
		dbHelper = new DBHelper(context);
	}
	
	public Notificacao select(int identificador) throws Exception {
		Notificacao notificacao = null;
		Cursor cursor = null;
 
		database = dbHelper.getReadableDatabase();
 
		String where = "LOGIN = ?";
 
		String argumentos[] = new String[] { String.valueOf(identificador)};
 
		cursor = database.query(dbHelper.cls.getName(), colunas, where, argumentos, null, null, null);
 
		if (cursor != null && cursor.moveToFirst()) {
			notificacao = new Notificacao();
			notificacao.setIdentificador(cursor.getInt(cursor.getColumnIndex("IDENTIFICADOR")));
			notificacao.setMensagem(cursor.getString(cursor.getColumnIndex("MENSAGEM")));
        }
 
		if (cursor != null)
			cursor.close();
 
		return notificacao;
	}
	
	public boolean salvarNotificacao(int identificador, String mensagem) {
		Notificacao notificacao = new Notificacao(identificador, mensagem);
		
		ContentValues content = new ContentValues();
		content.put("IDENTIFICADOR", notificacao.getIdentificador());
		content.put("MENSAGEM", notificacao.getMensagem());
		
		long retorno = database.insert(dbHelper.cls.getName(), null, content);
	       
        if(retorno != -1)
                return true;
        else
                return false;
	}

}
